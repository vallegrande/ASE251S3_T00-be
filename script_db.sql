-- 0. Crear Base de Datos (Correr esto primero si no existe)
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'ConstruccionDB')
BEGIN
    CREATE DATABASE ConstruccionDB;
END
GO

USE ConstruccionDB;
GO

-- 1. Crear tablas
CREATE TABLE categorias (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    estado BIT NOT NULL DEFAULT 1
);

CREATE TABLE maquinas (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    precio DECIMAL(12,2) NOT NULL,
    categoria_id BIGINT NOT NULL,
    estado BIT NOT NULL DEFAULT 1,
    CONSTRAINT FK_Categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE clientes (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo VARCHAR(150),
    estado BIT NOT NULL DEFAULT 1
);

CREATE TABLE empleados (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    sueldo DECIMAL(10,2) NOT NULL,
    estado BIT NOT NULL DEFAULT 1
);

-- 2. Insertar datos iniciales (Data de ejemplo)
INSERT INTO categorias (nombre, estado) VALUES ('Excavadoras', 1);
INSERT INTO categorias (nombre, estado) VALUES ('Cargadores', 1);
INSERT INTO categorias (nombre, estado) VALUES ('Compactadoras', 1);

INSERT INTO maquinas (nombre, marca, modelo, precio, categoria_id, estado) VALUES 
('Excavadora Hidráulica', 'Caterpillar', '320 GC', 145000.00, 1, 1),
('Mini Cargador', 'Bobcat', 'S450', 35000.00, 2, 1),
('Rodillo Liso', 'Volvo', 'SD110', 88000.00, 3, 1);

INSERT INTO clientes (nombre, apellido, documento, telefono, correo, estado) VALUES 
('Juan', 'Perez', '45879632', '987654321', 'juan.perez@email.com', 1),
('Maria', 'Lopez', '78451236', '912345678', 'm.lopez@email.com', 1);

INSERT INTO empleados (nombre, apellido, cargo, sueldo, estado) VALUES 
('Carlos', 'Sánchez', 'Vendedor Senior', 3500.00, 1),
('Ana', 'García', 'Gerente de Ventas', 6500.00, 1);
