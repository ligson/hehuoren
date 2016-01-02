<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/ui.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/help.css"/>

<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<title>帮助文档 - 爱尔特合伙人后台</title>
<script type="text/javascript">

</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<c:set var="g8Name" value="apply_tixian_number" scope="request"></c:set>
	<c:set var="g8" value="${applicationScope.globalMap[requestScope.g8Name]}"></c:set>
	
	<c:set var="g12Name" value="shopRegist_spending" scope="request"></c:set>
	<c:set var="g12" value="${applicationScope.globalMap[requestScope.g12Name]}"></c:set>
	
	
	<c:set var="g11Name" value="shopLevel221_price" scope="request"></c:set>
	<c:set var="g11" value="${applicationScope.globalMap[requestScope.g11Name]}"></c:set>
	
	<c:set var="g13Name" value="userLevel2vip_price" scope="request"></c:set>
	<c:set var="g13" value="${applicationScope.globalMap[requestScope.g13Name]}"></c:set>
	
	
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="bodyLeftMenu.jsp">
					<jsp:param value="userMust" name="leftMenu"/>
				</jsp:include>				
			</div>
			<div id="layout_bodyI_r">
				<div id="layout_bodyI_r_center">
					<div id="bodyR_head">
						<h2>用户必完善信息</h2>
					</div>
					<div id="bodyR_content">
						<div class="hgroup">
							<div class="top">
								<h2>基本信息</h2>
							</div>
							<div class="bot">
								<ul>
									<li>必完善信息有昵称、电话等基础信息</li>
									<li>如没有绑定手机则无法接收短信提醒，同时无法查看系统默认的财务密码（即无法操作财务账号）</li>
								</ul>
							</div>
						</div>
						<div class="hgroup">
							<div class="top">
								<h2>财务信息</h2>
							</div>
							<div class="bot">
								<ul>
									<li>如财务密码</li>
									<li>如果不完善财务信息，则无法进行充值、提现申请等操作。</li>
								</ul>
							</div>
						</div>
						
					
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>