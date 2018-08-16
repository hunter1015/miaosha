package com.miaosha.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name="t_goods_miaosha")
public class MiaoshaGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
