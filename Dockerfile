# =========================
# Stage 1: Build
# =========================
FROM maven:3.9.1-jdk-21 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar pom.xml y descargar dependencias offline
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente y compilar empaquetando el JAR sin tests
COPY src ./src
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:21-jdk-jammy

# Perfil activo de Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR generado desde la fase de build
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
