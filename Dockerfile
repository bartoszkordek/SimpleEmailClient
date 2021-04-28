FROM openjdk:11.0-jdk-slim
COPY ./build/libs /app
WORKDIR /app
ENTRYPOINT ["java","-jar","SimpleEmailClient.jar"]
