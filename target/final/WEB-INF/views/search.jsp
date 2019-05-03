<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="../css/search.css" type="text/css">
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
    <button class="create_btn" onclick="showWindow()">创建</button>	
    <button class="create_btn">退出</button>
    <div class="header_user">
        <img src="../img/yonghu1.png" style="max-height: 30px;margin-top: 5px;border:none;"/>
    </div>
    <div class="bar1">
        <form action="/space/getSpaceBySearchContent" method="post">
            <input type="text" name="spaceContent" placeholder="请输入您要搜索的内容...">
            <button type="submit"></button>
        </form>
    </div>
</header>
<div class="main">
	<div class="search_bar">
		<div class="search_text">
			搜索
		</div>
		<div class="search_form">
			<form action="/space/getSpaceBySearchContent" method="post">
			    <input type="text" name="spaceContent" placeholder="请输入您要搜索的内容...">
			    <button type="submit">
					<img src="../img/sousuo.png" style="max-height: 30px;margin-top: 5px;border: none;" />
				</button>
			</form>
		</div>
	</div>
	<div class="search_list">
		<div class="page_nav">
        <c:choose>
            <c:when test="${page.totalPageCount <= 10}"><!-- 如果总页数小于10，则全部显示 -->
                <c:set var="begin" value="1"></c:set>
                <c:set var="end" value="${page.totalPageCount}"></c:set>
            </c:when>
            <c:when test="${page.pageNow <= 5}"><!-- 如果当前页小于5，则显示1-10页 -->
                <c:set var="begin" value="1"></c:set>
                <c:set var="end" value="10"></c:set>
            </c:when>
            <c:otherwise><!-- 否则，显示前5页和后5页，保证当前页在中间 -->
                <c:set var="begin" value="${page.pageNow-5}"></c:set>
                <c:set var="end" value="${page.pageNow+5}"></c:set>
                <c:if test="${end > page.totalPageCount}"><!-- 如果end值小于总的记录数，则显示最后10页 -->
                    <c:set var="end" value="${page.totalPageCount}"></c:set>
                    <c:set var="begin" value="${end-10}"></c:set>
                </c:if>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="0" end="${page.totalCount % page.pageSize}" items="${requestScope.spacePOList}" var="spaceItem">
			<div class="spaceItem_name">
                空间名称：<a href="/space/getSpaceBySpaceId?spaceId=${spaceItem.id}">${spaceItem.name}</a>
			</div>
            <div class="spaceItem_describe">
                空间描述：${spaceItem.describe}
            </div>
        </c:forEach>

        <c:choose>
            <c:when test="${page.pageNow != 1}"><!-- 如果当前页为1，则不显示首页和上一页 -->
                <a href="?pageNow=1">首页</a> 
                <a href="?pageNow=${page.pageNow-1}">上一页</a>
            </c:when>
        </c:choose>
        <!-- 遍历页码 -->
        <c:forEach begin="${begin}" end="${end}" var="index">
            <c:choose>
                <c:when test="${page.pageNow == index}"><!-- 如果为当前页，则特殊显示 -->
                    <a style="height:24px; margin:0 3px; border:none; background:#C00;">${index}</a>
                </c:when>
                <c:otherwise><!-- 否则，普通显示 -->
                    <a href="?pageNow=${index}">${index}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${page.pageNow != page.totalPageCount }"><!-- 如果当前页为总的记录数，则不显示末页和下一页 -->
                <a href="?pageNow=${page.pageNow+1 }">下一页</a> 
                <a href="?pageNow=${page.totalPageCount }">末页</a>
            </c:when>
        </c:choose>
        共${page.totalPageCount }页，${page.totalCount }条记录 到第<input
            value="${page.pageNow }" name="pn" id="pn_input" />页 <input
            id="pn_btn" type="button" value="确定">
        
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
            //为按钮绑定一个单击响应函数
            $("#pn_btn").click(function() {
                //获取到要跳转的页码
                var pageNow = $("#pn_input").val();
                //通过修改window.location属性跳转到另一个页面
                window.location = "?pageNow=" + pageNow;
            });
        </script>
</body>
</html>