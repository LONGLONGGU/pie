//package com.framework.pie.admin.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsCofig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**") //允许跨域访问的路径
//                .allowedOriginPatterns("*") //允许访问的源
//                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE") //允许请求方法
//                .maxAge(16800)       //预检间隔时间
//                .allowedHeaders("*")    //允许头部设置
//                .allowCredentials(true); //是否发送token
//    }
//}
