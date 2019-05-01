package com.service;

import com.entities.PagePO;

import java.util.List;

public interface PageService {
    //根据空间id返回该空间的页面信息
    List<PagePO> getPagesBySpaceId(long id);
}
