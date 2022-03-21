-- MySQL dump 10.17  Distrib 10.3.13-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: coding_ex_db
-- ------------------------------------------------------
-- Server version	10.3.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_employee`
--

DROP TABLE IF EXISTS `t_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_employee` (
  `EmployeeID` int(11) NOT NULL AUTO_INCREMENT,
  `Employee_Name` varchar(200) NOT NULL,
  `Title` varchar(10) NOT NULL,
  `Business_Unit` varchar(100) NOT NULL,
  `Place` varchar(100) NOT NULL,
  `SupervisorID` int(11) DEFAULT NULL,
  `Competencies` varchar(100) NOT NULL,
  `Salary` int(11) NOT NULL,
  PRIMARY KEY (`EmployeeID`),
  KEY `fk_supervisor_id` (`SupervisorID`),
  CONSTRAINT `fk_supervisor_id` FOREIGN KEY (`SupervisorID`) REFERENCES `t_employee` (`EmployeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employee`
--

LOCK TABLES `t_employee` WRITE;
/*!40000 ALTER TABLE `t_employee` DISABLE KEYS */;
INSERT INTO `t_employee` VALUES (1,'Supervisor1','Mr.','BU1','Patna',NULL,'COMP1',2000000);
INSERT INTO `t_employee` VALUES (2,'Mgr1','Mrs.','BU1','Delhi',1,'COMP1',200000);
INSERT INTO `t_employee` VALUES (3,'Mgr2','Mrs.','BU2','Noida',1,'COMP2',204000);
INSERT INTO `t_employee` VALUES (4,'Emp1','Mr.','BU1','Gurgaon',1,'COMP1',169793);
INSERT INTO `t_employee` VALUES (5,'Emp2','Mrs.','BU1','Delhi',1,'COMP1',170000);
INSERT INTO `t_employee` VALUES (6,'Emp3','Mr.','BU1','Mumbai',1,'COMP1',180000);
INSERT INTO `t_employee` VALUES (7,'Emp4','Mr.','BU2','Bangalore',2,'COMP2',120000);
INSERT INTO `t_employee` VALUES (8,'Emp5','Mrs.','BU2','Chennai',2,'COMP2',140000);
INSERT INTO `t_employee` VALUES (9,'Emp6','Mrs.','BU2','Kolkata',2,'COMP2',150000);
INSERT INTO `t_employee` VALUES (10,'Emp7','Mrs.','BU2','Pune',2,'COMP2',160000);
INSERT INTO `t_employee` VALUES (11,'Emp8','Mr.','BU2','Gurgaon',2,'COMP2',106120);
INSERT INTO `t_employee` VALUES (12,'Emp9','Mr.','BU2','Gurgaon',2,'COMP2',106120);
INSERT INTO `t_employee` VALUES (13,'Emp10','Mr.','BU2','Gurgaon',2,'COMP2',116732);
INSERT INTO `t_employee` VALUES (14,'Emp11','Mr.','BU2','Gurgaon',2,'COMP2',116732);
INSERT INTO `t_employee` VALUES (15,'Emp12','Mrs.','BU2','Gurgaon',2,'COMP2',122038);
INSERT INTO `t_employee` VALUES (16,'Emp13','Mrs.','BU2','Gurgaon',2,'COMP2',132651);
INSERT INTO `t_employee` VALUES (17,'Emp14','Mrs.','BU2','Gurgaon',2,'COMP2',143263);
INSERT INTO `t_employee` VALUES (18,'Emp15','Mrs.','BU2','Gurgaon',2,'COMP2',132651);
INSERT INTO `t_employee` VALUES (19,'Emp16','Mrs.','BU2','Gurgaon',2,'COMP2',143263);
INSERT INTO `t_employee` VALUES (20,'Emp17','Mrs.','BU2','Gurgaon',2,'COMP2',132651);
INSERT INTO `t_employee` VALUES (21,'Emp18','Mrs.','BU3','Chennai',2,'COMP3',180000);
INSERT INTO `t_employee` VALUES (22,'Emp19','Mrs.','BU3','Chennai',2,'COMP3',135000);
INSERT INTO `t_employee` VALUES (23,'Emp20','Mrs.','BU3','Chennai',2,'COMP3',125000);
INSERT INTO `t_employee` VALUES (24,'Emp21','Mr.','BU3','Chennai',2,'COMP3',125000);
/*!40000 ALTER TABLE `t_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'coding_ex_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-02 16:17:44
