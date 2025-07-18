package com.huseyin.basicapp.cache.service;



import com.huseyin.basicapp.weather.cache.model.CacheEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
public class RedisCacheService <T extends CacheEntry> implements IRedisCacheService<T>{


    private final RedisTemplate<String,T> redisTemplate;
    private final Long ttl;


    public RedisCacheService(
            RedisTemplate<String,T> redisTemplate,
            Long ttl
    ){
        this.redisTemplate = redisTemplate;
        this.ttl = ttl;
    }

    @Override
    public boolean isKeyExists(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public T getCacheWeather(String key){
        return redisTemplate.opsForValue().get(key); // return null if the key does not exist
    }


    @Override
    public Boolean setCacheWeather(String key, T cacheEntry){

        return redisTemplate.opsForValue().setIfAbsent(key,cacheEntry,Duration.ofHours(ttl));

    }


    public void deleteCache(String key){
        redisTemplate.delete(key);
    }


}
