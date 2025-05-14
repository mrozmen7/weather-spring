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
		io.github.cdimascio.dotenv.Dotenv dotenv = io.github.cdimascio.dotenv.Dotenv.load();
		System.setProperty("WEATHER_STACK_API_KEY", dotenv.get("WEATHER_STACK_API_KEY"));
		SpringApplication.run(WeatherApplication.class, args);
	}

}
