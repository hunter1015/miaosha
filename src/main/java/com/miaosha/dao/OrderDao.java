package com.miaosha.dao;

import com.miaosha.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface OrderDao extends BaseMapper<OrderInfo> {
}
