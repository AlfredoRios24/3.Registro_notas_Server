# =========================
# Stage 1: Build
# =========================
FROM maven:3.9.1-eclipse-temurin-21 AS build

# Directorio de la app
WORKDIR /app

# Copiar POM y código fuente
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar el JAR (sin tests)
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Runtime
# =========================
FROM openjdk:17-jdk-slim

# Perfil activo de Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

# Directorio de la app
WORKDIR /app

# Copiar el JAR generado desde la fase de build
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
