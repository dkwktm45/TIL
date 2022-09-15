FROM openjdk:11-jdk-slim
COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8008
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar", "app.jar"]