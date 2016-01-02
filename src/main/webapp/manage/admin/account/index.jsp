<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/ui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/account.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<title>财务管理 - 爱尔特合伙人后台</title>
<script type="text/javascript">

</script>
</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="web-account" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>财务管理</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab_my.jsp">
							<jsp:param value="myTabLiA1" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					
					<div id="mainContent">
						<div class="tongjiBox quyukuang" style="width:50%">
							<div class="title">
						        <h3>财务余额</h3>
						    </div>
						    <div class="content">
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
						</div>
						<div class="quyukuang" style="width:48%;margin-left:10px;height:160px;display:none;">
							<div class="title">
						        <h3>财务信息</h3>
						    </div>
						    <div class="content">
						    	<div id="accountInfo">
						    		<ul>
							    		<li>支付宝账号：${requestScope.account.zhifubao}</li>
							    		<li>财务密码：
							    			<a href="<%=request.getContextPath()%>/admin/security/toLookPassword">查看</a>
							    		</li>
							    	</ul>
						    	</div>
						    </div>
						</div>
						<div class="queryArgsBox">
							<h2 class="zuijinmingxi">最近明细</h2>
						</div>
						<c:if test='${empty requestScope.accountItemList}'>
							<div class="noDataTip"></div>
						</c:if>
						<c:if test='${!empty requestScope.accountItemList}'>
							<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>编号</td>
										<td>类型</td>
										<td>金额</td>
										<td>描述</td>
										<td>日期</td>
										<td>状态</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="ai" items="${requestScope.accountItemList}">
										<tr>
											<td>${ai.markedNum}</td>
											<td>
												<c:if test='${ai.itemType=="RECHARGE"}'>充值</c:if>
													<c:if test='${ai.itemType=="SPENDING"}'>支付</c:if>
													<c:if test='${ai.itemType=="INCOME"}'>收入</c:if>
													<c:if test='${ai.itemType=="FROZEN"}'>冻结</c:if>
													<c:if test='${ai.itemType=="WITHDRAWALS"}'>提现</c:if>
											</td>
											<td>${fn:replace(ai.price,".0","")}</td>
											<td>${ai.info}</td>
											<td class="link">
												<fmt:formatDate value="${ai.relDate}" pattern="yy-MM-dd HH:mm" /> 
											</td>
											<td class="link">
												<c:if test='${ai.status=="WAIT"}'>
													<c:if test='${ai.itemType=="RECHARGE"}'>
														<form target="_blank" action="<%=request.getContextPath()%>/pay/alipay/alipayapi?whoclassName=myFrameU.user.entity.User&whoId=1"  method="post">
															<input type="hidden" value="${ai.price}" name="price"/>
															<input  type="submit" value="去充值" class="btn btnColor_1" />
														</form>
													</c:if>
													<c:if test='${ai.itemType=="SPENDING"}'>
														<input  type="button" value="去支付" class="btn btnColor_1" />
													</c:if>
												</c:if>
												<c:if test='${ai.status=="FINISH"}'>
													<c:if test='${ai.itemType=="RECHARGE"}'>充值成功</c:if>
													<c:if test='${ai.itemType=="SPENDING"}'>支付成功</c:if>
													<c:if test='${ai.itemType=="INCOME"}'>收入成功</c:if>
													<c:if test='${ai.itemType=="FROZEN"}'>冻结成功</c:if>
													<c:if test='${ai.itemType=="WITHDRAWALS"}'>提现成功</c:if>
												</c:if>
												<c:if test='${ai.status=="CLOSE"}'>
													已关闭
												</c:if>
											</td>
										</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</div>
						</c:if>
						
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
