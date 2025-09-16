# =========================
# Stage 1: Build
# =========================
FROM maven:3.9.1-jdk-21 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar POM y descargar dependencias sin compilar todavía
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente y compilar empaquetando el JAR (sin tests)
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

# Variables de entorno para la base de datos de Render
ENV DB_HOST=dpg-d340oq3uibrs73b0dmcg-a.frankfurt-postgres.render.com
ENV DB_PORT=5432
ENV DB_NAME=database_36ve
ENV DB_USER=root
ENV DB_PASSWORD=K8o1DP8OVKH2QWMAiqCjBAPwD7NAg4Ux

# Copiar el JAR generado desde la fase de build
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
