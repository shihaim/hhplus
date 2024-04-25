FROM openjdk:17-jdk-slim

WORKDIR /usr/src/myapp

COPY ./build/libs/ticketing-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./ticketing-0.0.1-SNAPSHOT.jar"]