package com.service.impl;

import com.dao.UserDao;
import com.entities.UserPO;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userServiceImpl")
@Scope("prototype")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserPO> getAllUser() {
        return userDao.getAllUsers();
    }

    @Override
    public UserPO getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Integer loginById(String username, String password) {
        UserPO userPO = userDao.getUserByUsername(username);
        if(userPO == null){
            return 0;
        }
        if(!userPO.getPassword().equals(password)){
            //return false;
            return 1;
        }
        else {
            //return true;
            return 2;
        }
    }

    @Override
    public UserPO getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}