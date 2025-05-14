package com.ozmenyavuz.repository;

import com.ozmenyavuz.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

    // Select*from entity
    // Bu özel method Spring JPA Query Method kurallarına göre yazılmış:
    // Spring, method ismine bakarak kendi SQL sorgusunu oluşturuyor.
    Optional<WeatherEntity> findFirstByRequestedCityNameOrderByLocalDateTimeDesc(String city);
}

// findFirstBy = ilk buldugu kaydi getir
// RequestedCityName = filtreleme yap. (WHERE requested_city_name = ?)
// OrderByLocalDateTimeDesc = localDateTime alanina gore siralama yap:  En yeni tarih Basta (DESC)
// Boylece memory icin daha performans saglar