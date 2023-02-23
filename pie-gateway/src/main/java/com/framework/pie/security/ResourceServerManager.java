package com.framework.pie.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.framework.pie.constant.AuthConstants;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.redis.client.RedisClient;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 网关自定义鉴权管理器
 * 主要作用是作为资源服务器验证是否有权访问资源的裁决者
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisClient redisClient;

    @SneakyThrows
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 1、对应跨域的预检请求放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        PathMatcher pathMatcher = new AntPathMatcher();
        String method = request.getMethodValue(); //获取请求方法
        String path = request.getURI().getPath(); //获取请求路径,些处的请求路径还带有/pie-admin这样的前缀信息
        //String restfulPath = method + ":" + path; // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14961707.html
        String restfulPath =  path; // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14961707.html

        //2、判断请求头中是否包含Authorization信息，如果token为空，就直接拒绝访问
        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        if(StrUtil.isBlank(token)){
            return Mono.just(new AuthorizationDecision(false));
        }

        //解析JWT令牌令牌，根据用户名称判断如果是超级管理员，则直接放行，不做权限验证处理,直接放过
        token = token.replace(AuthConstants.AUTHORIZATION_PREFIX, Strings.EMPTY);
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (Exception e){
            return Mono.just(new AuthorizationDecision(false));
        }
        String payload = jwsObject.getPayload().toString();
        JSONObject jsonObject = JSONObject.parseObject(URLDecoder.decode(payload,"UTF-8"));
        //直接从jwt令牌中获取出用户，jwt中的用户在admin服务中判断过是否是超级管理员
        if(jsonObject.getBoolean(AuthConstants.SUPER_ADMIN_KEY)){
            return Mono.just(new AuthorizationDecision(true));
        }

        // 3、缓存取 URL权限-角色集合 规则数据
        // urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
        //从缓存中获取URL对应有权限访问的角色的集合
        Map<String, Object> urlPermRolesRules;
        String permRolesStr = redisClient.get(GlobalConstants.URL_PERM_ROLES_KEY);
        urlPermRolesRules = JSONObject.parseObject(permRolesStr,Map.class);

        //如果缓存到的角色信息为空，则直接返回没有权限
        if(urlPermRolesRules == null || urlPermRolesRules.size() <= 0){
            return Mono.just(new AuthorizationDecision(false));
        }

        // 4、根据请求路径判断有访问权限的角色列表
        List<String> authorizedRoles = new ArrayList<>(); // 拥有访问权限的角色
        // 是否需要鉴权，默认“没有设置权限规则”不用鉴权,也就是如果在资源服务器中，
        // 如果没有配置对应的URL请求和对就的角色信息就不进行鉴权处理
        // boolean requireCheck = false;

        for (Map.Entry<String, Object> permRoles : urlPermRolesRules.entrySet()) {
            //获取到URL资源信息
            String perm = permRoles.getKey();
            if (pathMatcher.match(perm, restfulPath)) {
                List<String> roles = Convert.toList(String.class, permRoles.getValue());
                authorizedRoles.addAll(Convert.toList(String.class, roles));
                /*if (requireCheck == false) {
                    requireCheck = true;
                }*/
            }
        }

       /* if (requireCheck == false) {
            return Mono.just(new AuthorizationDecision(true));
        }*/

        // 5、判断JWT中携带的用户角色是否有权限访问
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    //从JWT中解析用户的角色信息，Security角色信息使用时，需要加ROLE_前缀，在认证服务器组装角色中添加了ROLE_前缀，
                    // 并存储到了JWT中，此处需要将前缀去掉之后再做比较
                    String roleCode = authority.substring(AuthConstants.AUTHORITY_PREFIX.length()); // 用户的角色

                    log.info("访问路径：{}", path);
                    log.info("用户角色roleId：{}", roleCode);
                    log.info("资源需要权限authorities：{}", authorizedRoles);
                    if(authorizedRoles == null || authorizedRoles.size() == 0){
                        System.err.println("["+path+"]路径未加入菜单管理或当前登录用户角色未授权访问!");
                    }

                    boolean hasAuthorized = CollectionUtil.isNotEmpty(authorizedRoles) && authorizedRoles.contains(roleCode);
                    return hasAuthorized;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}
