package com.framework.pie.file.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：longlong
 * @date ：Created in 2021/8/4 14:50
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.framework.pie"})
public class PieFileUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(PieFileUploadApplication.class,args);
    }
}
