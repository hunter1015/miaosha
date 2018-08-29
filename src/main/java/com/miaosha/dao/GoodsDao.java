package com.miaosha.dao;

import com.miaosha.entity.Goods;
import com.miaosha.entity.MiaoshaGoods;
import com.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Component
@Mapper
public interface GoodsDao extends BaseMapper<Goods> {

    /*@Select(select * from t_goods where goodsId)
    public Goods getGoodsByGoodsId(@Param("goodsId") long goodsId);*/

    @Update("Update t_goods_miaosha set stockCount=stockCount-1 where goodsId=#{goodsId} and stockCount > 0")
    public int reduceStock(MiaoshaGoods goods);


    //@Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from t_goods_miaosha mg left join goods g on mg.goods_id = g.id")
    @Select("SELECT * FROM t_goods")
    public List<Goods> listGoods();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from t_goods_miaosha mg left join t_goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from t_goods_miaosha mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    //@Select("SELECT * FROM t_goods where goodsId=#{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);


    @Update("update t_goods_miaosha set stock_count = #{stockCount} where goods_id = #{goodsId}")
    public int resetStock(MiaoshaGoods g);

}
