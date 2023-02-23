package com.framework.pie.auth.feign;

import com.framework.pie.auth.feign.fallback.SysOauthClientFeignFallbackClient;
import com.framework.pie.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pie-admin-service", fallback = SysOauthClientFeignFallbackClient.class)
public interface SysOauthClientFeignClient {
    /**
     * 通过clientId查询客户端信息
     */
    @GetMapping(value = "/sysOauthClient/getByClientId")
    HttpResult getByClientId(@RequestParam("clientId") String clientId);
}
