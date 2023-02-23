package com.framework.pie.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages={"com.framework.pie"})
@MapperScan({"com.framework.pie.**.dao"})
public class PieAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(PieAdminApplication.class, args);
	}

}
