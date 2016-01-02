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
	<c:set var="g5Name" value="auctionTime_noSpecial" scope="request"></c:set>
	<c:set var="g5" value="${applicationScope.globalMap[requestScope.g5Name]}"></c:set>
	
	
	<c:set var="g6Name" value="auction_noPay_day" scope="request"></c:set>
	<c:set var="g6" value="${applicationScope.globalMap[requestScope.g6Name]}"></c:set>
	
	<c:set var="g7Name" value="auction_noPay_day_day_pay" scope="request"></c:set>
	<c:set var="g7" value="${applicationScope.globalMap[requestScope.g7Name]}"></c:set>
	
	<c:set var="g1Name" value="paimai_daojishi" scope="request"></c:set>
	<c:set var="g1" value="${applicationScope.globalMap[requestScope.g1Name]}"></c:set>
	
	
	<c:set var="g18Name" value="jifen_100" scope="request"></c:set>
	<c:set var="g18" value="${applicationScope.globalMap[requestScope.g18Name]}"></c:set>
	
	
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="bodyLeftMenu.jsp">
					<jsp:param value="order" name="leftMenu"/>
				</jsp:include>				
			</div>
			<div id="layout_bodyI_r">
				<div id="layout_bodyI_r_center">
					<div id="bodyR_head">
						<h2>订单帮助</h2>
					</div>
					<div id="bodyR_content">
						
						
						
						
						<div class="hgroup">
							<div class="top">
								<h2>订单的状态流程</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										1）订单有以下状态：等待付款（WAITPAY）、已付款（PAYED）、已付款（PAYED）、已取货（PICKUPED）
									</li>
									<li style="height:auto">
										2）订单的款项支付均由本系统的财务账户支付，所以请事先充值。
									</li>
								</ul>
							</div>
						</div>
						
						
						<div class="hgroup">
							<div class="top">
								<h2>订单与积分</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										1）订单金额中，每100元则奖励${g18.myValue}分作为奖励
									</li>
									<li style="height:auto">
										2）管理员可在后台修改用户的积分，用来给用户积分兑换。
									</li>
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