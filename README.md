CREATE DATABASE Eventos;

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    usuario VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL
);



INSERT INTO usuarios (usuario, contrasenia, rol)
VALUES
('admin', 'admin', 'admin');

SELECT * FROM usuarios;

