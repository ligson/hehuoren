<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	var rtn=$("#rightTabInput").val();
	$("#myTabUl").find("a").removeClass("selected");
	$("#"+rtn).addClass("selected");
});
</script>
</head>
<body>
<input type="hidden" value="${param.rightTabName}" id="rightTabInput"/>
						<ul id="myTabUl">
							<li><a id="myTabLiA1" href="<%=request.getContextPath()%>/admin/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]">待支付订单</a></li>
							<li><a id="myTabLiA2" href="<%=request.getContextPath()%>/admin/order/finds?queryArgs=[{'key':'status','value':'PAYED,PAYING','operators':'in'}]">待取货订单</a></li>
							<li><a id="myTabLiA3" href="<%=request.getContextPath()%>/admin/order/finds?queryArgs=[{'key':'status','value':'PICKUPED'}]">完结订单</a></li>
							<li><a id="myTabLiA4" href="<%=request.getContextPath()%>/admin/order/finds?queryArgs=[{'key':'status','value':'CLOSE'}]">已关闭订单</a></li>
							
						</ul>
</body>
</html>