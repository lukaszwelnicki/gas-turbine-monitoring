FROM openjdk:8-jdk-alpine
LABEL mainainer="lukasz.welnicki@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/lws-api.jar
ADD ${JAR_FILE} lws-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/lws-api.jar"]