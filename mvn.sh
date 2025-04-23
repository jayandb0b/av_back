clear

mvn -U clean package

cp target/GsExampleService.war /home/jalbala/java/servers/apache-tomcat-9.0.65-UAT/webapps/

arrancaTomcat9UAT.sh

