<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/user.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ui.css"/>
<script type="text/javascript">

</script>
<style type="text/css">
h1{font-size:26px;}
td{padding:5px;}
td.k{width:120px;}
</style>
</head>
<body>
	<h1>模拟拍品页面，进行竞拍、收藏、设置提醒</h1>
	<hr/>
	<div style="width:50%;padding:20px;float:left;height:300px;background:#FFFAF0 	">
		<table>
		<tbody>
			<tr>
				<td class="k">名称：</td>
				<td>${requestScope.auctionPeriod.title}</td>
			</tr>
			<tr>
				<td class="k">状态：</td>
				<td>
					<c:if test='${requestScope.auctionPeriod.status=="CLOSE"}'>已关闭</c:if>
					<c:if test='${requestScope.auctionPeriod.status=="FINISH"}'>已完结</c:if>
					<c:if test='${requestScope.auctionPeriod.status=="WAITBEGIN"}'>等待开拍</c:if>
					<c:if test='${requestScope.auctionPeriod.status=="ING"}'>正在开拍</c:if>
					<c:if test='${requestScope.auctionPeriod.status=="END"}'>已结束</c:if>
				</td>
			</tr>
			<tr>
				<td class="k">当前价：</td>
				<td>${requestScope.auctionPeriod.currentPrice}</td>
			</tr>
			<tr>
				<td class="k">当前谁领先：</td>
				<td>${requestScope.auctionPeriod.currentUserName}</td>
			</tr>
			<tr>
				<td class="k">目前出价次数：</td>
				<td>${requestScope.auctionPeriod.chujiaNum}</td>
			</tr>
			
			<tr>
				<td class="k">产品扩展属性</td>
				<td>
					<jsp:include page="myFrameU/expand/useTest/propertyShowHTML.jsp">
						<jsp:param value="propertyDistributeList_dRangePT" name="propertyDistributeListName"/>
					</jsp:include>
					<jsp:include page="myFrameU/expand/useTest/propertyShowHTML.jsp">
								<jsp:param value="propertyDistributeList_all" name="propertyDistributeListName"/>
					</jsp:include>
				</td>
			</tr>
		</tbody>
	</table>
	</div>
	<div style="width:40%;padding:20px;float:right;height:300px;background:#F5FFFA ">
		<table>
		<tbody>
			<c:if test='${requestScope.auctionPeriod.status=="ING"}'>
			<tr>
				<td class="k">测试竞价</td>
				<td>
					<input type="text" value="${requestScope.auctionPeriod.beginPrice}" id="jppriceInput_apId${requestScope.auctionPeriod.id}"/>
					<input type="button" class="btn" value="加价" addPrice="${requestScope.auctionPeriod.addPrice}" id="" apId="${requestScope.auctionPeriod.id}" onClick="addPriceFunction(this)"/>
					<a href="javascript:void(0);"  class="btn btnColor_1" onClick="userAddAuctionItem(this)" autionId="${requestScope.auctionPeriod.id}">竞拍</a>
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="k">测试收藏</td>
				<td>
					<a href="javascript:void(0);" class="btn" onClick="shoucang(this);"  scEntityId="${requestScope.auctionPeriod.id}" scEntity="yishupaipai.auction.entity.AuctionPeriod">收藏</a>
				</td>
			</tr>
			<c:if test='${requestScope.auctionPeriod.status=="WAITBEGIN" || requestScope.auctionPeriod.status=="ING"}'>
				<tr>
					<td class="k">测试提醒</td>
					<td>
						<a href="javascript:void(0);" class="btn" onClick="shoucang(this);"  scEntityId="${requestScope.auctionPeriod.id}" scEntity="yishupaipai.auction.entity.AuctionPeriod">设置提醒</a>
					</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	</div>
</body>
</html>