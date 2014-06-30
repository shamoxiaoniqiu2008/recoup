/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50611
 Source Host           : localhost
 Source Database       : recoup_db

 Target Server Type    : MySQL
 Target Server Version : 50611
 File Encoding         : utf-8

 Date: 07/01/2014 00:21:13 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `pension_dept`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
--  Records of `pension_dept`
-- ----------------------------
BEGIN;
INSERT INTO `pension_dept` VALUES ('1', '管理部', '0', '1', '青岛', '无', null, '2');
COMMIT;

-- ----------------------------
--  Table structure for `pension_dic_message`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `pension_dict_info`
-- ----------------------------
DROP TABLE IF EXISTS `pension_dict_info`;
CREATE TABLE `pension_dict_info` (
  `DICT_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `DICT_NAME` varchar(90) NOT NULL COMMENT '字典名称',
  `DICT_DATATABLE_NAME` varchar(90) NOT NULL COMMENT '数据表名',
  PRIMARY KEY (`DICT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=830 DEFAULT CHARSET=utf8 COMMENT='字典信息表:用于公共数据字典维护';

-- ----------------------------
--  Records of `pension_dict_info`
-- ----------------------------
BEGIN;
INSERT INTO `pension_dict_info` VALUES ('802', '血型字典表', 'pension_dic_bloodtype'), ('805', '残疾类型字典', 'pension_dic_deformity'), ('806', '文化程度字典', 'pension_dic_degree'), ('810', '部门字典', 'pension_dic_dept'), ('820', '婚姻状况字典', 'pension_dic_marriage'), ('822', '民族字典', 'pension_dic_nation'), ('828', '支付方式字典', 'pension_dic_payway'), ('829', '政治面貌字典', 'pension_dic_politics');
COMMIT;

-- ----------------------------
--  Table structure for `pension_employee`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
--  Records of `pension_employee`
-- ----------------------------
BEGIN;
INSERT INTO `pension_employee` VALUES ('1', '管理员', '1', 'gly', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, null, null, null, null, '2');
COMMIT;

-- ----------------------------
--  Table structure for `pension_menu`
-- ----------------------------
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

-- ----------------------------
--  Records of `pension_menu`
-- ----------------------------
BEGIN;
INSERT INTO `pension_menu` VALUES ('001', '系统管理', null, '0', '1', null, null, 'systemManage', '', '100', '1'), ('001001', '用户管理', null, '001', '2', null, null, 'userManage.jsf', '', '1', '1'), ('001002', '修改密码', null, '001', '2', null, null, 'updatePassword.jsf', '', '2', '1'), ('001003', '权限管理', null, '001', '2', null, null, 'roleManage.jsf', '', '3', '1'), ('001004', '数据字典', null, '001', '2', null, null, 'datadict.jsf', '', '4', '2'), ('001005', '系统参数', null, '001', '2', null, null, 'systemConfig.jsf', '', '5', '2'), ('001006', '消息历史', null, '001', '2', null, null, 'messageHistory.jsf', '', '6', '1'), ('001007', '部门管理', null, '001', '2', null, null, 'deptManage.jsf', null, '7', '1'), ('002', '人员管理', null, '0', '1', null, null, 'employeeManage', null, '99', '1'), ('002001', '员工管理', null, '002', '2', null, null, 'employeemanage.jsf', null, '1', '1'), ('003', '报销管理', null, '0', '1', null, null, 'recoupManage', null, '98', '1'), ('003001', '报销查询', null, '003', '2', null, null, 'recoupSearch.jsf', null, '2', '1'), ('003002', '报销申请', null, '003', '2', null, null, 'recoupApply.jsf', null, '1', '1'), ('003003', '报销审批', null, '003', '2', null, null, 'recoupApprove.jsf', null, '3', '1');
COMMIT;

-- ----------------------------
--  Table structure for `pension_messagedelrecord`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `pension_messages`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `pension_role`
-- ----------------------------
DROP TABLE IF EXISTS `pension_role`;
CREATE TABLE `pension_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限标识',
  `name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `inputCode` varchar(100) DEFAULT NULL COMMENT '输入码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
--  Records of `pension_role`
-- ----------------------------
BEGIN;
INSERT INTO `pension_role` VALUES ('1', '管理员', 'gly', '最高权限', '2');
COMMIT;

-- ----------------------------
--  Table structure for `pension_role_menu`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2232 DEFAULT CHARSET=utf8 COMMENT='权限-菜单表';

-- ----------------------------
--  Records of `pension_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `pension_role_menu` VALUES ('2', '1', '001001', '用户管理'), ('3', '1', '001002', '修改密码'), ('4', '1', '001003', '权限管理'), ('5', '1', '001004', '数据字典维护'), ('6', '1', '001005', '系统参数配置'), ('7', '1', '001006', '消息历史记录'), ('2224', '1', '001007', null), ('2225', '1', '001', null), ('2226', '1', '002', null), ('2227', '1', '002001', null), ('2228', '1', '003', null), ('2229', '1', '003001', null), ('2230', '1', '003002', null), ('2231', '1', '003003', null);
COMMIT;

-- ----------------------------
--  Table structure for `pension_sys_user`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='登录用户表';

-- ----------------------------
--  Records of `pension_sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `pension_sys_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1', '1', 'admin', null, null, '2', '2', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `pension_system_config`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `recoup_apply_detail`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `recoup_apply_record`
-- ----------------------------
DROP TABLE IF EXISTS `recoup_apply_record`;
CREATE TABLE `recoup_apply_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键:',
  `receipt_no` varchar(50) NOT NULL COMMENT '单据号',
  `proj_code` varchar(50) NOT NULL COMMENT '项目代码',
  `apply_date` datetime NOT NULL COMMENT '申请日期',
  `type_id1` int(11) NOT NULL COMMENT '类别代码1',
  `type_id2` int(11) NOT NULL COMMENT '类别代码2',
  `neme` varchar(90) NOT NULL COMMENT '姓名',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态代码:0-默认 1-保存 2-提交 3-退回 4-完成',
  `money` decimal(10,2) NOT NULL COMMENT '金额',
  `pay_type` int(2) NOT NULL COMMENT '支付方式代码',
  `pay_state` int(2) NOT NULL DEFAULT '1' COMMENT '支付状态:1-未支付 2-已支付',
  `reason` varchar(200) DEFAULT NULL COMMENT '事由',
  `ohers` varchar(1000) DEFAULT NULL COMMENT '其他说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `recoup_dic_costclass1`
-- ----------------------------
DROP TABLE IF EXISTS `recoup_dic_costclass1`;
CREATE TABLE `recoup_dic_costclass1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `class1_code` varchar(30) DEFAULT NULL COMMENT '一级类别代码',
  `class1_name` varchar(90) NOT NULL COMMENT '一级类别名称',
  `input_code` varchar(30) NOT NULL COMMENT '类别输入码',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(1) NOT NULL DEFAULT '2' COMMENT '清除标记:1-是 2-否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `recoup_dic_costclass2`
-- ----------------------------
DROP TABLE IF EXISTS `recoup_dic_costclass2`;
CREATE TABLE `recoup_dic_costclass2` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `recoup_dic_payclass`
-- ----------------------------
DROP TABLE IF EXISTS `recoup_dic_payclass`;
CREATE TABLE `recoup_dic_payclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `class_name` varchar(90) NOT NULL COMMENT '支付方式名称',
  `input_code` varchar(30) NOT NULL COMMENT '输入码',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(1) NOT NULL DEFAULT '2' COMMENT '清除标记:1-是 2-否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `recoup_dic_project`
-- ----------------------------
DROP TABLE IF EXISTS `recoup_dic_project`;
CREATE TABLE `recoup_dic_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `project_code` varchar(90) DEFAULT NULL COMMENT '项目编码',
  `project_name` varchar(90) NOT NULL COMMENT '项目名称',
  `input_code` varchar(30) NOT NULL COMMENT '输入码',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(1) NOT NULL DEFAULT '2' COMMENT '清除标记:1-是 2-否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `recoup_dic_project`
-- ----------------------------
BEGIN;
INSERT INTO `recoup_dic_project` VALUES ('1', '101', '差旅费', 'clf', null, '2'), ('2', '102', '交通费', 'jtf', null, '2');
COMMIT;

-- ----------------------------
--  View structure for `columns`
-- ----------------------------
DROP VIEW IF EXISTS `columns`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `columns` AS select `information_schema`.`columns`.`TABLE_CATALOG` AS `TABLE_CATALOG`,`information_schema`.`columns`.`TABLE_SCHEMA` AS `TABLE_SCHEMA`,`information_schema`.`columns`.`TABLE_NAME` AS `TABLE_NAME`,`information_schema`.`columns`.`COLUMN_NAME` AS `COLUMN_NAME`,`information_schema`.`columns`.`ORDINAL_POSITION` AS `ORDINAL_POSITION`,`information_schema`.`columns`.`COLUMN_DEFAULT` AS `COLUMN_DEFAULT`,`information_schema`.`columns`.`IS_NULLABLE` AS `IS_NULLABLE`,`information_schema`.`columns`.`DATA_TYPE` AS `DATA_TYPE`,`information_schema`.`columns`.`CHARACTER_MAXIMUM_LENGTH` AS `CHARACTER_MAXIMUM_LENGTH`,`information_schema`.`columns`.`CHARACTER_OCTET_LENGTH` AS `CHARACTER_OCTET_LENGTH`,`information_schema`.`columns`.`NUMERIC_PRECISION` AS `NUMERIC_PRECISION`,`information_schema`.`columns`.`NUMERIC_SCALE` AS `NUMERIC_SCALE`,`information_schema`.`columns`.`CHARACTER_SET_NAME` AS `CHARACTER_SET_NAME`,`information_schema`.`columns`.`COLLATION_NAME` AS `COLLATION_NAME`,`information_schema`.`columns`.`COLUMN_TYPE` AS `COLUMN_TYPE`,`information_schema`.`columns`.`COLUMN_KEY` AS `COLUMN_KEY`,`information_schema`.`columns`.`EXTRA` AS `EXTRA`,`information_schema`.`columns`.`PRIVILEGES` AS `PRIVILEGES`,`information_schema`.`columns`.`COLUMN_COMMENT` AS `COLUMN_COMMENT` from `information_schema`.`columns` where (`information_schema`.`columns`.`TABLE_SCHEMA` = 'recoup_db');

SET FOREIGN_KEY_CHECKS = 1;
