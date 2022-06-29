/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.142.128本地
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.142.128:3306
 Source Schema         : weibo

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 06/04/2022 09:11:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `weibo_id` int NULL DEFAULT NULL,
                            `comment_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `comment_create_time` datetime NULL DEFAULT NULL,
                            `comment_user_id` int NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for def_avt
-- ----------------------------
DROP TABLE IF EXISTS `def_avt`;
CREATE TABLE `def_avt`  (
                            `default_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            PRIMARY KEY (`default_avatar`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of def_avt
-- ----------------------------

-- ----------------------------
-- Table structure for fans
-- ----------------------------
DROP TABLE IF EXISTS `fans`;
CREATE TABLE `fans`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `fans_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `locked` bit(1) NULL DEFAULT b'0' COMMENT '当前关系是否维持 1表示是 0表示否',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of fans
-- ----------------------------

-- ----------------------------
-- Table structure for order_information
-- ----------------------------
DROP TABLE IF EXISTS `order_information`;
CREATE TABLE `order_information`  (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `start_time` datetime NULL DEFAULT NULL,
                                      `end_time` datetime NULL DEFAULT NULL,
                                      `user_id` int NULL DEFAULT NULL,
                                      `amount` int NULL DEFAULT NULL,
                                      `order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_information
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
                         `id` int NOT NULL,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `nameZh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_user', '用户');
INSERT INTO `role` VALUES (2, 'ROLE_admin', '系统管理员');
INSERT INTO `role` VALUES (3, 'ROLE_dba', '数据管理员');

-- ----------------------------
-- Table structure for trtat
-- ----------------------------
DROP TABLE IF EXISTS `trtat`;
CREATE TABLE `trtat`  (
                          `name` int NOT NULL AUTO_INCREMENT,
                          `age` int NULL DEFAULT NULL,
                          PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of trtat
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` int NOT NULL AUTO_INCREMENT COMMENT '用户表的主键。每个用户对应一个唯一的ID，且该值自增',
                         `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名(用户名唯一)',
                         `user_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户绑定的手机',
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
                         `user_sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别',
                         `user_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的简介',
                         `user_birth` date NULL DEFAULT NULL COMMENT '用户生日',
                         `user_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的头像',
                         `enabled` bit(1) NULL DEFAULT b'1' COMMENT '账户是否可用',
                         `locked` bit(1) NULL DEFAULT b'0' COMMENT '账户是否被锁定',
                         `create_time` datetime NULL DEFAULT NULL COMMENT '账户的创建时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '$2a$10$eVhzKaW0K2wSRMk/H1yXKeZsR64IlNZagfkXPyNYn/3cCKn2jHFsG', NULL, NULL, NULL, NULL, b'1', b'0', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `uid` int NULL DEFAULT NULL,
                              `rid` int NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 2);

-- ----------------------------
-- Table structure for user_vip
-- ----------------------------
DROP TABLE IF EXISTS `user_vip`;
CREATE TABLE `user_vip`  (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '会员表id',
                             `user_id` int NULL DEFAULT NULL COMMENT 'user表主键',
                             `vip_endTime` datetime NULL DEFAULT NULL COMMENT '会员结束时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_vip
-- ----------------------------

-- ----------------------------
-- Table structure for weibo
-- ----------------------------
DROP TABLE IF EXISTS `weibo`;
CREATE TABLE `weibo`  (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `weibo_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `weibo_time` datetime NULL DEFAULT NULL,
                          `comment_count` int NULL DEFAULT NULL,
                          `like_count` int NULL DEFAULT NULL,
                          `enabled` bit(1) NULL DEFAULT NULL,
                          `deleted` bit(1) NULL DEFAULT NULL,
                          `repost_count` int NULL DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of weibo
-- ----------------------------

-- ----------------------------
-- Table structure for weibo_images
-- ----------------------------
DROP TABLE IF EXISTS `weibo_images`;
CREATE TABLE `weibo_images`  (
                                 `weibo_id` int NULL DEFAULT NULL,
                                 `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                 `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of weibo_images
-- ----------------------------

-- ----------------------------
-- Table structure for weibo_like
-- ----------------------------
DROP TABLE IF EXISTS `weibo_like`;
CREATE TABLE `weibo_like`  (
                               `weibo_id` int NULL DEFAULT NULL,
                               `user_id` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of weibo_like
-- ----------------------------

-- ----------------------------
-- Table structure for weibo_videos
-- ----------------------------
DROP TABLE IF EXISTS `weibo_videos`;
CREATE TABLE `weibo_videos`  (
                                 `weibo_id` int NULL DEFAULT NULL,
                                 `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                 `video_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of weibo_videos
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
