/*
Navicat MySQL Data Transfer

Source Server         : 208.210
Source Server Version : 50402
Source Host           : 192.168.208.210:3306
Source Database       : recoup_db

Target Server Type    : MYSQL
Target Server Version : 50402
File Encoding         : 65001

Date: 2014-06-17 17:20:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pension_dept
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of pension_dept
-- ----------------------------
INSERT INTO `pension_dept` VALUES ('1', '管理部', '0', '1', '青岛', '无', null, '2');

-- ----------------------------
-- Table structure for pension_dict_info
-- ----------------------------
DROP TABLE IF EXISTS `pension_dict_info`;
CREATE TABLE `pension_dict_info` (
  `DICT_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `DICT_NAME` varchar(90) NOT NULL COMMENT '字典名称',
  `DICT_DATATABLE_NAME` varchar(90) NOT NULL COMMENT '数据表名',
  PRIMARY KEY (`DICT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=841 DEFAULT CHARSET=utf8 COMMENT='字典信息表:用于公共数据字典维护';

-- ----------------------------
-- Records of pension_dict_info
-- ----------------------------
INSERT INTO `pension_dict_info` VALUES ('797', '事故类型字典表', 'pension_dic_accidenttype');
INSERT INTO `pension_dict_info` VALUES ('798', '过敏源字典表', 'pension_dic_allergy');
INSERT INTO `pension_dict_info` VALUES ('800', '排班字典表', 'pension_dic_arrange');
INSERT INTO `pension_dict_info` VALUES ('801', '床位类型字典', 'pension_dic_bedtype');
INSERT INTO `pension_dict_info` VALUES ('802', '血型字典表', 'pension_dic_bloodtype');
INSERT INTO `pension_dict_info` VALUES ('803', '康娱子项目字典', 'pension_dic_class');
INSERT INTO `pension_dict_info` VALUES ('804', '菜系字典表', 'pension_dic_cuisine');
INSERT INTO `pension_dict_info` VALUES ('805', '残疾类型字典', 'pension_dic_deformity');
INSERT INTO `pension_dict_info` VALUES ('806', '文化程度字典', 'pension_dic_degree');
INSERT INTO `pension_dict_info` VALUES ('807', '配送类别字典表', 'pension_dic_delivery');
INSERT INTO `pension_dict_info` VALUES ('808', '离院状态字典', 'pension_dic_departstatus');
INSERT INTO `pension_dict_info` VALUES ('810', '部门字典', 'pension_dic_dept');
INSERT INTO `pension_dict_info` VALUES ('811', '饮食习惯字典表', 'pension_dic_dietaryhabit');
INSERT INTO `pension_dict_info` VALUES ('812', '配药单名称字典表', 'pension_dic_dosagename');
INSERT INTO `pension_dict_info` VALUES ('814', '事件字典表', 'pension_dic_event');
INSERT INTO `pension_dict_info` VALUES ('815', '家族关系字典', 'pension_dic_familytree');
INSERT INTO `pension_dict_info` VALUES ('816', '缴费项目字典', 'pension_dic_feeitems');
INSERT INTO `pension_dict_info` VALUES ('817', '就医类别字典表', 'pension_dic_hospitalize');
INSERT INTO `pension_dict_info` VALUES ('818', '既往史疾病类别字典', 'pension_dic_illanamnesis');
INSERT INTO `pension_dict_info` VALUES ('819', '物资类型字典表', 'pension_dic_itemtype');
INSERT INTO `pension_dict_info` VALUES ('820', '婚姻状况字典', 'pension_dic_marriage');
INSERT INTO `pension_dict_info` VALUES ('822', '民族字典表', 'pension_dic_nation');
INSERT INTO `pension_dict_info` VALUES ('823', '护理级别字典', 'pension_dic_nurse');
INSERT INTO `pension_dict_info` VALUES ('824', '老人类型字典表', 'pension_dic_oldertype');
INSERT INTO `pension_dict_info` VALUES ('825', '既往史非疾病类别字典', 'pension_dic_otheranamnesis');
INSERT INTO `pension_dict_info` VALUES ('826', '医疗费用支付方式字典', 'pension_dic_payment');
INSERT INTO `pension_dict_info` VALUES ('827', '支付方式字典', 'pension_dic_paystyle');
INSERT INTO `pension_dict_info` VALUES ('828', '支付方式字典表', 'pension_dic_payway');
INSERT INTO `pension_dict_info` VALUES ('829', '政治面貌字典', 'pension_dic_politics');
INSERT INTO `pension_dict_info` VALUES ('830', '康复项目字典表', 'pension_dic_recure_item');
INSERT INTO `pension_dict_info` VALUES ('831', '关系字典表', 'pension_dic_relationship');
INSERT INTO `pension_dict_info` VALUES ('832', '周报类型表', 'pension_dic_reporttype');
INSERT INTO `pension_dict_info` VALUES ('833', '结账方式字典表', 'pension_dic_settletype');
INSERT INTO `pension_dict_info` VALUES ('834', '康娱项目字典', 'pension_dic_super_class');
INSERT INTO `pension_dict_info` VALUES ('835', '药物单位字典表', 'pension_dic_unit');
INSERT INTO `pension_dict_info` VALUES ('836', '来访类型字典', 'pension_dic_visitstyle');
INSERT INTO `pension_dict_info` VALUES ('837', '预算类型字典表', 'pension_dict_budgettype');
INSERT INTO `pension_dict_info` VALUES ('839', '价表类型字典表', 'pension_dict_pricetype');
INSERT INTO `pension_dict_info` VALUES ('840', '退费类型字典表', 'pension_dict_refundtype');

-- ----------------------------
-- Table structure for pension_dic_message
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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='消息字典表';

-- ----------------------------
-- Records of pension_dic_message
-- ----------------------------

-- ----------------------------
-- Table structure for pension_employee
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
-- Records of pension_employee
-- ----------------------------
INSERT INTO `pension_employee` VALUES ('1', '管理员', '1', 'gly', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, null, null, null, null, '2');

-- ----------------------------
-- Table structure for pension_menu
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
-- Records of pension_menu
-- ----------------------------
INSERT INTO `pension_menu` VALUES ('001', '系统管理', null, '0', '1', null, null, 'system', '', '100', '1');
INSERT INTO `pension_menu` VALUES ('001001', '用户管理', null, '001', '2', null, null, 'userManage.jsf', '', '1', '1');
INSERT INTO `pension_menu` VALUES ('001002', '修改密码', null, '001', '2', null, null, 'updatePassword.jsf', '', '2', '1');
INSERT INTO `pension_menu` VALUES ('001003', '权限管理', null, '001', '2', null, null, 'roleManage.jsf', '', '3', '1');
INSERT INTO `pension_menu` VALUES ('001004', '数据字典维护', null, '001', '2', null, null, 'datadict.jsf', '', '4', '1');
INSERT INTO `pension_menu` VALUES ('001005', '系统参数配置', null, '001', '2', null, null, 'systemConfig.jsf', '', '5', '1');
INSERT INTO `pension_menu` VALUES ('001006', '消息历史记录', null, '001', '2', null, null, 'messageHistory.jsf', '1', '6', '1');
INSERT INTO `pension_menu` VALUES ('001007', '部门管理', null, '001', '2', null, null, 'deptManage.jsf', null, '7', '1');

-- ----------------------------
-- Table structure for pension_messagedelrecord
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
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8 COMMENT='消息删除记录表';

-- ----------------------------
-- Records of pension_messagedelrecord
-- ----------------------------

-- ----------------------------
-- Table structure for pension_messages
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
) ENGINE=InnoDB AUTO_INCREMENT=411 DEFAULT CHARSET=utf8 COMMENT='系统消息表';

-- ----------------------------
-- Records of pension_messages
-- ----------------------------

-- ----------------------------
-- Table structure for pension_role
-- ----------------------------
DROP TABLE IF EXISTS `pension_role`;
CREATE TABLE `pension_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限标识',
  `name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `inputCode` varchar(100) DEFAULT NULL COMMENT '输入码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  `cleared` int(11) DEFAULT '2' COMMENT '清除标记:1是2否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of pension_role
-- ----------------------------
INSERT INTO `pension_role` VALUES ('1', '管理员', 'gly', '最高权限', '2');

-- ----------------------------
-- Table structure for pension_role_menu
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
) ENGINE=InnoDB AUTO_INCREMENT=2225 DEFAULT CHARSET=utf8 COMMENT='权限-菜单表';

-- ----------------------------
-- Records of pension_role_menu
-- ----------------------------
INSERT INTO `pension_role_menu` VALUES ('2', '1', '001001', '用户管理');
INSERT INTO `pension_role_menu` VALUES ('3', '1', '001002', '修改密码');
INSERT INTO `pension_role_menu` VALUES ('4', '1', '001003', '权限管理');
INSERT INTO `pension_role_menu` VALUES ('5', '1', '001004', '数据字典维护');
INSERT INTO `pension_role_menu` VALUES ('6', '1', '001005', '系统参数配置');
INSERT INTO `pension_role_menu` VALUES ('7', '1', '001006', '消息历史记录');
INSERT INTO `pension_role_menu` VALUES ('2224', '1', '001007', null);

-- ----------------------------
-- Table structure for pension_system_config
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
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- ----------------------------
-- Records of pension_system_config
-- ----------------------------
INSERT INTO `pension_system_config` VALUES ('1', '1', 'LIVING_APPLY_DPT_ID', '11', null, '入住申请发送到的部门Id');
INSERT INTO `pension_system_config` VALUES ('2', '1', 'LIVING_NOTICE_DPT_ID', '34,35,29', null, '入住通知发送到的部门');
INSERT INTO `pension_system_config` VALUES ('3', '1', 'BUDGET_DPT_ID', '3,33', null, '财务申请发到的部门ID');
INSERT INTO `pension_system_config` VALUES ('4', '2', 'BUDGET_EMP_ID', '', null, '财务申请发到的人员ID');
INSERT INTO `pension_system_config` VALUES ('5', '1', 'VACATION_DPT_ID', '3,4', null, '请假申请发送到的部门ID');
INSERT INTO `pension_system_config` VALUES ('6', '3', 'DINING_DATE_TIME', '16', null, '零点餐时间');
INSERT INTO `pension_system_config` VALUES ('7', '1', 'DEPART_DPT_ID', '3,4,28', null, '离院申请发送的审批部门ID');
INSERT INTO `pension_system_config` VALUES ('8', '2', 'DEPART_EMP_ID', '', null, '离院申请发送的审批人员ID');
INSERT INTO `pension_system_config` VALUES ('9', '1', 'DEPART_NOTIFY_DPT_ID', '34', null, '离院通知发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('10', '1', 'MEDICAL_DPT_ID', '4,33', null, '就医登记单和项目统计单发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('11', '2', 'MEDICAL_EMP_ID', '', null, '就医登记单和项目统计单发送的人员ID');
INSERT INTO `pension_system_config` VALUES ('14', '4', 'PROJECT_URL', 'http://123.235.17.94:7000/pension', null, '工程URL');
INSERT INTO `pension_system_config` VALUES ('15', '1', 'LIVING_CHECK_DPT_ID', '28', null, '质检通知发送部门ID');
INSERT INTO `pension_system_config` VALUES ('19', '1', 'DESK_DEPT_ID', '4', null, '院长审批通过通知部门ID');
INSERT INTO `pension_system_config` VALUES ('20', '1', 'VACATION_BACK_DEPT_ID', '4,6,3,34,35,29,33', null, '返院登记信息发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('21', '1', 'VACATION_LEAVE_DEPT_ID', '4,6,3,34,35,29,33', null, '离院登记信息发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('22', '1', 'VISIT_DEPT_ID', '3,28', null, '入住探访通知部门ID');
INSERT INTO `pension_system_config` VALUES ('23', '1', 'LEAVE_BACK_DEPT_ID', '18', null, '查看请假审批页面可进行离院登记和返院登记的部门ID');
INSERT INTO `pension_system_config` VALUES ('24', '3', 'LONGEST_VISIT_TIME', '200', null, '最长探访时间（单位为分钟）');
INSERT INTO `pension_system_config` VALUES ('25', '1', 'ALL_NOTIFY_DPT_ID', '1,3,4,5,6,28,29,30,31,32,33,34,35,36', null, '全院所有部门');
INSERT INTO `pension_system_config` VALUES ('26', '1', 'REPAIR_DPT_ID', '19', null, '维修部门');
INSERT INTO `pension_system_config` VALUES ('28', '1', 'AGENT_APPLY_DPT_ID', '30', null, '代办服务申请发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('29', '4', 'DEFAULT_PAY_WAY', '1', null, '默认支付方式');
INSERT INTO `pension_system_config` VALUES ('31', '1', 'ACCIDENT_DPT_ID', '3,28', null, '事故上报部门ID');
INSERT INTO `pension_system_config` VALUES ('32', '1', 'INFUSION_APPLY_DPT_ID', '4', null, '输液审核部门ID');
INSERT INTO `pension_system_config` VALUES ('33', '2', 'LIVING_APPLY_EMP_ID', '', null, '入住申请发送到的人员ID');
INSERT INTO `pension_system_config` VALUES ('34', '2', 'LIVING_NOTICE_EMP_ID', '', null, '入住通知发送的人员ID');
INSERT INTO `pension_system_config` VALUES ('35', '2', 'LIVING_CHECK_EMP_ID', '', null, '质检通知发送人员ID');
INSERT INTO `pension_system_config` VALUES ('36', '2', 'DESK_EMP_ID', null, null, '院长审批通过通知人员ID');
INSERT INTO `pension_system_config` VALUES ('37', '2', 'VISIT_EMP_ID', '', null, '入住探访通知人员ID');
INSERT INTO `pension_system_config` VALUES ('38', '2', 'AGENT_APPLY_EMP_ID', '', null, '代办服务申请发送的人员ID');
INSERT INTO `pension_system_config` VALUES ('39', '2', 'ACCIDENT_EMP_ID', '', null, '事故上报人员ID');
INSERT INTO `pension_system_config` VALUES ('40', '2', 'INFUSION_APPLY_EMP_ID', '', null, '输液审核人员ID');
INSERT INTO `pension_system_config` VALUES ('41', '1', 'ACCIDENT_DEPT_ID', '6', null, '事故录入部门');
INSERT INTO `pension_system_config` VALUES ('42', '3', 'FINANCIAL_BALANCE_TYPE', '1', null, '财务结帐类型-根据字典表设置1个人2科室3全院');
INSERT INTO `pension_system_config` VALUES ('43', '1', 'FINANCIAL_DEPT_ID', '17', null, '财务部编号');
INSERT INTO `pension_system_config` VALUES ('44', '2', 'FINANCIAL_BALANCE_CHECK_ROLE_ID', '28,18,16', null, '有权限查看财务结算记录的角色编号');
INSERT INTO `pension_system_config` VALUES ('45', '2', 'REPAIR_APPROVE_EMP_ID', '', null, '审批工程排查维修申请的人ID（服务部经理）');
INSERT INTO `pension_system_config` VALUES ('46', '2', 'DETAIL_APPROVE_EMP_ID', '', null, '审批工程排查维修申请内容的人ID（副院长）');
INSERT INTO `pension_system_config` VALUES ('47', '4', 'FOOD_SEND_ITEMPURSEID', '15', null, '菜品外送对应的价表ID');
INSERT INTO `pension_system_config` VALUES ('48', '1', 'DRUG_RECEIVE_DEPT_ID', '4', null, '药物接收审核部门ID');
INSERT INTO `pension_system_config` VALUES ('49', '2', 'DRUG_RECEIVE_EMP_ID', '', null, '药物接收审核人员ID');
INSERT INTO `pension_system_config` VALUES ('50', '2', 'MOVE_APPLY_SEND_EMP_ID', '', null, '搬家申请发送的人员ID');
INSERT INTO `pension_system_config` VALUES ('51', '1', 'MOVE_APPLY_SEND_DEPT_ID', '30', null, '搬家申请发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('61', '1', 'SHIFT_CHANGE_CHECK_ROLE_ID', '28', null, '有权查看交班记录的角色ID');
INSERT INTO `pension_system_config` VALUES ('62', '4', 'START_YEAR', '2013', null, '系统开始年份');
INSERT INTO `pension_system_config` VALUES ('63', '3', 'DRUG_OVERDUL_DAY', '15', null, '药品过期提醒天数');
INSERT INTO `pension_system_config` VALUES ('64', '3', 'DRUG_SHORTAGE_QUATITY', '15', null, '药品提醒数量');
INSERT INTO `pension_system_config` VALUES ('65', '1', 'NURSE_DEPT_ID_REPORT', '13,14,15', null, '护理部编号-报表用');
INSERT INTO `pension_system_config` VALUES ('66', '2', 'REPORT_TYPE_ID', '1', null, '总体护理工作周报-默认周报类型');
INSERT INTO `pension_system_config` VALUES ('67', '1', 'NURSE_WEEKLY_REPORT_DEPT_ID', '6', null, '护理情况周报发送的部门');
INSERT INTO `pension_system_config` VALUES ('68', '2', 'NURSE_WEEKLY_REPORT_EMP_ID', null, null, '护理情况周报发送的员工ID');
INSERT INTO `pension_system_config` VALUES ('69', '1', 'NURSE_WEEKLY_REPORT_ROLE_ID', '28,1', null, '有权编辑护理周报的角色ID');
INSERT INTO `pension_system_config` VALUES ('70', '3', 'projectURL', 'http://192.168.208.211:8080/pension', null, '工程URL');
INSERT INTO `pension_system_config` VALUES ('71', '1', 'CAR_ORDER_DPT_ID', '3,28,30', null, '出车预约与发车发送的部门id');
INSERT INTO `pension_system_config` VALUES ('72', '1', 'CARE_APPOINTMENT_DEPT_ID', '6,28,3', null, '预约护理通知部门ID');
INSERT INTO `pension_system_config` VALUES ('73', '2', 'CARE_APPOINTMENT_EMP_ID', '', null, '预约护理通知的人员ID');
INSERT INTO `pension_system_config` VALUES ('74', '1', 'PURCHASE_EVALUATE_DEPT_ID', '30', null, '采购申请审核部门ID');
INSERT INTO `pension_system_config` VALUES ('75', '2', 'PURCHASE_EVALUATE_EMP_ID', '', null, '采购申请审核人员ID');
INSERT INTO `pension_system_config` VALUES ('77', '1', 'AGENT_EVAL_DEPT_ID', '29', null, '代办服务审批完成发送部门');
INSERT INTO `pension_system_config` VALUES ('78', '2', 'AGENT_EVAL_EMP_ID', null, null, '代办服务申请人员ID');
INSERT INTO `pension_system_config` VALUES ('79', '2', 'MOVE_APPROVE_SEND_EMP_ID', '', null, '搬家审批结果发送的人员ID');
INSERT INTO `pension_system_config` VALUES ('80', '1', 'MOVE_APPROVE_SEND_DEPT_ID', '30', null, '搬家审批结果发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('81', '3', 'RING_SERVICE_TIME_LIST', '1,2,3', null, '呼叫报告上级时间间隔');
INSERT INTO `pension_system_config` VALUES ('82', '4', 'VACATION_PRINT_FLAG', '2', null, '老人请假的离返院登记是否采用指纹机制 1为是 2为否');
INSERT INTO `pension_system_config` VALUES ('83', '1', 'CHECK_APPROVE_EMP_ID', '', null, '排查维修审批结果发送的人员ID');
INSERT INTO `pension_system_config` VALUES ('84', '1', 'CHECK_APPROVE_DEPT_ID', '29', null, '排查维修审批结果发送的部门ID');
INSERT INTO `pension_system_config` VALUES ('85', '3', 'VISIT_PRINT_FLAG', '2', null, '探访管理是否采用指纹机制 1为是 2为否');
INSERT INTO `pension_system_config` VALUES ('87', '2', 'REPAIR_EMP_ID', '20', null, '维修人员');
INSERT INTO `pension_system_config` VALUES ('88', '1', 'REPAIR_APPROVE_DEPT_ID', '30', null, '审批工程排查维修申请的部门ID（服务部）');
INSERT INTO `pension_system_config` VALUES ('89', '1', 'DETAIL_APPROVE_DEPT_ID', '28', null, '审批工程排查维修申请内容的部门ID（副院长）');
INSERT INTO `pension_system_config` VALUES ('90', '3', 'ADVANCE_CANCEL_HAIRCAUT_TIME', '1', null, '取消预约 需要提前的时间 单位为小时');
INSERT INTO `pension_system_config` VALUES ('91', '4', 'ELECTRICITY_PURSE', '20', null, '电费价表主键');
INSERT INTO `pension_system_config` VALUES ('92', '4', 'MOVE_ITEM_PURSE_TYPE_ID', '4', null, '价表类别为搬家的ID');
INSERT INTO `pension_system_config` VALUES ('93', '4', 'CAR_ITEM_PURSE_TYPE_ID', '3', null, '价表类别为用车的ID');
INSERT INTO `pension_system_config` VALUES ('94', '4', 'ELECTRICITY_ITEM_PURSE_TYPE_ID', '5', null, '价表类别为电表的ID');
INSERT INTO `pension_system_config` VALUES ('95', '4', 'INFUSION_ITEM_PURSE_TYPE_ID', '7', null, '价表类别为输液的ID');
INSERT INTO `pension_system_config` VALUES ('96', '4', 'AGENT_ITEM_PURSE_TYPE_ID', '6', null, '价表类别为代办服务的ID');
INSERT INTO `pension_system_config` VALUES ('97', '4', 'REPAIR_ITEM_PURSE_TYPE_ID', '8', null, '价表类别为维修的ID');
INSERT INTO `pension_system_config` VALUES ('98', '3', 'DEFAULT_OLDMAN_JPG', '/images/oldManage/oldMan.jpg', null, '默认男老人头像');
INSERT INTO `pension_system_config` VALUES ('99', '3', 'DEFAULT_OLDWOMAN_JPG', '/images/oldManage/oldWomen.jpg', null, '默认女老人头像');
INSERT INTO `pension_system_config` VALUES ('100', '1', 'AGENT_TIME', '1', null, '代办服务默认查询时间（月）');
INSERT INTO `pension_system_config` VALUES ('101', '1', 'AGENT_EVALUATE_SHOW', '1', null, '代办服务是否显示1是1否');
INSERT INTO `pension_system_config` VALUES ('102', '1', 'CAR_ORDER_EMP_ID', null, null, '出车预约与发车发送的人员id');
INSERT INTO `pension_system_config` VALUES ('108', '1', 'VISIT_OLDER_REPORT', '1,2,3', null, '探访人次');
INSERT INTO `pension_system_config` VALUES ('109', '1', 'VACATION_EMP_ID', null, null, null);
INSERT INTO `pension_system_config` VALUES ('110', '1', 'DELAYBACK_DEPT_ID', '29', null, '未按时返院通知部门ID');
INSERT INTO `pension_system_config` VALUES ('111', '1', 'DELAYBACK_EMP_ID', null, null, '未按时返院通知员工ID');
INSERT INTO `pension_system_config` VALUES ('112', '1', 'NATURAL_MONTH_ENABLED ', '1', null, '是否启用自然月算法1是2否');

-- ----------------------------
-- Table structure for pension_sys_user
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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='登录用户表';

-- ----------------------------
-- Records of pension_sys_user
-- ----------------------------
INSERT INTO `pension_sys_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1', '1', 'admin', null, null, '2', '2', 'admin');

-- ----------------------------
-- View structure for columns
-- ----------------------------
DROP VIEW IF EXISTS `columns`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER  VIEW `columns` AS select `information_schema`.`columns`.`TABLE_CATALOG` AS `TABLE_CATALOG`,`information_schema`.`columns`.`TABLE_SCHEMA` AS `TABLE_SCHEMA`,`information_schema`.`columns`.`TABLE_NAME` AS `TABLE_NAME`,`information_schema`.`columns`.`COLUMN_NAME` AS `COLUMN_NAME`,`information_schema`.`columns`.`ORDINAL_POSITION` AS `ORDINAL_POSITION`,`information_schema`.`columns`.`COLUMN_DEFAULT` AS `COLUMN_DEFAULT`,`information_schema`.`columns`.`IS_NULLABLE` AS `IS_NULLABLE`,`information_schema`.`columns`.`DATA_TYPE` AS `DATA_TYPE`,`information_schema`.`columns`.`CHARACTER_MAXIMUM_LENGTH` AS `CHARACTER_MAXIMUM_LENGTH`,`information_schema`.`columns`.`CHARACTER_OCTET_LENGTH` AS `CHARACTER_OCTET_LENGTH`,`information_schema`.`columns`.`NUMERIC_PRECISION` AS `NUMERIC_PRECISION`,`information_schema`.`columns`.`NUMERIC_SCALE` AS `NUMERIC_SCALE`,`information_schema`.`columns`.`CHARACTER_SET_NAME` AS `CHARACTER_SET_NAME`,`information_schema`.`columns`.`COLLATION_NAME` AS `COLLATION_NAME`,`information_schema`.`columns`.`COLUMN_TYPE` AS `COLUMN_TYPE`,`information_schema`.`columns`.`COLUMN_KEY` AS `COLUMN_KEY`,`information_schema`.`columns`.`EXTRA` AS `EXTRA`,`information_schema`.`columns`.`PRIVILEGES` AS `PRIVILEGES`,`information_schema`.`columns`.`COLUMN_COMMENT` AS `COLUMN_COMMENT` from `information_schema`.`columns` where (`information_schema`.`columns`.`TABLE_SCHEMA` = 'pension_db') ;
