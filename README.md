# ☕ Chai Masala Backend Application

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MongoDB](https://img.shields.io/badge/MongoDB-Database-green.svg)](https://www.mongodb.com/)
[![Maven](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)

A robust, backend-driven Spring Boot web application designed for a custom tea shop. The application exposes REST APIs and controllers to manage product catalogs, handle order placements, track shipping statuses, and maintain secure user authentication using local credentials and Google OAuth2. It also includes an ingredient relationship service powered by the **JGraphT** library.

---

## ⚙️ Backend Features

*   **🔐 Authentication & Authorization (Spring Security)**:
    *   Secure local user registration and login using BCrypt password hashing.
    *   OAuth2 integration for Google sign-in.
    *   Role-based authorization restricting administrator actions (such as viewing all orders and updating statuses) to authorized users.
*   **📦 Order & Stock Management**:
    *   Business logic to place orders, calculate total bills, and dynamically update inventory stock.
    *   Prevents orders when requested quantities exceed available warehouse stock.
    *   Visual multi-stage order tracking support (e.g. `PLACED`, `PROCESSING`, `SHIPPED`, `DELIVERED`, `CANCELLED`).
*   **📊 Chai Ingredients Graph Service (JGraphT)**:
    *   Uses JGraphT to model a simple graph of Chai types (e.g., Masala Chai) and their core ingredients (e.g., Ginger, Cardamom) for queryable relationship mapping.
*   **⚠️ Global Exception Handling**:
    *   Centralized exception handling using `@ControllerAdvice` to return clean error messages for resource misses and validation errors.

---

## 🛠️ Tech Stack

*   **Language**: Java 21
*   **Framework**: Spring Boot 3.2.3 (Web, Security, Data MongoDB)
*   **Database**: MongoDB (NoSQL)
*   **Graph Processing**: JGraphT Core (v1.5.2)
*   **Build Tool**: Maven
*   **Boilerplate Reduction**: Lombok

---

## 📂 Project Architecture

```text
ChaiMasalaProject/
├── src/
│   ├── main/
│   │   ├── java/com/chaisharma/chaimasala/
│   │   │   ├── config/             # Security and utility configurations
│   │   │   ├── controller/         # Auth, Order, and Product HTTP endpoints
│   │   │   ├── DTO/                # Request & Response Data Transfer Objects
│   │   │   ├── exception/          # Custom exceptions & global exception handler
│   │   │   ├── model/              # MongoDB Document Models (User, Product, Order, Ingredient)
│   │   │   ├── repository/         # Spring Data MongoDB Repositories
│   │   │   ├── service/            # Order services, user services, recipe graph services
│   │   │   └── ChaiMasalaApplication.java
│   │   └── resources/
│   │       └── application.properties # Server port, MongoDB URI, and OAuth2 credentials
│   └── test/                       # Unit & integration tests
├── pom.xml                         # Dependency declaration file
└── .gitignore                      # Git ignore patterns
```

---

## 🚀 How to Run Locally

### 1. Prerequisites
- **JDK 21**
- **MongoDB** running on port `27017`

### 2. Configure application.properties
Configure your database properties in `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/chai_masala_db
server.port=8080
```

### 3. Build and Run
Execute the application using Maven:
```bash
mvn clean package
mvn spring-boot:run
```

---

## 🔌 API & HTTP Endpoints

### Authentication
*   `POST /register` - Register a new account
*   `GET /login` - Login endpoint

### Products & Cart
*   `GET /` - List all active products
*   `GET /cart` - Retrieve current cart items

### Orders
*   `POST /place-order` - Submit a new order via form POST
*   `POST /api/place-order` - Submit a new order via JSON request body
*   `GET /order-success?id={id}` - Retrieve details of a successfully placed order
*   `GET /order-tracking/{id}` - Retrieve real-time shipping tracking status for an order
*   `POST /order/cancel` - Cancel a pending order

### Admin Operations
*   `GET /admin/orders` - List all client orders
*   `POST /admin/update-status` - Transition order status (`PLACED` -> `PROCESSING` -> etc.)
