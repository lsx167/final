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
import com.service.base;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/space")
public class SpaceController {
    private static final Logger logger = Logger.getLogger(SpaceController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private PageService pageService;
    @Autowired
    private SpaceOperateRecordService spaceOperateRecordService;

    //根据搜索内容返回空间列表
    @RequestMapping(value = "/getSpaceBySearchContent", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getSpaceBySearchContent(HttpServletRequest request, HttpServletResponse response) {
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        ModelAndView mav = new ModelAndView();
        String content = request.getParameter("spaceContent");
        String currPage = request.getParameter("pageNow");
        Integer pagenow = 1;
        if(!(currPage == "" || currPage == null)){
            pagenow = Integer.valueOf(currPage);
        }

        Page page = new Page(0,1);
        List<SpacePO> spacePOList = new ArrayList<SpacePO>();

        if(null == content || content == ""){
            mav.setViewName("search");
            mav.addObject("spacePOList",spacePOList);
            mav.addObject("pageNow",pagenow);
            mav.addObject("page",page);
            mav.addObject("userPO",userPO);
            return mav;
        }

        LimitPageList limitPageList = spaceService.selectPageBySearch(pagenow,content);
        page = limitPageList.getPage();
        page.setTotalPageCount(page.getTotalPageCount());

        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());

        //强制类型转换
        spacePOList = (List<SpacePO>) limitPageList.getList();
        mav.setViewName("search");
        mav.addObject("spacePOList",spacePOList);
        mav.addObject("pageNow",pagenow);
        mav.addObject("page",page);
        mav.addObject("content",content);
        mav.addObject("userPO",userPO);
        mav.addObject("lastThree",lastThree);
        return mav;
    }

    //根据空间id返回空间信息
    @RequestMapping(value = "/getSpaceBySpaceId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getSpaceBySpaceId(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();
        base general = new base();

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long spaceId = Long.parseLong(request.getParameter("spaceId"));

        SpacePO spacePO = spaceService.getSpaceById(spaceId);

        //获取空间创始人信息
        UserPO originUserPO = userService.getUserById(spacePO.getOriginatorID());

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());

        //判断当前用户是否有目标空间的权限
        if(!spaceService.hasReadPermission(spacePO,userPO.getId())){
            mav.setViewName("noPermission");
            mav.addObject("userPO",userPO);
            mav.addObject("spacePO",spacePO);
            mav.addObject("spacePOS",spacePOS);
            return mav;
        }

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        pagePOS = pageService.pageDfs(pagePOS);
        //获取该空间最近5条操作记录
        List<SpaceOperateRecordPO> spaceOperateRecordPOS = spaceOperateRecordService.getLastFiveSpaceOperateRecord(spacePO.getId());

        mav = spaceService.packagePage(userPO,originUserPO,spacePO,spacePOS,pagePOS,spaceOperateRecordPOS);
        if(userPO.getId() == originUserPO.getId()){
            mav.addObject("originPermission",1);
        }else {
            mav.addObject("originPermission",0);
        }
        if(spaceService.haswritePermission(spacePO,userPO.getId())){
            mav.addObject("writePermission",1);
        }
        mav.addObject("lastThree",lastThree);
        return mav;
    }

    //创建空间
    @RequestMapping(value = "/createSpace", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView createSpace(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();
        String spaceName = request.getParameter("spaceName");
        String spaceDescribe = request.getParameter("spaceDescribe");
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());

        //根据空间名称去查询是否存在同名空间，若存在则创建失败，校验之后移到前端
        SpacePO spacePO = spaceService.getSpaceBySpaceName(spaceName);
        if(spacePO != null){
            System.out.println("该空间已存在");
            mav.addObject("lastThree",lastThree);
            mav.setViewName("main");
            return mav;
        }
        else{
            spacePO = new SpacePO();
            spacePO.setName(spaceName);
            spacePO.setOriginatorID(userPO.getId());
            spacePO.setIsMain(0);
            spacePO.setType(1);
            spacePO.setReadID("-1");
            spacePO.setWriteID("-1");
            spacePO.setChildPageID("-1");
            spacePO.setDescribe(spaceDescribe);
            spacePO.setExpired(false);
            spaceService.createSpace(spacePO);

            //获取空间信息
            List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
            //获取该空间页面信息
            List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
            pagePOS = pageService.pageDfs(pagePOS);

            //添加空间操作记录
            spaceOperateRecordService.createSpaceOperate(spacePO.getId(),userPO.getId(),spacePO.getName());

            //获取该空间最近5条操作记录
            List<SpaceOperateRecordPO> spaceOperateRecordPOS = spaceOperateRecordService.getLastFiveSpaceOperateRecord(spacePO.getId());

            mav = spaceService.packagePage(userPO,userPO,spacePO,spacePOS,pagePOS,spaceOperateRecordPOS);
            mav.addObject("originPermission",1);
            mav.addObject("writePermission",1);
            mav.addObject("lastThree",lastThree);
            return mav;
        }
    }

    //返回登录账号的主空间
    @RequestMapping(value = "/getMainSpace", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getMainSpace(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        //获取主空间信息
        SpacePO spacePO = spaceService.getMainSpaceById(userPO.getId());
        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        pagePOS = pageService.pageDfs(pagePOS);
        //获取该空间最近5条操作记录
        List<SpaceOperateRecordPO> spaceOperateRecordPOS = spaceOperateRecordService.getLastFiveSpaceOperateRecord(spacePO.getId());
        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());
        mav = spaceService.packagePage(userPO,userPO,spacePO,spacePOS,pagePOS,spaceOperateRecordPOS);
        mav.addObject("originPermission",1);
        mav.addObject("writePermission",1);
        mav.addObject("lastThree",lastThree);
        return mav;
    }

    //根据空间id来查看空间读写权限
    @RequestMapping(value = "/getSpaceRightById", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getSpaceRightById(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long spaceId = Long.parseLong(request.getParameter("spaceId"));

        SpacePO spacePO = spaceService.getSpaceById(spaceId);

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        pagePOS = pageService.pageDfs(pagePOS);
        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());

        mav = spaceService.packagePage(userPO,null,spacePO,spacePOS,pagePOS,null);

        mav = spaceService.packagePageRight(mav,spacePO);
        mav.addObject("lastThree",lastThree);
        return mav;
    }

    //根据空间id来修改读写权限
    @RequestMapping(value = "/updateSpaceRight", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView updateSpaceRight(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long spaceId = Long.parseLong(request.getParameter("spaceId"));

        SpacePO spacePO = spaceService.getSpaceById(spaceId);

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        pagePOS = pageService.pageDfs(pagePOS);
        mav = spaceService.packagePage(userPO,null,spacePO,spacePOS,pagePOS,null);
        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());

        //获取需要修改的用户姓名、读写权限
        String updateUserName = request.getParameter("updateUserName");
        String updateRead = request.getParameter("updateRead");
        String updateWrite = request.getParameter("updateWrite");

        spacePO = spaceService.updateSpaceRight(spacePO.getId(),updateUserName,userPO.getId(),updateRead,updateWrite);

        mav = spaceService.packagePageRight(mav,spacePO);
        mav.addObject("lastThree",lastThree);

        return mav;
    }

    //修改空间权限类型
    @RequestMapping(value = "/updateSpaceRightType", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView updateSpaceRightType(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long spaceId = Long.parseLong(request.getParameter("spaceId"));

        SpacePO spacePO = spaceService.getSpaceById(spaceId);

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        pagePOS = pageService.pageDfs(pagePOS);
        mav = spaceService.packagePage(userPO,null,spacePO,spacePOS,pagePOS,null);
        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());

        int type = Integer.parseInt(request.getParameter("type"));
        spacePO = spaceService.updateSpaceRightType(spacePO,type,userPO);
        mav = spaceService.packagePageRight(mav,spacePO);
        mav.addObject("lastThree",lastThree);

        return mav;
    }

    //添加空间用户权限
    @RequestMapping(value = "/addSpaceRightUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView addSpaceRightUser(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long spaceId = Long.parseLong(request.getParameter("spaceId"));

        SpacePO spacePO = spaceService.getSpaceById(spaceId);

        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        pagePOS = pageService.pageDfs(pagePOS);
        mav = spaceService.packagePage(userPO,null,spacePO,spacePOS,pagePOS,null);
        //获得最近访问的三个空间
        List<SpacePO> lastThree = spaceService.getLastThreeSpace(userPO.getId());

        String updateUserName = request.getParameter("updateUserName");

        spacePO = spaceService.addSpaceRight(spacePO,updateUserName,userPO.getId(),mav);
        mav = spaceService.packagePageRight(mav,spacePO);
        mav.addObject("lastThree",lastThree);
        return mav;
    }
}
