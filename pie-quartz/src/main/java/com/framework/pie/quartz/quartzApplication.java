package com.framework.pie.quartz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ：longlong
 * @date ：Created in 2021/8/13 15:11
 * @modified By：
 * @version: ：V1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.framework.pie.quartz"})
public class quartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(quartzApplication.class,args);
    }
}
