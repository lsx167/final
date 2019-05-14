package com.service.impl;

import com.dao.PageDao;
import com.dao.PageDetailDao;
import com.entities.PageDetailPO;
import com.entities.PagePO;
import com.entities.SpacePO;
import com.entities.UserPO;
import com.service.PageService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Service("PageServiceImpl")
@Scope("prototype")
public class PageServiceImpl implements PageService {
    @Resource
    private PageDao pageDao;

    @Resource
    private PageDetailDao pageDetailDao;

    @Override
    public List<PagePO> getPagesBySpaceId(long id) {
        return pageDao.getPagesBySpaceId(id);
    }

    @Override
    public PagePO getPageByPageId(long id) {
        return pageDao.getPageByPageId(id);
    }

    @Override
    public PageDetailPO getCurPageById(Long id) {
        return pageDetailDao.getCurPageById(id);
    }

    @Override
    public ModelAndView packagePage(UserPO userPO, UserPO originUserPO, SpacePO spacePO, List<SpacePO> spacePOS, List<PagePO> pagePOS, PagePO pagePO, PageDetailPO pageDetailPO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pageItem");
        mav.addObject("userPO",userPO);
        mav.addObject("originUserPO",originUserPO);
        mav.addObject("spacePO",spacePO);
        mav.addObject("spacePOS",spacePOS);
        mav.addObject("pagePOS",pagePOS);
        mav.addObject("pagePO",pagePO);
        mav.addObject("pageDetailPO",pageDetailPO);
        return mav;
    }
}
