# Rock Paper Scissors           ![Build Status](https://github.com/gavarava/rock-paper-scissors-2/actions/workflows/maven.yml/badge.svg)
A Spring Boot Microservice for the game of Rock Paper Scissors

A backend running in Postgres is required to run this application in the "prod" profile.
- Start Postgresql server
- Then run with profile liquibase for setting up the database
- Then run with prod command using the java jar command

###### About the game of Rock Paper Scissors
* [Rock Paper Scissors - Wikipedia ](https://en.wikipedia.org/wiki/Rock%E2%80%93paper%E2%80%93scissors)
* [Rock Paper Scissors - A Method for Competitive Game Play Design](http://www.gamasutra.com/view/feature/1733/rock_paper_scissors__a_method_for_.php)
## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Building the project
```
mvn clean install

mvn clean package -Dmaven.test.skip=true

```


## Running the tests
```
mvn clean verify

```

## Run the application
### Using java jar command
```
java -jar rock-paper-scissors-<version>.jar

java -jar -Dspring.profiles.active=transient rock-paper-scissors-2-1.4-SNAPSHOT.jar  

java -jar -Dspring.datasource.driver-class-name=org.postgresql.Driver -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -Dspring.datasource.username=postgres -Dspring.datasource.password=password123 -Dspring.profiles.active=liquibase rock-paper-scissors-2-1.4-SNAPSHOT.jar

java -jar -Dspring.datasource.driver-class-name=org.postgresql.Driver -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -Dspring.datasource.username=postgres -Dspring.datasource.password=password123 -Dspring.profiles.active=prod rock-paper-scissors-2-1.4-SNAPSHOT.jar

```
### Using Docker
##### Build and Push the Docker image
```
docker build -t edekargaurav88/rps2:1.2 .
docker push edekargaurav88/rps2:1.2
```
##### Run Docker container
```
docker run -p 8080:8080 -dit rps2:1.1
```

## Built With
* [SpringBoot](http://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - Containerization

## Versioning
* [Git](https://git-scm.com/)

## Java Coding Style for IDE
* [Google Style - Intellij](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)
* [Google Style - Eclipse](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml)

## Authors
* **Gaurav Edekar** - https://github.com/gavarava

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
* Google
