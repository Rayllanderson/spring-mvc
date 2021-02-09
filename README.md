# Treinamento Spring MVC
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Rayllanderson/spring-mvc/blob/master/LICENSE) 

# Sobre o projeto

https://spring-mvc-rayllanderson.herokuapp.com


Aplicação criada com o intuito de treinar a arquitetura MVC utilizando o Spring Framework. A aplicação, resumindo, é uma agenda de contatos. Um usuário possui sua lista personalizada de contatos. Cada contato possui uma lista de telefones e endereços, em que pode amarrar vários números de telefones, vários endereços e ainda fazer upload de um arquivo (currículo, como contexto). Também é possível gerar um relatório em PDF da lista de contatos do usuário. 

## Layout web
<h3>Home</h3>

![Web 1](https://github.com/Rayllanderson/assets/blob/master/spring-mvc/home.png)

<h3>Tela de contatos</h3>

![Web 2](https://github.com/Rayllanderson/assets/blob/master/spring-mvc/contact%20table2.png)

<h3>Tela de Informações</h3>

![Web 2](https://github.com/Rayllanderson/assets/blob/master/spring-mvc/infos1.png)

<h3>Endereços</h3>

![Web 2](https://github.com/Rayllanderson/assets/blob/master/spring-mvc/infos2.png)

<h3>Currículo</h3>

![Web 2](https://github.com/Rayllanderson/assets/blob/master/spring-mvc/infos3.png)

## Layout mobile
<img src="https://github.com/Rayllanderson/assets/blob/master/spring-mvc/mobile/table.jpeg" width="48%"/>  <img src="https://github.com/Rayllanderson/assets/blob/master/spring-mvc/mobile/edit.jpeg" width="48%"/> 


## Modelo conceitual
![Modelo Conceitual](https://github.com/Rayllanderson/assets/blob/master/spring-mvc/modelo.png)

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- Spring Framework
- JPA / Hibernate
- Maven
- Jasper Reports
## Front end
- HTML / CSS / JS
- Thymeleaf
- Bootstrap
- JQuery
## Implantação em produção
- Aplicação: Heroku
- Banco de dados: Postgresql

# Como executar o projeto
Pré-requisitos: 
- Java 11
- Configurar banco de dados em spring-mvc/src/main/resources/application.properties

```bash
# clonar repositório
git clone https://github.com/Rayllanderson/spring-mvc.git

# entrar na pasta do projeto
cd spring-mvc

# executar o projeto
./mvnw spring-boot:run
```

# Autor

Rayllanderson Gonçalves Rodrigues
