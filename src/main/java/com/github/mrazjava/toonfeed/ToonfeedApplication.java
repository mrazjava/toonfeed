package com.github.mrazjava.toonfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ToonfeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToonfeedApplication.class, args);
	}
}
