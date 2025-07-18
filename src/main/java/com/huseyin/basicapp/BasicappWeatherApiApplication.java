package com.huseyin.basicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.huseyin.basicapp.weather.api.external")
@EnableAspectJAutoProxy
public class BasicappWeatherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicappWeatherApiApplication.class, args);
	}

}
