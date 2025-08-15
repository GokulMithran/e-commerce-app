# E-Commerce Microservices Project

This project is a microservices-based e-commerce application built using **Java**, **Spring Boot**, **Maven**, and **SQL**. It consists of multiple services, including `order-service`, `customer-service`, and `product-service`, with configurations managed by a centralized `config-server`.

## Project Structure

### Services
1. **Order Service**:
   - Handles order-related operations.
   - Communicates with `customer-service` using Feign clients.
   - Secured with OAuth2 client credentials for service-to-service communication.

2. **Customer Service**:
   - Manages customer-related data.
   - Exposes APIs for retrieving customer information.

3. **Product Service**:
   - Manages product-related data.
   - Uses PostgreSQL as the database and Flyway for database migrations.

4. **Config Server**:
   - Centralized configuration management for all services.
   - Fetches configuration from external sources.

### Key Features
- **Spring Cloud Feign**: Used for inter-service communication.
- **OAuth2 Client Credentials**: Secures service-to-service communication.
- **PostgreSQL**: Database for storing product data.
- **Flyway**: Manages database migrations.
- **Spring Cloud Config**: Centralized configuration management.

## Prerequisites
- **Java 17** or higher
- **Maven 3.8+**
- **PostgreSQL** installed and running
- **Keycloak** for OAuth2 authentication
- **Docker** (optional, for containerized deployment)

## Configuration

### `application.yml` (Order Service)
- OAuth2 client credentials are configured for secure communication with `customer-service`.
- Example:
  ```yaml
  spring:
    security:
      oauth2:
        client:
          provider:
            keycloak:
              token-uri: http://localhost:9098/realms/micro-services/protocol/openid-connect/token
          registration:
            common-client:
              client-id: micro-services-api
              client-secret: OuGhrZdImzLDCfkzgGe5uWG7pknsMuMk
