package com.service;

import com.entities.AdministratorPO;

import java.util.List;

public interface AdministratorService {
    //查询所有管理员信息
    List<AdministratorPO> getAllAdministrators();

    //根据id查管理员
    AdministratorPO getAdministratorById(long id);

    //判断登陆
    Boolean loginById(long id, String password);
}
