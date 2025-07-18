package com.huseyin.basicapp.weather.mapper.dailyweathermapper;



import com.huseyin.basicapp.weather.cache.model.dailyweathercacheentry.DailyWeatherCacheEntry;
import com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto.DayDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyCacheEntryToDayDtoMapper {

    @Mapping(target = "hours",ignore = true)
    DayDto toDayDto(DailyWeatherCacheEntry dailyWeatherCacheEntry);


    List<DayDto> toDayDtoList(List<DailyWeatherCacheEntry> dailyWeatherCacheEntryList);




}
