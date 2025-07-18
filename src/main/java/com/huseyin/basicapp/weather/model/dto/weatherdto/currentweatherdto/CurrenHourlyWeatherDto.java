package com.huseyin.basicapp.weather.model.dto.weatherdto.currentweatherdto;


import com.huseyin.basicapp.weather.model.dto.weatherdto.WeatherDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto.HourDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CurrenHourlyWeatherDto extends WeatherDto {


    private LocalDate date;

    private HourDto hour;

    public static CurrenHourlyWeatherDtoBuilder builder(){
        return new CurrenHourlyWeatherDtoBuilder();
    }


    public static class CurrenHourlyWeatherDtoBuilder{
        private String location;
        private LocalDate date;
        private HourDto hour;

        public CurrenHourlyWeatherDtoBuilder location(String location){
            this.location = location;
            return this;
        }

        public CurrenHourlyWeatherDtoBuilder date(LocalDate  date){
            this.date = date;
            return this;
        }


        public CurrenHourlyWeatherDtoBuilder hour(HourDto hour){
            this.hour = hour;
            return this;
        }

        public CurrenHourlyWeatherDto build(){
            CurrenHourlyWeatherDto currenHourlyWeatherDto = new CurrenHourlyWeatherDto();
            currenHourlyWeatherDto.setLocation(this.location);
            currenHourlyWeatherDto.setDate(this.date);
            currenHourlyWeatherDto.setHour(this.hour);
            return currenHourlyWeatherDto;
        }



    }



}
