package com.framework.pie.quartz.feign;

import com.framework.pie.http.HttpResult;
import com.framework.pie.quartz.feign.fallback.BusDistrictFeignFallbackClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "pie-land-mark-service", fallback = BusDistrictFeignFallbackClient.class)
public interface BusDistrictFeignClient {

    /**
     * 查询行政区划树信息
     * @return
     */
    @GetMapping(value="/busDistrict/findTree")
    HttpResult findTree();

    /**
     * 异常加载行政区划树信息
     * @param parentId
     * @return
     */
    @GetMapping(value="/busDistrict/findTree/{parentId}")
    HttpResult asyncFindTree(@PathVariable("parentId") String parentId);
}
