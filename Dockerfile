FROM openjdk:11
MAINTAINER Aleksey Kurbakov kurbakov.av@gmail.com
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]