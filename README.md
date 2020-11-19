# effective-spring-on-kubernetes

Example applications show to use Spring Boot 2.4.x on Kubernetes.

## Sample Applications

There are two types of sample application in this repo. Samples showing various approaches for containirzing Spring appliactions in the `containerize` folder. Samples showing features for running spring applications on Kubernetes in 
`run` folder. Each sample application has a readme.md that explains how to run the sample and points out interestning
things to look at.

## Software Prerequisites

* [Java 11 JDK](https://adoptopenjdk.net/) 
* Favourite Java IDE [Eclipse Spring Tool Suite](https://spring.io/tools) or [IntelliJ](https://www.jetbrains.com/idea/download)
* [Docker](https://www.docker.com/products/docker-desktop) 
* [dive](https://github.com/wagoodman/dive) tool for exploring container layers 
* [k9s](https://github.com/derailed/k9s) text gui for k8s
* [Kubernetes](https://kubernetes.io/) sample tested with Docker Desktop K8s. If you have a different k8s install you must know how to expose the app in k8s to your machine machine if you don't just use docker desktop k8s to run these demos.

### Validate your Environment 

* Clone the repo 
* Go to the sample in `containerize/fatjar-dockerfile` 
  * follow the setps for running the app this will ensure you have a working Java 11 and maven.
  * import the application into your favourite IDE 
  * run the app from within the IDE 
* Test your docker dekstop Kuberentes using `kubectl get nodes` and you should see 1 node. On my machine 
I get the result below.

```
NAME             STATUS   ROLES    AGE   VERSION
docker-desktop   Ready    master   14d   v1.19.3
```
