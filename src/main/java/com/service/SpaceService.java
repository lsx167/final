package com.service;

import com.entities.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface SpaceService {
    //查询所有空间信息
    List<SpacePO> getAllSpace();

    //根据用户id返回主空间信息
    SpacePO getMainSpaceById(long id);

    //根据用户id返回空间列表
    List<SpacePO> getSpacesById(long id);

    //根据空间id返回空间信息
    SpacePO getSpaceById(long id);

    //根据空间名字返回空间信息
    SpacePO getSpaceBySpaceName(String name);


    //根据搜索内容获取分页记录
    LimitPageList selectPageBySearch(Integer pageNow, String content);

    /**
     * 获取数据库总的记录数
     */
    int getCountBySearch(String content);

    //创建空间
    Long createSpace(SpacePO spacePO);

    //包装空间页面方法
    //操作者
    ModelAndView packagePage(UserPO userPO, UserPO originUserPO, SpacePO spacePO, List<SpacePO> spacePOS,
                             List<PagePO>pagePOS, List<SpaceOperateRecordPO> spaceOperateRecordPOS);

    //添加页面时，更新空间信息
    void updateSpaceCreatePage(long spaceId,long pageId);

    //检查用户是否有该页面的读权限
    boolean hasReadPermission(SpacePO spacePO,long userId);

    //检查用户是否有该页面的写权限
    boolean haswritePermission(SpacePO spacePO,long userId);

    //包装空间权限
    ModelAndView packagePageRight(ModelAndView mav,SpacePO spacePO);

    //更改空间权限
    SpacePO updateSpaceRightType(SpacePO spacePO,Integer type,UserPO userPO);

    //添加用户权限
    SpacePO addSpaceRight(SpacePO spacePO,String updateUserName,Long operatorId,ModelAndView mav);

    //修改空间权限
    SpacePO updateSpaceRight(Long spaceId, String userName, Long operatorId, String read, String write);
}
