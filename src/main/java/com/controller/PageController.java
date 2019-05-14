package com.controller;

import com.entities.PageDetailPO;
import com.entities.PagePO;
import com.entities.SpacePO;
import com.entities.UserPO;
import com.service.PageService;
import com.service.SpaceOperateRecordService;
import com.service.SpaceService;
import com.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/page")
public class PageController {
    private static final Logger logger = Logger.getLogger(SpaceController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private PageService pageService;
    @Autowired
    private SpaceOperateRecordService spaceOperateRecordService;

    //根据页面id返回空间信息
    @RequestMapping(value = "/getPageByPageId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getSpaceBySpaceId(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        //理论上不存在找不到id的情况
        /*if(request.getParameter("spaceId").equals(null)||request.getParameter("spaceId") == ""){
            mav.setViewName("searchNull");
            return mav;
        }*/
        long pageId = Long.parseLong(request.getParameter("pageId"));

        PagePO pagePO = pageService.getPageByPageId(pageId);
        PageDetailPO pageDetailPO = pageService.getCurPageById(pageId);

        SpacePO spacePO = spaceService.getSpaceBySearchContent(pagePO.getSpaceID());

        //获取用户信息
        UserPO userPO = userService.getUserById(spacePO.getOriginatorID());
        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());

        UserPO originUserPO = userService.getUserById(pagePO.getOriginatorID());

        mav = pageService.packagePage(userPO,originUserPO,spacePO,spacePOS,pagePOS,pagePO,pageDetailPO);

        return mav;
    }
}
