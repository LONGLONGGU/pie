package com.framework.pie.quartz.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisPoolConfigInfo {

    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPool jedisPool(){
        if(redisConfig == null){
            throw new RuntimeException("请配置redis连接信息");
        }
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(redisConfig.getMaxTotal());
            config.setMaxIdle(redisConfig.getMaxIdle());
            config.setMinIdle(redisConfig.getMinIdle());
            config.setMaxWaitMillis(redisConfig.getMaxWait());
            config.setTestOnBorrow(redisConfig.isTestOnBorrow());
            return new JedisPool(config, redisConfig.getUrl(), redisConfig.getPort(),redisConfig.getMaxWait(),redisConfig.getPassword(),redisConfig.getDatabase());
        } catch (Exception e){
            throw new RuntimeException("redis连接信息出错，请检查redis配置信息");
        }
    }

}
