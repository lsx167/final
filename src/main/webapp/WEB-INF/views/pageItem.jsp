<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="../../css/pageItem.css" type="text/css">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/js/ajaxfileupload.js"></script>
	<script type="text/javascript" charset="utf-8" src="/utf8-jsp/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="body">
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        //Websocket的连接
        websocket = new WebSocket("ws://localhost:8080/websocket/socketServer");//WebSocket对应的地址  ?&userName=${requestScope.userPO.name}&pageId=${requestScope.pagePO.id}
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
    }

    function onMessage(evt) {
        var message = evt.data;
        if(message.substring(0,1) == "1"){
            alert(message);
        }else{
            $("#now_bianji").html("");//清空数据
            $("#now_bianji").append(message+"<br>"); // 接收后台发送的数据
        }
    }
    function onError() {
    }
    function onClose() {
    }

    function sendSaveMessage() {
        if(websocket.readyState == websocket.OPEN){
            alert("保存成功!");
            websocket.send(${requestScope.pagePO.id}+${requestScope.userPO.name});
        } else {
            alert("保存失败！网络状态"+websocket.readyState);
        }
    }

    // 编辑页面
    function show_bianji() {
        $('#cancel_bianji').css('display','block');
        $('#bianji').css('display','none');
        $('#page_content_update').css('display','block');
        $('#page_content_show').css('display','none');
        $('#now_bianji').css('display','block');
    }

    // 取消页面编辑
    function show_cancel_bianji() {
        $('#cancel_bianji').css('display','none');
        $('#bianji').css('display','block');
        $('#page_content_update').css('display','none');
        $('#page_content_show').css('display','block');
        $('#now_bianji').css('display','none');
    }

    window.close = function () {
        websocket.onclose();
    }
</script>
<header class="header">
    <img src="../img/logo.jpeg" style="max-height: 30px;float: left;margin-left: 10%;margin-top: 5px;border:none;"/>
    <div style="float: left;width: 200px;height: 30px;text-align: center;color: white;margin-top: 10px">
        多人协作文档编辑系统
    </div>
    <div class="dropdown">
        <button class="dropbtn">空间</button>
        <div class="dropdown-content">
            <span style="font-size: small;color: #2b669a">最近访问的空间</span>
            <a href="#">菜鸟教程 1</a>
            <a href="#">菜鸟教程 2</a>
            <a href="#">菜鸟教程 3</a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">创建</button>
        <div class="dropdown-content">
            <a href="/jsp/createSpace.jsp?userName=${requestScope.userPO.name}">创建空间</a>
            <a href="/jsp/createChildPage.jsp?spaceName=${requestScope.spacePO.name}&pageName=${requestScope.pagePO.name}&pageId=${requestScope.pagePO.id}&userName=${requestScope.userPO.name}">创建页面</a>
        </div>
    </div>
    <button class="create_btn">
        <a href="/user/logout?userName=${requestScope.userPO.name}" style="color: white;text-decoration: none;margin-left: 50px">
            退出
        </a>
    </button>
    <div class="header_user">
        <div style="float: left;text-align: center;color: white;margin-top: 10px">
            欢迎您，${requestScope.userPO.name}
        </div>
        <img src="../img/yonghu1.png" style="max-height: 30px;margin-top: 10px;border:none;float: right"/>
    </div>
    <div class="bar1">
        <form action="/space/getSpaceBySearchContent" method="post">
            <input type="text" name="spaceContent" placeholder="请输入您要搜索的内容...">
            <input type='hidden' name="userName" value ='${requestScope.userPO.name}'/>
            <button type="submit"></button>
        </form>
    </div>
</header>
<div class="main">
    <div class="main_left">
        <div class="left_name">
            <div class="left_name_img">
                <img src="../img/wujiaoxing.png" style="max-height: 30px;margin-top: 5px;border:none;"/>
            </div>
            <div class="left_name_item">
                ${requestScope.spacePO.name}
            </div>
        </div>
		<div class="left_page_tree">
		    <div style="color: gray;font-size: 20px;text-align: left;margin-left: 20px;margin-top: 20px;">
		        我创建的空间
		    </div>
		    <div class="left_page">
				<table>
				    <c:forEach items="${requestScope.spacePOS}" var="bean">
				        <tr>
				            <td>
                                <a href="/space/getSpaceBySpaceId?spaceId=${bean.id}&userName=${requestScope.userPO.name}" style="color: blue;text-decoration: none">
                                        ${bean.name}
                                </a>
                            </td>
				        </tr>
				    </c:forEach>
				</table>
		    </div>
		</div>
        <div class="left_page_tree">
            <div style="color: gray;font-size: 20px;text-align: left;margin-left: 20px;margin-top: 20px;">
                页面树
            </div>
            <div class="left_page">
                <table>
                    <c:forEach items="${requestScope.pagePOS}" var="bean">
                        <tr>
                            <td>
                                <a href="/page/getPageByPageId?pageId=${bean.id}&userName=${requestScope.userPO.name}" style="color: blue;text-decoration: none">
                                        ${bean.name}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>
    </div>
	
    <div class="main_right">
        <div class="right_1">
            <div class="right_1_left">
                ${requestScope.pagePO.name}
            </div>
            <c:choose>
                <c:when test="${writePermission == 1}"><!-- 如果用户没有写权限-->
                    <div class="right_1_right">
                        删除
                    </div>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${writePermission == 1}"><!-- 如果用户没有写权限-->
                    <div class="right_1_right">
                        <a href="/page/getPageRightByPageId?pageId=${requestScope.pagePO.id}&userName=${requestScope.userPO.name}" style="color: black;text-decoration: none">
                            权限
                        </a>
                    </div>
                </c:when>
            </c:choose>
            <div class="right_1_right">
                <a href="/page/getPageVersionsByPageId?pageId=${requestScope.pagePO.id}&userName=${requestScope.userPO.name}" style="color: black;text-decoration: none">
                    历史
                </a>
            </div>
            <c:choose>
                <c:when test="${writePermission == 1}"><!-- 如果用户没有写权限-->
                    <div class="right_1_right" id="bianji" style="display: block" onclick="show_bianji()">
                        编辑
                    </div>
                    <div class="right_1_right" id="cancel_bianji" style="display: none" onclick="show_cancel_bianji()">
                        取消编辑
                    </div>
                </c:when>
            </c:choose>
        </div>
        <div class="right_2">
            <div class="right_2_creator">
                创建者：${requestScope.originUserPO.name}
            </div>
            <div class="right_2_creator">
                版本号：${requestScope.pagePO.versionID}
            </div>
            <div class="right_2_time">
                最后一次修改时间：${requestScope.pageOperateRecordPO.operatorTime}
            </div>
        </div>
        <c:choose>
            <c:when test="${writePermission == 0}"><!-- 如果用户没有写权限-->
                <div>
                    对不起，您没有该页面的写权限，请联系该页面的负责人
                </div>
            </c:when>
        </c:choose>

        <!--页面内容-->
		<div class="right_3" id="page_content_show" style="display: block">
			${requestScope.pageDetailPO.pageContent}
		</div>

        <div class="right_3" id="page_content_update" style="display: none">
            <form action="/page/updatePageContent?pageId=${requestScope.pagePO.id}&userName=${requestScope.userPO.name}" method="post">
                <!-- 加载编辑器的容器 -->
                <script id="container" name="pageContent" type="text/plain" class="right_3_container" >
                    ${requestScope.pageDetailPO.pageContent}
                </script>
                <div>
                    <input type="submit" onclick="sendSaveMessage()" value="保存"/>
                </div>
            </form>
            
            <!-- 配置文件 -->
            <script type="text/javascript" src="../../utf8-jsp/ueditor.config.js"></script>
            <!-- 编辑器源码文件 -->
            <script type="text/javascript" src="../../utf8-jsp/ueditor.all.js"></script>
            <!-- 实例化编辑器 -->
            <script type="text/javascript">
                var ue = UE.getEditor('container');
            </script>
        </div>
        <div class="now_bianji" id="now_bianji">
        </div>
    </div>
</div>
<footer class="footer">
    <p class="footer_p" style="margin-left: 20%;float: left;">
        Xu Yuhan's Final
    </p>
    <p class="footer_p" style="margin-right: 20%;float: right;">
        联系方式:暂无联系方式
    </p>
</footer>
</body>
</html>