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
							<li><a id="myTabLiA2" href="<%=request.getContextPath()%>/admin/account/toOperation">操作账户</a></li>
							<li><a id="myTabLiA4" href="<%=request.getContextPath()%>/admin/account/userAccountList">用户账户列表</a></li>
						</ul>
</body>
</html>