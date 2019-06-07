<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="../css/main.css" type="text/css">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/js/ajaxfileupload.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="body">
<script type="text/javascript">
    function deletePage() {
        if((${requestScope.deletePage}) == 1){
            alert("删除成功");
        }
        if((${requestScope.deletePage}) == 0){
            alert("删除失败");
        }
    }
    window.onload =function(){deletePage();}
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
<%--            <a href="#">${requestScope.th}</a>
            <a href="#">菜鸟教程 2</a>
            <a href="#">菜鸟教程 3</a>--%>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">创建</button>
        <div class="dropdown-content">
            <a href="/jsp/createSpace.jsp?userName=${requestScope.userPO.name}">创建空间</a>
            <c:choose>
                <c:when test="${requestScope.writePermission == 1}"><!-- 如果用户有写权限-->
                    <a href="/jsp/createRootPage.jsp?spaceName=${requestScope.spacePO.name}&userName=${requestScope.userPO.name}">创建页面</a>
                </c:when>
            </c:choose>
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
		    <div class="left_page" style="position:relative; height:250px; overflow:auto">
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
        <div class="right_1">
            <div class="right_1_left">
                ${requestScope.spacePO.name}
            </div>
            <c:choose>
                <c:when test="${requestScope.originPermission == 1}"><!-- 如果用户是创建者-->
                    <div class="right_1_right">
                        <div class="left_setting_img">
                            <img src="../img/shezhi.png" style="max-height: 25px;margin-top:5px;border:none;"/>
                        </div>
                        <div class="left_setting_item">
                            <a href="/space/getSpaceRightById?spaceId=${requestScope.spacePO.id}&userName=${requestScope.userPO.name}" style="color: black;text-decoration: none">
                                空间配置
                            </a>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>

        <div class="right_2">
            <div class="right_2_creator">
                创建者：${requestScope.originUserPO.name}
            </div>
            <div class="right_2_time">
                最后一次修改时间：${requestScope.spaceOperateRecordPOS.get(0).operatorTime}
            </div>
        </div>
        <div class="right_3">
            <div class="right_3_item">
                ${requestScope.spacePO.describe}
            </div>
        </div>
		<div class="space_operate_record">
			<table>
				<tr>
					<td class="space_operate_record_content">空间操作记录</td>
					<td class="space_operate_record_time">操作时间</td>
				</tr>
			    <c:forEach items="${requestScope.spaceOperateRecordPOS}" var="bean">
			        <tr>
			            <td class="space_operate_record_content">
			                ${bean.operatorContent}
			            </td>
						<td class="space_operate_record_time">
						    ${bean.operatorTime}
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
</body>
</html>