package com.miaosha.service;

import com.miaosha.dao.GoodsDao;
import com.miaosha.entity.Goods;
import com.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<Goods> listGoods() {
        return goodsDao.selectAll();
    }

    public List<GoodsVo> listGoodsVo(){
        return  goodsDao.listGoodsVo();
    }
    public Goods getGoodsByGoodsId(long goodsId){
        return goodsDao.selectByPrimaryKey(goodsId);
    }
    public GoodsVo getGoodsVoByGoodsId(long goodsId){
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(Goods goods){
        return goodsDao.reduceStock(goods)>0;

    }

    public boolean createNewGoods(Goods goods){
        return goodsDao.insert(goods)>0;
    }
}
