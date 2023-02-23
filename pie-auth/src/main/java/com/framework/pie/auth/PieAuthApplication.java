package com.framework.pie.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages={"com.framework.pie"})
public class PieAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PieAuthApplication.class,args);
    }

}
