package com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto;


import com.huseyin.basicapp.weather.model.dto.weatherdto.WeatherDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto.DayDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HourlyWeatherDto extends WeatherDto {

    private List<DayDto> days;



    public static HourlyWeatherDtoBuilder builder(){
        return new HourlyWeatherDtoBuilder();
    }


    public static class HourlyWeatherDtoBuilder{

        private String location;
        private List<DayDto> days;

        public HourlyWeatherDtoBuilder location(String location){
            this.location = location;
            return this;
        }
        public HourlyWeatherDtoBuilder days(List<DayDto> days){
            this.days = days;
            return this;
        }

        public  HourlyWeatherDto build(){
            HourlyWeatherDto hourlyWeatherDto = new HourlyWeatherDto();
            hourlyWeatherDto.setLocation(this.location);
            hourlyWeatherDto.setDays(this.days);
            return  hourlyWeatherDto;
        }




    }



}
