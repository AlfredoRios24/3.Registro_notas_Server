# =========================
# Stage 1: Build
# =========================
FROM maven:3.9.1-eclipse-temurin-20 AS build

WORKDIR /app

# Copiar solo pom.xml y descargar dependencias offline para cachear
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente y compilar JAR sin tests
COPY src ./src
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:20-jdk-jammy

# Perfil de Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /app

# Copiar el JAR compilado desde la fase de build
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
