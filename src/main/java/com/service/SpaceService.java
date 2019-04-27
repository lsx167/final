package com.service;

import com.entities.SpacePO;

import java.util.List;

public interface SpaceService {
    //查询所有空间信息
    List<SpacePO> getAllSpace();

    //根据id返回主空间信息
    SpacePO getMainSpaceById(long id);
}
