FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} fleetmanager.jar
ENTRYPOINT ["java","-jar","/fleetmanager.jar"]