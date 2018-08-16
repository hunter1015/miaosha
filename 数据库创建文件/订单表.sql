CREATE TABLE `t_order` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `userId` int(8) NOT NULL COMMENT '用户ID',
  `goodsId` long NOT NULL COMMENT '商品ID',
  `deliveryAddrId` long NOT NULL COMMENT '交付地址',
  `goodsName` varchar(50) NOT NULL COMMENT '商品名称',
  `goodsCount` int NOT NULL COMMENT '购买数量',
  `goodsPrice` double NOT NULL COMMENT '购买价格',
  `orderChannel` int NOT NULL COMMENT '购买渠道',
  `status` int NOT NULL COMMENT '订单状态',
  `createDate` DATE NOT NULL COMMENT '创建订单时间',
  `payDate` DATE NOT NULL COMMENT '支付订单时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
