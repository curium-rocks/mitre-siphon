[![codecov](https://codecov.io/gh/curium-rocks/mitre-siphon/branch/master/graph/badge.svg?token=70JSHSV2IY)](https://codecov.io/gh/curium-rocks/mitre-siphon)![GitHub](https://img.shields.io/github/license/curium-rocks/mitre-siphon)![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/curium-rocks/mitre-siphon?include_prereleases)
# Mitre-Siphon

Watches the NVD CVE JSON repositories and on updates publishes to a queue. Queue data is processsed and saved into a database for full text searching across a REST Api.


### Prerequisites

- [docker](https://docs.docker.com/desktop/) 
- [docker-compose](https://docs.docker.com/compose/) 


## Running the tests

Tests are run using docker-compose. 
```
docker-compose --file docker-compose.test.yml build
docker-compose --file docker-compose.test.yml run sut
```

## Getting Started
Run `docker-compose up` to launch the application. Once everything has started up you will be able to access the application at http://localost:8080/ the default credentials are test:test. Once you are logged in you will have access to the swagger ui to interact with the REST API. This will allow you to do paged multi term searches against the saved NVD CVE data.

![image](https://user-images.githubusercontent.com/8363252/106229649-9adac180-61b3-11eb-8aa9-614ebfb1dcf3.png)


## Built With

- [Spring Boot] (https://spring.io/projects/spring-boot)
- [Gradle] (https://gradle.org/)
- [Quartz] (https://www.quartz-scheduler.org/)
- [Kafka] (http://kafka.apache.org/)
- [PostgreSQL] (https://www.postgresql.org/)
- [FlyWay] (https://flywaydb.org/documentation/)
- [Hibernate] (https://hibernate.org/)
- [OpenAPI3] (https://swagger.io/blog/news/announcing-openapi-3-0/)


## Versioning

[SemVer](http://semver.org/) is used for versioning. For the versions available, see the [tags on this repository](https://github.com/curim-rocks/mitre-siphon/tags). 

## Todos
- Helm chart
- Increase test coverage and cases covered in tests
- Add a UI for searching the data
- Smarter spring active profile switching based on environment
- More documentation on methods to enrich the data available for javadoc task and OpenAPI3
- More validation of input


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
