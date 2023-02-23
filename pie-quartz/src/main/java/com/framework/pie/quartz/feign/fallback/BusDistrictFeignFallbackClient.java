package com.framework.pie.quartz.feign.fallback;

import com.framework.pie.http.HttpResult;
import com.framework.pie.quartz.feign.BusDistrictFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class BusDistrictFeignFallbackClient implements BusDistrictFeignClient {

    @Override
    public HttpResult findTree() {
        log.error("Feign远程调用pie-land-mark-service服务[通过用户名获取用户服务方法-findTree]异常");
        return HttpResult.error("Feign远程调用pie-land-mark-service服务[通过用户名获取用户服务方法-findTree]异常,请刷新重试!");
    }

    @Override
    public HttpResult asyncFindTree(String parentId) {
        log.error("Feign远程调用pie-land-mark-service服务[通过用户名获取用户服务方法-asyncFindTree]异常");
        return HttpResult.error("Feign远程调用pie-land-mark-service服务[通过用户名获取用户服务方法-asyncFindTree]异常,请刷新重试!");
    }
}
