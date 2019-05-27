package com.controller;

import com.entities.*;
import com.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //当前登录用户列表
    private static Map editingUserPage = new HashMap();

    //根据页面id返回空间信息
    @RequestMapping(value = "/getPageByPageId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getSpaceBySpaceId(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();
        base general = new base();

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long pageId = Long.parseLong(request.getParameter("pageId"));
        /*//获取操作用户信息
        UserPO userPO = (UserPO) httpSession.getAttribute("userPO");*/
        //获取页面信息
        PagePO pagePO = pageService.getPageByPageId(pageId);

        //获取当前空间信息
        SpacePO spacePO = spaceService.getSpaceById(pagePO.getSpaceID());

        //获取空间列表信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());

        if(!pageService.hasReadPermission(spacePO,pagePO,userPO.getId())){
            mav.setViewName("noPermission");
            mav.addObject("userPO",userPO);
            mav.addObject("spacePO",spacePO);
            mav.addObject("spacePOS",spacePOS);
            mav.addObject("pagePOS",pagePOS);
            return mav;
        }

        PageDetailPO pageDetailPO = pageService.getCurPageById(pageId);

        //获得空间创建者信息
        //UserPO spaceOriginUserPO = userService.getUserById(spacePO.getOriginatorID());

        //获得页面创建者信息
        UserPO pageOriginUserPO1 = userService.getUserById(pagePO.getOriginatorID());

        PageOperateRecordPO pageOperateRecordPO = pageService.getLastPageRecordById(pageId);

        mav = pageService.packagePage(userPO,pageOriginUserPO1,spacePO,spacePOS,pagePOS,pagePO,pageDetailPO,pageOperateRecordPO);

        //判断当前操作者是否有编辑权限
        if(pageService.haswritePermission(spacePO,pagePO,userPO.getId())){
            mav.addObject("writePermission",1);
            request.getSession().setAttribute("editPageUsers",pageId + "+" + userPO.getName());
        }else {
            mav.addObject("writePermission",0);
        }
        return mav;
    }

    //修改页面内容
    @RequestMapping(value = "/updatePageContent", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView updatePageContent(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();
        long pageId = Long.parseLong(request.getParameter("pageId"));
        //获取页面信息
        PagePO pagePO = pageService.getPageByPageId(pageId);
        //获取所属空间信息
        SpacePO spacePO = spaceService.getSpaceById(pagePO.getSpaceID());
        //获取页面修改内容
        String pageContent = request.getParameter("pageContent");

        /*//获取用户信息
        UserPO userPO = (UserPO) httpSession.getAttribute("userPO");*/
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        //获取空间列表信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());

        //todo 写权限需要改到前端
        /*if(!pageService.haswritePermission(spacePO,pagePO,userPO.getId())){
            mav.setViewName("noPermission");
            mav.addObject("userPO",userPO);
            mav.addObject("spacePO",spacePO);
            mav.addObject("spacePOS",spacePOS);
            mav.addObject("pagePOS",pagePOS);
            return mav;
        }*/

        pageService.updatePageContent(pageId,pageContent,userPO.getId());

        return getSpaceBySpaceId(request,response,httpSession);
    }

    //创建根页面
    @RequestMapping(value = "/createNewRootPage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView createNewRootPage(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
        ModelAndView mav = new ModelAndView();
        String spaceName = request.getParameter("spaceName");
        String pageName = request.getParameter("pageName");
        String pageContent = request.getParameter("pageContent");

        /*UserPO userPO = (UserPO) httpSession.getAttribute("userPO");*/
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

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

        //更新空间表
        spaceService.updateSpaceCreatePage(spacePO.getId(),pageId);
        //更新空间操作记录
        spaceOperateRecordService.createPageOperate(spacePO.getId(),userPO.getId(),pageName);

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());

        mav = pageService.packagePage(userPO,userPO,spacePO,spacePOS,pagePOS,pagePO,pageDetailPO,pageOperateRecordPO);

        mav.addObject("writePermission",1);
        return mav;
    }

    //创建子页面
    @RequestMapping(value = "/createNewChildPage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView createNewChildPage(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
        ModelAndView mav = new ModelAndView();
        Long fatherPageId = Long.parseLong(request.getParameter("fatherPageId"));
        String pageName = request.getParameter("pageName");
        String pageContent = request.getParameter("pageContent");

/*
        UserPO userPO = (UserPO) httpSession.getAttribute("userPO");
*/
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        PagePO fatherPage = pageService.getPageByPageId(fatherPageId);
        SpacePO spacePO = spaceService.getSpaceById(fatherPage.getSpaceID());

        //更新page表
        Long pageId = pageService.insertNewChildPage(fatherPage,pageName,userPO.getId());
        PagePO pagePO = pageService.getPageByPageId(pageId);

        //更新pageDetail表
        pageService.insertNewRootPageDetail(pageId,pageContent);
        PageDetailPO pageDetailPO = pageService.getCurPageById(pageId);

        //更新pageOperateRecord表
        pageService.insertNewRootPageOperateRecord(pagePO);
        PageOperateRecordPO pageOperateRecordPO = pageService.getLastPageRecordById(pageId);

        //更新空间表
        spaceService.updateSpaceCreatePage(spacePO.getId(),pageId);

        //更新空间操作记录
        spaceOperateRecordService.createPageOperate(spacePO.getId(),userPO.getId(),pageName);

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());

        mav = pageService.packagePage(userPO,userPO,spacePO,spacePOS,pagePOS,pagePO,pageDetailPO,pageOperateRecordPO);

        mav.addObject("writePermission",1);
        return mav;
    }

    /*//编辑时给后端发送消息
    @RequestMapping(value = "/editMessage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void editMessage(String userName){

        Long pageId;

        if(editUsers.equals(null)){
            editUsers.append(userName);
        } else {
            editUsers.append("+"+userName);
        }
        editingUserPage.put(pageId,editUsers);
        request.getSession().setAttribute("editUsers",editUsers);
    }*/
}
