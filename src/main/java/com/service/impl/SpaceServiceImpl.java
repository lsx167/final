package com.service.impl;

import com.dao.SpaceDao;
import com.entities.*;
import com.service.SpaceService;
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
    public ModelAndView packagePage(UserPO userPO, SpacePO spacePO, List<SpacePO> spacePOS, List<PagePO> pagePOS, List<SpaceOperateRecordPO> spaceOperateRecordPOS) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");
        mav.addObject("userPO",userPO);
        mav.addObject("spacePO",spacePO);
        mav.addObject("spacePOS",spacePOS);
        mav.addObject("pagePOS",pagePOS);
        mav.addObject("spaceOperateRecordPOS",spaceOperateRecordPOS);
        return mav;
    }
}
