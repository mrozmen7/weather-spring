package com.ozmenyavuz.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CityParameterValidator.class}) // doğrulama işini CityParameterValidator sınıfı yapacak.

// Şehir ismini ister bir request parametresinde ister bir DTO field’ında ister bir API endpoint’inde kullanabiliyorum.
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})

@Retention(RetentionPolicy.RUNTIME) //  Bu anotasyonun runtime sırasında da erişilebilir olmasını sağlar.
// 	•	Spring Validation mekanizması, runtime sırasında bu anotasyonu okuyarak doğrulama yapar.
//	•	Eğer Retention CLASS ya da SOURCE olsaydı ➔ uygulama çalışırken bu anotasyon görünmezdi ➔ validation çalışmazdı.

public @interface CityNameConstraint { // interface ile kendimize özgü anotasyon icin

    String message() default "Invalid city name"; // Validation başarısız olursa kullanıcıya dönecek varsayılan hata mesajı.
    Class<?>[] groups() default {}; // 	•	Farklı validation grupları tanımlayarak örneğin “Create”, “Update” işlemlerinde farklı kurallar
    Class<? extends Payload>[] payload() default {}; //  Validation mesajlarına ekstra metadata taşımak için kullanılır.
}