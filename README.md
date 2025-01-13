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
  - Rate limiter configuration using Resilience4j for `/api/java-version` endpoints:
    ```yaml
    resilience4j:
      ratelimiter:
        configs:
          default:
            timeout-duration: 1000
            limit-refresh-period: 5000
            limit-for-period: 1
    ```
  - Accessible endpoints:
    - `http://localhost:8080/api/java-version`
    - `http://localhost:8072/cartworks/users/api/java-version`
- **Product Catalog**:
  - Add, update, delete, and view products.
  - Debug messages for correlation IDs for improved logging and traceability.
- **Order Management**:
  - Create and manage customer orders.
  - Integrates FeignClient for inter-service communication to fetch full order details from the Product Service.
  - Circuit breaker pattern implemented with FeignClient for resilient communication with the Product Service.
  - Accessible endpoints:
    - `http://localhost:9000/api/OrdersFullDetailsDto/{email}`
    - `http://localhost:8072/cartworks/orders/api/OrdersFullDetailsDto/{email}`
- **Service Discovery**:
  - Eureka server integration for microservice registration and discovery.
- **Centralized Configuration**:
  - Config server for managing shared and environment-specific configurations.
- **Gateway Routing**:
  - Gateway server to handle routing and API aggregation.
  - Circuit breaker pattern implemented for resilient routing and fault tolerance.
  - Retry pattern implemented for enhanced fault tolerance:
    - Example route for **User Service**:
      ```java
      .route("users_route", r -> r
          .path("/cartworks/users/**")
          .filters(f -> f.rewritePath("/cartworks/users/(?<segment>.*)", "/${segment}")
              .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
              .retry(retryConfig -> retryConfig.setRetries(3)
                  .setMethods(HttpMethod.GET)
                  .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
          .uri("lb://USERS"))
      ```
  - Rate limiter pattern implemented for controlled request flow:
    - Example route for **Product Service**:
      ```java
      .route("products_route", r -> r
          .path("/cartworks/products/**")
          .filters(f -> f.rewritePath("/cartworks/cards/(?<segment>.*)", "/${segment}")
              .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
              .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                  .setKeyResolver(userKeyResolver())))
          .uri("lb://PRODUCTS"))
      ```
    - Replenish Rate: 1 token per second.
    - Burst Capacity: 1 token.
    - Requested Tokens: 1 per request.

  
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

