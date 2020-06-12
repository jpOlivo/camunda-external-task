package com.demo.camunda.externaltask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	WebClient webClient() {
		return WebClient.builder().baseUrl("http://process-engine:8084/engine-rest").build();
	}
}
