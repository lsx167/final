package com.controller;

import com.entities.*;
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
import javax.servlet.http.HttpSession;
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

        PageOperateRecordPO pageOperateRecordPO = pageService.getLastPageRecordById(pageId);

        mav = pageService.packagePage(userPO,originUserPO,spacePO,spacePOS,pagePOS,pagePO,pageDetailPO,pageOperateRecordPO);

        return mav;
    }

    //修改页面内容
    @RequestMapping(value = "/updatePageContent", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView updatePageContent(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        long pageId = Long.parseLong(request.getParameter("pageId"));
        String pageContent = request.getParameter("pageContent");

        UserPO userPO = (UserPO) httpSession.getAttribute("userPO");

        pageService.updatePageContent(pageId,pageContent,userPO.getId());

        return getSpaceBySpaceId(request,response);
    }

    //创建根页面
    @RequestMapping(value = "/createNewRootPage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView createNewRootPage(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
        ModelAndView mav = new ModelAndView();
        String spaceName = request.getParameter("spaceName");
        String pageName = request.getParameter("pageName");
        String pageContent = request.getParameter("pageContent");

        UserPO userPO = (UserPO) httpSession.getAttribute("userPO");
        SpacePO spacePO = spaceService.getSpaceBySpaceName(spaceName);

        //更新page表
        Long pageId = pageService.insertNewRootPage(spacePO,pageName,userPO.getId());
        PagePO pagePO = pageService.getPageByPageId(pageId);

        //更新pageDetail表
        pageService.insertNewRootPageDetail(pageId,pageContent);
        PageDetailPO pageDetailPO = pageService.getCurPageById(pageId);

        //更新pageOperateRecord表
        pageService.insertNewRootPageOperateRecord(pagePO);
        PageOperateRecordPO pageOperateRecordPO = pageService.getLastPageRecordById(pageId);

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());

        mav = pageService.packagePage(userPO,userPO,spacePO,spacePOS,pagePOS,pagePO,pageDetailPO,pageOperateRecordPO);

        return mav;
    }

}
