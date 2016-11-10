/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : dcc_rsrept

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-10 20:21:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for model_detail
-- ----------------------------
DROP TABLE IF EXISTS `model_detail`;
CREATE TABLE `model_detail` (
  `id` int(11) NOT NULL,
  `phase_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `phase_id` (`phase_id`),
  CONSTRAINT `model_detail_ibfk_1` FOREIGN KEY (`phase_id`) REFERENCES `phase_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of model_detail
-- ----------------------------
INSERT INTO `model_detail` VALUES ('1', '1', 'CMM', null);
INSERT INTO `model_detail` VALUES ('2', '2', 'JM', 'J-M model');
INSERT INTO `model_detail` VALUES ('3', '2', 'GO', 'GO model');

-- ----------------------------
-- Table structure for phase_detail
-- ----------------------------
DROP TABLE IF EXISTS `phase_detail`;
CREATE TABLE `phase_detail` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of phase_detail
-- ----------------------------
INSERT INTO `phase_detail` VALUES ('1', 'before_testing', '测试阶段前');
INSERT INTO `phase_detail` VALUES ('2', 'testing', '测试阶段');

-- ----------------------------
-- Table structure for project_detail
-- ----------------------------
DROP TABLE IF EXISTS `project_detail`;
CREATE TABLE `project_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_detail
-- ----------------------------
INSERT INTO `project_detail` VALUES ('1', '仪控软件评估项目1', '2016-10-18 19:47:32', '2016-11-02 11:07:56', '小明', '未项目评估新建的任务');
INSERT INTO `project_detail` VALUES ('2', '仪控软件评估项目2', '2016-10-18 19:47:32', '2016-11-02 11:07:56', '小红', '未项目评估新建的任务');

-- ----------------------------
-- Table structure for result_detail
-- ----------------------------
DROP TABLE IF EXISTS `result_detail`;
CREATE TABLE `result_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `model_id` int(11) DEFAULT NULL,
  `input_dataset` varchar(255) DEFAULT NULL,
  `resultset` varchar(255) DEFAULT NULL,
  `input_dataset_key` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `model_id` (`model_id`),
  CONSTRAINT `result_detail_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project_detail` (`id`),
  CONSTRAINT `result_detail_ibfk_2` FOREIGN KEY (`model_id`) REFERENCES `model_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of result_detail
-- ----------------------------
INSERT INTO `result_detail` VALUES ('1', '1', '1', '{\"rank\":1,\"ksloc\":5}', '{\"mttf\":1200,\"failure_rate\", 0.001}', '', '2016-11-01 12:44:03', '2016-11-02 12:44:07');
INSERT INTO `result_detail` VALUES ('2', '1', '2', '{\"failure_time\":[1.0,5.0,7.0,8.0,9.0]}', '{\"mttf\":1200,\"failure_rate\", 0.001}', 'iexieixasw', null, null);
