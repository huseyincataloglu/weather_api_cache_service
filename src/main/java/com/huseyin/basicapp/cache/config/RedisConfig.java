package com.huseyin.basicapp.cache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huseyin.basicapp.cache.service.IRedisCacheService;
import com.huseyin.basicapp.cache.service.RedisCacheService;
import com.huseyin.basicapp.weather.cache.model.dailyweathercacheentry.DailyWeatherCacheEntry;
import com.huseyin.basicapp.weather.cache.model.hourlyweathercacheentry.HourlyWeatherCacheEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory redisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    @Qualifier("dailyWeatherRedisTemplate")
    public RedisTemplate<String, DailyWeatherCacheEntry> redisTemplate(@Autowired JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, DailyWeatherCacheEntry> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Jackson2JsonRedisSerializer<DailyWeatherCacheEntry> serializer = new Jackson2JsonRedisSerializer<>(objectMapper,DailyWeatherCacheEntry.class);
        template.setValueSerializer(serializer);
        return template;
    }

    @Bean
    @Qualifier("hourlyWeatherTemplate")
    public RedisTemplate<String, HourlyWeatherCacheEntry> redisTemplateCurrent(@Autowired JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, HourlyWeatherCacheEntry> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Jackson2JsonRedisSerializer<HourlyWeatherCacheEntry> serializer = new Jackson2JsonRedisSerializer<>(objectMapper,HourlyWeatherCacheEntry.class);
        template.setValueSerializer(serializer);
        return template;
    }

    @Value("${cache.ttl.hourly}")
    private long hourlyEntryTtl;

    @Value("${cache.ttl.daily}")
    private long dailyEntryTtl;

    @Bean
    public IRedisCacheService<DailyWeatherCacheEntry> weatherCacheService(
            @Qualifier("dailyWeatherRedisTemplate") RedisTemplate<String,DailyWeatherCacheEntry> weatherRedisTemplate
    ){
        return new RedisCacheService<DailyWeatherCacheEntry>(weatherRedisTemplate,dailyEntryTtl);
    }

    @Bean
    public IRedisCacheService<HourlyWeatherCacheEntry> currentWeatherCacheService(
            @Qualifier("hourlyWeatherTemplate") RedisTemplate<String,HourlyWeatherCacheEntry> currentWeatherTemplate
    ){
        return new RedisCacheService<>(currentWeatherTemplate,hourlyEntryTtl);
    }



}
