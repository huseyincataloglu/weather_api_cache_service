package com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Day {

    @JsonProperty("datetime")
    private LocalDate date;
    private Double tempmax;
    private Double tempmin;
    private Double temp;
    private Double humidity;
    @JsonProperty("windspeed")
    private Double windspeed;
    private String conditions;

    private List<Hour> hours;


}
