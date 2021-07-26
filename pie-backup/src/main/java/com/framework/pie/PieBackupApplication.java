package com.framework.pie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.framework.pie"})
public class PieBackupApplication {

    public static void main(String[] args){
        SpringApplication.run(PieBackupApplication.class,args);
    }
}
