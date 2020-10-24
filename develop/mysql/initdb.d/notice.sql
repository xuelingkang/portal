SET CHARACTER SET utf8mb4;
CREATE DATABASE portal_notice DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
USE portal_notice;

-- MySQL dump 10.13  Distrib 8.0.21, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: portal_content
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `t_mail`
--

DROP TABLE IF EXISTS `t_mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_mail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮件标题',
  `type` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮件类型',
  `status` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮件状态',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `send_user_id` int NOT NULL COMMENT '发送用户id',
  `to_user_ids` varchar(1200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '接收用户id，最多100个，优先级大于接收邮箱',
  `to_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '接收邮箱',
  `attachment_ids` varchar(1200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '附件id，最多100个',
  PRIMARY KEY (`id`),
  KEY `t_mail_idx_type` (`type`),
  KEY `t_mail_idx_status` (`status`),
  KEY `t_mail_idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='邮件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_mail`
--

LOCK TABLES `t_mail` WRITE;
/*!40000 ALTER TABLE `t_mail` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_mail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_mail_content`
--

DROP TABLE IF EXISTS `t_mail_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_mail_content` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mail_id` int NOT NULL COMMENT '邮件id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '邮件内容',
  PRIMARY KEY (`id`),
  KEY `t_mail_content_idx_mail_id` (`mail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='邮件内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_mail_content`
--

LOCK TABLES `t_mail_content` WRITE;
/*!40000 ALTER TABLE `t_mail_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_mail_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-24 17:55:29
