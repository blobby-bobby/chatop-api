# Chatop

Chatop is a rental application enabling users to list their properties for rent. 
Users can easily communicate with property owners by sending messages directly through the platform when interested in a rental.

> This is the backend part of this project, made with [Java Spring](https://spring.io/) version 3.2.2

# Structure and dependencies

This API works with the following dependencies :

- **Spring Data JPA**: Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
- **PostgreSQL**: allows to connect to a PostgreSQL database using standard, database independent Java code.
- **Spring Security**: Highly customizable authentication and access-control framework for Spring applications. 
- **Spring Web** : Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
- **OpenAPI, Swagger V3** : API documentation with OpenAPI and Swagger UI. 
- **jjwt-api, jjwt-impl, jjwt-jackson**: Dependencies for JWT (JSON Web Token) authentication. 
- **Lombok** : Java annotation library which helps to reduce boilerplate code.
- **Mapstruct**: for the implementation of mappings between Java bean types.

# Get started

### Install the project
1. `git clone` the project
2. Run `mvn package / mvn install` to build the jar for the project.

### Setup the database

1. As it uses PostgreSQL, the database must be created as a `PostgreSQL` database.
2. Credentials must be set in the `application.yml` file.
3. Once steps 1 and 2 done and the server running, tables and dependencies will be set automatically 

### Server port

- Default port is : `3001`
- Run the server and navigate to `http://localhost:3001/` to test the endpoints

### API Documentation

You can check API documentation (made with `Swagger V3/Open API`) here : http://localhost:3001/api/swagger-ui/index.html#/

### Testing the Chatop API

Once the server `http://localhost:3001/` is running, endpoints can be tested :

- with a software like [Postman](https://www.postman.com/)
- or with the corresponding frontend part of the Chatop application, which can is available on this [Github repository](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)