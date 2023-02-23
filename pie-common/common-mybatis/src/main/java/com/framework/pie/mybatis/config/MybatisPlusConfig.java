package com.framework.pie.mybatis.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.framework.pie.mybatis.hander.MyMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * 自动填充数据库创建人、创建时间、更新人、更新时间
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        return globalConfig;
    }

}
