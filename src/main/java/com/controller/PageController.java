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
import java.util.ArrayList;
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

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long pageId = Long.parseLong(request.getParameter("pageId"));
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

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        //获取空间列表信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());
        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());

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

    //根据页面id返回空间版本列表
    @RequestMapping(value = "/getPageVersionsByPageId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getPageVersionsByPageId(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);
        long pageId = Long.parseLong(request.getParameter("pageId"));
        //获取页面信息
        PagePO pagePO = pageService.getPageByPageId(pageId);

        //获取当前空间信息
        SpacePO spacePO = spaceService.getSpaceById(pagePO.getSpaceID());

        //获取空间列表信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        List<PageOperateRecordPO> pageOperateRecordPOS = pageService.getLastSevenPageOperateRecordsByPageId(pageId);
        List<Map> pageRecords = new ArrayList<Map>();

        for(int i=0;i<pageOperateRecordPOS.size();i++){
            Map map = new HashMap();
            map.put("afterVersionId",pageOperateRecordPOS.get(i).getAfterVersionId());
            map.put("operatorContent",pageOperateRecordPOS.get(i).getOperatorContent());
            map.put("operatorTime",pageOperateRecordPOS.get(i).getOperatorTime());
            map.put("operatorName",userService.getUserById(pageOperateRecordPOS.get(i).getOperatorId()).getName());
            pageRecords.add(i,map);
        }

        mav = pageService.packagePage(userPO,null,spacePO,spacePOS,pagePOS,pagePO,null,null);

        //判断当前操作者是否有编辑权限
        if(pageService.haswritePermission(spacePO,pagePO,userPO.getId())){
            mav.addObject("writePermission",1);
        }else {
            mav.addObject("writePermission",0);
        }
        mav.setViewName("pageHistory");
        mav.addObject("pageRecords",pageRecords);
        return mav;
    }

    //根据页面id和版本号进行版本回滚
    @RequestMapping(value = "/pageVersionReturn", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView pageVersionReturn(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO) ((Map) request.getSession().getAttribute("SESSION_USERNAME")).get(userName);
        long pageId = Long.parseLong(request.getParameter("pageId"));
        double version = Double.parseDouble(request.getParameter("version"));

        pageService.pageReturnVersion(pageId,version,userPO.getId());

        //获取页面信息
        PagePO pagePO = pageService.getPageByPageId(pageId);
        //获取当前空间信息
        SpacePO spacePO = spaceService.getSpaceById(pagePO.getSpaceID());

        //获取空间列表信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        List<PageOperateRecordPO> pageOperateRecordPOS = pageService.getLastSevenPageOperateRecordsByPageId(pageId);
        List<Map> pageRecords = new ArrayList<Map>();

        PageDetailPO pageDetailPO = pageService.getCurPageById(pageId);

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

    //根据页面id来查看页面读写权限
    @RequestMapping(value = "/getPageRightByPageId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getPageRightByPageId(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long pageId = Long.parseLong(request.getParameter("pageId"));
        //获取页面信息
        PagePO pagePO = pageService.getPageByPageId(pageId);

        //获取当前空间信息
        SpacePO spacePO = spaceService.getSpaceById(pagePO.getSpaceID());

        //获取空间列表信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        mav = pageService.packagePage(userPO,null,spacePO,spacePOS,pagePOS,pagePO,null,null);

        base base1 = new base();
        List<Map> userRight = new ArrayList<Map>();
        //所有人可读，所有人可写，类型为1
        if(pagePO.getWriteID().equals("-1")){
            mav.addObject("type",1);
        }else {
            //获取读取权限列表，读>写,读可能为-1
            List<Long> writeIds = base1.stringToLongList(pagePO.getWriteID());
            mav.addObject("writeIds",writeIds);
            //所有人可读，部分人可写，类型为2
            mav.addObject("type",2);
            if(pagePO.getReadID().equals("-1")){
                for(int i=0;i<writeIds.size();i++){
                    Map map = new HashMap();
                    map.put("userName",userService.getUserById(writeIds.get(i)).getName());
                    map.put("readId",1);
                    map.put("writeId",1);
                    userRight.add(map);
                }
            }else {
                //部分人可读，部分人可写，类型为3
                mav.addObject("type",3);
                List<Long> readIds = base1.stringToLongList(pagePO.getReadID());
                readIds = base1.findReadOnly(readIds,writeIds);
                for(int i=0;i<writeIds.size();i++){
                    Map map = new HashMap();
                    map.put("userName",userService.getUserById(writeIds.get(i)).getName());
                    map.put("readId",1);
                    map.put("writeId",1);
                    userRight.add(map);
                }
                for(int i=0;i<readIds.size();i++){
                    Map map = new HashMap();
                    map.put("userName",userService.getUserById(writeIds.get(i)).getName());
                    map.put("readId",1);
                    map.put("writeId",0);
                    userRight.add(map);
                }
            }
        }
        mav.addObject("userRight",userRight);
        mav.setViewName("pageRights");
        return mav;
    }

    /*//根据页面id来修改页面读写权限
    @RequestMapping(value = "/updatePageRight", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView updatePageRight(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        //获取登录账号
        String userName = request.getParameter("userName");
        UserPO userPO = (UserPO)((Map)request.getSession().getAttribute("SESSION_USERNAME")).get(userName);

        long pageId = Long.parseLong(request.getParameter("pageId"));
        //获取页面信息
        PagePO pagePO = pageService.getPageByPageId(pageId);

        //获取当前空间信息
        SpacePO spacePO = spaceService.getSpaceById(pagePO.getSpaceID());

        //获取空间列表信息
        List<SpacePO> spacePOS = spaceService.getSpacesById(userPO.getId());

        //获取该空间页面信息
        List<PagePO> pagePOS = pageService.getPagesBySpaceId(spacePO.getId());
        mav = pageService.packagePage(userPO,null,spacePO,spacePOS,pagePOS,pagePO,null,null);

        //获取需要修改的用户姓名、读写权限
        String updateUserName = request.getParameter("updateUserName");
        String updateRead = request.getParameter("updateRead");
        String updateWrite = request.getParameter("updateWrite");


        return mav;
    }*/

}
