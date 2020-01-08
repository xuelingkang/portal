/*
Navicat MySQL Data Transfer

Source Server         : docker
Source Server Version : 50726
Source Host           : docker:3306
Source Database       : portal

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-01-09 00:32:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `CRON_EXPRESSION` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8mb4_bin DEFAULT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8mb4_bin DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8mb4_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8mb4_bin NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8mb4_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8mb4_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8mb4_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8mb4_bin NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('clusteredScheduler', 'DESKTOP-RKG4OBL1578500790194', '1578501148513', '10000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8mb4_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8mb4_bin NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) COLLATE utf8mb4_bin DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `access` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '访问限制',
  `type` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '类型',
  `category` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '栏目',
  `title` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `outline` varchar(500) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '摘要',
  `visit_times` int(11) NOT NULL DEFAULT '0' COMMENT '访问次数',
  `source` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '来源',
  `source_url` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '来源网址',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `t_article_idx_user_id` (`user_id`),
  KEY `t_article_idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章';

-- ----------------------------
-- Records of t_article
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_collection
-- ----------------------------
DROP TABLE IF EXISTS `t_article_collection`;
CREATE TABLE `t_article_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '收藏夹名称',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章收藏夹';

-- ----------------------------
-- Records of t_article_collection
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_content
-- ----------------------------
DROP TABLE IF EXISTS `t_article_content`;
CREATE TABLE `t_article_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `content` mediumtext COLLATE utf8mb4_bin NOT NULL COMMENT '文章内容',
  PRIMARY KEY (`id`),
  KEY `t_article_content_idx_article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章内容';

-- ----------------------------
-- Records of t_article_content
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_favorite
-- ----------------------------
DROP TABLE IF EXISTS `t_article_favorite`;
CREATE TABLE `t_article_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `favorite_time` bigint(20) NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章收藏';

-- ----------------------------
-- Records of t_article_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标签名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章标签';

-- ----------------------------
-- Records of t_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_tag_link
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag_link`;
CREATE TABLE `t_article_tag_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  `seq` int(1) NOT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`),
  KEY `t_article_tag_link_idx_article_id` (`article_id`),
  KEY `t_article_tag_link_idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章标签关联';

-- ----------------------------
-- Records of t_article_tag_link
-- ----------------------------

-- ----------------------------
-- Table structure for t_attachment
-- ----------------------------
DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '附件类型',
  `name` varchar(260) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '附件名称',
  `url` varchar(500) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '访问路径',
  `address` varchar(500) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '磁盘路径',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='附件';

-- ----------------------------
-- Records of t_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `protocol` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '协议类型',
  `category` char(15) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限分组',
  `seq` int(5) NOT NULL DEFAULT '0' COMMENT '权限顺序',
  `pattern` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '权限的ant path',
  `method` char(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求方法',
  `description` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='权限';

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO `t_authority` VALUES ('1', 'HTTP', 'AUTHORIZATION', '100', '/website/user', 'POST', '注册');
INSERT INTO `t_authority` VALUES ('2', 'HTTP', 'AUTHORIZATION', '200', '/login', 'POST', '登录');
INSERT INTO `t_authority` VALUES ('3', 'HTTP', 'AUTHORIZATION', '300', '/user/reset-password', 'GET', '获取重置密码链接');
INSERT INTO `t_authority` VALUES ('4', 'HTTP', 'AUTHORIZATION', '400', '/user/reset-password', 'PATCH', '重置账户密码');
INSERT INTO `t_authority` VALUES ('5', 'HTTP', 'AUTHORIZATION', '500', '/logout', 'GET', '登出');
INSERT INTO `t_authority` VALUES ('6', 'HTTP', 'USER', '100', '/user', 'GET', '分页查询用户');
INSERT INTO `t_authority` VALUES ('7', 'HTTP', 'USER', '200', '/user/*', 'GET', '根据id查询用户');
INSERT INTO `t_authority` VALUES ('8', 'HTTP', 'USER', '300', '/user', 'POST', '保存用户');
INSERT INTO `t_authority` VALUES ('9', 'HTTP', 'USER', '400', '/user', 'PUT', '更新用户');
INSERT INTO `t_authority` VALUES ('10', 'HTTP', 'USER', '500', '/user/personal', 'PUT', '更新个人信息');
INSERT INTO `t_authority` VALUES ('11', 'HTTP', 'USER', '600', '/user/lock', 'PATCH', '锁定用户账户');
INSERT INTO `t_authority` VALUES ('12', 'HTTP', 'USER', '700', '/user/unlock', 'PATCH', '解锁用户账户');
INSERT INTO `t_authority` VALUES ('13', 'HTTP', 'USER', '800', '/user', 'DELETE', '删除用户账户');
INSERT INTO `t_authority` VALUES ('14', 'HTTP', 'USER', '900', '/user/password', 'PATCH', '修改用户账户密码');
INSERT INTO `t_authority` VALUES ('15', 'HTTP', 'USER', '1000', '/user/personal/password', 'PATCH', '修改个人账户密码');
INSERT INTO `t_authority` VALUES ('16', 'HTTP', 'USER', '1100', '/user/*/role', 'POST', '更新用户角色');
INSERT INTO `t_authority` VALUES ('17', 'HTTP', 'ROLE', '100', '/role', 'GET', '分页查询角色');
INSERT INTO `t_authority` VALUES ('18', 'HTTP', 'ROLE', '200', '/role/*', 'GET', '根据id查询角色');
INSERT INTO `t_authority` VALUES ('19', 'HTTP', 'ROLE', '300', '/role', 'POST', '保存角色');
INSERT INTO `t_authority` VALUES ('20', 'HTTP', 'ROLE', '400', '/role', 'PUT', '更新角色');
INSERT INTO `t_authority` VALUES ('21', 'HTTP', 'ROLE', '500', '/role', 'DELETE', '删除角色');
INSERT INTO `t_authority` VALUES ('22', 'HTTP', 'ROLE', '600', '/role/*/authority', 'POST', '更新角色权限');
INSERT INTO `t_authority` VALUES ('23', 'HTTP', 'AUTHORITY', '100', '/authority', 'GET', '分页查询权限');
INSERT INTO `t_authority` VALUES ('24', 'HTTP', 'AUTHORITY', '200', '/authority/*', 'GET', '根据id查询权限');
INSERT INTO `t_authority` VALUES ('25', 'HTTP', 'AUTHORITY', '300', '/authority', 'POST', '保存权限');
INSERT INTO `t_authority` VALUES ('26', 'HTTP', 'AUTHORITY', '400', '/authority', 'PUT', '更新权限');
INSERT INTO `t_authority` VALUES ('27', 'HTTP', 'AUTHORITY', '500', '/authority', 'DELETE', '删除权限');
INSERT INTO `t_authority` VALUES ('28', 'HTTP', 'ENUM', '100', '/enum', 'GET', '获取所有枚举');
INSERT INTO `t_authority` VALUES ('29', 'HTTP', 'ENUM', '200', '/enum/*', 'GET', '根据类名获取枚举');
INSERT INTO `t_authority` VALUES ('30', 'HTTP', 'ATTACHMENT', '100', '/attachment/*', 'POST', '上传附件');
INSERT INTO `t_authority` VALUES ('31', 'HTTP', 'ATTACHMENT', '200', '/attachment', 'GET', '分页查询附件');
INSERT INTO `t_authority` VALUES ('32', 'HTTP', 'ATTACHMENT', '300', '/attachment/*', 'GET', '根据id查询附件');
INSERT INTO `t_authority` VALUES ('33', 'HTTP', 'ATTACHMENT', '400', '/attachment', 'DELETE', '删除附件');
INSERT INTO `t_authority` VALUES ('34', 'HTTP', 'JOB_TEMPLATE', '100', '/job-template', 'GET', '分页查询任务模板');
INSERT INTO `t_authority` VALUES ('35', 'HTTP', 'JOB_TEMPLATE', '200', '/job-template/*', 'GET', '根据id查询任务模板');
INSERT INTO `t_authority` VALUES ('36', 'HTTP', 'JOB_TEMPLATE', '300', '/job-template', 'POST', '保存任务模板');
INSERT INTO `t_authority` VALUES ('37', 'HTTP', 'JOB_TEMPLATE', '400', '/job-template', 'PUT', '更新任务模板');
INSERT INTO `t_authority` VALUES ('38', 'HTTP', 'JOB_TEMPLATE', '500', '/job-template', 'DELETE', '删除任务模板');
INSERT INTO `t_authority` VALUES ('39', 'HTTP', 'JOB', '100', '/job', 'GET', '分页查询定时任务');
INSERT INTO `t_authority` VALUES ('40', 'HTTP', 'JOB', '200', '/job/*', 'GET', '根据id查询定时任务');
INSERT INTO `t_authority` VALUES ('41', 'HTTP', 'JOB', '300', '/job', 'POST', '保存定时任务');
INSERT INTO `t_authority` VALUES ('42', 'HTTP', 'JOB', '400', '/job', 'PUT', '更新定时任务');
INSERT INTO `t_authority` VALUES ('43', 'HTTP', 'JOB', '500', '/job', 'DELETE', '删除定时任务');
INSERT INTO `t_authority` VALUES ('44', 'HTTP', 'JOB', '600', '/job/pause', 'PATCH', '暂停定时任务');
INSERT INTO `t_authority` VALUES ('45', 'HTTP', 'JOB', '700', '/job/resume', 'PATCH', '恢复定时任务');
INSERT INTO `t_authority` VALUES ('46', 'HTTP', 'MAIL', '100', '/mail', 'GET', '分页查询邮件');
INSERT INTO `t_authority` VALUES ('47', 'HTTP', 'MAIL', '200', '/mail/*', 'GET', '根据id查询邮件');
INSERT INTO `t_authority` VALUES ('48', 'HTTP', 'MAIL', '300', '/mail', 'POST', '发送邮件');
INSERT INTO `t_authority` VALUES ('49', 'HTTP', 'MAIL', '400', '/mail', 'DELETE', '删除邮件');
INSERT INTO `t_authority` VALUES ('50', 'HTTP', 'USER_LINK', '100', '/user-link/idols', 'GET', '查询当前用户的偶像');
INSERT INTO `t_authority` VALUES ('51', 'HTTP', 'USER_LINK', '200', '/user-link/followers', 'GET', '查询当前用户的粉丝');
INSERT INTO `t_authority` VALUES ('52', 'HTTP', 'USER_LINK', '300', '/user-link/*', 'POST', '添加关注');
INSERT INTO `t_authority` VALUES ('53', 'HTTP', 'USER_LINK', '400', '/user-link/*', 'DELETE', '取消关注');
INSERT INTO `t_authority` VALUES ('54', 'HTTP', 'AUTHORIZATION', '600', '/user/activate', 'PATCH', '激活账户');
INSERT INTO `t_authority` VALUES ('55', 'HTTP', 'USER', '1200', '/user/rebind-email-code', 'GET', '生成重新绑定邮箱验证码');
INSERT INTO `t_authority` VALUES ('56', 'HTTP', 'USER', '1300', '/user/rebind-email', 'PATCH', '重新绑定邮箱');

-- ----------------------------
-- Table structure for t_job
-- ----------------------------
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_template_id` int(11) NOT NULL COMMENT '任务模板id',
  `start_time` bigint(20) NOT NULL COMMENT '开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `cron_expression` char(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'cron表达式',
  `description` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务描述',
  `sched_name` varchar(120) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '调度器名称',
  `trigger_name` varchar(190) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '触发器名称',
  `trigger_group` varchar(190) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '触发器组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时任务';

-- ----------------------------
-- Records of t_job
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_job_parameter`;
CREATE TABLE `t_job_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '定时任务id',
  `name` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数名称',
  `value` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时任务参数';

-- ----------------------------
-- Records of t_job_parameter
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_template
-- ----------------------------
DROP TABLE IF EXISTS `t_job_template`;
CREATE TABLE `t_job_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务模板名称',
  `class_name` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务模板类名',
  `description` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务模板描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务模板';

-- ----------------------------
-- Records of t_job_template
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_template_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_job_template_parameter`;
CREATE TABLE `t_job_template_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_template_id` int(11) NOT NULL COMMENT '任务模板id',
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数名称',
  `type` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数类型',
  `description` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务模板参数';

-- ----------------------------
-- Records of t_job_template_parameter
-- ----------------------------

-- ----------------------------
-- Table structure for t_mail
-- ----------------------------
DROP TABLE IF EXISTS `t_mail`;
CREATE TABLE `t_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮件标题',
  `type` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮件类型',
  `status` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮件状态',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `send_user_id` int(11) NOT NULL COMMENT '发送用户id',
  `to_user_ids` varchar(1200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '接收用户id，最多100个，优先级大于接收邮箱',
  `to_email` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '接收邮箱',
  `attachment_ids` varchar(1200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '附件id，最多100个',
  PRIMARY KEY (`id`),
  KEY `t_mail_idx_type` (`type`),
  KEY `t_mail_idx_status` (`status`),
  KEY `t_mail_idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='邮件';

-- ----------------------------
-- Records of t_mail
-- ----------------------------

-- ----------------------------
-- Table structure for t_mail_content
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_content`;
CREATE TABLE `t_mail_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail_id` int(11) NOT NULL COMMENT '邮件id',
  `content` text COLLATE utf8mb4_bin COMMENT '邮件内容',
  PRIMARY KEY (`id`),
  KEY `t_mail_content_idx_mail_id` (`mail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='邮件内容';

-- ----------------------------
-- Records of t_mail_content
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '角色名称',
  `seq` int(5) NOT NULL DEFAULT '0' COMMENT '角色顺序',
  `guest` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否游客用户的默认角色',
  `website` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否网站用户的默认角色',
  `description` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'ROLE_GUEST', '100', '1', '0', '游客用户');
INSERT INTO `t_role` VALUES ('2', 'ROLE_WEBSITE', '200', '0', '1', '网站用户');
INSERT INTO `t_role` VALUES ('3', 'ROLE_SYSTEM', '300', '0', '0', '后台用户');
INSERT INTO `t_role` VALUES ('4', 'ROLE_USER', '400', '0', '0', '用户管理员');
INSERT INTO `t_role` VALUES ('5', 'ROLE_ROLE', '500', '0', '0', '角色管理员');
INSERT INTO `t_role` VALUES ('6', 'ROLE_AUTHORITY', '600', '0', '0', '权限管理员');
INSERT INTO `t_role` VALUES ('7', 'ROLE_ATTACHMENT', '700', '0', '0', '附件管理员');
INSERT INTO `t_role` VALUES ('8', 'ROLE_JOB_TEMPLATE', '800', '0', '0', '任务模板管理员');
INSERT INTO `t_role` VALUES ('9', 'ROLE_JOB', '900', '0', '0', '定时任务管理员');
INSERT INTO `t_role` VALUES ('10', 'ROLE_MAIL', '1000', '0', '0', '邮件管理员');

-- ----------------------------
-- Table structure for t_role_authority_link
-- ----------------------------
DROP TABLE IF EXISTS `t_role_authority_link`;
CREATE TABLE `t_role_authority_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `authority_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色权限关联';

-- ----------------------------
-- Records of t_role_authority_link
-- ----------------------------
INSERT INTO `t_role_authority_link` VALUES ('1', '1', '1');
INSERT INTO `t_role_authority_link` VALUES ('2', '1', '2');
INSERT INTO `t_role_authority_link` VALUES ('3', '1', '3');
INSERT INTO `t_role_authority_link` VALUES ('4', '1', '4');
INSERT INTO `t_role_authority_link` VALUES ('8', '4', '6');
INSERT INTO `t_role_authority_link` VALUES ('9', '4', '7');
INSERT INTO `t_role_authority_link` VALUES ('10', '4', '8');
INSERT INTO `t_role_authority_link` VALUES ('11', '4', '9');
INSERT INTO `t_role_authority_link` VALUES ('12', '4', '10');
INSERT INTO `t_role_authority_link` VALUES ('13', '4', '11');
INSERT INTO `t_role_authority_link` VALUES ('14', '4', '12');
INSERT INTO `t_role_authority_link` VALUES ('15', '4', '13');
INSERT INTO `t_role_authority_link` VALUES ('16', '4', '14');
INSERT INTO `t_role_authority_link` VALUES ('17', '4', '15');
INSERT INTO `t_role_authority_link` VALUES ('18', '4', '16');
INSERT INTO `t_role_authority_link` VALUES ('19', '5', '17');
INSERT INTO `t_role_authority_link` VALUES ('20', '5', '18');
INSERT INTO `t_role_authority_link` VALUES ('21', '5', '19');
INSERT INTO `t_role_authority_link` VALUES ('22', '5', '20');
INSERT INTO `t_role_authority_link` VALUES ('23', '5', '21');
INSERT INTO `t_role_authority_link` VALUES ('24', '5', '22');
INSERT INTO `t_role_authority_link` VALUES ('25', '6', '23');
INSERT INTO `t_role_authority_link` VALUES ('26', '6', '24');
INSERT INTO `t_role_authority_link` VALUES ('27', '6', '25');
INSERT INTO `t_role_authority_link` VALUES ('28', '6', '26');
INSERT INTO `t_role_authority_link` VALUES ('29', '6', '27');
INSERT INTO `t_role_authority_link` VALUES ('30', '3', '28');
INSERT INTO `t_role_authority_link` VALUES ('31', '3', '29');
INSERT INTO `t_role_authority_link` VALUES ('32', '7', '30');
INSERT INTO `t_role_authority_link` VALUES ('33', '7', '31');
INSERT INTO `t_role_authority_link` VALUES ('34', '7', '32');
INSERT INTO `t_role_authority_link` VALUES ('35', '7', '33');
INSERT INTO `t_role_authority_link` VALUES ('36', '8', '34');
INSERT INTO `t_role_authority_link` VALUES ('37', '8', '35');
INSERT INTO `t_role_authority_link` VALUES ('38', '8', '36');
INSERT INTO `t_role_authority_link` VALUES ('39', '8', '37');
INSERT INTO `t_role_authority_link` VALUES ('40', '8', '38');
INSERT INTO `t_role_authority_link` VALUES ('41', '9', '39');
INSERT INTO `t_role_authority_link` VALUES ('42', '9', '40');
INSERT INTO `t_role_authority_link` VALUES ('43', '9', '41');
INSERT INTO `t_role_authority_link` VALUES ('44', '9', '42');
INSERT INTO `t_role_authority_link` VALUES ('45', '9', '43');
INSERT INTO `t_role_authority_link` VALUES ('46', '9', '44');
INSERT INTO `t_role_authority_link` VALUES ('47', '9', '45');
INSERT INTO `t_role_authority_link` VALUES ('48', '10', '46');
INSERT INTO `t_role_authority_link` VALUES ('49', '10', '47');
INSERT INTO `t_role_authority_link` VALUES ('50', '10', '48');
INSERT INTO `t_role_authority_link` VALUES ('51', '10', '49');
INSERT INTO `t_role_authority_link` VALUES ('52', '2', '50');
INSERT INTO `t_role_authority_link` VALUES ('53', '2', '51');
INSERT INTO `t_role_authority_link` VALUES ('54', '2', '52');
INSERT INTO `t_role_authority_link` VALUES ('55', '2', '53');
INSERT INTO `t_role_authority_link` VALUES ('56', '3', '50');
INSERT INTO `t_role_authority_link` VALUES ('57', '3', '51');
INSERT INTO `t_role_authority_link` VALUES ('58', '3', '52');
INSERT INTO `t_role_authority_link` VALUES ('59', '3', '53');
INSERT INTO `t_role_authority_link` VALUES ('60', '1', '54');
INSERT INTO `t_role_authority_link` VALUES ('61', '2', '55');
INSERT INTO `t_role_authority_link` VALUES ('62', '2', '56');
INSERT INTO `t_role_authority_link` VALUES ('63', '3', '55');
INSERT INTO `t_role_authority_link` VALUES ('64', '3', '56');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(60) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'email',
  `nickname` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '昵称',
  `sex` char(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '性别',
  `birth` bigint(20) DEFAULT NULL COMMENT '生日',
  `type` char(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户类型',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `login_time` bigint(20) DEFAULT NULL COMMENT '最后登录时间',
  `locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定',
  `activated` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否激活',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_uk_username` (`username`),
  UNIQUE KEY `t_user_uk_email` (`email`),
  KEY `t_user_idx_deleted` (`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '$2a$10$Fi6PLQ1AKP6xKTNwlJDQZ.FKhi8UPIleMrCXKTnIt9/epvABZKyVe', 'xuelingkang@163.com', '系统管理员', 'MALE', '655833600000', 'SYSTEM', '1578326122478', '1578500831158', '0', '1', '0');

-- ----------------------------
-- Table structure for t_user_link
-- ----------------------------
DROP TABLE IF EXISTS `t_user_link`;
CREATE TABLE `t_user_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idol_id` int(11) NOT NULL COMMENT '偶像id',
  `follower_id` int(11) NOT NULL COMMENT '粉丝id',
  `follow_time` bigint(20) NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`),
  KEY `t_user_link_idx_idol_id` (`idol_id`),
  KEY `t_user_link_idx_follower_id` (`follower_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='关注';

-- ----------------------------
-- Records of t_user_link
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_role_link
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role_link`;
CREATE TABLE `t_user_role_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `t_user_role_link_user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色关联';

-- ----------------------------
-- Records of t_user_role_link
-- ----------------------------
INSERT INTO `t_user_role_link` VALUES ('1', '1', '3');
INSERT INTO `t_user_role_link` VALUES ('2', '1', '4');
INSERT INTO `t_user_role_link` VALUES ('3', '1', '5');
INSERT INTO `t_user_role_link` VALUES ('4', '1', '6');
INSERT INTO `t_user_role_link` VALUES ('5', '1', '7');
INSERT INTO `t_user_role_link` VALUES ('6', '1', '8');
INSERT INTO `t_user_role_link` VALUES ('7', '1', '9');
