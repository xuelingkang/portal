SET CHARACTER SET utf8mb4;
CREATE DATABASE portal_content DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
USE portal_content;

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
-- Table structure for table `t_article`
--

DROP TABLE IF EXISTS `t_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `access` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '访问限制',
  `type` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '类型',
  `category` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '栏目',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `outline` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '摘要',
  `visit_times` int NOT NULL DEFAULT '0' COMMENT '访问次数',
  `finished` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否完成',
  `source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '来源',
  `source_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '来源网址',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `update_time` bigint NOT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `t_article_idx_user_id` (`user_id`),
  KEY `t_article_idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article`
--

LOCK TABLES `t_article` WRITE;
/*!40000 ALTER TABLE `t_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_collection`
--

DROP TABLE IF EXISTS `t_article_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article_collection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '收藏夹名称',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章收藏夹';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_collection`
--

LOCK TABLES `t_article_collection` WRITE;
/*!40000 ALTER TABLE `t_article_collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_content`
--

DROP TABLE IF EXISTS `t_article_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article_content` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL COMMENT '文章id',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文章内容',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_article_content_idx_article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_content`
--

LOCK TABLES `t_article_content` WRITE;
/*!40000 ALTER TABLE `t_article_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_favorite`
--

DROP TABLE IF EXISTS `t_article_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article_favorite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL COMMENT '文章id',
  `favorite_time` bigint NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章收藏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_favorite`
--

LOCK TABLES `t_article_favorite` WRITE;
/*!40000 ALTER TABLE `t_article_favorite` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article_favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_tag`
--

DROP TABLE IF EXISTS `t_article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article_tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标签名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_tag`
--

LOCK TABLES `t_article_tag` WRITE;
/*!40000 ALTER TABLE `t_article_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_tag_link`
--

DROP TABLE IF EXISTS `t_article_tag_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article_tag_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL COMMENT '文章id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `seq` int NOT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`),
  KEY `t_article_tag_link_idx_article_id` (`article_id`),
  KEY `t_article_tag_link_idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章标签关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_tag_link`
--

LOCK TABLES `t_article_tag_link` WRITE;
/*!40000 ALTER TABLE `t_article_tag_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article_tag_link` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-24 17:33:27
