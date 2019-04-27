package com.controller;

import com.entities.UserPO;
import com.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService service;

    /**
     *返回user对象信息给page1.jsp处理，然后在前端页面展示
     */
    @RequestMapping("/page1")
    public ModelAndView getUser() {
        System.out.println("访问page1的后台。。。");
        ModelAndView mav = new ModelAndView("page1");
        List<UserPO> users = service.getAllUser();
        System.out.println(users);
        mav.addObject("user", users.get(0));
        return mav;
//        return "page1"; //跳转到.jsp结尾的对应文件（page1.jsp）,此时返回值是String
    }

    /**
     * 根据id查询账号
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/id", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserById(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("v"));
        UserPO users = service.getUserById(id);
        return (users.getId()+users.getUserName()+users.getPassword()+users.getName());
    }

    /**
     * 账号登陆
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        if(request.getParameter("username").equals(null) || request.getParameter("username").equals("")){
            mav.setViewName("forward://jsp/login.jsp");
            mav.addObject("loginMsg", "登录失败,请输入账号");
            return mav;
        }
        else if(request.getParameter("password").equals(null) || request.getParameter("password").equals("")){
            mav.setViewName("forward://jsp/login.jsp");
            mav.addObject("loginMsg", "登录失败,请输入密码");
            return mav;
        }
        else {
            //long id = Long.parseLong(request.getParameter("username"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");



            Integer success = service.loginById(username,password);
            if (success == 0){
                mav.setViewName("forward://jsp/login.jsp");
                mav.addObject("loginMsg", "账号不存在");
                return mav;
            }
            else if(success == 1){
                mav.setViewName("forward://jsp/login.jsp");
                mav.addObject("loginMsg", "密码错误");
                return mav;
            }
            else {
                mav.setViewName("main");
                mav.addObject("loginMsg", "登录成功");
                return mav;
            }
        }

    }

    /**
     * 直接返回字符串给请求的页面（这里在请求URL增加参数v是验证前后台通信是否正常）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/say", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String sayHi(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("v");
        List<UserPO> users = service.getAllUser();
        //!!!!!!!看一下注释
        logger.info("{name:context:hi,你好}");
        return name+"-{name:context:hi,你好}"+users.get(0);
    }

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    @ResponseBody
    public String testController(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("1");
        return "main";
    }
}