/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : work

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 14/07/2024 15:58:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administratoruser
-- ----------------------------
DROP TABLE IF EXISTS `administratoruser`;
CREATE TABLE `administratoruser`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permissions` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administratoruser
-- ----------------------------
INSERT INTO `administratoruser` VALUES ('1', 'admin', '$2a$10$p/v27cGbUIPENP1Sw96mEO/xbENtbhM6POrAl63vlGBiNf.BZ.Apq', 'hasai', 'admin');

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `job` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salary` double(10, 2) NULL DEFAULT NULL,
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `recruitmentuser_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', '1', '1', '1', 1.00, '213333333333333333333333333333333211111111111', '1');
INSERT INTO `company` VALUES ('46c017c1-790e-40a6-875e-ade4c7878958', '111', '222', '333', 4444.00, '55555', '1');
INSERT INTO `company` VALUES ('99cc0e0b-657d-4b04-ba2d-1a9b23b1e762', 'aaa', 'bbb', 'ccc', 800.00, 'asdasdasdsadsa', 'e7747655-ff26-47df-be21-a067d5ac3df7');

-- ----------------------------
-- Table structure for jobseekers
-- ----------------------------
DROP TABLE IF EXISTS `jobseekers`;
CREATE TABLE `jobseekers`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `permissions` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jobseekers
-- ----------------------------
INSERT INTO `jobseekers` VALUES ('1', 'user', '$2a$10$p/v27cGbUIPENP1Sw96mEO/xbENtbhM6POrAl63vlGBiNf.BZ.Apq', NULL, '女', 0, 'jobseekers');

-- ----------------------------
-- Table structure for recruitmentuser
-- ----------------------------
DROP TABLE IF EXISTS `recruitmentuser`;
CREATE TABLE `recruitmentuser`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(655) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `permissions` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recruitmentuser
-- ----------------------------
INSERT INTO `recruitmentuser` VALUES ('1', 'user', '$2a$10$p/v27cGbUIPENP1Sw96mEO/xbENtbhM6POrAl63vlGBiNf.BZ.Apq', 'sss', '男', 0, 'recruitment');
INSERT INTO `recruitmentuser` VALUES ('e7747655-ff26-47df-be21-a067d5ac3df7', '666', '$2a$10$/.isJ6zPTmnOYspFw3TP/eI.AvtJcYKXCn4rt7GucOr5AGH2/YblC', '哈赛 徐', '男', 16, 'recruitment');

SET FOREIGN_KEY_CHECKS = 1;
