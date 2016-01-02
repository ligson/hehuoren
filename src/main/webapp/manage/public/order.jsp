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
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/user.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

	<title>订单详情 - 订单管理  - 爱尔特合伙人管理后台</title>

<style type="text/css">
#layout_bodyInner{width:1000px;padding:50px;min-height:500px;padding-top:20px;clear:both;}
.group{width:1000px;min-height:50px;margin-top:20px;}
.group h2{font-size:18px;font-weight: bold;color:#666;border-bottom:1px solid #ddd;padding-bottom:5px;}
.group h2 a{font-size:14px;padding-left:10px;}
.group h2 a:hover{text-decoration: underline;}
.contento{margin-top:10px;}

table{width:100%;}
table tr td {padding:9px;}
table thead tr td{background:#F0FFF0;border-bottom:1px solid #F0F8FF }
.spendngBox{clear:both;width:200px;float:right;height:50px;text-align: right;}

#layout_body{margin-top:0px;}
.orderBox{width:1100px;padding-top:20px;padding-bottom:20px;margin:0 auto;overflow: hidden;}
.orderBox .pub-steps ul li{width:270px;}
.orderStatus{font-size:18px;float:left;color: #ff4a00;font-weight: bold;display:none;}
.orderPrice{text-align: right;font-size:22px;font-weight: bold;color: #ff4a00;float:right;display:none;}


.accountTableB{width:100%;}
.accountRoleTd{background:#FFFFF0 ;width:100px;text-align: center;font-size: 14px;font-weight: bold;color:#666;border-bottom:1px solid #ddd;}
.accountRoleItemTd{border-bottom:1px solid #ddd;}
</style>
<link href="<%=request.getContextPath()%>/artDialog-5.0.3/skins/twitter.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/artDialog-5.0.3/artDialog.min.js"></script>
<script src="<%=request.getContextPath()%>/artDialog-5.0.3/artDialog.plugins.min.js"></script>
<script type="text/javascript">
function spending(b){
	var id=$(b).attr("orderId");
	var myAddressId=$("#myAddressInputId").val();
	if(null!=myAddressId && myAddressId!=""){
		art.dialog.prompt('请输财务密码', function (val) {
			var pwd = val;
			$.ajax( {
				type : "POST",
				url : allWeb+"user/order/spendingOrder?id="+id+"&password="+pwd+"&myAddressId="+myAddressId,
				success : function(msg) {
					if(msg!=null && msg!=""){
						myAlert(200,130,"失败!",msg,"error");
					}else{
						myAlert(200,130,"成功!","支付成功","ok");
						$("#zhifuBoxId").remove();
					}
				}
			})
		}, '');
	}else{
		alert("抱歉，请您选择收货地址");
	}
}
$(document).ready(function(){
	$("#myAddressULId").delegate("li a","click",function(){
		var curA=$(this);
		$("#myAddressULId").find("a").removeClass("selected");
		curA.addClass("selected");
		var curAddressId=curA.attr("myaddressId");
		$("#myAddressInputId").val(curAddressId);
	});
});
</script>
</head>
<body>
	<c:if test='${!empty sessionScope.myAdmin}'>
			<jsp:include page="../admin/head.jsp"></jsp:include>
		</c:if>
	<div class="orderBox">
		<div class="pub-steps">
		    <c:if test='${requestScope.order.status=="CLOSE"}'>
		    	<fmt:formatDate value="${requestScope.order.noSave_noPay2ShopDate}" pattern="yy-MM-dd HH:mm" var="noSave_noPay2ShopDate"/>
		    	<jsp:include page="orderStatus_close.jsp">
					<jsp:param value="step-on,step-on,step-on,," name="stepLiClass"/>
					<jsp:param value="${noSave_noPay2ShopDate}" name="noSave_noPay2ShopDate"/>
				</jsp:include>
		    </c:if>
			<c:if test='${requestScope.order.status=="WAITPAY"}'>
				<fmt:formatDate value="${requestScope.order.noSave_payTimeOverDate}" pattern="yy-MM-dd HH:mm" var="noSave_payTimeOverDate"/>
				<jsp:include page="orderStatus.jsp">
					<jsp:param value="step-on,step-on,step-on," name="stepLiClass"/>
					<jsp:param value="${noSave_payTimeOverDate}" name="noSave_payTimeOverDate"/>
				</jsp:include>
			</c:if>
			<c:if test='${requestScope.order.status=="PAYED"}'>
				<jsp:include page="orderStatus.jsp">
					<jsp:param value="step-on,step-on,step-on,step-on" name="stepLiClass"/>
				</jsp:include>
			</c:if>
		</div>
	</div>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div class="group">
				<h2>订单详情</h2>
				<div class="contento">
					<table cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<td>拍品</td>
								<td>拍品标题</td>
								<td>下单日期</td>
								<td>中标价格</td>
								<td>邮费</td>
								<td>商家</td>
								<td>买家</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								
								<td style="width:60px;">
									<img src="<%=request.getContextPath()%>/${requestScope.auctionPeriod.productMaiImage}" style="width:80px;"/>
								</td>
								<td>
									${requestScope.auctionPeriod.title}
								</td>
								<td>
									<fmt:formatDate value="${requestScope.order.createDate}" pattern="yy-MM-dd HH:mm" />
								</td>
								<td>
									${requestScope.auctionPeriod.bidPrice}
								</td>
								<td>	${requestScope.auctionPeriod.courier}</td>
								<td>${requestScope.auctionPeriod.shopName}</td>
								<td>${requestScope.order.userName}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<c:if test='${requestScope.order.status=="PAYED"}'>
				<div class="group">
					<h2>订单的收货地址</h2>
					<div class="contento">
						<c:set var="addIdStr" value="addId_${requestScope.myaddress.addressId}" scope="request"></c:set>
						<c:set var="add" value="${requestScope.addressAllMap[requestScope.addIdStr]}" scope="request"></c:set>
						<input type="hidden" value="${requestScope.myaddress.id}" id="myAddressInputId"/>
						<table>
							<thead>
								<tr>
									<td>收货人</td>
									<td>收货电话</td>
									<td>地区</td>
									<td>具体地址</td>
									<td>邮编</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${requestScope.myaddress.name}</td>
									<td>${requestScope.myaddress.telPhone}</td>
									<td>${requestScope.add.allName}</td>
									<td>${requestScope.myaddress.jutiAddress}</td>
									<td>${requestScope.myaddress.zipcode}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
			
			<div class="group">
				<h2>拍品</h2>
				<div class="contento">
					<table  cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<td></td>
								<td>拍品</td>
								<td>日期</td>
								<td>快递费</td>
								<td>起拍价</td>
								<td>增幅价</td>
								<td>保证金</td>
								<td>竞拍次数</td>
								<td>结果</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="width:60px">
									<img src="<%=request.getContextPath()%>/${requestScope.auctionPeriod.productMaiImage}" style="width:80px;"/>
								</td>
								<td style="width:200px;">
									<p><a href="<%=request.getContextPath()%>/fauction/${requestScope.auctionPeriod.id}" target="_blank">${requestScope.auctionPeriod.title}</a></p>
									<p><a href="<%=request.getContextPath()%>/fshop/${requestScope.auctionPeriod.shopId}/" target="_blank" style="color:blue;">${requestScope.auctionPeriod.shopName}</a></p>
								</td>
								<td>
									${fn:substring(requestScope.auctionPeriod.beginDate,2,16)} <br/>
									至
									${fn:substring(requestScope.auctionPeriod.actualEndDate,2,16)}
								</td>
								<td>
									<p>
										${fn:replace(requestScope.auctionPeriod.courier,".0","")}
									</p>
								</td>
								<td>
									<p>
										${fn:replace(requestScope.auctionPeriod.beginPrice,".0","")}
									</p>
								</td>
								<td>
									<p>
										${fn:replace(requestScope.auctionPeriod.addPrice,".0","")}
									</p>
								</td>
								<td>
									<p>
										${fn:replace(requestScope.auctionPeriod.baozhengjin,".0","")}
									</p>
								</td>
								<td>
									<p>${requestScope.auctionPeriod.chujiaNum}</p>
								</td>
								<td>
									<p>
										<c:if test='${requestScope.auctionPeriod.result=="BIDWAITPAY"}'>中标后待付款</c:if>
										<c:if test='${requestScope.auctionPeriod.result=="BIDNOPAY"}'>中标后拒付款</c:if>
										<c:if test='${requestScope.auctionPeriod.result=="BIDPAYED"}'>中标后已付款</c:if>
									</p>
								</td>
							</tr>
						</tbody>
					</table>     
				</div>
			</div>
			<div class="group">
				<h2>竞标记录</h2>
				<div class="contento">
					<table  cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<td>竞标次序</td>
								<td>竞标出价</td>
								<td>竞标时间</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>第${requestScope.auctionItem.chujiaNo}个</td>
								<td>
									${requestScope.auctionItem.chujiaPrice}元
								</td>
								<td>
									<p>${requestScope.auctionItem.chujiaDate}</p>
								</td>
							</tr>
						</tbody>
					</table>   
				</div>
			</div>
			<div class="group">
				<h2>财务明细</h2>
				<div class="contento">
					<table cellpadding="0" cellspacing="0" class="accountTableB">
						<tbody>
							<tr>
								<td class="accountRoleTd">买家财务</td>
								<td class="accountRoleItemTd">
									<table  cellpadding="0" cellspacing="0">
										<tbody>
											<tr>
												<td>编号：${requestScope.bzjAccountItem.markedNum}</td>
												<td>
													交易类型：<c:if test='${requestScope.bzjAccountItem.itemType=="FROZEN"}'>冻结</c:if>
												</td>
												<td>
													交易时间：${fn:substring(requestScope.bzjAccountItem.relDate,2,16)}
												</td>
												<td>
													交易金额：${fn:replace(requestScope.bzjAccountItem.price,".0","")}
												</td>
												<td>
													交易状态：
													<c:if test='${requestScope.bzjAccountItem.status=="FINISH"}'>完成</c:if>
													<c:if test='${requestScope.bzjAccountItem.status=="CLOSE"}'>解冻</c:if>
												</td>
											</tr>
										</tbody>
									</table>  
									<c:if test='${!empty requestScope.accountItem}'>
										<table  cellpadding="0" cellspacing="0">
											<tbody>
												<tr>
													<td>编号：${requestScope.accountItem.markedNum}</td>
													<td>
														交易类型：支付
													</td>
													<td>
														交易时间：
														<fmt:formatDate value="${requestScope.accountItem.relDate}" pattern="yy-MM-dd HH:mm" />
													</td>
													<td>
														交易金额：${fn:replace(requestScope.accountItem.price,".0","")}
													</td>
													<td>
														交易状态：
														<c:if test='${requestScope.accountItem.status=="FINISH"}'>完成</c:if>
													</td>
												</tr>
											</tbody>
										</table>        
										</c:if>  
								</td>
							</tr>
							<tr>
								<td class="accountRoleTd">卖家财务</td>
								<td class="accountRoleItemTd">
									<c:if test='${!empty requestScope.ai_shopIncome}'>
										<table  cellpadding="0" cellspacing="0">
											<tbody>
												<tr>
													<td>编号：${requestScope.ai_shopIncome.markedNum}</td>
													<td>
														交易类型：收入
													</td>
													<td>
														交易时间：
														<fmt:formatDate value="${requestScope.ai_shopIncome.relDate}" pattern="yy-MM-dd HH:mm" />
													</td>
													<td>
														交易金额：${fn:replace(requestScope.ai_shopIncome.price,".0","")}
													</td>
													<td>
														交易状态：
														<c:if test='${requestScope.ai_shopIncome.status=="FINISH"}'>完成</c:if>
													</td>
												</tr>
											</tbody>
										</table>        
									</c:if>  
								</td>
							</tr>
							<tr>
								<td class="accountRoleTd">平台财务</td>
								<td class="accountRoleItemTd">
									<c:if test='${!empty requestScope.ai_adminIncome}'>
										<table  cellpadding="0" cellspacing="0">
											<tbody>
												<tr>
													<td>编号：${requestScope.ai_adminIncome.markedNum}</td>
													<td>
														交易类型：收入
													</td>
													<td>
														交易时间：
														<fmt:formatDate value="${requestScope.ai_adminIncome.relDate}" pattern="yy-MM-dd HH:mm" />
													</td>
													<td>
														交易金额：${fn:replace(requestScope.ai_adminIncome.price,".0","")}
													</td>
													<td>
														交易状态：
														<c:if test='${requestScope.ai_adminIncome.status=="FINISH"}'>完成</c:if>
													</td>
												</tr>
											</tbody>
										</table>        
									</c:if>  
								</td>
							</tr>
						</tbody>
					</table>
					 
				</div>
			</div>
			<c:if test='${requestScope.roleClassName=="myFrameU.user.entity.User"}'>
				<div class="group">
					<div class="contento">
						<c:if test='${requestScope.order.status=="WAITPAY"}'>
							<div class="spendngBox" id="zhifuBoxId">
								<a href="#" class="btn btnColor_1" onClick="spending(this)" orderId="${requestScope.order.id}">立即支付</a>
							</div>
						</c:if>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<c:if test='${!empty sessionScope.myAdmin}'>
			<jsp:include page="../admin/foot.jsp"></jsp:include>
		</c:if>
</body>
</html>
