-- MySQL dump 10.13  Distrib 8.0.16, for Linux (x86_64)
--
-- Host: localhost    Database: joboonja
-- ------------------------------------------------------
-- Server version	8.0.16
CREATE DATABASE IF NOT EXISTS joboonja;
USE joboonja;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Bid`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE IF NOT EXISTS `Bid` (
  `userId` int(20) NOT NULL,
  `projectId` varchar(50) NOT NULL,
  `bidAmount` int(20) NOT NULL,
  KEY `Bid_projectId_fk_idx` (`projectId`),
  KEY `Bid_userId_fk_idx` (`userId`),
  CONSTRAINT `Bid_projectId_fk` FOREIGN KEY (`projectId`) REFERENCES `Project` (`id`),
  CONSTRAINT `Bid_userId_fk` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `Endorsement`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE IF NOT EXISTS `Endorsement` (
  `endorserId` int(15) NOT NULL,
  `endorsedId` int(15) NOT NULL,
  `skillName` varchar(50) NOT NULL,
  KEY `Endorsement_endorsedId_fk_idx` (`endorsedId`),
  KEY `Endorsement_endorserId_fk_idx` (`endorserId`),
  KEY `Endorsement_skillName_fk_idx` (`skillName`),
  CONSTRAINT `Endorsement_endorsedId_fk` FOREIGN KEY (`endorsedId`) REFERENCES `User` (`id`),
  CONSTRAINT `Endorsement_endorserId_fk` FOREIGN KEY (`endorserId`) REFERENCES `User` (`id`),
  CONSTRAINT `Endorsement_skillName_fk` FOREIGN KEY (`skillName`) REFERENCES `Skill` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `Project`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE IF NOT EXISTS `Project` (
  `id` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `imageURL` varchar(200) DEFAULT NULL,
  `budget` int(15) NOT NULL,
  `deadline` bigint(50) NOT NULL,
  `creationDate` bigint(50) NOT NULL,
  `winnerId` int(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `winnerId_fk_idx` (`winnerId`),
  CONSTRAINT `Project_winnerId_fk` FOREIGN KEY (`winnerId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `ProjectRequirement`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE IF NOT EXISTS `ProjectRequirement` (
  `projectId` varchar(50) NOT NULL,
  `skillName` varchar(50) NOT NULL,
  `point` int(15) NOT NULL,
  PRIMARY KEY (`projectId`,`skillName`),
  KEY `skillName_fk_idx` (`skillName`),
  KEY `ProjectRequirement_skillName_fk_idx` (`skillName`),
  CONSTRAINT `ProjectRequirement_projectId_fk` FOREIGN KEY (`projectId`) REFERENCES `Project` (`id`),
  CONSTRAINT `ProjectRequirement_skillName_fk` FOREIGN KEY (`skillName`) REFERENCES `Skill` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `Skill`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE IF NOT EXISTS `Skill` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `User`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `jobTitle` varchar(50) DEFAULT NULL,
  `profilePictureURL` varchar(200) DEFAULT NULL,
  `bio` varchar(2000) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `UserSkill`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE IF NOT EXISTS `UserSkill` (
  `userId` int(15) NOT NULL,
  `skillName` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`,`skillName`),
  KEY `UserSkill_skillName_fk_idx` (`skillName`),
  CONSTRAINT `UserSkill_skillName_fk` FOREIGN KEY (`skillName`) REFERENCES `Skill` (`name`),
  CONSTRAINT `UserSkill_userId_fk` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-24 14:38:26
