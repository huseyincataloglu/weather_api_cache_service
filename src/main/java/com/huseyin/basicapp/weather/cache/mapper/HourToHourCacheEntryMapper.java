package com.huseyin.basicapp.weather.cache.mapper;



import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.Hour;
import com.huseyin.basicapp.weather.cache.model.HourCacheEntry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HourToHourCacheEntryMapper {

    HourCacheEntry toHourCacheEntry(Hour hour);

    List<HourCacheEntry> toHourCacheEntryList(List<Hour> hour);


}
