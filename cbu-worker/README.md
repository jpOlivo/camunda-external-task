# Cbu Worker
Worker que utiliza [Camunda External Task Client Java](https://github.com/camunda/camunda-external-task-client-java).

Este worker busca por tareas con el topico `cbu-creation` y bloquea las mismas por 1000 ms

```java
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
```

Existe un flag para habilitar o hacer *by-pass* del [Alias Worker](../-worker/README.md):

```zsh
flag:
  aliasAssociationRequired: true    
```

### Reference Documentation
* [Camunda External Task Client](https://docs.camunda.org/manual/7.9/user-guide/ext-client/)
