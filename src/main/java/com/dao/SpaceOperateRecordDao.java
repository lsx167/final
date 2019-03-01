package com.dao;

import com.entities.SpaceOperateRecordPO;

import java.util.List;

public interface SpaceOperateRecordDao {
    //查询所有空间操作信息
    List<SpaceOperateRecordPO> getAllSpaceOperateRecord();
}
