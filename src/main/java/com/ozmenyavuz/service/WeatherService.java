package com.ozmenyavuz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozmenyavuz.constans.WeatherApiProperties;
import com.ozmenyavuz.dto.WeatherDto;
import com.ozmenyavuz.dto.WeatherResponse;
import com.ozmenyavuz.model.WeatherEntity;
import com.ozmenyavuz.repository.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository; // Spring
    private final RestTemplate restTemplate; // Spring
    private final WeatherApiProperties weatherApiProperties; //
    private final ObjectMapper objectMapper = new ObjectMapper(); // Spring

    public WeatherService(WeatherRepository weatherRepository,
                          RestTemplate restTemplate,
                          WeatherApiProperties weatherApiProperties) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
        this.weatherApiProperties = weatherApiProperties;
    }


    // 1 ANA METOT

    public WeatherDto getWeatherByCityName(String city) {
        Optional<WeatherEntity> weatherEntityOptional =
                weatherRepository.findFirstByRequestedCityNameOrderByLocalDateTimeDesc(city);
        //SELECT * FROM weather WHERE city = ? ORDER BY local_time DESC LIMIT 1 gibi bir SQL

        return weatherEntityOptional.map(weather -> {
            boolean isOld = weather.getLocalDateTime() // Verinin sisteme kaydedildiği zamanı döndürür.
                    .isBefore(
                            LocalDateTime.now().minusMinutes(30)); //Şu anki zaman/   Cache 30 dakika öncesine bir zaman hesaplıyoruz.
            if (isOld) {
                return WeatherDto.convert(getWeatherFromWeatherStack(city));
            }
            // 	Eğer veri 30 dakikadan eskiyse, API’ye yeni istek gönderilir:
            //	•	getWeatherFromWeatherStack(city): WeatherStack API’den güncel veri çeker.
            //	•	WeatherDto.convert(...): Dış API’den gelen WeatherEntity nesnesi, DTO’ya çevrilip döndürülür.

            return WeatherDto.convert(weather); // VERI GUNCELSE veri WeatherDto’ya çevrilerek döndürülür.
        }).orElseGet(() -> WeatherDto.convert(getWeatherFromWeatherStack(city))); // HIC KAYIT YOKSA YINE DIS API YE GIT
    }


    // 2 GET ATAR SERVICE = WeatherStack API CEKME
    // 🌍 WeatherStack API’den güncel hava durumu verisi alır ve işleyip veritabanına kaydetmek üzere hazırlar.

    private WeatherEntity getWeatherFromWeatherStack(String city) {
        String fullUrl = getWeatherStackUrl(city); // Dış API’ye istek atmak için tam URL’yi üretir.
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(fullUrl, String.class);
        //getForEntity → dış API’ye GET isteği atar

        String json = responseEntity.getBody();
        System.out.println("WeatherStack JSON Response: " + json);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(json, WeatherResponse.class); //  Jackson kütüphanesinin JSON’ı Java nesnesine çevirmek

            if (weatherResponse == null || weatherResponse.location() == null || weatherResponse.current() == null) {
                throw new RuntimeException("Hatalı veri geldi. API key doğru mu? Şehir geçerli mi?");
            }

            // JSON’dan elde edilen Java nesnesini veritabanına yazmak için saveWeatherEntity(...) metoduna gönderiyoruz
            return saveWeatherEntity(city, weatherResponse); //API’den gelen doğru veri şimdi veritabanına yazılacak
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON parse hatası: " + e.getMessage(), e);
        }
    }

    // 3 DB’ye Kaydetme Metodu
    // API’den gelen veriyi WeatherEntity nesnesine çevirip veritabanına kaydeder.

    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //WeatherStack API tarihleri string olarak gönderir, biz LocalDateTime’a çevirmek istiyoruz.

        WeatherEntity weatherEntity = new WeatherEntity(
                city,
                weatherResponse.location().name(), // sehir adi
                weatherResponse.location().country(), // ulkesi
                weatherResponse.current().temperature(), // sicaklik
                LocalDateTime.now(), // APIâ€™nin verdiÄŸi lokal saat (Ã¶rn. "2025-04-02 13:00")
                LocalDateTime.parse(weatherResponse.location().localTime(), dateTimeFormatter)
        );

        return weatherRepository.save(weatherEntity); // Veritabanına Kaydet
        // 	•	Oluşturulan entity, veritabanına yazılır.
        //	•	Geriye kayıt edilmiş hali (ID dahil) döndürülür.
    }

    // 4  Tam URL Üretici

    private String getWeatherStackUrl(String city) {
        return weatherApiProperties.buildUrlForCity(city);
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
