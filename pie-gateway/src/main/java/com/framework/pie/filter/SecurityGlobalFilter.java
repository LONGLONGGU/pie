package com.framework.pie.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.framework.pie.constant.AuthConstants;
import com.framework.pie.constant.ResultCode;
import com.framework.pie.redis.client.RedisClient;
import com.framework.pie.util.ResponseUtils;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;

/**
 * 安全拦截全局过滤器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisClient redisClient;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 非JWT或者JWT为空不作处理,请求头中不包含Authorization或者请求参数中的token不以bearer 开头
        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !token.startsWith(AuthConstants.AUTHORIZATION_PREFIX)) {
            return chain.filter(exchange);
        }

        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在拦截响应token失效
        token = token.replace(AuthConstants.AUTHORIZATION_PREFIX, Strings.EMPTY);
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (Exception e){
            return ResponseUtils.writeErrorInfo(response, ResultCode.TOKEN_INVALID_OR_EXPIRED.getCode()
                    ,ResultCode.TOKEN_INVALID_OR_EXPIRED.getMsg());
        }
        String payload = jwsObject.getPayload().toString();
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        String jti = jsonObject.getStr(AuthConstants.JWT_JTI);
        Boolean isBlack = redisClient.exists(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (isBlack) {
            return ResponseUtils.writeErrorInfo(response, ResultCode.TOKEN_ACCESS_FORBIDDEN.getCode()
                    ,ResultCode.TOKEN_ACCESS_FORBIDDEN.getMsg());
        }

        // 存在token且不是黑名单，request写入JWT的载体信息
        request = exchange.getRequest().mutate()
                .header(AuthConstants.JWT_PAYLOAD_KEY, URLEncoder.encode(payload,"UTF-8"))
                .build();

        exchange = exchange.mutate().request(request).build();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        //此处修改过滤器执行的优先级，不会进入ServerHttpResponseDecorator方法
        return 0;
    }
}
