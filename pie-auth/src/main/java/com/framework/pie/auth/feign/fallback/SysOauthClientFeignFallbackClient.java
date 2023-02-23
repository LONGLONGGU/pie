package com.framework.pie.auth.feign.fallback;

import com.framework.pie.auth.feign.SysOauthClientFeignClient;
import com.framework.pie.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SysOauthClientFeignFallbackClient implements SysOauthClientFeignClient {

    @Override
    public HttpResult getByClientId(String clientId) {
        log.error("Feign远程调用pie-admin-service服务[通过clientId查询客户端信息-getByClientId]异常!");
        return HttpResult.error("Feign远程调用pie-admin-service服务[通过clientId查询客户端信息-getByClientId]异常,请刷新重试!");
    }
}
