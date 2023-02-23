package com.framework.pie.quartz.redis.client;

import com.framework.pie.quartz.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class RedisClient {

    @Autowired
	private RedisUtils redisUtils;

    /**
     * 通过key删除（字节）
     * @param key
     */
    public void del(byte [] key){
        Jedis jedis = redisUtils.getJedis();
        jedis.del(key);
        redisUtils.returnResource(jedis);
    }

    /**
     * 通过key删除
     * @param key
     */
    public void del(String key){
        Jedis jedis = redisUtils.getJedis();
        jedis.del(key);
        redisUtils.returnResource(jedis);
    }



    /**
     * 添加key value 并且设置存活时间(byte)
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte [] key,byte [] value,int liveTime){
        Jedis jedis = redisUtils.getJedis();
        jedis.set(key, value);
        jedis.expire(key, liveTime);
        redisUtils.returnResource(jedis);
    }

    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */

    public void set(String key,String value,int liveTime){
        Jedis jedis = redisUtils.getJedis();
        jedis.set(key, value);
        jedis.expire(key, liveTime);
        redisUtils.returnResource(jedis);
    }

    /**
     * 添加key value
     * @param key
     * @param value
     */
    public void set(String key,String value){
        Jedis jedis = redisUtils.getJedis();
        jedis.set(key, value);
        redisUtils.returnResource(jedis);
    }

    /**添加key value (字节)(序列化)
     * @param key
     * @param value
     */
    public void set(byte [] key,byte [] value){
        Jedis jedis = redisUtils.getJedis();
        jedis.set(key, value);
        redisUtils.returnResource(jedis);
    }

    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis = redisUtils.getJedis();
        String value = jedis.get(key);
        redisUtils.returnResource(jedis);
        return value;
    }

    /**
     * 获取redis value (byte [] )(反序列化)
     * @param key
     * @return
     */

    public byte[] get(byte [] key){
        Jedis jedis = redisUtils.getJedis();
        byte[] value = jedis.get(key);
        redisUtils.returnResource(jedis);
        return value;
    }



    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        Jedis jedis = redisUtils.getJedis();
        Set<String> value = jedis.keys(pattern);
        redisUtils.returnResource(jedis);
        return value;
    }



    /**
     * 检查key是否已经存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        Jedis jedis = redisUtils.getJedis();
        boolean value = jedis.exists(key);
        redisUtils.returnResource(jedis);
        return value;
    }



    /*******************redis list操作************************/
    /**
     * 往list中添加元素
     * @param key
     * @param value
     */
    public long lpush(String key,String value){
        Jedis jedis = redisUtils.getJedis();
        long str = jedis.lpush(key, value);
        redisUtils.returnResource(jedis);
        return str;
    }

    public void rpush(String key,String value){
        Jedis jedis = redisUtils.getJedis();
        jedis.rpush(key, value);
        redisUtils.returnResource(jedis);
    }

    /**
     * 数组长度
     * @param key
     * @return
     */
    public Long llen(String key){
        Jedis jedis = redisUtils.getJedis();
        Long len = jedis.llen(key);
        redisUtils.returnResource(jedis);
        return len;
    }

    /**
     * 获取下标为index的value
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key,Long index){
        Jedis jedis = redisUtils.getJedis();
        String str = jedis.lindex(key, index);
        redisUtils.returnResource(jedis);
        return str;
    }

    public String lpop(String key){
        Jedis jedis = redisUtils.getJedis();
        String str = jedis.lpop(key);
        redisUtils.returnResource(jedis);
        return str;
    }

    public List<String> lrange(String key,long start,long end){
        Jedis jedis = redisUtils.getJedis();
        List<String> str = jedis.lrange(key, start, end);
        redisUtils.returnResource(jedis);
        return str;
    }
    /*********************redis list操作结束**************************/


    /**
     * 清空redis 所有数据
     * @return
     */
    public String flushDB(){
        Jedis jedis = redisUtils.getJedis();
        String str = jedis.flushDB();
        redisUtils.returnResource(jedis);
        return str;
    }

    /**
     * 查看redis里有多少数据
     */
    public long dbSize(){
        Jedis jedis = redisUtils.getJedis();
        long len = jedis.dbSize();
        redisUtils.returnResource(jedis);
        return len;
    }

    /**
     * 检查是否连接成功
     * @return
     */
    public String ping(){
        Jedis jedis = redisUtils.getJedis();
        String str = jedis.ping();
        redisUtils.returnResource(jedis);
        return str;
    }


    /**
     * 获取redis时间
     * @return
     */
    public String time(){
        Jedis jedis = redisUtils.getJedis();
        List<String> list = jedis.time();
        redisUtils.returnResource(jedis);
        Calendar calendar = Calendar.getInstance();
        long second = Integer.valueOf(list.get(0));
        calendar.setTimeInMillis(second * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);
        return dateString;
    }

    public String date(){
        Jedis jedis = redisUtils.getJedis();
        List<String> list = jedis.time();
        redisUtils.returnResource(jedis);
        Calendar calendar = Calendar.getInstance();
        long second = Integer.valueOf(list.get(0));
        calendar.setTimeInMillis(second * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateString = format.format(date);
        return dateString;
    }

    public int hour(){
        Jedis jedis = redisUtils.getJedis();
        List<String> list = jedis.time();
        redisUtils.returnResource(jedis);
        Calendar calendar = Calendar.getInstance();
        long second = Integer.valueOf(list.get(0));
        calendar.setTimeInMillis(second * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String dateString = format.format(date);
        return Integer.valueOf(dateString);
    }

    public static void main(String[] args) {
    	//System.out.println(lpush("dd","ddss"));
        /*JedisPoolConfigInfo j = new JedisPoolConfigInfo();
        j.jedisPool();
        RedisClient rs = new RedisClient();
        rs.set("test","111222");
        System.out.println(rs.exists("111111111"));
        int a = 1;*/
    }

}
