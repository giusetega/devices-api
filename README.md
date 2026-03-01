# 🚀 Devices API

## 📖 Overview

Spring Boot 3 REST API for managing devices.

Features: 
- CRUD operations 
- Full and partial updates 
- Business rule enforcement 
- - Input validation 
- Dockerized MySQL setup

------------------------------------------------------------------------

## 🛠 Tech Stack

-   Java 21
-   Maven 3.9
-   Spring Boot 3.2.4
-   Spring Web & Spring Data JPA
-   MySQL 
-   Hibernate (ddl-auto: update)
-   JUnit 5 + Mockito
-   Docker + Docker Compose
-   OpenAPI (Swagger UI)

------------------------------------------------------------------------

## 🗄 Database

Schema is automatically managed by Hibernate:

spring: jpa: hibernate: ddl-auto: update

-   Tables are created automatically if missing
-   Schema updates when entities change
-   No external migration tool is used

------------------------------------------------------------------------

## ▶ Running the Application

### 💻 Local

Create database:

CREATE DATABASE devices;

Run:

mvn spring-boot:run

------------------------------------------------------------------------

### 🐳 Docker (Recommended)

docker-compose up --build

API available at: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html

------------------------------------------------------------------------

## 📡 API Endpoints

-   POST /v1/devices
-   PUT /v1/devices/{id}
-   PATCH /v1/devices/{id}
-   GET /v1/devices
-   GET /v1/devices/{id}
-   DELETE /v1/devices/{id}

------------------------------------------------------------------------

## 📏 Business Rule

- Creation time cannot be updated.
- Name and brand properties cannot be updated if the device is in use.
- In use devices cannot be deleted.

------------------------------------------------------------------------

## 🧪 Testing

Run:

mvn test

Service layer → unit tested with Mockito\
Controller layer → slice tested with @WebMvcTest

------------------------------------------------------------------------

## ⚠ Known Limitations

-   ddl-auto: update not suitable for production
-   No migration tool (Flyway/Liquibase)
-   No authentication
-   No pagination or sorting
-   No monitoring or CI/CD pipeline

------------------------------------------------------------------------

## 🔮 Future Improvements

-   Introduce Flyway and use ddl-auto: validate
-   Add pagination and sorting with Pageable
-   Add integration tests
-   Add production profile separation
