FROM openjdk:8-jdk-alpine
LABEL mainainer="lukasz.welnicki@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/msc-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} msc-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/msc-api.jar"]