package com.service.impl;

import com.dao.AdministratorDao;
import com.entities.AdministratorPO;
import com.service.AdministratorService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("administratorServiceImpl")
@Scope("prototype")
public class AdministratorServiceImpl implements AdministratorService{

    @Resource
    private AdministratorDao administratorDao;

    @Override
    public List<AdministratorPO> getAllAdministrators() {
        return administratorDao.getAllAdministrators();
    }

    @Override
    public AdministratorPO getAdministratorById(long id) {
        return administratorDao.getAdministratorById(id);
    }

    @Override
    public Boolean loginById(long id, String password) {
        AdministratorPO administratorPO = administratorDao.getAdministratorById(id);
        if(password == administratorPO.getPassword()){
            return true;
        }
        else {
            return false;
        }
    }
}
