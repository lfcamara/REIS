-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: reis
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `paciente_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o7rt0909f6ygply7judc4s8v` (`login`),
  KEY `FK_6gp4n6w69dt0rijcgg4e7cc57` (`paciente_id`),
  CONSTRAINT `FK_6gp4n6w69dt0rijcgg4e7cc57` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (2,'joao','joao',2),(4,'luana','luana',4);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicao_balanca`
--

DROP TABLE IF EXISTS `medicao_balanca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicao_balanca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `altura` double DEFAULT NULL,
  `altura_unidade` varchar(255) DEFAULT NULL,
  `data_hora` datetime DEFAULT NULL,
  `massa` double DEFAULT NULL,
  `massa_unidade` varchar(255) DEFAULT NULL,
  `peso` double DEFAULT NULL,
  `peso_unidade` varchar(255) DEFAULT NULL,
  `paciente_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8y2kfmscjjd260jv9ex2qcwf1` (`paciente_id`),
  CONSTRAINT `FK_8y2kfmscjjd260jv9ex2qcwf1` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicao_balanca`
--

LOCK TABLES `medicao_balanca` WRITE;
/*!40000 ALTER TABLE `medicao_balanca` DISABLE KEYS */;
INSERT INTO `medicao_balanca` VALUES (1,163,'cm','2017-01-09 04:27:00',0,'kg m-2',60,'kg',2),(3,163,'cm','2017-01-09 04:27:00',0,'kg m-2',60,'kg',4);
/*!40000 ALTER TABLE `medicao_balanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicao_oximetro`
--

DROP TABLE IF EXISTS `medicao_oximetro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicao_oximetro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_hora` datetime DEFAULT NULL,
  `spo2` double DEFAULT NULL,
  `spo2_unidade` varchar(255) DEFAULT NULL,
  `taxa_pulso` double DEFAULT NULL,
  `taxa_pulso_Unidade` varchar(255) DEFAULT NULL,
  `paciente_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ok6ui8ne4inhw2kus3yuxtk3l` (`paciente_id`),
  CONSTRAINT `FK_ok6ui8ne4inhw2kus3yuxtk3l` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicao_oximetro`
--

LOCK TABLES `medicao_oximetro` WRITE;
/*!40000 ALTER TABLE `medicao_oximetro` DISABLE KEYS */;
INSERT INTO `medicao_oximetro` VALUES (1,'2016-09-01 07:47:05',100,'%',50,'bpm',2),(3,'2016-09-01 07:48:45',97.5,'%',73.5,'bpm',2),(5,'2016-09-01 07:52:10',100,'%',69,'bpm',2),(6,'2016-09-01 08:03:51',99,'%',84,'bpm',2),(7,'2016-09-01 08:40:03',100,'%',92,'bpm',2),(16,'2016-09-09 13:40:45',97.5,'%',73.5,'bpm',4);
/*!40000 ALTER TABLE `medicao_oximetro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicao_pressao`
--

DROP TABLE IF EXISTS `medicao_pressao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicao_pressao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_hora` datetime DEFAULT NULL,
  `pressao_diastolica` double DEFAULT NULL,
  `pressao_diastolica_unidade` varchar(255) DEFAULT NULL,
  `pressao_media` double DEFAULT NULL,
  `pressao_media_unidade` varchar(255) DEFAULT NULL,
  `pressao_sistolica` double DEFAULT NULL,
  `pressao_sistolica_unidade` varchar(255) DEFAULT NULL,
  `taxa_pulso` double DEFAULT NULL,
  `taxa_pulso_unidade` varchar(255) DEFAULT NULL,
  `paciente_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oaptrqpc212y32ykid67h2r6c` (`paciente_id`),
  CONSTRAINT `FK_oaptrqpc212y32ykid67h2r6c` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicao_pressao`
--

LOCK TABLES `medicao_pressao` WRITE;
/*!40000 ALTER TABLE `medicao_pressao` DISABLE KEYS */;
INSERT INTO `medicao_pressao` VALUES (1,'2016-03-09 07:25:30',65,'mmHg',80,'mmHg',111,'mmHg',60,'bpm',2),(3,'2016-03-09 07:25:30',65,'mmHg',80,'mmHg',111,'mmHg',60,'bpm',4);
/*!40000 ALTER TABLE `medicao_pressao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paciente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cidade` varchar(255) DEFAULT NULL,
  `data_nascimento` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `sobrenome` varchar(255) DEFAULT NULL,
  `telefone_casa` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES (2,'Campina Grande','30/09/1989','Av. Floriano Peixoto, 123','PB','João Antonio','Masculino','Silva Porto','(83) 9967-5436'),(4,'Cidade de Luana','03/06/1991','Rua de Luana, 123','PB','Luana','Feminino','Janaina','(83) 9967-5436');
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-10 23:49:19
