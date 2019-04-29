package com.dao;

import com.entities.SpacePO;

import java.util.List;

public interface SpaceDao {
    //查询所有空间信息
    List<SpacePO> getAllSpaces();

    //根据id返回主空间信息
    SpacePO getMainSpaceById(long id);

    //根据id返回空间信息
    List<SpacePO> getSpacesById(long id);
}
