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

<title>购买名单  - 爱尔特合伙人后台</title>
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
					<jsp:param value="user-order" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>购买收货人名单</h2>
					<div class="tabBox" id="bodyRH_tab">
						<ul id="myTabUl">
							<li><a id="myTabLiA1" class="selected" href="javascript:void(0);">购买收货人名单</a></li>
						</ul>
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
									<span><input type="text" value="收货人姓名" class="queryTxt" queryArgsKey="shouhuoName"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="收货人电话" class="queryTxt" queryArgsKey="shouhuoTelphone"/></span>
									<span class="searchBut"></span>
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
										<td>收货人姓名</td>
										<td>收货人电话</td>
										<td>订单号</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="o" items="${requestScope.orderList}">
										<tr>
											<td>
												${o.shouhuoName}
											</td>
											<td>
												${o.shouhuoTelphone}
											</td>
											<td>
												${o.markedNum}
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="3">
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
