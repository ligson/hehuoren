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
							<li><a id="myTabLiA1" href="<%=request.getContextPath()%>/admin/security/toPassword">登录密码</a></li>
							<li style="display:none;"><a id="myTabLiA2" href="<%=request.getContextPath()%>/admin/security/toAccountpassword">财务密码</a></li>
							<li style="display:none"><a id="myTabLiA3" href="<%=request.getContextPath()%>/admin/security/toAccount">绑定支付宝</a></li>
							<li><a id="myTabLiA4" href="<%=request.getContextPath()%>/admin/security/toTelphone">绑定手机</a></li>
							<li style="display:none;"><a id="myTabLiA5" href="<%=request.getContextPath()%>/admin/security/toLookPassword">查看财务密码</a></li>
						</ul>
</body>
</html>