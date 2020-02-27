package com.framework.pie.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class PieZuulApplication {

    public static void main(String[] args){
        SpringApplication.run(PieZuulApplication.class,args);
    }
}
