package com.framework.pie.quartz.feign;

import com.framework.pie.http.HttpResult;
import com.framework.pie.quartz.feign.fallback.DataCleanClientFallbackClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pie-data-clean-service", fallback = DataCleanClientFallbackClient.class)
public interface DataCleanClient {
    /**
     * 企业行业匹配
     * @return
     */
    @PostMapping(value="/dataCleanManager/dataCawlerNewsClean")
    HttpResult dataCleaning(@RequestBody MultiValueMap<String, String> map, @RequestParam("type") String type, @RequestParam("id") String id);

}
