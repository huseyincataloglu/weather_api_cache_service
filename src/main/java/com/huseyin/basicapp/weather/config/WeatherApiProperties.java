package com.huseyin.basicapp.weather.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weatherapi")
@Data
public class WeatherApiProperties {

    private String url;
    private String key;
    private String lang;
    private String unitGroup;
    private String include;
    private String elements;



}
