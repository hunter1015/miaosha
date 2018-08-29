package com.miaosha.vo;


import com.miaosha.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods ;
    private User user;
}

