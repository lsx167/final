package com.dao;

import com.entities.PageOperateRecordPO;

import java.util.List;

public interface PageOperateRecordDao {
    //根据空间id查询最近5条操作记录
    List<PageOperateRecordPO> getLastFivePageOperateRecord(Long id);
}
