# Etapa 1: Build con Maven + Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar pom.xml y descargar dependencias para aprovechar cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código
COPY src ./src

# Compilar el proyecto sin ejecutar tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución liviana
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el .jar desde el contenedor builder
COPY --from=builder /app/target/av-*.jar app.jar

# Exponer el puerto de Spring Boot
EXPOSE 8080

# Variables de entorno para OpenTelemetry (puedes configurarlas desde Northflank también)
ENV OTEL_SERVICE_NAME=av-service \
    OTEL_TRACES_EXPORTER=otlp \
    OTEL_METRICS_EXPORTER=none \
    OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
