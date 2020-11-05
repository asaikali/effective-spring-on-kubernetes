# k8s-probes  

Example showing how to  configure Kubernetes Health & Readiness Probes using spring boot 
actuators.

**Prerequisites** 

* [Java 11 JDK](https://adoptopenjdk.net/) 
* [Docker](https://www.docker.com/products/docker-desktop) 
* [dive](https://github.com/wagoodman/dive) tool for exploring container layers 
* [k9s](https://github.com/derailed/k9s) text gui for k8s
* [Kubernetes](https://kubernetes.io/) sample tested with Docker Desktop K8s but any k8s should work. 

**build and run the app** 

* build the app `mvnw clean package` to produce the fat jar 
* build the container `docker build . -t k8s-probes:1` 
* run the container `docker run -p 8080:8080 -t k8s-probes:1`
* test the app using a browser `http://localhost:8080/`

**try the spring boot k8s probes**

* inspect the `src/main/java/resources/application.yml` notice the probes enabled flag
* using a web browser or curl go to 
  * `http://127.0.0.1:8080/actuator/health` notice the `livenessState` and `readinessState`
     which are designed for use Kubernetes
  * `http://127.0.0.1:8080/actuator/health/liveness` it should report up, this is dedicated 
     liveness probe for spring boot and k8s.
  * `http://127.0.0.1:8080/actuator/health/readiness` it should report up, this is dedicated 
     readiness probe.

**configure**

* terminate the container using `Ctrl+C` or `docker kill`


**Resources**
 
* [Liveness and Readiness Probes with Spring Boot](https://spring.io/blog/2020/03/25/liveness-and-readiness-probes-with-spring-boot) 
* [Spring on Kubernetes](https://spring.io/guides/topicals/spring-on-kubernetes/)
* [Spring Boot Docs](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/html/spring-boot-features.html#layering-docker-images) 
