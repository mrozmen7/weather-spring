package com.ozmenyavuz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Request (
        String type,     // API isteği tipi (örnek: City)
        String query,    // Kullanıcının yazdığı şehir adı (örnek: anKara)
        String language, // Dil (örnek: en)
        String unit      // Ölçü birimi (örnek: m -> metre)
) {
}
