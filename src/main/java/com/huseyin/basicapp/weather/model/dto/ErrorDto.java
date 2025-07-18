package com.huseyin.basicapp.weather.model.dto;



public record ErrorDto(
        String timestamp,
        int status,
        String error,
        String path,
        String message,
        String errorCode
){


}
