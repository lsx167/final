package com.dao;

import com.entities.SpaceOperateRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpaceOperateRecordDao {
    //查询所有空间操作信息
    List<SpaceOperateRecordPO> getAllSpaceOperateRecord();

    //添加空间操作记录
    Long insertSpaceOperate(SpaceOperateRecordPO spaceOperateRecordPO);

    //根据空间id查询最近5条操作记录
    List<SpaceOperateRecordPO> getLastFiveSpaceOperateRecord(Long id);

    //根据用户id查询最近访问的3个空间
    List<SpaceOperateRecordPO> getLastThreeSpaceById(@Param("operatorID")Long operatorID);
}
