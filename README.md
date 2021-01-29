[![codecov](https://codecov.io/gh/akboyd88/mitre-siphon/branch/master/graph/badge.svg?token=70JSHSV2IY)](https://codecov.io/gh/akboyd88/mitre-siphon)![GitHub](https://img.shields.io/github/license/akboyd88/mitre-siphon)![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/akboyd88/mitre-siphon?include_prereleases)
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
TODO: add screenshots and instructions of how to launch and interact wtih the application.

## Deployment

TODO: will use a helm chart to deploy into a kubernetes cluster

## Built With

- [Spring Boot] (https://spring.io/projects/spring-boot)
- [Gradle] (https://gradle.org/)
- [Quartz] (https://www.quartz-scheduler.org/)
- [Kafka] (http://kafka.apache.org/)
- [PostgreSQL] (https://www.postgresql.org/)
- [FlyWay] (https://www.postgresql.org/)
- [Hibernate] (https://hibernate.org/)


## Versioning

[SemVer](http://semver.org/) is used for versioning. For the versions available, see the [tags on this repository](https://github.com/akboyd88/mitre-siphon/tags). 

## Todos
- Helm chart
- Increase test coverage and cases covered in tests
- Add a UI for searching the data
- Cleanup spring active profiles

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
