package com.miaosha.dao;

import com.miaosha.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

@Component
@Mapper
public interface GoodsDao extends BaseMapper<Goods> {

    /*@Select(select * from t_goods where goodsId)
    public Goods getGoodsByGoodsId(@Param("goodsId") long goodsId);*/

    @Update("Update t_goods set goodsStock=goodsStock-1 where goodsId=#{goodsId} and goodsStock > 0")
    public int reduceStock(Goods goods);
}
