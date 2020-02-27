package com.framework.pie.consumer.controller;

import com.framework.pie.consumer.feignInterface.PieProducerService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class PieProducerHystrix implements PieProducerService {

    @RequestMapping("/hello")
    public String hello() {
        return "sorry, hello service call failed.";
    }
}
