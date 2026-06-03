-- USUARIOS
INSERT INTO usuario (id, nombre, apellidos, email, telefono, fecha_nacimiento) VALUES
(1, 'Juan', 'Pérez', 'juan@email.com', '600111222', '1995-03-10'),
(2, 'Laura', 'García', 'laura@email.com', '600333444', '1990-07-21'),
(3, 'Carlos', 'López', 'carlos@email.com', '600555666', '1988-11-05');

ALTER TABLE usuario ALTER COLUMN id RESTART WITH 4;

-- PASAPORTES
INSERT INTO pasaporte (id, numero, pais_expedicion, fecha_caducidad, id_usuario) VALUES
(1, 'X1234567', 'España', '2030-05-01', 1),
(2, 'Y7654321', 'España', '2029-09-15', 2);

ALTER TABLE pasaporte ALTER COLUMN id RESTART WITH 3;

insert into destino (ciudad, pais, precio, requiere_pasaporte)
values ('Roma', 'Italia', 450.00, false);

insert into destino (ciudad, pais, precio, requiere_pasaporte)
values ('Nueva York', 'Estados Unidos', 1200.00, true);

insert into guia (nombre, apellidos, especialidad, id_destino)
values ('Mario', 'Rossi', 'Historia', 1);

insert into guia (nombre, apellidos, especialidad, id_destino)
values ('Ana', 'Martinez', 'Arquitectura', 1);

insert into guia (nombre, apellidos, especialidad, id_destino)
values ('John', 'Smith', 'Geografia', 2);