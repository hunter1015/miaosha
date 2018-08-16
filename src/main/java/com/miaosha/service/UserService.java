package com.miaosha.service;

import com.miaosha.dao.UserDao;
import com.miaosha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {

    @Autowired
    UserDao userDao;

    public boolean newUser(User user){
        return userDao.insert(user)>0;
    }

    public boolean deleteUser(int userId){
        return userDao.deleteByPrimaryKey(userId)>0;
    }

    public List<User> listAllUser(){
        return userDao.selectAll();
    }
}
