package com.dao;

import com.entities.SpacePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpaceDao {
    //查询所有空间信息
    List<SpacePO> getAllSpaces();

    //根据id返回主空间信息
    SpacePO getMainSpaceById(long id);

    //根据id返回空间信息
    List<SpacePO> getSpacesById(long id);

    //根据空间id返回空间信息
    SpacePO getSpaceBySearchContent(long id);

    /**
     3      * 根据搜索内容获取分页记录
     4      * @param startPos:从数据库中第几行开始获取
     5      * @param pageSize:获取的条数
     6      * @return 返回pageSize条数据的集合，数据足够多
     7      */
    List<SpacePO> selectPageBySearch(@Param("startPos")Integer startPos, @Param("pageSize")Integer pageSize, @Param("content") String content);
    /**
       * 获取数据库总的记录数
       * @return 返回数据库表的总条数
       */
    Integer getCountBySearch(@Param("content") String content);
}
