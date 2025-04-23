#@echo off

REM START "JEAGER" "C:\Java\servers\jaeger-2.3.0-windows-amd64\jaeger.exe"
REM START "LOKI" "C:\Java\servers\grafana-v11.5.1\loki\loki-windows-amd64.exe" --config.file=config.yml
REM START "GRAFANA" "C:\Java\servers\grafana-v11.5.1\bin\grafana-server.exe"

REM C:\Java\jdk\jdk-17.0.2\bin\java -javaagent:opentelemetry-javaagent.jar -Dotel.service.name=AV -jar C:\Java\demo\AS\target\av-0.0.1-SNAPSHOT.jar

@echo off

REM CD C:\Java\demo\AS
CD C:\Java\servers\jaeger-2.3.0-windows-amd64\

START "JEAGER" "jaeger.exe"

CD C:\Java\servers\grafana-v11.5.1\bin\

START "GRAFANA" "grafana-server.exe"

CD C:\Java\servers\grafana-v11.5.1\loki\

START "LOKI" "loki-windows-amd64.exe" --config.file=config.yml

CD C:\Java\demo\AS

C:\Java\jdk\jdk-17.0.2\bin\java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1043 -javaagent:opentelemetry-javaagent.jar -Dotel.service.name=AV -jar C:\Java\av\AS\target\av-0.0.1-SNAPSHOT.jar