package com.huseyin.basicapp.weather.mapper.hourlyweathermapper;



import com.huseyin.basicapp.weather.cache.model.hourlyweathercacheentry.HourlyWeatherCacheEntry;
import com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto.DayDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = HourCacheEntryToDtoMapper.class)
public interface HourlyWeatherCacheEntryToDayDtoMapper {

    @Mapping(target = "date", source = "date")
    @Mapping(target = "hours", source = "hours")
    @Mapping(target = "tempmax", ignore = true)
    @Mapping(target = "tempmin", ignore = true)
    @Mapping(target = "temp", ignore = true)
    @Mapping(target = "humidity", ignore = true)
    @Mapping(target = "windspeed", ignore = true)
    @Mapping(target = "conditions", ignore = true)
    DayDto toDayDto(HourlyWeatherCacheEntry hourlyWeatherCacheEntry);

    List<DayDto> toDayDtoList(List<HourlyWeatherCacheEntry> hourlyWeatherCacheEntryList);

}
