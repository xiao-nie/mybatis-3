package com.javae.cache.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisUtil {

    private static JedisPool jedisPool = null;
    private static Properties properties = new Properties();


    static {
        InputStream inputStream = null;
        try {
            inputStream = JedisUtil.class.getResourceAsStream("/redis.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(properties.getProperty("maxWaitMillis")));

        jedisPool = new JedisPool(jedisPoolConfig, properties.getProperty("ip"), Integer.parseInt(properties.getProperty("port")));
    }

    /**
     * 获取jedis客户端
     *
     * @return jedis
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void close(Jedis jedis) {
        jedis.close();
    }

}