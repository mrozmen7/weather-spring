package com.ozmenyavuz.controller;

import com.ozmenyavuz.dto.WeatherDto;
import com.ozmenyavuz.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/weather")
public class WeatherApi {


    private final WeatherService wearherService;

    public WeatherApi(WeatherService wearherService) {
        this.wearherService = wearherService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city")  @NotBlank String city){
        return ResponseEntity.ok(wearherService.getWeatherByCityName(city));
    }
}
