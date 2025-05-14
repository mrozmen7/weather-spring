package com.ozmenyavuz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozmenyavuz.config.WeatherStackConfig;
import com.ozmenyavuz.constans.Constants;
import com.ozmenyavuz.dto.WeatherDto;
import com.ozmenyavuz.dto.WeatherResponse;
import com.ozmenyavuz.model.WeatherEntity;
import com.ozmenyavuz.repository.WeatherRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository; // Spring
    private final RestTemplate restTemplate; // Spring Dis Api icin HTTP iletimi kurmak icin//
    private final ObjectMapper objectMapper = new ObjectMapper(); // Spring

    public WeatherService(WeatherRepository weatherRepository,
                          RestTemplate restTemplate
                          ) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;

    }


    // 1 ANA METOT

    // Memorimiz sismemsi daha pratik ve esnek calismasi icin ek bir sorgu metotu

    public WeatherDto getWeatherByCityName(String city) {
        // 1. Veritabanında bu şehir için en son sorgulanmış kaydı bul: SQL komutu
        Optional<WeatherEntity> weatherEntityOptional =
                weatherRepository.findFirstByRequestedCityNameOrderByLocalDateTimeDesc(city);

        // 2. Eğer kayıt varsa kontrol et: 30 dakikadan eski mi?
        return weatherEntityOptional.map(entity -> {
            boolean isOld = entity.getLocalDateTime()
                    .isBefore(LocalDateTime.now().minusMinutes(30));

            if (isOld) {
                // Veri eskiyse: API’den yeni veri çek ve DTO’ya çevir
                WeatherEntity freshEntity = getWeatherFromWeatherStack(city);
                return WeatherDto.convert(freshEntity);
            }

            // Veri güncelse: direkt DTO’ya çevir ve döndür
            return WeatherDto.convert(entity);

        }).orElseGet(() -> {
            // 3. Hiç kayıt yoksa: API’den veri çek, kaydet ve döndür
            WeatherEntity freshEntity = getWeatherFromWeatherStack(city);
            return WeatherDto.convert(freshEntity);
        });
    }


    // 2 GET ATAR SERVICE = WeatherStack API CEKME
    // 🌍 WeatherStack API’den güncel hava durumu verisi alır ve işleyip veritabanına kaydetmek üzere hazırlar.
    // 4  Tam URL Üretici

    private String getWeatherStackUrl(String city) {
        return WeatherStackConfig.API_URL
                + Constants.ACCESS_KEY_PARAM + WeatherStackConfig.API_KEY
                + Constants.ACCESS_QUERY_PARAM + city;
    }

    @RateLimiter(name = "basic", fallbackMethod = "fallbackWeather")
    private WeatherEntity getWeatherFromWeatherStack(String city) {
        String url = WeatherStackConfig.API_URL
                + Constants.ACCESS_KEY_PARAM + WeatherStackConfig.API_KEY
                + Constants.ACCESS_QUERY_PARAM + city;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        //getForEntity → dış API’ye GET isteği atar

        String json = responseEntity.getBody();
        System.out.println("WeatherStack JSON Response: " + json);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(json, WeatherResponse.class); //  Jackson kütüphanesinin JSON’ı Java nesnesine çevirmek

            if (weatherResponse == null || weatherResponse.location() == null || weatherResponse.current() == null) {
                throw new RuntimeException("Hatalı veri geldi. API key doğru mu? Şehir geçerli mi?");
            }

            // JSON’dan elde edilen Java nesnesini veritabanına yazmak için saveWeatherEntity(...) metoduna gönderiyoruz.
            return saveWeatherEntity(city, weatherResponse); //API’den gelen doğru veri şimdi veritabanına yazılacak
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON parse hatası: " + e.getMessage(), e);
        }

    }


    // 3 DB’ye Kaydetme Metodu
    // API’den gelen veriyi WeatherEntity nesnesine çevirip veritabanına kaydeder.
    private WeatherEntity saveWeatherEntity(String requestedCity, WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // API'den gelen verileri parçalayıp entity'ye mapliyoruz:
        String cityName = weatherResponse.location().name();
        String country = weatherResponse.location().country();
        Integer temperature = weatherResponse.current().temperature();
        Integer humidity = weatherResponse.current().humidity();
        Double windSpeed = weatherResponse.current().wind_speed();
        String description = weatherResponse.current().weather_descriptions().isEmpty()
                ? null
                : weatherResponse.current().weather_descriptions().get(0);
        String iconUrl = weatherResponse.current().weather_icons().isEmpty()
                ? null
                : weatherResponse.current().weather_icons().get(0);
        LocalDateTime responseLocalTime = LocalDateTime.now();
        LocalDateTime localDateTime = LocalDateTime.parse(weatherResponse.location().localTime(), dateTimeFormatter);

        WeatherEntity weatherEntity = new WeatherEntity(
                requestedCity,     // Kullanıcının yazdığı şehir (örnek: "ankara")
                cityName,          // API'den gelen düzgün şehir adı
                country,           // Ülke adı
                temperature,       // Sıcaklık
                humidity,          // Nem
                windSpeed,         // Rüzgar hızı
                description,       // Hava açıklaması
                iconUrl,           // İkon resmi
                localDateTime,     // Şehirdeki saat
                responseLocalTime  // Bizim cevabı oluşturduğumuz an
        );

        return weatherRepository.save(weatherEntity);
    }
}

//package com.ozmenyavuz.service;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ozmenyavuz.dto.WeatherDto;
//import com.ozmenyavuz.dto.WeatherResponse;
//import com.ozmenyavuz.model.WeatherEntity;
//import com.ozmenyavuz.repository.WeatherRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.format.DateTimeFormatter;
//import java.util.Optional;
//
//@Service
//public class WearherService {
//
//    private static final String API_URL = "http://api.weatherstack.com/current?access_key=216142cadf02819e6f4b74ff41bd86be&query=Basel";
//
//    private final WeatherRepository weatherRepository;
//    private final RestTemplate restTemplate; //Senin uygulamanla dış dünyadaki başka bir servisi konuşturmak.
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public WearherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
//        this.weatherRepository = weatherRepository;
//        this.restTemplate = restTemplate;
//    }
//
//
//    public WeatherDto getWeatherByCityName(String city) {
//        Optional<WeatherEntity> weatherEntityOptional = weatherRepository.findFirstByRequestedCityNameOrderByLocalDateTimeDesc(city);
//        if (!weatherEntityOptional.isPresent()) {
//            return WeatherDto.convert(getWeatherFromWeatherStackt(city));
//        }
//        return WeatherDto.convert(weatherEntityOptional.get());
//
//    }
//
//    private WeatherEntity getWeatherFromWeatherStackt(String city) {
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_URL + city, String.class);
//        try {
//            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
//            return saveWeatherEntity(city, weatherResponse);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        WeatherEntity weatherEntity = new WeatherEntity(
//                city,
//                weatherResponse.location().name(),
//                weatherResponse.location().country(),
//                weatherResponse.current().temperature(),
//                localDateTime.now(),
//                localDateTime.parse(weatherResponse.location().localTime(), dateTimeFormatter));
//        return weatherRepository.save(weatherEntity);
//
//    }
//
//}
