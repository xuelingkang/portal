-- MySQL dump 10.13  Distrib 8.0.21, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: portal
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

--
-- Table structure for table `t_attachment`
--

DROP TABLE IF EXISTS `t_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_attachment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '附件类型',
  `name` varchar(260) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '附件名称',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '访问路径',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '磁盘路径',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='附件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_attachment`
--

LOCK TABLES `t_attachment` WRITE;
/*!40000 ALTER TABLE `t_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_authority`
--

DROP TABLE IF EXISTS `t_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_authority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `protocol` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '协议类型',
  `category` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限分组',
  `seq` int NOT NULL DEFAULT '0' COMMENT '权限顺序',
  `pattern` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '权限的ant path',
  `method` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求方法',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_authority`
--

LOCK TABLES `t_authority` WRITE;
/*!40000 ALTER TABLE `t_authority` DISABLE KEYS */;
INSERT INTO `t_authority` VALUES (1,'HTTP','SYSTEM',100,'/**','ALL','超级权限'),(2,'HTTP','AUTHORIZATION',100,'/website/user','POST','注册'),(3,'HTTP','AUTHORIZATION',200,'/login','POST','登录'),(4,'HTTP','AUTHORIZATION',300,'/user/reset-password','GET','获取重置密码链接'),(5,'HTTP','AUTHORIZATION',400,'/user/reset-password','PATCH','重置账户密码'),(6,'HTTP','AUTHORIZATION',500,'/logout','GET','登出'),(7,'HTTP','USER',100,'/user','GET','分页查询用户'),(8,'HTTP','USER',200,'/user/*','GET','根据id查询用户'),(9,'HTTP','USER',300,'/user','POST','保存用户'),(10,'HTTP','USER',400,'/user','PUT','更新用户'),(11,'HTTP','USER',500,'/user/personal','PUT','更新个人信息'),(12,'HTTP','USER',600,'/user/lock','PATCH','锁定用户账户'),(13,'HTTP','USER',700,'/user/unlock','PATCH','解锁用户账户'),(14,'HTTP','USER',800,'/user','DELETE','删除用户账户'),(15,'HTTP','USER',900,'/user/password','PATCH','修改用户账户密码'),(16,'HTTP','USER',1000,'/user/personal/password','PATCH','修改个人账户密码'),(17,'HTTP','USER',1100,'/user/*/role','POST','更新用户角色'),(18,'HTTP','ROLE',100,'/role','GET','分页查询角色'),(19,'HTTP','ROLE',200,'/role/*','GET','根据id查询角色'),(20,'HTTP','ROLE',300,'/role','POST','保存角色'),(21,'HTTP','ROLE',400,'/role','PUT','更新角色'),(22,'HTTP','ROLE',500,'/role','DELETE','删除角色'),(23,'HTTP','ROLE',600,'/role/*/authority','POST','更新角色权限'),(24,'HTTP','AUTHORITY',100,'/authority','GET','分页查询权限'),(25,'HTTP','AUTHORITY',200,'/authority/*','GET','根据id查询权限'),(26,'HTTP','AUTHORITY',300,'/authority','POST','保存权限'),(27,'HTTP','AUTHORITY',400,'/authority','PUT','更新权限'),(28,'HTTP','AUTHORITY',500,'/authority','DELETE','删除权限'),(29,'HTTP','ENUM',100,'/enum','GET','获取所有枚举'),(30,'HTTP','ENUM',200,'/enum/*','GET','根据类名获取枚举'),(31,'HTTP','ATTACHMENT',100,'/attachment/*','POST','上传附件'),(32,'HTTP','ATTACHMENT',200,'/attachment','GET','分页查询附件'),(33,'HTTP','ATTACHMENT',300,'/attachment/*','GET','根据id查询附件'),(34,'HTTP','ATTACHMENT',400,'/attachment','DELETE','删除附件'),(35,'HTTP','JOB_TEMPLATE',100,'/job-template','GET','分页查询任务模板'),(36,'HTTP','JOB_TEMPLATE',200,'/job-template/*','GET','根据id查询任务模板'),(37,'HTTP','JOB_TEMPLATE',300,'/job-template','POST','保存任务模板'),(38,'HTTP','JOB_TEMPLATE',400,'/job-template','PUT','更新任务模板'),(39,'HTTP','JOB_TEMPLATE',500,'/job-template','DELETE','删除任务模板'),(40,'HTTP','JOB',100,'/job','GET','分页查询定时任务'),(41,'HTTP','JOB',200,'/job/*','GET','根据id查询定时任务'),(42,'HTTP','JOB',300,'/job','POST','保存定时任务'),(43,'HTTP','JOB',400,'/job','PUT','更新定时任务'),(44,'HTTP','JOB',500,'/job','DELETE','删除定时任务'),(45,'HTTP','JOB',600,'/job/pause','PATCH','暂停定时任务'),(46,'HTTP','JOB',700,'/job/resume','PATCH','恢复定时任务'),(47,'HTTP','MAIL',100,'/mail','GET','分页查询邮件'),(48,'HTTP','MAIL',200,'/mail/*','GET','根据id查询邮件'),(49,'HTTP','MAIL',300,'/mail','POST','发送邮件'),(50,'HTTP','MAIL',400,'/mail','DELETE','删除邮件'),(51,'HTTP','USER_LINK',100,'/user-link/idols','GET','查询当前用户的偶像'),(52,'HTTP','USER_LINK',200,'/user-link/followers','GET','查询当前用户的粉丝'),(53,'HTTP','USER_LINK',300,'/user-link/*','POST','添加关注'),(54,'HTTP','USER_LINK',400,'/user-link/*','DELETE','取消关注'),(55,'HTTP','AUTHORIZATION',600,'/user/activate','PATCH','激活账户'),(56,'HTTP','USER',1200,'/user/rebind-email-code','GET','生成重新绑定邮箱验证码'),(57,'HTTP','USER',1300,'/user/rebind-email','PATCH','重新绑定邮箱');
/*!40000 ALTER TABLE `t_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job`
--

DROP TABLE IF EXISTS `t_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_template_id` int NOT NULL COMMENT '任务模板id',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `cron_expression` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'cron表达式',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务描述',
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '调度器名称',
  `trigger_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '触发器名称',
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '触发器组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job`
--

LOCK TABLES `t_job` WRITE;
/*!40000 ALTER TABLE `t_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_parameter`
--

DROP TABLE IF EXISTS `t_job_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_job_parameter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '定时任务id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数名称',
  `value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时任务参数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_parameter`
--

LOCK TABLES `t_job_parameter` WRITE;
/*!40000 ALTER TABLE `t_job_parameter` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_template`
--

DROP TABLE IF EXISTS `t_job_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_job_template` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务模板名称',
  `class_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务模板类名',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务模板描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_template`
--

LOCK TABLES `t_job_template` WRITE;
/*!40000 ALTER TABLE `t_job_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_template_parameter`
--

DROP TABLE IF EXISTS `t_job_template_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_job_template_parameter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_template_id` int NOT NULL COMMENT '任务模板id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数类型',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务模板参数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_template_parameter`
--

LOCK TABLES `t_job_template_parameter` WRITE;
/*!40000 ALTER TABLE `t_job_template_parameter` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_template_parameter` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '角色名称',
  `seq` int NOT NULL DEFAULT '0' COMMENT '角色顺序',
  `guest` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否游客用户的默认角色',
  `website` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否网站用户的默认角色',
  `description` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'ROLE_SUPER',100,0,0,'超级管理员'),(2,'ROLE_GUEST',200,1,0,'游客用户'),(3,'ROLE_WEBSITE',300,0,1,'网站用户'),(4,'ROLE_SYSTEM',400,0,0,'后台用户'),(5,'ROLE_USER',500,0,0,'用户管理员'),(6,'ROLE_ROLE',600,0,0,'角色管理员'),(7,'ROLE_AUTHORITY',700,0,0,'权限管理员'),(8,'ROLE_ATTACHMENT',800,0,0,'附件管理员'),(9,'ROLE_JOB_TEMPLATE',900,0,0,'任务模板管理员'),(10,'ROLE_JOB',1000,0,0,'定时任务管理员'),(11,'ROLE_MAIL',1100,0,0,'邮件管理员');
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_authority_link`
--

DROP TABLE IF EXISTS `t_role_authority_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_authority_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL COMMENT '角色id',
  `authority_id` int NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色权限关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_authority_link`
--

LOCK TABLES `t_role_authority_link` WRITE;
/*!40000 ALTER TABLE `t_role_authority_link` DISABLE KEYS */;
INSERT INTO `t_role_authority_link` VALUES (1,2,2),(2,2,3),(3,2,4),(4,2,5),(8,5,7),(9,5,8),(10,5,9),(11,5,10),(12,5,11),(13,5,12),(14,5,13),(15,5,14),(16,5,15),(17,5,16),(18,5,17),(19,6,18),(20,6,19),(21,6,20),(22,6,21),(23,6,22),(24,6,23),(25,7,24),(26,7,25),(27,7,26),(28,7,27),(29,7,28),(30,4,29),(31,4,30),(32,8,31),(33,8,32),(34,8,33),(35,8,34),(36,9,35),(37,9,36),(38,9,37),(39,9,38),(40,9,39),(41,10,40),(42,10,41),(43,10,42),(44,10,43),(45,10,44),(46,10,45),(47,10,46),(48,11,47),(49,11,48),(50,11,49),(51,11,50),(52,3,51),(53,3,52),(54,3,53),(55,3,54),(56,4,51),(57,4,52),(58,4,53),(59,4,54),(60,2,55),(61,3,56),(62,3,57),(63,4,56),(64,4,57),(65,1,1);
/*!40000 ALTER TABLE `t_role_authority_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'email',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '昵称',
  `sex` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '性别',
  `birth` bigint DEFAULT NULL COMMENT '生日',
  `type` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户类型',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `login_time` bigint DEFAULT NULL COMMENT '最后登录时间',
  `locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定',
  `activated` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否激活',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_uk_username` (`username`),
  UNIQUE KEY `t_user_uk_email` (`email`),
  KEY `t_user_idx_deleted` (`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'admin','$2a$10$uSj1t4ad0oicwP2UqHoX3uw9LZEhJYRsa1BPe/4Q/zztEkHyXq4g6','xuelingkang@163.com','系统管理员','MALE',655833600000,'SYSTEM',1578326122478,1599582101329,0,1,0);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_link`
--

DROP TABLE IF EXISTS `t_user_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idol_id` int NOT NULL COMMENT '偶像id',
  `follower_id` int NOT NULL COMMENT '粉丝id',
  `follow_time` bigint NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`),
  KEY `t_user_link_idx_idol_id` (`idol_id`),
  KEY `t_user_link_idx_follower_id` (`follower_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='关注';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_link`
--

LOCK TABLES `t_user_link` WRITE;
/*!40000 ALTER TABLE `t_user_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_role_link`
--

DROP TABLE IF EXISTS `t_user_role_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_role_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `t_user_role_link_user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role_link`
--

LOCK TABLES `t_user_role_link` WRITE;
/*!40000 ALTER TABLE `t_user_role_link` DISABLE KEYS */;
INSERT INTO `t_user_role_link` VALUES (1,1,1);
/*!40000 ALTER TABLE `t_user_role_link` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-09  0:25:10
