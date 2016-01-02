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
	
	<c:set var="g19Name" value="addFensi_totalPrice" scope="request"></c:set>
	<c:set var="g19" value="${applicationScope.globalMap[requestScope.g19Name]}"></c:set>
	
	<c:set var="g20Name" value="xcPrice_baifenbi_xsPrice" scope="request"></c:set>
	<c:set var="g20" value="${applicationScope.globalMap[requestScope.g20Name]}"></c:set>
	
	
	
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="bodyLeftMenu.jsp">
					<jsp:param value="account" name="leftMenu"/>
				</jsp:include>				
			</div>
			<div id="layout_bodyI_r">
				<div id="layout_bodyI_r_center">
					<div id="bodyR_head">
						<h2>财务帮助</h2>
					</div>
					<div id="bodyR_content">
						<div class="hgroup">
							<div class="top">
								<h2>1、系统财务系统概要</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">1）系统内有自己的财务系统，只需要在充值与提现时候与外界进行通信（如充值、提现），其余的业务逻辑均使用系统内财务</li>
									<li style="height:auto">2）本系统与外界通信的方案选择微信支付、支付宝</li>
									<li style="height:auto">3）提现需要提出申请，待管理员审核通过，需管理员人工进行转账。</li>
								</ul>
							</div>
						</div>
						
						
						<div class="hgroup">
							<div class="top">
								<h2>2、使用财务系统前需要完善的资料</h2>
							</div>
							<div class="bot">
								<ul>
									<li>1）用户需绑定手机号，因为只有绑定手机号才能查看系统默认为您生成的财务密码</li>
									<li style="height:auto">2）完善财务密码，将系统默认给您生成的财务密码修改为自己能识别记忆的密码，并妥善保管</li>
								</ul>
							</div>
						</div>
						
						<div class="hgroup">
							<div class="top">
								<h2>3、财务账户内的总额、可用余额、冻结金额</h2>
							</div>
							<div class="bot">
								<ul>
									<li>1）财务账户内有总额、可用余额、冻结金额三者基本信息。总额=冻结资金+可用余额</li>
									<li style="height:auto">2）什么是冻结资金？保证金的金额形式。如：用户申请提现需要冻结相应资金。</li>
								</ul>
							</div>
						</div>
						
						<div class="hgroup">
							<div class="top">
								<h2>4、账户提现申请</h2>
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
								<h2>5、合伙人的销售佣金和宣传佣金</h2>
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