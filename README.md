# SIAC – Inventory Management System

SIAC is a full-stack inventory management system designed to manage collectible items such as Pokémon cards, accessories, structures, and inventory movements.

The application was developed using Spring Boot, Spring Security, JWT Authentication, MariaDB, and React, following RESTful API principles and a layered architecture approach.

---

# Features

## Authentication & Security
- JWT Authentication
- Access Token & Refresh Token
- Stateless Authentication with Spring Security
- Role-Based Authorization
- Protected API Endpoints
- BCrypt Password Encryption

## Inventory Management
- Product CRUD Operations
- Category Management
- Inventory Movement Control
- Logical Activation / Deactivation
- Stock Management

## Administration
- User Management
- Role & Permission Management
- User Role Assignment
- Access Control System

## Backend Architecture
- Layered Architecture
- DTO & Mapper Pattern
- Global Exception Handling
- Pageable Responses
- Audit Logging System
- Relational Database Modeling

## OCR Intelligent Search
- Product search using uploaded images
- Text extraction with Tesseract OCR
- Automatic product matching

## Frontend
- JWT Login Flow
- Protected Routes
- Dynamic Data Rendering
- Pagination & Filters
- Dashboard Interface

---

# Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- Maven
- MariaDB

## Frontend
- React
- Vite
- JavaScript
- CSS

## Tools & Technologies
- Git & GitHub
- Postman
- IntelliJ IDEA
- Tesseract OCR

---

# System Architecture

The project follows a layered architecture:

```text
Controller → Service → Repository → Database
```

Additional backend practices implemented:

- DTOs
- Mappers
- Validations
- Pageable Pagination
- Global Exception Handling
- Audit Logging
- Role-Based Authorization

---

# System Roles

| Role | Description |
|---|---|
| ADMIN | Full system access |
| SUPERVISOR | Inventory & movement management |
| EMPLEADO | Basic inventory operations |
| CLIENTE | Product visualization |
| AUDITOR | Audit log consultation |

---

# Main API Endpoints

## Authentication

```http
POST /api/auth/login
POST /api/auth/refresh
```

## Products

```http
GET /api/productos
POST /api/productos
PUT /api/productos/{id}
PATCH /api/productos/{id}/activate
PATCH /api/productos/{id}/deactivate
```

## Categories

```http
GET /api/categorias
POST /api/categorias
PUT /api/categorias/{id}
PATCH /api/categorias/{id}/activate
PATCH /api/categorias/{id}/deactivate
```

## Users

```http
GET /api/usuarios
POST /api/usuarios
PUT /api/usuarios/{id}
```

## Audit Logs

```http
GET /api/bitacora-movimientos
```

---

# Database Design

The application uses MariaDB with relational modeling between entities such as:

- Usuario
- Rol
- Permiso
- UsuarioRol
- RolPermiso
- Producto
- Categoria
- MovimientoInventario
- BitacoraMovimiento
- ImagenProducto
- TipoMovimiento
- TipoCarta
- ProductoCarta

---

# Run Locally

## Clone Repository

```bash
git clone https://github.com/lissettzuniga/siac.git
```

---

## Backend Setup

```bash
cd siac
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

## Frontend Setup

```bash
cd siac-frontend
npm install
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

# JWT Authentication Flow

1. User logs into the application
2. Spring Security validates credentials
3. Access Token & Refresh Token are generated
4. React stores authentication tokens
5. Protected requests use:

```text
Authorization: Bearer <token>
```

---

# OCR Intelligent Search

The application integrates Tesseract OCR for intelligent product searching.

## OCR Flow

1. User uploads an image
2. Tesseract extracts text from the image
3. The system searches matching registered products

---

# Future Improvements

- Password recovery via email
- PDF & Excel report generation
- Advanced analytics dashboard
- Cloud deployment
- Notification system
- Docker containerization
- Unit & integration testing

---

# Screenshots

> Add screenshots here:
- Login Page
- Dashboard
- Product Management
- OCR Search
- Inventory Movements
- Audit Logs

---

# Author

## Lissett Zuñiga Reyes

Computer Engineering – UNAM

Passionate about backend development, databases, software architecture, and scalable applications.

- GitHub: https://github.com/lissettzuniga
- LinkedIn: https://linkedin.com/in/lissett-zuniga-reyes

---

# License

Project developed for academic and learning purposes.

