FROM openjdk:14-jdk-alpine
EXPOSE 8080
ADD /build/libs/stock-reader-0.0.1-SNAPSHOT.jar stock-reader.jar
ENTRYPOINT ["java", "-jar", "stock-reader.jar"]