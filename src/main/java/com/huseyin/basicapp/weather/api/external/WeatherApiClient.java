package com.huseyin.basicapp.weather.api.external;




import com.huseyin.basicapp.weather.api.model.dto.weatherapiresponse.WeatherApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherapi",url = "${weatherapi.url}")
public interface WeatherApiClient {

    @GetMapping("{location}")
    WeatherApiResponse getWeather(
            @PathVariable(name = "location") String location,
            @RequestParam(name = "key") String key,
            @RequestParam(name = "lang") String lang,
            @RequestParam(name="unitGroup") String unitGroup,
            @RequestParam(name = "include") String include,
            @RequestParam(name = "elements") String elements
    );

    @GetMapping("{location}/{date1}")
     WeatherApiResponse getWeather(
            @PathVariable(name = "location") String location,
            @PathVariable(name = "date1") String date1,
            @RequestParam(name = "key") String key,
            @RequestParam(name = "lang") String lang,
            @RequestParam(name="unitGroup") String unitGroup,
            @RequestParam(name = "include") String include,
            @RequestParam(name = "elements") String elements
    );

    @GetMapping("{location}/{date1}/{date2}")
     WeatherApiResponse getWeather(
            @PathVariable(name = "location") String location,
            @PathVariable(name = "date1") String date1,
            @PathVariable(name = "date2") String date2,
            @RequestParam(name = "key") String key,
            @RequestParam(name = "lang") String lang,
            @RequestParam(name="unitGroup") String unitGroup,
            @RequestParam(name = "include") String include,
            @RequestParam(name = "elements") String elements
    );








}
