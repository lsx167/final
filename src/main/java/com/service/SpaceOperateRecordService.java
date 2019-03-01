package com.service;

import com.entities.SpaceOperateRecordPO;

import java.util.List;

public interface SpaceOperateRecordService {
    //查询所有空间操作记录
    List<SpaceOperateRecordPO> getAllSpaceOperateRecord();
}
