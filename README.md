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

## Tecnologías utilizadas

- Java 17
- JavaFX (interfaz gráfica)
- PostgreSQL (base de datos relacional)
- JDBC (conexión a base de datos, patrón Singleton)
- IntelliJ IDEA (IDE de desarrollo)
- Launch4j (empaquetado como ejecutable .exe)

## Estructura del proyecto

<img width="921" height="779" alt="image" src="https://github.com/user-attachments/assets/e5c8485e-a16c-4e40-aff9-064f827965ec" />

<img width="811" height="263" alt="image" src="https://github.com/user-attachments/assets/ec1ab17a-cba4-451a-b8b4-bd150896f957" />

## Base de datos

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

1. Clonar el repositorio:
   
git clone https://github.com/Sebastian-Caiza/Sistema_Eventos.git

2. Crear la base de datos en PostgreSQL y ejecutar
3. Configurar las credenciales de conexión en `db/ConexionBD.java` (URL, usuario y contraseña de PostgreSQL).
4. Abrir el proyecto en IntelliJ IDEA y ejecutar `Launcher.java`.

### Ejecutable

También puede ejecutarse directamente el archivo `.exe`, sin necesidad de tener el IDE ni JavaFX instalados por separado.

## Capturas de pantalla

- Login
  
<img width="456" height="515" alt="image" src="https://github.com/user-attachments/assets/7bdb3c19-9d36-4e50-b093-858161b51741" />

-Dashboard por rol

<img width="1122" height="720" alt="image" src="https://github.com/user-attachments/assets/310a4af6-657c-4bd4-81e2-7e7c06f3e010" />

<img width="1123" height="717" alt="image" src="https://github.com/user-attachments/assets/01efc7f3-9338-42ce-ac19-e8b2e409e303" />

## Video demostrativo

https://epnecuador-my.sharepoint.com/:v:/g/personal/darwin_caiza_epn_edu_ec/IQD5j2DMkFaARqj0Y6RXPCAEAQEranEKvOwl7do3hpljUoM?e=5K1MXv&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D

## Licencia

Proyecto académico — Escuela Politécnica Nacional, Programación Orientada a Objetos, 2026-A.
