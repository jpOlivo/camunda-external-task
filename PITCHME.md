![Camunda Logo](https://www.attuneww.com/wp-content/uploads/2018/07/Camunda.jpg)

# Service Tasks

Presented by [Juan Olivera](https://jpolivo.github.io/resume/)

---

## Approaches

* [Internal Tasks](https://docs.camunda.org/manual/7.9/reference/bpmn20/tasks/service-task/#calling-java-code)
* [External Tasks](https://docs.camunda.org/manual/7.7/user-guide/process-engine/external-tasks/)

---

## Internal Task

* Synchronous invocation
* [Push-Principle](https://camunda.com/best-practices/invoking-services-from-the-process/#_understanding_strong_push_and_pull_strong)
* Implemented as [Delegation Code](https://docs.camunda.org/manual/7.7/user-guide/process-engine/delegation-code/) / [Script](https://docs.camunda.org/manual/7.7/user-guide/process-engine/scripting/)
* Deployed alongside engine process

---

## External Task

* Asynchronous invocation
* [Pull-Principle](https://camunda.com/best-practices/invoking-services-from-the-process/#_understanding_strong_push_and_pull_strong)
* Implemented with [External Task Pattern](https://docs.camunda.org/manual/7.7/user-guide/process-engine/external-tasks/#the-external-task-pattern)
* Workers can be decoupled from processes engine

---

## External Task Pattern

![External Task Pattern](https://docs.camunda.org/manual/7.8/user-guide/process-engine/img/external-task-pattern.png)

----
### Long Polling to Fetch and Lock 

![Long polling](https://docs.camunda.org/manual/latest/user-guide/process-engine/img/external-task-long-polling.png)

---

## External Task API

* [REST API](https://docs.camunda.org/manual/7.8/reference/rest/external-task/)
* [Java API](https://docs.camunda.org/manual/7.8/user-guide/process-engine/external-tasks/#java-api)

---

## External Task Client Libraries
* [Java](https://github.com/camunda/camunda-external-task-client-java)
* [Javascript](https://github.com/camunda/camunda-external-task-client-js) 
* *[Springboot Starter](https://github.com/camunda/camunda-external-task-client-spring-boot)*

---

## External Task Features

* Fetch and Lock
* Unlocking Tasks
* Extend the lock duration
* Reporting Failures
* Request Interceptors (Client Libs)
* Task Priorization (only API)
* Task Retries (only API)

---

# Demo

----

## Summary

1. Target
2. Strategy
3. Scope
4. Scenarios
   
---

## External Tasks & Idempotency

![Workers and Idempotency](https://blog.camunda.com/post/2017/08/remote-workers-and-idempotency/idempotentWorker.png)

---
<!-- .slide: data-background="https://media.giphy.com/media/jPAdK8Nfzzwt2/giphy.gif" -->
## External Tasks

##### or

## Delegates?

---

## Much More

* [Camunda APIs](https://blog.bernd-ruecker.com/use-camunda-without-touching-java-and-get-an-easy-to-use-rest-based-orchestration-and-workflow-7bdf25ac198e)
* [Camunda External Tasks](https://docs.camunda.org/manual/7.8/user-guide/process-engine/external-tasks/)
* [Camunda Service Task](https://docs.camunda.org/manual/7.9/reference/bpmn20/tasks/service-task/)
* [Camunda Delegation Code](https://docs.camunda.org/manual/7.13/user-guide/process-engine/delegation-code/)
* [Camunda External Task Client](https://docs.camunda.org/manual/7.12/user-guide/ext-client/)
* [Invoking Services from the Process](https://camunda.com/best-practices/invoking-services-from-the-process/)

---

<!-- .slide: style="text-align: left;" -->
# The End 

* [Source Code External Task](https://github.com/jpOlivo/camunda-external-task)