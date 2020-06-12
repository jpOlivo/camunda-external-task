# Loan Worker
Worker que utiliza [Camunda External Task Client Java](https://github.com/camunda/camunda-external-task-client-java).

Este worker busca por tareas con el topico `check-loan-request`

```java
public void run(String... args) throws Exception {
	ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://process-engine:8084/engine-rest")
			.asyncResponseTimeout(10000) // long polling timeout
			.build();

	// subscribe to an external task topic as specified in the process
	client.subscribe("check-loan-request").lockDuration(1000) // the default lock duration is 20 seconds, but you
															  // can override this
			.handler((externalTask, externalTaskService) -> {
				// Put your business logic here

				LOGGER.info("Check loan request ~ {}", externalTask.getProcessInstanceId());

				externalTaskService.complete(externalTask);
			}).open();

}
```


### Reference Documentation
* [Camunda External Task Client](https://docs.camunda.org/manual/7.9/user-guide/ext-client/)
