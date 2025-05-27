-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: ahorcado_db
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administradores`
--

DROP TABLE IF EXISTS `administradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administradores` (
  `id_admin` int NOT NULL,
  `nivel_acceso` int DEFAULT '1',
  PRIMARY KEY (`id_admin`),
  CONSTRAINT `administradores_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administradores`
--

LOCK TABLES `administradores` WRITE;
/*!40000 ALTER TABLE `administradores` DISABLE KEYS */;
INSERT INTO `administradores` VALUES (1,1),(4,3);
/*!40000 ALTER TABLE `administradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backups`
--

DROP TABLE IF EXISTS `backups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `backups` (
  `id_backup` int NOT NULL AUTO_INCREMENT,
  `nombre_archivo` varchar(255) NOT NULL,
  `tamano` bigint NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `creado_por` int NOT NULL,
  PRIMARY KEY (`id_backup`),
  KEY `creado_por` (`creado_por`),
  CONSTRAINT `backups_ibfk_1` FOREIGN KEY (`creado_por`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backups`
--

LOCK TABLES `backups` WRITE;
/*!40000 ALTER TABLE `backups` DISABLE KEYS */;
INSERT INTO `backups` VALUES (1,'backup_ahorcado_1747842573537.sql',12819,'2025-05-21 15:49:35',4);
/*!40000 ALTER TABLE `backups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Animales','Nombres de animales en diferentes idiomas'),(2,'Comida','Alimentos y platos típicos'),(3,'Países','Nombres de países y ciudades'),(4,'Tecnología','Términos tecnológicos y de informática'),(5,'Deportes','Deportes y actividades físicas'),(6,'Objetos cotidianos','Objetos de uso común');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idiomas`
--

DROP TABLE IF EXISTS `idiomas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `idiomas` (
  `id_idioma` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `codigo` varchar(5) NOT NULL,
  PRIMARY KEY (`id_idioma`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idiomas`
--

LOCK TABLES `idiomas` WRITE;
/*!40000 ALTER TABLE `idiomas` DISABLE KEYS */;
INSERT INTO `idiomas` VALUES (1,'Español','es'),(2,'Inglés','en'),(3,'Francés','fr'),(4,'Alemán','de'),(5,'Italiano','it'),(6,'valenciano','ca');
/*!40000 ALTER TABLE `idiomas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `palabras`
--

DROP TABLE IF EXISTS `palabras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `palabras` (
  `id_palabra` int NOT NULL AUTO_INCREMENT,
  `contenido` varchar(255) NOT NULL,
  `tipo` enum('palabra','frase') NOT NULL,
  `id_idioma` int NOT NULL,
  `id_categoria` int DEFAULT NULL,
  `dificultad` enum('facil','medio','dificil') DEFAULT 'medio',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `creado_por` int DEFAULT NULL,
  PRIMARY KEY (`id_palabra`),
  KEY `id_idioma` (`id_idioma`),
  KEY `id_categoria` (`id_categoria`),
  KEY `creado_por` (`creado_por`),
  CONSTRAINT `palabras_ibfk_1` FOREIGN KEY (`id_idioma`) REFERENCES `idiomas` (`id_idioma`),
  CONSTRAINT `palabras_ibfk_2` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id_categoria`),
  CONSTRAINT `palabras_ibfk_3` FOREIGN KEY (`creado_por`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `palabras`
--

LOCK TABLES `palabras` WRITE;
/*!40000 ALTER TABLE `palabras` DISABLE KEYS */;
INSERT INTO `palabras` VALUES (1,'elefante','palabra',1,1,'facil','2025-05-16 17:59:39',1),(2,'hipopótamo','palabra',1,1,'medio','2025-05-16 17:59:39',1),(3,'ornitorrinco','palabra',1,1,'dificil','2025-05-16 17:59:39',1),(4,'paella','palabra',1,2,'medio','2025-05-16 17:59:39',1),(5,'croissant','palabra',1,2,'medio','2025-05-16 17:59:39',1),(6,'Alemania','palabra',1,3,'facil','2025-05-16 17:59:39',1),(7,'Barcelona','palabra',1,3,'facil','2025-05-16 17:59:39',1),(8,'smartphone','palabra',1,4,'facil','2025-05-16 17:59:39',1),(9,'algoritmo','palabra',1,4,'medio','2025-05-16 17:59:39',1),(10,'baloncesto','palabra',1,5,'facil','2025-05-16 17:59:39',1),(11,'paraguas','palabra',1,6,'medio','2025-05-16 17:59:39',1),(12,'El perro ladra por la noche','frase',1,1,'medio','2025-05-16 17:59:39',1),(13,'La tecnología avanza rápidamente','frase',1,4,'dificil','2025-05-16 17:59:39',1),(14,'elephant','palabra',2,1,'facil','2025-05-16 17:59:39',1),(15,'hippopotamus','palabra',2,1,'medio','2025-05-16 17:59:39',1),(16,'platypus','palabra',2,1,'dificil','2025-05-16 17:59:39',1),(17,'pizza','palabra',2,2,'facil','2025-05-16 17:59:39',1),(18,'hamburger','palabra',2,2,'facil','2025-05-16 17:59:39',1),(19,'Germany','palabra',2,3,'facil','2025-05-16 17:59:39',1),(20,'New York','palabra',2,3,'facil','2025-05-16 17:59:39',1),(21,'computer','palabra',2,4,'facil','2025-05-16 17:59:39',1),(22,'software','palabra',2,4,'medio','2025-05-16 17:59:39',1),(23,'basketball','palabra',2,5,'facil','2025-05-16 17:59:39',1),(24,'umbrella','palabra',2,6,'medio','2025-05-16 17:59:39',1),(25,'The quick brown fox jumps over the lazy dog','frase',2,1,'dificil','2025-05-16 17:59:39',1),(26,'Practice makes perfect','frase',2,5,'medio','2025-05-16 17:59:39',1),(27,'éléphant','palabra',3,1,'facil','2025-05-16 17:59:39',1),(28,'hippopotame','palabra',3,1,'medio','2025-05-16 17:59:39',1),(29,'ornithorynque','palabra',3,1,'dificil','2025-05-16 17:59:39',1),(30,'baguette','palabra',3,2,'facil','2025-05-16 17:59:39',1),(31,'croissant','palabra',3,2,'facil','2025-05-16 17:59:39',1),(32,'Paris','palabra',3,3,'facil','2025-05-16 17:59:39',1),(33,'ordinateur','palabra',3,4,'medio','2025-05-16 17:59:39',1),(34,'football','palabra',3,5,'facil','2025-05-16 17:59:39',1),(35,'Liberté, Égalité, Fraternité','frase',3,3,'dificil','2025-05-16 17:59:39',1),(36,'Elefant','palabra',4,1,'facil','2025-05-16 17:59:41',1),(37,'Flusspferd','palabra',4,1,'medio','2025-05-16 17:59:41',1),(38,'Schnabeltier','palabra',4,1,'dificil','2025-05-16 17:59:41',1),(39,'Bratwurst','palabra',4,2,'medio','2025-05-16 17:59:41',1),(40,'Berlin','palabra',4,3,'facil','2025-05-16 17:59:41',1),(41,'Computer','palabra',4,4,'facil','2025-05-16 17:59:41',1),(42,'Fußball','palabra',4,5,'facil','2025-05-16 17:59:41',1),(44,'capibara','palabra',1,1,'medio','2025-05-21 18:49:46',NULL),(45,'Katze','palabra',4,1,'facil','2025-05-21 18:58:37',NULL),(46,'Eichhörnchen','palabra',4,1,'medio','2025-05-21 18:58:57',NULL),(47,'Stachelschwein','palabra',4,1,'dificil','2025-05-21 18:59:14',NULL),(48,'Die Katze schläft','frase',4,1,'facil','2025-05-21 18:59:38',NULL),(49,'Das Eichhörnchen klettert auf den Baum','frase',4,1,'medio','2025-05-21 19:00:03',NULL),(50,'Das Stachelschwein verteidigt sich mit seinen Stacheln','frase',4,1,'dificil','2025-05-21 19:00:21',NULL),(51,'El perro ladra','frase',1,1,'facil','2025-05-21 19:02:43',NULL);
/*!40000 ALTER TABLE `palabras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partidas`
--

DROP TABLE IF EXISTS `partidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partidas` (
  `id_partida` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_palabra` int NOT NULL,
  `fecha_inicio` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_fin` timestamp NULL DEFAULT NULL,
  `resultado` enum('ganada','perdida','abandonada') DEFAULT NULL,
  `intentos_restantes` int DEFAULT NULL,
  `letras_usadas` varchar(255) DEFAULT '',
  PRIMARY KEY (`id_partida`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_palabra` (`id_palabra`),
  CONSTRAINT `partidas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `partidas_ibfk_2` FOREIGN KEY (`id_palabra`) REFERENCES `palabras` (`id_palabra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partidas`
--

LOCK TABLES `partidas` WRITE;
/*!40000 ALTER TABLE `partidas` DISABLE KEYS */;
/*!40000 ALTER TABLE `partidas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranking`
--

DROP TABLE IF EXISTS `ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ranking` (
  `id_ranking` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `puntuacion` int DEFAULT '0',
  `partidas_ganadas` int DEFAULT '0',
  `partidas_jugadas` int DEFAULT '0',
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_ranking`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `ranking_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranking`
--

LOCK TABLES `ranking` WRITE;
/*!40000 ALTER TABLE `ranking` DISABLE KEYS */;
INSERT INTO `ranking` VALUES (1,2,30,3,7,'2025-05-27 14:17:39'),(2,3,0,0,1,'2025-05-19 18:56:27');
/*!40000 ALTER TABLE `ranking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tipo_cuenta` enum('normal','premium','admin') DEFAULT 'normal',
  `numero_cuenta` varchar(20) DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ultimo_login` timestamp NULL DEFAULT NULL,
  `recordar_password` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `numero_cuenta` (`numero_cuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'erizo','1234','felipe','apellido','juanfe@gmail.com','admin','12342','2025-05-14 18:18:41',NULL,0),(2,'erigod','1234','felipe','arevalo','juan@gmail.com','normal',NULL,'2025-05-16 17:49:20',NULL,0),(3,'cenicero','4321','nicolas','villafañe','vilaa@gmail.com','premium',NULL,'2025-05-19 18:54:48',NULL,0),(4,'admin','admin123','Admin','Principal','admin@ahorcado.com','admin',NULL,'2025-05-20 16:56:48',NULL,0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-27 16:50:55
