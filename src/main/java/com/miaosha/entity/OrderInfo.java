package com.miaosha.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "t_order")
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;//交付地址
    private String goodsName;
    private Integer goodsCount;//数量
    private Double goodsPrice;
    private Integer orderChannel;//渠道
    private Integer status;//状态
    private Date createDate;
    private Date payDate;
}

