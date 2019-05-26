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
            <a href="/jsp/createSpace.jsp?userName=${param.userName}">创建空间</a>
            <a href="/jsp/createRootPage.jsp?spaceName=${requestScope.spacePO.name}&userName=${param.userName}">创建页面</a>
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
   	<div>
   	    <form action="/page/createNewRootPage" method="post">
            <div class="form_info">
                <div class="info">
                    <label class="username-label">页面名称：</label>
                    <input type="text" name="pageName" id="pageName" class="login_text" placeholder="请输入页面名称" />
                </div>
                <div class="info">
                    页面类型：根页面
                </div>
                <div class="info">
                    所属空间为:<input type="text" name="spaceName" value="${param.spaceName}" readonly>
                </div>
            </div>
            <input type='hidden' name="userName" value ='${param.userName}'/>
            <div class="page_content">
                <!-- 加载编辑器的容器 -->
                <script id="container" name="pageContent" type="text/plain" class="page_container">

                </script>
                <div>
                    <input type="submit" value="保存" />
                </div>
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