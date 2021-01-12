package com.epam.incubation.service.guestprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GuestProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestProfileServiceApplication.class, args);
	}
}
