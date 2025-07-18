package com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto;



import com.huseyin.basicapp.weather.model.dto.weatherdto.WeatherDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class DailyWeatherDto extends WeatherDto {


    private List<DayDto> days;


    public static DailyWeatherDtoBuilder builder(){
        return new DailyWeatherDtoBuilder();
    }


    public static class DailyWeatherDtoBuilder{
        private String location;
        private List<DayDto> dayDtoList;

        public DailyWeatherDtoBuilder location(String location){
            this.location = location;
            return this;
        }
        public DailyWeatherDtoBuilder dayDtolist(List<DayDto> dayDtoList){
            this.dayDtoList = dayDtoList;
            return this;
        }

        public DailyWeatherDto build(){
            DailyWeatherDto dailyWeatherDto = new DailyWeatherDto();
            dailyWeatherDto.setLocation(this.location);
            dailyWeatherDto.setDays(this.dayDtoList);
            return dailyWeatherDto;
        }



    }

}
