-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: stu_manage
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `tb_admin`
--

DROP TABLE IF EXISTS `tb_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_admin`
--

LOCK TABLES `tb_admin` WRITE;
/*!40000 ALTER TABLE `tb_admin` DISABLE KEYS */;
INSERT INTO `tb_admin` VALUES ('admin','12345'),('auv','$2a$10$xRkWcufSKpbzHsZbQIxkYuwn9JO3CuYmY/3vyRYul30t.bBlbx9ym');
/*!40000 ALTER TABLE `tb_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_clazz`
--

DROP TABLE IF EXISTS `tb_clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_clazz` (
  `clazzno` varchar(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`clazzno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_clazz`
--

LOCK TABLES `tb_clazz` WRITE;
/*!40000 ALTER TABLE `tb_clazz` DISABLE KEYS */;
INSERT INTO `tb_clazz` VALUES ('1001','class1'),('1002','class2'),('1003','class3'),('1004','class4'),('1005','class5');
/*!40000 ALTER TABLE `tb_clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_course`
--

DROP TABLE IF EXISTS `tb_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_course` (
  `cno` varchar(20) NOT NULL,
  `tno` varchar(20) DEFAULT NULL,
  `cname` varchar(20) DEFAULT NULL,
  `begindate` date DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `credits` decimal(3,1) DEFAULT NULL,
  `maximum` int DEFAULT NULL,
  `count` int DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cno`),
  KEY `frn_course_teacher` (`tno`),
  CONSTRAINT `frn_course_teacher` FOREIGN KEY (`tno`) REFERENCES `tb_teacher` (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_course`
--

LOCK TABLES `tb_course` WRITE;
/*!40000 ALTER TABLE `tb_course` DISABLE KEYS */;
INSERT INTO `tb_course` VALUES ('A001','001','Politics','2026-05-01','2026-07-23',4.0,50,48,'PoliticsLearning'),('A002','001','Mathematics','2026-05-01','2026-07-23',4.0,50,48,'MathematicsLearning'),('A003','001','OOP','2024-05-01','2025-07-23',4.0,50,48,'JavaCodingLearning\n'),('A004','001','English','2026-05-01','2026-07-23',4.0,50,48,'EnglishLearning'),('A005','001','IrelandCulture','2026-05-01','2026-07-23',4.0,50,48,'IrelandCultureLearning'),('A006','001','PE','2026-05-01','2026-07-23',4.0,50,48,'PELearning'),('A007','001','Economy','2026-05-01','2026-07-23',4.0,50,48,'EconomyLearning');
/*!40000 ALTER TABLE `tb_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_notice`
--

DROP TABLE IF EXISTS `tb_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `publisher` varchar(20) NOT NULL,
  `create_time` date DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`),
  KEY `frn_notice_admin` (`publisher`),
  CONSTRAINT `frn_notice_admin` FOREIGN KEY (`publisher`) REFERENCES `tb_admin` (`username`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_notice`
--

LOCK TABLES `tb_notice` WRITE;
/*!40000 ALTER TABLE `tb_notice` DISABLE KEYS */;
INSERT INTO `tb_notice` VALUES (1,'auv','2026-06-18','今天不上早八'),(2,'auv','2026-06-27','今天大家看世界杯不上课'),(3,'admin','2026-05-13','哈哈哈骗你的');
/*!40000 ALTER TABLE `tb_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_stu_course`
--

DROP TABLE IF EXISTS `tb_stu_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_stu_course` (
  `cno` varchar(20) NOT NULL,
  `sno` varchar(20) NOT NULL,
  `chosetime` date DEFAULT NULL,
  `score` decimal(5,2) DEFAULT NULL,
  `evaluation` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cno`,`sno`),
  KEY `frn_chose_student` (`sno`),
  CONSTRAINT `frn_chose_course` FOREIGN KEY (`cno`) REFERENCES `tb_course` (`cno`),
  CONSTRAINT `frn_chose_student` FOREIGN KEY (`sno`) REFERENCES `tb_student` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_stu_course`
--

LOCK TABLES `tb_stu_course` WRITE;
/*!40000 ALTER TABLE `tb_stu_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_stu_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_student`
--

DROP TABLE IF EXISTS `tb_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_student` (
  `sno` varchar(20) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `tele` char(11) DEFAULT NULL,
  `enterdate` date DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `clazzno` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sno`),
  KEY `frn_stu_clazz` (`clazzno`),
  CONSTRAINT `frn_stu_clazz` FOREIGN KEY (`clazzno`) REFERENCES `tb_clazz` (`clazzno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_student`
--

LOCK TABLES `tb_student` WRITE;
/*!40000 ALTER TABLE `tb_student` DISABLE KEYS */;
INSERT INTO `tb_student` VALUES ('20240101','20240101','Dengtianyao','1234567890',NULL,18,'男','BDIC','1001'),('20240102','20240102','Wuguannan','1234567890',NULL,18,'男','BDIC','1001'),('20240103','20240103','Guoyi','1234567890',NULL,18,'女','BDIC','1001'),('20240201','$2a$10$19RU3/inr8OfjH6O5t2WmOLfkBKJlFRv93QI5bI4N6TblF4ya/.3.','Hanyuchen','1234567890',NULL,18,'女','BDIC','1002'),('20240202','20240202','Hanyuxiao','1234567890',NULL,18,'男','BDIC','1002'),('20240203','20240203','Hanyunima','1234567890',NULL,18,'女','BDIC','1002'),('20240301','20240301','Donk','1234567890',NULL,18,'男','BDIC','1003'),('20240302','20240302','Danking','1234567890',NULL,18,'男','BDIC','1003'),('20240303','20240303','Faerkong','1234567890',NULL,18,'女','BDIC','1003'),('20240401','20240401','Liuli','1234567890',NULL,18,'女','BDIC','1004'),('20240402','20240402','Zhangrao','1234567890',NULL,18,'女','BDIC','1004'),('20240403','20240403','Fujing','1234567890',NULL,18,'女','BDIC','1004'),('20240501','20240501','ZhengAndy','1234567890',NULL,18,'男','BDIC','1005'),('20240502','20240502','Nige','1234567890',NULL,18,'男','BDIC','1005'),('20240503','20240503','Tiger','1234567890',NULL,18,'男','BDIC','1005');
/*!40000 ALTER TABLE `tb_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_student_archive`
--

DROP TABLE IF EXISTS `tb_student_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_student_archive` (
  `archive_id` varchar(20) NOT NULL,
  `sno` varchar(20) NOT NULL,
  `id_card` char(18) NOT NULL,
  `nationality` varchar(20) DEFAULT NULL,
  `birth_place` varchar(50) DEFAULT NULL,
  `political_status` varchar(20) DEFAULT NULL,
  `enrollment_type` varchar(20) DEFAULT NULL,
  `major` varchar(50) DEFAULT NULL,
  `clazzno` varchar(20) DEFAULT NULL,
  `graduation_school` varchar(50) DEFAULT NULL,
  `archive_status` tinyint NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `guardian_name` varchar(20) DEFAULT NULL,
  `guardian_phone` char(11) DEFAULT NULL,
  PRIMARY KEY (`archive_id`),
  UNIQUE KEY `sno` (`sno`),
  UNIQUE KEY `id_card` (`id_card`),
  CONSTRAINT `tb_student_archive_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `tb_student` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_student_archive`
--

LOCK TABLES `tb_student_archive` WRITE;
/*!40000 ALTER TABLE `tb_student_archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_student_archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_teacher`
--

DROP TABLE IF EXISTS `tb_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_teacher` (
  `tno` varchar(20) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `tname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_teacher`
--

LOCK TABLES `tb_teacher` WRITE;
/*!40000 ALTER TABLE `tb_teacher` DISABLE KEYS */;
INSERT INTO `tb_teacher` VALUES ('001','123','Mathew'),('002','123','Paul'),('003','123','Jeferri'),('004','123','Emily'),('005','123','Imilio'),('006','123','Peter'),('007','123','Eco'),('008','123','Armstrong');
/*!40000 ALTER TABLE `tb_teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-28 22:21:38
