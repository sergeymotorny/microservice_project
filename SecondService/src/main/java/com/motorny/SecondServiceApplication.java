package com.motorny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SecondServiceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SecondServiceApplication.class, args);
	}
}
