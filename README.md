# Sistema de Processos

Avaliação técnica de um Sistema de Processos, desenvolvido em Java com Spring-Boot e AngularJS.

Descrição da prova:

https://github.com/g-cpa-squad-produto/softplan-desafio-fullstack

## Características

- CRUD
- API RESTful
- ACL
- Validation
- Filter
- Pagination
- Login
- Responsive
- JUnit
- Mockito

## Requisitos

- Java JDK 14
- Apache Maven >= 3.6.3
- PostgreSQL >= 12
- Docker (Opcional)

## Tecnologias

- Java
- JPA
- Maven
- Spring
- Swagger
- CSS
- Jquery
- Bootstrap
- ThymeLeaf
- AngularJS
- PostgreSQL
- Docker

## Instalação

```
$ git clone https://github.com/danilomeneghel/softplan-desafio-fullstack-danilo-meneghel.git

$ cd softplan-desafio-fullstack-danilo-meneghel/
```

Crie o banco de dados com o nome sistema-processos em seu PostgreSQL.

Depois abra o arquivo "src/main/resources/application.properties" e coloque os dados de acordo com a conexão do seu banco de dados.

Para carregar o projeto, digite no terminal:

```
$ mvn spring-boot:run
```

Aguarde carregar todo o serviço web. <br>
Após concluído, abra o seu navegador o seguinte endereço: <br>

http://localhost:8080/

## Docker

```
docker-compose up --build
```

## Swagger 

Documentação da API RESTful: <br>

http://localhost:8080/swagger-ui.html

- Login
    - Username: admin
    - Password: admin

## Demonstração

http://sistema-processos.herokuapp.com/ <br>

- Login Administrador
    - Username: admin
    - Password: admin

- Login Triador
    - Username: bruna
    - Password: bruna

- Login Finalizador
    - Username: carlos
    - Password: carlos

    - Username: catia
    - Password: catia

## Licença

Projeto licenciado sob <a href="LICENSE">The MIT License (MIT)</a>.<br>

## Screenshots

![Screenshots](screenshots/screenshot01.png)<br><br>
![Screenshots](screenshots/screenshot02.png)<br><br>
![Screenshots](screenshots/screenshot03.png)<br><br>
![Screenshots](screenshots/screenshot04.png)<br><br>
![Screenshots](screenshots/screenshot05.png)<br><br>
![Screenshots](screenshots/screenshot06.png)<br><br>
![Screenshots](screenshots/screenshot07.png)<br><br>
![Screenshots](screenshots/screenshot08.png)<br><br>
![Screenshots](screenshots/screenshot09.png)<br><br>

## Testes

Para realizar os testes, crie o banco de dados com o nome "sistema-processos-test".
Após feito isso, execute o seguinte comando no terminal:

```
$ mvn test
```
![Screenshots](screenshots/test.png)<br><br>


Desenvolvido por<br>
Danilo Meneghel<br>
danilo.meneghel@gmail.com<br>
http://danilomeneghel.github.io/<br>
