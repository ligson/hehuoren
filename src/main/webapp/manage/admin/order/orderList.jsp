<%@ page language="java" import="java.util.Date,myFrameU.util.commonUtil.date.DateUtils"  contentType="text/html; charset=utf-8"
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
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/user.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/ui.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/riqi.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/riqi.js"></script>

<title>订单管理  - 爱尔特合伙人后台</title>
<style type="text/css">
.tableBox thead tr td{border-top:none !important;}
.moreTr{display:none;}
.moreTr td{background:#eee;}
.moreTr td table{width:90%;margin:0 auto;}
.moreTr td table  td{background:#FFFFF0;}
</style>
<script type="text/javascript">
function findOrder(b){
	var fatherTr=$(b).parents("tr");
	var moreTr=fatherTr.next(".moreTr");
	
	var id=$(b).attr("orderId");
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/order/findID?id="+id,
		success : function(msg) {
			moreTr.show();
			moreTr.find(".moreTrTd").html(msg);
		}
	})
}
function closeOrder(b){
	var id=$(b).attr("orderId");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/order/closeOrder?id="+id,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","关闭成功,系统会将您的保证金支付给商家","ok");
			}
		}
	})
}


function payOrder(b){
	var id=$(b).attr("orderId");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/order/pay?id="+id,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","支付成功","ok");
			}
		}
	})
}
function picked(b){
	var id=$(b).attr("orderId");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/order/picked?id="+id,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","确认自提成功","ok");
			}
		}
	})
}
function close(b){
	var id=$(b).attr("orderId");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/order/close?id="+id,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","关闭成功","ok");
			}
		}
	})
}



</script>
</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="order-order" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>订单管理</h2>
					<div class="tabBox" id="bodyRH_tab">
						<c:if test='${fn:contains(requestScope.queryArgs,"WAITPAY")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA1" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${fn:contains(requestScope.queryArgs,"PAYED")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA2" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${fn:contains(requestScope.queryArgs,"PICKUPED")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA3" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${fn:contains(requestScope.queryArgs,"CLOSE")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA4" name="rightTabName"/>
							</jsp:include>
						</c:if>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
					<div id="queryArgsBox">
						<input type="hidden" value="${requestScope.queryArgs}" class="requestQueryArgsInput"/>
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="收货人电话" class="queryTxt" queryArgsKey="shouhuoTelphone"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						
						
						<%
							String now=DateUtils.getCurrDateStr_YYYY_MM_DD();
						%>
						<div class="everyQuery">
							<span class="k"></span>
							<span>
								<span class="dateRange" queryArgsKey="createDate">
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
					
						<c:if test='${empty requestScope.orderList}'>
							<div class="noDataTip"></div>
						</c:if>
						<c:if test='${!empty requestScope.orderList}'>
							<div class="tableBox myList " >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>订单描述</td>
										<td>订单明细</td>
										<td style="display:none;">操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="o" items="${requestScope.orderList}">
										<tr>
											<td>
												<p>下单日期：<fmt:formatDate value="${o.createDate}" pattern="yy-MM-dd HH:mm" /></p>
												<p>订单状态：
													<c:if test='${o.status=="CLOSE"}'>已关闭</c:if>
													<c:if test='${o.status=="WAITPAY"}'>待付款</c:if>
													<c:if test='${o.status=="PAYED"}'>已付款</c:if>
													<c:if test='${o.status=="PAYING"}'>等待自提付款</c:if>
													<c:if test='${o.status=="PICKUPED"}'>已取货</c:if>
												</p>
												<p>订单买家：${o.userName}</p>
												<p>
													订单金额：${o.totalPrice}元
													<c:if test='${o.tuijianRenId!=0}'>
														[其中付给推荐人${o.toHehuorenPrice}元，付给平台${o.toWebPrice}元]
													</c:if>
												</p>
												<p>收货人姓名：${o.shouhuoName},收货人电话：${o.shouhuoTelphone}</p>
											</td>
											<td style="width:600px;">
												<table cellpadding="0" cellspacing="0">
													<thead>
														<tr>
															<td>产品</td>
															<td>单价</td>
															<td>数量</td>
															<td>小计</td>
															<td>自提点</td>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="oi" items="${o.oiSet}">
															<tr>
																<td>
																	<c:set var="proPriceIdStr" value="productPriceId_${oi.productPriceId}"></c:set>
																	<c:set var="proPrice" value="${requestScope.productPriceMap[proPriceIdStr]}"></c:set>
																	<p>${proPrice.productName}</p>
																	<p>${proPrice.xilieName}+${proPrice.baozhuangName}+${proPrice.shiyongName}</p>
																</td>
																<td>${oi.price}</td>
																<td>${oi.ocount}</td>
																<td>${oi.tprice}</td>
																<td>
																	<c:set var="puaIdStr" value="pickUpAddressId_${oi.pickupAddressId}"></c:set>
																	<c:set var="pua" value="${applicationScope.pickUpAddressMap[puaIdStr]}"></c:set>
																	${pua.name}
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</td>
											<td style="width:100px;display:none;">
												<c:if test='${o.status=="WAITPAY"}'>
													<p>
														<a href="javascript:void(0);" onClick="payOrder(this)" orderId="${o.id}" class="btn">去支付</a>
													</p>
													<p>
														<a href="javascript:void(0);" onClick="close(this)" orderId="${o.id}" class="btn">关闭</a>
													</p>
												</c:if>
												<c:if test='${o.status=="PAYED"}'>
													<p>
														<a href="javascript:void(0);" onClick="picked(this)" orderId="${o.id}" class="btn">确定已取货</a>
													</p>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="2">
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
