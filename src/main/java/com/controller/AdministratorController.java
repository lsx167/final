package com.controller;

import com.entities.AdministratorPO;
import com.service.AdministratorService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/administrator")
public class AdministratorController {
    private static final Logger logger = Logger.getLogger(AdministratorController.class);
    @Resource
    private AdministratorService service;

    /**
     * 所有管理员
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/all", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllAdministrator(HttpServletRequest request, HttpServletResponse response) {
        List<AdministratorPO> administratorPOS = service.getAllAdministrators();
        return (administratorPOS.get(0).getId()+administratorPOS.get(0).getPassword());
    }

    /**
     * 根据id查询管理员
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/id", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserById(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("v"));
        AdministratorPO administratorPO = service.getAdministratorById(id);
        return (administratorPO.getId()+administratorPO.getPassword());
    }
}
