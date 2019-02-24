package com.dao;

import com.entities.AdministratorPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdministratorDao {
    //查询所有管理员信息
    List<AdministratorPO> getAllAdministrators();

    //根据id查管理员
    AdministratorPO getAdministratorById(@Param("id") long id);
}
