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
		        <input type="submit" class="input_btn" value="创建" />
		    </div>
		</form>
	</div>
	<div class="right_img">
		<h2>关于空间< /h2>
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