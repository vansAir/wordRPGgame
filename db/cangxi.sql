/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : cangxi

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 23/03/2021 10:47:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '地区id',
  `area_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '地区名',
  `attack` decimal(20, 2) NOT NULL COMMENT '攻击力',
  `defense` decimal(20, 2) NOT NULL COMMENT '防御力',
  `lucky` decimal(20, 2) NOT NULL COMMENT '幸运值',
  `speed` decimal(20, 2) NOT NULL COMMENT '速度',
  `health` decimal(20, 2) NOT NULL COMMENT '生命值',
  `money` decimal(20, 2) NOT NULL COMMENT '金币',
  `kill_probability` decimal(20, 2) NOT NULL COMMENT '即死几率',
  `strike_back` decimal(20, 2) NOT NULL COMMENT '反击几率',
  `double_hit` decimal(20, 2) NOT NULL COMMENT '连击几率',
  `critical_hit` decimal(20, 2) NOT NULL COMMENT '暴击几率',
  `area_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '地点描述',
  `thief` decimal(20, 2) NOT NULL COMMENT '偷窃率',
  `attack_probability` decimal(20, 2) NOT NULL COMMENT '必中概率',
  `miss_probability` decimal(20, 2) NOT NULL COMMENT '闪避率',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES ('0', '王之渡', 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, '传说中上古时代的渡口，秦始皇曾在此处飞升。因此每年农历七月十五，都能听见天空隐隐约约传来一阵声音：”我，秦始皇，打钱。“', 1.00, 0.00, 0.00);
INSERT INTO `area` VALUES ('1', '马桑场', 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, '雄纠纠、气昂昂。马桑人民热爱和平,但为了和平,从不也永不害怕战争。马桑人常说：”我的神不爱和平。“', 1.00, 0.00, 0.00);
INSERT INTO `area` VALUES ('2', '资中台', 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, '善良和蔼的资中人，从不主动占人便宜，喜欢吃亏，几乎不抬杠。但是如果你敢碰我，不出三秒你就会跪在地上求我起来你家里没有钱。', 1.00, 0.00, 0.00);

-- ----------------------------
-- Table structure for back_package
-- ----------------------------
DROP TABLE IF EXISTS `back_package`;
CREATE TABLE `back_package`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '背包内物品id',
  `user_id` int(0) NOT NULL COMMENT '物品对应用户id',
  `goods_id` int(0) NOT NULL COMMENT '物品id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '物品名称',
  `goods_rank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '物品评级',
  `goods_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '物品描述',
  `nums` int(0) NOT NULL COMMENT '物品数量',
  `can_use` int(0) NULL DEFAULT NULL COMMENT '是否可以主动使用',
  `can_thief` int(0) NULL DEFAULT NULL COMMENT '是否可以被偷窃',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of back_package
-- ----------------------------

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '装备id',
  `equipment_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '装备名称',
  `attack` decimal(20, 2) NOT NULL COMMENT '攻击力',
  `defense` decimal(20, 2) NOT NULL COMMENT '防御力',
  `lucky` decimal(20, 2) NOT NULL COMMENT '幸运值',
  `speed` decimal(20, 2) NOT NULL COMMENT '速度',
  `health` decimal(20, 2) NOT NULL COMMENT '生命值',
  `money` decimal(20, 2) NOT NULL COMMENT '金币',
  `kill_probability` decimal(20, 2) NOT NULL COMMENT '即死几率',
  `strike_back` decimal(20, 2) NOT NULL COMMENT '反击几率',
  `double_hit` decimal(20, 2) NOT NULL COMMENT '连击几率',
  `critical_hit` decimal(20, 2) NOT NULL COMMENT '暴击几率',
  `equ_rank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '装备评级',
  `equ_type` int(0) NOT NULL COMMENT '所属类别',
  `equ_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '装备描述',
  `thief` decimal(20, 2) NOT NULL COMMENT '偷窃率',
  `attack_probability` decimal(20, 2) NOT NULL COMMENT '必中概率',
  `miss_probability` decimal(20, 2) NOT NULL COMMENT '闪避率',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES ('0', '一级头', 0.00, 10.00, 0.00, 0.00, 100.00, 0.00, 1.00, 1.00, 1.00, 1.00, 'N', 1, '最下级的头盔，甚至无法抵挡任宏潇小时候耍的弹绷子。', 0.00, 0.00, 0.00);
INSERT INTO `equipment` VALUES ('1', '布甲', 0.00, 10.00, 0.00, 0.00, 100.00, 0.00, 0.00, 0.00, 0.00, 0.00, 'N', 2, '最下级的护甲，防御能力甚至不如任宏潇的黄色坎肩。', 0.00, 0.00, 0.00);
INSERT INTO `equipment` VALUES ('2', '人字拖', 0.00, 10.00, 0.00, 50.00, 100.00, 0.00, 0.00, 0.00, 0.00, 0.00, 'N', 3, '最下级的鞋子，甚至跑不过赤脚的任宏潇。', 0.00, 0.00, 0.00);
INSERT INTO `equipment` VALUES ('3', '铅笔刀', 15.00, 10.00, 0.00, 0.00, 100.00, 0.00, 0.00, 0.00, 0.00, 0.00, 'N', 4, '最下级的武器，甚至无法和任宏潇小时候挨打的黄荆条相比。', 0.00, 0.00, 0.00);
INSERT INTO `equipment` VALUES ('4', '腕带', 0.00, 10.00, 0.00, 15.00, 100.00, 0.00, 0.00, 0.00, 0.00, 0.00, 'N', 5, '最下级的饰品，打篮球的时候让你自认为很帅但是实际上就很捞。', 0.00, 0.00, 0.00);

-- ----------------------------
-- Table structure for fate
-- ----------------------------
DROP TABLE IF EXISTS `fate`;
CREATE TABLE `fate`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '天命id',
  `fate_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '天命名称',
  `fate_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '天命描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fate
-- ----------------------------
INSERT INTO `fate` VALUES ('10001', '跑得快', '因为童年生活在乡下，经常被野狗追逐，于是练就一身跑功。无论任何情况，一定会是先手。');
INSERT INTO `fate` VALUES ('10002', '神偷', '从小便活络在各大驿站，以偷窃为生。从未失手，人称摸钩圣手。拥有极高的偷窃率。');
INSERT INTO `fate` VALUES ('10003', '摸钩儿', '从小便活络在各大驿站，以偷窃为生。七岁那一年不小心被抓住一次，打了个半死。从此励志练习偷窃技术，拥有更高偷窃率和速度。');
INSERT INTO `fate` VALUES ('10004', '残疾人', '天生有所残缺，但身残志坚。移动速度会更慢，但是所受到的所有伤害也会更少。');
INSERT INTO `fate` VALUES ('10005', '莽夫', '出生于屠夫世家，从小与猪为伍，精通刀法。不是经常能砍到人，但是刀刀见血。暴击会更高，但是相应的会降低必中。');
INSERT INTO `fate` VALUES ('10006', '斗鸡眼', '因为斗鸡眼从小就被嘲笑，但是善良的品质吸引了村花高翠兰，人人都说是上辈子修来的福分。必中率会降低，但是幸运值会增加。');
INSERT INTO `fate` VALUES ('20001', '武器大师', '一生贡献给打铁，据说隔壁一位叫可汗的邻居每天夜里都会做噩梦，噩梦中只有无尽的打铁声。开局赠送一颗+12的装备强化石。');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '物品id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '物品名称',
  `goods_rank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '物品评级',
  `goods_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '物品描述',
  `can_use` int(0) NULL DEFAULT NULL COMMENT '是否可以主动使用',
  `can_thief` int(0) NULL DEFAULT NULL COMMENT '是否可以被偷窃',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '气球', 'N', '普普通通的气球，可以把它吹起来放走，什么都不会发生。', 0, 1);
INSERT INTO `goods` VALUES (2, '强化石', 'R', '强化装备所必须的蓝色石头。', 0, 1);
INSERT INTO `goods` VALUES (3, '装备置换卷', 'B', '是不是看手头上的装备不爽了，赶紧摇TM一发。', 0, 1);
INSERT INTO `goods` VALUES (4, '还要大的气球', 'A', '普普通通的气球，可以把它吹起来放走，什么都不会发生。', 0, 1);
INSERT INTO `goods` VALUES (5, '相当大的气球', 'S', '普普通通的气球，可以把它吹起来放走，什么都不会发生。', 0, 1);
INSERT INTO `goods` VALUES (6, '最大的气球', 'SS', '普普通通的气球，可以把它吹起来放走，什么都不会发生。', 0, 1);
INSERT INTO `goods` VALUES (7, '真的最大的气球', 'SSS', '普普通通的气球，可以把它吹起来放走，什么都不会发生。', 0, 1);
INSERT INTO `goods` VALUES (8, '倒数第二大的气球', 'Z', '普普通通的气球，可以把它吹起来放走，什么都不会发生。', 0, 1);
INSERT INTO `goods` VALUES (9, '天地初开气球之星', 'EX', '普普通通的气球，可以把它吹起来放走，什么都不会发生。', 0, 1);

-- ----------------------------
-- Table structure for luck_draw
-- ----------------------------
DROP TABLE IF EXISTS `luck_draw`;
CREATE TABLE `luck_draw`  (
  `id` int(0) NOT NULL COMMENT '抽奖次数id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `use_times` int(0) NOT NULL COMMENT '剩余抽奖次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of luck_draw
-- ----------------------------

-- ----------------------------
-- Table structure for skill
-- ----------------------------
DROP TABLE IF EXISTS `skill`;
CREATE TABLE `skill`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '技能id',
  `skill_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '技能名称',
  `skill_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '技能描述',
  `skill_rank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '技能评级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of skill
-- ----------------------------
INSERT INTO `skill` VALUES ('0', '乱打', '双手画圆的最基础攻击技能,常见于小时候和隔壁幼儿园打架。', 'N');

-- ----------------------------
-- Table structure for user_equipment
-- ----------------------------
DROP TABLE IF EXISTS `user_equipment`;
CREATE TABLE `user_equipment`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '装备id',
  `equipment_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '装备名称',
  `attack` decimal(20, 2) NOT NULL COMMENT '攻击力',
  `defense` decimal(20, 2) NOT NULL COMMENT '防御力',
  `lucky` decimal(20, 2) NOT NULL COMMENT '幸运值',
  `speed` decimal(20, 2) NOT NULL COMMENT '速度',
  `health` decimal(20, 2) NOT NULL COMMENT '生命值',
  `money` decimal(20, 2) NOT NULL COMMENT '金币',
  `kill_probability` decimal(20, 2) NOT NULL COMMENT '即死几率',
  `strike_back` decimal(20, 2) NOT NULL COMMENT '反击几率',
  `double_hit` decimal(20, 2) NOT NULL COMMENT '连击几率',
  `critical_hit` decimal(20, 2) NOT NULL COMMENT '暴击几率',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `equ_rank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '装备评级',
  `level` int(0) NOT NULL COMMENT '强化等级',
  `equ_type` int(0) NOT NULL COMMENT '所属类别',
  `equ_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '装备描述',
  `thief` decimal(20, 2) NOT NULL COMMENT '偷窃率',
  `attack_probability` decimal(20, 2) NOT NULL COMMENT '必中概率',
  `miss_probability` decimal(20, 2) NOT NULL COMMENT '闪避率',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_equipment
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `attack` decimal(20, 2) NOT NULL COMMENT '攻击力',
  `defense` decimal(20, 2) NOT NULL COMMENT '防御力',
  `lucky` decimal(20, 2) NOT NULL COMMENT '幸运值',
  `speed` decimal(20, 2) NOT NULL COMMENT '速度',
  `health` decimal(20, 2) NOT NULL COMMENT '生命值',
  `money` decimal(20, 2) NOT NULL COMMENT '金币',
  `kill_probability` decimal(20, 2) NOT NULL COMMENT '即死几率',
  `strike_back` decimal(20, 2) NOT NULL COMMENT '反击几率',
  `double_hit` decimal(20, 2) NOT NULL COMMENT '连击几率',
  `critical_hit` decimal(20, 2) NOT NULL COMMENT '暴击几率',
  `attack_probability` decimal(20, 2) NOT NULL COMMENT '必中概率',
  `miss_probability` decimal(20, 2) NOT NULL COMMENT '闪避率',
  `thief` decimal(20, 2) NOT NULL COMMENT '偷窃率',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '出生地',
  `head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '头盔',
  `body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '衣服',
  `arms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '武器',
  `shoes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '鞋子',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `prefix_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '前缀',
  `skill` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '技能',
  `fate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '天命',
  `ornaments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '饰品',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
