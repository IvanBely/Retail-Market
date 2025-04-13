package com.example.analysis_service.service.impl;

import com.example.analysis_service.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheServiceImpl implements CacheService {
    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Async
    public <T> void cacheData(String key, List<T> data) {
        try {
            logger.info("Caching data under key: {}", key);
            redisTemplate.opsForValue().set(key, data);
        } catch (Exception e) {
            logger.error("Error caching data under key: {}", key, e);
            throw new RuntimeException("Error caching data", e);
        }
    }
}
