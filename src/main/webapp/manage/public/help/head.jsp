<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function moniUserlogout(a){
	var userId=$(a).attr("userId");
	var name=$(a).attr("name");
	var password=$(a).attr("password");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/logout",
		success : function(msg) {
			myAlert(200,130,"成功!","退出成功","ok");
			$("#headermiddelBox").remove();
		}
	})
}
</script>
</head>
<body>
	<div id="layout_head" style="position: relative;top:0px;">
		<!-- 顶部 -->
		<div id="layout_headInner">
			<div id="layout_headI_l">
				<h1 class="logo"><a href="/" title=""></a></h1>
			</div>
			<div id="layout_headI_m"></div>
			<div id="layout_headI_r">
				<div id="lhr_inner">
					<div id="lhr_l">
					</div>
					<div id="lhr_c">
						<div id="lhrc_t">
						</div>
						<div id="lhrc_b">
							<a href="javascript:void(0);" class="roleName">超级管理员</a>
						</div>
					</div>
					<div id="lhr_r">
						<div id="lhrr_b">
							<a class="headioc duanxin" href="javascript:void(0);">
								<i class="headioc hongdian"></i>
							</a>
							<a href="<%=request.getContextPath()%>/admin/logout" class="logoutA">退出</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test='${!empty sessionScope.myUser}'>
		<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
		<div id="headermiddelBox" style="">
			<div id="headermiddelBoxI">
				<span>当前登录的模拟用户：</span>
				<span>${sessionScope.myUser.nicheng}</span>
				<span><a href="<%=request.getContextPath()%>/user/index" target="_blank">前去用户中心</a></span>
				<span>|</span>
				<span><a href="javascript:void(0);"  onClick="moniUserlogout(this)" name="${sessionScope.myUser.name}" password="${sessionScope.myUser.password}" userId="${sessionScope.myUser.id}">退出模拟用户</a></span>
			</div>
		</div>
		</c:if>
		</c:if>
	</div>
</body>
</html>