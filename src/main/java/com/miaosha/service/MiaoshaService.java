package com.miaosha.service;

import com.miaosha.entity.MiaoshaOrder;
import com.miaosha.entity.OrderInfo;
import com.miaosha.entity.User;
import com.miaosha.redis.MiaoshaKey;
import com.miaosha.redis.RedisService;
import com.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

     @Transactional
    public OrderInfo miaosha(User user, GoodsVo goods) {
        //秒杀到了，减库存，下订单。写入秒杀订单
         boolean success = goodsService.reduceStock(goods);
         if (success){
             //order_info.miashao_order,生成订单
             return orderService.createOrder(user,goods);
         }else {

             setGoodsOver(goods.getId());
            return null;
         }

         //不能在自家不能调用别家的dao，但可以调用别家的service
//         Goods g=new Goods();
//         g.setId(goods.getId());
//         g.setGoodsStock(goods.getGoodsStock()-1);
    }


    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaUserByUserIdGoodsId(userId, goodsId);
        if (order != null){
             return order.getId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return  -1;
            }else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isOver,""+goodsId );
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isOver, ""+goodsId, true);
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }
}
