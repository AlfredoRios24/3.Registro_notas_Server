# ===================================
# Dockerfile ultraoptimizado para Maven + Java 21
# ===================================

# 1. Imagen base con Maven + JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# 2. Directorio de trabajo
WORKDIR /app

# 3. Copiar los archivos de proyecto
COPY pom.xml .
COPY src ./src

# 4. Compilar sin tests
RUN mvn clean package -DskipTests

# 5. Imagen final más ligera (solo JDK 21)
FROM eclipse-temurin:21-jre
WORKDIR /app

# 6. Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# 7. Exponer puerto
EXPOSE 8080

# 8. Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
