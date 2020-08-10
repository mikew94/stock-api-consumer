FROM openjdk:14-jdk-alpine
EXPOSE 8080
ADD /build/libs/stock-api-consumer-0.0.1-SNAPSHOT.jar stock-api-consumer.jar
ENTRYPOINT ["java", "-jar", "stock-api-consumer.jar"]