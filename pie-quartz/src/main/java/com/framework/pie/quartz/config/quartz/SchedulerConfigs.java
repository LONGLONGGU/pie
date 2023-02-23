package com.framework.pie.quartz.config.quartz;


import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

//@Configuration
//@Configuration的作用：标注在类上，配置spring容器(应用上下文)。相当于把该类作为spring的xml配置文件中的<beans>。@Configuration注解的类中，使用@Bean注解标注的方法，返回的类型都会直接注册为bean。
public class SchedulerConfigs {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factoryBean=new SchedulerFactoryBean();
        factoryBean.setSchedulerName("cluster_scheduler");
        factoryBean.setDataSource(dataSource);
        factoryBean.setApplicationContextSchedulerContextKey("application");
        factoryBean.setQuartzProperties(quartzProperties());
        //线程池配置
        factoryBean.setTaskExecutor(schedulerThreadPool());
        factoryBean.setStartupDelay(10);//延迟执行
        return factoryBean;
    }
    @Bean
    public Executor schedulerThreadPool(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());//核心线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());//最大线程数
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
        return executor;
    }
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean=new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/application-quartz.yml"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
