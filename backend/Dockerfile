#FROM java:8-jdk-alpine
#MAINTAINER isrdic
#COPY application/target/application-0.0.1.jar
#WORKDIR /usr/app
#RUN sh -c touch application-0.0.1.jar
#ENTRYPOINT ["java","-jar","/application-0.0.1.jar"]

FROM adoptopenjdk/openjdk11:latest
MAINTAINER isrdic
EXPOSE 8089
ARG JAR_FILE=application/target/application-0.0.1.jar
ADD ${JAR_FILE} application-0.0.1.jar
ENTRYPOINT ["java","-jar","/application-0.0.1.jar"]