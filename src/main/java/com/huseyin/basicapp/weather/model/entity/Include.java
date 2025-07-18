package com.huseyin.basicapp.weather.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.huseyin.basicapp.exception.InvalidIncludeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Schema(description = "Choose days or hours",example = "days")
public enum Include {
    DAYS("days"),
    HOURS("hours");

    private final String innerValue;

    Include(String innerValue){this.innerValue = innerValue;}


    @JsonCreator
    public static Include fromInnerValue(
            @JsonProperty("include") String value
    ) {
        if (value == null){
            return DAYS;
        }
        return Arrays.stream(Include.values())
                .filter(include -> include.innerValue.equals(value.toLowerCase()))
                .findFirst()
                .orElseThrow(() ->  new InvalidIncludeException(value,Include.values()));
    }


}
