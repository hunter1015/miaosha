-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: miaoshademo
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_goods`
--

DROP TABLE IF EXISTS `t_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_goods` (
  `id` int(8) NOT NULL COMMENT '主键自增',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名',
  `goods_title` varchar(80) NOT NULL COMMENT '商品标题',
  `goods_img` varchar(200) NOT NULL COMMENT '商品图片',
  `goods_detail` varchar(500) NOT NULL COMMENT '商品描述',
  `goods_price` double NOT NULL COMMENT '商品价格',
  `goods_stock` int(11) NOT NULL COMMENT '商品库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_goods`
--

LOCK TABLES `t_goods` WRITE;
/*!40000 ALTER TABLE `t_goods` DISABLE KEYS */;
INSERT INTO `t_goods` VALUES (1,'iphoneX','Apple/苹果iPhone X 全网通4G手机苹果X 10','/img/iphonex.png','Apple/苹果iPhone X 全网通4G手机苹果X 10',7788,100),(2,'华为 P20 PRO','Huawei/华为 P20 PRO全网通4G智能手机','/img/p20pro.png','Huawei/华为 P20 PRO 8G+256G 全网通4G智能手机',5299,50),(3,'荣耀9i','Huawei/荣耀9i','/img/荣耀9i.jpg','Huawei/荣耀9i',999,9999),(4,'魅族PRO7','meizu/魅族PRO7','/img/魅族PRO7.jpg','魅族PRO7',1599,200);
/*!40000 ALTER TABLE `t_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_goods_miaosha`
--

DROP TABLE IF EXISTS `t_goods_miaosha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_goods_miaosha` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `miaosha_price` decimal(10,0) NOT NULL,
  `stock_count` int(11) NOT NULL COMMENT '秒杀库存',
  `start_date` date NOT NULL COMMENT '秒杀开始时间',
  `end_date` date NOT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_goods_miaosha`
--

LOCK TABLES `t_goods_miaosha` WRITE;
/*!40000 ALTER TABLE `t_goods_miaosha` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_goods_miaosha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_order` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `userId` int(8) NOT NULL COMMENT '用户ID',
  `goodsId` mediumtext NOT NULL COMMENT '商品ID',
  `deliveryAddrId` mediumtext NOT NULL COMMENT '交付地址',
  `goodsName` varchar(50) NOT NULL COMMENT '商品名称',
  `goodsCount` int(11) NOT NULL COMMENT '购买数量',
  `goodsPrice` double NOT NULL COMMENT '购买价格',
  `orderChannel` int(11) NOT NULL COMMENT '购买渠道',
  `status` int(11) NOT NULL COMMENT '订单状态',
  `createDate` date NOT NULL COMMENT '创建订单时间',
  `payDate` date NOT NULL COMMENT '支付订单时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_user` (
  `id` int(10) NOT NULL COMMENT '主键自增',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `phone` char(11) NOT NULL,
  `password` varchar(50) NOT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL,
  `head` varchar(45) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (17,'测试用户1','18610650001','feb0fbcf63b4d7a4e12dd57e05bf8635','91IrK3TQfH4A/ycabVE+fQ==',NULL,'2018-08-22 15:50:55',NULL,NULL),(18,'测试用户2','18610650002','3e47e501a0dc5686b6c79ec2fd6f45d3','HdH5aB56rIIBPek6ya/jrw==',NULL,'2018-08-22 15:50:56',NULL,NULL),(19,'测试用户3','18610650003','b3f1e1c198a74c4e9c1c9d23c6b7e675','w95XdGR/kTbXkR3GYoiGkA==',NULL,'2018-08-22 15:50:56',NULL,NULL),(20,'测试用户4','18610650004','66758b8d4caa52e9fd40b434a450d2c7','e67RZNC6y18SHuBdZZM6oQ==',NULL,'2018-08-22 15:50:56',NULL,NULL),(21,'测试用户5','18610650005','a2707a85507ddecaaf8748c71667186a','kledJaGRsv6v+XpTxSVmHw==',NULL,'2018-08-22 15:50:56',NULL,NULL),(22,'测试用户6','18610650006','0048d50b443d7ce4eeac1b6840b12fc3','GYuW3zkRjpQZ1lIaSg36DQ==',NULL,'2018-08-22 15:50:57',NULL,NULL),(23,'测试用户7','18610650007','c07a1304ba2877e44ba56d66f220531c','JDo9Kq01+so+1lwUxa/HEg==',NULL,'2018-08-22 15:50:57',NULL,NULL),(24,'测试用户8','18610650008','3ba109325964ab092b7ad7dcb00bc8fd','//bSbzjkFUJOrJI8LwZ2jg==',NULL,'2018-08-22 15:50:57',NULL,NULL),(25,'测试用户9','18610650009','c61694303b1de30bb81546425de7ce79','ZNyrE3o7sOYWyLuy5QtiOw==',NULL,'2018-08-22 15:50:57',NULL,NULL),(26,'测试用户10','18610650010','d319a78305e664fa23e97b48cb535a89','2AzslsBjy98bWL3Shx2IJA==',NULL,'2018-08-22 15:50:57',NULL,NULL),(27,'yyh123','11111111111','59e0e5c313914b505eb0549db0fa5620','bytDGG19w2FmituKz0jxcg==',NULL,'2018-08-22 16:30:31',NULL,NULL),(28,'yyh001','11111111111','b106d1d467afb2ff819453d50f1a776b','xLK8QzYfa+svFsxrfDN4sg==',NULL,'2018-08-23 16:21:15',NULL,NULL),(29,'yyh002','11111111111','1e121cd54a058a500f9dffc2411d833e','gDsXv3lJG97kYDEb5EJ10A==',NULL,'2018-08-23 16:22:32',NULL,NULL),(30,'yyh1231','11111111111','f860b81ae149ea3f7b6c9193f36c83f2','X/+uucKM5PZXb8TOKluacg==',NULL,'2018-08-29 10:33:13',NULL,NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-31 17:01:43
