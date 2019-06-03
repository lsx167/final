package com.service.impl;

import com.dao.SpaceDao;
import com.dao.SpaceOperateRecordDao;
import com.dao.UserDao;
import com.entities.*;
import com.service.PageService;
import com.service.SpaceService;
import com.service.base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("SpaceServiceImpl")
@Scope("prototype")
public class SpaceServiceImpl implements SpaceService{
    @Resource
    private SpaceDao spaceDao;
    @Resource
    private SpaceOperateRecordDao spaceOperateRecordDao;
    @Resource
    private UserDao userDao;
    @Autowired
    private PageService pageService;

    @Override
    public List<SpacePO> getAllSpace() {
        return spaceDao.getAllSpaces();
    }

    @Override
    public SpacePO getMainSpaceById(long id) {
        return spaceDao.getMainSpaceById(id);
    }

    @Override
    public List<SpacePO> getSpacesById(long id) {
        return spaceDao.getSpacesById(id);
    }

    @Override
    public SpacePO getSpaceById(long id) {
        return spaceDao.getSpaceById(id);
    }

    @Override
    public SpacePO getSpaceBySpaceName(String name) {
        return spaceDao.getSpaceBySpaceName(name);
    }

    @Override
    public LimitPageList selectPageBySearch(Integer pageNow, String content) {

        content = "%"+content+"%";
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=spaceDao.getCountBySearch(content);//获取总的记录数
        List<SpacePO> spacePOList=new ArrayList<SpacePO>();
        Page page=null;
        if(pageNow!=null){
            page=new Page(totalCount, pageNow);
            page.setPageSize(4);
            spacePOList=spaceDao.selectPageBySearch(page.getStartPos(), page.getPageSize(),content);//从startPos开始，获取pageSize条数据
        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(4);
            spacePOList=spaceDao.selectPageBySearch(page.getStartPos(), page.getPageSize(),content);//从startPos开始，获取pageSize条数据
        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(spacePOList);
        return LimitPageStuList;

//        return spaceDao.selectPageBySearch(startPos,pageSize,content);
    }

    @Override
    public int getCountBySearch(String content) {
        content = "\'%"+content+"%\'";
        return spaceDao.getCountBySearch(content);
    }

    @Override
    public Long createSpace(SpacePO spacePO) {
        spaceDao.insertSpace(spacePO);

        return spacePO.getId();
    }

    @Override
    public ModelAndView packagePage(UserPO userPO, UserPO originUserPO,SpacePO spacePO, List<SpacePO> spacePOS, List<PagePO> pagePOS, List<SpaceOperateRecordPO> spaceOperateRecordPOS) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");
        mav.addObject("userPO",userPO);
        mav.addObject("originUserPO",originUserPO);
        mav.addObject("spacePO",spacePO);
        mav.addObject("spacePOS",spacePOS);
        mav.addObject("pagePOS",pagePOS);
        mav.addObject("spaceOperateRecordPOS",spaceOperateRecordPOS);
        return mav;
    }

    @Override
    public void updateSpaceCreatePage(long spaceId, long pageId) {
        SpacePO spacePO = getSpaceById(spaceId);

        if(spacePO.getChildPageID().equals("-1")){
            spacePO.setChildPageID(pageId+"");
        }else {
            spacePO.setChildPageID(spacePO.getChildPageID()+"+"+pageId);
        }

        spaceDao.updateSpaceInfo(spacePO);
    }

    @Override
    public boolean hasReadPermission(SpacePO spacePO, long userId) {
        base general = new base();
        boolean b = true;

        //判断当前用户是否有目标空间的权限
        if(!(spacePO.getReadID().equals("-1") ||
                general.isLongBelongToList(userId,general.stringToLongList(spacePO.getReadID())))){
            b = false;
        }
        return b;
    }

    @Override
    public boolean haswritePermission(SpacePO spacePO, long userId) {
        base general = new base();
        boolean b = true;

        //判断当前用户是否有目标空间的权限
        if(!(spacePO.getWriteID().equals("-1") ||
                general.isLongBelongToList(userId,general.stringToLongList(spacePO.getWriteID())))){
            b = false;
        }
        return b;
    }

    @Override
    public ModelAndView packagePageRight(ModelAndView mav, SpacePO spacePO) {
        base base1 = new base();
        mav.setViewName("spaceRights");

        //更新page的type
        if(spacePO.getReadID().equals("-1") && spacePO.getWriteID().equals("-1")){
            spacePO.setType(1);
        } else if(spacePO.getReadID().equals("-1") && !spacePO.getWriteID().equals("-1")){
            spacePO.setType(2);
        } else {
            spacePO.setType(3);
        }

        spaceDao.updateSpaceInfo(spacePO);
        mav.addObject("type",spacePO.getType());

        List<Map> userRight = new ArrayList<Map>();
        //所有人可读，部分人可写
        if(spacePO.getType() == 2){
            //获取可写人ids
            List<Long> writeIds = base1.stringToLongList(spacePO.getWriteID());
            pageService.putRightIdsIntoMap(writeIds,userRight,1);
        }else if(spacePO.getType() == 3){    //部分人可读部分人可写
            //获取可写人ids
            List<Long> writeIds = base1.stringToLongList(spacePO.getWriteID());
            //获取可读人ids
            List<Long> readIds = base1.stringToLongList(spacePO.getReadID());
            pageService.putRightIdsIntoMap(writeIds,userRight,1);
            //查找可读不可写的人ids
            readIds = base1.findReadOnly(readIds,writeIds);
            pageService.putRightIdsIntoMap(readIds,userRight,0);
        }

        mav.addObject("userRight",userRight);
        return mav;
    }

    @Override
    public SpacePO updateSpaceRightType(SpacePO spacePO, Integer type, UserPO userPO) {
        //当页面类型不发生改变
        if(spacePO.getType().equals(type)){
            return spacePO;
        } else if(spacePO.getType().equals(1)){
            //1->2
            spacePO.setWriteID(userPO.getId()+"");
            if(type.equals(3)){
                //1->3
                spacePO.setReadID(userPO.getId()+"");
            }
        } else if(spacePO.getType().equals(2)){
            if(type.equals(1)){
                //2->1
                spacePO.setWriteID("-1");
            } else {
                //2->3
                spacePO.setReadID(spacePO.getWriteID());
            }
        } else if(spacePO.getType().equals(3)){
            //3->2
            spacePO.setReadID("-1");
            if(type.equals(1)){
                //3->1
                spacePO.setWriteID("-1");
            }
        }

        //修改两张表
        SpaceOperateRecordPO spaceOperateRecordPO = new SpaceOperateRecordPO();

        spaceOperateRecordPO.setSpaceId(spacePO.getId());
        spaceOperateRecordPO.setOperatorId(userPO.getId());
        spaceOperateRecordPO.setOperatorTime(new base().getCurrTime());
        spaceOperateRecordPO.setType(2);
        spaceOperateRecordPO.setOperatorContent("更改权限");
        spaceOperateRecordPO.setExpired(false);

        spaceDao.updateSpaceInfo(spacePO);
        spaceOperateRecordDao.insertSpaceOperate(spaceOperateRecordPO);
        return spacePO;
    }

    @Override
    public SpacePO addSpaceRight(SpacePO spacePO, String updateUserName, Long operatorId, ModelAndView mav) {
        base base1 = new base();
        UserPO addUser = userDao.getUserByName(updateUserName);
        if(addUser == null){
            //用户名不存在
            mav.addObject("msg","1");
        } else if(base1.isLongBelongToList(addUser.getId(),base1.stringToLongList(spacePO.getReadID())) ||
                base1.isLongBelongToList(addUser.getId(),base1.stringToLongList(spacePO.getWriteID()))){
            //用户名已在list中
            mav.addObject("msg",2);
        } else {
            if(spacePO.getType().equals(2)){
                spacePO.setWriteID(addUser.getId()+"+"+spacePO.getWriteID());
            }else {
                spacePO.setWriteID(addUser.getId()+"+"+spacePO.getWriteID());
                spacePO.setReadID(addUser.getId()+"+"+spacePO.getReadID());
            }

            //修改两张表
            SpaceOperateRecordPO spaceOperateRecordPO = new SpaceOperateRecordPO();

            spaceOperateRecordPO.setSpaceId(spacePO.getId());
            spaceOperateRecordPO.setOperatorId(operatorId);
            spaceOperateRecordPO.setOperatorTime(new base().getCurrTime());
            spaceOperateRecordPO.setType(2);
            spaceOperateRecordPO.setOperatorContent("更改权限");
            spaceOperateRecordPO.setExpired(false);

            spaceDao.updateSpaceInfo(spacePO);
            spaceOperateRecordDao.insertSpaceOperate(spaceOperateRecordPO);

            mav.addObject("msg",3);
        }
        return spacePO;
    }

    @Override
    public SpacePO updateSpaceRight(Long spaceId, String userName, Long operatorId, String read, String write) {
        base base1 = new base();
        SpacePO spacePO = spaceDao.getSpaceById(spaceId);
        UserPO userPO = userDao.getUserByName(userName);

        if(spacePO.getType().equals(2)){
            //类型为2,修改情况只有多条数据的删除
            if(write.equals("0")){
                List<Long> list = base1.stringToLongList(spacePO.getWriteID());
                list.remove(userPO.getId());
                spacePO.setWriteID(base1.longListToString(list));
            }
        }else {
            //类型为3
            //删除可写
            if (write.equals("0")) {
                List<Long> list = base1.stringToLongList(spacePO.getWriteID());
                list.remove(userPO.getId());
                spacePO.setWriteID(base1.longListToString(list));
            }
            //删除可读
            if (read.equals("不可以")) {
                List<Long> list = base1.stringToLongList(spacePO.getReadID());
                list.remove(userPO.getId());
                spacePO.setReadID(base1.longListToString(list));
            }
            //两个都可以
            if(read.equals("可以") && write.equals("1")){
                //如果不存在
                if(!base1.isLongBelongToList(userPO.getId(),base1.stringToLongList(spacePO.getWriteID()))){
                    spacePO.setWriteID(userPO.getId()+"+"+spacePO.getWriteID());
                }
            }
        }

        //修改两张表
        SpaceOperateRecordPO spaceOperateRecordPO = new SpaceOperateRecordPO();

        spaceOperateRecordPO.setSpaceId(spacePO.getId());
        spaceOperateRecordPO.setOperatorId(operatorId);
        spaceOperateRecordPO.setOperatorTime(new base().getCurrTime());
        spaceOperateRecordPO.setType(2);
        spaceOperateRecordPO.setOperatorContent("更改权限");
        spaceOperateRecordPO.setExpired(false);

        spaceDao.updateSpaceInfo(spacePO);
        spaceOperateRecordDao.insertSpaceOperate(spaceOperateRecordPO);

        return spacePO;
    }
}
