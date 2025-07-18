package com.huseyin.basicapp.weather.mapper.hourlyweathermapper;



import com.huseyin.basicapp.weather.cache.model.HourCacheEntry;
import com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto.HourDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HourCacheEntryToDtoMapper {

    HourDto toHourDto(HourCacheEntry hourCacheEntry);

    List<HourDto> toHourDto(List<HourCacheEntry> hourCacheEntry);


}
