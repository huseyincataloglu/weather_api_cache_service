package com.huseyin.basicapp.weather.service;

import com.huseyin.basicapp.exception.CurrentWithDateException;
import com.huseyin.basicapp.exception.EndDateWithoutStartDateException;
import com.huseyin.basicapp.exception.StartDateEqualsEndDateException;
import com.huseyin.basicapp.exception.StartDateMustBeBeforeException;
import com.huseyin.basicapp.weather.model.dto.weatherdto.WeatherDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto.DailyWeatherDto;
import com.huseyin.basicapp.weather.model.entity.Include;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
@AllArgsConstructor
public class WeatherService {

    private DailyWeatherService dailyWeatherService;
    private HourlyWeatherService hourlyWeatherService;

    public WeatherDto resolveWeatherRequest(
            String location,
            LocalDate startDate,
            LocalDate endDate,
            Boolean current,
            Include include
    ){

        validateRequest(startDate,endDate,current);
        

        String innerValue = include.getInnerValue();
        if(innerValue.equals("days")){
            return handleDailyRequest(current, startDate, endDate, location);
        }
        else {
            return handleHourlyRequest(current, startDate, endDate, location);

        }


    }

    private WeatherDto handleHourlyRequest(Boolean current, LocalDate startDate, LocalDate endDate, String location) {
        if(current != null && current.equals(Boolean.TRUE)){
            return hourlyWeatherService.getCurrentWeather(location);
        }
        else if (startDate == null && endDate == null){
            return hourlyWeatherService.getWeatherBetweenDates(location, LocalDate.now(), LocalDate.now().plusDays(7));
        } else if (endDate == null) {
            return hourlyWeatherService.getWeatherByDate(location, startDate);
        }
        else {
            return hourlyWeatherService.getWeatherBetweenDates(location, startDate, endDate);
        }
    }

    private DailyWeatherDto handleDailyRequest(Boolean current, LocalDate startDate, LocalDate endDate, String location) {
        if(current != null && current.equals(Boolean.TRUE)){
            return dailyWeatherService.getWeatherByDate(location, LocalDate.now());
        }
        else if (startDate == null && endDate == null){
            return dailyWeatherService.getWeatherBetweenDates(location, LocalDate.now(), LocalDate.now().plusDays(7));

        } else if (endDate == null) {
            return dailyWeatherService.getWeatherByDate(location, startDate);
        }
        else {
            return dailyWeatherService.getWeatherBetweenDates(location, startDate, endDate);
        }
    }


    private void validateRequest(
            LocalDate startDate,
            LocalDate endDate,
            Boolean current
    ){
        if((current != null && current.equals(Boolean.TRUE)) &&(startDate != null || endDate !=null)){
            throw new CurrentWithDateException();
        }
        if(startDate == null && endDate != null){
            throw new EndDateWithoutStartDateException("You cannot pass endDate without startDate");
        }

        if(startDate != null && endDate != null){
            if(startDate.isAfter(endDate)){
                throw new StartDateMustBeBeforeException();
            } else if (startDate.equals(endDate)) {
                throw new StartDateEqualsEndDateException();
            }
        }


    }



}
