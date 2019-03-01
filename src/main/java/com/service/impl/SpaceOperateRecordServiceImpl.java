package com.service.impl;

import com.dao.SpaceOperateRecordDao;
import com.entities.SpaceOperateRecordPO;
import com.service.SpaceOperateRecordService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SpaceOperateRecordServiceImpl")
@Scope("prototype")
public class SpaceOperateRecordServiceImpl implements SpaceOperateRecordService {
    @Resource
    private SpaceOperateRecordDao spaceOperateRecordDao;

    @Override
    public List<SpaceOperateRecordPO> getAllSpaceOperateRecord() {
        return spaceOperateRecordDao.getAllSpaceOperateRecord();
    }
}
