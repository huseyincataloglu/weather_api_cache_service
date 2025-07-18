package com.huseyin.basicapp.weather.cache.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class HourCacheEntry {


    private LocalTime datetime;
    private Double temp;

    private Double humidity;

    @JsonProperty("windspeed")
    private Double windspeed;

    private String conditions;



}
