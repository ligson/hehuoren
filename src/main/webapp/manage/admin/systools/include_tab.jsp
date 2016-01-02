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
							<li><a id="myTabLiA1" href="<%=request.getContextPath()%>/admin/dataBackup/findAlls">数据备份</a></li>
							<li style="display:none;"><a id="myTabLiA3" href="<%=request.getContextPath()%>/admin/adv/findAdvertisements">广告管理</a></li>
							<li style="display:none;"><a id="myTabLiA4" href="<%=request.getContextPath()%>/admin/sms/findSMSRecords">短信接口管理</a></li>
							<li style="display:none;"><a id="myTabLiA5" href="<%=request.getContextPath()%>/admin/static/index">静态化管理</a></li>
						</ul>
</body>
</html>