package com.ozmenyavuz.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Data
public class WeatherEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "requested_city_name", nullable = false)
    private String requestedCityName;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "temperature", nullable = false)
    private Integer temperature;

    @Column(name = "humidity", nullable = false)
    private Integer humidity; // ✅ UI'de gösterilen nem oranı

    @Column(name = "wind_speed", nullable = false)
    private Double windSpeed; // ✅ UI'de gösterilen rüzgar hızı

    @Column(name = "description", nullable = false)
    private String description; // ✅ Açıklama (örn: Partly cloudy)

    @Column(name = "icon_url", nullable = false)
    private String iconUrl; // ✅ Hava durumu ikonu

    @Column(name = "local_date_time", nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "response_local_time", nullable = false)
    private LocalDateTime responseLocalTime;

    // Constructor (gerekli alanlarla)
    public WeatherEntity(String requestedCityName, String cityName, String country, Integer temperature,
                         Integer humidity, Double windSpeed, String description, String iconUrl,
                         LocalDateTime localDateTime, LocalDateTime responseLocalTime) {
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.description = description;
        this.iconUrl = iconUrl;
        this.localDateTime = localDateTime;
        this.responseLocalTime = responseLocalTime;
    }

    public WeatherEntity() {
    }



    // getter / setter'lar (ya da @Getter, @Setter kullanabilirsin)
}