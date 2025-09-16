# Imagen base
FROM openjdk:17-jdk-slim

# Variables de entorno para Spring
ENV SPRING_PROFILES_ACTIVE=prod

# Directorio de la app
WORKDIR /app

# Copiar el jar generado
COPY target/3.Registro_notas_Server-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
