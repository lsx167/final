package com.dao;

import com.entities.PagePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PageDao {
    //根据空间id返回该空间的页面信息
    List<PagePO> getPagesBySpaceId(long id);

    //根据空间id返回该空间的页面信息
    PagePO getPageByPageId(long id);

    //修改页面版本号
    void updatePageVersion(@Param("id")long id, @Param("versionId") double versionId);

    //添加页面
    long insertNewPage(PagePO pagePO);

    //更新页面记录
    void updatePageInfo(PagePO pagePO);
}
