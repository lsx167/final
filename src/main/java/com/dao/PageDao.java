package com.dao;

import com.entities.PagePO;

import java.util.List;

public interface PageDao {
    //根据空间id返回该空间的页面信息
    List<PagePO> getPagesBySpaceId(long id);

    //根据空间id返回该空间的页面信息
    PagePO getPageByPageId(long id);
}
