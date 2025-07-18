package com.huseyin.basicapp.weather.controller;




import com.huseyin.basicapp.weather.model.entity.Include;
import com.huseyin.basicapp.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@Tag(name = "Weather Endpoints", description = "Weather related operations")
@RestController
@RequestMapping(value = "/weather",produces = "application/json")
@Validated
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;



    @Operation(
            summary = "Get weather conditions with specified parameters",
            description = "This method provides weather conditions specified by request parameters.\n" +
                    "The request parameters should be passed in query parameters.\n" +
                    "With given location name, weather conditions will be returned. If any date range\n" +
                    "is not passed, 7 day will automatically passed by the system.If only start date is passed\n" +
                    "then only one single day is processed.You cannot give end date without start date.\n" +
                    "Lastly, you can specify include parameter if you want to get hours with corressponding dates.",
            tags = {"get weather"}
    )
    @GetMapping
    public ResponseEntity<?> getWeather(
            @Parameter(description = "Name of the location (e.g. Istanbul)")
            @RequestParam(name = "location") @NotBlank String location,

            @Parameter(description = "Start date parameter format should be yyyy-MM-dd ")
            @RequestParam(name = "startDate",required = false)LocalDate startDate,

            @Parameter(description = "End date parameter format should be yyyy-MM-dd ")
            @RequestParam(name = "endDate",required = false)LocalDate endDate,

            @Parameter(description = "Possible values are true ,false or null.")
            @RequestParam(name = "current",required = false)Boolean current,

            @Parameter(description = "Possible values are days ,hours or null.")
            @RequestParam(name = "include",required = false)Include include
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(weatherService.resolveWeatherRequest(
                        location,
                        startDate,
                        endDate,
                        current,
                        include

                ));
    }



}
