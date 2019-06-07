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
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
</head>
<body class="body">
<script type="text/javascript">

    //获取页面类型
    function getSpaceType() {
        if((${requestScope.spacePO.type}) == 1){
            $("#sel").val(1);
        } else if((${requestScope.spacePO.type}) == 2){
            $("#sel").val(2);
        }else if((${requestScope.spacePO.type}) == 3){
            $("#sel").val(3);
        }
    }
    window.onload =function(){getSpaceType();}
    // 编辑页面
    function doEdit(number,write) {
        if(write == 0){
            $("#updateWrite"+number).val(0)
        } else {
            $("#updateWrite"+number).val(1)
        }
        $("#baocun"+number).css('display','block');
        $('#kedu2'+number).css('display','block');
        $('#kexie2'+number).css('display','block');
        $('#xiugai'+number).css('display','none');
        $('#kedu1'+number).css('display','none');
        $('#kexie1'+number).css('display','none');
    }

    // 取消页面编辑
    function doSave(number) {
        //判断是否可以修改
        var size = ${fn:length(requestScope.userRight)}
        if(size == 1 && $('#updateWrite'+number).val() == "0"){
            //单条数据不允许修改
            alert("当前仅有一个用户可编辑，不允许修改！")
        } else{
            //多条数据，修改第一条时
            if(number == 1 && $('#updateWrite1').val() == "0" && $('#kexie12').text().trim() == "不可以"){
                alert("当前仅有一个用户可编辑，不允许修改！");
            } else {
                location.href = "/space/updateSpaceRight?userName=${requestScope.userPO.name}&spaceId=${requestScope.spacePO.id}&updateUserName="+
                    $('#updateUserName'+number).val()+"&updateRead="+$('#updateRead'+number).val()+"&updateWrite="+$('#updateWrite'+number).val();
            }
        }
    }

    function changeType() {
        location.href = "/space/updateSpaceRightType?spaceId=${requestScope.spacePO.id}&userName=${requestScope.userPO.name}&type="+$('#sel').val();
    }
    function newRead(number) {
        if($('#updateRead'+number).val() == "不可以"){
            $('#updateWrite'+number).val("不可以");
        }
    }

    function newWrite(number) {
        if($('#updateWrite'+number).val() == "可以" && $('#updateRead'+number).val() == "不可以"){
            alert("该用户没有读权限，不可拥有写权限，请检查后再修改！");
            $('#updateWrite'+number).val("不可以");
        }
    }
    function addUser() {
        location.href = "/space/addSpaceRightUser?spaceId=${requestScope.spacePO.id}&userName=${requestScope.userPO.name}&updateUserName="+$('#addUser').val();
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
            <a href="/jsp/createSpace.jsp?userName=${requestScope.userPO.name}">创建空间</a>
<%--
            <a href="/jsp/createChildPage.jsp?spaceName=${requestScope.spacePO.name}&pageName=${requestScope.pagePO.name}&pageId=${requestScope.pagePO.id}&userName=${requestScope.userPO.name}">创建页面</a>
--%>
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
		<div class="left_page_tree" style="position:relative; height:250px; overflow:auto">
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
            <div class="left_page" style="position:relative; height:250px; overflow:auto">
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
                ${requestScope.spacePO.name}
            </div>
			<div class="page_return">
                <a href="/space/getSpaceBySpaceId?spaceId=${requestScope.spacePO.id}&userName=${requestScope.userPO.name}" style="color: black;text-decoration: none">
                    返回
                </a>
			</div>
		</div>
		<div class="page_rights_list">
			权限列表
		</div>
        <c:choose>
            <c:when test="${requestScope.type == 2 || requestScope.type == 3}">
                <div class="power_search">
                    <input type="text" name="addUser" placeholder="请输入您要添加的人..." id="addUser">
                    <button type="submit" onclick="addUser()">添加</button>
                </div>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.msg == 1}">
                <div class="power_search" style="margin-top: 10px">
                    添加失败，查不到该用户！
                </div>
            </c:when>
            <c:when test="${requestScope.msg == 2}">
                <div class="power_search" style="margin-top: 10px">
                    添加失败，该用户已经拥有权限！
                </div>
            </c:when>
            <c:when test="${requestScope.msg == 3}">
                <div class="power_search" style="margin-top: 10px">
                    添加成功！
                </div>
            </c:when>
        </c:choose>
		<div class="space_operate_record">
			<div style="position:relative; height:500px; overflow:auto">
                <select id="sel">
                    <option value="1">所有人可读可写</option>
                    <option value="2">所有人可读，部分人可写</option>
                    <option value="3">部分人可读，部分人可写</option>
                </select>
                <p>
                    <a onclick="changeType()" style="color: blue;text-decoration: none">
                        修改空间类型
                    </a>

                </p>
                <c:choose>
                    <c:when test="${requestScope.type == 2 || requestScope.type == 3}">
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
                                    <td class="page_page_rights_username">
                                        ${bean.userName}
                                        <input type='hidden' name="userName" id="updateUserName${bean.number}" value ='${bean.userName}'/>
                                    </td>
                                    <td class="page_page_rights_read" id="kedu1${bean.number}">
                                        可以
                                    </td>
                                    <td class="page_page_rights_read" id="kedu2${bean.number}" style="display: none">
                                        <select id="updateRead${bean.number}" onChange="newRead(${bean.number})">
                                            <option>可以</option>
                                            <c:choose>
                                                <c:when test="${requestScope.type == 3}"><!-- 当类型为2时，可读只能为可以，类型为3时，可读可以为可以或不可以-->
                                                    <option>不可以</option>
                                                </c:when>
                                            </c:choose>--%>
                                        </select>
                                    </td>
                                    <td class="page_page_rights_wirte" id="kexie1${bean.number}">
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
                                    <td class="page_page_rights_wirte" id="kexie2${bean.number}" style="display: none">
                                        <select id="updateWrite${bean.number}" onchange="newWrite(${bean.number})">
                                            <option value="1">可以</option>
                                            <option value="0">不可以</option>
                                        </select>
                                    </td>
                                    <td class="page_rights_change" id="xiugai${bean.number}" onclick="doEdit(${bean.number},${bean.writeId})">
                                        修改
                                    </td>
                                    <td class="page_rights_change" id="baocun${bean.number}" style="display: none" onclick="doSave(${bean.number})">
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