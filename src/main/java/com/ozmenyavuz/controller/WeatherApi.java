package com.ozmenyavuz.controller;

import com.ozmenyavuz.dto.WeatherDto;
import com.ozmenyavuz.service.WeatherService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5174")
@RestController // Bu sınıftaki tüm metodlar JSON response döner
@RequestMapping("/v1/api/weather") // Tüm endpoint'ler bu URL ile başlar
@Validated // @PathVariable gibi giriş verilerinin validasyonu için
public class WeatherApi {

    private final WeatherService weatherService;

    // Constructor Injection (tavsiye edilen yöntem)
    public WeatherApi(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}") // Örnek: /v1/api/weather/Ankara
    @RateLimiter(name = "basic") // Resilience4j RateLimiter kullanımı
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city") @NotBlank String city) {
        WeatherDto dto = weatherService.getWeatherByCityName(city);
        return ResponseEntity.ok(dto);
    }
}