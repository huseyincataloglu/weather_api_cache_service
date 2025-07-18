package com.huseyin.basicapp.weather.cache.model.hourlyweathercacheentry;

import com.huseyin.basicapp.weather.cache.model.CacheEntry;
import com.huseyin.basicapp.weather.cache.model.HourCacheEntry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HourlyWeatherCacheEntry extends CacheEntry {

    private LocalDate date;

    private List<HourCacheEntry> hours;



}
