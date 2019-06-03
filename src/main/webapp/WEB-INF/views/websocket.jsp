<%--
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Java API for WebSocket (JSR-356)</title>
</head>
<body>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        //Websocket的连接
        websocket = new WebSocket("ws://localhost:8080/websocket/socketServer");//WebSocket对应的地址
    }
    else if ('MozWebSocket' in window) {
        //Websocket的连接
        websocket = new MozWebSocket("ws://localhost:8080/websocket/socketServer");//SockJS对应的地址
    }
    else {
        //SockJS的连接
        websocket = new SockJS("http://localhost:8080/sockjs/socketServer");    //SockJS对应的地址
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
        //alert(openEvt.Data);
    }

    function onMessage(evt) {
        $("#content").append(evt.data+"<br>"); // 接收后台发送的数据
    }
    function onError() {
    }
    function onClose() {
    }

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            websocket.send($("#targetName").val()+"@"+$("#inputMsg").val());//调用后台handleTextMessage方法
            alert("发送成功!");
        } else {

            alert("连接失败!"+websocket.readyState);
        }
    }

    window.close = function () {
        websocket.onclose();
    }
</script>
欢迎你，${sessionScope.SESSION_USERNAME}
请输入目标名称：<input type="text" id = "targetName" />
请输入：<textarea rows="3" cols="100" id="inputMsg" name="inputMsg"></textarea>
<button onclick="doSend();">发送</button>
<div id="content"></div>
</body>
</html>  --%>
