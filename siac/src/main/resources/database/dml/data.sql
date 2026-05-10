-- =======================================================
-- 1) USUARIO
-- =======================================================
INSERT INTO usuario (
    nombre, ap_paterno, ap_materno, correo_electronico,
    contrasena, fecha_registro, fecha_desactivacion, activo
) VALUES
('Lissett', 'Zuñiga', 'Reyes', 'lissett@siac.com',
 '$2y$11$ujanc/zJZOaEPfEPRwuUHu/U4LhVMkk2ITW75//UMLodN9LU7aUwS',
 '2026-03-01 09:00:00', NULL, 1),

('Ana', 'Lopez', 'Martinez', 'ana.lopez@siac.com',
 '$2y$11$ujanc/zJZOaEPfEPRwuUHu/U4LhVMkk2ITW75//UMLodN9LU7aUwS',
 '2026-03-02 10:15:00', NULL, 1),

('Carlos', 'Ramirez', 'Soto', 'carlos.ramirez@siac.com',
 '$2y$11$ujanc/zJZOaEPfEPRwuUHu/U4LhVMkk2ITW75//UMLodN9LU7aUwS',
 '2026-03-03 11:20:00', NULL, 1),

('Mariana', 'Gomez', 'Torres', 'mariana.gomez@siac.com',
 '$2y$11$ujanc/zJZOaEPfEPRwuUHu/U4LhVMkk2ITW75//UMLodN9LU7aUwS',
 '2026-03-04 12:00:00', NULL, 1),

('Roberto', 'Hernandez', 'Diaz', 'roberto.hernandez@siac.com',
 '$2y$11$ujanc/zJZOaEPfEPRwuUHu/U4LhVMkk2ITW75//UMLodN9LU7aUwS',
 '2026-03-05 13:00:00', NULL, 1);
-- =========================================================
-- 2) ROL
-- =========================================================
INSERT INTO rol (nombre, descripcion, activo) VALUES
('ROLE_ADMIN', 'Administrador principal del sistema. Acceso completo a todas las funcionalidades de SIAC.', 1),
('ROLE_SUPERVISOR', 'Supervisa operaciones del inventario, reportes, productos, categorías y movimientos.', 1),
('ROLE_EMPLEADO', 'Usuario operativo encargado de registrar y administrar movimientos de inventario.', 1),
('ROLE_CLIENTE', 'Usuario con acceso limitado únicamente a consultas de productos y categorías.', 1),
('ROLE_AUDITOR', 'Usuario encargado de revisar reportes, movimientos y bitácoras en modo lectura.', 1);

-- =========================================================
-- 3) PERMISO
-- =========================================================
INSERT INTO permiso (accion, recurso, activo) VALUES
('READ', 'DASHBOARD', 1),

('CREATE', 'USUARIO', 1),
('READ', 'USUARIO', 1),
('UPDATE', 'USUARIO', 1),
('DELETE', 'USUARIO', 1),

('CREATE', 'ROL', 1),
('READ', 'ROL', 1),
('UPDATE', 'ROL', 1),
('DELETE', 'ROL', 1),

('CREATE', 'PERMISO', 1),
('READ', 'PERMISO', 1),
('UPDATE', 'PERMISO', 1),
('DELETE', 'PERMISO', 1),

('CREATE', 'PRODUCTO', 1),
('READ', 'PRODUCTO', 1),
('UPDATE', 'PRODUCTO', 1),
('DELETE', 'PRODUCTO', 1),

('CREATE', 'CATEGORIA', 1),
('READ', 'CATEGORIA', 1),
('UPDATE', 'CATEGORIA', 1),
('DELETE', 'CATEGORIA', 1),

('CREATE', 'MOVIMIENTO_INVENTARIO', 1),
('READ', 'MOVIMIENTO_INVENTARIO', 1),
('UPDATE', 'MOVIMIENTO_INVENTARIO', 1),
('DELETE', 'MOVIMIENTO_INVENTARIO', 1),

('READ', 'BITACORA_MOVIMIENTO', 1),
('READ', 'REPORTE', 1),
('READ', 'IMAGEN_PRODUCTO', 1),
('CREATE', 'IMAGEN_PRODUCTO', 1),
('UPDATE', 'IMAGEN_PRODUCTO', 1),
('DELETE', 'IMAGEN_PRODUCTO', 1);

-- =========================================================
-- 4) CATEGORIA
-- =========================================================
INSERT INTO categoria (
    nombre, descripcion, activo
) VALUES
('Cartas', 'Productos coleccionables tipo carta', 1),
('Figuras', 'Figuras de colección', 1),
('Accesorios', 'Accesorios para productos coleccionables', 1);


-- =========================================================
-- 6) TIPO_MOVIMIENTO
-- =========================================================
INSERT INTO tipo_movimiento (
    nombre, clave, descripcion, activo
) VALUES
('Entrada de producto', 'ENTRADA', 'Incrementa el stock del producto', 1),
('Salida de producto', 'SALIDA', 'Disminuye el stock del producto', 1);
-- ('AJUSTE', 'Corrección o ajuste de inventario', 1);

-- =========================================================
-- 7) TIPO_CARTA
-- =========================================================
INSERT INTO tipo_carta (
    nombre, descripcion, activo
) VALUES
('Pokemon', 'Carta del juego Pokemon TCG', 1),
('Yu-Gi-Oh', 'Carta del juego Yu-Gi-Oh!', 1),
('Magic', 'Carta del juego Magic: The Gathering', 1);

-- =========================================================
-- 8) USUARIO_ROL
-- =========================================================
INSERT INTO usuario_rol (
    id_usuario, id_rol, fecha_inicio, fecha_fin, activo
) VALUES
(1, 1, '2026-03-01 09:30:00', NULL, 1), -- Lissett -> ROLE_ADMIN
(2, 2, '2026-03-02 10:30:00', NULL, 1), -- Ana -> ROLE_SUPERVISOR
(3, 3, '2026-03-03 11:45:00', NULL, 1), -- Carlos -> ROLE_EMPLEADO
(4, 4, '2026-03-04 12:30:00', NULL, 1), -- Mariana -> ROLE_CLIENTE
(5, 5, '2026-03-05 13:30:00', NULL, 1); -- Roberto -> ROLE_AUDITOR

-- =========================================================
-- 9) ROL_PERMISO
-- =========================================================
INSERT INTO rol_permiso (id_permiso, id_rol, activo) VALUES
-- ROLE_ADMIN: todos los permisos
(1,1,1),(2,1,1),(3,1,1),(4,1,1),(5,1,1),
(6,1,1),(7,1,1),(8,1,1),(9,1,1),
(10,1,1),(11,1,1),(12,1,1),(13,1,1),
(14,1,1),(15,1,1),(16,1,1),(17,1,1),
(18,1,1),(19,1,1),(20,1,1),(21,1,1),
(22,1,1),(23,1,1),(24,1,1),(25,1,1),
(26,1,1),(27,1,1),(28,1,1),(29,1,1),
(30,1,1),

-- ROLE_SUPERVISOR
(1,2,1),
(14,2,1),(15,2,1),(16,2,1),(17,2,1),
(18,2,1),(19,2,1),(20,2,1),(21,2,1),
(22,2,1),(23,2,1),(24,2,1),(25,2,1),
(27,2,1),
(28,2,1),(29,2,1),(30,2,1),

-- ROLE_EMPLEADO
(1,3,1),
(14,3,1),(15,3,1),(16,3,1),(17,3,1),
(18,3,1),(19,3,1),(20,3,1),(21,3,1),
(22,3,1),(23,3,1),(24,3,1),(25,3,1),
(28,3,1),(29,3,1),(30,3,1),

-- ROLE_CLIENTE: solo lectura
(1,4,1),
(15,4,1),
(19,4,1),
(28,4,1),

-- ROLE_AUDITOR: solo lectura
(1,5,1),
(11,5,1),
(13,5,1),
(15,5,1),
(19,5,1),
(23,5,1),
(26,5,1),
(27,5,1),
(28,5,1);
-- =========================================================
-- 10) PRODUCTO
-- =========================================================
INSERT INTO producto (
    nombre, descripcion, precio, cantidad_actual,
    id_usuario, id_categoria,
    fecha_creacion, activo
) VALUES
('Pikachu V', 'Carta coleccionable Pikachu V', 150.00, 10, 1, 1, '2026-03-05 12:00:00', 1),
('Charizard GX', 'Carta coleccionable Charizard GX', 450.00, 5, 2,1, '2026-03-05 12:20:00', 1),
('Figura Goku', 'Figura de colección de Goku', 799.00, 3, 2, 2, '2026-03-06 13:00:00', 1),
('Protector Acrilico', 'Protector transparente para carta', 45.00, 25, 3, 3, '2026-03-06 13:30:00', 1),
('Carta Mago Oscuro', 'Carta Yu-Gi-Oh! Mago Oscuro', 220.00, 7, 3, 1, '2026-03-07 14:00:00', 1);

-- =========================================================
-- 11) MOVIMIENTO_INVENTARIO
-- =========================================================
INSERT INTO movimiento_inventario (
    id_producto, id_tipo_movimiento, cantidad, fecha,
    id_usuario, comentario, activo
) VALUES
(1, 1, 10, '2026-03-05 12:05:00', 1, 'Ingreso inicial de Pikachu V', 1),
(2, 1, 5,  '2026-03-05 12:25:00', 2, 'Ingreso inicial de Charizard GX', 1),
(3, 1, 3,  '2026-03-06 13:05:00', 2, 'Ingreso inicial de Figura Goku', 1),
(4, 1, 25, '2026-03-06 13:35:00', 1, 'Ingreso inicial de protectores', 1),
(5, 1, 7,  '2026-03-07 14:05:00', 3, 'Ingreso inicial de Mago Oscuro', 1),
(1, 2, 1,  '2026-03-08 16:00:00', 2, 'Venta de una carta Pikachu V', 1);

-- =========================================================
-- 12) BITACORA_MOVIMIENTO
-- =========================================================
INSERT INTO bitacora_movimiento (
    id_usuario, entidad, accion, descripcion, fecha
) VALUES
(1, 'USUARIO', 'CREAR', 'Alta del usuario Lissett Zuñiga Reyes', '2026-03-01 09:00:00'),
(1, 'ROL', 'CREAR', 'Registro del rol ROLE_ADMIN', '2026-03-01 09:10:00'),
(1, 'PERMISO', 'CREAR', 'Registro del permiso CREATE sobre PRODUCTO', '2026-03-01 09:15:00'),
(1, 'PRODUCTO', 'CREAR', 'Alta del producto Pikachu V', '2026-03-05 12:00:00'),
(2, 'PRODUCTO', 'CREAR', 'Alta del producto Charizard GX', '2026-03-05 12:20:00'),
(2, 'MOVIMIENTO_INVENTARIO', 'CREAR', 'Ingreso inicial del producto Pikachu V', '2026-03-05 12:05:00'),
(2, 'MOVIMIENTO_INVENTARIO', 'DESACTIVAR', 'Salida por venta del producto Pikachu V', '2026-03-08 16:00:00'),
(3, 'PRODUCTO', 'CREAR', 'Alta del producto Carta Mago Oscuro', '2026-03-07 14:00:00');
-- =========================================================
-- 13) IMAGEN_PRODUCTO
-- =========================================================
INSERT INTO imagen_producto (
    id_producto, ruta, nombre_archivo, fecha_registro, activo
) VALUES
(1, '/img/productos/pikachu-v-front.jpg', 'pikachu-v-front.jpg', '2026-03-05 12:10:00', 1),
(1, '/img/productos/pikachu-v-back.jpg', 'pikachu-v-back.jpg', '2026-03-05 12:11:00', 1),
(2, '/img/productos/charizard-gx.jpg', 'charizard-gx.jpg', '2026-03-05 12:30:00', 1),
(3, '/img/productos/figura-goku.jpg', 'figura-goku.jpg', '2026-03-06 13:10:00', 1),
(5, '/img/productos/mago-oscuro.jpg', 'mago-oscuro.jpg', '2026-03-07 14:10:00', 1);

-- =========================================================
-- 14) PRODUCTO_CARTA
-- Solo para productos cuyo tipo_producto = Carta
-- =========================================================
INSERT INTO producto_carta (
    id_producto, id_tipo_carta, atributo, ataque, defensa, nivel, activo
) VALUES
(1, 1, 'Electrico', 120, 90, 4, 1),   -- Pikachu V
(2, 1, 'Fuego', 250, 200, 8, 1),      -- Charizard GX
(5, 2, 'Oscuridad', 2500, 2100, 7, 1); -- Mago Oscuro


