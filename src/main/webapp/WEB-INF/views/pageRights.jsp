<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="../../css/main.css" type="text/css">
	<link rel="stylesheet" href="../../css/pageRigths.css" type="text/css">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/js/ajaxfileupload.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="body">
<script type="text/javascript">
    // 编辑页面
    function doEdit() {
        $('#baocun').css('display','block');
        $('#kedu2').css('display','block');
        $('#kexie2').css('display','block');
        $('#xiugai').css('display','none');
        $('#kedu1').css('display','none');
        $('#kexie1').css('display','none');
    }

    // 取消页面编辑
    function doSave() {
        $('#baocun').css('display','none');
        $('#kedu2').css('display','none');
        $('#kexie2').css('display','none');
        $('#xiugai').css('display','block');
        $('#kedu1').css('display','block');
        $('#kexie1').css('display','block');

        /*location.href = "/page/updatePageRight?userName=${requestScope.userPO.name}&pageId=${requestScope.pagePO.id}&updateUserName=<%--${requestScope.userRight.get(0).userName}--%>"+
            /!*$('#updateUserName').val()+*!/"&updateRead="+$('#updateRead').val()+"&updateWrite="+$('#updateWrite').val();*/
    }

    function newRead() {
        if($('#updateRead').val() == "不可以"){
            $('#updateWrite').val("不可以");
        }
    }

    function newWrite() {
        if($('#updateWrite').val() == "可以" && $('#updateRead').val() == "不可以"){
            alert("该用户没有读权限，不可拥有写权限，请检查后再修改！");
            $('#updateWrite').val("不可以");
        }
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
                <img src="../../img/wujiaoxing.png" style="max-height: 30px;margin-top: 5px;border:none;"/>
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
                                <c:forEach begin="1" end="${bean.depth}">
                                    &nbsp;&nbsp;
                                </c:forEach>
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
		<div class="right_title">
			<div class="page_name">
                ${requestScope.pagePO.name}
            </div>
			<div class="page_return">
                <a href="/page/getPageByPageId?pageId=${requestScope.pagePO.id}&userName=${requestScope.userPO.name}" style="color: black;text-decoration: none">
                    返回
                </a>
			</div>
		</div>
		<div class="page_rights_list">
			权限列表
		</div>
		<div class="power_search">
            <input type="text" name="peoplename" placeholder="请输入您要添加的人...">
            <button type="submit">添加</button>
		</div>
		<div class="space_operate_record">
			<div style="position:relative; height:500px; overflow:auto">
                <c:choose>
                    <c:when test="${requestScope.type == 1}"><!-- 所有人可读可写-->
                        当前文档所有人可读可写
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.type == 2 || requestScope.type == 3}">
                        <c:choose>
                            <c:when test="${requestScope.type == 2}"><!-- 所有人可读部分人可写-->
                                当前文档所有人可读，部分人可写
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${requestScope.type == 3}"><!-- 部分人可读部分人可写-->
                                当前文档部分人可读，部分人可写
                            </c:when>
                        </c:choose>
                        <table>
                            <hr/>
                            <tr>
                                <th style="text-align: left" class="page_page_rights_read">用户账号</th>
                                <th style="text-align: left" class="page_page_rights_read">可以访问</th>
                                <th style="text-align: left" class="page_page_rights_wirte">可以编辑</th>
                                <th style="text-align: left" class="page_rights_change">修改</th>
                            </tr>
                            <c:forEach items="${requestScope.userRight}" var="bean">
                                <tr>
                                    <td class="page_page_rights_username" id="updateUserName">
                                            ${bean.userName}
                                    </td>
                                    <td class="page_page_rights_read" id="kedu1">
                                        可以
                                    </td>
                                    <td class="page_page_rights_read" id="kedu2" style="display: none">
                                        <select id="updateRead" onChange="newRead()">
                                            <option>可以</option>
                                            <option>不可以</option>
                                        </select>
                                    </td>
                                    <td class="page_page_rights_wirte" id="kexie1">
                                        <c:choose>
                                            <c:when test="${bean.writeId == 1}"><!-- 如果用户有写权限-->
                                                可以
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${bean.writeId == 0}"><!-- 如果用户没有写权限-->
                                                不可以
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td class="page_page_rights_wirte" id="kexie2" style="display: none">
                                        <select id="updateWrite" onchange="newWrite()">
                                            <option>可以</option>
                                            <option>不可以</option>
                                        </select>
                                    </td>
                                    <td class="page_rights_change" id="xiugai" onclick="doEdit()">
                                        修改
                                    </td>
                                    <td class="page_rights_change" id="baocun" style="display: none" onclick="doSave()">
                                        保存
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                </c:choose>
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