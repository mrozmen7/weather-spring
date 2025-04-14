package com.ozmenyavuz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.ozmenyavuz"})
@ComponentScan("com.ozmenyavuz")
public class WeatherApplication {

	public static void main(String[] args) {

		SpringApplication.run(WeatherApplication.class, args);
	}

}
