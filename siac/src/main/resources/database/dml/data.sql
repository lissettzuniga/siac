-- =======================================================
-- 1) USUARIO
-- =========================================================
INSERT INTO usuario (
    nombre, ap_paterno, ap_materno, correo_electronico,
    fecha_registro, fecha_desactivacion, activo
) VALUES
('Lissett', 'Zuñiga', 'Reyes', 'lissett@siac.com', '2026-03-01 09:00:00', NULL, 1),
('Ana', 'Lopez', 'Martinez', 'ana.lopez@siac.com', '2026-03-02 10:15:00', NULL, 1),
('Carlos', 'Ramirez', 'Soto', 'carlos.ramirez@siac.com', '2026-03-03 11:20:00', NULL, 1);

-- =========================================================
-- 2) ROL
-- =========================================================
INSERT INTO rol (
    nombre, descripcion, activo
) VALUES
('ADMIN', 'Administrador general del sistema', 1),
('CAPTURISTA', 'Usuario encargado de registrar productos y movimientos', 1),
('CONSULTA', 'Usuario con permisos de solo consulta', 1);

-- =========================================================
-- 3) PERMISO
-- =========================================================
INSERT INTO permiso (
    accion, recurso, activo
) VALUES
('INSERT', 'USUARIO', 1),
('UPDATE', 'USUARIO', 1),
('INSERT', 'PRODUCTO', 1),
('UPDATE', 'PRODUCTO', 1),
('INSERT', 'MOVIMIENTO_INVENTARIO', 1),
('UPDATE', 'MOVIMIENTO_INVENTARIO', 1),
('READ', 'PRODUCTO', 1),
('READ', 'CATEGORIA', 1);

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
-- 5) TIPO_PRODUCTO
-- =========================================================
INSERT INTO tipo_producto (
    nombre_tipo, descripcion, activo
) VALUES
('Carta', 'Producto individual tipo carta', 1),
('Figura', 'Producto tipo figura coleccionable', 1),
('Accesorio', 'Producto complementario o accesorio', 1);

-- =========================================================
-- 6) TIPO_MOVIMIENTO
-- =========================================================
INSERT INTO tipo_movimiento (
    nombre, descripcion, activo
) VALUES
('ENTRADA', 'Ingreso de inventario', 1),
('SALIDA', 'Salida de inventario', 1),
('AJUSTE', 'Corrección o ajuste de inventario', 1);

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
(1, 1, '2026-03-01 09:30:00', NULL, 1), -- Lissett -> ADMIN
(2, 2, '2026-03-02 10:30:00', NULL, 1), -- Ana -> CAPTURISTA
(3, 3, '2026-03-03 11:45:00', NULL, 1); -- Carlos -> CONSULTA

-- =========================================================
-- 9) ROL_PERMISO
-- =========================================================
INSERT INTO rol_permiso (
    id_permiso, id_rol, activo
) VALUES
-- ADMIN
(1, 1, 1),
(2, 1, 1),
(3, 1, 1),
(4, 1, 1),
(5, 1, 1),
(6, 1, 1),
(7, 1, 1),
(8, 1, 1),

-- CAPTURISTA
(3, 2, 1),
(4, 2, 1),
(5, 2, 1),
(6, 2, 1),
(7, 2, 1),
(8, 2, 1),

-- CONSULTA
(7, 3, 1),
(8, 3, 1);

-- =========================================================
-- 10) PRODUCTO
-- =========================================================
INSERT INTO producto (
    nombre, descripcion, precio, cantidad_actual,
    id_usuario, id_tipo_producto, id_categoria,
    fecha_creacion, activo
) VALUES
('Pikachu V', 'Carta coleccionable Pikachu V', 150.00, 10, 1, 1, 1, '2026-03-05 12:00:00', 1),
('Charizard GX', 'Carta coleccionable Charizard GX', 450.00, 5, 2, 1, 1, '2026-03-05 12:20:00', 1),
('Figura Goku', 'Figura de colección de Goku', 799.00, 3, 2, 2, 2, '2026-03-06 13:00:00', 1),
('Protector Acrilico', 'Protector transparente para carta', 45.00, 25, 1, 3, 3, '2026-03-06 13:30:00', 1),
('Carta Mago Oscuro', 'Carta Yu-Gi-Oh! Mago Oscuro', 220.00, 7, 3, 1, 1, '2026-03-07 14:00:00', 1);

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
    id_usuario, entidad, id_entidad, accion, descripcion, fecha
) VALUES
(1, 'USUARIO', 1, 'INSERT', 'Alta del usuario Lissett Zuñiga Reyes', '2026-03-01 09:00:00'),
(1, 'ROL', 1, 'INSERT', 'Registro del rol ADMIN', '2026-03-01 09:10:00'),
(1, 'PERMISO', 3, 'INSERT', 'Registro del permiso INSERT sobre PRODUCTO', '2026-03-01 09:15:00'),
(1, 'PRODUCTO', 1, 'INSERT', 'Alta del producto Pikachu V', '2026-03-05 12:00:00'),
(2, 'PRODUCTO', 2, 'INSERT', 'Alta del producto Charizard GX', '2026-03-05 12:20:00'),
(2, 'MOVIMIENTO_INVENTARIO', 1, 'INSERT', 'Ingreso inicial del producto Pikachu V', '2026-03-05 12:05:00'),
(2, 'MOVIMIENTO_INVENTARIO', 6, 'INSERT', 'Salida por venta del producto Pikachu V', '2026-03-08 16:00:00'),
(3, 'PRODUCTO', 5, 'INSERT', 'Alta del producto Carta Mago Oscuro', '2026-03-07 14:00:00');

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


