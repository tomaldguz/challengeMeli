package com.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;


@SpringBootApplication()
public class DemoApplication {

	public static void main(String[] args) {
		TimeZone.setDefault( TimeZone.getTimeZone("GMT-3") );
		SpringApplication.run(DemoApplication.class, args);
	}


}
