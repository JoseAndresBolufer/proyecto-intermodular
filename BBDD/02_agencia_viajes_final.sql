CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_nacimiento DATE NOT NULL
);

CREATE TABLE destinos (
    id_destino SERIAL PRIMARY KEY,
    ciudad VARCHAR(100) NOT NULL,
    pais VARCHAR(60) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    requiere_pasaporte BOOLEAN NOT NULL
);

CREATE TABLE guias (
    id_guia SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    especialidad VARCHAR(50) NOT NULL CHECK (
        especialidad IN ('Geografia', 'Historia', 'Arquitectura', 'Comida')
    ),
    id_destino INT NOT NULL,
    FOREIGN KEY (id_destino) REFERENCES destinos(id_destino)
);

CREATE TABLE pasaporte (
    id_pasaporte SERIAL PRIMARY KEY,
    numero VARCHAR(50) NOT NULL,
    pais_expedicion VARCHAR(60) NOT NULL,
    fecha_caducidad DATE NOT NULL,
    id_usuario INT UNIQUE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE elegir (
    id_usuario INT NOT NULL,
    id_destino INT NOT NULL,
    PRIMARY KEY (id_usuario, id_destino),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_destino) REFERENCES destinos(id_destino)
);


