package com.huseyin.basicapp.weather.model.dto.weatherdto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.huseyin.basicapp.weather.model.dto.weatherdto.currentweatherdto.CurrenHourlyWeatherDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto.DailyWeatherDto;
import com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto.HourlyWeatherDto;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CurrenHourlyWeatherDto.class, name = "current"),
        @JsonSubTypes.Type(value = DailyWeatherDto.class, name = "daily"),
        @JsonSubTypes.Type(value = HourlyWeatherDto.class, name = "hourly")
})

@Data
@NoArgsConstructor
public class WeatherDto {

    private String location;

}
