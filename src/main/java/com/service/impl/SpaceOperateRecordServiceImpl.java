package com.service.impl;

import com.dao.SpaceOperateRecordDao;
import com.entities.SpaceOperateRecordPO;
import com.service.SpaceOperateRecordService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.service.base;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public Long insertSpaceOperate(SpaceOperateRecordPO spaceOperateRecordPO) {
        spaceOperateRecordDao.insertSpaceOperate(spaceOperateRecordPO);
        return spaceOperateRecordPO.getId();
    }

    @Override
    public Long createSpaceOperate(Long spaceId, Long operatorId, String spaceName){
        /*Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);*/

        SpaceOperateRecordPO spaceOperateRecordPO = new SpaceOperateRecordPO();
        spaceOperateRecordPO.setSpaceId(spaceId);
        spaceOperateRecordPO.setOperatorId(operatorId);
        spaceOperateRecordPO.setOperatorTime(new base().getCurrTime());
        spaceOperateRecordPO.setType(1);
        spaceOperateRecordPO.setOperatorContent("创建空间-"+spaceName);
        spaceOperateRecordPO.setExpired(false);

        spaceOperateRecordDao.insertSpaceOperate(spaceOperateRecordPO);
        return spaceOperateRecordPO.getId();
    }

    @Override
    public List<SpaceOperateRecordPO> getLastFiveSpaceOperateRecord(Long id) {
        return spaceOperateRecordDao.getLastFiveSpaceOperateRecord(id);
    }

    @Override
    public Long createPageOperate(long spaceId, long operatorID, String pageName) {

        SpaceOperateRecordPO spaceOperateRecordPO = new SpaceOperateRecordPO();
        spaceOperateRecordPO.setSpaceId(spaceId);
        spaceOperateRecordPO.setOperatorId(operatorID);
        spaceOperateRecordPO.setOperatorTime(new base().getCurrTime());
        spaceOperateRecordPO.setType(3);
        spaceOperateRecordPO.setOperatorContent("创建页面-"+pageName);
        spaceOperateRecordPO.setExpired(false);

        spaceOperateRecordDao.insertSpaceOperate(spaceOperateRecordPO);
        return spaceOperateRecordPO.getId();    }
}
