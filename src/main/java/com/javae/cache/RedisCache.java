package com.javae.cache;

import com.javae.cache.config.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.ibatis.cache.Cache;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;

@Slf4j
public class RedisCache implements Cache {

    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        byte[] k = SerializationUtils.serialize((Serializable) key);
        byte[] v = SerializationUtils.serialize((Serializable) value);
        System.out.println("往Redis中缓存获取数据 key: " + key);
        System.out.println("往Redis中缓存获取数据 value: " + value);
        JedisUtil.getJedis().set(k, v);
    }

    @Override
    public Object getObject(Object key) {
        byte[] k = SerializationUtils.serialize((Serializable) key);
        byte[] v = JedisUtil.getJedis().get(k);
        if (v == null) {
            System.out.println("从Redis缓存中获取数据: 未获取到");
            return null;
        }
        System.out.println("从Redis缓存中获取数据:" + SerializationUtils.deserialize(v));
        return SerializationUtils.deserialize(v);
    }

    @Override
    public Object removeObject(Object key) {
        Object value = this.getObject(key);
        byte[] k = SerializationUtils.serialize((Serializable) key);
        JedisUtil.getJedis().del(k);
        System.out.println("从Redis缓存中删除数据 key: " + key);
        return value;
    }

    @Override
    public void clear() {
        System.out.println("清空 redis");
        JedisUtil.getJedis().flushDB();
    }

    @Override
    public int getSize() {
        System.out.println("获取 redis size");
        return JedisUtil.getJedis().dbSize().intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}