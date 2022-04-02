[![License](http://img.shields.io/github/license/BureauTech/Cadastrol-Server)](https://github.com/BureauTech/Cadastrol-Server/blob/main/LICENSE)
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/BureauTech/Cadastrol-Server/)
[![Java CI](https://github.com/BureauTech/Cadastrol-Server/actions/workflows/maven.yml/badge.svg)](https://github.com/BureauTech/Cadastrol-Server/actions/workflows/maven.yml)
[![Docker Image CI](https://github.com/BureauTech/Cadastrol-Server/actions/workflows/docker-image.yml/badge.svg)](https://github.com/BureauTech/Cadastrol-Server/actions/workflows/docker-image.yml)

# Cadastrol-Server

## How to install

### Docker Image

To make it run on your machine without installing everything locally, you can simply run it via docker image, by following the commands below.

```docker compose up```

NOTE: It's important to have [docker installed](https://docs.docker.com/engine/install/) on your machine.

### Locally

In case you want to run it in your machine without using docker, you must run the following commands.

For development environment:

```bash setup-env.sh```

```mvnw spring-boot:run```

For production environment:

```bash setup-env.sh docker```

```mvnw clean package -DskipTests && cp target/server-*.jar app.jar```


### Prerequisites:

You must have installed [Java 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) and [Postgres 12.x](https://www.postgresql.org/download/) previously.
