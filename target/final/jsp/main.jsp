<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="../css/main.css" type="text/css">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/js/ajaxfileupload.js"></script>
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

    <div class="bar1">
        <form>
            <input type="text" placeholder="请输入您要搜索的内容...">
            <button type="submit"></button>
        </form>
    </div>

</header>
<div class="main">
	<div class="main_left">
		<div class="left_name">
			<div class="left_name_img">
				
			</div>
			<div class="left_name_item">
				空间名称	
			</div>
		</div>
		
		<div class="left_page_tree">
			<div style="color: gray;font-size: 20px;text-align: left;margin-left: 20px;margin-top: 20px;">
				页面树
			</div>
			<div class="left_page">
				
			</div>
		</div>
		
		<div class="left_setting">
			<div class="left_setting_img">
				
			</div>
			<div class="left_setting_item">
				空间配置	
			</div>
		</div>
	</div>
	<div class="main_right">
		<div class="right_1">
			<div class="right_1_left">
				空间名称
			</div>
			<div class="right_1_right">
				删除
			</div>
			<div class="right_1_right">
				页面历史
			</div>
			<div class="right_1_right">
				编辑
			</div>
		</div>
		<div class="right_2">
			<div class="right_2_creator">
				创建者：xyh
			</div>
			<div class="right_2_time">
				最后一次修改时间：2019-04-24
			</div>
		</div>
		<div class="right_3">
			<div class="right_3_item">
				Welcome to your new space!
			</div>
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