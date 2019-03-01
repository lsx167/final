package com.controller;

import com.entities.SpaceOperateRecordPO;
import com.service.SpaceOperateRecordService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/spaceOperateRecord")
public class SpaceOperateRecordController {
    private static final Logger logger = Logger.getLogger(SpaceController.class);

    @Resource
    private SpaceOperateRecordService service;

    /**
     * 查询所有空间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/allSpaceRecord", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllspace(HttpServletRequest request, HttpServletResponse response) {
        List<SpaceOperateRecordPO> spaceOperateRecordPOS = service.getAllSpaceOperateRecord();
        //    //操作类型（1、创建空间，2、更改权限，3、新建页面，4、修改页面，5、删除页面）
        return (spaceOperateRecordPOS.get(0).getId()+spaceOperateRecordPOS.get(0).getOperatorContent()+
                spaceOperateRecordPOS.get(0).getType()+spaceOperateRecordPOS.get(0).getOperatorTime());
    }

}
