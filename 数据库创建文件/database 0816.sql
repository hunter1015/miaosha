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
  `goodsId` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `goodsName` varchar(50) NOT NULL COMMENT '商品名',
  `goodsTitle` varchar(10) NOT NULL COMMENT '商品标题',
  `goodsImg` varchar(200) NOT NULL COMMENT '商品图片',
  `goodsDetail` varchar(500) NOT NULL COMMENT '商品描述',
  `goodsPrice` double NOT NULL COMMENT '商品价格',
  `goodsStock` int(11) NOT NULL COMMENT '商品库存',
  PRIMARY KEY (`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_goods`
--

LOCK TABLES `t_goods` WRITE;
/*!40000 ALTER TABLE `t_goods` DISABLE KEYS */;
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
  `goodsId` mediumtext NOT NULL COMMENT '商品ID',
  `stockCount` int(11) NOT NULL COMMENT '秒杀库存',
  `startDate` date NOT NULL COMMENT '秒杀开始时间',
  `endDate` date NOT NULL COMMENT '秒杀结束时间',
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
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
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

-- Dump completed on 2018-08-16 17:26:40
