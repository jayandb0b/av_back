# Etapa 1: build con Maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiamos dependencias primero para aprovechar el cache de Docker
COPY pom.xml .
COPY src ./src

# Empaquetamos la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: imagen final con JRE y OpenTelemetry
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia el .jar generado desde el builder
COPY --from=builder /app/target/av-*.jar app.jar

# Copia el agente de OpenTelemetry (si lo usas como agente externo)
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

# Puerto en el que corre tu app
EXPOSE 8080

# Variables de entorno básicas de OpenTelemetry (puedes extenderlas en Northflank)
ENV OTEL_SERVICE_NAME=av-service \
    OTEL_TRACES_EXPORTER=otlp \
    OTEL_METRICS_EXPORTER=none \
    OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317

# Comando de inicio con el agente
ENTRYPOINT ["java", "-javaagent:/otel/opentelemetry-javaagent.jar", "-jar", "app.jar"]
