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
	
	<c:set var="g14Name" value="on_hhr_lvseTongdao" scope="request"></c:set>
	<c:set var="g14" value="${applicationScope.globalMap[requestScope.g14Name]}"></c:set>
	
	
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="bodyLeftMenu.jsp">
					<jsp:param value="apply" name="leftMenu"/>
				</jsp:include>				
			</div>
			<div id="layout_bodyI_r">
				<div id="layout_bodyI_r_center">
					<div id="bodyR_head">
						<h2>申请/审批帮助</h2>
					</div>
					<div id="bodyR_content">
						<div class="hgroup">
							<div class="top">
								<h2>账户提现申请</h2>
							</div>
							<div class="bot">
								<ul>
									<li>1) 用户提现需要提出申请,提交申请之后，程序会自动冻结所欲提现的资金，待管理员审核</li>
									<li>2) 用户每个月可成功申请提现<b class="numB">${g8.myValue}</b>次。</li>
									<li>3) 管理员在审核通过之后，务必遵守信约将所提现资金打到用户支付宝账号/微信账号中</li>
									<li>4) 每次申请提现最少提现M元（M等于销售佣金+可提现的宣传佣金）</li>
									<li>5) 管理员审核不通过之后，会将所冻结的资金在本平台财务系统中解冻</li>
									<li>6) 管理员审核之后（通过/不通过），发送短信通知用户</li>
								</ul>
								<div class="exampleBox">
									例如：张三在2015-7-1日提出财务提现500元申请，程序先判断（1、账户的可用资金是否满500元。2、当月是否已经成功申请超过<b class="numB">${g8.myValue}</b>次）档次申请有没有资格。
									如果可以申请，则冻结相应提现资金500元，等待管理员接审批，管理员根据具体情况来判定是否要审核通过（如审核通过，则需要将500元打到商家支付宝账号上。）
								</div>
							</div>
						</div>
						
						
						
						<div class="hgroup">
							<div class="top">
								<h2>用户申请成为合伙人</h2>
							</div>
							<div class="bot">
								<ul>
									<li>1) 如果系统开启了绿色通道，则不需要用户事先购买一次产品，直接可申请成为合伙人。</li>
									<li>2) 如果系统尚未开启绿色通道，则需要用户事先购买一次产品，才有资格提出成为合伙人申请</li>
									<li>3) 当前系统绿色通道状态：
										<c:if test='${g14.myValue=="1"}'>已经开启</c:if>
										<c:if test='${g14.myValue!="1"}'>已经关闭</c:if>
									</li>
								</ul>
								<div class="exampleBox">
								</div>
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