package com.assignment.holiday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HolidayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolidayApplication.class, args);
	}

}
