package com.ozmenyavuz.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

/**
 * Bu sınıf, API'den gelen "current" alanını karşılamak için kullanılır.
 * İçinde şu anki hava durumu bilgileri yer alır.
 */
public record Current(
        Integer temperature,                // Sıcaklık (örn: 21)
        Integer humidity,                   // Nem oranı (örn: 67)
        Double wind_speed,                  // Rüzgar hızı (örn: 2.06)
        List<String> weather_descriptions,  // Açıklama (örn: ["Partly cloudy"])
        List<String> weather_icons          // İkon URL (örn: ["https://...png"])
) {
}

// 	•	Şu anki hava durumu verilerini temsil eder.
//	•	Örnek: sıcaklık, rüzgar hızı, nem oranı gibi bilgiler.