package com.framework.pie.consumer.feignInterface;

import com.framework.pie.consumer.controller.PieProducerHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "pie-producer",fallback = PieProducerHystrix.class)
public interface PieProducerService {
    @RequestMapping("/hello")
    public String hello();
}
