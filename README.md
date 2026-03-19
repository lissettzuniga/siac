#  SIAC – Sistema de Inventario de Artículos Coleccionables

## Overview

SIAC is a database-driven system designed to manage collectible items inventory, including products, categories, users, roles, and inventory movements.
The system supports different types of products (such as collectible cards, accessories, and sets) and allows tracking of stock changes through inventory movements.

---

## Objectives

* Manage collectible products with different characteristics
* Track inventory movements (entries, exits, adjustments)
* Implement role-based access control (RBAC)
* Maintain historical data for auditing purposes
* Provide a scalable and normalized database design

---

## Database Design

The database design follows normalization principles and is structured into:

### Security Module

* `USUARIO`
* `ROL`
* `PERMISO`
* `USUARIO_ROL` (with temporal validity)
* `ROL_PERMISO`

### Catalogs

* `CATEGORIA`
* `TIPO_PRODUCTO`
* `TIPO_MOVIMIENTO`
* `TIPO_CARTA`

### Core Module

* `PRODUCTO`
* `IMAGEN_PRODUCTO`
* `MOVIMIENTO_INVENTARIO`
* `BITACORA_MOVIMIENTO`

### Product Specialization

* `PRODUCTO_CARTA` (for collectible cards with attributes like attack, defense, level, etc.)

---

## Technologies

* Database Design (Relational Model)
* Git & GitHub
* (Future) Spring Boot / Java Backend

---


## Author

**Lissett Zuñiga Reyes**
Computer Engineering – UNAM
Passionate about software development, data structures, and database systems.

---

