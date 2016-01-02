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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/index.css"/>

<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<title>爱尔特合伙人平台管理后台</title>
<script type="text/javascript">

</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="bodyLeftMenu.jsp">
					<jsp:param value="index" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
			
				<div id="layout_bodyI_r_center">
					
					<div id="roleInfoBox">
					<div id="roleB_l">
						<div class="roleBL_imgBox" style="display:none;">
						</div>
						<div class="roleBL_infoBox">
							<div class="roleBLIB_t">
								<h1 class="shopName">${sessionScope.myAdmin.name}</h1>
								<span>
									超级管理员
								</span>
							</div>
							<div class="roleBLIB_b">
								<ul>
									<c:if test='${requestScope.account.withdrawalsPwd==null || requestScope.account.withdrawalsPwd==""}'>
										<li>
											<span class="iocRZ password"></span>
											<span>财务密码(未设置)</span>
											<span><a href="<%=request.getContextPath()%>/admin/security/toAccountpassword">去设置</a></span>
											<span><a href="<%=request.getContextPath()%>/admin/security/toLookPassword">查看</a></span>
										</li>
									</c:if>
									<c:if test='${requestScope.account.withdrawalsPwd!=null && requestScope.account.withdrawalsPwd!=""}'>
										<li>
											<span class="iocRZ password"></span>
											<span>财务密码(已设置)</span>
											<span><a href="<%=request.getContextPath()%>/admin/security/toAccountpassword">去修改</a></span>
											<span><a href="<%=request.getContextPath()%>/admin/security/toLookPassword">查看</a></span>
										</li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
					<div id="roleB_r">
						
					</div>
				</div>
				
				
				<div id="accountBox">
					<div class="quyukuang tongjiBox" >
							<div class="title">
						        <h3>账户余额</h3>
						    </div>
						    <div class="content">
						    	<div class="accountInfo">
						    		<div class="everyNumbox">
							    		<div class="everyNB_txt">账户总额</div>
							    		<div class="everyNB_num">${fn:replace(requestScope.account.totalPrice,".0","")}</div>
							    	</div>
							    	<div class="everyNumbox">
							    		<div class="everyNB_txt">冻结数额</div>
							    		<div class="everyNB_num">${fn:replace(requestScope.account.frozenPrice,".0","")}</div>
							    	</div>
							    	<div class="everyNumbox last">
							    		<div class="everyNB_txt">可用资金</div>
							    		<div class="everyNB_num">${fn:replace(requestScope.account.availablePrice,".0","")}</div>
							    	</div>
						    	</div>
						    	<div class="accountOper">
						    		<ul>
						    			<li><a href="<%=request.getContextPath()%>/admin/account/toRecharge">充值</a></li>
						    			<li><a href="<%=request.getContextPath()%>/admin/account/toWithdrawals">提现</a></li>
						    		</ul>
						    	</div>
						    </div>
					</div>
				</div>
				
				<c:if test='${sessionScope.myAdmin.wxId==""||sessionScope.myAdmin.wxId==null}'>
					<div>
						<h2>管理员请用你的微信账号扫描下面的二维码，以便于生成管理员的微信号，方便管理员接收到通知信息</h2>
						<img src="<%=request.getContextPath()%>/img/user/admin2weima1.png" style="width:400px;"/>
					</div>
				</c:if>
				
				<div class="countBox">
					<div class="left">
						<h2>待审统计</h2>
					</div>
					<div class="right">
						
						<div class="everyCountBox">
								<div class="everyCount_l">
									用户提现申请：
								</div>
								<div class="everyCount_r">
									<c:set var="key" value="AccountTXApply"></c:set>
									<c:set var="count" value="${requestScope.applyKey_count_map[key]}"></c:set>
									<c:if test='${empty count}'>
										<span class="countTxt">0</span>
									</c:if>
									<c:if test='${!empty count}'>
										<span class="countTxt">${count}</span>
									</c:if>
								</div>
						</div>
						<div class="everyCountBox">
								<div class="everyCount_l">
									用户申请合伙人：
								</div>
								<div class="everyCount_r">
									<c:set var="key" value="UserLevelHHR"></c:set>
									<c:set var="count" value="${requestScope.applyKey_count_map[key]}"></c:set>
									<c:if test='${empty count}'>
										<span class="countTxt">0</span>
									</c:if>
									<c:if test='${!empty count}'>
										<span class="countTxt">${count}</span>
									</c:if>
								</div>
						</div>
						
					</div>
				</div>
				
				<div class="countBox">
					<div class="left">
						<h2>订单统计</h2>
					</div>
					<div class="right">
						<div class="everyCountBox">
							<div class="everyCount_l">
								待付款：
							</div>
							<div class="everyCount_r">
								<span class="countTxt">${requestScope.orderCount_WAITPAY}</span>
							</div>
						</div>
						<div class="everyCountBox">
							<div class="everyCount_l">
								待取货：
							</div>
							<div class="everyCount_r">
								<span class="countTxt">${requestScope.orderCount_PAYED}</span>
							</div>
						</div>
						<div class="everyCountBox">
							<div class="everyCount_l">
								成功取货：
							</div>
							<div class="everyCount_r">
								<span class="countTxt">${requestScope.orderCount_PICKUPED}</span>
							</div>
						</div>
						<div class="everyCountBox">
							<div class="everyCount_l">
								关闭的订单：
							</div>
							<div class="everyCount_r">
								<span class="countTxt">${requestScope.orderCount_CLOSE}</span>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="countBox">
					<div class="left">
						<h2>短信余额</h2>
					</div>
					<div class="right">
						<div class="everyCountBox">
							<div class="everyCount_l">
								剩余条数：
							</div>
							<div class="everyCount_r">
								<span class="countTxt">${requestScope.duanxin.overage}</span>
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