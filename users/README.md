# CartWorks Users Microservice

## Overview
The **Users Microservice** is a part of the CartWorks platform that provides APIs for managing user accounts, roles, and auditing user activities. It is built using the Spring Boot framework and follows a microservices architecture. The microservice implements REST APIs for user CRUD operations, role management, and auditing capabilities, with features such as OpenAPI documentation and Docker deployment.

---

## Features

- **User Management**: Create, read, update, and delete user accounts.
- **Role Management**: Assign and manage roles for users.
- **Auditing**: Tracks changes to user data and identifies the creator/updater.
- **Validation**: Validates user input using annotations.
- **Error Handling**: Centralized exception handling for cleaner responses.
- **Profile-based Initialization**: Seed data for QA environment.
- **OpenAPI Documentation**: API documentation using Swagger.
- **Dockerized Deployment**: Packaged with Docker for easy deployment.

---

## Project Structure
```
users/
├── .mvn/                       # Maven wrapper files
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── cartworks/
│   │   │           └── users/
│   │   │               ├── audit/         # Auditing-related code
│   │   │               ├── config/        # Configuration classes
│   │   │               ├── controller/    # REST Controllers
│   │   │               ├── dto/           # Data Transfer Objects (DTOs)
│   │   │               ├── entity/        # Entity classes for JPA
│   │   │               ├── exception/     # Custom exception handling
│   │   │               ├── mapper/        # Mappers for DTO <-> Entity conversion
│   │   │               ├── repository/    # Spring Data JPA Repositories
│   │   │               ├── service/       # Business logic and service layer
│   │   │               └── UsersApplication.java # Main Spring Boot application class
│   ├── resources/
│   │   ├── application.yml    # Spring configuration file
│   ├── test/
├── Dockerfile                 # Docker image definition
├── mvnw                       # Maven wrapper script
├── mvnw.cmd                   # Maven wrapper script for Windows
└── pom.xml                    # Maven project file
```

---

## Prerequisites

- **Java 17** or higher
- **Maven**
- **Docker** (optional, for containerized deployment)

---

## Installation

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd users
   ```

2. **Build the Application**:
   ```bash
   ./mvnw clean package
   ```

3. **Run the Application**:
   ```bash
   java -jar target/users-0.0.1-SNAPSHOT.jar
   ```

4. **Run with Docker**:
   ```bash
   docker build -t cartworks-users .
   docker run -p 8080:8080 cartworks-users
   ```

---

## Configuration

### application.yml

Key configurations include:
- **Database**: H2 in-memory database is used by default.
- **Profiles**: Profiles for QA and production environments.

```yaml
spring:
  application:
    name: "users"
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

---

## API Documentation

The APIs are documented using **Swagger UI**. Access the API docs at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Key Endpoints

### User Management

- **Register User**: `POST /api/users/register`
- **Fetch User by Email**: `GET /api/users/fetch?email={email}`
- **Update User**: `PUT /api/users/{email}`
- **Delete User**: `DELETE /api/users/{email}`

### Role Management

- **Assign Roles**: Implemented via QA profile initialization.

### Metadata

- **Build Info**: `GET /api/users/build-info`
- **Java Version**: `GET /api/users/java-version`

---

## Development Notes

- **Auditing**: Tracks created/updated entities.
- **Error Handling**: Implements `GlobalExceptionHandler`.
- **Validation**: Ensures robust data input using annotations.

---

## Contributors

- **Itay Olivcovich** - *Developer*

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.
