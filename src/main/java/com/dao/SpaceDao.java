package com.dao;

import com.entities.SpacePO;

import java.util.List;

public interface SpaceDao {
    //查询所有空间信息
    List<SpacePO> getAllSpaces();
}
