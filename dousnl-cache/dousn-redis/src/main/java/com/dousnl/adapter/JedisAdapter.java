package com.dousnl.adapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/14 11:21
 */
@Slf4j
@Service
public class JedisAdapter implements InitializingBean {

    private Jedis jedis=null;

    private JedisPool jedisPool=null;
    @Override
    public void afterPropertiesSet() throws Exception {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMinEvictableIdleTimeMillis(10);
        poolConfig.setSoftMinEvictableIdleTimeMillis(10);
        // 最大建立连接等待时间
        poolConfig.setMaxWaitMillis(300);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, "localhost", 6379, 2000, null, 8, null);
    }

    private Jedis getJedis(){
        try{
            jedis = jedisPool.getResource();
        }catch (Exception e){
            log.error("获取jedis失败！" + e.getMessage());
        }finally {
            if (jedis!=null){
                //注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
                jedis.close();
            }
        }
        return jedis;
    }

    public String getKey(String key){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            log.error("Jedis get发生异常 " + e.getMessage());
            return null;
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
    }

    /**
     * 给Redis中Set集合中某个key值设值
     * @param key
     * @param value
     */
    public void set(String key, String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        }catch (Exception e){
            log.error("Jedis set 异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 向Redis中Set集合添加值:点赞
     * @return
     */
    public long sadd(String key, String value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.sadd(key, value);
        }catch (Exception e){
            log.error("Jedis sadd 异常 ：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 移除：取消点赞
     * @param key
     * @param value
     * @return
     */
    public long srem(String key,String value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.srem(key, value);
        }catch (Exception e){
            log.error("Jedis srem 异常 ：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     *判断key,value是否是集合中值
     * @param key
     * @param value
     * @return
     */
    public boolean sismember(String key,String value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.sismember(key, value);
        }catch (Exception e){
            log.error("Jedis sismember 异常：" + e.getMessage());
            return false;
        }finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    log.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }

    /**
     * 获取集合大小
     * @param key
     * @return
     */
    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            log.error("Jedis scard 异常：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }


}
