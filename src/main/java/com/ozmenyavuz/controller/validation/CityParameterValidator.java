package com.ozmenyavuz.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CityParameterValidator implements ConstraintValidator<CityNameConstraint, String> {

    // Logger ile hatalı girişleri kayıt altına alıyorum.
    // Hangi veriler hatalı geliyor, hangi kullanıcılar kötü veri gönderiyor ➔ bunu prod ortamda loglayarak görebilirim.
    private static final Logger logger = LoggerFactory.getLogger(CityParameterValidator.class);

    @Override
    public void initialize(CityNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    // asıl doğrulama mantığının çalıştığı yer.
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        value = value.replaceAll("[^a-zA-Z0-9]", ""); // Gelen şehir isminden harf ve rakam dışı tüm karakterleri kaldırıyorum.
        boolean isValid = !StringUtils.isNumeric(value) && !StringUtils.isAllBlank(value);

        // Loglama ve hata mesajı oluşturma:
        if (!isValid) {
            context.buildConstraintViolationWithTemplate(value).addConstraintViolation();
            logger.info("The city parameter is not valid. value:" + value);
        }
        return !StringUtils.isNumeric(value) && !StringUtils.isAllBlank(value);
        // Validation başarılı mı, değil mi ➔ true ya da false dönüyorum.
    }
}

// Amacım:
//	•	Kullanıcıdan gelen şehir isminin gerçekten anlamlı bir veri olup olmadığını kontrol etmek.
//	•	Tamamen temiz, merkezi, güvenli bir doğrulama mekanizması kurmak.
//	•	Kirli, sahte, boş ya da sadece sayıdan oluşan şehir isimlerini engellemek.