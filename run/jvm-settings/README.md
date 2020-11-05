# jvm-settings  

Example showing best practices for configuring JVM settings RAM, and CPU when running in a 
container. 

**Prerequisites** 

* [Java 11 JDK](https://adoptopenjdk.net/) 
* [Docker](https://www.docker.com/products/docker-desktop) 
* [dive](https://github.com/wagoodman/dive) tool for exploring container layers 

**build and run the app** 

* build the app `mvnw clean package` to produce the fat jar 
* build the container `docker build . -t jvm-settings:1` 
* check the size of the container `jvm-settings:1` using `docker images` 
* run the container `docker run -p 8080:8080 -t jvm-settings:1`
* notice the first line of output it start with `Picked up JAVA_TOOL_OPTIONS: `
* inspect the `Dockerfile` notice the `ENV` command to set a default value for `JAVA_TOOL_OPTIONS`
* test the app using a browser `http://localhost:8080/`
* terminate the container using `Ctrl+C` or `docker kill`
* override the memory settings before launching the container 
  `docker run -e JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75" -p 8080:8080 -t jvm-settings:1`
* notice the first line of output it start with `Picked up JAVA_TOOL_OPTIONS: ` has the new 
  percentage you set. 

**Resources**
 
* [JAVA_TOOL_OPTIONS env var](https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/envvars002.html)
* [Spring Boot Docs](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/reference/html/spring-boot-features.html#layering-docker-images) 
* [Maven Plugin](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/maven-plugin/reference/htmlsingle/#repackage-layers)
* [Gradle Plugin](https://docs.spring.io/spring-boot/docs/2.4.0-RC1/gradle-plugin/reference/htmlsingle/#packaging-layered-jars )
