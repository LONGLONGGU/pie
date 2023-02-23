package com.framework.pie.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 注入配置上下文信息
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping(value = "/hello")
    public Object hello(){

        return "Hello Pie ！";
    }

    @GetMapping(value = "/hi")
    public String sayHi(){
        return "Hello " + applicationContext.getEnvironment().getProperty("user.name");
    }

}
