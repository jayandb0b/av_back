# --- Etapa de Construcción ---
FROM shenlw/maven:3.9-jdk-17 AS builder  # Cambia la etiqueta aquí
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Descarga las dependencias (aprovechando la capa de Docker para caché)
RUN mvn dependency:go-offline

# Construye la aplicación
RUN mvn clean package -DskipTests

# --- Etapa de Descarga del Agente de OpenTelemetry ---
FROM alpine/curl:latest AS otel_agent_downloader
ARG OTEL_AGENT_VERSION=1.36.0  # Define la versión del agente
WORKDIR /otel-agent
RUN curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar -o opentelemetry-javaagent.jar

# --- Etapa de Ejecución ---
FROM eclipse-temurin:17-jre-jammy

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la etapa de construcción
COPY --from=builder /app/target/*.jar av.jar

# Copia el agente de OpenTelemetry
COPY --from=otel_agent_downloader /otel-agent/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

# Define el usuario para ejecutar la aplicación (por seguridad)
RUN addgroup --system spring && adduser --system --group spring spring

USER spring:spring

# Expone el puerto
EXPOSE 8080

# Define variables de entorno para OpenTelemetry y Java
ENV JAVA_OPTS="-javaagent:/app/opentelemetry-javaagent.jar \
               -Dotel.exporter.otlp.endpoint=otel-collector:4317 \
               -Dotel.service.name=av \
               -Dotel.exporter.otlp.metrics.protocol=grpc \
               -Dotel.exporter.otlp.traces.protocol=grpc \
               -Xms512m -Xmx1024m"

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "$JAVA_OPTS", "-jar", "av.jar"]

# Define un health check
HEALTHCHECK --interval=5m --timeout=3s CMD curl -f http://localhost:8080/actuator/health || exit 1
