package com.miaosha.dao;

import com.miaosha.entity.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserDao extends BaseMapper<User> {


}
