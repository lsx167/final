package com.service;

import com.entities.LimitPageList;
import com.entities.SpacePO;

import java.util.List;

public interface SpaceService {
    //查询所有空间信息
    List<SpacePO> getAllSpace();

    //根据用户id返回主空间信息
    SpacePO getMainSpaceById(long id);

    //根据用户id返回空间信息
    List<SpacePO> getSpacesById(long id);

    //根据空间id返回空间信息
    SpacePO getSpaceBySearchContent(long id);

    //根据搜索内容获取分页记录
    LimitPageList selectPageBySearch(Integer pageNow, String content);

    /**
     * 获取数据库总的记录数
     */
    int getCountBySearch(String content);
}
