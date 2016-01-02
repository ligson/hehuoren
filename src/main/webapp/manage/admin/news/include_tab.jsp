<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	var rtn=$("#rightTabInput").val();
	$("#myTabUl").find("a").removeClass("selected");
	$("#"+rtn).addClass("selected");
});
</script>
</head>
<body>
<input type="hidden" value="${param.rightTabName}" id="rightTabInput"/>
						<ul id="myTabUl">
							<li><a id="myTabLiA1" href="<%=request.getContextPath()%>/admin/news/finds">帮助文档</a></li>
							<li><a id="myTabLiA2" href="<%=request.getContextPath()%>/admin/news/toAdd_step1">添加资讯</a></li>
							<li style="display:none;"><a id="myTabLiA3" href="<%=request.getContextPath()%>/admin/newsType/finds">帮助文档分类</a></li>
						</ul>
</body>
</html>