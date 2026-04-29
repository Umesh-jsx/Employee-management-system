# Employee Management System (EMPMS)

A REST API built with Spring Boot for managing employees, users, and roles with JWT-based authentication and role-based access control.

---

## Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 4.0.5 |
| Spring Security | 7.0.4 |
| Spring Data JPA | 7.x |
| MySQL | 8.0+ |
| JWT (jjwt) | 0.11.5 |
| Maven | 3.9.6 |

---

## Project Structure

```
empms-project/empms/src/main/java/com/example/empms/
├── config/
│   └── SecurityConfig.java          # Spring Security & JWT filter config
├── controller/
│   ├── AuthController.java          # Register & Login
│   ├── EmployeeController.java      # Employee CRUD (role-protected)
│   └── RoleController.java          # Role CRUD
├── dao/
│   ├── EmployeeRepository.java
│   ├── RoleRepository.java
│   └── UserRepository.java
├── entity/
│   ├── Employee.java
│   ├── Role.java
│   └── User.java
├── request/
│   ├── LoginRequest.java
│   └── RegisterRequest.java
├── security/
│   ├── CustomerDetailsService.java  # UserDetailsService implementation
│   └── JwtFilter.java               # JWT request filter
├── service/
│   ├── AuthService.java
│   └── EmployeeService.java
├── serviceimpl/
│   ├── AuthServiceImpl.java
│   └── EmployeeServiceImpl.java
└── util/
    └── JwtUtil.java                 # JWT generate & extract
```

---

## Database Setup

### 1. Create Database

```sql
CREATE DATABASE emsdb;
```

### 2. Tables (auto-created by JPA on startup)

| Table | Columns |
|-------|---------|
| `role` | id, name, created_at, updated_at |
| `users` | id, username, password, role_id, created_at, updated_at |
| `employee` | id, name, email, department, salary, created_at, updated_at |

---

## Configuration

File: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/emsdb?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
jwt.secret=${mysecrectkeyemployeemanagement}
```

Set environment variable before running:

```bash
# Windows Command Prompt
set DB_PASSWORD=your_mysql_password

# Windows PowerShell
$env:DB_PASSWORD = "your_mysql_password"
```

---

## How to Run

```bash
# Windows
set DB_PASSWORD=your_mysql_password
cd empms-project/empms
mvnw.cmd spring-boot:run
```

App starts at: `http://localhost:8080`

---

## Authentication

This API uses **JWT (JSON Web Token)** authentication.

- Public endpoints (no token needed): `/api/auth/**`, `/api/roles/**`
- Protected endpoints: require `Authorization: Bearer <token>` header

**How to get a token:**
1. Create a role → Register a user → Login → copy the JWT token from the response
2. Add to Postman: **Authorization tab → Bearer Token → paste token**

---

## Role-Based Access Control

| Role | Permissions |
|------|-------------|
| `ADMIN` | Full access to all employee operations (GET, POST, PUT, DELETE) |
| `USER` | Read-only access (GET only) |

---

## API Endpoints

### Base URL: `http://localhost:8080`

---

### 1. Role APIs — No Authentication Required

#### Create Role
```
POST /api/roles
Content-Type: application/json
```
**Request Body:**
```json
{ "name": "ADMIN" }
```
**Response:**
```
Role 'ADMIN' created successfully
```

---

#### Create USER Role
```
POST /api/roles
Content-Type: application/json
```
**Request Body:**
```json
{ "name": "USER" }
```

---

#### Get All Roles
```
GET /api/roles
```
**Response:**
```json
[
  { "id": 1, "name": "ADMIN", "createdAt": "2026-04-28T10:00:00", "updatedAt": "2026-04-28T10:00:00" },
  { "id": 2, "name": "USER",  "createdAt": "2026-04-28T10:00:00", "updatedAt": "2026-04-28T10:00:00" }
]
```

---

#### Get Role by ID
```
GET /api/roles/1
```
**Response:**
```json
{ "id": 1, "name": "ADMIN", "createdAt": "2026-04-28T10:00:00", "updatedAt": "2026-04-28T10:00:00" }
```

---

#### Update Role
```
PUT /api/roles/1
Content-Type: application/json
```
**Request Body:**
```json
{ "name": "SUPER_ADMIN" }
```
**Response:**
```json
{ "id": 1, "name": "SUPER_ADMIN", "createdAt": "2026-04-28T10:00:00", "updatedAt": "2026-04-28T10:05:00" }
```

---

#### Delete Role
```
DELETE /api/roles/1
```
**Response:**
```
Role deleted successfully
```

---

### 2. Auth APIs — No Authentication Required

#### Register User
```
POST /api/auth/register
Content-Type: application/json
```
**Request Body:**
```json
{
  "username": "admin",
  "password": "admin123",
  "roleName": "ADMIN"
}
```
**Response:**
```
User registered successfully
```

---

#### Login
```
POST /api/auth/login
Content-Type: application/json
```
**Request Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```
**Response:**
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0NTg1...
```
> Copy this token and use it as `Bearer <token>` for all protected endpoints.

---

### 3. Employee APIs — Authentication Required

> Add header: `Authorization: Bearer <your_jwt_token>`

---

#### Create Employee — ADMIN only
```
POST /api/employees
Authorization: Bearer <token>
Content-Type: application/json
```
**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "salary": 75000.00
}
```
**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "salary": 75000.0,
  "createdAt": "2026-04-28T10:10:00",
  "updatedAt": "2026-04-28T10:10:00"
}
```

---

#### Get All Employees — ADMIN & USER
```
GET /api/employees
Authorization: Bearer <token>
```
**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "department": "Engineering",
    "salary": 75000.0,
    "createdAt": "2026-04-28T10:10:00",
    "updatedAt": "2026-04-28T10:10:00"
  }
]
```

---

#### Get Employee by ID — ADMIN & USER
```
GET /api/employees/1
Authorization: Bearer <token>
```
**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "salary": 75000.0,
  "createdAt": "2026-04-28T10:10:00",
  "updatedAt": "2026-04-28T10:10:00"
}
```

---

#### Update Employee — ADMIN only
```
PUT /api/employees/1
Authorization: Bearer <token>
Content-Type: application/json
```
**Request Body:**
```json
{
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "department": "HR",
  "salary": 90000.00
}
```
**Response:**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "department": "HR",
  "salary": 90000.0,
  "createdAt": "2026-04-28T10:10:00",
  "updatedAt": "2026-04-28T10:15:00"
}
```

---

#### Delete Employee — ADMIN only
```
DELETE /api/employees/1
Authorization: Bearer <token>
```
**Response:** `200 OK` (no body)

---

## Complete API Summary

| # | Method | Endpoint | Auth | Role |
|---|--------|----------|------|------|
| 1 | POST | `/api/roles` | No | - |
| 2 | GET | `/api/roles` | No | - |
| 3 | GET | `/api/roles/{id}` | No | - |
| 4 | PUT | `/api/roles/{id}` | No | - |
| 5 | DELETE | `/api/roles/{id}` | No | - |
| 6 | POST | `/api/auth/register` | No | - |
| 7 | POST | `/api/auth/login` | No | - |
| 8 | POST | `/api/employees` | Yes | ADMIN |
| 9 | GET | `/api/employees` | Yes | ADMIN, USER |
| 10 | GET | `/api/employees/{id}` | Yes | ADMIN, USER |
| 11 | PUT | `/api/employees/{id}` | Yes | ADMIN |
| 12 | DELETE | `/api/employees/{id}` | Yes | ADMIN |

---

## Postman Testing — Step by Step

### Step 1: Create Roles
```
POST http://localhost:8080/api/roles
Body: { "name": "ADMIN" }

POST http://localhost:8080/api/roles
Body: { "name": "USER" }
```

### Step 2: Register Users
```
POST http://localhost:8080/api/auth/register
Body: { "username": "admin", "password": "admin123", "roleName": "ADMIN" }

POST http://localhost:8080/api/auth/register
Body: { "username": "viewer", "password": "viewer123", "roleName": "USER" }
```

### Step 3: Login & Get Token
```
POST http://localhost:8080/api/auth/login
Body: { "username": "admin", "password": "admin123" }
```
Copy the JWT token from the response.

### Step 4: Test Employee Endpoints
In Postman → **Authorization tab** → Type: **Bearer Token** → paste token.

```
POST   http://localhost:8080/api/employees   → Create (ADMIN only)
GET    http://localhost:8080/api/employees   → List all
GET    http://localhost:8080/api/employees/1 → Get by ID
PUT    http://localhost:8080/api/employees/1 → Update (ADMIN only)
DELETE http://localhost:8080/api/employees/1 → Delete (ADMIN only)
```

### Step 5: Test USER Role Restriction
Login as `viewer` → use that token → try `POST /api/employees` → returns **403 Forbidden**

---

## Sample Test Data

### Roles
```json
{ "name": "ADMIN" }
{ "name": "USER" }
```

### Users
```json
{ "username": "admin",   "password": "admin123",  "roleName": "ADMIN" }
{ "username": "viewer",  "password": "viewer123",  "roleName": "USER"  }
```

### Employees
```json
{ "name": "John Doe",      "email": "john@example.com",    "department": "Engineering", "salary": 75000 }
{ "name": "Jane Smith",    "email": "jane@example.com",    "department": "HR",          "salary": 65000 }
{ "name": "Bob Johnson",   "email": "bob@example.com",     "department": "Finance",     "salary": 80000 }
{ "name": "Alice Brown",   "email": "alice@example.com",   "department": "Marketing",   "salary": 70000 }
{ "name": "Charlie Davis", "email": "charlie@example.com", "department": "Engineering", "salary": 90000 }
```
