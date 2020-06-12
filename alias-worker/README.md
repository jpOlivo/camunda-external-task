# Alias Worker
Worker que utiliza [Camunda External Task Client Java](https://github.com/camunda/camunda-external-task-client-java).

Este worker simula una ***Long-Running Task***, bloqueandose por 15 segundos y buscando por tareas con el topico `alias-association`

```java
public void run(String... args) throws Exception {
    ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://process-engine:8084/engine-rest")
            .asyncResponseTimeout(10000) // long polling timeout
            .build();

    // subscribe to an external task topic as specified in the process
    client.subscribe("alias-association").lockDuration(1000) // the default lock duration is 20 seconds, but you can
                                                            // override this
            .handler((externalTask, externalTaskService) -> {
                // Put your business logic here
                
                try {
                    LOGGER.info("Sleeping task of account workflow...zzzz");
                    TimeUnit.SECONDS.sleep(15);
                } catch (InterruptedException e) {
                    LOGGER.error("Error on sleeping thread", e);
                }
                
                LOGGER.info("Association Alias ~ {}", externalTask.getProcessInstanceId());

                externalTaskService.complete(externalTask);
            }).open();
    
}
```


### Reference Documentation
* [Camunda External Task Client](https://docs.camunda.org/manual/7.9/user-guide/ext-client/)
