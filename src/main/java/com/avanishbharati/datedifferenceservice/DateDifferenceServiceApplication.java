package com.avanishbharati.datedifferenceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DateDifferenceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DateDifferenceServiceApplication.class, args);
	}

}
