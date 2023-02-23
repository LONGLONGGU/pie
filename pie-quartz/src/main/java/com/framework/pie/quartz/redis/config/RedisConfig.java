package com.framework.pie.quartz.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author houjh
 * @desc redis 连接配置类
 * @date 2021/08/04
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfig {
    //Redis服务器IP
    private String url = "127.0.0.1";

    //Redis的端口号
    private Integer port = 6379;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private Integer maxTotal = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private Integer maxIdle = 200;

    //控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private Integer minIdle = 100;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private Integer maxWait = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private boolean testOnBorrow = true;

    //连接密码信息
    private String password = "111111";

    //连接数据库信息
    private Integer database = 0;
}
