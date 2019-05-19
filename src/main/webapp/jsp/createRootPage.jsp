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
    <div style="float: left;width: 200px;height: 30px;text-align: center;color: white;">
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
    <button class="create_btn">创建</button>
    <button class="create_btn">退出</button>
    <div class="header_user">
        <img src="../img/yonghu1.png" style="max-height: 30px;margin-top: 5px;border:none;"/>
    </div>
    <div class="bar1">
        <form>
            <input type="text" placeholder="请输入您要搜索的内容...">
            <button type="submit"></button>
        </form>
    </div>
</header>
<div class="main">
   	<div class="">
   	    <form action="/page/createNewRootPage" method="post">
			<div>
                <label class="username-label">页面名称：</label>
                <input type="text" name="pageName" id="pageName" class="login_text" placeholder="请输入页面名称" />
			</div>
			<div>
                页面类型：根页面
            </div>
            <div>
                所属空间为:<input type="text" name="spaceName" value="${param.spaceName}" readonly>
            </div>
   	        <!-- 加载编辑器的容器 -->
   	        <script id="container" name="pageContent" type="text/plain">
   	            
   	        </script>
   			<div>
   			    <input type="submit" value="保存" />
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