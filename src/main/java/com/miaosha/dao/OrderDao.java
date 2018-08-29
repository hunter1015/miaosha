package com.miaosha.dao;

import com.miaosha.entity.MiaoshaOrder;
import com.miaosha.entity.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
@Component
public interface OrderDao extends BaseMapper<OrderInfo> {
    /**
     * 通过用户ID和订单ID查找秒杀订单号
     */
    @Select("select * from t_miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")long userId, @Param("goodsId")long goodsId);

    /**
     * 新建订单+返回订单id
     * @param orderInfo
     * @return
     */
    @Insert("insert into t_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insertOrder(OrderInfo orderInfo);

    /**
     * 新建秒杀订单
     * @param miaoshaOrder
     * @return
     */
    @Insert("insert into t_miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    /**
     * 查找-通过订单ID查找订单详情
     * @param orderId
     * @return
     */
    @Select("select * from t_order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId")long orderId);

    /**
     * 删除所有订单
     */
    @Delete("delete from t_order_info")
    public void deleteOrders();

    /**
     * 删除所有秒杀订单
     */
    @Delete("delete from t_miaosha_order")
    public void deleteMiaoshaOrders();
}
