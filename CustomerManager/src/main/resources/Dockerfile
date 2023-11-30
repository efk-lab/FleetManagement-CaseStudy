FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} customermanager.jar
ENTRYPOINT ["java","-jar","/customermanager.jar"]