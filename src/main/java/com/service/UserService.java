package com.service;

import com.entities.UserPO;

import java.util.List;

public interface UserService {

    //查询所有用户信息
    List<UserPO> getAllUser();

    //根据id查用户
    UserPO getUserById(long id);

    //判断登陆
    Integer loginById(String username, String password);

    //根据用户名查用户
    UserPO getUserByUsername(String username);

    //添加新用户
    UserPO register(String username,String password,String name);
}