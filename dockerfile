# Etapa 1: Build de la app con Maven y Java 17
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

# Cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Código fuente
COPY src ./src

# Build sin tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final solo con JRE y el jar
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos el .jar generado por Maven
COPY --from=builder /app/target/av-*.jar app.jar

# Puerto expuesto (Spring Boot por defecto)
EXPOSE 8080

# Variables para OTEL, puedes configurarlas también desde Northflank
ENV OTEL_SERVICE_NAME=av-service \
    OTEL_TRACES_EXPORTER=otlp \
    OTEL_METRICS_EXPORTER=none \
    OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
