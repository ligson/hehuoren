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
							<li><a id="myTabLiA1" href="<%=request.getContextPath()%>/admin/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'AccountTXApply'}]">财务提现</a></li>
							<li><a id="myTabLiA2" href="<%=request.getContextPath()%>/admin/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'UserLevelHHR'}]">申请成为合伙人</a></li>
						</ul>
</body>
</html>