-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2022-02-07 01:37:01
-- 服务器版本： 5.7.34-log
-- PHP 版本： 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `dldlol_bot`
--

-- --------------------------------------------------------

--
-- 表的结构 `batdata`
--

CREATE TABLE `batdata` (
  `id` bigint(255) NOT NULL,
  `hsname` varchar(255) DEFAULT NULL,
  `hshp` bigint(255) DEFAULT NULL,
  `hsmp` bigint(255) DEFAULT NULL,
  `p1` varchar(255) DEFAULT NULL,
  `p2` varchar(255) DEFAULT NULL,
  `p3` varchar(255) DEFAULT NULL,
  `p4` varchar(255) DEFAULT NULL,
  `p5` varchar(255) DEFAULT NULL,
  `p6` varchar(255) DEFAULT NULL,
  `p7` varchar(255) DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `lasttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `ztstartdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `hjbatdata`
--

CREATE TABLE `hjbatdata` (
  `id` int(255) DEFAULT NULL,
  `batid` bigint(255) DEFAULT NULL,
  `qq` bigint(255) DEFAULT NULL,
  `ap` int(255) DEFAULT NULL COMMENT '0为玩家1为魂兽',
  `hjname` varchar(255) DEFAULT NULL,
  `startdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `hunhuan`
--

CREATE TABLE `hunhuan` (
  `qq` bigint(255) DEFAULT NULL,
  `hhnum` int(255) DEFAULT NULL,
  `hh1` varchar(255) DEFAULT NULL,
  `hh2` varchar(255) DEFAULT NULL,
  `hh3` varchar(255) DEFAULT NULL,
  `hh4` varchar(255) DEFAULT NULL,
  `hh5` varchar(255) DEFAULT NULL,
  `hh6` varchar(255) DEFAULT NULL,
  `hh7` varchar(255) DEFAULT NULL,
  `hh8` varchar(255) DEFAULT NULL,
  `hh9` varchar(255) DEFAULT NULL,
  `hh10` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `hunhuan`
--

INSERT INTO `hunhuan` (`qq`, `hhnum`, `hh1`, `hh2`, `hh3`, `hh4`, `hh5`, `hh6`, `hh7`, `hh8`, `hh9`, `hh10`) VALUES
(434658198, 1, '打击@200', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2301865114, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2733944636, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2331292718, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `hunji`
--

CREATE TABLE `hunji` (
  `hjname` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `around` int(1) DEFAULT NULL COMMENT '0=敌全体\r\n1=己方单体\r\n2=己方全体',
  `hjmp` int(255) DEFAULT NULL,
  `hjpr` int(255) DEFAULT NULL,
  `hjhr` int(255) DEFAULT NULL,
  `hjfujia` varchar(255) DEFAULT NULL,
  `hjzsfujia` varchar(255) DEFAULT NULL COMMENT '魂技自身附加',
  `hjcd` int(255) DEFAULT NULL,
  `hjtyp` int(1) DEFAULT NULL COMMENT '0=主动1=被动',
  `hjsffujia` int(1) DEFAULT NULL COMMENT '0=不附加1=附加',
  `hjfjfw` int(1) DEFAULT NULL COMMENT '0=附加敌方1=附加自己'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `hunji`
--

INSERT INTO `hunji` (`hjname`, `info`, `around`, `hjmp`, `hjpr`, `hjhr`, `hjfujia`, `hjzsfujia`, `hjcd`, `hjtyp`, `hjsffujia`, `hjfjfw`) VALUES
('撞击', '直接撞击对方', 0, 20, 105, 70, NULL, NULL, 5, 0, 0, 0),
('打击', '冲向对方进行打击', 0, 30, 106, 75, '', NULL, 5, 0, 0, 0),
('狂化', '为自身附加状态【狂化】', 1, 40, 100, 99, NULL, '狂化', 120, 0, 1, 1);

-- --------------------------------------------------------

--
-- 表的结构 `hunshou`
--

CREATE TABLE `hunshou` (
  `hsname` varchar(255) DEFAULT NULL,
  `pow` varchar(255) DEFAULT NULL,
  `dodge` varchar(255) DEFAULT NULL,
  `ct` varchar(255) DEFAULT NULL,
  `ctp` varchar(255) DEFAULT NULL,
  `hp` varchar(255) DEFAULT NULL,
  `de` varchar(255) DEFAULT NULL,
  `mp` varchar(255) DEFAULT NULL,
  `speed` bigint(20) DEFAULT NULL,
  `map` varchar(255) DEFAULT NULL,
  `exp` varchar(255) DEFAULT NULL,
  `hj` varchar(255) DEFAULT NULL,
  `hjsf` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `hunshou`
--

INSERT INTO `hunshou` (`hsname`, `pow`, `dodge`, `ct`, `ctp`, `hp`, `de`, `mp`, `speed`, `map`, `exp`, `hj`, `hjsf`) VALUES
('史莱姆', '25', '10', '45', '130', '1600', '200', '100', 10, '圣魂村', '500', '撞击@70$70|打击@30$30', 1),
('哥布林', '20', '10', '30', '150', '300', '20', '300', 20, '圣魂村', '700', '', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `map`
--

CREATE TABLE `map` (
  `map` varchar(255) DEFAULT NULL,
  `t` varchar(255) DEFAULT NULL,
  `d` varchar(255) DEFAULT NULL,
  `l` varchar(255) DEFAULT NULL,
  `r` varchar(255) DEFAULT NULL,
  `tp` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `map`
--

INSERT INTO `map` (`map`, `t`, `d`, `l`, `r`, `tp`) VALUES
('圣魂村', '天斗帝国主城', '西尔维斯', '落日森林', '悬天崖', 1),
('天斗帝国主城', '派蒙学院东大门', '圣魂村', '金属商城', '史莱克学院', 1),
('西尔维斯', '圣魂村', '无', '猎魂森林', '无', 0),
('落日森林', '无', '猎魂森林', '冰封森林', '圣魂村', 0),
('悬天崖', '无', '无', '圣魂村', '昊天宗', 0),
('新手村', '无', '圣魂村', '无', '无', 0);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `qq` bigint(255) NOT NULL,
  `name` varchar(18) NOT NULL,
  `sex` varchar(3) NOT NULL DEFAULT '',
  `wuhun` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `lv` int(3) DEFAULT NULL,
  `pow` bigint(255) DEFAULT NULL,
  `dodge` int(255) DEFAULT NULL,
  `ct` int(255) DEFAULT NULL,
  `ctp` int(255) DEFAULT NULL,
  `cdadd` int(255) DEFAULT NULL,
  `pr` bigint(255) DEFAULT NULL,
  `de` bigint(255) DEFAULT NULL,
  `mp` bigint(255) DEFAULT NULL,
  `nowmp` bigint(255) DEFAULT NULL,
  `speed` bigint(255) DEFAULT NULL,
  `sp` bigint(255) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `exp` bigint(255) DEFAULT NULL,
  `con` int(255) DEFAULT NULL,
  `hp` bigint(255) DEFAULT NULL,
  `nowmap` varchar(255) DEFAULT NULL,
  `battle` int(255) DEFAULT NULL,
  `batid` bigint(255) DEFAULT NULL,
  `nowhp` bigint(255) DEFAULT NULL,
  `bubat` int(255) DEFAULT NULL COMMENT '是否允许战斗',
  `fjzt` varchar(255) DEFAULT NULL COMMENT '附加状态',
  `ztstartdete` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`qq`, `name`, `sex`, `wuhun`, `category`, `lv`, `pow`, `dodge`, `ct`, `ctp`, `cdadd`, `pr`, `de`, `mp`, `nowmp`, `speed`, `sp`, `des`, `exp`, `con`, `hp`, `nowmap`, `battle`, `batid`, `nowhp`, `bubat`, `fjzt`, `ztstartdete`) VALUES
(434658198, '夜夜', '男', '镰刀', '敏攻系', 19, 283, 15, 30, 110, 1, 257, 220, 239, 239, 5, 65, '器武魂', 277, 298, 602, '圣魂村', 0, 666276, 602, 0, '', '2022-02-05 23:44:15'),
(2301865114, '小竹笋', '男', '独狼', '敏攻系', 2, 7, 15, 30, 110, 1, 7, 12, 34, 34, 5, 55, '兽武魂', 0, 300, 326, '新手村', NULL, NULL, 326, NULL, NULL, NULL),
(2331292718, '小夏', '女', '独狼', '敏攻系', 8, 45, 15, 30, 110, 1, 37, 12, 28, 28, 5, 41, '兽武魂', 0, 300, 295, '新手村', NULL, NULL, 295, NULL, NULL, NULL),
(2733944636, '小舞', '女', '萝卜', '食物系', 9, 41, 10, 20, 120, 1, 14, 13, 18, 18, 5, 59, '兽武魂', 0, 300, 382, '新手村', NULL, NULL, 382, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `wuhun`
--

CREATE TABLE `wuhun` (
  `name` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `pow` int(255) NOT NULL,
  `dodge` int(255) NOT NULL,
  `ct` int(255) NOT NULL,
  `ctp` int(255) NOT NULL,
  `cdadd` int(255) NOT NULL,
  `pr` int(255) NOT NULL,
  `de` int(255) NOT NULL,
  `mp` int(255) NOT NULL,
  `speed` int(255) NOT NULL,
  `sp` int(255) NOT NULL,
  `des` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `wuhun`
--

INSERT INTO `wuhun` (`name`, `category`, `pow`, `dodge`, `ct`, `ctp`, `cdadd`, `pr`, `de`, `mp`, `speed`, `sp`, `des`) VALUES
('独狼', '敏攻系', 5, 15, 30, 110, 1, 5, 5, 5, 5, 5, '兽武魂'),
('萝卜', '食物系', 5, 10, 20, 120, 1, 5, 5, 5, 5, 5, '兽武魂'),
('镰刀', '敏攻系', 5, 15, 30, 110, 1, 5, 5, 5, 5, 5, '器武魂');

-- --------------------------------------------------------

--
-- 表的结构 `zhuangtai`
--

CREATE TABLE `zhuangtai` (
  `ztname` varchar(255) DEFAULT NULL,
  `bubat` int(255) DEFAULT NULL,
  `buuphp` int(255) DEFAULT NULL,
  `pr` bigint(255) DEFAULT NULL,
  `pow` bigint(255) DEFAULT NULL,
  `ct` bigint(255) DEFAULT NULL,
  `ctp` bigint(255) DEFAULT NULL,
  `speed` bigint(255) DEFAULT NULL,
  `de` bigint(255) DEFAULT NULL,
  `diehp` bigint(255) DEFAULT NULL,
  `cd` int(255) NOT NULL,
  `info` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `zhuangtai`
--

INSERT INTO `zhuangtai` (`ztname`, `bubat`, `buuphp`, `pr`, `pow`, `ct`, `ctp`, `speed`, `de`, `diehp`, `cd`, `info`) VALUES
('狂化', 0, 0, 105, 105, 5, 10, 105, 110, NULL, 60, '使自身进入狂化状态60秒'),
('禁锢', 1, NULL, 100, 100, 0, 0, 0, 0, NULL, 60, '禁锢60秒');

--
-- 转储表的索引
--

--
-- 表的索引 `batdata`
--
ALTER TABLE `batdata`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `hjbatdata`
--
ALTER TABLE `hjbatdata`
  ADD UNIQUE KEY `id` (`id`);

--
-- 表的索引 `hunhuan`
--
ALTER TABLE `hunhuan`
  ADD UNIQUE KEY `qq` (`qq`);

--
-- 表的索引 `hunji`
--
ALTER TABLE `hunji`
  ADD UNIQUE KEY `hjname` (`hjname`);

--
-- 表的索引 `hunshou`
--
ALTER TABLE `hunshou`
  ADD UNIQUE KEY `hsname` (`hsname`);

--
-- 表的索引 `map`
--
ALTER TABLE `map`
  ADD UNIQUE KEY `map` (`map`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD UNIQUE KEY `qq` (`qq`);

--
-- 表的索引 `wuhun`
--
ALTER TABLE `wuhun`
  ADD UNIQUE KEY `name` (`name`);

--
-- 表的索引 `zhuangtai`
--
ALTER TABLE `zhuangtai`
  ADD UNIQUE KEY `ztname` (`ztname`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
