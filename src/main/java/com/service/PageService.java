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

    //包装页面方法
    ModelAndView packagePage(UserPO userPO, UserPO originUserPO, SpacePO spacePO, List<SpacePO> spacePOS,
                             List<PagePO>pagePOS, PagePO pagePO, PageDetailPO pageDetailPO);
}
