# CartWorks - E-commerce Microservices Platform

## Overview
CartWorks is a microservices-based e-commerce platform designed to demonstrate and implement the core principles of distributed systems architecture. The platform is built using Java with Spring Boot, Spring Cloud, and Docker. It is a modular project with the following microservices:

- **User Service**: Handles user registration, authentication, and management.
- **Product Service**: Manages the product catalog, including CRUD operations for products.
- **Order Service**: Facilitates the creation and management of orders.
- **Gateway Server**: Acts as a unified entry point for the platform.
- **Config Server**: Centralized configuration management for all microservices.
- **Eureka Server**: Service discovery for managing the availability and location of microservices.

The platform also integrates RabbitMQ as a message broker to enable communication between microservices using Spring Cloud Bus.

---

## Features
- **User Management**: 
  - Registration and login.
  - Role-based authentication.
- **Product Catalog**:
  - Add, update, delete, and view products.
  - Debug messages for correlation IDs for improved logging and traceability.
- **Order Management**:
  - Create and manage customer orders.
- **Service Discovery**:
  - Eureka server integration for microservice registration and discovery.
- **Centralized Configuration**:
  - Config server for managing shared and environment-specific configurations.
- **Gateway Routing**:
  - Gateway server to handle routing and API aggregation.
- **Messaging**:
  - RabbitMQ integration for message-driven communication.

---

## Tech Stack
- **Backend**: Java, Spring Boot, Spring Cloud
- **Messaging**: RabbitMQ
- **Service Discovery**: Spring Cloud Eureka Server
- **Configuration Management**: Spring Cloud Config Server
- **Containerization**: Docker, Docker Compose

---

## Prerequisites
To run the project locally, ensure you have the following installed:
- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose

---

## Getting Started
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd CartWorks
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Start the services using Docker Compose:
   ```bash
   docker-compose up --build
   ```

4. Access the services:
   - **Gateway Server**: http://localhost:8080
   - **Eureka Server**: http://localhost:8761

---

## Development Workflow
### Profiles
The project supports multiple profiles for different environments. Update the `docker-compose.yml` file to set the desired profile.

### Debugging
- Add debug messages in services for better traceability.
- Use correlation IDs to track requests across microservices.

### Common Commands
- Restart a specific service:
  ```bash
  docker-compose up --build <service-name>
  ```

---

## Contributing
Contributions are welcome! Please submit a pull request or open an issue to discuss potential improvements.

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---

## Acknowledgments
This project was created as part of a Udemy course on microservices with Java and Spring Cloud.
