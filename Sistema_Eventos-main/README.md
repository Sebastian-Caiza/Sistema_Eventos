CREATE TABLE usuarios ( id SERIAL PRIMARY KEY, usuario VARCHAR(100) UNIQUE NOT NULL, contrasenia VARCHAR(255) NOT NULL, rol VARCHAR(30) NOT NULL );

INSERT INTO usuarios (usuario, contrasenia, rol) VALUES ('admin', 'admin', 'admin');

SELECT * FROM usuarios;

CREATE TABLE eventos( id SERIAL PRIMARY KEY, nombre VARCHAR(100) UNIQUE NOT NULL, tipo VARCHAR(100), inicio VARCHAR(100), fin VARCHAR(100), fecha VARCHAR(100)

);


SELECT * FROM eventos;


ALTER TABLE eventos ADD COLUMN estado VARCHAR(20) NOT NULL DEFAULT 'pendiente';

INSERT INTO usuarios (usuario, contrasenia, rol) VALUES ('recepcion', 'recepcion', 'recepcionista');

INSERT INTO usuarios (usuario, contrasenia, rol) VALUES ('supervisor', 'supervisor', 'supervisor');

CREATE TABLE reservas (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    evento_id INTEGER NOT NULL REFERENCES eventos(id),
    cantidad INTEGER NOT NULL CHECK (cantidad > 0)
);
