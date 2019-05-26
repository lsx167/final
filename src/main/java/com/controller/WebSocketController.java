package com.controller;

import com.websocket.WebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/webSocket")
public class WebSocketController {

    @Bean // 这个注解会从Spring容器拿出Bean
    public WebSocketHandler infoHandler() {
        return new WebSocketHandler();
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        System.out.println(username + "登录");
        HttpSession session = request.getSession();
        session.setAttribute("SESSION_USERNAME", username);
        return "websocket";
    }
}