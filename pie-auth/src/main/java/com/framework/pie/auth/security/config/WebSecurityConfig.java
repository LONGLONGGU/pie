package com.framework.pie.auth.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置WebSecurityConfig
 * 安全配置主要是配置请求访问权限、定义认证管理器
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/oauth/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/getPublicKey").permitAll()
                 // @link https://gitee.com/xiaoym/knife4j/issues/I1Q5X6 (接口文档knife4j需要放行的规则)
                .antMatchers("/webjars/**","/doc.html","/swagger-resources/**","/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }


    /**
     *  如果不配置SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
