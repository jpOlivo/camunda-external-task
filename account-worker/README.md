# Account Worker

Worker que utiliza [Camunda Rest API External Task](https://docs.camunda.org/manual/7.6/reference/rest/external-task/) para buscar y ejecutar tareas con el topico `account-creation`

Este worker ejecuta en intervalos de tiempo fijo dado por:

```yml
scheduler:
  fixedDelay: 100
```
y en cada busqueda puede obtener hasta 10 tareas para procesar

```java
private FetchAndLockRequestDto buildFetchAndLockRequestDto() {	         
    return new FetchAndLockRequestDto("account-worker-id", 10, 10000L, getTopics());
}
```

tambien, cada tarea es bloqueada por 1000 ms, pasado ese tiempo la tarea se libera para ser procesada por otro worker

```java
private List<TopicRequestDto> getTopics() {
    List<TopicRequestDto> topics = new ArrayList<TopicRequestDto>();
    TopicRequestDto topicRequestDto = new TopicRequestDto("account-creation", 1000L, null, null);
    topics.add(topicRequestDto);
    return topics;
}
```
Una vez obtenidas y bloqueadas las tareas, el worker las procesa una a una:

```java
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
```

Por ultimo, existe un flag para habilitar o hacer *by-pass* del [CBU Worker](../cbu-worker/README.md):

```zsh
flag:
  cbuCreationRequired: true    
```

### Reference Documentation
* [Rest API External Task](https://docs.camunda.org/manual/7.6/reference/rest/external-task/)
