# Rock Paper Scissors           ![Build Status](https://github.com/gavarava/rock-paper-scissors-2/actions/workflows/maven.yml/badge.svg)
A Spring Boot Microservice for the game of Rock Paper Scissors
###### About the game of Rock Paper Scissors
* [Rock Paper Scissors - Wikipedia ](https://en.wikipedia.org/wiki/Rock%E2%80%93paper%E2%80%93scissors)
* [Rock Paper Scissors - A Method for Competitive Game Play Design](http://www.gamasutra.com/view/feature/1733/rock_paper_scissors__a_method_for_.php)
## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Building the project
Project is bundled with the Gradle Wrapper
```
mvn clean install
```


## Running the tests
```
mvn clean verify
```

## Run the application
### Using java jar command
```
java -jar rock-paper-scissors-<version>.jar
```
### Using Docker
##### Build the Docker image
```
docker build -t rps2:1.1 .
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
