# fatjar-dockerfile 

Example showing how to bundle a Spring Boot fat jar into a container image using 
a Dockerfile. 

**Prerequisites** 

* [Java 11 JDK](https://adoptopenjdk.net/) 
* [Docker](https://www.docker.com/products/docker-desktop) 
* [dive](https://github.com/wagoodman/dive) tool for exploring container layers 

**Try it out** 

* compile the app `mvnw clean package` to produce the fat jar 
* check the `target/` folder, observe the size of the `fatjar-dockerfile-0.0.1-SNAPSHOT.jar` file
* build the container `docker build . -t boot-fatjar` 
* check the size of the container `boot-fatjar` using `docker images ls` 
* run the container `docker run -p 8080:8080 -t boot-fatjar`
* test the app using a browser `http://localhost:8080/`
* terminate the container using `Ctrl+C` or `docker kill`
* explore the layers in the container using `dive boot-fatjar:latest`

**Resources**
 
* [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/) 
