# 🚀 Food Delivery Application

A modern, scalable food delivery platform built with **Spring Boot** and **Java 17**. Features real-time order tracking, secure JWT authentication, and a comprehensive API for restaurant management.

## ✨ Key Features

- 🔐 **Secure Authentication** - JWT-based auth with role-based access control
- 🍽️ **Restaurant Management** - Complete restaurant and menu management system
- 📱 **Order Processing** - Real-time order tracking with WebSocket updates
- 🚗 **Delivery System** - Live courier tracking and delivery management
- 💳 **Payment Integration** - Multiple payment methods with secure processing
- 📊 **Admin Dashboard** - Comprehensive admin tools and analytics

## 🛠️ Tech Stack

**Backend:**
- Java 17, Spring Boot 3.1.8, Spring Security
- PostgreSQL, H2 (testing), JPA/Hibernate
- JWT, WebSocket, MapStruct, Lombok
- Maven, Docker, Swagger/OpenAPI

**Testing:**
- JUnit 5, Mockito, Spring Boot Test
- Unit, Integration, and E2E test coverage

## 🚀 Quick Start

### Prerequisites
- Java 17+, Maven 3.6+, PostgreSQL 12+

### Setup
```bash
# Clone repository
git clone https://github.com/emirkorall/DeliveryApp.git
cd DeliveryApp

# Configure environment
cp env.example .env
# Edit .env with your database credentials

# Run with Docker
docker-compose up -d

# Or run locally
./mvnw spring-boot:run
```

**Access:**
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/actuator/health

## 📚 API Overview

### Core Endpoints
```
POST /api/auth/signup     - User registration
POST /api/auth/login      - User authentication
GET  /api/restaurants     - List restaurants
POST /api/orders          - Create order
GET  /api/orders/{id}     - Get order details
```

### Role-Based Access
- **Customer** - Order placement, tracking, reviews
- **Restaurant Owner** - Menu management, order processing
- **Driver** - Delivery management, location updates
- **Admin** - User management, system oversight

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run specific test suites
./mvnw test -Dtest=AuthControllerTest,UserControllerTest,OrderControllerTest
```

**Test Coverage:**
- Unit tests for service layer business logic
- Integration tests for API endpoints
- End-to-end tests for complete workflows

## 🔒 Security

- **JWT Authentication** with refresh tokens
- **Role-based Access Control** (RBAC)
- **Password Encryption** using BCrypt
- **Input Validation** and rate limiting
- **Environment-based Configuration** (no hardcoded secrets)

## 📊 Project Structure

```
src/main/java/com/emirkoral/deliveryapp/
├── auth/          # Authentication & JWT
├── config/        # Security & WebSocket config
├── order/         # Order management
├── restaurant/    # Restaurant operations
├── delivery/      # Delivery tracking
├── payment/       # Payment processing
├── user/          # User management
└── review/        # Rating system
```

## 🐳 Deployment

### Docker
```bash
docker build -t delivery-app .
docker-compose up -d
```

### Environment Variables
```bash
DB_URL=jdbc:postgresql://localhost:5432/delivery_app
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=your-base64-encoded-secret
```

## 👨‍💻 Author

**Emir Koral**
- GitHub: [@emirkorall](https://github.com/emirkorall)

---

⭐ **Star this repository if you find it helpful!**
