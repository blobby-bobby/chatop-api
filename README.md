# Chatop

Chatop is a rental application enabling users to list their properties for rent. 
Users can easily communicate with property owners by sending messages directly through the platform when interested in a rental.

> This is the backend part of this project, made with [Java Spring](https://spring.io/) version 3.2.2

# Structure and dependencies

This API works with the following dependencies :

- **Spring Data JPA**: Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
- **PostgreSQL**: allows to connect to a PostgreSQL database using standard, database independent Java code.
- **Spring Securityy**: Highly customizable authentication and access-control framework for Spring applications. 
- **Spring Web** : Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
- **OpenAPI, Swagger V3** : API documentation with OpenAPI and Swagger UI. 
- **jjwt-api, jjwt-impl, jjwt-jackson**: Dependencies for JWT (JSON Web Token) authentication. 
- **Lombok** : Java annotation library which helps to reduce boilerplate code.
- **mapstruct**: for the implementation of mappings between Java bean types.

# Setup

### Server port

- Default port is : `3001`

### Testing the Chatop API

Once the server `http://localhost:3001/` is running, endpoints can be tested :

- with a software like [Postman](https://www.postman.com/)
- or with the corresponding frontend part of the Chatop application, which can is available on this [Github repository](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)