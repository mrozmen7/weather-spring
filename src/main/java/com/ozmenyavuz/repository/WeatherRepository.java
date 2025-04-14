package com.ozmenyavuz.repository;

import com.ozmenyavuz.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

    // Select*from entity
    Optional<WeatherEntity> findFirstByRequestedCityNameOrderByLocalDateTimeDesc(String city);
}
