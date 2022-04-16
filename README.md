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

# Authors

<table align="center">
  <tr>
    <td align="center"><a href="https://github.com/anaclaragraciano"><img src="https://avatars.githubusercontent.com/u/64653864?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Ana Clara<br>Dev</b></sub></a><br /><a href="https://github.com/BureauTech/Cadastrol-Server/commits?author=anaclaragraciano" title="PO">:sparkles::iphone::open_book:</a></td>
    <td align="center"><a href="https://github.com/bibiacoutinho"><img src="https://avatars.githubusercontent.com/u/56437723?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Beatriz Coutinho<br>Master</b></sub></a><br /><a href="https://github.com/BureauTech/Cadastrol-Server/commits?author=bibiacoutinho" title="Master">:headphones::nail_care::computer_mouse:</a></td>
    <td align="center"><a href="https://github.com/caiquesjc"><img src="https://avatars.githubusercontent.com/u/54915913?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Caique Nascimento<br>Dev</b></sub></a><br /><a href="https://github.com/BureauTech/Cadastrol-Server/commits?author=caiquesjc" title="Dev Team">:keyboard::desktop_computer::computer_mouse:</a></td>    
    <td align="center"><a href="https://github.com/charles-ramos"><img src="https://avatars.githubusercontent.com/u/25464287?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Charles Ramos<br>PO</b></sub></a><br /><a href="https://github.com/BureauTech/Cadastrol-Server/commits?author=charles-ramos" title="Dev Team">:fist_raised::open_book::hamburger:</a></td> 
</table>
<table align="center">
    <td align="center"><a href="https://github.com/danielsantosoliveira"><img src="https://avatars.githubusercontent.com/u/55162125?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Daniel Oliveira<br>Dev</b></sub></a><br /><a href="https://github.com/BureauTech/Cadastrol-Server/commits?author=danielsantosoliveira" title="Dev Team">:computer::guitar::soccer:</a></td>
    <td align="center"><a href="https://github.com/Denis-Lima"><img src="https://avatars.githubusercontent.com/u/55518511?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Denis Lima<br>Dev</b></sub></a><br /><a href="https://github.com/BureauTech/Cadastrol-Server/commits?author=Denis-Lima" title="Dev Team">:computer::v::pizza:</a></td>
    <td align="center"><a href="https://github.com/WeDias"><img src="https://avatars.githubusercontent.com/u/56437612?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Wesley Dias<br>Dev</b></sub></a><br /><a href="https://github.com/BureauTech/Cadastrol-Server/commits?author=WeDias" title="Dev Team">:rocket::milky_way::new_moon:</a></td>
  </tr>
</table>
