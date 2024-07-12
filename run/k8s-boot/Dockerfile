FROM bellsoft/liberica-openjdk-debian:21 as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM bellsoft/liberica-openjre-debian:21
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
#ENV JAVA_TOOL_OPTIONS "-XX:MaxRAMPercentage=80"
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
