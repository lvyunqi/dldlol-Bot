-- MySQL dump 10.13  Distrib 5.7.37, for Linux (x86_64)
--
-- Host: localhost    Database: qimenbot
-- ------------------------------------------------------
-- Server version	5.7.37-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `command`
--

DROP TABLE IF EXISTS `command`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `command` (
  `id` int(255) NOT NULL,
  `admin` json DEFAULT NULL COMMENT '管理员',
  `group_list` json DEFAULT NULL COMMENT '群授权',
  `exp_up` int(255) DEFAULT NULL COMMENT '经验增幅',
  `start_map` varchar(255) DEFAULT NULL COMMENT '初始地图',
  `con` int(255) DEFAULT NULL COMMENT '体力上限',
  `currency` json DEFAULT NULL COMMENT '币种',
  `init_money` json DEFAULT NULL COMMENT '初始货币数量',
  `init_backpack` json DEFAULT NULL COMMENT '初始背包（初始赠送，默认空）',
  `sgin_in` json DEFAULT NULL COMMENT '签到奖励',
  `twinsp` int(255) DEFAULT NULL COMMENT '双生武魂概率',
  `nofight` int(255) DEFAULT NULL COMMENT '全体禁止战斗，0允许1禁止',
  `revive_currency` varchar(255) DEFAULT NULL COMMENT '复活扣除的币种',
  `question_player` int(255) DEFAULT NULL COMMENT '答题成功人数上限',
  `last_running_time` mediumtext COMMENT '上一次运行时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `command`
--

LOCK TABLES `command` WRITE;
/*!40000 ALTER TABLE `command` DISABLE KEYS */;
INSERT INTO `command` VALUES (1,'{\"434658198\": \"123456\"}','{\"0\": \"null\", \"410279299\": \"null\", \"547507321\": \"null\", \"557647235\": \"null\", \"746339543\": \"null\", \"853016654\": \"null\", \"1060810432\": \"null\"}',2,'新手村',250,'{\"点券\": \"null\", \"联邦币\": \"null\"}','{\"点券\": 0, \"联邦币\": 1000}','{\"mecha\": {}, \"weapons\": {}, \"material\": {}, \"consumables\": {}, \"battle_armor\": {}}','{\"1\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {}, \"consumables\": {}}, \"2\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": 1}, \"consumables\": {}}, \"3\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": 1}, \"consumables\": {}}, \"4\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": 1}, \"consumables\": {}}, \"5\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": 1}, \"consumables\": {}}, \"6\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": 1}, \"consumables\": {}}, \"7\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": 1}, \"consumables\": {}}}',70,0,'联邦币',5,'1671458904211'),(2,'{\"2367645814\": \"123456\"}','{\"0\": \"null\", \"557647235\": \"null\", \"746339543\": \"null\", \"853016654\": \"null\", \"1060810432\": \"null\"}',2,'新手村',250,'{\"点券\": \"null\", \"联邦币\": \"null\"}','{\"点券\": 0, \"联邦币\": 1000}','{\"mecha\": {}, \"weapons\": {}, \"material\": {}, \"consumables\": {}, \"battle_armor\": {}}','{\"1\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {}, \"consumables\": {}}, \"2\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": {\"lv\": 0, \"num\": 1}}, \"consumables\": {}}, \"3\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": {\"lv\": 0, \"num\": 1}}, \"consumables\": {}}, \"4\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": {\"lv\": 0, \"num\": 1}}, \"consumables\": {}}, \"5\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": {\"lv\": 0, \"num\": 1}}, \"consumables\": {}}, \"6\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": {\"lv\": 0, \"num\": 1}}, \"consumables\": {}}, \"7\": {\"currency\": {\"点券\": 300, \"联邦币\": 300}, \"material\": {\"沉银\": {\"lv\": 0, \"num\": 1}}, \"consumables\": {}}}',70,0,'联邦币',5,NULL);
/*!40000 ALTER TABLE `command` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fightdata`
--

DROP TABLE IF EXISTS `fightdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fightdata` (
  `id` bigint(255) NOT NULL,
  `hsname` varchar(255) DEFAULT NULL,
  `data` json DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  `lastdate` datetime DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  `bubat` int(11) DEFAULT NULL,
  `diehp` json DEFAULT NULL,
  `status` json DEFAULT NULL,
  `hsskilldata` json DEFAULT NULL COMMENT '魂兽释放魂技数据',
  `userskilldata` json DEFAULT NULL COMMENT '玩家技能释放数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fightdata`
--

LOCK TABLES `fightdata` WRITE;
/*!40000 ALTER TABLE `fightdata` DISABLE KEYS */;
INSERT INTO `fightdata` VALUES (269078,'哥布林','{\"力量\": 20, \"暴伤\": 130, \"生命\": 295, \"速度\": 20, \"闪避\": 10, \"防御\": 200, \"魂力\": 370, \"暴击率\": 45}','2022-07-08 10:49:06','2022-07-08 10:49:26',NULL,NULL,NULL,NULL,'{\"打击\": \"1657248553897\"}',NULL);
/*!40000 ALTER TABLE `fightdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `function`
--

DROP TABLE IF EXISTS `function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `function` (
  `question` json DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `function`
--

LOCK TABLES `function` WRITE;
/*!40000 ALTER TABLE `function` DISABLE KEYS */;
INSERT INTO `function` VALUES ('{\"1\": \"2\"}');
/*!40000 ALTER TABLE `function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `effect_range` int(11) NOT NULL DEFAULT '0' COMMENT '效果范围（己方单体：0，己方全体：1，敌方：2）	',
  `use_scenarios` int(11) NOT NULL DEFAULT '0' COMMENT '使用场景（所有：0，非战斗：1，战斗：2）',
  `item_type` int(11) NOT NULL DEFAULT '0' COMMENT '物品类型（是否可消耗）',
  `ability` varchar(255) NOT NULL COMMENT '能力提升类型',
  `rise` mediumtext COMMENT '提升量',
  `hp_recovery` mediumtext COMMENT '生命恢复',
  `hp_storage` mediumtext COMMENT '生命储备',
  `mp_recovery` mediumtext COMMENT '魂力恢复',
  `mp_storage` mediumtext COMMENT '魂力储存',
  `hit_rate` int(11) NOT NULL DEFAULT '99' COMMENT '命中率（百分比）',
  `tradability` int(11) NOT NULL DEFAULT '0' COMMENT '是否可交易（默认是）',
  `delete_account` int(11) NOT NULL DEFAULT '1' COMMENT '是否可删除账号（默认否）',
  `times_per_day` int(11) NOT NULL DEFAULT '0' COMMENT '每天使用次数（默认无限）',
  `additional_status` varchar(255) DEFAULT NULL COMMENT '附加状态（一个）',
  `self_status` varchar(255) DEFAULT NULL COMMENT '自身附加状态（一个）',
  `tp_location` int(11) NOT NULL DEFAULT '1' COMMENT '是否为定位物品（默认否）',
  `tp_location_tp` int(11) NOT NULL DEFAULT '1' COMMENT '是否为定位传送物品（默认否）',
  `tp_random` int(11) NOT NULL DEFAULT '1' COMMENT '是否随机传送（默认否）',
  `tp` varchar(255) DEFAULT NULL COMMENT '传送地图',
  `goods_list` json DEFAULT NULL COMMENT '内涵物品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1002,'示例',0,0,0,'攻击',NULL,NULL,NULL,NULL,NULL,99,0,1,0,NULL,NULL,1,1,1,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),(10001,'低级生命药水',0,0,0,'经验',NULL,NULL,NULL,NULL,NULL,99,0,1,60,NULL,NULL,1,1,1,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"1000\"}, \"consumables\": {\"低级生命药水\": \"10\"}}, \"probability\": {\"consumables\": {\"低级生命药水\": \"30\"}}}');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hjbatdata`
--

DROP TABLE IF EXISTS `hjbatdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hjbatdata` (
  `id` int(255) DEFAULT NULL,
  `batid` bigint(255) DEFAULT NULL,
  `qq` bigint(255) DEFAULT NULL,
  `ap` int(255) DEFAULT NULL COMMENT '0为玩家1为魂兽',
  `hjname` varchar(255) DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hjbatdata`
--

LOCK TABLES `hjbatdata` WRITE;
/*!40000 ALTER TABLE `hjbatdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `hjbatdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hunhuan`
--

DROP TABLE IF EXISTS `hunhuan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `hh10` varchar(255) DEFAULT NULL,
  UNIQUE KEY `qq` (`qq`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hunhuan`
--

LOCK TABLES `hunhuan` WRITE;
/*!40000 ALTER TABLE `hunhuan` DISABLE KEYS */;
INSERT INTO `hunhuan` VALUES (1,2,'3','4','5','6','7','8','9','10','11','12');
/*!40000 ALTER TABLE `hunhuan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hunshou`
--

DROP TABLE IF EXISTS `hunshou`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hunshou` (
  `hsname` varchar(255) NOT NULL,
  `data` json DEFAULT NULL,
  `age` bigint(20) DEFAULT NULL,
  `map` varchar(255) DEFAULT NULL,
  `exp` bigint(20) DEFAULT NULL,
  `skill` json DEFAULT NULL,
  `drop_items` json DEFAULT NULL COMMENT '掉落物品',
  PRIMARY KEY (`hsname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hunshou`
--

LOCK TABLES `hunshou` WRITE;
/*!40000 ALTER TABLE `hunshou` DISABLE KEYS */;
INSERT INTO `hunshou` VALUES ('12',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('123214',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('21',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('213',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('231',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('2321',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('2342',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('史莱姆','{\"力量\": 1, \"暴伤\": 130, \"生命\": 20, \"速度\": 20, \"闪避\": 10, \"防御\": 1, \"魂力\": 100, \"暴击率\": 45}',10,'圣魂村',10000,NULL,NULL),('哥布林','{\"力量\": 20, \"暴伤\": 130, \"生命\": 300, \"速度\": 20, \"闪避\": 10, \"防御\": 20, \"魂力\": 400, \"暴击率\": 45}',450,'圣魂村',2000,'{\"打击\": {\"ap\": 30, \"rp\": 70}, \"撞击\": {\"ap\": 70, \"rp\": 30}}','{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"哥布林手指\": \"1\"}}, \"probability\": {\"material\": {\"哥布林战旗\": \"1\"}, \"consumables\": {\"低级生命药水\": \"1\"}}}'),('示3',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('示41',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('示例',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('示例1',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('示例2',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}'),('示例4',NULL,NULL,NULL,NULL,NULL,'{\"necessary\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}, \"probability\": {\"currency\": {\"联邦币\": \"100\"}, \"material\": {\"沉银\": \"0|5\", \"材料a\": \"0|1\", \"材料b\": \"2|3\", \"材料c\": \"3|2\"}, \"consumables\": {\"物品a\": \"1\", \"物品b\": \"3\", \"物品c\": \"2\"}}}');
/*!40000 ALTER TABLE `hunshou` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `map`
--

DROP TABLE IF EXISTS `map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `map` (
  `map` varchar(255) NOT NULL,
  `t` varchar(255) DEFAULT NULL,
  `d` varchar(255) DEFAULT NULL,
  `l` varchar(255) DEFAULT NULL,
  `r` varchar(255) DEFAULT NULL,
  `tp` int(255) DEFAULT NULL,
  PRIMARY KEY (`map`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `map`
--

LOCK TABLES `map` WRITE;
/*!40000 ALTER TABLE `map` DISABLE KEYS */;
INSERT INTO `map` VALUES ('一线天',NULL,NULL,'沙忍村南','热沙荒野',0),('七圣渊','史莱克学院内城','史莱克学院',NULL,'圣魂村',0),('万古毒谷','龙幽','断魂峰',NULL,NULL,0),('上古凶地',NULL,NULL,NULL,NULL,0),('世界联盟大会','宝可梦世界','','','',0),('五封结界',NULL,NULL,NULL,'川之国峡谷',0),('传灵塔总部','天斗帝国主城',NULL,NULL,NULL,1),('修罗谷',NULL,NULL,'诺丁河',NULL,1),('冰封森林',NULL,'索托',NULL,'落日森林',1),('判官府',NULL,NULL,'猎魂森林','地府',0),('北笙世界',NULL,NULL,'圣魂村',NULL,0),('十八层地狱',NULL,NULL,'圣魂村',NULL,0),('十阎罗殿',NULL,'地府','圣魂村',NULL,0),('史莱克城',NULL,NULL,NULL,'史莱克学院内城',1),('史莱克学院','史莱克学院内城',NULL,NULL,NULL,0),('史莱克学院内城','海神岛','永恒天空城','永恒古树','七圣渊',1),('唐门',NULL,NULL,NULL,NULL,1),('四灵大陆',NULL,'神坠大陆',NULL,'次元时空裂缝',0),('圣域',NULL,'圣丹城',NULL,NULL,0),('圣天崖',NULL,NULL,'通天洞',NULL,1),('圣魂村','天斗帝国主城','西尔维斯','落日森林','悬天崖',1),('地府','十阎罗殿','十八层地狱','判官府','鬼门关',0),('地狱',NULL,'圣魂村',NULL,NULL,0),('地狱之门',NULL,NULL,NULL,NULL,0),('塔斯草原','索托试练场',NULL,NULL,NULL,1),('大神圈',NULL,NULL,NULL,NULL,0),('大竹峰',NULL,NULL,NULL,NULL,0),('大蛇丸基地',NULL,NULL,NULL,'大蛇丸基地外围',0),('大蛇丸基地外围',NULL,'鸦之森','大蛇丸基地',NULL,0),('天地桥',NULL,'雨隐之村',NULL,'鸦之森',0),('天宫','天界','天庭','兜率宫','月宫',0),('天宫市',NULL,NULL,'二次元聚集地',NULL,0),('天庭','天宫','凌霄殿',NULL,NULL,0),('天斗帝国主城','星斗边缘','圣魂村',NULL,NULL,1),('天青湖','星斗边缘',NULL,NULL,NULL,1),('天音门',NULL,'炼狱山谷',NULL,NULL,0),('奈何桥',NULL,NULL,NULL,'判官府',0),('妙木山后山','妙木山池塘',NULL,NULL,NULL,0),('妙木山池塘','木叶村西边','妙木山后山',NULL,NULL,0),('宇宙','凌霄殿','混沌',NULL,NULL,0),('定制世界',NULL,'圣魂村',NULL,NULL,0),('宝可梦世界','世界联盟大会','动漫圣域','真新镇','石英联盟',0),('封印之地','雪之领域','龙神领域','神界','圣魂村',0),('川之国峡谷','热沙荒野',NULL,'五封结界','火之国森林',0),('巴拉克','西尔维斯','索托试练场',NULL,'炼狱山谷',1),('幻·神殿','幻之湖','幻之阁','幻神秘境',NULL,0),('幻之尽头',NULL,'幻海',NULL,NULL,0),('幻之桥','幻海',NULL,'幻梦之森',NULL,0),('幻之湖','幻岛',NULL,NULL,NULL,0),('幻之门',NULL,NULL,NULL,NULL,0),('幻之阁',NULL,NULL,NULL,NULL,0),('幻境','幻岛','幻梦之港',NULL,NULL,0),('幻岛','幻之湖','幻境','幻灵部落','幻·神殿',0),('幻梦之森','幻梦之森深处','幻梦之港','梦之桥','幻之桥',0),('幻梦之森深处',NULL,'幻梦之森',NULL,NULL,0),('幻梦之港','幻梦之森',NULL,NULL,NULL,0),('幻海','幻之尽头','幻之桥',NULL,NULL,0),('幻灵部落',NULL,NULL,NULL,NULL,0),('幻神秘境',NULL,NULL,NULL,NULL,0),('幽玄结境',NULL,'苍澜之境','断魂峰',NULL,0),('忍者学校',NULL,'学校走廊',NULL,NULL,0),('悬天崖',NULL,NULL,'圣魂村',NULL,0),('斗魂空间',NULL,NULL,NULL,NULL,1),('断魂峰','万古毒谷',NULL,NULL,NULL,0),('新手村',NULL,'圣魂村',NULL,NULL,0),('无人之境',NULL,NULL,NULL,'圣魂村',0),('无极草原','塔斯草原',NULL,NULL,'通天洞',1),('时空裂缝','圣魂村','天青湖','索托','圣魂村',0),('昊天宗',NULL,'杀戮之都','悬天崖',NULL,1),('星斗中心','生命之湖',NULL,NULL,'星斗边缘',1),('星斗外围','索托','龙兴',NULL,NULL,1),('星斗边缘','龙兴','天青湖','星斗中心',NULL,1),('星罗帝国主城',NULL,'斗魂空间','索托试练场',NULL,1),('星辰宫',NULL,NULL,'圣魂村',NULL,0),('木叶医院',NULL,NULL,NULL,'木叶村西边',0),('木叶悬崖','木叶训练场',NULL,NULL,NULL,0),('木叶村东边',NULL,'澡堂','火之国','一乐拉面馆',0),('木叶村北边','火影办公室','火之国','学校走廊',NULL,0),('木叶村南边','火之国','木叶森林',NULL,NULL,0),('木叶村西边',NULL,'妙木山池塘','木叶医院','火之国',0),('木叶森林','木叶村南边','草波海岸','火之国森林','木叶训练场',0),('木叶训练场','木叶训练场深处','木叶悬崖','木叶森林','死亡森林',0),('木叶训练场深处','长门的据点','木叶训练场',NULL,NULL,0),('机甲废墟',NULL,NULL,NULL,NULL,0),('杀戮之都','昊天宗',NULL,'炼狱山谷',NULL,1),('极深海域',NULL,NULL,'神界委员会',NULL,0),('枯木之森',NULL,NULL,'短册街','火之寺',0),('梦之彼岸',NULL,'梦海',NULL,NULL,0),('梦之桥','梦海',NULL,NULL,'幻梦之森',0),('梦海','梦之彼岸','梦之桥',NULL,NULL,0),('次元时空裂缝',NULL,'天青湖','四灵大陆',NULL,0),('次元裂缝',NULL,NULL,NULL,NULL,0),('武魂殿主城',NULL,NULL,'龙兴','索托试练场',1),('死亡森林',NULL,NULL,'木叶训练场','短册街',0),('水帘洞','花果山',NULL,NULL,NULL,0),('永恒古树','圣魂村','史莱克学院内城',NULL,NULL,1),('永恒天空城','圣魂村',NULL,'史莱克学院内城',NULL,1),('永恒零度',NULL,NULL,'圣魂村',NULL,0),('沙忍村北',NULL,'沙忍村南',NULL,NULL,0),('沙忍村南','沙忍村北',NULL,NULL,'一线天',0),('波之国','草波海岸','波之国森林',NULL,NULL,0),('波之国森林','波之国',NULL,NULL,NULL,0),('派蒙学院东大门','派蒙学院东长廊','天斗帝国主城',NULL,NULL,0),('派蒙学院东花园','派蒙学院内院','派蒙学院东长廊','派蒙学院初级试炼场','派蒙学院高级试炼场',0),('派蒙学院东长廊','派蒙学院东花园','派蒙学院东大门','派蒙学院小学部','派蒙学院中学部',0),('派蒙学院中学部',NULL,NULL,'派蒙学院东长廊','派蒙学院商店',0),('派蒙学院内院',NULL,'派蒙学院东花园',NULL,NULL,0),('派蒙学院初级试炼场',NULL,NULL,NULL,'派蒙学院东花园',0),('派蒙学院商店',NULL,NULL,'派蒙学院中学部',NULL,0),('派蒙学院小学部',NULL,NULL,NULL,'派蒙学院东长廊',0),('派蒙学院高级试炼场',NULL,NULL,'派蒙学院东花园',NULL,0),('海湾酒馆',NULL,'圣魂村',NULL,NULL,0),('海神岛',NULL,NULL,'百花谷',NULL,1),('海神阁',NULL,'永恒古树','永恒天空城',NULL,0),('深渊通道',NULL,NULL,NULL,'神秘空间',0),('混乱世界',NULL,'落日森林',NULL,NULL,0),('混沌','宇宙',NULL,NULL,NULL,0),('澡堂',NULL,NULL,'木叶村东边',NULL,0),('火之国森林','鸦之森',NULL,'川之国峡谷','木叶森林',0),('火之寺',NULL,NULL,'枯木之森',NULL,0),('炼狱山谷','天音门','诺丁河','巴拉克','杀戮之都',1),('热沙荒野','风之国沙漠','川之国峡谷','一线天',NULL,0),('猎魂森林','落日森林',NULL,'索托','西尔维斯',1),('王之领域','圣魂村','雪之仙境','黄金十二宫','蛮荒',0),('琉璃岛',NULL,'圣魂村',NULL,'落日森林',0),('生命之湖',NULL,'星斗中心',NULL,NULL,1),('百花谷','通天洞','迷罗湖',NULL,'海神岛',1),('真新镇','','宝可梦世界','','',0),('短册街','终末之谷下方',NULL,'死亡森林','枯木之森',0),('石英联盟','','宝可梦世界','','',0),('破灭废墟','海神岛','星斗边缘','圣魂村','炼狱山谷',1),('神坠大陆','四灵大陆','二次元聚集地',NULL,NULL,0),('神界','神秘空间','神界委员会',NULL,NULL,0),('神界委员会',NULL,NULL,NULL,NULL,0),('神禁之地',NULL,NULL,'神界',NULL,0),('神秘空间',NULL,'神界','龙神领域','雪之领域',0),('索托','冰封森林','星斗外围',NULL,'猎魂森林',1),('索托试练场','巴拉克','塔斯草原','武魂殿主城',NULL,1),('终末之谷',NULL,'终末之谷下方',NULL,NULL,0),('终末之谷下方','终末之谷','短册街',NULL,NULL,0),('经验空间','雪之领域','圣魂村','杀戮之都','落日森林',0),('缥缈圣域',NULL,NULL,'圣魂村',NULL,0),('苍澜之境','幽玄结境',NULL,NULL,NULL,0),('草波海岸','木叶森林','波之国',NULL,NULL,0),('落日森林',NULL,NULL,NULL,'圣魂村',0),('蛮荒','王之领域','黄金十二宫','圣魂村','雪之仙境',0),('西尔维斯','圣魂村',NULL,NULL,NULL,0),('试炼圣地','圣魂村',NULL,NULL,NULL,0),('诺丁河','炼狱山谷',NULL,NULL,'修罗谷',1),('转生世界',NULL,NULL,NULL,NULL,0),('迷罗湖','百花谷',NULL,NULL,NULL,1),('通天洞',NULL,'百花谷',NULL,'圣天崖',1),('通灵世界',NULL,'圣魂村',NULL,NULL,0),('邪神禁地',NULL,'星斗中心',NULL,'龙兴',0),('金属商城',NULL,NULL,NULL,'天斗帝国主城',1),('长门的据点',NULL,'木叶训练场深处',NULL,NULL,0),('雨隐之村','天地桥',NULL,NULL,NULL,0),('雪之仙境','蛮荒','王之领域','黄金十二宫','圣魂村',0),('雪之领域',NULL,NULL,'神秘空间','极深海域',0),('青云门',NULL,NULL,NULL,NULL,0),('风之国沙漠',NULL,'热沙荒野',NULL,NULL,0),('飘渺仙境',NULL,NULL,'炎帝药园','九重天',0),('鬼王宗',NULL,NULL,NULL,NULL,0),('鬼门关',NULL,'奈何桥',NULL,'地府',0),('魂兽位面',NULL,'圣魂村',NULL,NULL,0),('鸦之森','大蛇丸基地外围','火之国森林','天地桥',NULL,0),('黄金十二宫','蛮荒','王之领域','圣魂村','雪之仙境',0),('龙兴','星斗外围','星斗边缘','星斗中心','武魂殿主城',1),('龙幽','龙族禁地','万古毒谷','神灵秘境',NULL,0),('龙神领域',NULL,NULL,NULL,'神秘空间',0);
/*!40000 ALTER TABLE `map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `npc`
--

DROP TABLE IF EXISTS `npc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `npc` (
  `name` varchar(255) NOT NULL,
  `map` varchar(255) DEFAULT NULL,
  `task` json DEFAULT NULL COMMENT '任务',
  `task_talk` json DEFAULT NULL COMMENT '任务条件对话',
  `daily_talk` json DEFAULT NULL COMMENT '日常对话',
  `shop` json DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `npc`
--

LOCK TABLES `npc` WRITE;
/*!40000 ALTER TABLE `npc` DISABLE KEYS */;
INSERT INTO `npc` VALUES ('圣魂长老','圣魂村',NULL,NULL,NULL,'{\"weapons\": {}, \"material\": {\"沉银\": \"联邦币|5\"}, \"consumables\": {\"低级生命药水\": \"联邦币|1\"}}');
/*!40000 ALTER TABLE `npc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other`
--

DROP TABLE IF EXISTS `other`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other` (
  `baidu_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other`
--

LOCK TABLES `other` WRITE;
/*!40000 ALTER TABLE `other` DISABLE KEYS */;
/*!40000 ALTER TABLE `other` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(255) NOT NULL,
  `question` text,
  `answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'小明的大哥叫大毛，小明的二哥叫二毛，那小明叫什么？','小明'),(2,'十棵树排五行，每行要有四棵树，请问该摆个什么形状呢？（3个字）','五角星'),(3,'蜗牛、鲇鱼、蚯蚓哪种动物不是软体动物？','鲶鱼'),(4,'666×20×52.5÷2×12=几？','4171824'),(5,'朱庇特、丘比特、坦丁，哪一个是罗马神话中的神？','朱庇特'),(6,'号\"六一居士\"的是？','欧阳修'),(7,'绿茶、福茶、乌龙茶，哪一类茶是半发酵茶？','乌龙茶'),(8,'毛泽东、孙中山、周恩来，谁是\"中山装\"的创始人？','孙中山'),(9,'\"红娘\"是哪部作品中的人物？','西厢记'),(10,'《雪绒花》是哪部影片的插曲？','音乐之声'),(11,'太阳金字塔坐落在哪里？','墨西哥'),(12,'世界上最深的湖在哪个国家？','俄罗斯'),(13,'阿姆斯特朗是乘哪个飞船成功登月的？','阿波罗11号'),(14,'最早假牙牙床是什么材料制成的？','黄金'),(15,'人体最大的解毒器官是？','肝脏'),(16,'我国最大的诗歌集是？','全唐诗'),(17,'世界上的哪个国家有\"黄金海岸\"之称？','加纳'),(18,'法国干邑白兰地酒的\"X.O\"标志表示什么意思？','贮藏期'),(19,'《兰亭序》具有极高的艺术价值，那么它的作者是','王羲之'),(20,'我国高校收费完全并轨开始于哪一年？','2000'),(21,'从何时起，对最高统治者称\"王\"？','商朝'),(22,'歌剧诞生于哪个国家？','意大利'),(23,'地动仪的制造人是谁？','张衡'),(24,'中国第一个奴隶制度的王朝是哪个朝代？','夏朝'),(25,'茅盾小说的代表作是？','子夜'),(26,'三国时建造\"铜雀台\"的是谁?','曹操'),(27,'南宋都城临安.是现今何处?（地名）','杭州'),(28,'我国著名的赵州桥建于哪个朝代？','隋朝'),(29,'三国时的马超是哪个民族的？','羌族'),(30,'唐太宗李世民的年号是什么？','贞观'),(31,'古代著名的抒情诗《离骚》的作者是谁？','屈原'),(32,'“金屋藏娇”说的是哪一位皇帝？','刘彻'),(33,'古代六艺，\"礼、乐、射、御、书、数\"中的\"御\"是指','驾车'),(34,'“名不正则言不顺”是哪家的思想？','儒家'),(35,'杜甫诗句“故人入我梦，明我长相忆。君今在罗网，何以有羽翼？”中“故人”是指谁?（诗人名字）','李白'),(36,'“飞鸟尽，良弓藏，狡兔死，走狗烹”是谁最先说的','范蠡'),(37,'“鹅鹅鹅，曲项向天歌”这句诗是谁写的？','骆宾王'),(38,'“十年生死两茫茫。不思量，自难忘。千里孤坟，无处话凄凉。”出自苏轼的哪首词？','江城子'),(39,'古诗“应怜屐齿印苍苔”的下句','小扣柴扉久不开'),(40,'古诗名句“举杯遨明月”的下句','对影成三人'),(41,'古诗名句“大漠孤烟直”的下句','长河落日圆'),(42,'古诗名句“凭君莫话封侯事”的下句','一将功成万骨枯'),(43,'古诗名句“郎骑竹马来”的下句','绕床弄青梅'),(44,'古诗名句“人生得意须尽欢”的下句','莫使金樽空对月'),(45,'《洛神赋图》是谁的作品？','顾恺之'),(46,'作家老舍的原名叫什么？','舒庆春'),(47,'有了肯德基','生活好滋味'),(48,'黄山在哪个省？','安徽省'),(49,'云贵高原上最大的湖泊是哪个湖泊？','滇池'),(50,'请在诗句“明月___间照，清泉石上流”中填空。','松'),(51,'第一包方便面诞生在哪个国家','日本'),(52,'“除非你能在床上赚钱，否则就不要赖在床上”是谁说的？','苍井空'),(53,'希特勒是哪国的人？','奥地利'),(54,'人民英雄纪念碑上的雕塑作品《五四运动》是浮雕还是圆雕？','浮雕'),(55,'徐克电影笑傲江湖中的东方不败是由哪位女演员扮演的？','林青霞'),(56,'被称为“天可汗”的是谁？','唐太宗'),(57,'19世纪，哪个国家成为欧洲的美术中心？','法国'),(58,'天然气的主要成分是什么？','甲烷'),(59,'女演员马伊琍的丈夫叫什么？','文章'),(60,'世界上第一台计算机诞生于哪一年？','1946'),(61,'十面埋伏 所用的乐器是什么？','琵琶'),(62,'女演员马伊琍的女儿叫什么？','文爱马'),(63,'“不为五斗米折腰”的古代人物是谁','陶渊明'),(64,'《北京遇上西雅图》中男主角的饰演者是谁？','吴秀波'),(65,'热播电视剧《步步惊心》的作者是谁？','桐华'),(66,'热播电视剧《步步惊心》的第二部叫什么？','步步惊情'),(67,'笄礼是古代女子几岁成年的插笄仪式？','15'),(68,'张国荣曾向哪位女演员求婚被拒绝？','毛舜筠'),(69,'荣迷们一般称张国荣为什么？','哥哥'),(70,'张国荣的同性恋人叫什么？','唐鹤德'),(71,'张国荣去世前拍的最后一部影片是？','异度空间'),(72,'俄罗斯象征友谊的颜色是什么颜色？','蓝色'),(73,'“飞人”乔丹曾多少次获得NBA称号？（回答数字）','6'),(74,'战国时期,总结前人医疗经验归纳出的四诊法,是哪位名医?','扁鹊'),(75,'中国第一位女诗人是谁？','蔡琰'),(76,'中国第一位女词人是谁？','李清照'),(77,'中国第一部大百科全书叫什么名字？','永乐大典'),(78,'中国第一部诗歌总集叫什么名字？','诗经'),(79,'中国第一部文选是什么？','昭明文选'),(80,'中国第一部神话集叫什么名字？','山海经'),(81,'中国第一部文言志人小说集叫什么名字？','世说新语'),(82,'中国第一部语录体著作叫什么名字？','论语'),(83,'中国第一部纪传体通史叫什么名字？','史记'),(84,'第一部编年体史书叫什么名字？','春秋'),(85,'台湾歌手齐秦的《大约在冬季》是写给哪位香港女演员？','王祖贤'),(86,'湖南卫视节目《我是歌手》复活赛中哪位歌手复活？','杨宗纬'),(87,'人类的主要造血器官是什么？','骨髓'),(88,'“似曾相识燕归来”上一句？','无可奈何花落去'),(89,'世界天气的推动力是？','太阳'),(90,'“问世间，情为何物，直教人生死相许”出自哪位词人？','元好问'),(91,'《洛神赋》是谁的作品？','曹植'),(92,'“金屋藏娇”的故事与哪位皇帝有关？','汉武帝'),(93,'在我国内地驾驶车辆，必须遵守： 1 左侧通行的原则 2 右侧通行的原则','2'),(94,'围棋棋盘共有几个交叉点？','361'),(95,'人体最大的细胞是：1:卵细胞 2 脑细胞 3 淋巴细胞','1'),(96,'歌手黄贯中的老婆叫什么？','朱茵'),(97,'峨嵋山、华山、泰山、嵩山，哪座山是中国佛教四大名山之一？','峨嵋山'),(98,'人体最坚硬的部分是？','牙齿'),(99,'“诗鬼”是谁？','李贺'),(100,'戛纳电影节在哪国举办？','法国'),(101,'被称为\"国际会议之都\"的城市是？','日内瓦'),(102,'我国最大的淡水湖是？','鄱阳湖'),(103,'扎伊尔、刚果、南非，哪个国家是钻石的最大产出国？','扎伊尔'),(104,'好莱坞位于美国什么州？','加利福尼亚州'),(105,'“婴宁”是谁笔下的人物？','蒲松龄'),(106,'巴金的作品《家》《春》《秋》合称什么？','激流三部曲'),(107,'香港女演员袁咏仪的儿子叫什么？','张慕童'),(108,'股票市场中指数大幅上升又称？','牛市'),(109,'《义勇军进行曲》是哪部电影的主题歌？','风云儿女'),(110,'水上芭蕾又称？','花样游泳'),(111,'《一帘幽梦》的女主角叫什么？','紫菱'),(112,'UNESCO是什么国际组织的简称？','联合国科教文组织'),(113,'发射第一颗人造卫星的国家是？','前苏联'),(114,'“画圣”是？','吴道子'),(115,'企鹅是否可以生活在赤道附近？','可以'),(116,'乒乓球比赛中，甲方进攻时未将球打在台内，而是直接打到了台外乙方的球拍上，请问该球应判谁得分？','乙方'),(117,'雨果、莫泊桑、巴尔扎克、海涅，哪个作家不是法国人？','海涅'),(118,'中国古代名医华佗为谁所杀?','曹操'),(119,'自称\"白蒙古\"的民族是哪一民族？','土族'),(120,'江苏卫视节目《非诚勿扰》中女嘉宾出场时放的什么音乐？','Girlfriend'),(121,'老年斑仅出现在人体表面吗？','不是'),(122,'中国民间的\"冬九九\"是从哪一天开始的？','冬至'),(123,'电影《变形金刚》中与擎天柱作对的反派首领叫什么？','威震天'),(124,'马头琴是我国哪一民族的拉弦乐器？','蒙古族'),(125,'《你是人间的四月天》作者是谁？','林徽因'),(126,'油条、松花蛋、豆腐、粉条，经常食用哪种食物容易引起铅中毒？','松花蛋'),(127,'“黑夜给了我黑色的眼睛，我却用它来寻找光明”出自哪位诗人？','顾城'),(128,'2013年著名导演李安凭借哪部电影获得奥斯卡最佳导演奖？','少年派的奇幻漂流'),(129,'电影《无间道》中梁朝伟与刘德华在音响店里试音响用的那首歌？','被遗忘的时光'),(130,'首位在戛纳电影节封后的华人女演员是？','张曼玉'),(131,'系列电影《加勒比海盗》是哪间电影公司的作品？','迪士尼'),(132,'\"人生自古谁无死，留取丹心照汗青\"的作者是？','文天祥'),(133,'中国无声影片的最高峰《神女》是谁的代表作？','阮玲玉'),(134,'给折枝山茶花保鲜应该用以下哪种水，淡盐水、自来水、浓盐水？','淡盐水'),(135,'\"打蛇打七寸\"的七寸是指？','心脏'),(136,'歌手孙燕姿是哪国人？','新加坡'),(137,'《我是歌手》中黄绮珊原名？','黄晓霞'),(138,'南丁格尔奖章是不是国际护士的最高荣誉？','是'),(139,'台湾第一届《超级星光大道》总冠军是谁？','林宥嘉'),(140,'世界上第一条地铁在1863年建于哪里？','伦敦'),(141,'\"变脸\"是哪个剧种的绝活？','川剧'),(142,'人体含水量百分比最高的器官是？','眼球'),(143,'中国签定的第一个不平等条约是？','南京条约'),(144,'春秋时的齐国和鲁国，在现在的哪个省？','山东'),(145,'\"山药蛋派\"的代表作家是？','赵树理'),(146,'\"花儿\"是属于哪一个少数民族的民歌？','回族'),(147,'创造内地贺岁片之风，《甲方乙方》《不见不散》的导演是？','冯小刚'),(148,'“如果我多一张船票，你会不会跟我一起走”出自哪部电影？','花样年华'),(149,'电影《失恋33天》女主角的老公是谁？','陈羽凡'),(150,'电影《忠犬八公的故事》是真实发生在哪里的故事？','日本'),(151,'人的面部与手一样有左右偏性，那么哪种偏性的人更多？','右'),(152,'《勇敢的心》中华莱士在临死前喊了什么？','Freedom'),(153,'著名小说《乱世佳人》的女主角叫什么？','郝思嘉'),(154,'五岳中的中岳是哪座山？','嵩山'),(155,'我国海洋气温最高值出现在哪月？','八月'),(156,'《在那遥远的地方》是哪里的民歌？','青海'),(157,'俗称\"四不象\"的动物是？','麋鹿'),(158,'著名的卢浮宫博物馆在？','伦敦'),(159,'音乐城在哪个国家 ？','奥地利'),(160,'\"锅庄\"是下面哪一个少数民族的舞蹈？','羌族'),(161,'《泰坦尼克号》中Rose扔进海里的蓝色钻石叫什么？','海洋之心'),(162,'世界上最好的咖啡产于？','牙买加'),(163,'中国神话当中，嫦娥在月亮上住的行宫叫什么？','广寒宫'),(164,'我国古迹龙门石窟位于哪个城市？','洛阳'),(165,'诸葛亮的办公地点，武侯祠在什么地方？','成都'),(166,'《孙子兵法》的作者是？','孙武'),(167,'鳄鱼是不是哺乳动物？','不是'),(168,'“遥知兄弟登高处，遍插茱萸少一人”指的是什么节日？','重阳节'),(169,'童话故事《皮皮鲁和鲁西西》系列的作者是？','郑渊洁'),(170,'作家三毛的丈夫叫什么？','荷西'),(171,'《钢铁是怎样炼成的》作者是谁？','奥斯特洛夫斯基'),(172,'电视剧《北京青年》中四兄弟重走一回青春第一站是哪里？','烟台'),(173,'首届国际大专辩论会是在哪个国家或地区举行的？','新加坡'),(174,'86版《西游记》孙悟空的扮演者是？','六小龄童'),(175,'周长相等的等边三角形、正方形、圆形，哪一个的面积最大？','圆形'),(176,'\"喇叭\"是对什么乐器的俗称？','唢呐'),(177,'清朝被称为万园之园的皇家园林是？','圆明园'),(178,'周杰伦在《头文字D》中饰演的拓海开的车是什么型号？','AE86'),(179,'馒头是谁发明的？','诸葛亮'),(180,'跨欧亚两大洲的湖泊是死海对吗？','不对'),(181,'手绢是由什么演变而来的？','头巾'),(182,'关于阴阳下面说法正确的是？山南为阳、水北为阳、山北为阳、水南为阳？','山南为阳'),(183,'《神雕侠侣》中，绝情谷谷主公孙止的女儿叫什么？','公孙绿萼'),(184,'\"春风不度玉门关\"是谁的诗句？','王之涣'),(185,'《射雕英雄传》94版中杨康的扮演者是？','罗嘉良'),(186,'中国工商银行可以发行货币吗？','不可以'),(187,'周星驰主演的电影《大话西游》有两个系列，一个是月光宝盒，另一个是？','仙履奇缘'),(188,'普通小轿车大多数是采用前轮驱动还是后轮驱动？','前轮驱动'),(189,'放大镜的设计原理是采用了？','凸透镜'),(190,'\"黄梅戏\"是哪个省的地方戏？','安徽'),(191,'欧洲最长的河流是？','伏尔加河'),(192,'以刘志丹命名的志丹县在哪个省？','陕西'),(193,'“己所不欲勿施于人”是哪位圣人提出？','孔子'),(194,'“闭月羞花沉鱼落雁”中的“沉鱼”指的哪位美女？','西施'),(195,'“士为自己者死”下一句？','女为悦己者容'),(196,'人类最古老的绘画形式是？','壁画'),(197,'在汽车前面供驾驶员看左右和后面车辆的是凹镜还是凸镜？','凸镜'),(198,'《金瓶梅》中“瓶”指哪位女性？','李瓶儿'),(199,'按风俗，\"腊八粥\"应在阴历哪一天喝？','十二月初八'),(200,'男子五十叫什么？','知天命'),(201,'由白百合、彭于晏主演，4月12日即将上映的影片叫什么？','分手合约'),(202,'世界上第一部成文宪法是？','美国宪法'),(203,'河南出土的商代文物\"司母戊鼎\"是用什么材料制作的？','青铜'),(204,'北极的气温比南极的气温低还是高？','高'),(205,'“钢丝”是哪位艺人的粉丝最常用的称呼？','郭德纲'),(206,'\"茅盾\"是一位作家的笔名，这位作家的原名是？','沈雁冰'),(207,'张家界风景区位于我国的哪一个省份？','湖南'),(208,'“姑苏城外寒山寺”中的“姑苏”现在指哪个城市？','苏州'),(209,'中国在哪一年举办了奥运会？','2008'),(210,'著名笑星，最常与郭达搭档演出小品的女演员是？','蔡明'),(211,'工业革命的发祥地在？','英国'),(212,'巴西的首都是哪座城市？','巴西利亚'),(213,'用齿轮传动不能改变运动的方向，对吗？','不对'),(214,'中国端阳节有用雄黄酒灭五毒的习惯，那么雄黄酒可以饮用吗？','不可以'),(215,'《色戒》原作者是谁？','张爱玲'),(216,'银杉、银杏、松，哪一种树被称为\"活化石\"？','银杏'),(217,'非洲象的耳朵比亚洲象小还是大？','大'),(218,'校园歌曲《童年》的词曲作者？','罗大佑'),(219,'世界名画《和平鸽》的创作者是？','毕加索'),(220,'《黄河大合唱》的作曲者是？','冼星海'),(221,'李白笔下的\"飞流直下三千尺，疑是银河落九天\"指的是哪个风景区？','庐山'),(222,'“回眸一笑百媚生，六宫粉黛无颜色”指的是？','杨贵妃'),(223,'世界上地势最低的国家是？','荷兰'),(224,'\"蒙太奇\"一词源于哪国语言？','法国'),(225,'日本古典文学名著《源氏物语》作者是？','紫式部'),(226,'欧洲最大的半岛在东欧还是北欧？','北欧'),(227,'水能资源主要分布在我国东北区域还是西南区域？','西南区域'),(228,'动画片《我为歌狂》中叶峰，楚天歌等人组成的乐队叫什么？','OPEN乐队'),(229,'婴儿需要的必需氨基酸比成人多一种，是吗？','是'),(230,'电视剧《蜗居》中的女主角海藻是由谁扮演的？','李念'),(231,'普洱茶的产地在哪？','云南'),(232,'霍建华版《笑傲江湖》中东方不败的扮演者是？','陈乔恩'),(233,'“曾经沧海难为水，除却巫山不是云”出自哪位词人？','元稹'),(234,'“蓦然回首，那人却在___________？','灯火阑珊处'),(235,'“衣带渐宽终不悔，为伊消得人憔悴”出自哪位词人？','柳永'),(236,'世界最深的洼地是？','死海'),(237,'哪个城市被称作\"草原钢城\"？','包头'),(238,'歌剧《卡门》中女主角卡门是哪个种族的女郎？','吉普赛'),(239,'自然界已知的最硬物质为？','金刚石'),(240,'黄山在我国的什么省份？','安徽'),(241,'\"三月街\"是我国哪个民族的传统节日？','白族'),(242,'香港演员郑伊健的新婚妻子是？','蒙嘉慧'),(243,'砌砖墙时，码砖的规则是？','错缝'),(244,'香港著名导演陈可辛的妻子是？','吴君如'),(245,'世界最重要的IT高科技产业基地硅谷位于美国的哪个州？','加利弗尼亚州'),(246,'骆驼有双层眼睫毛，对吗？','对'),(247,'亚欧大陆桥欧洲的终点站是？','鹿特丹'),(248,'香港演员罗家英的妻子是？','汪明荃'),(249,'琼瑶剧《还珠格格》中金锁最后嫁给了谁？','柳青'),(250,'电视剧《武林外传》中郭芙蓉的扮演者是？','姚晨'),(251,'北斗星的勺柄指向北极星对吗？','对'),(252,'据考古资料显示，我国的钻孔技术开始于哪个时代？','山顶洞人时代'),(253,'瑞士是联合国的成员国吗？','不是'),(254,'被称为易安居士的是南宋哪位著名女词人？','李清照'),(255,'电视剧《北京爱情故事》中程锋的外号是？','疯子'),(256,'香槟酒是葡萄酒吗？','是'),(257,'藏历新年，人们见面时都要说\"扎西德勒\"是什么意思？','吉祥如意'),(258,'电视剧《奋斗》中小灵仙儿的饰演者是？','陈意涵'),(259,'木星和土星谁卫星最多？','土星'),(260,'\"玛祖卡舞\"起源于？','波兰'),(261,'世界上最大的淡水湖是？','苏必略湖'),(262,'台湾娱乐节目《康熙来了》是由小S与哪位男艺人一起主持的？','蔡康永'),(263,'香港无线电视台的外文简称是？','TVB'),(264,'台湾版《流星花园》中言承旭饰演的角色叫什么？','道明寺'),(265,'中国的尼姑最早是何时出现的？','南北朝'),(266,'电视剧《护花危情》中男女主角的定情曲是邓丽君的哪首代表作？','月亮代表我的心'),(267,'京剧中，饰演性格活泼、开朗的青年女性的应是？','花旦'),(268,'湖南卫视节目《百变大咖秀》的主持人是何炅与哪位女艺人？','谢娜'),(269,'\"碧云天，黄叶地，北雁南飞\"语出于？','西厢记'),(270,'古人称为\"手谈\"的是指？','围棋'),(271,'世界上最深的湖是？','贝加尔湖'),(272,'电影《重庆森林》的导演是？','王家卫'),(273,'电影《春光乍泄》是由张国荣与哪位香港男艺人一起主演的？','梁朝伟'),(274,'《红楼梦》中平儿是谁的丫鬟？','王熙凤'),(275,'比长江还要长的河是？','亚马逊河'),(276,'电影《功夫熊猫》中，熊猫大侠的名字叫什么？','阿宝'),(277,'贾宝玉说“男人是泥做的骨肉”，女人是什么做的骨肉？','水'),(278,'诗歌《炉中煤》是谁的作品？','郭沫若'),(279,'电视剧《穿越时空的爱恋》原作者是？','水阡墨'),(280,'坦克是哪个国家发明的？','英国'),(281,'古代四大美女在唐朝的是？','杨玉环'),(282,'“冲天香阵透长安，满城尽带黄金价”是从谁的诗作中诗作中摘录的？','黄巢'),(283,'“东风无力百花残”的上一句是什么？','相见时难别亦难'),(284,'人体缺少哪种元素会造成甲状腺肿大？','钠'),(285,'“客舍青青柳色新”的上一句是什么？','渭城朝雨邑轻尘'),(286,'“朝辞白帝彩云间”的下一句是什么？','千里江陵一日还'),(287,'发酵程度大于百分之80的的全发酵茶叫什么？','红茶'),(288,'牛羊肉泡馍古时称什么?','羊羹'),(289,'在天体（主要指月亮和地球）引潮力作用下所产生的周期性运动叫什么?','潮汐'),(290,'我国三大名酒茅台，泸州老窖和什么？','汾酒'),(291,'中国十大名茶之一的安徽祁门红属于什么茶？','红茶'),(292,'西湖白堤是因纪念哪位诗人而得名？','白居易'),(293,'国十中大名茶排名第二的是苏州洞庭什么茶？','碧螺春'),(294,'鸢的俗称是什么？','老鹰'),(295,'神话《白蛇传》中\"白娘娘盗仙草\"盗的是？','灵芝'),(296,'猪八戒和唐僧是喝了哪个河水而怀孕的?','子母河'),(297,'云南省省花是什么花?','茶花'),(298,'我国八大菜系分别是鲁菜，川菜，粤菜，苏菜，湘菜，闽菜，徽菜和什么？','浙菜'),(299,'世界杯足球赛的首届冠军是？','乌拉圭'),(300,'当月球运行至地球的阴影部分时，在月球和地球之间的地区会因为太阳光被地球所遮蔽，看到月球缺了一块的现象叫什么？','月食'),(301,'中国名茶之冠是杭州西湖的什么茶?','龙井'),(302,'被称为\"书圣\"的古代书法家为？','王羲之'),(303,'星际空间的气体和尘埃结合成的云雾状天体叫什么？','星云'),(304,'广东省的省花是什么？','木棉花'),(305,'西游记中偷走唐僧袈裟的是什么妖怪?','黑熊精'),(306,'地球表面的点随地球自转产生的轨迹中周长最长的圆周线叫什么?','赤道'),(307,'“及第粥”是我国哪个省份的著名小吃？','广东'),(308,'荔枝主要产于我国的那个省份？','广东'),(309,'四川把混沌叫什么?','抄手'),(310,'汤圆最早叫什么？','浮元子'),(311,'馄饨在江西俗称为什么？','清汤'),(312,'刀削面是我国哪个省份的人们日常喜爱的面食？','山西'),(313,'“葫芦鸡”是我国那个城市的名菜？','西安'),(314,'烹调东坡肉最好选用什么猪肉?','五花肉'),(315,'乌龙茶又称什么？','青茶'),(316,'为了表演的需要，芭蕾鞋的鞋尖内塞有一小块什么？','木头'),(317,'历史上第一个获得两次诺贝尔奖的人是谁？','居里夫人'),(318,'挖掘机技术哪家强？','中国山东找蓝翔');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skill` (
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
  `hjfjfw` int(1) DEFAULT NULL COMMENT '0=附加敌方1=附加自己',
  `yearbuff` json DEFAULT NULL,
  UNIQUE KEY `hjname` (`hjname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='技能';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
INSERT INTO `skill` VALUES ('撞击','直接撞击对方',0,20,105,70,NULL,NULL,5,0,0,0,NULL),('打击','冲向对方进行打击',0,30,106,75,NULL,NULL,10,0,0,0,NULL),('狂化','为自身附加状态【狂化】',0,40,100,99,NULL,'狂化',120,0,1,1,NULL);
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `ztname` varchar(255) DEFAULT NULL,
  `bubat` int(255) DEFAULT NULL,
  `pr` bigint(255) DEFAULT NULL,
  `pow` bigint(255) DEFAULT NULL,
  `ct` bigint(255) DEFAULT NULL,
  `ctp` bigint(255) DEFAULT NULL,
  `speed` bigint(255) DEFAULT NULL,
  `de` bigint(255) DEFAULT NULL,
  `diehp` bigint(255) DEFAULT NULL,
  `cd` int(255) NOT NULL,
  `info` varchar(255) DEFAULT NULL,
  UNIQUE KEY `ztname` (`ztname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES ('狂化',0,105,105,5,10,105,110,NULL,60,'使自身进入狂化状态60秒'),('禁锢',1,100,100,0,0,0,0,NULL,60,'禁锢60秒');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `talent_skill`
--

DROP TABLE IF EXISTS `talent_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `talent_skill` (
  `id` int(10) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `data` json DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `talent_skill`
--

LOCK TABLES `talent_skill` WRITE;
/*!40000 ALTER TABLE `talent_skill` DISABLE KEYS */;
/*!40000 ALTER TABLE `talent_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `cat` varchar(255) DEFAULT NULL COMMENT '完成某任务',
  `racl` int(255) DEFAULT NULL COMMENT '达到某级',
  `ci` json DEFAULT NULL COMMENT '收集某物品',
  `npcd` varchar(255) DEFAULT NULL COMMENT 'NPC对话',
  `reward` json DEFAULT NULL COMMENT '奖励',
  `details` json DEFAULT NULL COMMENT '任务说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_data`
--

DROP TABLE IF EXISTS `task_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_data` (
  `id` int(255) NOT NULL,
  `task_id` int(255) DEFAULT NULL COMMENT '任务id',
  `qq` int(255) DEFAULT NULL,
  `data` json DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_data`
--

LOCK TABLES `task_data` WRITE;
/*!40000 ALTER TABLE `task_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `qq` bigint(255) NOT NULL,
  `name` varchar(18) NOT NULL,
  `sex` varchar(3) NOT NULL DEFAULT '',
  `nowmap` varchar(255) DEFAULT NULL,
  `nofight` int(255) DEFAULT NULL COMMENT '禁止战斗',
  `batid` json DEFAULT NULL,
  `status` json DEFAULT NULL COMMENT '附加状态',
  `ztstartdate` datetime DEFAULT NULL,
  `skill` json DEFAULT NULL,
  `state_info` json DEFAULT NULL,
  `backpack` json DEFAULT NULL,
  `money` json DEFAULT NULL,
  `weapons_attack` int(255) DEFAULT NULL COMMENT '攻击魂导器',
  `weapons_defence` int(255) DEFAULT NULL COMMENT '防御魂导器',
  `sgin_in_day` json DEFAULT NULL COMMENT '签到天数',
  `team` json DEFAULT NULL,
  `talent` json DEFAULT NULL COMMENT '天赋技能',
  `task` json DEFAULT NULL,
  `alias` json DEFAULT NULL COMMENT '快捷指令',
  `temporary_skill` json DEFAULT NULL COMMENT '临时魂环存放',
  `skill_data` json DEFAULT NULL COMMENT '原始魂环',
  `goods_data` json DEFAULT NULL COMMENT '物品使用次数',
  `npc` varchar(255) DEFAULT NULL COMMENT '当前对话的npc',
  PRIMARY KEY (`qq`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (434658198,'夜夜','男','圣魂村',NULL,NULL,NULL,NULL,NULL,'{\"userData\": {\"体力\": 216, \"力量\": 97, \"攻击\": 65, \"生命\": 62654, \"等级\": 50, \"经验\": 70070110, \"速度\": 35, \"闪避\": 28, \"防御\": 1208238, \"暴击率\": 32, \"精神力\": 64, \"魂力值\": 41499, \"当前生命\": 2001, \"技能加速\": 1, \"暴击伤害\": 180, \"气血之力\": \"\", \"生命储备\": 0, \"第一武魂\": \"萝卜\", \"第二武魂\": \"\", \"魂力储备\": 1400, \"当前魂力值\": 42269, \"气血之力描述\": \"\", \"生命储备上限\": 5000, \"第一武魂描述\": \"器武魂\", \"第一武魂类型\": \"食物系\", \"第二武魂描述\": \"\", \"第二武魂类型\": \"\", \"魂力储备上限\": 5000}, \"userInfo\": {\"sex\": \"男\", \"name\": \"夜夜\", \"当前属性\": \"第一武魂\", \"武魂数量\": \"1\", \"气血之力增幅\": \"\", \"第二武魂增幅\": \"\"}}','{\"mecha\": {}, \"weapons\": {\"永恒之铠\": \"1|20\", \"黄金龙枪\": \"0|19\"}, \"material\": {\"沉银\": 1, \"哥布林战旗\": 8, \"哥布林手指\": 22}, \"consumables\": {\"低级生命药水\": 447}, \"battle_armor\": {}}','{\"点券\": 600, \"联邦币\": -7200}',508635,NULL,'{\"allDay\": 13, \"nowDay\": 6, \"isNowDay\": 0}',NULL,NULL,NULL,'{\"攻击\": {\"[CQ:face,id=56]\": \"null\"}}','{\"Age\": 8, \"HunShouName\": \"史莱姆\"}','{\"气血之力\": {}, \"第一武魂\": {\"1\": {\"SkillAge\": \"424\", \"SkillName\": \"打击\", \"SkillHunShouName\": \"哥布林\"}, \"2\": {\"SkillAge\": \"515\", \"SkillName\": \"打击\", \"SkillHunShouName\": \"哥布林\"}, \"3\": {\"SkillAge\": \"524\", \"SkillName\": \"打击\", \"SkillHunShouName\": \"哥布林\"}, \"4\": {\"SkillAge\": \"357\", \"SkillName\": \"打击\", \"SkillHunShouName\": \"哥布林\"}}}','{\"低级生命药水\": 11}','圣魂长老');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_weapons_data`
--

DROP TABLE IF EXISTS `user_weapons_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_weapons_data` (
  `id` int(255) NOT NULL,
  `qq` int(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `data_info` json DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_weapons_data`
--

LOCK TABLES `user_weapons_data` WRITE;
/*!40000 ALTER TABLE `user_weapons_data` DISABLE KEYS */;
INSERT INTO `user_weapons_data` VALUES (54303,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(81140,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(150003,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(242064,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(266969,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(456701,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(481551,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": 1, \"力量\": 1, \"攻击\": 2, \"血量\": 3, \"速度\": 5, \"闪避\": 3, \"防御\": 10, \"暴击率\": 2, \"魂力值\": 5, \"暴击伤害\": 10}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(508635,434658198,'黄金龙枪','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"100\", \"攻击\": \"200\", \"血量\": \"30\", \"速度\": \"50\", \"闪避\": \"3\", \"防御\": \"100\", \"暴击率\": \"20\", \"魂力值\": \"5000\", \"暴击伤害\": \"50\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(531631,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}'),(677147,434658198,'永恒之铠','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}');
/*!40000 ALTER TABLE `user_weapons_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userdata`
--

DROP TABLE IF EXISTS `userdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userdata` (
  `qq` int(255) NOT NULL,
  `status` json DEFAULT NULL,
  `skill` longtext,
  PRIMARY KEY (`qq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdata`
--

LOCK TABLES `userdata` WRITE;
/*!40000 ALTER TABLE `userdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `userdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weapons`
--

DROP TABLE IF EXISTS `weapons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weapons` (
  `name` varchar(255) NOT NULL,
  `type` int(255) DEFAULT NULL COMMENT '类型',
  `grade` varchar(255) DEFAULT NULL COMMENT '品阶',
  `breach` json DEFAULT NULL COMMENT '突破素材',
  `info` text COMMENT '说明',
  `data_info` json DEFAULT NULL COMMENT '数据信息',
  `grade_limit` int(255) DEFAULT NULL COMMENT '等级限制',
  `seal_goods` varchar(255) DEFAULT NULL COMMENT '封印需要物品',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weapons`
--

LOCK TABLES `weapons` WRITE;
/*!40000 ALTER TABLE `weapons` DISABLE KEYS */;
INSERT INTO `weapons` VALUES ('永恒之铠',1,'神器','{\"1\": {\"物品1\": \"1\"}, \"2\": {\"物品1\": \"1\"}, \"3\": {\"物品1\": \"1\"}, \"4\": {\"物品1\": \"1\"}}','传说生命之神的防御甲胄！','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"1\", \"攻击\": \"2\", \"血量\": \"3\", \"速度\": \"5\", \"闪避\": \"3\", \"防御\": \"10\", \"暴击率\": \"2\", \"魂力值\": \"5\", \"暴击伤害\": \"10\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}',5,'{\"生命之果\": \"1\"}'),('黄金龙枪',0,'神器','{\"1\": {\"物品1\": \"1\"}, \"2\": {\"物品1\": \"1\"}, \"3\": {\"物品1\": \"1\"}, \"4\": {\"物品1\": \"1\"}}','传说中龙神肋骨所化，能吞噬生命力！','{\"dataInfo\": {\"冷却\": \"1\", \"力量\": \"100\", \"攻击\": \"200\", \"血量\": \"30\", \"速度\": \"50\", \"闪避\": \"3\", \"防御\": \"100\", \"暴击率\": \"20\", \"魂力值\": \"5000\", \"暴击伤害\": \"50\"}, \"stateSkill\": {\"自身附加\": \"狂化|30\", \"附加状态\": \"灰烬汲取|30\"}}',5,'{\"生命之果\": \"1\"}');
/*!40000 ALTER TABLE `weapons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wordNotice`
--

DROP TABLE IF EXISTS `wordNotice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wordNotice` (
  `id` int(255) NOT NULL,
  `notice` text COMMENT '公告内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wordNotice`
--

LOCK TABLES `wordNotice` WRITE;
/*!40000 ALTER TABLE `wordNotice` DISABLE KEYS */;
INSERT INTO `wordNotice` VALUES (1,'发现BUG及时报告管理才有奖励，利用BUG玩游戏给予警告！！'),(2,'违规的信息可以私信发给群主，领取丰厚的举报奖励！');
/*!40000 ALTER TABLE `wordNotice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wuhun`
--

DROP TABLE IF EXISTS `wuhun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `des` varchar(255) NOT NULL,
  `data` json DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wuhun`
--

LOCK TABLES `wuhun` WRITE;
/*!40000 ALTER TABLE `wuhun` DISABLE KEYS */;
INSERT INTO `wuhun` VALUES ('独狼','敏攻系',5,15,30,110,1,5,5,5,5,5,'兽武魂','{\"力量\": \"5\", \"攻击\": \"5\", \"速度\": \"5\", \"闪避\": \"15\", \"防御\": \"5\", \"暴击率\": \"30\", \"精神力\": \"5\", \"魂力值\": \"5\", \"技能加速\": \"1\", \"暴击伤害\": \"110\", \"武魂描述\": \"兽武魂\", \"武魂类型\": \"敏攻系\"}'),('萝卜','食物系',5,10,20,120,1,5,5,5,5,5,'兽武魂','{\"力量\": \"5\", \"攻击\": \"5\", \"速度\": \"5\", \"闪避\": \"10\", \"防御\": \"5\", \"暴击率\": \"20\", \"精神力\": \"5\", \"魂力值\": \"5\", \"技能加速\": \"1\", \"暴击伤害\": \"120\", \"武魂描述\": \"器武魂\", \"武魂类型\": \"食物系\"}'),('镰刀','敏攻系',5,15,30,110,1,5,5,5,5,5,'器武魂','{\"力量\": \"5\", \"攻击\": \"5\", \"速度\": \"5\", \"闪避\": \"15\", \"防御\": \"5\", \"暴击率\": \"30\", \"精神力\": \"5\", \"魂力值\": \"5\", \"技能加速\": \"1\", \"暴击伤害\": \"110\", \"武魂描述\": \"器武魂\", \"武魂类型\": \"敏攻系\"}');
/*!40000 ALTER TABLE `wuhun` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'qimenbot'
--

--
-- Dumping routines for database 'qimenbot'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-20 15:32:22
