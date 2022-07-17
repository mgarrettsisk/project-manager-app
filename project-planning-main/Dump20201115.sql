-- MySQL dump 10.13  Distrib 8.0.21, for macos10.15 (x86_64)
--
-- Host: localhost    Database: project_manager
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `member_name` varchar(100) NOT NULL,
  `project_id` int NOT NULL,
  `active` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`member_id`),
  KEY `project_id_fkey3_idx` (`project_id`),
  CONSTRAINT `project_id_fkey3` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'Kim Van',1,1),(2,'John Smith',1,1),(3,'John Doe',29,1),(4,'Jane Smith',29,1),(5,'Kim Van',29,1),(6,'Harry VanAsselberg',29,1),(7,'David Van',29,1),(8,'test 1',29,0),(9,'test 2',29,1),(10,'Joe',30,1),(11,'James',30,1),(12,'James',31,0),(13,'James',31,1),(14,'test 1',29,1);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phase`
--

DROP TABLE IF EXISTS `phase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phase` (
  `phase_id` int NOT NULL,
  `phase_desc` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`phase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phase`
--

LOCK TABLES `phase` WRITE;
/*!40000 ALTER TABLE `phase` DISABLE KEYS */;
INSERT INTO `phase` VALUES (1,'Requirements Analysis'),(2,'Designing'),(3,'Coding'),(4,'Testing'),(5,'Project Management');
/*!40000 ALTER TABLE `phase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(100) NOT NULL,
  `project_desc` varchar(1000) NOT NULL,
  `owner` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'ABC Project','Talend project to import data from Redshift database and convert to XML files.  FTP to Client','Kim Van'),(2,'XYZ','default project description','David Van'),(3,'Project  Planning Proj','KSU class project','Joe Smith'),(4,'Java Test Project','Test database queries','Kim VanAsselberg'),(5,'Java Test Project 2','Test more database queries','John Doe'),(6,'project X','Design user interface for bank app','Jane Smith'),(7,'project Y','Design database for bank app','Joe Smith'),(8,'project name','project description','owner'),(9,'another project name','project description','owner'),(10,'project name','project description','owner'),(11,'XYZ','default project description','David Van'),(12,'WorldPay','Financial systems project','Kim VanAsselberg'),(13,'test test test','test description','owner name'),(14,'Java project','Project about Talend','Kim VanAsselberg'),(15,'test','desc','kim'),(16,'ABCDE project name','test','Kim'),(29,'xx','xx','x'),(30,'Java Dev Project','Java Development','Kim Van'),(31,'A Java project','This is a test','Kim Van');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_time`
--

DROP TABLE IF EXISTS `project_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_time` (
  `pt_id` int NOT NULL AUTO_INCREMENT,
  `hours` int NOT NULL,
  `member_id` int NOT NULL,
  `phase_id` int NOT NULL,
  `work_date` date NOT NULL,
  `project_id` int NOT NULL,
  PRIMARY KEY (`pt_id`),
  KEY `member_id_fkey_idx` (`member_id`),
  KEY `phase_id_fkey_idx` (`phase_id`),
  KEY `project_id_fkey_idx` (`project_id`),
  CONSTRAINT `member_id_fkey` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `phase_id_fkey` FOREIGN KEY (`phase_id`) REFERENCES `phase` (`phase_id`),
  CONSTRAINT `project_id_fkey` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_time`
--

LOCK TABLES `project_time` WRITE;
/*!40000 ALTER TABLE `project_time` DISABLE KEYS */;
INSERT INTO `project_time` VALUES (1,8,1,1,'2020-09-21',1),(2,6,1,1,'2020-09-20',1),(3,8,1,2,'2020-12-03',1);
/*!40000 ALTER TABLE `project_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement`
--

DROP TABLE IF EXISTS `requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requirement` (
  `req_id` int NOT NULL AUTO_INCREMENT,
  `req_desc` varchar(500) NOT NULL,
  `req_type` int NOT NULL,
  `project_id` int DEFAULT NULL,
  PRIMARY KEY (`req_id`),
  KEY `req_type_fkey_idx` (`req_type`),
  KEY `project_id_fkey5_idx` (`project_id`),
  CONSTRAINT `project_id_fkey5` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `req_type_fkey` FOREIGN KEY (`req_type`) REFERENCES `requirement_type` (`req_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement`
--

LOCK TABLES `requirement` WRITE;
/*!40000 ALTER TABLE `requirement` DISABLE KEYS */;
INSERT INTO `requirement` VALUES (1,'Meet deadlines',1,1);
/*!40000 ALTER TABLE `requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement_type`
--

DROP TABLE IF EXISTS `requirement_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requirement_type` (
  `req_type` int NOT NULL,
  `req_type_desc` varchar(45) NOT NULL,
  PRIMARY KEY (`req_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement_type`
--

LOCK TABLES `requirement_type` WRITE;
/*!40000 ALTER TABLE `requirement_type` DISABLE KEYS */;
INSERT INTO `requirement_type` VALUES (1,'Functional'),(2,'Nonfunctional');
/*!40000 ALTER TABLE `requirement_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risk`
--

DROP TABLE IF EXISTS `risk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk` (
  `risk_id` int NOT NULL AUTO_INCREMENT,
  `risk_desc` varchar(200) NOT NULL,
  `risk_status_id` int NOT NULL,
  `project_id` int NOT NULL,
  PRIMARY KEY (`risk_id`),
  KEY `risk_status_id_idx` (`risk_status_id`),
  KEY `project_id_idx` (`project_id`),
  CONSTRAINT `project_id_fkey2` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `risk_status_id` FOREIGN KEY (`risk_status_id`) REFERENCES `risk_status` (`risk_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk`
--

LOCK TABLES `risk` WRITE;
/*!40000 ALTER TABLE `risk` DISABLE KEYS */;
INSERT INTO `risk` VALUES (1,'Technology problems',1,14);
/*!40000 ALTER TABLE `risk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risk_status`
--

DROP TABLE IF EXISTS `risk_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_status` (
  `risk_status_id` int NOT NULL,
  `risk_status_desc` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`risk_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk_status`
--

LOCK TABLES `risk_status` WRITE;
/*!40000 ALTER TABLE `risk_status` DISABLE KEYS */;
INSERT INTO `risk_status` VALUES (1,'High'),(2,'Medium'),(3,'Low'),(4,'Resolved');
/*!40000 ALTER TABLE `risk_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-15 18:40:01
