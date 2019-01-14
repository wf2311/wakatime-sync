-- ----------------------------
-- Table structure for day_category
-- ----------------------------
DROP TABLE IF EXISTS `day_category`;
CREATE TABLE `day_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL COMMENT '日期',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日category统计表';
ALTER TABLE `day_category` add index idx_1(day,name);

-- ----------------------------
-- Table structure for day_dependency
-- ----------------------------
DROP TABLE IF EXISTS `day_dependency`;
CREATE TABLE `day_dependency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL COMMENT '日期',
  `name` varchar(300) DEFAULT NULL COMMENT '名称',
  `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日dependency统计表';
ALTER TABLE `day_dependency` add index idx_1(day,name);

-- ----------------------------
-- Table structure for day_editor
-- ----------------------------
DROP TABLE IF EXISTS `day_editor`;
CREATE TABLE `day_editor` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `day` date DEFAULT NULL COMMENT '日期',
    `name` varchar(30) DEFAULT NULL COMMENT '名称',
    `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日编辑器统计表';
ALTER TABLE `day_editor` add index idx_1(day,name);

-- ----------------------------
-- Table structure for day_entity
-- ----------------------------
DROP TABLE IF EXISTS `day_entity`;
CREATE TABLE `day_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL COMMENT '日期',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `simple_name` varchar(50) DEFAULT NULL COMMENT '简称',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日实体统计表';
ALTER TABLE `day_entity` add index idx_1(day,name);

-- ----------------------------
-- Table structure for day_grand_total
-- ----------------------------
DROP TABLE IF EXISTS `day_grand_total`;
CREATE TABLE `day_grand_total` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `day` date DEFAULT NULL COMMENT '日期',
 `name` varchar(50) DEFAULT NULL COMMENT '名称',
 `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
 `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日合计统计表';
ALTER TABLE `day_grand_total` add index idx_1(day,name);

-- ----------------------------
-- Table structure for day_language
-- ----------------------------
DROP TABLE IF EXISTS `day_language`;
CREATE TABLE `day_language` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL COMMENT '日期',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日语言类型统计表';
ALTER TABLE `day_language` add index idx_1(day,name);

-- ----------------------------
-- Table structure for day_operating_system
-- ----------------------------
DROP TABLE IF EXISTS `day_operating_system`;
CREATE TABLE `day_operating_system` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL COMMENT '日期',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日操作系统统计表';
ALTER TABLE `day_operating_system` add index idx_1(day,name);

-- ----------------------------
-- Table structure for day_project
-- ----------------------------
DROP TABLE IF EXISTS `day_project`;
CREATE TABLE `day_project` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `day` date DEFAULT NULL COMMENT '日期',
 `name` varchar(50) DEFAULT NULL COMMENT '名称',
 `total_seconds` int(11) DEFAULT NULL COMMENT '总时长(秒)',
 `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日项目统计表';
ALTER TABLE `day_project` add index idx_1(day,name);

-- ----------------------------
-- Table structure for duration
-- ----------------------------
DROP TABLE IF EXISTS `duration`;
CREATE TABLE `duration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dependencies` json DEFAULT NULL COMMENT '依赖',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `duration` double DEFAULT NULL COMMENT '持续时间(秒)',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `project` varchar(50) DEFAULT NULL COMMENT '所属项目',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '持续时间统计表';
ALTER TABLE `duration` add index idx_1(start_time,project);

-- ----------------------------
-- Table structure for heart_beat
-- ----------------------------
DROP TABLE IF EXISTS `heart_beat`;
CREATE TABLE `heart_beat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `simple_name` varchar(50) DEFAULT NULL COMMENT '简称',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `uuid` varchar(50) DEFAULT NULL COMMENT 'UUID',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '心跳统计表';
ALTER TABLE `heart_beat` add index idx_1(time);

-- ----------------------------
-- Table structure for project_duration
-- ----------------------------
DROP TABLE IF EXISTS `project_duration`;
CREATE TABLE `project_duration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dependencies` json DEFAULT NULL COMMENT '依赖',
  `branch` varchar(20) DEFAULT NULL COMMENT '所属分支',
  `start_time` datetime(3) DEFAULT NULL COMMENT '开始时间',
  `duration` double DEFAULT NULL COMMENT '持续时间(秒)',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `entity` varchar(255) DEFAULT NULL COMMENT '实体',
  `language` varchar(20) DEFAULT NULL COMMENT '语言类型',
  `project` varchar(50) DEFAULT NULL COMMENT '所属项目',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '项目持续时间统计表';
ALTER TABLE `project_duration` add index idx_1(project,start_time);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `uuid` varchar(50) DEFAULT NULL COMMENT 'UUID',
    `name` varchar(30) DEFAULT NULL COMMENT '项目名称',
    `privacy` varchar(30) DEFAULT NULL COMMENT '项目隐私',
    `public_url` varchar(300) DEFAULT NULL COMMENT 'url',
    `repository` varchar(500) DEFAULT NULL COMMENT '仓库',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '项目基本信息表';
ALTER TABLE `project` add index idx_1(name);
