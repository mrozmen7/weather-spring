//package com.ozmenyavuz.constans;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//// Constants katmanı, sabit değerlerin tek bir yerde toplanmasını sağlar.
//// Böylece kodda magic number veya hard-coded string kullanımı önlenir.
//
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationProperties(prefix = "weather-stack")
//@Getter
//@Setter // Lombok @Getter ve @Setter kullandığınız için manuel getter/setter'lara gerek yok.
//public class WeatherApiProperties {
//
//    private String apiUrl; // 'url' -> 'apiUrl' olarak değiştirildi
//    private String apiKey; // 'key' -> 'apiKey' olarak değiştirildi
//
//    public String buildUrlForCity(String city) {
//        // Alan adları değiştiği için burada da güncelliyoruz.
//        return apiUrl + "?access_key=" + apiKey + "&query=" + city;
//    }
//
//    // Lombok @Getter ve @Setter kullandığınız için aşağıdaki manuel getter/setter'lar
//    // aslında gereksizdir ve kaldırılabilir. Ancak projenizin yapısına göre
//    // bırakmak isterseniz, alan adlarına göre güncellenmeleri gerekir.
//    // Eğer Lombok kullanıyorsanız, bu blok tamamen silinebilir.
//    /*
//    public String getApiUrl() { // getUrl -> getApiUrl
//        return apiUrl;
//    }
//
//    public void setApiUrl(String apiUrl) { // setUrl -> setApiUrl
//        this.apiUrl = apiUrl;
//    }
//
//    public String getApiKey() { // getKey -> getApiKey
//        return apiKey;
//    }
//
//    public void setApiKey(String apiKey) { // setKey -> setApiKey
//        this.apiKey = apiKey;
//    }
//    */
//}