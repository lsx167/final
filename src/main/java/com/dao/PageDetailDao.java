package com.dao;

import com.entities.PageDetailPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PageDetailDao {
    //根据页面id返回最近的存在空间
    PageDetailPO getCurPageById(Long id);

    //根据页面id假删记录
    void deletePageRecord(@Param("id")long id);

    //添加页面详细信息、页面版本更新
    Long insertPageVersion(PageDetailPO pageDetailPO);

}
