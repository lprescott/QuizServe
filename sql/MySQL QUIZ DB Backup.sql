-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: quiz
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `administrator` (
  `ADMIN_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(75) NOT NULL,
  `PASSWORD` varchar(25) NOT NULL,
  PRIMARY KEY (`ADMIN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'admin@gmail.com','csi2019');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allowed_users`
--

DROP TABLE IF EXISTS `allowed_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `allowed_users` (
  `ALLOWED_USERS_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `USERS_ID` int(10) unsigned NOT NULL,
  `TEST_ID` int(10) unsigned NOT NULL,
  `TEST_ASSIGNED` date NOT NULL,
  PRIMARY KEY (`ALLOWED_USERS_ID`),
  KEY `USERS_ID` (`USERS_ID`),
  KEY `TEST_ID` (`TEST_ID`),
  CONSTRAINT `allowed_users_ibfk_1` FOREIGN KEY (`USERS_ID`) REFERENCES `users` (`USERS_ID`) ON DELETE CASCADE,
  CONSTRAINT `allowed_users_ibfk_2` FOREIGN KEY (`TEST_ID`) REFERENCES `test` (`TEST_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allowed_users`
--

LOCK TABLES `allowed_users` WRITE;
/*!40000 ALTER TABLE `allowed_users` DISABLE KEYS */;
INSERT INTO `allowed_users` VALUES (1,1,2,'2019-04-10');
/*!40000 ALTER TABLE `allowed_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `answer` (
  `ANSWER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ANSWER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ANSWER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,'Titan'),(2,'Ganymede'),(3,'Triton'),(4,'Io (pron: I - O)'),(5,'30.3 days'),(6,'30 days'),(7,'29.5 days'),(8,'28 days'),(9,'materialize'),(10,'persistent'),(11,'fragrance'),(12,'engross'),(13,'Temblor'),(14,'Photosynthesis'),(15,'Parameter'),(16,'Deleterious');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `question` (
  `QUESTION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TEXT` varchar(250) NOT NULL,
  `CATEGORY` varchar(75) DEFAULT NULL,
  `IMAGE_NAME` varchar(25) DEFAULT NULL,
  `IS_TRUE_FALSE` bit(1) NOT NULL,
  `TF_IS_TRUE` bit(1) DEFAULT NULL,
  `NUM_ANSWERS` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`QUESTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'The largest moon in our solar system has an atmosphere that is denser than the atmosphere of Mars. The name of this moon is:','Astronomy',NULL,_binary '\0',NULL,4),(2,'The period from one full moon to the next is:','Astronomy',NULL,_binary '\0',NULL,4),(3,'Firm or obstinate continuance in a course of action in spite of difficulty or opposition.','Vocabulary',NULL,_binary '\0',NULL,4),(4,'A factor that determines a range of variations; a boundary.','Vocabulary',NULL,_binary '\0',NULL,4),(5,'The earth is the fourth planet from the sun.','Astronomy',NULL,_binary '',_binary '\0',NULL),(6,'The planet Venus has no moons.','Astronomy',NULL,_binary '',_binary '',NULL),(7,'Jupiter is composed mostly of iron.','Astronomy',NULL,_binary '',_binary '\0',NULL),(8,'The sun is a star of average size.','Astronomy',NULL,_binary '',_binary '',NULL),(9,'A lunar eclipse occurs when the sun passes','Astronomy',NULL,_binary '',_binary '\0',NULL),(10,'A RIVER is bigger than a STREAM.','Vocabulary',NULL,_binary '',_binary '',NULL),(11,'There are one thousand years in a CENTURY.','Vocabulary',NULL,_binary '',_binary '\0',NULL),(12,'FOUNDED is the past tense of FOUND.','Vocabulary',NULL,_binary '',_binary '',NULL),(13,'ANSWER can be used as a noun and a verb.','Vocabulary',NULL,_binary '',_binary '',NULL),(14,'SCARLET is a brilliant red colour.','Vocabulary',NULL,_binary '',_binary '',NULL),(15,'USED TO DOING and USED TO DO mean the same thing.','Vocabulary',NULL,_binary '',_binary '\0',NULL),(16,'You can use IMPROVE as a noun and as a verb.','Vocabulary',NULL,_binary '',_binary '\0',NULL),(17,'DOZEN is equivalent to 20.','Vocabulary',NULL,_binary '',_binary '\0',NULL),(18,'The past tense of FIND is FOUND.','Vocabulary',NULL,_binary '',_binary '',NULL),(19,'EQUIVALENT TO is (more or less) the same as EQUAL TO.','Vocabulary',NULL,_binary '',_binary '',NULL);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_answer`
--

DROP TABLE IF EXISTS `question_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `question_answer` (
  `QUESTION_ANSWER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ANSWER_ID` int(10) unsigned NOT NULL,
  `QUESTION_ID` int(10) unsigned NOT NULL,
  `IS_CORRECT_ANSWER` bit(1) NOT NULL,
  PRIMARY KEY (`QUESTION_ANSWER_ID`),
  KEY `ANSWER_ID` (`ANSWER_ID`),
  KEY `QUESTION_ID` (`QUESTION_ID`),
  CONSTRAINT `question_answer_ibfk_1` FOREIGN KEY (`ANSWER_ID`) REFERENCES `answer` (`ANSWER_ID`) ON DELETE CASCADE,
  CONSTRAINT `question_answer_ibfk_2` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`QUESTION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_answer`
--

LOCK TABLES `question_answer` WRITE;
/*!40000 ALTER TABLE `question_answer` DISABLE KEYS */;
INSERT INTO `question_answer` VALUES (1,1,1,_binary ''),(2,2,1,_binary '\0'),(3,3,1,_binary '\0'),(4,4,1,_binary '\0'),(5,5,2,_binary '\0'),(6,6,2,_binary '\0'),(7,7,2,_binary ''),(8,8,2,_binary '\0'),(9,9,3,_binary '\0'),(10,10,3,_binary ''),(11,11,3,_binary '\0'),(12,12,3,_binary '\0'),(13,13,4,_binary '\0'),(14,14,4,_binary '\0'),(15,15,4,_binary ''),(16,16,4,_binary '\0');
/*!40000 ALTER TABLE `question_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `results`
--

DROP TABLE IF EXISTS `results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `results` (
  `RESULTS_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TEST_TAKEN_ID` int(10) unsigned NOT NULL,
  `QUESTION_ID` int(10) unsigned NOT NULL,
  `ANSWER_ID` int(10) unsigned NOT NULL,
  `TF_CHOSEN` bit(1) NOT NULL,
  PRIMARY KEY (`RESULTS_ID`),
  KEY `TEST_TAKEN_ID` (`TEST_TAKEN_ID`),
  KEY `QUESTION_ID` (`QUESTION_ID`),
  KEY `ANSWER_ID` (`ANSWER_ID`),
  CONSTRAINT `results_ibfk_1` FOREIGN KEY (`TEST_TAKEN_ID`) REFERENCES `tests_taken` (`TEST_TAKEN_ID`) ON DELETE CASCADE,
  CONSTRAINT `results_ibfk_2` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`QUESTION_ID`) ON DELETE CASCADE,
  CONSTRAINT `results_ibfk_3` FOREIGN KEY (`ANSWER_ID`) REFERENCES `answer` (`ANSWER_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results`
--

LOCK TABLES `results` WRITE;
/*!40000 ALTER TABLE `results` DISABLE KEYS */;
/*!40000 ALTER TABLE `results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `test` (
  `TEST_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ADMIN_ID` int(10) unsigned NOT NULL,
  `IMAGE_NAME` varchar(25) DEFAULT NULL,
  `TITLE` varchar(25) NOT NULL,
  `HEADER_TEXT` varchar(25) NOT NULL,
  `FOOTER_TEXT` varchar(25) NOT NULL,
  `TEST_DUE` date DEFAULT NULL,
  PRIMARY KEY (`TEST_ID`),
  KEY `ADMIN_ID` (`ADMIN_ID`),
  CONSTRAINT `test_ibfk_1` FOREIGN KEY (`ADMIN_ID`) REFERENCES `administrator` (`ADMIN_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (2,1,NULL,'Astronomy Test','TEST','TEST','2019-04-13');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_questions`
--

DROP TABLE IF EXISTS `test_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `test_questions` (
  `TEST_QUESTIONS_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TEST_ID` int(10) unsigned NOT NULL,
  `QUESTION_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`TEST_QUESTIONS_ID`),
  KEY `TEST_ID` (`TEST_ID`),
  KEY `QUESTION_ID` (`QUESTION_ID`),
  CONSTRAINT `test_questions_ibfk_1` FOREIGN KEY (`TEST_ID`) REFERENCES `test` (`TEST_ID`) ON DELETE CASCADE,
  CONSTRAINT `test_questions_ibfk_2` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`QUESTION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_questions`
--

LOCK TABLES `test_questions` WRITE;
/*!40000 ALTER TABLE `test_questions` DISABLE KEYS */;
INSERT INTO `test_questions` VALUES (8,2,1),(9,2,2),(10,2,5),(11,2,6),(12,2,7),(13,2,8),(14,2,9);
/*!40000 ALTER TABLE `test_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests_taken`
--

DROP TABLE IF EXISTS `tests_taken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tests_taken` (
  `TEST_TAKEN_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `USERS_ID` int(10) unsigned NOT NULL,
  `TEST_ID` int(10) unsigned NOT NULL,
  `TEST_DATE` date NOT NULL,
  `SCORE` decimal(5,2) NOT NULL,
  PRIMARY KEY (`TEST_TAKEN_ID`),
  KEY `USERS_ID` (`USERS_ID`),
  KEY `TEST_ID` (`TEST_ID`),
  CONSTRAINT `tests_taken_ibfk_1` FOREIGN KEY (`USERS_ID`) REFERENCES `users` (`USERS_ID`) ON DELETE CASCADE,
  CONSTRAINT `tests_taken_ibfk_2` FOREIGN KEY (`TEST_ID`) REFERENCES `test` (`TEST_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests_taken`
--

LOCK TABLES `tests_taken` WRITE;
/*!40000 ALTER TABLE `tests_taken` DISABLE KEYS */;
/*!40000 ALTER TABLE `tests_taken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `USERS_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(75) NOT NULL,
  `PASSWORD` varchar(25) NOT NULL,
  `IS_ACTIVE` bit(1) NOT NULL,
  PRIMARY KEY (`USERS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'active@gmail.com','csi2019',_binary ''),(2,'inactive@gmail.com','csi2019',_binary '\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-10 12:18:46
