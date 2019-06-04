<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<html>
<head>
	<link rel="stylesheet" href="../css/login.css" type="text/css">
</head>
<body class="body">
<header class="header">
	<img src="../img/logo.jpeg" style="max-height: 30px;float: left;margin-left: 10%;margin-top: 5px;border:none;"/>
</header>
<div class="main">
	<div class="login">
		<div class="login_1">
			注册用户
		</div>
		<div style="width: 90%;height: 1px;margin-left: 5%;background-color: black;margin-top: 10px;"></div>
		<div class="login_2">
			<form action="/user/register" method="post">
				<div class="field-group">
					<label class="username-label">用户名：</label>
					<input type="text" name="username" id="id" class="login_text" placeholder="请输入用户名" />
				</div>
				<div class="field-group">
					<label class="username-label">密码：</label>
					<input type="text" name="password" id="passward" class="login_text" placeholder="请输入密码" />
				</div>
				<div class="field-group">
					<label class="username-label">姓名：</label>
					<input type="text" name="name" id="name" class="login_text" placeholder="请输入姓名" />
				</div>
				<div class="field-group" style="margin-top: 10px;margin-left: 20px">
					<input type="submit" class="input_btn" value="注册" />
				</div>
			</form>
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
