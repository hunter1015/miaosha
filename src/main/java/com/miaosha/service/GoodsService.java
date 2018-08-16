package com.miaosha.service;

import com.miaosha.dao.GoodsDao;
import com.miaosha.entity.Goods;
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

    public Goods getGoodsByGoodsId(long goodsId){
        return goodsDao.selectByPrimaryKey(goodsId);
    }

    public boolean reduceStock(Goods goods){
        return goodsDao.reduceStock(goods)>0;

    }

    public boolean createNewGoods(Goods goods){
        return goodsDao.insert(goods)>0;
    }
}
