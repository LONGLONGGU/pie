package com.framework.pie.auth.feign.fallback;

import com.framework.pie.auth.feign.SysUserFeignClient;
import com.framework.pie.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@Slf4j
public class SysUserFeignFallbackClient implements SysUserFeignClient {

    @Override
    public HttpResult getSysUserByName(String name) {
        log.error("Feign远程调用pie-admin-service服务[通过用户名获取用户服务方法-getSysUserByName]异常");
        return HttpResult.error("Feign远程调用pie-admin-service服务[通过用户名获取用户服务方法-getSysUserByName]异常,请刷新重试!");
    }

    @Override
    public HttpResult loginOut(String userName, String loginType) {
        log.error("Feign远程调用pie-admin-service服务[通过退出登录服务方法-loginOut]异常");
        return HttpResult.error("Feign远程调用pie-admin-service服务[通过退出登录服务方法-loginOut]异常,请刷新重试!");
    }

    @Override
    public HttpResult getTokenApple(String id) {
        log.error("Feign远程调用pie-admin-service服务[查询TokenApple方法-get/id]异常");
        return HttpResult.error("Feign远程调用pie-admin-service服务[查询TokenApple方法-get/id]异常,请刷新重试!");
    }

    @Override
    public HttpResult tokenApply(Map<String, String> parms) {
        log.error("Feign远程调用pie-admin-service服务[tokenApply]异常");
        return HttpResult.error("Feign远程调用pie-admin-service服务[tokenApply异常,请刷新重试!");
    }
}
