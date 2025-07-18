package com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherApiResponse {

    private String resolvedAddress;

    @JsonProperty("days")
    private List<Day> days;


    private CurrentConditions currentConditions;


}
