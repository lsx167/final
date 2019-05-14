package com.dao;

import com.entities.PageOperateRecordPO;

import java.util.List;

public interface PageOperateRecordDao {
    //根据页面id查询最近1条操作记录
    PageOperateRecordPO getLastPageOperateRecord(Long id);
}
