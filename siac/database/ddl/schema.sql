CREATE DATABASE siac CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE siac;

CREATE TABLE usuario (
  id                   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre               VARCHAR(80) NOT NULL,
  ap_paterno           VARCHAR(80) NOT NULL,
  ap_materno           VARCHAR(80) NULL,
  correo_electronico   VARCHAR(120) NOT NULL,
  fecha_registro       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_desactivacion  DATETIME NULL,
  activo               TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT uq_usuario_correo UNIQUE (correo_electronico),
  CONSTRAINT chk_usuario_activo CHECK (activo IN (0,1)),
  CONSTRAINT chk_usuario_fechas CHECK (
    fecha_desactivacion IS NULL OR fecha_desactivacion >= fecha_registro
  )
);

CREATE TABLE rol (
  id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(80) NOT NULL,
  descripcion  VARCHAR(255) NULL,
  activo       TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT uq_rol_nombre UNIQUE (nombre),
  CONSTRAINT chk_rol_activo CHECK (activo IN (0,1))
);

CREATE TABLE permiso (
  id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  accion       VARCHAR(80) NOT NULL,
  recurso      VARCHAR(80) NOT NULL,
  activo       TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT uq_permiso_accion_recurso UNIQUE (accion, recurso),
  CONSTRAINT chk_permiso_activo CHECK (activo IN (0,1))
);

CREATE TABLE categoria (
  id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(80) NOT NULL,
  descripcion  VARCHAR(255) NULL,
  activo       TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT uq_categoria_nombre UNIQUE (nombre),
  CONSTRAINT chk_categoria_activo CHECK (activo IN (0,1))
);

CREATE TABLE tipo_producto (
  id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre_tipo  VARCHAR(50) NOT NULL,
  descripcion  VARCHAR(100) NULL,
  activo       TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT uq_tipo_producto_nombre UNIQUE (nombre_tipo),
  CONSTRAINT chk_tipo_producto_activo CHECK (activo IN (0,1))
);

CREATE TABLE tipo_movimiento (
  id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(50) NOT NULL,
  descripcion  VARCHAR(100) NULL,
  activo       TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT uq_tipo_movimiento_nombre UNIQUE (nombre),
  CONSTRAINT chk_tipo_movimiento_activo CHECK (activo IN (0,1))
);

CREATE TABLE tipo_carta (
  id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(50) NOT NULL,
  descripcion  VARCHAR(100) NULL,
  activo       TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT uq_tipo_carta_nombre UNIQUE (nombre),
  CONSTRAINT chk_tipo_carta_activo CHECK (activo IN (0,1))
);

CREATE TABLE producto (
  id                INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre            VARCHAR(120) NOT NULL,
  descripcion       VARCHAR(255) NULL,
  precio            DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  cantidad_actual   INT UNSIGNED NOT NULL DEFAULT 0,
  id_usuario        INT UNSIGNED NOT NULL,
  id_tipo_producto  INT UNSIGNED NOT NULL,
  id_categoria      INT UNSIGNED NULL,
  fecha_creacion    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  activo            TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT fk_producto_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),

  CONSTRAINT fk_producto_tipo_producto
    FOREIGN KEY (id_tipo_producto) REFERENCES tipo_producto(id),

  CONSTRAINT fk_producto_categoria
    FOREIGN KEY (id_categoria) REFERENCES categoria(id),

  CONSTRAINT chk_producto_precio CHECK (precio >= 0),
  CONSTRAINT chk_producto_cantidad CHECK (cantidad_actual >= 0),
  CONSTRAINT chk_producto_activo CHECK (activo IN (0,1))
);

CREATE TABLE usuario_rol (
  id            INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  id_usuario    INT UNSIGNED NOT NULL,
  id_rol        INT UNSIGNED NOT NULL,
  fecha_inicio  DATETIME NOT NULL,
  fecha_fin     DATETIME NULL,
  activo        TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT fk_usuario_rol_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),

  CONSTRAINT fk_usuario_rol_rol
    FOREIGN KEY (id_rol) REFERENCES rol(id),

  CONSTRAINT uq_usuario_rol UNIQUE (id_usuario, id_rol, fecha_inicio),
  CONSTRAINT chk_usuario_rol_activo CHECK (activo IN (0,1)),
  CONSTRAINT chk_usuario_rol_fechas CHECK (
    fecha_fin IS NULL OR fecha_fin >= fecha_inicio
  )
);

CREATE TABLE rol_permiso (
  id          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  id_permiso  INT UNSIGNED NOT NULL,
  id_rol      INT UNSIGNED NOT NULL,
  activo      TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT fk_rol_permiso_permiso
    FOREIGN KEY (id_permiso) REFERENCES permiso(id),

  CONSTRAINT fk_rol_permiso_rol
    FOREIGN KEY (id_rol) REFERENCES rol(id),

  CONSTRAINT uq_rol_permiso UNIQUE (id_permiso, id_rol),
  CONSTRAINT chk_rol_permiso_activo CHECK (activo IN (0,1))
);

CREATE TABLE movimiento_inventario (
  id                  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  id_producto         INT UNSIGNED NOT NULL,
  id_tipo_movimiento  INT UNSIGNED NOT NULL,
  cantidad            INT UNSIGNED NOT NULL,
  fecha               DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  id_usuario          INT UNSIGNED NOT NULL,
  comentario          VARCHAR(255) NULL,
  activo              TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT fk_movimiento_producto
    FOREIGN KEY (id_producto) REFERENCES producto(id),

  CONSTRAINT fk_movimiento_tipo
    FOREIGN KEY (id_tipo_movimiento) REFERENCES tipo_movimiento(id),

  CONSTRAINT fk_movimiento_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),

  CONSTRAINT chk_movimiento_cantidad CHECK (cantidad > 0),
  CONSTRAINT chk_movimiento_activo CHECK (activo IN (0,1))
);

CREATE TABLE bitacora_movimiento (
  id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  id_usuario   INT UNSIGNED NOT NULL,
  entidad      VARCHAR(50) NOT NULL,
  id_entidad   BIGINT UNSIGNED NOT NULL,
  accion       VARCHAR(20) NOT NULL,
  descripcion  VARCHAR(255) NULL,
  fecha        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_bitacora_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),

  CONSTRAINT chk_bitacora_entidad CHECK (
    entidad IN ('USUARIO',
      'ROL',
      'PERMISO',
      'CATEGORIA',
      'TIPO_PRODUCTO',
      'TIPO_MOVIMIENTO',
      'TIPO_CARTA',
      'PRODUCTO',
      'USUARIO_ROL',
      'ROL_PERMISO',
      'MOVIMIENTO_INVENTARIO',
      'IMAGEN_PRODUCTO',
      'PRODUCTO_CARTA')
  ),

  CONSTRAINT chk_bitacora_accion CHECK (
    accion IN ('INSERT','UPDATE','DELETE','ACTIVAR','DESACTIVAR'))

);

CREATE TABLE imagen_producto (
  id_imagen_producto  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  id_producto         INT UNSIGNED NOT NULL,
  ruta                VARCHAR(255) NOT NULL,
  nombre_archivo      VARCHAR(150) NOT NULL,
  fecha_registro      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  activo              TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT fk_imagen_producto
    FOREIGN KEY (id_producto) REFERENCES producto(id),

  CONSTRAINT chk_imagen_producto_activo CHECK (activo IN (0,1))
);

CREATE TABLE producto_carta (
  id_producto    INT UNSIGNED PRIMARY KEY,
  id_tipo_carta  INT UNSIGNED NOT NULL,
  atributo       VARCHAR(50) NULL,
  ataque         INT UNSIGNED NULL,
  defensa        INT UNSIGNED NULL,
  nivel          INT UNSIGNED NULL,
  activo         TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT fk_producto_carta_producto
    FOREIGN KEY (id_producto) REFERENCES producto(id),

  CONSTRAINT fk_producto_carta_tipo_carta
    FOREIGN KEY (id_tipo_carta) REFERENCES tipo_carta(id),

  CONSTRAINT chk_producto_carta_activo CHECK (activo IN (0,1)),
  CONSTRAINT chk_producto_carta_ataque CHECK (ataque IS NULL OR ataque >= 0),
  CONSTRAINT chk_producto_carta_defensa CHECK (defensa IS NULL OR defensa >= 0),
  CONSTRAINT chk_producto_carta_nivel CHECK (nivel IS NULL OR nivel >= 0)
);