FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ambulance-transfer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ambulance-transfer-0.0.1-SNAPSHOT.jar"]
