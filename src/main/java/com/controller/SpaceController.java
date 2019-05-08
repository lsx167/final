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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 查询所有空间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/allspace", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllspace(HttpServletRequest request, HttpServletResponse response) {
        List<SpacePO> spacePOS = spaceService.getAllSpace();
        return (spacePOS.get(1).getId()+spacePOS.get(1).getName()+spacePOS.get(1).getOriginatorID()+spacePOS.get(1).getChildPageID());
    }

    /**
     * 根据用户id返回主空间信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getMainSpaceById", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMainSpaceById(HttpServletRequest request, HttpServletResponse response) {
        SpacePO spacePO = spaceService.getMainSpaceById(10001);
        return spacePO.getName()+spacePO.getId()+spacePO.getIsMain();
    }

    //模糊搜索空间列表
    @RequestMapping(value = "/getSpacesBySearch", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getSpacesBySearch(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // TODO: 2019/5/2
        return null;
    }

    //根据搜索内容返回空间列表
    @RequestMapping(value = "/getSpaceBySearchContent", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getSpaceBySearchContent(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        String content = request.getParameter("spaceContent");

        Integer pagenow = 1;
        Page page = new Page(0,1);
        List<SpacePO> spacePOList = new ArrayList<SpacePO>();

        if(null == content || content == ""){
            mav.setViewName("search");
            mav.addObject("spacePOList",spacePOList);
            mav.addObject("pageNow",pagenow);
            mav.addObject("page",page);
            return mav;
        }

        LimitPageList limitPageList = spaceService.selectPageBySearch(pagenow,content);
        page = limitPageList.getPage();
        page.setTotalPageCount(page.getTotalPageCount());
        //强制类型转换
        spacePOList = (List<SpacePO>) limitPageList.getList();
        mav.setViewName("search");
        mav.addObject("spacePOList",spacePOList);
        mav.addObject("pageNow",pagenow);
        mav.addObject("page",page);
        return mav;
    }

    //根据空间id返回空间信息
    @RequestMapping(value = "/getSpaceBySpaceId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getSpaceBySpaceId(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        //理论上不存在找不到id的情况
        /*if(request.getParameter("spaceId").equals(null)||request.getParameter("spaceId") == ""){
            mav.setViewName("searchNull");
            return mav;
        }*/
        long spaceId = Long.parseLong(request.getParameter("spaceId"));

        SpacePO spacePO = spaceService.getSpaceBySearchContent(spaceId);
        /*if(null==spacePO){
            mav.setViewName("searchNull");
            return mav;
        }*/

        //获取用户信息
        UserPO userPO = userService.getUserById(spacePO.getOriginatorID());
        //获取空间信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        //获取该空间最近5条操作记录
        List<SpaceOperateRecordPO> spaceOperateRecordPOS = spaceOperateRecordService.getLastFiveSpaceOperateRecord(spacePO.getId());


        /*mav.setViewName("main");
        mav.addObject("userPO",userPO);
        mav.addObject("spacePO",spacePO);
        mav.addObject("spacePOS",spacePOS);
        mav.addObject("pagePOS",pagePOS);
        mav.addObject("spaceOperateRecordPOS",spaceOperateRecordPOS);*/
        mav = spaceService.packagePage(userPO,spacePO,spacePOS,pagePOS,spaceOperateRecordPOS);

        return mav;
    }

    //创建空间
    @RequestMapping(value = "/createSpace", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView createSpace(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();
        String spaceName = request.getParameter("spaceName");
        String spaceDescribe = request.getParameter("spaceDescribe");
        UserPO userPO = (UserPO) httpSession.getAttribute("userPO");

        //根据空间名称去查询是否存在同名空间，若存在则创建失败，校验之后移到前端
        // todo
        SpacePO spacePO = spaceService.getSpaceBySpaceName(spaceName);
        if(spacePO != null){
            System.out.println("该空间已存在");
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


            //添加空间操作记录
            spaceOperateRecordService.createSpaceOperate(spacePO.getId(),userPO.getId(),spacePO.getName());

            //获取该空间最近5条操作记录
            List<SpaceOperateRecordPO> spaceOperateRecordPOS = spaceOperateRecordService.getLastFiveSpaceOperateRecord(spacePO.getId());

            /*mav.setViewName("main");
            mav.addObject("userPO",userPO);
            mav.addObject("spacePO",spacePO);
            mav.addObject("spacePOS",spacePOS);
            mav.addObject("pagePOS",pagePOS);*/
            mav = spaceService.packagePage(userPO,spacePO,spacePOS,pagePOS,spaceOperateRecordPOS);

            return mav;
        }
    }
}
