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
            if (session != null) {
                String editingUserPage = (String) session.getAttribute("editPageUsers");  //这边获得登录时设置的唯一用户标识
                if (editingUserPage == null) {
                    editingUserPage = "未知" + session.getId();
                }
                attributes.put("editingUserPage", editingUserPage);  //将用户标识放入参数列表后，下一步的websocket处理器可以读取这里面的数据
            }
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("After Handshake");
    }
}