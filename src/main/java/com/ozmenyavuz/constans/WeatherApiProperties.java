package com.ozmenyavuz.constans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiProperties {

    private String url;
    private String key;

    public String buildUrlForCity(String city) {
        return url + "?access_key=" + key + "&query=" + city;
    }

    // getter + setter (zorunlu)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}