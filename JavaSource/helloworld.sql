/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : helloworld
Target Host     : localhost:3306
Target Database : helloworld
Date: 2013-07-25 21:15:58
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for dept_info
-- ----------------------------
DROP TABLE IF EXISTS `dept_info`;
CREATE TABLE `dept_info` (
  `dept_id` bigint(20) NOT NULL,
  `dept_name` varchar(255) NOT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `dept_info` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'tom', null);
