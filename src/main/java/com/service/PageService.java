package com.service;

import com.entities.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface PageService {
    //根据空间id返回该空间的页面信息
    List<PagePO> getPagesBySpaceId(long id);

    //根据页面id查询页面信息
    PagePO getPageByPageId(long id);

    //根据页面id返回最近的存在空间
    PageDetailPO getCurPageById(Long id);

    //根据页面id返回最近的操作记录
    PageOperateRecordPO getLastPageRecordById(Long id);

    //包装页面方法
    ModelAndView packagePage(UserPO userPO, UserPO originUserPO, SpacePO spacePO, List<SpacePO> spacePOS, List<PagePO>pagePOS,
                             PagePO pagePO, PageDetailPO pageDetailPO, PageOperateRecordPO pageOperateRecordPO);

    //更新页面信息
    void updatePageContent(long pageId,String pageContent,long operatorId);

    //新增根页面
    Long insertNewRootPage(SpacePO spacePO,String pagename,long originatorID);

    //新增子页面
    Long insertNewChildPage(PagePO pagePO,String pagename,long originatorID);

    //新增根页面详细信息
    long insertNewRootPageDetail(long pageId, String pageContent);

    //新增根页面操作记录
    long insertNewRootPageOperateRecord(PagePO pagePO);

    //检查用户是否有该页面的读权限
    boolean hasReadPermission(SpacePO spacePO,PagePO pagePO,long userId);

    //检查用户是否有该页面的写权限
    boolean haswritePermission(SpacePO spacePO,PagePO pagePO,long userId);

    //根据页面id获取最近的七条操作记录页面操作记录
    List<PageOperateRecordPO> getLastSevenPageOperateRecordsByPageId(Long id);

    //根据页面id和版本号进行版本回滚
    PagePO pageReturnVersion(Long id,double version,long userId);
}
