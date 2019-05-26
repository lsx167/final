package com.websocket;

import com.service.base;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * WebSocket握手拦截器
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {

        base base1 = new base();
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);

            //获取用户名和页面id
            String userName = servletRequest.getServletRequest().getParameter("userName");
            String pageId = servletRequest.getServletRequest().getParameter("pageId");
            if (session != null) {
                Map editingUserPage = (Map) session.getAttribute("editingUserPage");  //这边获得登录时设置的唯一用户标识

                String editPageUsers = (String) editingUserPage.get(Long.parseLong(pageId));//获取当前页面编辑用户列表
                /*if (editingUserPage == null) {
                    editingUserPage
                }*/
                List<String> editUsers= base1.userList(editPageUsers);
                int count = editUsers.size();
                attributes.put("editUsers", editUsers);  //将用户标识放入参数列表后，下一步的websocket处理器可以读取这里面的数据
                attributes.put("count", count);
                attributes.put("pageId", pageId);
                attributes.put("userName", userName);

            }
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("After Handshake");
    }
}