package com.service.impl;

import com.dao.PageDao;
import com.dao.PageDetailDao;
import com.dao.PageOperateRecordDao;
import com.dao.UserDao;
import com.entities.*;
import com.service.PageService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.service.base;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("PageServiceImpl")
@Scope("prototype")
public class PageServiceImpl implements PageService {
    @Resource
    private PageDao pageDao;

    @Resource
    private UserDao userDao;

    @Resource
    private PageDetailDao pageDetailDao;

    @Resource
    private PageOperateRecordDao pageOperateRecordDao;

    @Autowired
    private UserService userService;

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
    public PageOperateRecordPO getLastPageRecordById(Long id) {
        return pageOperateRecordDao.getLastPageOperateRecord(id);
    }

    @Override
    public ModelAndView packagePage(UserPO userPO, UserPO originUserPO, SpacePO spacePO, List<SpacePO> spacePOS, List<PagePO> pagePOS,
                                    PagePO pagePO, PageDetailPO pageDetailPO ,PageOperateRecordPO pageOperateRecordPO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pageItem");
        mav.addObject("userPO",userPO);
        mav.addObject("originUserPO",originUserPO);
        mav.addObject("spacePO",spacePO);
        mav.addObject("spacePOS",spacePOS);
        mav.addObject("pagePOS",pagePOS);
        mav.addObject("pagePO",pagePO);
        mav.addObject("pageDetailPO",pageDetailPO);
        mav.addObject("pageOperateRecordPO",pageOperateRecordPO);
        return mav;
    }

    @Override
    public ModelAndView packagePageRight(ModelAndView mav,PagePO pagePO) {
        base base1 = new base();
        mav.setViewName("pageRights");

        //更新page的type
        if(pagePO.getReadID().equals("-1") && pagePO.getWriteID().equals("-1")){
            pagePO.setType(1);
        } else if(pagePO.getReadID().equals("-1") && !pagePO.getWriteID().equals("-1")){
            pagePO.setType(2);
        } else {
            pagePO.setType(3);
        }
        pageDao.updatePageInfo(pagePO);

        mav.addObject("type",pagePO.getType());

        List<Map> userRight = new ArrayList<Map>();
        //所有人可读，部分人可写
        if(pagePO.getType() == 2){
            //获取可写人ids
            List<Long> writeIds = base1.stringToLongList(pagePO.getWriteID());
            putRightIdsIntoMap(writeIds,userRight,1);
        }else if(pagePO.getType() == 3){    //部分人可读部分人可写
            //获取可写人ids
            List<Long> writeIds = base1.stringToLongList(pagePO.getWriteID());
            //获取可读人ids
            List<Long> readIds = base1.stringToLongList(pagePO.getReadID());
            putRightIdsIntoMap(writeIds,userRight,1);
            //查找可读不可写的人ids
            readIds = base1.findReadOnly(readIds,writeIds);
            putRightIdsIntoMap(readIds,userRight,0);
        }

        mav.addObject("userRight",userRight);
        return mav;
    }


    @Override
    public void updatePageContent(long pageId, String pageContent,long operatorId) {
        //获取页面
        PagePO pagePO = pageDao.getPageByPageId(pageId);
        PageDetailPO pageDetailPO = pageDetailDao.getCurPageById(pageId);

        double version = ((double)((int)((pageDetailPO.getVersionID()+0.1)*10)))/10;
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        pageDetailPO.setPageContent(pageContent);
        pageDetailPO.setVersionID(version);
        pageDetailPO.setId(0);

        PageOperateRecordPO pageOperateRecordPO = new PageOperateRecordPO();
        pageOperateRecordPO.setPageId(pageId);
        pageOperateRecordPO.setOperatorId(operatorId);
        pageOperateRecordPO.setOperatorTime(dateString);
        pageOperateRecordPO.setType(2);
        pageOperateRecordPO.setOperatorContent("修改页面");
        pageOperateRecordPO.setBeforeVersionId(((double)((int)((version-0.1)*10)))/10);
        pageOperateRecordPO.setAfterVersionId(version);
        pageOperateRecordPO.setExpired(false);

        //页面表更新版本
        pageDao.updatePageVersion(pageId,version);
        //页面详情表假删、添加新纪录
        pageDetailDao.deletePageRecord(pageId);
        pageDetailDao.insertPageVersion(pageDetailPO);
        //添加页面操作记录
        pageOperateRecordDao.insertPageOperateRecord(pageOperateRecordPO);
    }

    @Override
    public Long insertNewRootPage(SpacePO spacePO,String pagename,long originatorID) {
        PagePO pagePO = new PagePO();
        pagePO.setId(0);
        pagePO.setName(pagename);
        pagePO.setOriginatorID(originatorID);
        pagePO.setType(1);
        pagePO.setReadID("-1");
        pagePO.setWriteID("-1");
        pagePO.setSpaceID(spacePO.getId());
        pagePO.setRootPage(true);
        pagePO.setDepth(1);
        pagePO.setFatherPageID(-1);
        pagePO.setChildPageID("-1");
        pagePO.setVersionID(1.0);
        pagePO.setExpired(false);

        pageDao.insertNewPage(pagePO);
        return pagePO.getId();
    }

    @Override
    public Long insertNewChildPage(PagePO pagePO, String pagename, long originatorID) {

        PagePO pagePO1 = new PagePO();
        pagePO1.setId(0);
        pagePO1.setName(pagename);
        pagePO1.setOriginatorID(originatorID);
        pagePO1.setType(1);
        pagePO1.setReadID("-1");
        pagePO1.setWriteID("-1");
        pagePO1.setSpaceID(pagePO.getSpaceID());
        pagePO1.setRootPage(false);
        pagePO1.setDepth(pagePO.getDepth()+1);
        pagePO1.setFatherPageID(pagePO.getId());
        pagePO1.setChildPageID("-1");
        pagePO1.setVersionID(1.0);
        pagePO1.setExpired(false);

        //添加新页面
        pageDao.insertNewPage(pagePO1);

        //更新原页面
        if(pagePO.getChildPageID().equals("-1")){
            pagePO.setChildPageID(pagePO1.getId()+"");
        }else {
            pagePO.setChildPageID(pagePO.getChildPageID()+"+"+pagePO1.getId());
        }

        pageDao.updatePageInfo(pagePO);

        return pagePO1.getId();
    }

    @Override
    public long insertNewRootPageDetail(long pageId, String pageContent) {
        PageDetailPO pageDetailPO = new PageDetailPO();
        pageDetailPO.setId(0);
        pageDetailPO.setPageId(pageId);
        pageDetailPO.setVersionID(1.0);
        pageDetailPO.setPageContent(pageContent);
        pageDetailPO.setExpired(false);

        return pageDetailDao.insertPageVersion(pageDetailPO);
    }

    @Override
    public long insertNewRootPageOperateRecord(PagePO pagePO) {

        PageOperateRecordPO pageOperateRecordPO = new PageOperateRecordPO();
        pageOperateRecordPO.setId(0);
        pageOperateRecordPO.setPageId(pagePO.getId());
        pageOperateRecordPO.setOperatorId(pagePO.getOriginatorID());
        pageOperateRecordPO.setOperatorTime(new base().getCurrTime());
        pageOperateRecordPO.setType(1);
        pageOperateRecordPO.setOperatorContent("新建页面\""+pagePO.getName()+"\"");
        pageOperateRecordPO.setBeforeVersionId(-1);
        pageOperateRecordPO.setAfterVersionId(1.0);
        pageOperateRecordPO.setExpired(false);

        return pageOperateRecordDao.insertPageOperateRecord(pageOperateRecordPO);
    }

    @Override
    public boolean hasReadPermission(SpacePO spacePO, PagePO pagePO, long userId) {
        base general = new base();
        boolean b = true;

        //判断当前用户是否有目标空间的权限
        if(!(spacePO.getReadID().equals("-1") ||
                general.isLongBelongToList(userId,general.stringToLongList(spacePO.getReadID())))){
            b = false;
        }
        //判断当前用户是否有目标页面的权限
        if(!(pagePO.getReadID().equals("-1") ||
                general.isLongBelongToList(userId,general.stringToLongList(pagePO.getReadID())))){
            b = false;
        }
        //判断当前用户是否有目标页面其父页面的权限
        while (pagePO.isRootPage()==false){
            pagePO = getPageByPageId(pagePO.getFatherPageID());
            if(!(pagePO.getReadID().equals("-1") ||
                    general.isLongBelongToList(userId,general.stringToLongList(pagePO.getReadID())))){
                b = false;
                break;
            }
        }
        return b;
    }

    @Override
    public boolean haswritePermission(SpacePO spacePO, PagePO pagePO, long userId) {
        base general = new base();
        boolean b = true;

        //判断当前用户是否有目标空间的权限
        if(!(spacePO.getWriteID().equals("-1") ||
                general.isLongBelongToList(userId,general.stringToLongList(spacePO.getWriteID())))){
            b = false;
        }
        //判断当前用户是否有目标页面的权限
        if(!(pagePO.getWriteID().equals("-1") ||
                general.isLongBelongToList(userId,general.stringToLongList(pagePO.getWriteID())))){
            b = false;
        }
        //判断当前用户是否有目标页面其父页面的权限
        while (pagePO.isRootPage()==false){
            pagePO = getPageByPageId(pagePO.getFatherPageID());
            if(!(pagePO.getWriteID().equals("-1") ||
                    general.isLongBelongToList(userId,general.stringToLongList(pagePO.getWriteID())))){
                b = false;
                break;
            }
        }
        return b;
    }

    @Override
    public List<PageOperateRecordPO> getLastSevenPageOperateRecordsByPageId(Long id) {
        return pageOperateRecordDao.getLastSevenPageOperateRecordsByPageId(id);
    }

    @Override
    public PagePO pageReturnVersion(Long id, double version,long userId) {
        PagePO pagePO = pageDao.getPageByPageId(id);
        double newVersion = (double)((int)((pagePO.getVersionID()+0.1)*10))/10;
        //更新page版本号
        pagePO.setVersionID(newVersion);
        pageDao.updatePageInfo(pagePO);

        //更新pageDetail表
        PageDetailPO pageDetailPO = pageDetailDao.getPageByPageIdAndVersion(id,version);
        pageDetailPO.setId(0);
        pageDetailPO.setVersionID(newVersion);
        pageDetailPO.setExpired(false);
        //删除原版本
        pageDetailDao.deletePageRecord(id);
        //添加新版本
        pageDetailDao.insertPageVersion(pageDetailPO);

        //更新page操作记录表
        base base1 = new base();
        PageOperateRecordPO pageOperateRecordPO = new PageOperateRecordPO();
        pageOperateRecordPO.setPageId(id);
        pageOperateRecordPO.setOperatorId(userId);
        pageOperateRecordPO.setOperatorTime(base1.getCurrTime());
        pageOperateRecordPO.setType(2);
        pageOperateRecordPO.setOperatorContent("页面版本回滚");
        pageOperateRecordPO.setBeforeVersionId(((double)((int)((newVersion-0.1)*10)))/10);
        pageOperateRecordPO.setAfterVersionId(newVersion);
        pageOperateRecordPO.setExpired(false);
        pageOperateRecordDao.insertPageOperateRecord(pageOperateRecordPO);

        return pagePO;
    }

    @Override
    public void updatePageRight(Long pageId, String userName, Long operatorId, String read, String write) {
        PagePO pagePO = pageDao.getPageByPageId(pageId);
        UserPO userPO = userDao.getUserByName(userName);
        PageOperateRecordPO pageOperateRecordPO = pageOperateRecordDao.getLastPageOperateRecord(pageId);
        //所有人可读可写,添加新用户权限
        if(pagePO.getWriteID().equals("-1")){
            pagePO.setReadID(userPO.getId()+"");
            pagePO.setWriteID(userPO.getId()+"");
        } else if(pagePO.getReadID().equals("-1")){
            pagePO.setReadID(userPO.getId()+"");
            pagePO.setWriteID(userPO.getId()+"+"+pagePO.getWriteID());
        } else {
            pagePO.setReadID(userPO.getId()+"+"+pagePO.getReadID());
            pagePO.setWriteID(userPO.getId()+"+"+pagePO.getWriteID());
        }
        pageDao.updatePageInfo(pagePO);

        //更新page操作记录表
        base base1 = new base();
        pageOperateRecordPO.setId(0);
        pageOperateRecordPO.setPageId(pageId);
        pageOperateRecordPO.setOperatorId(operatorId);
        pageOperateRecordPO.setOperatorTime(base1.getCurrTime());
        pageOperateRecordPO.setType(3);
        pageOperateRecordPO.setOperatorContent("页面权限修改");
        pageOperateRecordPO.setBeforeVersionId(pageOperateRecordPO.getAfterVersionId());
        pageOperateRecordPO.setAfterVersionId(((double)((int)((pageOperateRecordPO.getAfterVersionId()+0.1)*10)))/10);
        pageOperateRecordPO.setExpired(false);
        pageOperateRecordDao.insertPageOperateRecord(pageOperateRecordPO);
    }

    @Override
    public void putRightIdsIntoMap(List<Long> Ids,List<Map> maps,int isRead) {
        for(int i=0;i<Ids.size();i++){
            Map map = new HashMap();
            map.put("userName",userService.getUserById(Ids.get(i)).getName());
            map.put("readId",1);
            map.put("writeId",isRead);
            maps.add(map);
        }
    }

    @Override
    public List<PagePO> pageDfs(List<PagePO> pagePOS){
        List<PagePO> pagePOS1 = new ArrayList<PagePO>();

        //把所有的根页面加入队列
        Queue<PagePO> queue = new LinkedList<PagePO>();
        for (int i = 0;i<pagePOS.size();i++){
            if(pagePOS.get(i).isRootPage() == true){
                queue.add(pagePOS.get(i));
            }
        }

        pageDfsAssistant1(queue,pagePOS1);

        return pagePOS1;
    }

    @Override
    public void pageDfsAssistant1(Queue<PagePO> queue,List<PagePO> pagePOS) {
        base base1 = new base();
        while (queue.peek() != null){
            //获得队列最上方元素
            pagePOS.add(queue.peek());
            String childIds = queue.peek().getChildPageID();
            if(childIds.equals("-1")){
                queue.remove();
            } else {
                //对最上方元素的子集进行递归
                pageDfsAssistant1(pageDfsAssistant2(base1.stringToLongList(childIds)),pagePOS);
                queue.remove();
            }
        }

    }

    @Override
    public Queue<PagePO> pageDfsAssistant2(List<Long> pagePOS) {
        Queue<PagePO> queue = new LinkedList<PagePO>();
        for(int i =0;i<pagePOS.size();i++){
            PagePO pagePO = pageDao.getPageByPageId(pagePOS.get(i));
            queue.add(pagePO);
        }
        return queue;
    }
}
