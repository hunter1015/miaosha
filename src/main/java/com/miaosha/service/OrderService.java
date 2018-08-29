package com.miaosha.service;

import com.miaosha.dao.OrderDao;
import com.miaosha.entity.MiaoshaOrder;
import com.miaosha.entity.OrderInfo;
import com.miaosha.entity.User;
import com.miaosha.redis.OrderKey;
import com.miaosha.redis.RedisService;
import com.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Component
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    //根据用户ID+商品ID获取秒杀订单号
    public MiaoshaOrder getMiaoshaUserByUserIdGoodsId(long userId, long goodsId) {
//        return  orderDao.getMiaoshaUserByUserIdGoodsId(userId,goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class);
    }

    //通过ID获取订单详情
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods) {
        //新建订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        //插入订单
        orderDao.insert(orderInfo);

        //新建秒杀订单（与订单相关联）
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        //秒杀成功后把信息写到缓存
        redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user
                .getId()+"_"+goods.getId(),miaoshaOrder);
        return orderInfo;
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }

}
