# Stage 1: Build with Maven
# Imagen base personalizada de Maven en tu Docker Hub
FROM elsermanuel/maven:3.9.9-amazoncorretto-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Java
# Imagen base personalizada de JRE en tu Docker Hub
FROM elsermanuel/eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


# --- GUÍA PARA EL ENTREGABLE (Docker Hub) ---

# A. SUBIR IMÁGENES BASE A TU CUENTA (Hacer esto una vez)
# -------------------------------------------------------
# 1. Maven:
# docker pull maven:3.9.9-amazoncorretto-21-alpine
# docker tag maven:3.9.9-amazoncorretto-21-alpine elsermanuel/maven:3.9.9-amazoncorretto-21-alpine
# docker push elsermanuel/maven:3.9.9-amazoncorretto-21-alpine

# 2. JRE:
# docker pull eclipse-temurin:21-jre-alpine
# docker tag eclipse-temurin:21-jre-alpine elsermanuel/eclipse-temurin:21-jre-alpine
# docker push elsermanuel/eclipse-temurin:21-jre-alpine


# B. CONSTRUIR TU APLICACIÓN
# -------------------------------------------------------
# docker build -t elsermanuel/springboot-sqlserver:1.0 -f dockerfile-Elser/Dockerfile .


# C. SUBIR TU APLICACIÓN A DOCKER HUB
# -------------------------------------------------------
# docker push elsermanuel/springboot-sqlserver:1.0


# D. EJECUTAR PARA PROBAR
# -------------------------------------------------------
# docker run -d --name app-be -p 8080:8080 elsermanuel/springboot-sqlserver:1.0
