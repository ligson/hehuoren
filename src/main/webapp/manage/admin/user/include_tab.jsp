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
							<li><a id="myTabLiA1" href="<%=request.getContextPath()%>/admin/user/finds?queryArgs=[{'key':'userLevelId','value':'1'}]">普通用户</a></li>
							<li><a id="myTabLiA2" href="<%=request.getContextPath()%>/admin/user/finds?queryArgs=[{'key':'userLevelId','value':'2'}]">试用期合伙人</a></li>
							<li><a id="myTabLiA3" href="<%=request.getContextPath()%>/admin/user/finds?queryArgs=[{'key':'userLevelId','value':'3'}]">正式合伙人</a></li>
							<li style="display:none;"><a id="myTabLiA4" href="<%=request.getContextPath()%>/admin/user/toRegist">添加用户(测试)</a></li>
						</ul>
</body>
</html>