package com.callsign.ticketly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class RedisService {

    @Autowired
    ListOperations<String, Object> listOperations;
    @Autowired
    ValueOperations<String, Object> valueOperations;
    @Autowired
    private RedisLockRegistry redisLockRegistry;

    public void queuePush(String key, Object value) {
        listOperations.rightPush(key, value);
    }

    public Object queuePop(String key) {
        return listOperations.leftPop(key);
    }

    public void lock() {
        Lock lock =redisLockRegistry.obtain("test");
        lock.lock();
    }

    public void unlock() {
        Lock lock =redisLockRegistry.obtain("test");
        lock.unlock();
    }

    public void setValue(String key, Object value) {
        valueOperations.set(key, value, 5000, TimeUnit.MILLISECONDS);
    }

    public Object getValue(String key) {
        return valueOperations.get(key);
    }
}
