package com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;

@Data
public class Hour {


    private LocalTime datetime;

    private Double temp;

    private Double humidity;

    @JsonProperty("windspeed")
    private Double windspeed;

    private String conditions;

}
