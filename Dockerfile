FROM openjdk:11-jre
ARG JAR_FILE=target/datastreamer-*.jar
COPY ${JAR_FILE} datastreamer.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "datastreamer.jar"]