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
	<c:set var="g15Name" value="hhr_syq_timeLong" scope="request"></c:set>
	<c:set var="g15" value="${applicationScope.globalMap[requestScope.g15Name]}"></c:set>
	
	
	<c:set var="g16Name" value="hhr_syq_numberFS" scope="request"></c:set>
	<c:set var="g16" value="${applicationScope.globalMap[requestScope.g16Name]}"></c:set>
	
	<c:set var="g7Name" value="auction_noPay_day_day_pay" scope="request"></c:set>
	<c:set var="g7" value="${applicationScope.globalMap[requestScope.g7Name]}"></c:set>
	
	<c:set var="g1Name" value="paimai_daojishi" scope="request"></c:set>
	<c:set var="g1" value="${applicationScope.globalMap[requestScope.g1Name]}"></c:set>
	
	
	<c:set var="g2Name" value="paimai_jiashi" scope="request"></c:set>
	<c:set var="g2" value="${applicationScope.globalMap[requestScope.g2Name]}"></c:set>
	
	<c:set var="g10Name" value="shop_ap_tuijian_maxNum" scope="request"></c:set>
	<c:set var="g10" value="${applicationScope.globalMap[requestScope.g10Name]}"></c:set>
	
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="bodyLeftMenu.jsp">
					<jsp:param value="auction" name="leftMenu"/>
				</jsp:include>				
			</div>
			<div id="layout_bodyI_r">
				<div id="layout_bodyI_r_center">
					<div id="bodyR_head">
						<h2>合伙人机制</h2>
					</div>
					<div id="bodyR_content">
						<div class="hgroup">
							<div class="top">
								<h2>1、如何成为合伙人</h2>
							</div>
							<div class="bot">
								<ul>
									<li>1) 如果系统开启了绿色通道，则不需要用户事先购买一次产品，直接可申请成为合伙人。</li>
									<li>2) 如果系统尚未开启绿色通道，则需要用户事先购买一次产品，才有资格提出成为合伙人申请</li>
									<li>3) 当前系统绿色通道状态：
										<c:if test='${g14.myValue=="1"}'>已经开启</c:if>
										<c:if test='${g14.myValue!="1"}'>已经关闭</c:if>
									</li>
									<li>
										4) 一旦审核通过，则改用户成为试用期的合伙人。
									</li>
								</ul>
							</div>
						</div>
						
						<div class="hgroup">
							<div class="top">
								<h2>2、合伙人试用期机制</h2>
							</div>
							<div class="bot">
								<ul>
									<li>
										1) 一旦审核通过，则改用户成为试用期的合伙人。
									</li>
									<li>
										2) 试用期期限为${g15.myValue}天
									</li>		
									<li>
										3) 试用期期间，必须积累${g16.myValue}个粉丝，否则试用期过，则降为普通用户。
									</li>	
									<li>
										4) 即使在试用期期间，已经积累足粉丝数，也必须是在试用期到期才可转为正式合伙人。。
									</li>								
								</ul>
							</div>
						</div>
						
							
						<div class="hgroup">
							<div class="top">
								<h2>3、合伙人提成绩算</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto;">1) 合伙人的财务账户中存在销售佣金和宣传佣金</li>
									<li style="height:auto;">2) 
										销售佣金：如一个产品网店普通价格为100元，合伙人价格为80元，您的粉丝中有一人购买了该产品，则您会有相应的销售佣金。<br/>
										销售佣金的计算方式是：每个产品的销售佣金提成百分比x粉丝购买的订单金额
									</li>
									<li style="height:auto;">3) 
										宣传佣金：每增加一位粉丝，您会收到系统给您的${g19.myValue}元作为宣传佣金。
										但是可提现的宣传佣金最多不能超过当前财务账户中销售佣金的${g20.myValue}%
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