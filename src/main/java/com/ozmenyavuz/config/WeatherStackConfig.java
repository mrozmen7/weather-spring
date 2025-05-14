package com.ozmenyavuz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherStackConfig {

    public static String API_URL;
    public static String API_KEY;

    @Value("${weather-stack.api.url}")
    public void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    @Value("${weather-stack.api.key}")
    public void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}