FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} vehiclemanager.jar
ENTRYPOINT ["java","-jar","/vehiclemanager.jar"]