package com.dao;
import com.entities.UserPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//访问底层数据的接口
public interface UserDao {

    //查询所有用户信息
    List<UserPO> getAllUsers();

    //根据id查用户
    UserPO getUserById(@Param("id") long id);

}
