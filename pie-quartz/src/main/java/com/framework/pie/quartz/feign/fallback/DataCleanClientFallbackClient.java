package com.framework.pie.quartz.feign.fallback;

import com.framework.pie.http.HttpResult;
import com.framework.pie.quartz.feign.DataCleanClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@Slf4j
public class DataCleanClientFallbackClient implements DataCleanClient {

    @Override
    @PostMapping("/dataCleanManager/dataCawlerNewsClean")
    public HttpResult dataCleaning(MultiValueMap<String, String> map, String type, String id) {
        log.error("Feign远程调用pie-data-clean-service服务[数据清洗企业行业匹配-dataCleaning]异常");
        return HttpResult.error("Feign远程调用pie-data-clean-service服务[数据清洗企业行业匹配-dataCleaning]异常,请刷新重试!");
    }

}
