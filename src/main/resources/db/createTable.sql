-- 创建 bookstore 需要的表

-- 创建 book 表
DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
    `book_id` bigint(20) AUTO_INCREMENT NOT NULL COMMENT '图书编号',
    `book_name` varchar(64) NOT NULL COMMENT '书名',
    `sub_title` varchar(128) NOT NULL COMMENT '子标题',
    `author` varchar(32) NOT NULL COMMENT '作者',
    `cover` varchar(255) NOT NULL COMMENT '封面图片URL',
    `description` text NOT NULL COMMENT '图书详情',
    `category_id` bigint(20) NOT NULL COMMENT '分类编号',
    `evaluation_score` float NOT NULL COMMENT '图书评分',
    `evaluation_quantity` int NOT NULL COMMENT '评分数量',
    PRIMARY KEY (`book_id`)
)ENGINE=InnoDB, DEFAULT CHARSET utf8;

-- member 会员表
DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
    `member_id`  bigint(20) AUTO_INCREMENT NOT NULL COMMENT '会员编号',
    `username` varchar(16) NOT NULL COMMENT '用户名',
    `password` varchar(64) NOT NULL COMMENT '密码',
    `salt` int(4) NOT NULL COMMENT '盐值',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `nickname` varchar(16) NOT NULL COMMENT '昵称',
    PRIMARY KEY (`member_id`)
)ENGINE=InnoDB, DEFAULT CHARSET utf8;

-- evaluation 评论表
DROP TABLE IF EXISTS `evaluation`;

CREATE TABLE `evaluation` (
    `evaluation_id`  bigint(20) AUTO_INCREMENT NOT NULL COMMENT '评价编号',
    `content` varchar(255) NOT NULL COMMENT '短评内容',
    `score` int(255) NOT NULL COMMENT '评分-5分制',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `member_id`  bigint(20) NOT NULL COMMENT '会员编号',
    `book_id` bigint(20) NOT NULL COMMENT '图书编号',
    `enjoy` int(255) NOT NULL DEFAULT 0 COMMENT '点赞数量',
    `state` varchar(16) NOT NULL DEFAULT 'enable' COMMENT '审核状态 enable启用，disable禁用',
    `disable_reason` varchar(255) COMMENT '禁用理由',
    `disable_time` datetime COMMENT '禁用时间',
    PRIMARY KEY (`evaluation_id`)
)ENGINE=InnoDB, DEFAULT CHARSET utf8;

-- member 图书类别表
DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
    `category_id`  bigint(20) AUTO_INCREMENT NOT NULL COMMENT '分类编号',
    `category_name` varchar(32) NOT NULL COMMENT '图书分类',
    PRIMARY KEY (`category_id`)
)ENGINE=InnoDB, DEFAULT CHARSET utf8;


-- read_state 阅读状态表
DROP TABLE IF EXISTS `member_read_state`;

CREATE TABLE `member_read_state` (
    `rs_id`  bigint(20) AUTO_INCREMENT NOT NULL COMMENT '阅读状态id',
    `book_id` bigint(20) NOT NULL COMMENT '图书编号',
    `member_id` bigint(20) NOT NULL COMMENT '会员编号',
    `read_state` int(20) NOT NULL COMMENT '阅读状态 1-想看 2-看过',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`rs_id`)
)ENGINE=InnoDB, DEFAULT CHARSET utf8;