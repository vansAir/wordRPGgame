FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/demo-0.0.1-SNAPSHOT.jar spring-docker.jar
ENTRYPOINT ["java","-jar","/spring-docker.jar"]