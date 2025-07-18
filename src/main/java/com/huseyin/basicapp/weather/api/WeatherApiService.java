package com.huseyin.basicapp.weather.api;



import com.huseyin.basicapp.weather.api.external.WeatherApiClient;
import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.WeatherApiResponse;
import com.huseyin.basicapp.weather.config.WeatherApiProperties;



import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Slf4j
@Service
@AllArgsConstructor
public class WeatherApiService {



    private final WeatherApiProperties weatherApiProperties;
    private final WeatherApiClient weatherApiClient;

    public WeatherApiResponse getWeatherByDatesFromApi(
            String location,
            List<LocalDate> dateList,
            String include
    ){
        List<LocalDate> sortedDates = dateList.stream().sorted().toList();
        if(sortedDates.size() == 1){
            return getWeatherByDateFromApi(location,sortedDates.getFirst(),include);
        }
        return weatherApiClient.getWeather(
                location,
                sortedDates.getFirst().toString(),
                sortedDates.getLast().toString(),
                weatherApiProperties.getKey(),
                weatherApiProperties.getLang(),
                weatherApiProperties.getUnitGroup(),
                include,
                weatherApiProperties.getElements()

        );


    }


    public WeatherApiResponse getWeatherByDateFromApi(
            String location,
            LocalDate date,
            String include
    ){

        return weatherApiClient.getWeather(
                location,
                date.toString(),
                weatherApiProperties.getKey(),
                weatherApiProperties.getLang(),
                weatherApiProperties.getUnitGroup(),
                include,
                weatherApiProperties.getElements()
        );


    }



}


