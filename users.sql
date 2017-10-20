/*
Navicat MySQL Data Transfer

Source Server         : aliyun_cent
Source Server Version : 50636
Source Host           : 101.200.44.47:3306
Source Database       : xuzc

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2017-10-20 16:19:27
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id`       INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `userid`   INT(10) UNSIGNED NOT NULL
  COMMENT '用户Id',
  `username` VARCHAR(25)               DEFAULT NULL
  COMMENT '用户名称',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '123', 'bob');
