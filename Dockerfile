FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/Register_Notes-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8091

ENTRYPOINT ["java", "-jar", "app.jar"]