package com.framework.pie.consumer.feignInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "pie-producer")
public interface PieProducerService {
    @RequestMapping("/hello")
    public String hello();

}
