package com.huseyin.basicapp.weather.model.dto.weatherdto.dailyweatherdto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.huseyin.basicapp.weather.model.dto.weatherdto.hourlyweatherdto.HourDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayDto {

    private LocalDate date;


    private Double tempmax;
    private Double tempmin;
    private Double temp;
    private Double humidity;
    private Double windspeed;
    private String conditions;

    private List<HourDto> hours;




}
