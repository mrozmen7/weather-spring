package com.ozmenyavuz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



// Config katmanında, uygulama genelinde kullanılacak bağımlılıkları ve yapılandırmaları merkezi olarak tanımlarız.
// Bu, tekrar eden kodu azaltır ve yönetilebilirliği artırır.

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplete(){
        return new RestTemplate();

        // Bu metotun döndürdüğü RestTemplate nesnesi Spring Container’a kaydedilir.
        // Ve her yerde kullaniriz bu temiz kod ilkesidir

    }
}
