package com.huseyin.basicapp.weather.cache.model.dailyweathercacheentry;


import com.huseyin.basicapp.weather.cache.model.CacheEntry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class DailyWeatherCacheEntry extends CacheEntry {


    private LocalDate date;
    private Double tempmax;
    private Double tempmin;
    private Double temp;
    private Double humidity;
    private Double windspeed;
    private String conditions;



}
