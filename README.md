# Sistema de Eventos y Reservas

Aplicación de escritorio desarrollada en **JavaFX** que permite administrar eventos y sus reservas, con control de acceso según tres roles de usuario. Proyecto final de la asignatura de Programación Orientada a Objetos (POO) — período académico 2026-A.

## Descripción

El sistema permite gestionar el ciclo completo de un evento (registro, edición, confirmación, cancelación) y las reservas asociadas a cada uno, aplicando los cuatro pilares de la Programación Orientada a Objetos: **encapsulamiento, herencia, polimorfismo y abstracción**.

Cada usuario accede a una única pantalla de dashboard que se adapta dinámicamente según su rol, mostrando solo las funciones que le corresponden.

## Integrantes

- Estudiante 1: Darwin Sebastian Caiza Jami
- Estudiante 2: Leandro Andres Jacho Camuendo 

## Roles de usuario

| Rol | Acceso | Funciones |
|---|---|---|
| **Administrador** | Total | Crear, editar y eliminar eventos · Confirmar/cancelar eventos · Ver reservas |
| **Recepcionista** | Operativo | Confirmar/cancelar eventos · Gestionar reservas |
| **Supervisor (Reportes)** | Solo lectura | Ver resumen y estadísticas de eventos |

## 🛠️ Tecnologías utilizadas

- Java 17
- JavaFX (interfaz gráfica)
- PostgreSQL (base de datos relacional)
- JDBC (conexión a base de datos, patrón Singleton)
- IntelliJ IDEA (IDE de desarrollo)
- Launch4j (empaquetado como ejecutable .exe)

## Estructura del proyecto

<img width="921" height="779" alt="image" src="https://github.com/user-attachments/assets/e5c8485e-a16c-4e40-aff9-064f827965ec" />

<img width="811" height="263" alt="image" src="https://github.com/user-attachments/assets/ec1ab17a-cba4-451a-b8b4-bd150896f957" />

## 🗄️ Base de datos

3 tablas relacionadas en PostgreSQL:

- **usuarios**: id, usuario, contraseña, rol (admin, recepcionista, supervisor)
- **eventos**: id, nombre, tipo, inicio, fin, fecha, estado
- **reservas**: id, usuario_id (FK), evento_id (FK), cantidad

<details>
<summary>Ver script SQL completo</summary>

```sql
-- ============================================
-- Sistema de Eventos y Reservas
-- Script de creación de base de datos (PostgreSQL)
-- ============================================

-- Tabla de usuarios (roles: admin, recepcionista, supervisor)
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    usuario VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL
);

-- Tabla de eventos (recurso principal)
CREATE TABLE eventos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    tipo VARCHAR(100),
    inicio VARCHAR(100),
    fin VARCHAR(100),
    fecha VARCHAR(100),
    estado VARCHAR(20) NOT NULL DEFAULT 'pendiente'
);

-- Tabla de reservas (relación usuarios-eventos)
CREATE TABLE reservas (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    evento_id INTEGER NOT NULL REFERENCES eventos(id),
    cantidad INTEGER NOT NULL CHECK (cantidad > 0)
);

-- ============================================
-- Datos de prueba: un usuario por cada rol
-- ============================================
INSERT INTO usuarios (usuario, contrasenia, rol) VALUES ('admin', 'admin', 'admin');
INSERT INTO usuarios (usuario, contrasenia, rol) VALUES ('recepcion', 'recepcion', 'recepcionista');
INSERT INTO usuarios (usuario, contrasenia, rol) VALUES ('supervisor', 'supervisor', 'supervisor');

-- ============================================
-- Verificación
-- ============================================
SELECT * FROM usuarios;
SELECT * FROM eventos;
SELECT * FROM reservas;
```

</details>

## Instalación y ejecución
