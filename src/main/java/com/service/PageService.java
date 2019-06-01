package com.service;

import com.entities.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Queue;

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

    ModelAndView packagePageRight(ModelAndView mav,PagePO pagePO);
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

    //修改页面权限
    PagePO updatePageRight(Long pageId, String userName, Long operatorId, String read, String write);

    //将List<Long> 放进map中
    void putRightIdsIntoMap(List<Long> Ids,List<Map> maps,int isRead);

    //页面dfs
    List<PagePO> pageDfs(List<PagePO> pagePOS);

    //辅助dfs1
    void pageDfsAssistant1(Queue<PagePO> queue,List<PagePO> pagePOS);

    //辅助dfs1
    Queue<PagePO> pageDfsAssistant2(List<Long> pagePOS);

    //更改页面权限
    PagePO updatePageRightType(PagePO pagePO,Integer type,UserPO userPO);

    //添加用户权限
    PagePO addPageRight(PagePO pagePO,String updateUserName,Long operatorId,ModelAndView mav);
}
