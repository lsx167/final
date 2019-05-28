package com.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Websocket处理器
 */

public class WebSocketHandler extends TextWebSocketHandler {

    // 已建立连接的用户
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    /**
     * 处理前端发送的文本信息 js调用websocket.send时候，会调用该方法
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String editingUserPage = (String) session.getAttributes().get("editingUserPage");
        String pageId = editingUserPage.substring(0,editingUserPage.indexOf("+"));
        String userName = editingUserPage.substring(editingUserPage.indexOf("+")+1);

        // 获取提交过来的消息详情
        System.out.println("1收到用户 " + userName + " 的消息:" + message.toString());

        users.remove(session);
        sendMessageToPageUsers(pageId,new TextMessage("用户"+userName+"提交了新的编辑，请刷新同步，注意在本地保存您的修改"));
        users.add(session);
    }

    /**
     * 当新连接建立的时候，被调用 连接成功时候，会触发页面上onOpen方法
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //获取当前登录页面和用户信息
        String editingUserPage = (String) session.getAttributes().get("editingUserPage");
        String pageId = editingUserPage.substring(0,editingUserPage.indexOf("+"));
        String userName = editingUserPage.substring(editingUserPage.indexOf("+")+1);
        System.out.println("用户 " + userName + "正在编辑页面"+pageId);

        //查找正在编辑统一页面的用户
        int count = 1;//统计用户数
        StringBuffer editUsers = new StringBuffer();//统计用户
        editUsers.append(userName);
        for (WebSocketSession user : users) {
            String pageUsers = (String) user.getAttributes().get("editingUserPage");
            if(pageUsers.substring(0,pageUsers.indexOf("+")).equals(pageId)){
                count++;
                editUsers.append("、"+pageUsers.substring(pageUsers.indexOf("+")+1));
            }
        }
        users.add(session);

        System.out.println("当前正有"+count+"人正在编辑");
        System.out.println("他们分别有:"+editUsers);

        sendMessageToPageUsers(pageId,new TextMessage("当前正有"+count+"人正在编辑，他们分别有:"+editUsers));
        //session.sendMessage(new TextMessage(userName + "连接成功"));
    }

    /**
     * 当连接关闭时被调用
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //获取当前登录页面和用户信息
        String editingUserPage = (String) session.getAttributes().get("editingUserPage");
        String pageId = editingUserPage.substring(0,editingUserPage.indexOf("+"));
        String userName = editingUserPage.substring(editingUserPage.indexOf("+")+1);
        System.out.println("用户" + userName + "正在退出编辑"+pageId);

        users.remove(session);
        //查找正在编辑统一页面的用户
        int count = 0;//统计用户数
        StringBuffer editUsers = new StringBuffer();//统计用户
        for (WebSocketSession user : users) {
            String pageUsers = (String) user.getAttributes().get("editingUserPage");
            if(pageUsers.substring(0,pageUsers.indexOf("+")).equals(pageId)){
                count++;
                if(editUsers.equals(null) || editUsers.length()==0){
                    editUsers.append(pageUsers.substring(pageUsers.indexOf("+")+1));
                } else {
                    editUsers.append("、"+pageUsers.substring(pageUsers.indexOf("+")+1));
                }
            }
        }
        sendMessageToPageUsers(pageId,new TextMessage("当前正有"+count+"人正在编辑，他们分别有:"+editUsers));

    }

    /**
     * 传输错误时调用
     *
     * @param session
     * @param exception
     * @throws Exception
     *//*
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //获取当前登录页面和用户信息
        String editingUserPage = (String) session.getAttributes().get("editingUserPage");
        String userName = editingUserPage.substring(editingUserPage.indexOf("+")+1);
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("用户:" + userName + " websocket connection closed......");
        users.remove(session);
    }*/

    /**
     * 给正在编辑某个页面的所有用户发送消息
     *
     * @param message
     */
    public void sendMessageToPageUsers(String pageId,TextMessage message) {
        for (WebSocketSession user : users) {
            String pageUsers = (String) user.getAttributes().get("editingUserPage");
            //如果页面编号相同
            if(pageUsers.substring(0,pageUsers.indexOf("+")).equals(pageId)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            String pageUsers = (String) user.getAttributes().get("editingUserPage");
            if(pageUsers.substring(pageUsers.indexOf("+")+1).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}