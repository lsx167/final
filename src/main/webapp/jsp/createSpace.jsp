<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="../css/create.css" type="text/css">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/js/ajaxfileupload.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="body">
<script type="text/javascript">
    function createSpace() {
        //获取到要跳转的页码
        var spaceName = $("#spaceName").val();
        var spaceDescribe = $("#spaceDescribe").val();
        var userName = $("#userName").val();
        if(spaceName == ""){
            alert("空间名不能为空");
        } else if(spaceDescribe == ""){
            alert("空间描述不能为空");
        }else{
            alert("/space/createSpace?spaceName="+spaceName+"&spaceDescribe="+spaceDescribe+"&userName="+userName);
            location.href = "/space/createSpace?spaceName="+spaceName+"&spaceDescribe="+spaceDescribe+"&userName="+userName;
        }
    };
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
            <c:forEach items="${requestScope.lastThree}" var="bean">
                <tr>
                    <td>
                        <a href="/space/getSpaceBySpaceId?spaceId=${bean.id}&userName=${requestScope.userPO.name}" style="color: blue;text-decoration: none">
                                ${bean.name}
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">创建</button>
        <div class="dropdown-content">
            <a href="/jsp/createSpace.jsp?userName=${param.userName}">创建空间</a>
<%--
            <a href="/jsp/createRootPage.jsp?spaceName=${requestScope.spacePO.name}&userName=${param.userName}">创建页面</a>
--%>
        </div>
    </div>
    <button class="create_btn">
        <a href="/user/logout?userName=${param.userName}" style="color: white;text-decoration: none;margin-left: 50px">
            退出
        </a>
    </button>
    <div class="header_user">
        <%--<div style="float: left;text-align: center;color: white;margin-top: 10px">
            欢迎您，${requestScope.userPO.name}
        </div>--%>
        <img src="../img/yonghu1.png" style="max-height: 30px;margin-top: 10px;border:none;float: right"/>
    </div>
    <div class="bar1">
        <form action="/space/getSpaceBySearchContent" method="post">
            <input type="text" name="spaceContent" placeholder="请输入您要搜索的内容...">
            <input type='hidden' name="userName" value ='${param.userName}'/>
            <button type="submit"></button>
        </form>
    </div>
</header>
<div class="main">
   	<div class="left_input">
		<form action="/space/createSpace" method="post">
		    <div class="field-group">
		        <label class="username-label">空间名称：</label>
		        <input type="text" name="spaceName" id="spaceName" class="login_text" placeholder="请输入空间名称" />
		    </div>
		    <div class="field-group">
		        <label class="username-label">空间描述：</label>
		        <input type="text" name="spaceDescribe" id="spaceDescribe" class="login_text" placeholder="请输入空间描述" />
		    </div>
		    <div class="field-group" style="margin-top: 10px;margin-left: 20px">
		        <input type="submit" class="input_btn" onclick="createSpace()" value="创建" />
		    </div>
            <input type='hidden' name="userName" id="userName" value ='${param.userName}'/>
		</form>
	</div>
	<div class="right_img">
		<h2>关于空间</h2>
		<p style="text-align: left;font-size: 20px;">
			&nbsp;&nbsp;&nbsp;&nbsp;与您的团队分享知识并与项目，流程和程序进行协作。他们将收到有关此空间和所有更新的通知。团队成员将被授予权限并添加为该空间的观察者。
		</p>
        <img src="../img/team.png" style="max-height: 150px;margin-top: 30px;border:none;"/>
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