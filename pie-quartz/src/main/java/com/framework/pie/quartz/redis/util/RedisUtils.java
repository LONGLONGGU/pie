package com.framework.pie.quartz.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtils{

    @Autowired
    private JedisPool jedisPool;

     /**
      * 获取Jedis实例
      * @return
      */
     public synchronized Jedis getJedis() {
         try {
             if (jedisPool != null) {
                 Jedis resource = jedisPool.getResource();
                 return resource;
             } else {
                 return null;
             }
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }

     /**
      * 释放jedis资源
      * @param jedis
      */
      public void returnResource(final Jedis jedis) {
          if (jedis != null) {
               jedisPool.returnResourceObject(jedis);
          }
      }
}
