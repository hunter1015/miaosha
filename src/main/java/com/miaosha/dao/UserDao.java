package com.miaosha.dao;

import com.miaosha.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Component
public interface UserDao extends BaseMapper<User> {

    @Update("update t_user set password = #{password} where id = #{id}")
    public void update(User toBeUpdate);

    @Select("select * from t_user where username=#{username}")
    public List<User> selectUserByName(@Param("username") String username);

    @Select("select * from t_user where id=#{id}")
    public User selectUserById(@Param("id") long id);

}
