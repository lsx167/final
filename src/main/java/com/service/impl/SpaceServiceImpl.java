package com.service.impl;

import com.dao.SpaceDao;
import com.entities.*;
import com.service.SpaceService;
import com.service.base;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("SpaceServiceImpl")
@Scope("prototype")
public class SpaceServiceImpl implements SpaceService{
    @Resource
    private SpaceDao spaceDao;

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
}
