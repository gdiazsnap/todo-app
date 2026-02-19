# Todo App (Spring Boot + Thymeleaf + H2)

A minimal CRUD application for Todos using:
- Spring Boot 3
- Spring MVC (Thymeleaf views)
- Spring Data JPA (Hibernate)
- H2 in-memory database

## Requirements
- Java 17+

## Run (with Maven)
From the project root:
```bash
mvn spring-boot:run
```

Then open:
- App: http://localhost:8080/todos
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:todo-db
  - User: sa
  - Password: (empty)
