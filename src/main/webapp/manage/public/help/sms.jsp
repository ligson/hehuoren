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
					<jsp:param value="sms" name="leftMenu"/>
				</jsp:include>				
			</div>
			<div id="layout_bodyI_r">
				<div id="layout_bodyI_r_center">
					<div id="bodyR_head">
						<h2>短信发送规则</h2>
					</div>
					<div id="bodyR_content">
						<div class="hgroup">
							<div class="top">
								<h2>1、通知方式</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										本系统采用短信通知、微信模板消息通知（前提是用户必须关注了微信公共平台）
									</li>
								</ul>
							</div>
						</div>
						
						<div class="hgroup">
							<div class="top">
								<h2>2、申请审核完毕之后，发送短信通知用户</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										本系统中有以下申请审批处：
										<dl class="smallDl">
											<dd>A）用户提现申请。</dd>
											<dd>B）申请成为合伙人。</dd>
										</dl>
									</li>
								</ul>
							</div>
						</div>
						
						
						
						<div class="hgroup">
							<div class="top">
								<h2>3、订单通知</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										1）用户下单成功并且支付成功，则通知用户和管理员
									</li>
								</ul>
							</div>
						</div>
						
							<div class="hgroup">
							<div class="top">
								<h2>4、财务通知</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										1）用户充值成功，发送信息通知
									</li>
									<li style="height:auto">
										2）用户的提现申请审核通过，则发送信息通知
									</li>
									
								</ul>
							</div>
						</div>
						
						<div class="hgroup">
							<div class="top">
								<h2>5、合伙人佣金通知</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										1）当合伙人的粉丝有新订单且支付成功，则发送恭喜通知。
									</li>
									
								</ul>
							</div>
						</div>
						<div class="hgroup">
							<div class="top">
								<h2>6、来款通知</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										1）系统初始化之时，管理员登录后台，先扫描二维码，接入管理员微信，用来收取通知
									</li>
									<li style="height:auto">
										2）当有用户进行充值，表明系统来款，则管理员收到此通知
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