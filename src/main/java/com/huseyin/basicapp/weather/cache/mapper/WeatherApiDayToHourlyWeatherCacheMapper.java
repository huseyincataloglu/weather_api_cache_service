package com.huseyin.basicapp.weather.cache.mapper;


import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.Day;
import com.huseyin.basicapp.weather.cache.model.hourlyweathercacheentry.HourlyWeatherCacheEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;


@Mapper(componentModel = "spring",uses = HourToHourCacheEntryMapper.class)
public interface WeatherApiDayToHourlyWeatherCacheMapper {


    @Mapping(target = "date",source = "date")
    @Mapping(target = "hours",source = "hours")
    @Mapping(target = "fetchedAt",ignore = true)
    @Mapping(target = "dataSource",ignore = true)
    HourlyWeatherCacheEntry toHourlyCacheEntry(Day day);

    List<HourlyWeatherCacheEntry> toHourlyCacheEntryList(List<Day> day);


}
