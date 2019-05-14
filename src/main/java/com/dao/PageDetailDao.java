package com.dao;

import com.entities.PageDetailPO;

import java.util.List;

public interface PageDetailDao {
    //根据页面id返回最近的存在空间
    PageDetailPO getCurPageById(Long id);


}
