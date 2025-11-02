FROM openjdk:17-oraclelinux8
ARG JAR_FILE=target/rock-paper-scissors-*.jar
COPY ${JAR_FILE} rock-paper-scissors-2.jar
ENTRYPOINT ["java", "-jar", "rock-paper-scissors-2.jar"]