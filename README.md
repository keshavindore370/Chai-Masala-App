# ☕ Chai Masala E-Commerce Platform

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MongoDB](https://img.shields.io/badge/MongoDB-Database-green.svg)](https://www.mongodb.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A premium, full-stack Spring Boot e-commerce web application designed for gourmet tea enthusiasts. **Chai Masala** allows users to browse unique tea blends, place customized orders with real-time stock management, track orders through a visual progress system, and authenticate securely using local credentials or Google OAuth2. It features an innovative ingredient relationship graph powered by **JGraphT** to visualize and analyze Chai recipe compositions.

---

## 🌟 Key Features

*   **🔐 Hybrid Authentication System**:
    *   Secure local sign-up and login with standard password hashing (BCrypt).
    *   Smooth **Google OAuth2** integration for social login.
    *   Role-based authorization restricting admin views (orders management) to authorized personnel.
*   **📊 Chai Recipe Graph (JGraphT)**:
    *   Models the relationships between different Chai types and their raw ingredients (e.g., Ginger, Cardamom) using a mathematical graph structure.
    *   Provides high-efficiency pathfinding and relationship analysis, laying the foundation for smart recommendations.
*   **🛒 E-Commerce & Cart Operations**:
    *   Interactive cart interface to add/remove products, adjust quantities, and select sizes/weights.
    *   Dynamic inventory tracking that automatically decrements product stock quantities on successful orders and prevents over-purchasing.
*   **🚚 Live Order Tracking & Admin Panel**:
    *   Real-time multi-stage status tracking (`PLACED`, `PROCESSING`, `SHIPPED`, `DELIVERED`, `CANCELLED`).
    *   Intuitive admin panel to list, filter, update delivery status, or cancel customer orders.
*   **🎨 Premium Responsive Frontend**:
    *   Fully styled with modern HTML5/CSS3 and **Thymeleaf** templates.
    *   Provides user profile settings, order success notifications, and custom dashboards.

---

## 🛠️ Tech Stack & Architecture

*   **Backend Core**: Java 21, Spring Boot 3.2.3 (Starter Web, Thymeleaf, Security)
*   **Security & OAuth**: Spring Security 6, Spring OAuth2 Client, Thymeleaf Spring Security Extras
*   **Database**: MongoDB (NoSQL) via Spring Data MongoDB
*   **Mathematical Modeling**: JGraphT Core (v1.5.2) for relational node graphs
*   **Utilities**: ModelMapper (Object-to-DTO conversion), Lombok (Boilerplate reduction)
*   **Build Tool**: Maven

---

## 📂 Project Structure

```text
ChaiMasalaProject/
├── src/
│   ├── main/
│   │   ├── java/com/chaisharma/chaimasala/
│   │   │   ├── config/             # Security configs, ModelMapper configurations
│   │   │   ├── controller/         # Auth, Order, and Product HTTP endpoints
│   │   │   ├── DTO/                # Request & Response Data Transfer Objects
│   │   │   ├── exception/          # Global Exception Handling and custom Exceptions
│   │   │   ├── model/              # MongoDB Document Models (User, Product, Order, Ingredient)
│   │   │   ├── repository/         # Spring Data MongoDB Repositories
│   │   │   ├── service/            # Business Logic (Order processing, Chai Graph relationships)
│   │   │   └── ChaiMasalaApplication.java
│   │   └── resources/
│   │       ├── templates/          # Thymeleaf HTML Views (index, cart, checkout, tracking)
│   │       └── application.properties # Application configurations
│   └── test/                       # Unit & Integration Tests (JUnit 5, Spring Boot Test)
├── pom.xml                         # Project dependencies and plugins
└── .gitignore                      # Git exclusion rules
```

---

## ⚙️ Local Setup Instructions

### Prerequisites
- **Java Development Kit (JDK)**: Version 21
- **MongoDB**: Installed and running locally (Default port `27017`)
- **Maven**: Version 3.8+

### 1. Clone the Repository
```bash
git clone https://github.com/keshavindore370/Chai-Masala-App.git
cd Chai-Masala-App
```

### 2. Configure Environment Variables
Open `src/main/resources/application.properties` and customize the configuration. If you intend to use Google OAuth2, fill in your Google Client ID and Secret:
```properties
# MongoDB Connection
spring.data.mongodb.uri=mongodb://localhost:27017/chai_masala_db

# Google OAuth2 Credentials
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email
```

### 3. Run the Application
You can run the application directly using the Maven Wrapper (if present) or your local Maven installation:
```bash
mvn spring-boot:run
```
The server will start on port `8080`. Open your browser and navigate to `http://localhost:8080`.

---

## 🔌 API Endpoints Summary

### User & Authentication
*   `GET /login` - Show login page
*   `GET /register` - Show registration page
*   `POST /register` - Register a new user
*   `GET /account` - User profile details

### Product Catalog
*   `GET /` - Home page containing active product listing
*   `GET /adrak-chai` - Detailed view of the signature Adrak Chai
*   `GET /cart` - Current user shopping cart

### Order Management
*   `POST /place-order` - Place order via form submission
*   `POST /api/place-order` - JSON API endpoint for order placement
*   `GET /order-success` - Order success details
*   `GET /order-tracking/{id}` - Track order status in real time
*   `POST /order/cancel` - Cancel a pending order

### Administration
*   `GET /admin/orders` - View all database orders
*   `POST /admin/update-status` - Update an order's status (`PLACED` -> `PROCESSING` -> etc.)
