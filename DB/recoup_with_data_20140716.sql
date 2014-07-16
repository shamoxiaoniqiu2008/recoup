-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.13-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  8.3.0.4796
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 recoup_db 的数据库结构
CREATE DATABASE IF NOT EXISTS `recoup_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `recoup_db`;


-- 导出  视图 recoup_db.columns 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `columns` (
	`TABLE_CATALOG` VARCHAR(512) NOT NULL COLLATE 'utf8_general_ci',
	`TABLE_SCHEMA` VARCHAR(64) NOT NULL COLLATE 'utf8_general_ci',
	`TABLE_NAME` VARCHAR(64) NOT NULL COLLATE 'utf8_general_ci',
	`COLUMN_NAME` VARCHAR(64) NOT NULL COLLATE 'utf8_general_ci',
	`ORDINAL_POSITION` BIGINT(21) UNSIGNED NOT NULL,
	`COLUMN_DEFAULT` LONGTEXT NULL COLLATE 'utf8_general_ci',
	`IS_NULLABLE` VARCHAR(3) NOT NULL COLLATE 'utf8_general_ci',
	`DATA_TYPE` VARCHAR(64) NOT NULL COLLATE 'utf8_general_ci',
	`CHARACTER_MAXIMUM_LENGTH` BIGINT(21) UNSIGNED NULL,
	`CHARACTER_OCTET_LENGTH` BIGINT(21) UNSIGNED NULL,
	`NUMERIC_PRECISION` BIGINT(21) UNSIGNED NULL,
	`NUMERIC_SCALE` BIGINT(21) UNSIGNED NULL,
	`CHARACTER_SET_NAME` VARCHAR(32) NULL COLLATE 'utf8_general_ci',
	`COLLATION_NAME` VARCHAR(32) NULL COLLATE 'utf8_general_ci',
	`COLUMN_TYPE` LONGTEXT NOT NULL COLLATE 'utf8_general_ci',
	`COLUMN_KEY` VARCHAR(3) NOT NULL COLLATE 'utf8_general_ci',
	`EXTRA` VARCHAR(30) NOT NULL COLLATE 'utf8_general_ci',
	`PRIVILEGES` VARCHAR(80) NOT NULL COLLATE 'utf8_general_ci',
	`COLUMN_COMMENT` VARCHAR(1024) NOT NULL COLLATE 'utf8_general_ci'
) ENGINE=MyISAM;


-- 导出  表 recoup_db.pension_dept 结构
CREATE TABLE IF NOT EXISTS `pension_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门标识',
  `name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级部门',
  `manager_id` int(11) DEFAULT NULL COMMENT '部门负责人',
  `address` varchar(100) DEFAULT NULL COMMENT '办公地点',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `inputCode` varchar(100) DEFAULT NULL COMMENT '拼音',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- 正在导出表  recoup_db.pension_dept 的数据：~2 rows (大约)
DELETE FROM `pension_dept`;
/*!40000 ALTER TABLE `pension_dept` DISABLE KEYS */;
INSERT INTO `pension_dept` (`id`, `name`, `parent_id`, `manager_id`, `address`, `notes`, `inputCode`, `cleared`) VALUES
	(1, '管理部', 0, 1, '青岛', '无', NULL, 2),
	(2, '实施一部', 0, 2, '303', '', 'ssyb', 2);
/*!40000 ALTER TABLE `pension_dept` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_dict_info 结构
CREATE TABLE IF NOT EXISTS `pension_dict_info` (
  `DICT_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `DICT_NAME` varchar(90) NOT NULL COMMENT '字典名称',
  `DICT_DATATABLE_NAME` varchar(90) NOT NULL COMMENT '数据表名',
  PRIMARY KEY (`DICT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=830 DEFAULT CHARSET=utf8 COMMENT='字典信息表:用于公共数据字典维护';

-- 正在导出表  recoup_db.pension_dict_info 的数据：~8 rows (大约)
DELETE FROM `pension_dict_info`;
/*!40000 ALTER TABLE `pension_dict_info` DISABLE KEYS */;
INSERT INTO `pension_dict_info` (`DICT_ID`, `DICT_NAME`, `DICT_DATATABLE_NAME`) VALUES
	(802, '血型字典表', 'pension_dic_bloodtype'),
	(805, '残疾类型字典', 'pension_dic_deformity'),
	(806, '文化程度字典', 'pension_dic_degree'),
	(810, '部门字典', 'pension_dic_dept'),
	(820, '婚姻状况字典', 'pension_dic_marriage'),
	(822, '民族字典', 'pension_dic_nation'),
	(828, '支付方式字典', 'pension_dic_payway'),
	(829, '政治面貌字典', 'pension_dic_politics');
/*!40000 ALTER TABLE `pension_dict_info` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_dic_message 结构
CREATE TABLE IF NOT EXISTS `pension_dic_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inputCode` varchar(100) NOT NULL COMMENT '拼音码',
  `type` varchar(100) NOT NULL COMMENT '消息类别名称',
  `url` varchar(200) NOT NULL COMMENT '点击消息后的地址',
  `note` varchar(100) NOT NULL COMMENT '备注',
  `invalided` int(11) NOT NULL COMMENT '是否有效:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息字典表';

-- 正在导出表  recoup_db.pension_dic_message 的数据：~0 rows (大约)
DELETE FROM `pension_dic_message`;
/*!40000 ALTER TABLE `pension_dic_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `pension_dic_message` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_employee 结构
CREATE TABLE IF NOT EXISTS `pension_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '员工标识',
  `name` varchar(50) DEFAULT NULL COMMENT '员工名称',
  `rest_id` varchar(100) DEFAULT NULL COMMENT '分院ID',
  `inputCode` varchar(100) NOT NULL COMMENT '拼音码',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `job` varchar(100) DEFAULT NULL COMMENT '职务',
  `health` varchar(10) DEFAULT NULL COMMENT '健康状况',
  `married` varchar(1) DEFAULT NULL COMMENT '婚姻状况',
  `IDcard` varchar(32) DEFAULT NULL COMMENT '员工卡号',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `knowledge` varchar(100) DEFAULT NULL COMMENT '学历',
  `school` varchar(100) DEFAULT NULL COMMENT '毕业院校',
  `speciality` varchar(100) DEFAULT NULL COMMENT '特殊技能',
  `technology` varchar(100) DEFAULT NULL COMMENT '技能',
  `language` varchar(20) DEFAULT NULL COMMENT '语言',
  `computer` varchar(20) DEFAULT NULL COMMENT '电脑技能',
  `certName` varchar(50) DEFAULT NULL COMMENT '证书',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门',
  `accessDate` datetime DEFAULT NULL,
  `nativePLace` varchar(100) DEFAULT NULL COMMENT '本地住址',
  `address` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `wage` int(11) DEFAULT NULL COMMENT '工资',
  `notes` varchar(100) DEFAULT NULL,
  `cleared` int(11) DEFAULT NULL COMMENT '清除标记:1是2否',
  `bankName` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `bankCard` varchar(20) DEFAULT NULL COMMENT '银行卡号',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_11` (`dept_id`) USING BTREE,
  CONSTRAINT `pension_employee_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `pension_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='员工表';

-- 正在导出表  recoup_db.pension_employee 的数据：~3 rows (大约)
DELETE FROM `pension_employee`;
/*!40000 ALTER TABLE `pension_employee` DISABLE KEYS */;
INSERT INTO `pension_employee` (`id`, `name`, `rest_id`, `inputCode`, `age`, `sex`, `birthday`, `job`, `health`, `married`, `IDcard`, `phone`, `knowledge`, `school`, `speciality`, `technology`, `language`, `computer`, `certName`, `dept_id`, `accessDate`, `nativePLace`, `address`, `wage`, `notes`, `cleared`, `bankName`, `bankCard`) VALUES
	(1, '管理员', '1', 'gly', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL),
	(2, '张三', '1', 'zs', 0, '1', '2014-07-01 00:00:00', '111', '', '', '111', '111', '', '', '', '', '', '', '', 2, '2014-07-02 00:00:00', '', '', NULL, '', 2, '招商银行', '6225885322503345'),
	(3, '王会计', '1', 'whj', NULL, '2', NULL, '出纳', NULL, NULL, NULL, '32424', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2014-07-02 00:00:00', NULL, NULL, NULL, NULL, 2, '招商银行', '6225885322503342');
/*!40000 ALTER TABLE `pension_employee` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_menu 结构
CREATE TABLE IF NOT EXISTS `pension_menu` (
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

-- 正在导出表  recoup_db.pension_menu 的数据：~14 rows (大约)
DELETE FROM `pension_menu`;
/*!40000 ALTER TABLE `pension_menu` DISABLE KEYS */;
INSERT INTO `pension_menu` (`id`, `displayText`, `types`, `parent_id`, `levels`, `picturePath`, `notes`, `url`, `license`, `sort`, `usable`) VALUES
	('001', '系统管理', NULL, '0', 1, NULL, NULL, 'systemManage', '', 100, 1),
	('001001', '用户管理', NULL, '001', 2, NULL, NULL, 'userManage.jsf', '', 1, 1),
	('001002', '修改密码', NULL, '001', 2, NULL, NULL, 'updatePassword.jsf', '', 2, 1),
	('001003', '权限管理', NULL, '001', 2, NULL, NULL, 'roleManage.jsf', '', 3, 1),
	('001004', '数据字典', NULL, '001', 2, NULL, NULL, 'datadict.jsf', '', 4, 2),
	('001005', '系统参数', NULL, '001', 2, NULL, NULL, 'systemConfig.jsf', '', 5, 2),
	('001006', '消息历史', NULL, '001', 2, NULL, NULL, 'messageHistory.jsf', '', 6, 1),
	('001007', '部门管理', NULL, '001', 2, NULL, NULL, 'deptManage.jsf', NULL, 7, 1),
	('002', '人员管理', NULL, '0', 1, NULL, NULL, 'employeeManage', NULL, 99, 1),
	('002001', '员工管理', NULL, '002', 2, NULL, NULL, 'employeemanage.jsf', NULL, 1, 1),
	('003', '报销管理', NULL, '0', 1, NULL, NULL, 'recoupManage', NULL, 98, 1),
	('003001', '报销查询', NULL, '003', 2, NULL, NULL, 'recoupSearch.jsf', NULL, 2, 1),
	('003002', '报销申请', NULL, '003', 2, NULL, NULL, 'recoupApply.jsf', NULL, 1, 1),
	('003003', '报销审批', NULL, '003', 2, NULL, NULL, 'recoupApprove.jsf', NULL, 3, 1);
/*!40000 ALTER TABLE `pension_menu` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_messagedelrecord 结构
CREATE TABLE IF NOT EXISTS `pension_messagedelrecord` (
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

-- 正在导出表  recoup_db.pension_messagedelrecord 的数据：~0 rows (大约)
DELETE FROM `pension_messagedelrecord`;
/*!40000 ALTER TABLE `pension_messagedelrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `pension_messagedelrecord` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_messages 结构
CREATE TABLE IF NOT EXISTS `pension_messages` (
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

-- 正在导出表  recoup_db.pension_messages 的数据：~0 rows (大约)
DELETE FROM `pension_messages`;
/*!40000 ALTER TABLE `pension_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `pension_messages` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_role 结构
CREATE TABLE IF NOT EXISTS `pension_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限标识',
  `name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `inputCode` varchar(100) DEFAULT NULL COMMENT '输入码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- 正在导出表  recoup_db.pension_role 的数据：~3 rows (大约)
DELETE FROM `pension_role`;
/*!40000 ALTER TABLE `pension_role` DISABLE KEYS */;
INSERT INTO `pension_role` (`id`, `name`, `inputCode`, `notes`, `cleared`) VALUES
	(1, '管理员', 'gly', '最高权限', 2),
	(2, '报销用户', 'bxyh', '', 2),
	(3, '会计', 'hj', '', 2);
/*!40000 ALTER TABLE `pension_role` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_role_menu 结构
CREATE TABLE IF NOT EXISTS `pension_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限-菜单标识',
  `role_id` int(11) NOT NULL COMMENT '权限ID',
  `menu_id` varchar(20) DEFAULT NULL COMMENT '菜单ID',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`role_id`) USING BTREE,
  KEY `FK_Reference_3` (`menu_id`) USING BTREE,
  CONSTRAINT `pension_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `pension_role` (`id`),
  CONSTRAINT `pension_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `pension_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2237 DEFAULT CHARSET=utf8 COMMENT='权限-菜单表';

-- 正在导出表  recoup_db.pension_role_menu 的数据：~19 rows (大约)
DELETE FROM `pension_role_menu`;
/*!40000 ALTER TABLE `pension_role_menu` DISABLE KEYS */;
INSERT INTO `pension_role_menu` (`id`, `role_id`, `menu_id`, `notes`) VALUES
	(2, 1, '001001', '用户管理'),
	(3, 1, '001002', '修改密码'),
	(4, 1, '001003', '权限管理'),
	(5, 1, '001004', '数据字典维护'),
	(6, 1, '001005', '系统参数配置'),
	(7, 1, '001006', '消息历史记录'),
	(2224, 1, '001007', NULL),
	(2225, 1, '001', NULL),
	(2226, 1, '002', NULL),
	(2227, 1, '002001', NULL),
	(2228, 1, '003', NULL),
	(2230, 1, '003002', NULL),
	(2231, 1, '003003', NULL),
	(2232, 2, '003002', NULL),
	(2233, 3, '003002', NULL),
	(2234, 3, '003003', NULL),
	(2235, 2, '001002', NULL),
	(2236, 3, '001002', NULL);
/*!40000 ALTER TABLE `pension_role_menu` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_system_config 结构
CREATE TABLE IF NOT EXISTS `pension_system_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '消息发送类型：1消息发送到部门，2消息发送员工，3 特殊设置参数，4不可编辑类型',
  `config_name` varchar(100) NOT NULL COMMENT '名称',
  `config_value` varchar(100) DEFAULT NULL COMMENT '内容',
  `Column 5` varchar(100) DEFAULT NULL COMMENT '内容',
  `config_description` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- 正在导出表  recoup_db.pension_system_config 的数据：~0 rows (大约)
DELETE FROM `pension_system_config`;
/*!40000 ALTER TABLE `pension_system_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `pension_system_config` ENABLE KEYS */;


-- 导出  表 recoup_db.pension_sys_user 结构
CREATE TABLE IF NOT EXISTS `pension_sys_user` (
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='登录用户表';

-- 正在导出表  recoup_db.pension_sys_user 的数据：~3 rows (大约)
DELETE FROM `pension_sys_user`;
/*!40000 ALTER TABLE `pension_sys_user` DISABLE KEYS */;
INSERT INTO `pension_sys_user` (`id`, `username`, `password`, `employee_id`, `role_id`, `loginname`, `rest_id`, `notes`, `cleared`, `locked`, `inputCode`) VALUES
	(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1, 1, 'admin', NULL, NULL, 2, 2, 'admin'),
	(2, 'zhangsan', 'c4ca4238a0b923820dcc509a6f75849b', 2, 2, '张三', NULL, '', 2, 2, 'zs'),
	(3, 'wang', 'c4ca4238a0b923820dcc509a6f75849b', 3, 3, '王会计', NULL, '', 2, 2, 'whj');
/*!40000 ALTER TABLE `pension_sys_user` ENABLE KEYS */;


-- 导出  表 recoup_db.recoup_apply_detail 结构
CREATE TABLE IF NOT EXISTS `recoup_apply_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `record_id` int(11) NOT NULL COMMENT '报销单ID',
  `fee_datetime` varchar(90) NOT NULL COMMENT '费用发生时间',
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  recoup_db.recoup_apply_detail 的数据：~7 rows (大约)
DELETE FROM `recoup_apply_detail`;
/*!40000 ALTER TABLE `recoup_apply_detail` DISABLE KEYS */;
INSERT INTO `recoup_apply_detail` (`id`, `record_id`, `fee_datetime`, `name`, `price`, `qty`, `unit`, `amount`, `processer_id`, `processer_name`, `out_proj_id`, `out_proj_name`, `in_proj_id`, `in_proj_name`, `image_url`, `image_data`) VALUES
	(1, 1, 'Wed Jul 09 00:00:00 CST 2014', '2', 2.00, 2, 'y', 4.00, NULL, '2', NULL, '2', NULL, '2', '/images/invoice/2ead806fd12e4373b3ecc9d67974a104-20140709234856.jpg', NULL),
	(2, 1, 'Wed Jul 09 00:00:00 CST 2014', '3', 3.00, 3, 'y', 9.00, NULL, '3', NULL, '3', NULL, '3', '/images/invoice/d733cf1e34024f2ca0553b658708e5d3-20140709234920.jpg', NULL),
	(3, 1, 'Thu Jul 10 00:00:00 CST 2014', '3', 3.00, 3, '3', 9.00, NULL, '3', NULL, '3', NULL, '3', '/images/invoice/38e605d74ce54d1ca4f8c93428201d5c-20140710000749.jpg', NULL),
	(4, 1, '2014-07-12 00:00:00', '3', 3.00, 3, '3', 9.00, NULL, '3', NULL, '3', NULL, '3', NULL, NULL),
	(5, 1, '2014-07-12 00:00:00', '4', 4.00, 4, '4', 16.00, NULL, '4', NULL, '4', NULL, '4', NULL, NULL),
	(6, 1, '2014-07-12 00:00:00', '99', 99.00, 99, 'y', 9801.00, NULL, '9', NULL, '9', NULL, '9', NULL, NULL),
	(7, 1, '2014-07-12 00:00:00', '3', 3.00, 3, '3', 9.00, NULL, '3', NULL, '3', NULL, '3', NULL, NULL);
/*!40000 ALTER TABLE `recoup_apply_detail` ENABLE KEYS */;


-- 导出  表 recoup_db.recoup_apply_record 结构
CREATE TABLE IF NOT EXISTS `recoup_apply_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键:',
  `receipt_no` varchar(50) NOT NULL COMMENT '单据号',
  `proj_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `proj_code` varchar(50) NOT NULL COMMENT '项目代码',
  `apply_date` varchar(90) NOT NULL COMMENT '申请日期',
  `exp_type_code_p` varchar(50) NOT NULL COMMENT '费用大类',
  `exp_type_code` varchar(50) NOT NULL COMMENT '费用小类',
  `user_id` int(11) NOT NULL COMMENT '员工标识',
  `state` int(1) NOT NULL COMMENT '状态代码:1-保存 2-提交 3-退回 4-完成 5-撤销',
  `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `pay_type` varchar(50) NOT NULL COMMENT '支付方式代码',
  `pay_state` int(2) unsigned zerofill NOT NULL DEFAULT '01' COMMENT '支付状态:1-未支付 2-已支付',
  `reason` varchar(200) DEFAULT NULL COMMENT '事由',
  `ohers` varchar(1000) DEFAULT NULL COMMENT '其他说明',
  `cleared` int(11) NOT NULL DEFAULT '2' COMMENT '是否清除:1-是 2-否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  recoup_db.recoup_apply_record 的数据：~7 rows (大约)
DELETE FROM `recoup_apply_record`;
/*!40000 ALTER TABLE `recoup_apply_record` DISABLE KEYS */;
INSERT INTO `recoup_apply_record` (`id`, `receipt_no`, `proj_name`, `proj_code`, `apply_date`, `exp_type_code_p`, `exp_type_code`, `user_id`, `state`, `money`, `pay_type`, `pay_state`, `reason`, `ohers`, `cleared`) VALUES
	(1, 'XH0010000101', '昆山项目', 'XH', '2014-07-02 00:00:00', '0', '0', 0, 0, 100.00, '1', 01, '测', '查的', 2),
	(2, 'XHY01Y012266C000001300', NULL, 'XHY', '2014-07-09 00:00:00', 'Y', 'domain.recoup.SysDeDatarangeitem@7ac0689', 1, 1, 13.00, 'C', 01, 'aaa', 'aaa', 2),
	(3, 'XHY01Y012266C000000900', NULL, 'XHY', '2014-07-10 00:00:00', 'Y', 'Y01', 1, 1, 9.00, 'C', 01, 'ccccc', 'ccccc', 2),
	(4, 'XHY01Y012266C000000900', NULL, 'XHY', '2014-07-12 00:00:00', '0', '0', 1, 1, 9.00, 'C', 01, '3', '3', 2),
	(5, 'XHY01Y012266C000001600', NULL, 'XHY', '2014-07-12 00:00:00', '0', '0', 1, 2, 16.00, 'C', 01, '2222', '2222', 2),
	(6, 'XHY01Y012266C000980100', NULL, 'XHY', '2014-07-12 00:00:00', 'Y', 'Y03', 1, 5, 9801.00, 'C', 01, '6666', '6666', 1),
	(7, 'TTS01Y012266C000000900', NULL, 'TTS', '2014-07-12 00:00:00', 'Y', 'Y02', 1, 2, 9.00, 'C', 01, '2', '2', 2);
/*!40000 ALTER TABLE `recoup_apply_record` ENABLE KEYS */;


-- 导出  表 recoup_db.recoup_dic_costclass1 结构
CREATE TABLE IF NOT EXISTS `recoup_dic_costclass1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `class1_code` varchar(30) DEFAULT NULL COMMENT '一级类别代码',
  `class1_name` varchar(90) NOT NULL COMMENT '一级类别名称',
  `input_code` varchar(30) NOT NULL COMMENT '类别输入码',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(1) NOT NULL DEFAULT '2' COMMENT '清除标记:1-是 2-否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  recoup_db.recoup_dic_costclass1 的数据：~2 rows (大约)
DELETE FROM `recoup_dic_costclass1`;
/*!40000 ALTER TABLE `recoup_dic_costclass1` DISABLE KEYS */;
INSERT INTO `recoup_dic_costclass1` (`id`, `class1_code`, `class1_name`, `input_code`, `note`, `cleared`) VALUES
	(1, 'Y', '材料费用', 'rcfy', NULL, 2),
	(2, 'H', '日常费用', 'lsfy', NULL, 2);
/*!40000 ALTER TABLE `recoup_dic_costclass1` ENABLE KEYS */;


-- 导出  表 recoup_db.recoup_dic_costclass2 结构
CREATE TABLE IF NOT EXISTS `recoup_dic_costclass2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `class1_id` int(11) NOT NULL COMMENT '所属一级类别',
  `class1_code` varchar(30) DEFAULT NULL COMMENT '一级类别代码',
  `class2_code` varchar(30) DEFAULT NULL COMMENT '二级类别代码',
  `class2_name` varchar(90) NOT NULL COMMENT '二级类别名称',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(1) NOT NULL DEFAULT '2' COMMENT '清除标记:1-是 2-否',
  PRIMARY KEY (`id`),
  KEY `class1_id` (`class1_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`class1_id`) REFERENCES `recoup_dic_costclass1` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  recoup_db.recoup_dic_costclass2 的数据：~2 rows (大约)
DELETE FROM `recoup_dic_costclass2`;
/*!40000 ALTER TABLE `recoup_dic_costclass2` DISABLE KEYS */;
INSERT INTO `recoup_dic_costclass2` (`id`, `class1_id`, `class1_code`, `class2_code`, `class2_name`, `note`, `cleared`) VALUES
	(1, 1, 'Y', 'Y01', '设备材料', NULL, 2),
	(2, 2, 'H', 'H01', '交际应酬', NULL, 2);
/*!40000 ALTER TABLE `recoup_dic_costclass2` ENABLE KEYS */;


-- 导出  表 recoup_db.recoup_dic_payclass 结构
CREATE TABLE IF NOT EXISTS `recoup_dic_payclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `pay_type_name` varchar(90) NOT NULL COMMENT '支付方式名称',
  `input_code` varchar(30) NOT NULL COMMENT '输入码',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(1) NOT NULL DEFAULT '2' COMMENT '清除标记:1-是 2-否',
  `sort_by` int(2) NOT NULL COMMENT '排序字段',
  `pay_type_code` varchar(30) DEFAULT NULL COMMENT '支付方式代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  recoup_db.recoup_dic_payclass 的数据：~2 rows (大约)
DELETE FROM `recoup_dic_payclass`;
/*!40000 ALTER TABLE `recoup_dic_payclass` DISABLE KEYS */;
INSERT INTO `recoup_dic_payclass` (`id`, `pay_type_name`, `input_code`, `note`, `cleared`, `sort_by`, `pay_type_code`) VALUES
	(1, '刷卡', 'K', NULL, 2, 2, NULL),
	(2, '现金', 'C', NULL, 2, 1, NULL);
/*!40000 ALTER TABLE `recoup_dic_payclass` ENABLE KEYS */;


-- 导出  表 recoup_db.recoup_dic_project 结构
CREATE TABLE IF NOT EXISTS `recoup_dic_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `project_code` varchar(90) DEFAULT NULL COMMENT '项目编码',
  `project_name` varchar(90) NOT NULL COMMENT '项目名称',
  `input_code` varchar(30) NOT NULL COMMENT '输入码',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(1) NOT NULL DEFAULT '2' COMMENT '清除标记:1-是 2-否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  recoup_db.recoup_dic_project 的数据：~2 rows (大约)
DELETE FROM `recoup_dic_project`;
/*!40000 ALTER TABLE `recoup_dic_project` DISABLE KEYS */;
INSERT INTO `recoup_dic_project` (`id`, `project_code`, `project_name`, `input_code`, `note`, `cleared`) VALUES
	(1, '101', '项目一', 'XHY', NULL, 2),
	(2, '102', '项目二', 'TST', NULL, 2);
/*!40000 ALTER TABLE `recoup_dic_project` ENABLE KEYS */;


-- 导出  表 recoup_db.sys_de_datarange 结构
CREATE TABLE IF NOT EXISTS `sys_de_datarange` (
  `ID` varchar(50) NOT NULL COMMENT '唯一标识',
  `RANGE_CODE` varchar(50) DEFAULT NULL COMMENT '值域类代码',
  `RANGE_NAME` varchar(100) DEFAULT NULL COMMENT '值域类名称',
  `AUDI_FLAG` varchar(2) DEFAULT NULL COMMENT '审核标记 0未审核，1通过，2不通过',
  `USE_FLAG` varchar(2) DEFAULT NULL COMMENT '使用标记0不可用，1可用',
  `CREAT_DATE` varchar(15) DEFAULT NULL COMMENT '创建日期',
  `CREATE_PERSON` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 ROW_FORMAT=DYNAMIC;

-- 正在导出表  recoup_db.sys_de_datarange 的数据：~3 rows (大约)
DELETE FROM `sys_de_datarange`;
/*!40000 ALTER TABLE `sys_de_datarange` DISABLE KEYS */;
INSERT INTO `sys_de_datarange` (`ID`, `RANGE_CODE`, `RANGE_NAME`, `AUDI_FLAG`, `USE_FLAG`, `CREAT_DATE`, `CREATE_PERSON`) VALUES
	('1', 'RC001', '费用类别代码', '1', '1', '20140704', 'qiufj'),
	('2', 'RC002', '支付方式代码', '1', '1', '20140704', 'qiufj'),
	('3', 'RC003', '项目代码', '1', '1', '20140704', 'qiufj');
/*!40000 ALTER TABLE `sys_de_datarange` ENABLE KEYS */;


-- 导出  表 recoup_db.sys_de_datarangeitem 结构
CREATE TABLE IF NOT EXISTS `sys_de_datarangeitem` (
  `ID` varchar(50) NOT NULL COMMENT '唯一标识',
  `CODE` varchar(50) DEFAULT NULL COMMENT '值域代码',
  `NAME` varchar(200) DEFAULT NULL COMMENT '值域名称',
  `PARENT_ID` varchar(50) DEFAULT NULL COMMENT '父节点ID',
  `RANGE_CODE` varchar(32) DEFAULT NULL COMMENT '值域类别代码',
  `AUDI_FLAG` varchar(2) DEFAULT NULL COMMENT '审批标记0不通过，1审批通过',
  `USE_FLAG` varchar(2) DEFAULT NULL COMMENT '有效标记，0无效,1有效',
  `CREAT_DATE` varchar(15) DEFAULT NULL COMMENT '创建时间17位字符',
  `CREATE_PERSON` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- 正在导出表  recoup_db.sys_de_datarangeitem 的数据：~15 rows (大约)
DELETE FROM `sys_de_datarangeitem`;
/*!40000 ALTER TABLE `sys_de_datarangeitem` DISABLE KEYS */;
INSERT INTO `sys_de_datarangeitem` (`ID`, `CODE`, `NAME`, `PARENT_ID`, `RANGE_CODE`, `AUDI_FLAG`, `USE_FLAG`, `CREAT_DATE`, `CREATE_PERSON`) VALUES
	('1', 'Y', '业务费', NULL, 'RC001', '1', '1', '20140704', 'qfj'),
	('10', 'C', '打卡', NULL, 'RC002', '1', '1', '20140704', 'qfj'),
	('11', 'M', '现金', NULL, 'RC002', '1', '1', '20140704', 'qfj'),
	('12', 'XHY', '项目一', NULL, 'RC003', '1', '1', '20140704', 'qfj'),
	('13', 'TTS', '项目二', NULL, 'RC003', '1', '1', '20140704', 'qfj'),
	('14', 'PRJ', '项目三', NULL, 'RC003', '1', '1', '20140704', 'qfj'),
	('15', 'TST', '项目四', NULL, 'RC003', '1', '1', '20140704', 'qfj'),
	('2', 'Y01', '差旅费', 'Y', 'RC001', '1', '1', '20140704', 'qfj'),
	('3', 'Y02', '食宿费', 'Y', 'RC001', '1', '1', '20140704', 'qfj'),
	('4', 'Y03', '招待费', 'Y', 'RC001', '1', '1', '20140704', 'qfj'),
	('5', 'Y04', '礼品费', 'Y', 'RC001', '1', '1', '20140704', 'qfj'),
	('6', 'O', '其他', '', 'RC001', '1', '1', '20140704', 'qfj'),
	('7', 'O', '房租费用', 'O', 'RC001', '1', '1', '20140704', 'qfj'),
	('8', 'O', '物业费用', 'O', 'RC001', '1', '1', '20140704', 'qfj'),
	('9', 'O', '电话及宽带', 'O', 'RC001', '1', '1', '20140704', 'qfj');
/*!40000 ALTER TABLE `sys_de_datarangeitem` ENABLE KEYS */;


-- 导出  视图 recoup_db.columns 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `columns`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` VIEW `columns` AS select `information_schema`.`columns`.`TABLE_CATALOG` AS `TABLE_CATALOG`,`information_schema`.`columns`.`TABLE_SCHEMA` AS `TABLE_SCHEMA`,`information_schema`.`columns`.`TABLE_NAME` AS `TABLE_NAME`,`information_schema`.`columns`.`COLUMN_NAME` AS `COLUMN_NAME`,`information_schema`.`columns`.`ORDINAL_POSITION` AS `ORDINAL_POSITION`,`information_schema`.`columns`.`COLUMN_DEFAULT` AS `COLUMN_DEFAULT`,`information_schema`.`columns`.`IS_NULLABLE` AS `IS_NULLABLE`,`information_schema`.`columns`.`DATA_TYPE` AS `DATA_TYPE`,`information_schema`.`columns`.`CHARACTER_MAXIMUM_LENGTH` AS `CHARACTER_MAXIMUM_LENGTH`,`information_schema`.`columns`.`CHARACTER_OCTET_LENGTH` AS `CHARACTER_OCTET_LENGTH`,`information_schema`.`columns`.`NUMERIC_PRECISION` AS `NUMERIC_PRECISION`,`information_schema`.`columns`.`NUMERIC_SCALE` AS `NUMERIC_SCALE`,`information_schema`.`columns`.`CHARACTER_SET_NAME` AS `CHARACTER_SET_NAME`,`information_schema`.`columns`.`COLLATION_NAME` AS `COLLATION_NAME`,`information_schema`.`columns`.`COLUMN_TYPE` AS `COLUMN_TYPE`,`information_schema`.`columns`.`COLUMN_KEY` AS `COLUMN_KEY`,`information_schema`.`columns`.`EXTRA` AS `EXTRA`,`information_schema`.`columns`.`PRIVILEGES` AS `PRIVILEGES`,`information_schema`.`columns`.`COLUMN_COMMENT` AS `COLUMN_COMMENT` from `information_schema`.`columns` where (`information_schema`.`columns`.`TABLE_SCHEMA` = 'recoup_db') ;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
