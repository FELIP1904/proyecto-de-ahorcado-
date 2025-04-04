CREATE DATABASE IF NOT EXISTS Ahorcado;
USE Ahorcado;

CREATE TABLE Usuario (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Apellido1 VARCHAR(50) NOT NULL,
    Apellido2 VARCHAR(50) NOT NULL
);

CREATE TABLE Cuota (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Tipo_Cuota VARCHAR(50) NOT NULL,
    Precio DECIMAL(10,2) NOT NULL
);

CREATE TABLE Jugador (
    Usuario_ID INT PRIMARY KEY,
    Tipo_Cuota INT,
    FOREIGN KEY (Usuario_ID) REFERENCES Usuario(ID),
    FOREIGN KEY (Tipo_Cuota) REFERENCES Cuota(ID)
);

CREATE TABLE Administrador (
    Usuario_ID INT PRIMARY KEY,
    Nivel INT CHECK (Nivel BETWEEN 1 AND 3),
    FOREIGN KEY (Usuario_ID) REFERENCES Usuario(ID)
);

CREATE TABLE Idioma (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL
);

CREATE TABLE Frase (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Idioma INT NOT NULL,
    Descripcion TEXT NOT NULL,
    FOREIGN KEY (Idioma) REFERENCES Idioma(ID)
);

CREATE TABLE Juego (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Fecha DATE NOT NULL,
    Hora TIME NOT NULL,
    ID_Idioma INT,
    FOREIGN KEY (ID_Idioma) REFERENCES Idioma(ID)
);

CREATE TABLE Partida (
    ID_Juego INT,
    ID_Jugador INT,
    ID_Frase INT,
    Duracion INT NOT NULL,
    Puntos INT NOT NULL,
    Resultado VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID_Juego, ID_Jugador, ID_Frase),
    FOREIGN KEY (ID_Juego) REFERENCES Juego(ID),
    FOREIGN KEY (ID_Jugador) REFERENCES Jugador(Usuario_ID),
    FOREIGN KEY (ID_Frase) REFERENCES Frase(ID)
);

CREATE TABLE IdiomaPalabra (
    ID_Frase INT,
    ID_Idioma INT,
    PRIMARY KEY (ID_Frase, ID_Idioma),
    FOREIGN KEY (ID_Frase) REFERENCES Frase(ID),
    FOREIGN KEY (ID_Idioma) REFERENCES Idioma(ID)
);

CREATE TABLE Historico (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Version VARCHAR(50) NOT NULL,
    Idioma INT NOT NULL,
    Descripcion TEXT NOT NULL,
    FOREIGN KEY (Idioma) REFERENCES Idioma(ID)
);

CREATE TABLE Sinonimo (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Frase_ID INT NOT NULL,
    FOREIGN KEY (Frase_ID) REFERENCES Frase(ID)
);

CREATE TABLE Gestion (
    ID_Administrador INT,
    ID_Frase INT,
    Fecha DATE NOT NULL,
    PRIMARY KEY (ID_Administrador, ID_Frase),
    FOREIGN KEY (ID_Administrador) REFERENCES Administrador(Usuario_ID),
    FOREIGN KEY (ID_Frase) REFERENCES Frase(ID)
);
 
