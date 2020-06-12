package com.demo.camunda.externaltask;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CbuWorkerApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(CbuWorkerApplication.class);

	@Value("${flag.aliasAssociationRequired:false}")
	private boolean aliasAssociationRequired;

	public static void main(String[] args) {
		SpringApplication.run(CbuWorkerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://process-engine:8084/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("cbu-creation").lockDuration(1000) // the default lock duration is 20 seconds, but you can
															// override this
				.handler((externalTask, externalTaskService) -> {
					// Put your business logic here

					LOGGER.info("Creating CBU ~ {}", externalTask.getProcessInstanceId());
					LOGGER.debug("aliasAssociationRequired {}", aliasAssociationRequired);

					externalTaskService.complete(externalTask,
							Variables.putValue("alias_association_required", aliasAssociationRequired));
				}).open();

	}

}
