package com.ozmenyavuz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ozmenyavuz.model.WeatherEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDto(
        String cityName,
        String country,
        Integer temperature,
        String updatedTime,
        Integer humidity,
        Double windSpeed,
        String description,
        String iconUrl
) {
    public static WeatherDto convert(WeatherEntity entity) {
        return new WeatherDto(
                entity.getCityName(),
                entity.getCountry(),
                entity.getTemperature(),
                entity.getResponseLocalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                entity.getHumidity(),
                entity.getWindSpeed(),
                entity.getDescription(),
                entity.getIconUrl()
        );
    }
}

// 	•	Sisteminin kendi içinde kullandığı kendi özel DTO’dur.
//	•	Genellikle dış API’den gelen veriyi kendi sistemine göre özelleştirip sadeleştirmek için kullanırsın.
//	•	Örnek: sadece cityName, temperature gibi verileri taşıyabilir.