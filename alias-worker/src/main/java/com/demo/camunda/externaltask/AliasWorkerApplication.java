package com.demo.camunda.externaltask;

import java.util.concurrent.TimeUnit;

import org.camunda.bpm.client.ExternalTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AliasWorkerApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(AliasWorkerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AliasWorkerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://process-engine:8084/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("alias-association").lockDuration(1000) // the default lock duration is 20 seconds, but you can
																// override this
				.handler((externalTask, externalTaskService) -> {
					// Put your business logic here
					
					LOGGER.info("Association Alias ~ {}", externalTask.getProcessInstanceId());

					try {
						LOGGER.info("Sleeping task of account workflow...zzzz");
						TimeUnit.SECONDS.sleep(15);
					} catch (InterruptedException e) {
						LOGGER.error("Error on sleeping thread", e);
					}
					

					externalTaskService.complete(externalTask);
				}).open();
		
	}

}
