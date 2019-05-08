package com.dao;

import com.entities.SpaceOperateRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpaceOperateRecordDao {
    //查询所有空间操作信息
    List<SpaceOperateRecordPO> getAllSpaceOperateRecord();

    //添加空间操作记录
    Long insertSpaceOperate(SpaceOperateRecordPO spaceOperateRecordPO);
}
