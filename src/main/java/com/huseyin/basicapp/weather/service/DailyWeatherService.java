package com.huseyin.basicapp.weather.service;


import com.huseyin.basicapp.cache.key.KeyGenerator;
import com.huseyin.basicapp.cache.service.IRedisCacheService;

import com.huseyin.basicapp.weather.api.WeatherApiService;
import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.Day;
import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.WeatherApiResponse;
import com.huseyin.basicapp.weather.cache.mapper.DayToDailyWeatherCacheEntryMapper;
import com.huseyin.basicapp.util.LocalDateListCreator;
import com.huseyin.basicapp.weather.cache.model.dailyweathercacheentry.DailyWeatherCacheEntry;
import com.huseyin.basicapp.weather.mapper.dailyweathermapper.DailyCacheEntryToDayDtoMapper;
import com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto.DailyWeatherDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto.DayDto;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DailyWeatherService {
    private final IRedisCacheService<DailyWeatherCacheEntry> dailyIRedisCacheService;
    private final DailyCacheEntryToDayDtoMapper dailyCacheEntryToDayDtoMapper;
    private final DayToDailyWeatherCacheEntryMapper dayToDailyWeatherCacheEntryMapper;
    private final WeatherApiService weatherApiService;


    public DailyWeatherDto getWeatherBetweenDates(
            String location,
            LocalDate startDate,
            LocalDate endDate
    ){
        List<LocalDate> localDateList = LocalDateListCreator.getDateList(startDate,endDate);
        CacheResult cacheResult = getCacheEntryByDates(location,localDateList);
        List<DailyWeatherCacheEntry> existingEntries = cacheResult.exstinEntries;

        if(!cacheResult.missingDates.isEmpty()){
            existingEntries.addAll(fetchAndCacheMissingDates(location,cacheResult.missingDates));
        }
        return DailyWeatherDto.builder()
                .location(location)
                .dayDtolist(dailyCacheEntryToDayDtoMapper.toDayDtoList(existingEntries))
                .build();


    }

    public DailyWeatherDto getWeatherByDate(
            String location,
            LocalDate date
    ){
        DailyWeatherCacheEntry dailyWeatherCacheEntry = getCacheEntry(location,date);
        if(dailyWeatherCacheEntry != null){
            DayDto dayDto =  dailyCacheEntryToDayDtoMapper.toDayDto(dailyWeatherCacheEntry);
            return DailyWeatherDto.builder()
                    .location(location)
                    .dayDtolist(List.of(dayDto))
                    .build();
        }
        else{
            DailyWeatherCacheEntry missingEntry = fetchAndCacheMissingDate(location, date);
            return DailyWeatherDto.builder()
                    .location(location)
                    .dayDtolist(List.of(dailyCacheEntryToDayDtoMapper.toDayDto(missingEntry)))
                    .build();


        }

    }



    private CacheResult getCacheEntryByDates(
            String location,
            List<LocalDate> dates
    ){
        List<DailyWeatherCacheEntry> existingEntriesList = new ArrayList<>();
        List<LocalDate> missingLocalDateList = new ArrayList<>();
        for(LocalDate localDate :dates){
            DailyWeatherCacheEntry dailyWeatherCacheEntry = getCacheEntry(location,localDate);
            if(dailyWeatherCacheEntry != null){
                existingEntriesList.add(dailyWeatherCacheEntry);
            }
            else{
                missingLocalDateList.add(localDate);
            }
        }
        return new CacheResult(existingEntriesList,missingLocalDateList);
    }





    private DailyWeatherCacheEntry getCacheEntry(
            String location,
            LocalDate date
    ){
        String redisKey = KeyGenerator.generateKey(
                location,
                date,
                "days"
        );

        if(dailyIRedisCacheService.getCacheWeather(redisKey) != null){
            log.info("Daily weather data for :%s:%s retrieved from cache".formatted(location,date));
        }
        return dailyIRedisCacheService.getCacheWeather(redisKey);

    }

    private Boolean setCacheEntry(
            String location,
            LocalDate date,
            DailyWeatherCacheEntry dailyWeatherCacheEntry
    ){
        String redisKey = KeyGenerator.generateKey(
                location,
                date,
                "days"
        );
        return dailyIRedisCacheService.setCacheWeather(redisKey,dailyWeatherCacheEntry);


    }

    private List<DailyWeatherCacheEntry> fetchAndCacheMissingDates(
            String location,
            List<LocalDate> dates
    ){
        WeatherApiResponse weatherApiResponse =weatherApiService.getWeatherByDatesFromApi(location,dates,"hours");
        List<DailyWeatherCacheEntry> missingCacheEntryList = new ArrayList<>();
        weatherApiResponse.getDays().forEach(
                day -> {
                    DailyWeatherCacheEntry dailyWeatherCacheEntry = dayToDailyWeatherCacheEntryMapper.toDailyWeatherCacheEntry(day);
                    Boolean result = setCacheEntry(location, dailyWeatherCacheEntry.getDate(), dailyWeatherCacheEntry);
                    if(result.equals(Boolean.TRUE)){
                        log.info("Daily weather data for :%s:%s retrieved from API".formatted(location,dailyWeatherCacheEntry.getDate()));
                    }
                    missingCacheEntryList.add(dailyWeatherCacheEntry);
                });
       return missingCacheEntryList;

    }




    private DailyWeatherCacheEntry fetchAndCacheMissingDate(String location, LocalDate date) {
        WeatherApiResponse weatherApiResponse = weatherApiService.getWeatherByDateFromApi(
                location,
                date,
                "hours"
        );
        List<Day> daysList = weatherApiResponse.getDays();
        DailyWeatherCacheEntry missingCacheEntry = dayToDailyWeatherCacheEntryMapper.toDailyWeatherCacheEntry(daysList.getFirst());
        Boolean result = setCacheEntry(location, missingCacheEntry.getDate(), missingCacheEntry);
        if(result.equals(Boolean.TRUE)){
            log.info("Daily weather data for :%s:%s retrieved from API".formatted(location,date));
        }
        return missingCacheEntry;
    }

    private record CacheResult(
            List<DailyWeatherCacheEntry> exstinEntries,
            List<LocalDate> missingDates
    ){}









}
