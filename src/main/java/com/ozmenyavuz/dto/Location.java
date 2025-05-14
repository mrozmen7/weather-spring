package com.ozmenyavuz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Location(
        String name,
        String country,

        @JsonProperty("localtime")
        String localTime // // Şehirdeki yerel saat
) {
        public Location {
                if (name == null || name.isBlank()) {
                        throw new IllegalArgumentException("City name cannot be null or blank");
                }
                if (country == null || country.isBlank()) {
                        throw new IllegalArgumentException("Country cannot be null or blank");
                }
        }
}

// 	•	Dis APIden gelen Şehrin konum bilgilerini içerir.
//	•	Örnek: şehir adı, ülke, saat dilimi gibi bilgiler.
//	immutable (değiştirilemeyen)
