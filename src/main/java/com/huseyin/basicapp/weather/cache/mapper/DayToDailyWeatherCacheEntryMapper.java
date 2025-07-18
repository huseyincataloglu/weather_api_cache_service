package com.huseyin.basicapp.weather.cache.mapper;



import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.Day;
import com.huseyin.basicapp.weather.cache.model.dailyweathercacheentry.DailyWeatherCacheEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DayToDailyWeatherCacheEntryMapper {

    @Mapping(target = "fetchedAt",ignore = true)
    @Mapping(target = "dataSource",ignore = true)
    DailyWeatherCacheEntry toDailyWeatherCacheEntry(Day day);

    List<DailyWeatherCacheEntry> toDailyWeatherCacheEntryList(List<Day> dayList);



}
