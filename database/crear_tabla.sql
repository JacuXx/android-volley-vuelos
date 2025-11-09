-- Script SQL para crear la base de datos y la tabla de rutas
-- Base de datos: vuelos
-- Tabla: rutas

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS vuelos;

-- Usar la base de datos
USE vuelos;

-- Crear la tabla rutas
CREATE TABLE IF NOT EXISTS rutas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    o VARCHAR(100) NOT NULL COMMENT 'Origen del vuelo',
    d VARCHAR(100) NOT NULL COMMENT 'Destino del vuelo',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar algunos datos de ejemplo (opcional)
-- INSERT INTO rutas (o, d) VALUES ('Madrid', 'Barcelona');
-- INSERT INTO rutas (o, d) VALUES ('Ciudad de México', 'Guadalajara');
-- INSERT INTO rutas (o, d) VALUES ('Nueva York', 'Los Ángeles');
