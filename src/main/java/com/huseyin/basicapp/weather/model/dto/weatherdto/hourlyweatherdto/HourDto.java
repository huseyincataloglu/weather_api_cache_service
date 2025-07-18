package com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto;

import lombok.Data;


import java.time.LocalTime;

@Data
public class HourDto {


    private LocalTime datetime;

    private Double temp;
    private Double humidity;
    private Double windspeed;
    private String conditions;

}
