package com.huseyin.basicapp.cache.service;


import com.huseyin.basicapp.weather.cache.model.CacheEntry;

public interface IRedisCacheService <T extends CacheEntry> {


    public boolean isKeyExists(String key);

    public T getCacheWeather(String key);

    public Boolean setCacheWeather(String key, T cacheEntry);

    public void deleteCache(String key);



}
