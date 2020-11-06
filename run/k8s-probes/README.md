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

**Actuator Heath Check**

* using a web browser or curl go to `http://127.0.0.1:8080/actuator/health` 
* notice the `livenessState` and `readinessState` which are designed for use Kubernetes     
* inspect the `src/main/java/resources/application.yml` notice the probes enabled flag. This
  flag is set automatically when the app is launched in k8s. We are turning it on so you can 
  explore the app outside of k8s to understand the spring boot behavior.

**Readiness Probe**

A failing readiness probe causes k8s to stop sending requests to the app container. 

* using a web browser or curl go to `http://127.0.0.1:8080/actuator/health/readiness` it should 
  report up, this is dedicated readiness probe.
  
* using a browser go to `http://127.0.0.1:8080/readiness/fail` it will cause the readiness probe 
  to fail. 
  
* using a browser go to `http://127.0.0.1:8080/actuator/health` you should see the overall app 
  status is OUT_OF_SERVICE 
  
* using a browser go to `http://127.0.0.1:8080/` notice the app is still working. The output of 
  the readiness state is for kuberenets to stop sending traffic to the app, it does cause the app to 
  stop accepting network connection.  
  
* using a browser go to `http://127.0.0.1:8080/readiness/pass` it will cause the readiness probe 
    to start passing gain.
    
* using a browser go to `http://127.0.0.1:8080/` you will notice overall app status is back up that
  signals to kuberenets to start sending the app requests. 

*  Inspect the `ProbesController` class to see how the application publishes events that cause 
  it to transition it's state. 

**Liveness Probe**

A failing liveness probe causes k8s to restart the container. 

* using a web browser or curl go to `http://127.0.0.1:8080/actuator/health/liveness` it should 
  report up, this is dedicated liveness probe.
  
* using a browser go to `http://127.0.0.1:8080/liveness/fail` it will cause the liveness probe 
  to fail. 
  
* using a browser go to `http://127.0.0.1:8080/actuator/health` you should see the overall app 
  status is DOWN  
  
* using a browser go to `http://127.0.0.1:8080/` notice the app is still working. The output of 
  of the liveness state is for kuberenets to stop restart the app container. 
   
* using a browser go to `http://127.0.0.1:8080/liveness/pass` it will cause the liveness probe 
    to start passing gain.
    
* using a browser go to `http://127.0.0.1:8080/` you will notice overall app status is back up.

* Inspect the `ProbesController` class to see how the application publishes events that cause 
  it to transition it's state. 
  

**deploy to k8s**

*The following steps assume you have a K8s cluster running on your laptop. Docker Desktop Kubernetes
or minikube are sufficient. If you are using a remote cluster you will need to adapt the K8s 
deployment manifests to expose the deployment to your laptop.*

* terminate the container using `Ctrl+C` or `docker kill`
* execute `kubectl get all` to validate that you have access to a Kubernetes cluster.
* K8s deployment manifests are in the `k8s` directory. Open a terminal then execute `cd k8s`.
* inspect `src/deployment.yml` file and notice the readiness probes and liveness probes are pointing
  to the Spring Boot Actuators dedicated k8s probes you accessed in the previous steps. 
* execute `kubectl apply -f deployment.yml` 
* inspect `src/service.yml` notice that it exposes the container as a NodePort. 
* execute `kubectl apply -f service.yml` 
* execute `kubectl get all` it will show NodePort mapping. NodePort value is picked randomly from 
an available port by k8s, therefore your NodePort will be different. On my machine it picked `31264`
as the port number, your machine will probably have a different value. note the node port value and 
use it in the following steps to reach the application url.
* using your browser access the NodePort of the app for example `http://localhost:31264/` you 
  should some randomly rotating quotes. 
* Access the application probes
* using a web browser or curl go to spring boot probes on the app Node Port for example. 
  * `http://127.0.0.1:31264/actuator/health` notice the `livenessState` and `readinessState`
     which are designed for use Kubernetes
  * `http://127.0.0.1:31264/actuator/health/liveness` it should report up, this is dedicated 
     liveness probe for spring boot and k8s.
  * `http://127.0.0.1:31264/actuator/health/readiness` it should report up, this is dedicated 
     readiness probe.

**Fail the Readiness Probe**


**Liveness definition**

liveness definition From the Spring Boot [docs](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/htmlsingle/#boot-features-application-availability)

> The internal state of Spring Boot applications is mostly represented by the Spring 
ApplicationContext. If the application context has started successfully, 
Spring Boot assumes that the application is in a valid state. An application is considered 
live as soon as the context has been refreshed

readiness definition from the Spring Boot [docs](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/htmlsingle/#boot-features-application-availability) 


> The “Readiness” state of an application tells whether the application is ready to handle traffic. 
A failing “Readiness” state tells the platform that it should not route traffic to the application 
>for now. This typically happens during startup, while CommandLineRunner and ApplicationRunner 
>components are being processed, or at any time if the application decides that it’s too busy for 
>additional traffic.

> An application is considered ready as soon as application and command-line runners have been 
>called, see Spring Boot application lifecycle and related Application Events.



**Resources**

* Relevant sections from Spring Boot docs  
  * [Application Availability](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/htmlsingle/#boot-features-application-availability)
  * [Kubernetes Probes](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/htmlsingle/#production-ready-kubernetes-probes)
  * [Boot Kubernetes Deployment Guide](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/htmlsingle/#cloud-deployment-kubernetes)
  * [Graceful shutdown](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/htmlsingle/#boot-features-graceful-shutdown)
  
* Guides and blog posts 
  * [Liveness and Readiness Probes with Spring Boot](https://spring.io/blog/2020/03/25/liveness-and-readiness-probes-with-spring-boot) 
  * [Spring on Kubernetes](https://spring.io/guides/topicals/spring-on-kubernetes/)
  * [Spring Boot Docs](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/html/spring-boot-features.html#layering-docker-images) 
