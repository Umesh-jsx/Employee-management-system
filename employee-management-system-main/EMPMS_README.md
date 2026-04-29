# 🚀 Employee Management System (EMPMS)

<p align="center">
  <b>Secure • Scalable • Production-Ready Spring Boot REST API</b>
</p>

<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-green?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Security-JWT-blue?style=for-the-badge&logo=auth0)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Build-Maven-red?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge)

</p>

---

## 📌 Overview

A Spring Boot REST API designed to manage Employees, Users, and Roles with JWT Authentication and Role-Based Access Control (RBAC).

---

## ✨ Features

- JWT Authentication
- Role-Based Authorization (ADMIN / USER)
- Secure REST APIs
- Clean Architecture
- MySQL + JPA Integration

---

## 🧱 Architecture

Controller → Service → Repository → Database  
Security Layer (JWT)

---

## ⚙️ Setup Instructions

### 1. Create Database
```sql
CREATE DATABASE emsdb;
```

### 2. Configure application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/emsdb?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your_secret_key
```

### 3. Set Environment Variable
```
set DB_PASSWORD=your_password
```

### 4. Run Application
```
mvnw.cmd spring-boot:run
```

App URL: http://localhost:8080

---

## 🔐 Authentication Flow

Create Role → Register → Login → Get Token → Access APIs

Header:
Authorization: Bearer <token>

---

## 🛡️ Roles

ADMIN → Full Access  
USER → Read Only

---

## 🌐 APIs

Public:
- POST /api/roles
- GET /api/roles
- POST /api/auth/register
- POST /api/auth/login

Protected:
- POST /api/employees (ADMIN)
- GET /api/employees (ADMIN, USER)
- PUT /api/employees (ADMIN)
- DELETE /api/employees (ADMIN)

---

## ⭐ Author

Vikas Potdar
