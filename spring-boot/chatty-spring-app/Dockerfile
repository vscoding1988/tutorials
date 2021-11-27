FROM openjdk:11-jdk-slim

ARG JAR_FILE=target/app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]

#  docker build -t vs/spring-boot-base .
