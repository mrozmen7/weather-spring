package com.ozmenyavuz.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// 	•	Gelen JSON içinde senin modelinde tanımlı olmayan alanlar varsa,
//	•	Normalde Spring Boot (veya Jackson) hata fırlatır (“Unrecognized field” hatası).
//	•	@JsonIgnoreProperties(ignoreUnknown = true) yazarsan ➔ ekstra alanlar yokmuş gibi davranılır ➔ hata fırlatılmaz.

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse (
        Request request,
        Location location,
        Current current
){
}

// 	    Kullanıcıya dönülecek olan final response modelidir.
//	•	Yani Controller’dan dış dünyaya gönderilecek veri yapısıdır.
//	•	Örnek: Kullanıcıya dönecek cityName, temperature, responseTime gibi bilgiler burada olur.