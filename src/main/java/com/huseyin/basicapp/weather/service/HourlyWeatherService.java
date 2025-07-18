package com.huseyin.basicapp.weather.service;


import com.huseyin.basicapp.cache.key.KeyGenerator;
import com.huseyin.basicapp.cache.service.IRedisCacheService;
import com.huseyin.basicapp.util.LocalDateListCreator;
import com.huseyin.basicapp.weather.api.WeatherApiService;
import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.WeatherApiResponse;
import com.huseyin.basicapp.weather.cache.mapper.WeatherApiDayToHourlyWeatherCacheMapper;
import com.huseyin.basicapp.weather.cache.model.HourCacheEntry;
import com.huseyin.basicapp.weather.cache.model.hourlyweathercacheentry.HourlyWeatherCacheEntry;
import com.huseyin.basicapp.weather.mapper.hourlyweathermapper.HourCacheEntryToDtoMapper;
import com.huseyin.basicapp.weather.mapper.hourlyweathermapper.HourlyWeatherCacheEntryToDayDtoMapper;
import com.huseyin.basicapp.weather.model.dto.weatherdto.currentweatherdto.CurrenHourlyWeatherDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto.HourDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto.HourlyWeatherDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class HourlyWeatherService {

    private IRedisCacheService<HourlyWeatherCacheEntry> hourlyCacheService;
    private WeatherApiDayToHourlyWeatherCacheMapper hourlyWeatherCacheEntryMapper;
    private HourlyWeatherCacheEntryToDayDtoMapper hourlyWeatherCacheEntryToDayDtoMapper;
    private HourCacheEntryToDtoMapper hourCacheEntryToDtoMapper;
    private WeatherApiService weatherApiService;

    public HourlyWeatherDto getWeatherBetweenDates(
            String location,
            LocalDate startDate,
            LocalDate endDate
    ) {
        List<LocalDate> localDateList = LocalDateListCreator.getDateList(startDate, endDate);
        CacheResult cacheResult = getCacheEntryBetweenDates(location,localDateList);

        List<HourlyWeatherCacheEntry> fullEntriesList = cacheResult.existingCacheEntries;

        if(!cacheResult.missingDates.isEmpty()){
            List<HourlyWeatherCacheEntry> missingEntries = fetchWeatherAndSetCacheByDates(
                    location,cacheResult.missingDates
            );
            fullEntriesList.addAll(missingEntries);
        }
        return HourlyWeatherDto.builder()
                .location(location)
                .days(hourlyWeatherCacheEntryToDayDtoMapper.toDayDtoList(fullEntriesList))
                .build();


    }

    public HourlyWeatherDto getWeatherByDate(
            String location,
            LocalDate date
    ) {
        HourlyWeatherCacheEntry existingEntry = getCacheEntry(location,date);

        if(existingEntry != null){
            return HourlyWeatherDto.builder()
                    .location(location)
                    .days(List.of(hourlyWeatherCacheEntryToDayDtoMapper.toDayDto(existingEntry)))
                    .build();
        }
        else {
            HourlyWeatherCacheEntry missingEntry = fetchWeatherAndSetCacheByDate(location,date);
            return HourlyWeatherDto.builder()
                    .location(location)
                    .days(List.of(hourlyWeatherCacheEntryToDayDtoMapper.toDayDto(missingEntry)))
                    .build();

        }

    }

    public CurrenHourlyWeatherDto getCurrentWeather(
            String location
    ){
        HourlyWeatherCacheEntry existingEntry = getCacheEntry(location,LocalDate.now());
        if(existingEntry != null){
            List<HourCacheEntry> hourCacheEntries = existingEntry.getHours();
            List<HourCacheEntry> filteredList = hourCacheEntries.stream().filter(
                    hourCacheEntry -> hourCacheEntry.getDatetime().withNano(0).equals(LocalTime.now().withMinute(0).withSecond(0).withNano(0))
            ).toList();

            HourDto hourDto = hourCacheEntryToDtoMapper.toHourDto(filteredList.getFirst());
            return CurrenHourlyWeatherDto.builder()
                    .location(location)
                    .date(LocalDate.now())
                    .hour(hourDto)
                    .build();

        }
        else {
            HourlyWeatherCacheEntry missingEntry = fetchWeatherAndSetCacheByDate(location,LocalDate.now());
            List<HourCacheEntry> hourCacheEntries = missingEntry.getHours();
            List<HourCacheEntry> filteredList = hourCacheEntries.stream().filter(
                    hourCacheEntry -> hourCacheEntry.getDatetime().withNano(0).equals(LocalTime.now().withMinute(0).withSecond(0).withNano(0))
            ).toList();
            HourDto hourDto = hourCacheEntryToDtoMapper.toHourDto(filteredList.getFirst());
            return CurrenHourlyWeatherDto.builder()
                    .location(location)
                    .date(LocalDate.now())
                    .hour(hourDto)
                    .build();
        }


    }




    private CacheResult getCacheEntryBetweenDates(
            String location,
            List<LocalDate> dateList
    ){
        List<HourlyWeatherCacheEntry> existingEntries = new ArrayList<>();
        List<LocalDate> missingDates = new ArrayList<>();

        for(LocalDate localDate : dateList){
            HourlyWeatherCacheEntry existingEntry =getCacheEntry(location,localDate);
            if (existingEntry!=null){
                existingEntries.add(existingEntry);
            }
            else{
                missingDates.add(localDate);
            }
        }
        return new CacheResult(existingEntries,missingDates);

    }


    private HourlyWeatherCacheEntry getCacheEntry(
            String location,
            LocalDate date
    ) {
        String redisKey = KeyGenerator.generateKey(
                location,
                date,
                "hours"
        );
        if(hourlyCacheService.getCacheWeather(redisKey) != null){
            log.info("Hourly weather data for :%s:%s retrieved from cache".formatted(location,date));
        }
        return hourlyCacheService.getCacheWeather(redisKey);
    }

    private Boolean setCacheEntry(
            String location,
            LocalDate date,
            HourlyWeatherCacheEntry HourlyWeatherCacheEntry
    ) {
        String redisKey = KeyGenerator.generateKey(
                location,
                date,
                "hours"
        );
        return hourlyCacheService.setCacheWeather(redisKey, HourlyWeatherCacheEntry);

    }

    private List<HourlyWeatherCacheEntry> fetchWeatherAndSetCacheByDates(
            String location,
            List<LocalDate> dates
    ){
        WeatherApiResponse weatherApiResponse = weatherApiService.getWeatherByDatesFromApi(
                location,dates,"hours"
        );
        List<HourlyWeatherCacheEntry> missingEntries = hourlyWeatherCacheEntryMapper.toHourlyCacheEntryList(weatherApiResponse.getDays());
        missingEntries.forEach(missingEntry -> {
            Boolean result = setCacheEntry(location,missingEntry.getDate(),missingEntry).equals(Boolean.TRUE);
            if(result.equals(Boolean.TRUE)){
                log.info("Hourly weather data for :%s:%s retrieved from API".formatted(location,missingEntry.getDate()));
            }

        });
        return missingEntries;

    }


    private HourlyWeatherCacheEntry fetchWeatherAndSetCacheByDate(
            String location,
            LocalDate date
    ){
        WeatherApiResponse weatherApiResponse = weatherApiService.getWeatherByDateFromApi(
                location,date,"hours"
        );
        HourlyWeatherCacheEntry missingEntry = hourlyWeatherCacheEntryMapper.toHourlyCacheEntry(weatherApiResponse.getDays().getFirst());
        Boolean result = setCacheEntry(location,missingEntry.getDate(),missingEntry).equals(Boolean.TRUE);
        if(result.equals(Boolean.TRUE)){
            log.info("Hourly weather data for :%s:%s retrieved from API".formatted(location,date));
        }
        return missingEntry;

    }


    private record CacheResult(
            List<HourlyWeatherCacheEntry> existingCacheEntries,
            List<LocalDate> missingDates
    ){

    }


}
