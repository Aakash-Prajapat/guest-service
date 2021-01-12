package com.epam.incubation.service.guestprofile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
