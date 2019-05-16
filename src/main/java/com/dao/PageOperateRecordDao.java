package com.dao;

import com.entities.PageOperateRecordPO;

import java.util.List;

public interface PageOperateRecordDao {
    //根据页面id查询最近1条操作记录
    PageOperateRecordPO getLastPageOperateRecord(Long id);

    //修改页面内容后，增加页面操作记录
    Long updatePageContent(PageOperateRecordPO pageOperateRecordPO);
}
