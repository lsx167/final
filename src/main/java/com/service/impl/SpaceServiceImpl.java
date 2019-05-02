package com.service.impl;

import com.dao.SpaceDao;
import com.entities.SpacePO;
import com.service.SpaceService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public SpacePO getSpaceBySpaceId(long id) {
        return spaceDao.getSpaceBySpaceId(id);
    }
}
