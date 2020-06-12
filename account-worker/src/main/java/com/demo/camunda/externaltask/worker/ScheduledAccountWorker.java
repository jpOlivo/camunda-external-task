package com.demo.camunda.externaltask.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.client.task.impl.dto.CompleteRequestDto;
import org.camunda.bpm.client.topic.impl.dto.FetchAndLockRequestDto;
import org.camunda.bpm.client.topic.impl.dto.TopicRequestDto;
import org.camunda.bpm.client.variable.impl.TypedValueField;
import org.camunda.bpm.engine.rest.dto.externaltask.LockedExternalTaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.camunda.externaltask.AccountWorkerApplication;

@Component
public class ScheduledAccountWorker implements AccountWorker {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledAccountWorker.class);

	@Value("${flag.cbuCreationRequired:false}")
	private boolean cbuCreationRequired;

	@Autowired
	private WebClient webClient;

	public static void main(String[] args) {
		SpringApplication.run(AccountWorkerApplication.class, args);
	}

	@Override
	@Scheduled(fixedDelayString = "${scheduler.fixedDelay}")
	public void execute() {
		LockedExternalTaskDto[] lockedExternalTask = this.webClient.post().uri("/external-task/fetchAndLock")
				.body(BodyInserters.fromValue(buildFetchAndLockRequestDto())).retrieve()
				.bodyToMono(LockedExternalTaskDto[].class).block();

		for (LockedExternalTaskDto task : lockedExternalTask) {
			// Put your business logic here

			LOGGER.debug("Working in task {}", task.getId());
			LOGGER.info("Creating account ~ {}", task.getProcessInstanceId());
			LOGGER.debug("cbuCreationRequired {}", cbuCreationRequired);

			// Complete the task
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("id", task.getId());

			webClient.post().uri("/external-task/{id}/complete", variables)
					.body(BodyInserters.fromValue(buildCompleteRequestDto())).retrieve().bodyToMono(Void.class).block();
		}

	}

	private FetchAndLockRequestDto buildFetchAndLockRequestDto() {
		return new FetchAndLockRequestDto("account-worker-id", 10, 10000L, getTopics());
	}

	private List<TopicRequestDto> getTopics() {
		List<TopicRequestDto> topics = new ArrayList<TopicRequestDto>();
		TopicRequestDto topicRequestDto = new TopicRequestDto("account-creation", 1000L, null, null);
		topics.add(topicRequestDto);
		return topics;
	}

	private CompleteRequestDto buildCompleteRequestDto() {
		Map<String, TypedValueField> vars = new HashMap<String, TypedValueField>();

		TypedValueField field = new TypedValueField();
		field.setType("boolean");
		field.setValue(Boolean.valueOf(cbuCreationRequired));
		vars.put("cbu_creation_required", field);

		CompleteRequestDto requestDto = new CompleteRequestDto("account-worker-id", vars, vars);
		return requestDto;
	}

}
