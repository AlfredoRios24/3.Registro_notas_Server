# =========================
# Stage 1: Build
# =========================
FROM maven:3.9.1-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar pom.xml y fuentes
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar el JAR (sin tests)
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Run
# =========================
FROM openjdk:17-jdk-slim

# Directorio de la app
WORKDIR /app

# Copiar el JAR generado desde el stage de build
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Activar perfil de producción
ENV SPRING_PROFILES_ACTIVE=prod

# Exponer el puerto de Spring Boot
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
