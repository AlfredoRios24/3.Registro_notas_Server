# Imagen base
FROM openjdk:17-jdk-slim

# Perfil activo de Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

# Directorio de la app
WORKDIR /app

# Copiar el JAR generado por Maven
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
