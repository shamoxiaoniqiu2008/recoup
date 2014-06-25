# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.11)
# Database: recoup_db
# Generation Time: 2014-06-25 15:59:17 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table columns
# ------------------------------------------------------------

DROP VIEW IF EXISTS `columns`;

CREATE TABLE `columns` (
   `TABLE_CATALOG` VARCHAR(512) NOT NULL DEFAULT '',
   `TABLE_SCHEMA` VARCHAR(64) NOT NULL DEFAULT '',
   `TABLE_NAME` VARCHAR(64) NOT NULL DEFAULT '',
   `COLUMN_NAME` VARCHAR(64) NOT NULL DEFAULT '',
   `ORDINAL_POSITION` BIGINT(21) UNSIGNED NOT NULL DEFAULT '0',
   `COLUMN_DEFAULT` LONGTEXT NULL DEFAULT NULL,
   `IS_NULLABLE` VARCHAR(3) NOT NULL DEFAULT '',
   `DATA_TYPE` VARCHAR(64) NOT NULL DEFAULT '',
   `CHARACTER_MAXIMUM_LENGTH` BIGINT(21) UNSIGNED NULL DEFAULT NULL,
   `CHARACTER_OCTET_LENGTH` BIGINT(21) UNSIGNED NULL DEFAULT NULL,
   `NUMERIC_PRECISION` BIGINT(21) UNSIGNED NULL DEFAULT NULL,
   `NUMERIC_SCALE` BIGINT(21) UNSIGNED NULL DEFAULT NULL,
   `CHARACTER_SET_NAME` VARCHAR(32) NULL DEFAULT NULL,
   `COLLATION_NAME` VARCHAR(32) NULL DEFAULT NULL,
   `COLUMN_TYPE` LONGTEXT NOT NULL,
   `COLUMN_KEY` VARCHAR(3) NOT NULL DEFAULT '',
   `EXTRA` VARCHAR(30) NOT NULL DEFAULT '',
   `PRIVILEGES` VARCHAR(80) NOT NULL DEFAULT '',
   `COLUMN_COMMENT` VARCHAR(1024) NOT NULL DEFAULT ''
) ENGINE=MyISAM;



# Dump of table pension_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_dept`;

CREATE TABLE `pension_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门标识',
  `name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级部门',
  `manager_id` int(11) DEFAULT NULL COMMENT '部门负责人',
  `address` varchar(100) DEFAULT NULL COMMENT '办公地点',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `inputCode` varchar(100) DEFAULT NULL COMMENT '拼音',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

LOCK TABLES `pension_dept` WRITE;
/*!40000 ALTER TABLE `pension_dept` DISABLE KEYS */;

INSERT INTO `pension_dept` (`id`, `name`, `parent_id`, `manager_id`, `address`, `notes`, `inputCode`, `cleared`)
VALUES
	(1,'管理部',0,1,'青岛','无',NULL,2);

/*!40000 ALTER TABLE `pension_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pension_dic_message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_dic_message`;

CREATE TABLE `pension_dic_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inputCode` varchar(100) NOT NULL COMMENT '拼音码',
  `type` varchar(100) NOT NULL COMMENT '消息类别名称',
  `url` varchar(200) NOT NULL COMMENT '点击消息后的地址',
  `note` varchar(100) NOT NULL COMMENT '备注',
  `invalided` int(11) NOT NULL COMMENT '是否有效:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息字典表';



# Dump of table pension_dict_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_dict_info`;

CREATE TABLE `pension_dict_info` (
  `DICT_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `DICT_NAME` varchar(90) NOT NULL COMMENT '字典名称',
  `DICT_DATATABLE_NAME` varchar(90) NOT NULL COMMENT '数据表名',
  PRIMARY KEY (`DICT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典信息表:用于公共数据字典维护';

LOCK TABLES `pension_dict_info` WRITE;
/*!40000 ALTER TABLE `pension_dict_info` DISABLE KEYS */;

INSERT INTO `pension_dict_info` (`DICT_ID`, `DICT_NAME`, `DICT_DATATABLE_NAME`)
VALUES
	(802,'血型字典表','pension_dic_bloodtype'),
	(805,'残疾类型字典','pension_dic_deformity'),
	(806,'文化程度字典','pension_dic_degree'),
	(810,'部门字典','pension_dic_dept'),
	(820,'婚姻状况字典','pension_dic_marriage'),
	(822,'民族字典','pension_dic_nation'),
	(828,'支付方式字典','pension_dic_payway'),
	(829,'政治面貌字典','pension_dic_politics');

/*!40000 ALTER TABLE `pension_dict_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pension_employee
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_employee`;

CREATE TABLE `pension_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '员工标识',
  `name` varchar(100) DEFAULT NULL COMMENT '员工名称',
  `rest_id` varchar(100) DEFAULT NULL COMMENT '分院ID',
  `inputCode` varchar(100) NOT NULL COMMENT '拼音码',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(100) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `job` varchar(100) DEFAULT NULL COMMENT '职务',
  `health` varchar(100) DEFAULT NULL COMMENT '健康状况',
  `married` varchar(100) DEFAULT NULL COMMENT '婚姻状况',
  `IDcard` varchar(100) DEFAULT NULL COMMENT '员工卡号',
  `phone` varchar(100) DEFAULT NULL COMMENT '联系电话',
  `knowledge` varchar(100) DEFAULT NULL COMMENT '学历',
  `school` varchar(100) DEFAULT NULL COMMENT '毕业院校',
  `speciality` varchar(100) DEFAULT NULL COMMENT '特殊技能',
  `technology` varchar(100) DEFAULT NULL COMMENT '技能',
  `language` varchar(100) DEFAULT NULL COMMENT '语言',
  `computer` varchar(100) DEFAULT NULL COMMENT '电脑技能',
  `certName` varchar(100) DEFAULT NULL COMMENT '证书',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门',
  `accessDate` datetime DEFAULT NULL,
  `nativePLace` varchar(100) DEFAULT NULL COMMENT '本地住址',
  `address` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `wage` int(11) DEFAULT NULL COMMENT '工资',
  `notes` varchar(100) DEFAULT NULL,
  `cleared` int(11) DEFAULT NULL COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_11` (`dept_id`) USING BTREE,
  CONSTRAINT `pension_employee_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `pension_dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';

LOCK TABLES `pension_employee` WRITE;
/*!40000 ALTER TABLE `pension_employee` DISABLE KEYS */;

INSERT INTO `pension_employee` (`id`, `name`, `rest_id`, `inputCode`, `age`, `sex`, `birthday`, `job`, `health`, `married`, `IDcard`, `phone`, `knowledge`, `school`, `speciality`, `technology`, `language`, `computer`, `certName`, `dept_id`, `accessDate`, `nativePLace`, `address`, `wage`, `notes`, `cleared`)
VALUES
	(1,'管理员','1','gly',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,2);

/*!40000 ALTER TABLE `pension_employee` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pension_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_menu`;

CREATE TABLE `pension_menu` (
  `id` varchar(20) NOT NULL COMMENT '菜单标识',
  `displayText` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `types` int(11) DEFAULT NULL COMMENT '菜单类型',
  `parent_id` varchar(20) DEFAULT NULL COMMENT '上级菜单',
  `levels` int(11) DEFAULT NULL COMMENT '菜单层次',
  `picturePath` varchar(200) DEFAULT NULL COMMENT '菜单图标',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `url` varchar(100) DEFAULT NULL COMMENT '点击后连接地址',
  `license` varchar(500) DEFAULT NULL COMMENT 'license序列号',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `usable` int(11) DEFAULT '1' COMMENT '1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

LOCK TABLES `pension_menu` WRITE;
/*!40000 ALTER TABLE `pension_menu` DISABLE KEYS */;

INSERT INTO `pension_menu` (`id`, `displayText`, `types`, `parent_id`, `levels`, `picturePath`, `notes`, `url`, `license`, `sort`, `usable`)
VALUES
	('001','系统管理',NULL,'0',1,NULL,NULL,'systemManage','',100,1),
	('001001','用户管理',NULL,'001',2,NULL,NULL,'userManage.jsf','',1,1),
	('001002','修改密码',NULL,'001',2,NULL,NULL,'updatePassword.jsf','',2,1),
	('001003','权限管理',NULL,'001',2,NULL,NULL,'roleManage.jsf','',3,1),
	('001004','数据字典',NULL,'001',2,NULL,NULL,'datadict.jsf','',4,2),
	('001005','系统参数',NULL,'001',2,NULL,NULL,'systemConfig.jsf','',5,2),
	('001006','消息历史',NULL,'001',2,NULL,NULL,'messageHistory.jsf','',6,1),
	('001007','部门管理',NULL,'001',2,NULL,NULL,'deptManage.jsf',NULL,7,1),
	('002','人员管理',NULL,'0',1,NULL,NULL,'employeeManage',NULL,99,1),
	('002001','员工管理',NULL,'002',2,NULL,NULL,'employeemanage.jsf',NULL,1,1),
	('003','报销管理',NULL,'0',1,NULL,NULL,'recoupManage',NULL,98,1),
	('003001','报销查询',NULL,'003',2,NULL,NULL,'recoupSearch.jsf',NULL,2,1),
	('003002','报销申请',NULL,'003',2,NULL,NULL,'recoupApply.jsf',NULL,1,1),
	('003003','报销审批',NULL,'003',2,NULL,NULL,'recoupApprove.jsf',NULL,3,1);

/*!40000 ALTER TABLE `pension_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pension_messagedelrecord
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_messagedelrecord`;

CREATE TABLE `pension_messagedelrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息删除记录表标识',
  `message_id` int(11) NOT NULL COMMENT '消息id',
  `deletor_id` int(11) NOT NULL COMMENT '删除者Id',
  `deleteTime` datetime DEFAULT NULL COMMENT '删除者Id',
  `notes` varchar(100) DEFAULT NULL COMMENT '备注',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_34` (`message_id`) USING BTREE,
  KEY `FK_Reference_49` (`deletor_id`) USING BTREE,
  CONSTRAINT `pension_messagedelrecord_ibfk_1` FOREIGN KEY (`deletor_id`) REFERENCES `pension_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息删除记录表';



# Dump of table pension_messages
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_messages`;

CREATE TABLE `pension_messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息表标识',
  `messageType_id` int(11) NOT NULL COMMENT '消息类别id',
  `messageName` varchar(100) NOT NULL COMMENT '消息类别名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门Id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户Id',
  `isProcessor` int(11) NOT NULL DEFAULT '2' COMMENT '是否完成:1是2否',
  `processor_id` int(11) DEFAULT NULL COMMENT '处理人Id',
  `notes` varchar(1000) DEFAULT NULL COMMENT '备注',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  `message_url` varchar(100) DEFAULT NULL COMMENT '点击消息后连接地址',
  `message_date` datetime DEFAULT NULL COMMENT '消息时间',
  `table_name` varchar(50) DEFAULT NULL COMMENT '消息关联表',
  `table_key_id` int(11) DEFAULT NULL COMMENT '消息关联表标示',
  `processor_date` datetime DEFAULT NULL COMMENT '设置完成时间',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_32` (`dept_id`) USING BTREE,
  KEY `FK_Reference_33` (`processor_id`) USING BTREE,
  KEY `FK_Reference_48` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息表';



# Dump of table pension_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_role`;

CREATE TABLE `pension_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限标识',
  `name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `inputCode` varchar(100) DEFAULT NULL COMMENT '输入码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

LOCK TABLES `pension_role` WRITE;
/*!40000 ALTER TABLE `pension_role` DISABLE KEYS */;

INSERT INTO `pension_role` (`id`, `name`, `inputCode`, `notes`, `cleared`)
VALUES
	(1,'管理员','gly','最高权限',2);

/*!40000 ALTER TABLE `pension_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pension_role_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_role_menu`;

CREATE TABLE `pension_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限-菜单标识',
  `role_id` int(11) NOT NULL COMMENT '权限ID',
  `menu_id` varchar(20) DEFAULT NULL COMMENT '菜单ID',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`role_id`) USING BTREE,
  KEY `FK_Reference_3` (`menu_id`) USING BTREE,
  CONSTRAINT `pension_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `pension_role` (`id`),
  CONSTRAINT `pension_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `pension_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限-菜单表';

LOCK TABLES `pension_role_menu` WRITE;
/*!40000 ALTER TABLE `pension_role_menu` DISABLE KEYS */;

INSERT INTO `pension_role_menu` (`id`, `role_id`, `menu_id`, `notes`)
VALUES
	(2,1,'001001','用户管理'),
	(3,1,'001002','修改密码'),
	(4,1,'001003','权限管理'),
	(5,1,'001004','数据字典维护'),
	(6,1,'001005','系统参数配置'),
	(7,1,'001006','消息历史记录'),
	(2224,1,'001007',NULL),
	(2225,1,'001',NULL),
	(2226,1,'002',NULL),
	(2227,1,'002001',NULL),
	(2228,1,'003',NULL),
	(2229,1,'003001',NULL),
	(2230,1,'003002',NULL),
	(2231,1,'003003',NULL);

/*!40000 ALTER TABLE `pension_role_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pension_sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_sys_user`;

CREATE TABLE `pension_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户标识',
  `username` varchar(100) DEFAULT NULL COMMENT '系统户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `employee_id` int(11) DEFAULT NULL COMMENT '员工ID',
  `role_id` int(11) DEFAULT NULL COMMENT '权限ID',
  `loginname` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `rest_id` int(11) DEFAULT NULL COMMENT '公寓ID',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  `locked` int(11) DEFAULT '2' COMMENT '锁定用户:1是2否',
  `inputCode` varchar(100) DEFAULT NULL COMMENT '输入码',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1` (`role_id`) USING BTREE,
  CONSTRAINT `pension_sys_user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `pension_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户表';

LOCK TABLES `pension_sys_user` WRITE;
/*!40000 ALTER TABLE `pension_sys_user` DISABLE KEYS */;

INSERT INTO `pension_sys_user` (`id`, `username`, `password`, `employee_id`, `role_id`, `loginname`, `rest_id`, `notes`, `cleared`, `locked`, `inputCode`)
VALUES
	(1,'admin','21232f297a57a5a743894a0e4a801fc3',1,1,'admin',NULL,NULL,2,2,'admin');

/*!40000 ALTER TABLE `pension_sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pension_system_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pension_system_config`;

CREATE TABLE `pension_system_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '消息发送类型：1消息发送到部门，2消息发送员工，3 特殊设置参数，4不可编辑类型',
  `config_name` varchar(100) NOT NULL COMMENT '名称',
  `config_value` varchar(100) DEFAULT NULL COMMENT '内容',
  `Column 5` varchar(100) DEFAULT NULL COMMENT '内容',
  `config_description` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';



# Dump of table recoup_apply_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `recoup_apply_detail`;

CREATE TABLE `recoup_apply_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `record_id` int(11) NOT NULL COMMENT '报销单ID',
  `fee_datetime` datetime NOT NULL COMMENT '费用发生时间',
  `name` varchar(90) DEFAULT NULL COMMENT '费用名称',
  `price` decimal(8,2) DEFAULT NULL COMMENT '单价',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `unit` varchar(30) DEFAULT NULL COMMENT '单位',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '金额',
  `processer_id` int(11) DEFAULT NULL COMMENT '经手人ID',
  `processer_name` varchar(90) DEFAULT NULL COMMENT '经手人姓名',
  `out_proj_id` int(11) DEFAULT NULL COMMENT '调出项目部ID',
  `out_proj_name` varchar(90) DEFAULT NULL COMMENT '调出项目部名称',
  `in_proj_id` int(11) DEFAULT NULL COMMENT '调入项目部ID',
  `in_proj_name` varchar(90) DEFAULT NULL COMMENT '调入项目部名称',
  `image_url` varchar(800) DEFAULT NULL,
  `image_data` longblob COMMENT '图片二进制数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table recoup_apply_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `recoup_apply_record`;

CREATE TABLE `recoup_apply_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键:',
  `receipt_no` varchar(50) NOT NULL COMMENT '单据号',
  `proj_code` varchar(50) NOT NULL COMMENT '项目代码',
  `apply_date` datetime NOT NULL COMMENT '申请日期',
  `type_id1` int(11) NOT NULL COMMENT '类别代码1',
  `type_id2` int(11) NOT NULL COMMENT '类别代码2',
  `neme` varchar(90) NOT NULL COMMENT '姓名',
  `state` int(1) NOT NULL COMMENT '状态代码',
  `money` decimal(10,2) NOT NULL COMMENT '金额',
  `pay_type` int(2) NOT NULL COMMENT '支付方式代码',
  `pay_state` int(2) NOT NULL COMMENT '支付状态',
  `reason` varchar(200) DEFAULT NULL COMMENT '事由',
  `ohers` varchar(1000) DEFAULT NULL COMMENT '其他说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





# Replace placeholder table for columns with correct view syntax
# ------------------------------------------------------------

DROP TABLE `columns`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `columns`
AS SELECT
   `information_schema`.`columns`.`TABLE_CATALOG` AS `TABLE_CATALOG`,
   `information_schema`.`columns`.`TABLE_SCHEMA` AS `TABLE_SCHEMA`,
   `information_schema`.`columns`.`TABLE_NAME` AS `TABLE_NAME`,
   `information_schema`.`columns`.`COLUMN_NAME` AS `COLUMN_NAME`,
   `information_schema`.`columns`.`ORDINAL_POSITION` AS `ORDINAL_POSITION`,
   `information_schema`.`columns`.`COLUMN_DEFAULT` AS `COLUMN_DEFAULT`,
   `information_schema`.`columns`.`IS_NULLABLE` AS `IS_NULLABLE`,
   `information_schema`.`columns`.`DATA_TYPE` AS `DATA_TYPE`,
   `information_schema`.`columns`.`CHARACTER_MAXIMUM_LENGTH` AS `CHARACTER_MAXIMUM_LENGTH`,
   `information_schema`.`columns`.`CHARACTER_OCTET_LENGTH` AS `CHARACTER_OCTET_LENGTH`,
   `information_schema`.`columns`.`NUMERIC_PRECISION` AS `NUMERIC_PRECISION`,
   `information_schema`.`columns`.`NUMERIC_SCALE` AS `NUMERIC_SCALE`,
   `information_schema`.`columns`.`CHARACTER_SET_NAME` AS `CHARACTER_SET_NAME`,
   `information_schema`.`columns`.`COLLATION_NAME` AS `COLLATION_NAME`,
   `information_schema`.`columns`.`COLUMN_TYPE` AS `COLUMN_TYPE`,
   `information_schema`.`columns`.`COLUMN_KEY` AS `COLUMN_KEY`,
   `information_schema`.`columns`.`EXTRA` AS `EXTRA`,
   `information_schema`.`columns`.`PRIVILEGES` AS `PRIVILEGES`,
   `information_schema`.`columns`.`COLUMN_COMMENT` AS `COLUMN_COMMENT`
FROM `information_schema`.`columns` where (`information_schema`.`columns`.`TABLE_SCHEMA` = 'recoup_db');

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
