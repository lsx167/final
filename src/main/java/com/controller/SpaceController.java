package com.controller;

import com.entities.SpacePO;
import com.service.SpaceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/space")
public class SpaceController {
    private static final Logger logger = Logger.getLogger(SpaceController.class);

    @Resource
    private SpaceService service;

    /**
     * 查询所有空间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/allspace", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllspace(HttpServletRequest request, HttpServletResponse response) {
        List<SpacePO> spacePOS = service.getAllSpace();
        return (spacePOS.get(0).getId()+spacePOS.get(0).getName()+spacePOS.get(0).getOriginatorID()+spacePOS.get(0).getChildPageID());
    }

}