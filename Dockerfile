# =========================
# Stage 1: Build
# =========================
FROM maven:3.9.1-eclipse-temurin-21 AS build

# Directorio de la app
WORKDIR /app

# Copiar solo pom.xml primero para descargar dependencias (optimiza cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar el JAR sin tests
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:21-jdk-jammy

# Perfil activo de Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

# Directorio de la app
WORKDIR /app

# Copiar JAR generado desde la fase de build
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
