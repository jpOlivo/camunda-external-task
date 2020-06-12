package com.demo.camunda.externaltask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountWorkerApplication.class, args);
	}

}
