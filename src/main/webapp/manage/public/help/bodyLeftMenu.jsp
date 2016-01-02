<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
$(document).ready(function(){
	$("#menuBar").find("a").removeClass("selected");
	var curVal=$("#leftMenuInput").val();
	var curA=$("#"+curVal);
	curA.addClass("selected");
});
</script>
</head>

<body >
<input type="hidden" value="${param.leftMenu}" id="leftMenuInput" />
				<div class="menu_box" id="menuBar">
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu help"></i>系统帮助
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/apply.jsp" id="apply">申请/审批</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/userMust.jsp" id="userMust">用户基础信息</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/auction.jsp" id="auction">合伙人机制</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/order.jsp" id="order">订单帮助</a>
						</dd>
						
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/account.jsp" id="account">财务帮助</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/sms.jsp" id="sms">通知</a>
						</dd>
						<c:if test='${!empty sessionScope.myAdmin}'>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/expand.jsp" id="expand">产品扩展属性</a>
						</dd>
						</c:if>
					</dl>
				</div>
</body>
</html>