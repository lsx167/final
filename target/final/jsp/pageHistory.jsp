<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="../css/main.css" type="text/css">
	<link rel="stylesheet" href="../css/pageHistory.css" type="text/css">
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
            <a href="/jsp/createSpace.jsp">创建空间</a>
            <a href="/jsp/createRootPage.jsp?spaceName=${requestScope.spacePO.name}">创建页面</a>
        </div>
    </div>
    <button class="create_btn">
        <a href="/user/logout" style="color: white;text-decoration: none;margin-left: 50px">
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
                                <a href="/space/getSpaceBySpaceId?spaceId=${bean.id}" style="color: blue;text-decoration: none">
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
                                <a href="/page/getPageByPageId?pageId=${bean.id}" style="color: blue;text-decoration: none">
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
		<div class="right_title">
			<div class="page_name">
                页面名称
            </div>
			<div class="page_return">
				返回
			</div>
		</div>
		<div class="space_operate_record">
			<table>
				<tr>
					<td class="page_operate_record_version">版本号</td>
					<td class="page_operate_record_content">页面操作记录</td>
					<td class="page_operate_record_time">操作时间</td>
					<td class="page_operate_record_username">操作人</td>
					<td class="page_operate_record_back">版本回滚</td>
				</tr>
				<c:forEach begin="0" end="5">
			        <tr>
						<td class="page_operate_record_version">
							v1.0
						</td>
			            <td class="page_operate_record_content">
			                123
			            </td>
						<td class="page_operate_record_time">
						    2334
						</td>
						<td class="page_operate_record_username">
							username
						</td>
						<td class="page_operate_record_back">
							回滚
						</td>
			        </tr>
			    </c:forEach>
			</table>
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
<script type="text/javascript">
  /*// 弹窗
  function showWindow() {
    $('#showdiv').show();  //显示弹窗
    $('#cover').css('display','block'); //显示遮罩层
    $('#cover').css('height',document.body.clientHeight+'px'); //设置遮罩层的高度为当前页面高度
  }
  // 关闭弹窗
  function closeWindow() {
    $('#showdiv').hide();  //隐藏弹窗
    $('#cover').css('display','none');   //显示遮罩层
  }*/
</script>
</body>
</html>