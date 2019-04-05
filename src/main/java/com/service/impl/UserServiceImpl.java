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
    public Boolean loginById(long id, String password) {
        UserPO userPO = userDao.getUserById(id);
        if(userPO == null || !userPO.getPassword().equals(password)){
            return false;
        }
        else {
            return true;
        }
    }
}