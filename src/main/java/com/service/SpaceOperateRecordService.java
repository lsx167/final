package com.service;

import com.dao.SpaceOperateRecordDao;
import com.entities.SpaceOperateRecordPO;

import java.util.Date;
import java.util.List;

public interface SpaceOperateRecordService {
    //查询所有空间操作记录
    List<SpaceOperateRecordPO> getAllSpaceOperateRecord();

    //添加空间操作记录
    Long insertSpaceOperate(SpaceOperateRecordPO spaceOperateRecordPO);

    //创建空间时，操作记录
    Long createSpaceOperate(Long spaceId, Long operatorId,String spaceName);

    //根据空间id查询最近5条操作记录
    List<SpaceOperateRecordPO> getLastFiveSpaceOperateRecord(Long id);

    //创建页面时，添加空间操作记录
    Long createPageOperate(long spaceId,long operatorID,String pageName);
}
