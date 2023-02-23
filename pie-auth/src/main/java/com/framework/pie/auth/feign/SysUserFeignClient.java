package com.framework.pie.auth.feign;

import com.framework.pie.auth.feign.fallback.SysUserFeignFallbackClient;
import com.framework.pie.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value = "pie-admin-service", fallback = SysUserFeignFallbackClient.class)
public interface SysUserFeignClient {
    /**
     * 通过用户名称查询用户信息，将用户角色一起查询出来
     * @param name
     * @return
     */
    @GetMapping(value="/user/getSysUserByName")
    HttpResult getSysUserByName(@RequestParam("name") String name);

    /**
     * 退出登录
     * @param userName
     * @param loginType
     * @return
     */
    @GetMapping(value = "/loginOut")
    HttpResult loginOut(@RequestParam("userName") String userName, @RequestParam("loginType") String loginType);

    /**
     * 查询应用申请信息
     * @param id
     * @return
     */
    @GetMapping(value = "sysTokenApple/get/{id}")
    HttpResult getTokenApple(@PathVariable("id") String id);

    @PostMapping(value = "sysTokenApple/tokenApply")
    HttpResult tokenApply(Map<String, String> parms);

}
