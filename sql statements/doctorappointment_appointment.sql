-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: doctorappointment
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `date` date NOT NULL,
  `startSlotTime` time NOT NULL,
  `endSlotTime` time NOT NULL,
  `PATIENT_patientAMKA` varchar(11) NOT NULL,
  `DOCTOR_doctorAMKA` varchar(11) NOT NULL,
  UNIQUE KEY `PATIENT_patientAMKA` (`PATIENT_patientAMKA`,`DOCTOR_doctorAMKA`,`date`),
  UNIQUE KEY `date` (`date`,`startSlotTime`,`endSlotTime`,`DOCTOR_doctorAMKA`),
  UNIQUE KEY `date_2` (`date`,`startSlotTime`,`endSlotTime`,`PATIENT_patientAMKA`),
  KEY `fk_APPOINTMENT_DOCTOR1_idx` (`DOCTOR_doctorAMKA`),
  CONSTRAINT `fk_APPOINTMENT_DOCTOR` FOREIGN KEY (`DOCTOR_doctorAMKA`) REFERENCES `doctor` (`doctorAMKA`),
  CONSTRAINT `fk_APPOINTMENT_PATIENT` FOREIGN KEY (`PATIENT_patientAMKA`) REFERENCES `patient` (`patientAMKA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('2017-06-15','17:00:00','17:30:00','00000000002','10000000002'),('2017-06-15','18:00:00','18:30:00','00000000003','10000000002'),('2017-07-16','13:00:00','13:30:00','00000000003','10000000003'),('2017-08-19','12:00:00','12:30:00','00000000004','20000000001'),('2017-09-08','12:00:00','12:30:00','00000000001','10000000001'),('2017-09-08','18:00:00','18:30:00','00000000005','20000000001'),('2018-08-13','18:00:00','18:30:00','00000000001','10000000003'),('2021-06-01','19:00:00','19:30:00','00000000001','20000000001'),('2021-06-07','10:00:00','10:30:00','00000000001','20000000001'),('2021-06-07','17:00:00','17:30:00','00000000001','10000000003'),('2021-07-01','18:00:00','18:30:00','00000000001','10000000003'),('2021-07-01','18:30:00','19:00:00','00000000001','10000000002'),('2021-07-09','18:30:00','19:00:00','0','10000000002'),('2021-07-10','18:30:00','19:00:00','0','10000000001');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-01 17:40:43
