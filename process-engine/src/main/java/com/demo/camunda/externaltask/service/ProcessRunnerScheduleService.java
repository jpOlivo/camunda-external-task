package com.demo.camunda.externaltask.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProcessRunnerScheduleService implements ProcessRunnerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessRunnerScheduleService.class);

	@Autowired
	private RuntimeService runtimeService;

	@Scheduled(fixedRateString = "${scheduler.fixedRate}", initialDelayString = "${scheduler.initialDelay}")
	public void run() {
		String processDefKey = "accountCreationProcess";
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefKey);
		LOGGER.info("Starting new {} with id {} ", processDefKey, instance.getId());

		processDefKey = "loanApproval";
		instance = runtimeService.startProcessInstanceByKey(processDefKey);
		LOGGER.info("Starting new {} with id {} ", processDefKey, instance.getId());
	}
}
