<%@ page language="java"  import="java.util.Date,myFrameU.util.commonUtil.date.DateUtils" contentType="text/html; charset=utf-8"
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
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/ui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/account.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/ui.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/riqi.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/riqi.js"></script>

<title>财务管理 -  爱尔特合伙人后台</title>
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
					<jsp:param value="account-account" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>账户明细</h2>
					<div class="tabBox" id="bodyRH_tab">
						<ul id="myTabUl">
							<li><a id="myTabLiA2" href="<%=request.getContextPath()%>/admin/account/toOperation">操作账户</a></li>
							<li><a id="myTabLiA4" href="<%=request.getContextPath()%>/admin/account/userAccountList">用户账户列表</a></li>
							<li><a id="myTabLiA1" href="javascript:void(0)" class="selected">【${requestScope.account.whoName}】财务明细列表</a></li>
						</ul>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="bodyRC_tip">
						<p class="desc">
							<span>
								<c:if test='${requestScope.account.whoclassName=="myFrameU.shop.entity.Shop"}'>
									店铺财务：
								</c:if>
								<c:if test='${requestScope.account.whoclassName=="myFrameU.user.entity.User"}'>
									用户财务：
								</c:if>
							</span>
							<span>
								<c:if test='${requestScope.account.whoclassName=="myFrameU.shop.entity.Shop"}'>
									<a href="<%=request.getContextPath()%>/fshop/${requestScope.account.whoId}/" target="_blank">${requestScope.account.whoName}</a>
								</c:if>
								<c:if test='${requestScope.account.whoclassName=="myFrameU.user.entity.User"}'>
									${requestScope.account.whoName}
								</c:if>
							</span>
							<span>
								总余额(
									${requestScope.account.totalPrice}
								)
							</span>
							<span>
								可用余额(
									${requestScope.account.availablePrice}
								)
							</span>
							<span>
								冻结资金(
								${requestScope.account.frozenPrice}
								)
							</span>
							<span style="display:none;">
								支付宝(${requestScope.account.zhifubao})
							</span>
							<span style="display:none;">
								财务密码(${requestScope.account.withdrawalsPwd})
							</span>
						</p>
					</div>
					<div id="queryArgsBox">
						<input type="hidden" value="${requestScope.queryArgs}" class="requestQueryArgsInput"/>
						
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="明细编号" class="queryTxt" queryArgsKey="markedNum"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						
						<div class="everyQuery" >
								<span class="k"></span>
								<span>
									<span class="mySelect" width="80" status="close">
										<span class="input">明细类型</span>
										<span class="arrow"></span>
										<span class="down">
											<ul>
												<li><a href="javascript:void(0);"  value="{'key':'itemType','value':'RECHARGE'}">充值</a></li>
												<li><a href="javascript:void(0);"  value="{'key':'itemType','value':'WITHDRAWALS'}">提现</a></li>
												<li><a href="javascript:void(0);"  value="{'key':'itemType','value':'INCOME'}">收入</a></li>
												<li><a href="javascript:void(0);"  value="{'key':'itemType','value':'SPENDING'}">支出</a></li>
												<li><a href="javascript:void(0);"  value="{'key':'itemType','value':'FROZEN'}">冻结</a></li>
											</ul>
										</span>
									</span>
								</span>
						</div>
							<div class="everyQuery" >
									<span class="k"></span>
									<span>
										<span class="mySelect" width="90" status="close">
											<span class="input">状态</span>
											<span class="arrow"></span>
											<span class="down">
												<ul>
													<li><a href="javascript:void(0);"  value="{'key':'status','value':'WAIT'}">等待</a></li>
													<li><a href="javascript:void(0);"  value="{'key':'status','value':'FINISH'}">完结</a></li>
													<li><a href="javascript:void(0);"  value="{'key':'status','value':'CLOSE'}">关闭</a></li>
												</ul>
											</span>
										</span>
									</span>
							</div>
						
						<%
							String now=DateUtils.getCurrDateStr_YYYY_MM_DD();
						%>
						<div class="everyQuery">
							<span class="k" style="display:block;">明细时间：</span>
							<span>
								<span class="dateRange" queryArgsKey="relDate">
									<span class="begin riqiBox">
										<input name="beginDate" type="text" value="" class="textInput" id="beginDateInput" readonly="readonly"/>
										<i></i>
									</span>
									<span class="fenge">至</span>
									<span class="begin riqiBox">
										<input name="endDate" type="text" value="<%=now%>" class="textInput" id="endDateInput" readonly="readonly"/>
										<i></i>
									</span>
								</span>
							</span>
						</div>
						
					</div>
					
					<div id="mainContent">
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
													<c:if test='${ai.itemType=="itemType"}'>提现成功</c:if>
												</c:if>
												<c:if test='${ai.status=="CLOSE"}'>
													已关闭
												</c:if>
											</td>
										</tr>
									</c:forEach>
									
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6">
											<jsp:include page="../../util/pager.jsp"></jsp:include>
										</td>
									</tr>
								</tfoot>
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
