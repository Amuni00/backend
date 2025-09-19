# Project Management System

A **Project Management System** built with **Spring Boot** for the backend and **Angular** for the frontend. This application allows managing users, roles, and tasks with JWT-based authentication and role-based authorization.

---

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Backend Structure](#backend-structure)
- [Frontend Structure](#frontend-structure)
- [Authentication & Security](#authentication--security)
- [Setup & Installation](#setup--installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Future Enhancements](#future-enhancements)
- [License](#license)
- [Author](#author)

---

## Features
- **User Management:** Register, update, delete users.
- **Role Management:** Define roles such as Admin, Manager, Support.
- **Task Management:** Create, assign, update, complete, and delete tasks.
- **Authentication:** JWT-based login and secure endpoints.
- **Validation:** Input validation for users and tasks.
- **Exception Handling:** Global exception handling for validation and errors.
- **Role-based Authorization:** Access control based on user roles.

---

## Technologies Used
- **Backend:** Java, Spring Boot, Spring Security, JPA/Hibernate, MySQL
- **Frontend:** Angular, HTML, CSS, TypeScript
- **Authentication:** JWT (JSON Web Tokens)
- **Build Tools:** Maven, Node.js, npm
- **Others:** Lombok, Jackson

---

## Architecture
Angular Frontend
       │
       ▼
REST Controllers (UserController, TaskController, etc.)
       │
       ▼
Service Layer (Business Logic)
       │
       ▼
Repository Layer (JPA Repositories)
       │
       ▼
Database (MySQL)

---

## Key Components:
- **Entity:** Maps Java classes to database tables (UserEntity, Role, Task).
- **DTO (Data Transfer Object):** Objects for transferring data between frontend and backend.
- **Repository:** Interfaces to interact with the database.
- **Service:** Contains business logic and transaction management.
- **Controller:** Exposes REST APIs for frontend consumption.
- **Validation:** Ensures correct user and task inputs.
- **Exception Handling:** Custom exceptions and global handlers.
- **Security:** JWT authentication, authorization, password encoding.

---

## Backend Structure
---
src/main/java/com/backend/backend/
├─ config/                # Security and JWT configuration
├─ controller/            # REST Controllers
├─ dto/                   # Data Transfer Objects
├─ entity/                # JPA Entity classes
├─ exception/             # Custom exception classes
├─ repositories/          # JPA repositories
├─ service/               # Service layer (business logic)
├─ validation/            # Input validations
└─ Application.java       # Main Spring Boot application

---

## Frontend Structure
src/app/
├─ components/            # Angular components for UI
├─ services/              # Services for API calls
├─ models/                # TypeScript interfaces / models
├─ app-routing.module.ts  # Angular routing
└─ app.module.ts          # Angular main module

---

## Authentication & Security
- Users login via `/api/auth/login-user`.
- JWT is issued upon successful login.
- JWT is sent in the `Authorization: Bearer <token>` header for subsequent requests.
- **Roles:**
  - **Admin:** Manage users and roles
  - **Manager:** Manage tasks
  - **Support:** Assigned tasks
- Passwords are stored in **BCrypt hashed form**.
- Security filter validates tokens for every request.

---

## Setup & Installation

### Backend
1. Clone the repository:
   ```bash
   git clone <repository-url>

2. MySQL database in application.properties:
   ```java
   spring.datasource.url=jdbc:mysql://localhost:3306/mydb
   spring.datasource.username=root
   spring.datasource.password=65W8@BALRtxkB9g
   spring.jpa.hibernate.ddl-auto=update

4. Build and run the Spring Boot application:
   ```bash
   mvn clean install
   mvn spring-boot:run

---

### Frontend
1. Navigate to the Angular project:
   ```bash
   cd frontend

2. Install dependencies:
   ```bash
   npm install

4. Run the frontend:
   ```bash
   ng serve

5. Open Browser:
http://localhost:4200

---

### Usage
1. **Login**: Use Admin credentials to log in.
2. **User Management**: Admin can create, update, delete users.
3. **Role Management**: Admin can assign roles to users.
4. **Task Management**: Managers can create and assign tasks to Support users.
5. **Task Completion**: Support users can update the status of assigned tasks.
6. **Validation**: Invalid inputs are handled gracefully with clear messages.

---

### API Endpoints:
Authentication
- **POST /api/auth/login-user** → Login with username and password
---

User Management (Admin only)
- **POST /api/admin/register-user** → Register a user
- **GET /api/admin/get-users** → List all users
- **PUT /api/admin/update-user/{id}** → Update user by ID
- **DELETE /api/admin/delete-user/{id}** → Delete user by ID
- **GET /api/admin/get-support-users** → List all support users
---

Role Management (Admin only)
- **GET /api/admin/get-roles** → List all roles
- 
---
Task Management (Manager/Support)
- **POST /api/task/create-task** → Create a task
- **PUT /api/task/assign-task/{id}** → Assign task to support
- **GET /api/task/get-tasks** → Get all tasks
- **GET /api/task/get-assigned-task/{id}** → Get tasks assigned to a user
- **PUT /api/task/update-task/{id}** → Update task by ID
- **DELETE /api/task/delete-task/{id}** → Delete task
- **PUT /api/task/complete-task/{id}** → Mark task as completed

---

### Furter Enhancement
- Add frontend role-based dashboards.
- Implement notifications/email alerts for task assignments.
- Add file attachments for tasks.
- Integrate advanced reporting and analytics.
- Improve unit and integration testing.

---

### Licence

This project is open-source and available under the MIT License.

---

### Author

Amanuel Asfaw
Software Engineering Student | Ethiopia
Email: amanuelasfaw@example.com

---
