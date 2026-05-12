# SIAC - Sistema de Inventario de Artículos Coleccionables

SIAC es un sistema web de gestión de inventario desarrollado para administrar artículos coleccionables como cartas Pokémon, accesorios, estructuras y sets.

El sistema permite registrar, consultar, actualizar y controlar inventario mediante una arquitectura RESTful segura utilizando Spring Boot, Spring Security con JWT y React.

---

# Tecnologías Utilizadas

## Backend
- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- Maven
- MariaDB

## Frontend
- React
- Vite
- JavaScript
- CSS

## Herramientas
- Git & GitHub
- Postman
- IntelliJ IDEA
- Tesseract OCR

---

# Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas:

```text
Controller → Service → Repository → Database
```

También se implementa el uso de:

- DTOs
- Mappers
- Validaciones
- Manejo global de excepciones
- Paginación con Pageable
- Auditoría de movimientos

---

# Funcionalidades Implementadas

## Seguridad
- Autenticación con JWT
- Access Token y Refresh Token
- Spring Security Stateless
- Roles y permisos
- Protección de endpoints
- BCryptPasswordEncoder

## Gestión de Inventario
- CRUD de productos
- CRUD de categorías
- Activación/desactivación lógica
- Control de stock
- Movimientos de inventario

## Administración
- Gestión de usuarios
- Gestión de roles
- Gestión de permisos
- Asignación de roles a usuarios

## Auditoría
- Bitácora de movimientos
- Registro de acciones:
  - CREAR
  - ACTUALIZAR
  - ACTIVAR
  - DESACTIVAR
  - ELIMINAR

## OCR
- Búsqueda de productos mediante imágenes
- Extracción de texto usando Tesseract OCR

## Frontend
- Login con JWT
- Dashboard
- Gestión visual de productos
- Paginación
- Filtros
- Protección de rutas

---

# Roles del Sistema

| Rol | Descripción |
|---|---|
| ADMIN | Control total del sistema |
| SUPERVISOR | Gestión de inventario y movimientos |
| EMPLEADO | Operaciones básicas |
| CLIENTE | Consulta de productos |
| AUDITOR | Consulta de bitácoras |

```

---

# Endpoints Principales

## Autenticación

```http
POST /api/auth/login
POST /api/auth/refresh
```

## Productos

```http
GET /api/productos
POST /api/productos
PUT /api/productos/{id}
PATCH /api/productos/{id}/activate
PATCH /api/productos/{id}/deactivate
```

## Categorías

```http
GET /api/categorias
POST /api/categorias
PUT /api/categorias/{id}
PATCH /api/categorias/{id}/activate
PATCH /api/categorias/{id}/deactivate
```

## Usuarios

```http
GET /api/usuarios
POST /api/usuarios
PUT /api/usuarios/{id}
```

## Bitácora

```http
GET /api/bitacora-movimientos
```

---

# Base de Datos

El sistema utiliza MariaDB con relaciones entre entidades como:

- Usuario
- Rol
- Permiso
- UsuarioRol
- RolPermiso
- Producto
- Categoría
- MovimientoInventario
- BitacoraMovimiento
- ImagenProducto
- TipoMovimiento
- TipoCarta
- ProductoCarta

---

# Cómo Ejecutar el Proyecto

## Clonar repositorio

```bash
git clone https://github.com/lissettzuniga/siac.git
```

---

## Backend

```bash
cd siac
mvn spring-boot:run
```

El backend se ejecutará en:

```text
http://localhost:8080
```

---

## Frontend

```bash
cd siac-frontend
npm install
npm run dev
```

El frontend se ejecutará en:

```text
http://localhost:5173
```

---

# Seguridad JWT

Flujo de autenticación:

1. El usuario inicia sesión
2. Spring Security valida credenciales
3. Se genera Access Token y Refresh Token
4. React almacena los tokens
5. Las peticiones protegidas utilizan:

```text
Authorization: Bearer <token>
```

---

# OCR con Tesseract

El sistema incluye búsqueda inteligente de productos mediante OCR.

## Proceso

1. El usuario sube una imagen
2. Tesseract OCR extrae texto
3. El sistema busca coincidencias en productos registrados

---

---

# Futuras Mejoras

- Recuperación de contraseña mediante correo electrónico
- Reportes en PDF y Excel
- Dashboard avanzado con gráficas
- Notificaciones automáticas
- Despliegue en la nube

---

# Autor

**Lissett Zuñiga Reyes**  
Ingeniería en Computación – UNAM  

Apasionada por el desarrollo de software, estructuras de datos y sistemas de bases de datos.

---

# Licencia

Proyecto desarrollado con fines académicos y de aprendizaje.

