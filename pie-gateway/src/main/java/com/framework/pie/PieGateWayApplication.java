package com.framework.pie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ Author     ：longlong
 * @ Date       ：Created in 13:25 2021/7/21
 */
@SpringBootApplication
@EnableDiscoveryClient
public class   PieGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PieGateWayApplication.class,args);

    }
}
