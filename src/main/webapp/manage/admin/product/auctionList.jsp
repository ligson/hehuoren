<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
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
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/user.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<title>拍品 - 拍卖管理 - 艺术拍拍管理后台</title>
<style type="text/css">
.tableBox thead tr td{border-top:none !important;}
.jingpaiJiluTr{display:none;}
.jingpaiJiluTr td{background:#eee;}
.jingpaiJiluTr td table thead td{background:#fff;}
.jingpaiJiluTr td table{width:90%;margin:0 auto;}
.jingpaiJiluTr td table  td{background:#FFFFF0;}

</style>
<script type="text/javascript">
function findAuctionItems(b){
	var fatherTr=$(b).parents("tr");
	var auItemTr=fatherTr.next(".jingpaiJiluTr");
	
	var apId=$(b).attr("apId");
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/auction/findAIsByapId?auctionPeriodId="+apId,
		success : function(msg) {
			auItemTr.show();
			auItemTr.find(".jingpaiJiluTrTd").html(msg);
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
					<jsp:param value="auction-auction" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>产品的拍品拍卖纪录</h2>
					<div class="tabBox" id="bodyRH_tab">
						<ul id="myTabUl">
							<li><a id="myTabLiA1" href="<%=request.getContextPath()%>/admin/product/finds">产品中心</a></li>
							<li><a id="myTabLiA2" href="javascript:void(0);" class="selected">${requestScope.product.name}的拍品纪录</a></li>
						</ul>
					</div>
				</div>
				<div id="bodyR_content">
					<div class="tableBox myList " >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>拍品</td>
										<td>属性</td>
										<td>拍卖情况</td>
										<td>时间</td>
										<td>拍卖结果</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="ap" items="${requestScope.auctionPeriodList}">
										<c:set var="p" value="${requestScope.product}" scope="request"></c:set>
										<c:if test='${!empty requestScope.p}'>
											
											<tr>
												<td style="width:260px;">
													<div class="imgBox">
														<img src="<%=request.getContextPath()%>/${requestScope.p.mainImage}" style="width:60px;"/>
													</div>
													<div class="infoBox">
														<p><a href="<%=request.getContextPath()%>/fauction/${ap.id}/" target="_blank" class="link">${ap.title}</a></p>
														<p>
															<c:forEach var="pt" items="${requestScope.productTypeList}">
																<c:if test='${pt.id==requestScope.p.productTypeId}'>
																	${pt.allName}
																</c:if>
															</c:forEach>
														</p>	
														<p>所属店：
															<a href="<%=request.getContextPath()%>/fshop/${ap.shopId}/" target="_blank">${ap.shopName}</a>
														</p>
													</div>
												</td>
												<td style="width:120px;">
													<ul class="tdUl">
														<li>
															<span class="k">起拍价：</span>
															<span class="v">${fn:replace(ap.beginPrice,".0","")}</span>
														</li>
														<li>
															<span class="k">增幅价：</span>
															<span class="v">${fn:replace(ap.addPrice,".0","")}</span>
														</li>
														<li>
															<span class="k">保证金：</span>
															<span class="v">${fn:replace(ap.baozhengjin,".0","")}</span>
														</li>
														<li>
															<span class="k">保留价：</span>
															<span class="v">${fn:replace(ap.baoliujia,".0","")}</span>
														</li>
														<li>
															<span class="k">邮费：</span>
															<span class="v">${fn:replace(ap.courier,".0","")}</span>
														</li>
														
													</ul>
												</td>
												<td style="width:120px">
													<ul class="tdUl">
														<li>
															<span class="k">出价数：</span>
															<span class="v">${fn:replace(ap.chujiaNum,".0","")}</span>
														</li>
														<li>
															<span class="k">当前价：</span>
															<span class="v">${fn:replace(ap.currentPrice,".0","")}</span>
														</li>
														<li>
															<span class="k">当前人：</span>
															<span class="v">${ap.currentUserName}</span>
														</li>
														<li>
															<span class="k">中标人：</span>
															<span class="v">${ap.bidUserName }</span>
														</li>
														<li>
															<span class="k">中标价：</span>
															<span class="v">
																<c:if test='${ap.result!="NOBID"}'>
																	<c:if test='${ap.status=="END" || ap.status=="FINISH"}'>
																		${fn:replace(ap.bidPrice,".0","")}
																	</c:if>
																</c:if>
															</span>
														</li>
													</ul>
												</td>
												<td style="color:#999;width:170px;" class="morePTD">
													<c:if test='${ap.beginDate==null}'>
														<c:if test='${ap.status=="WAITBEGIN"}'>
															<a href="javascript:void(0)" class="auctionNoDate"></a>
														</c:if>
													</c:if>
													<c:if test='${ap.beginDate!=null}'>
														<p>
															<span class="k">【开拍：</span>
															<span><fmt:formatDate value="${ap.beginDate}" pattern="yy-MM-dd HH:mm" /> 】</span>
														</p>
														<p>
															<span class="k">【结束：</span>
															<span>${fn:substring(ap.actualEndDate,2,16)}】</span>
														</p>
													
													
														<c:if test='${ap.status=="WAITBEGIN"}'>
															<jsp:include page="../../util/djsTime.jsp">
																<jsp:param value="djsTimeBox_${ap.id}_begin" name="boxId"/>
																<jsp:param value="${ap.beginDate}" name="endDate"/>
																<jsp:param value="开拍" name="title"/>
															</jsp:include>
														</c:if>
														<c:if test='${ap.status=="ING"}'>
															<jsp:include page="../../util/djsTime.jsp">
																<jsp:param value="djsTimeBox_${ap.id}_end" name="boxId"/>
																<jsp:param value="${ap.actualEndDate}" name="endDate"/>
																<jsp:param value="结束" name="title"/>
															</jsp:include>
														</c:if>
													</c:if>
													
													<c:if test='${ap.status=="CLOSE"}'>
														<p class="status">已关闭</p>
													</c:if>
													<c:if test='${ap.status=="FINISH"}'>
														<p class="status">已结束</p>
													</c:if>
												</td>
												
												<td style="width:80px;">
													<jsp:include page="../../public/auctionStatus.jsp">
														<jsp:param value="${ap.status}" name="status"/>
														<jsp:param value="${ap.result}" name="result"/>
													</jsp:include>
												</td>
												<td class="" style="width:100px;">
													
													<c:if test='${ap.status=="CLOSE" || ap.status=="FINISH"}'>
														<c:if test='${ap.chujiaNum>0}'>
															<input  type="button" value="竞拍纪录" class="btn btnColor_1" onClick="findAuctionItems(this);" apId="${ap.id}"/>
														</c:if>
													</c:if>
													<c:if test='${ap.status=="ING"}'>
														<c:if test='${ap.chujiaNum>0}'>
															<input  type="button" value="竞拍纪录" class="btn btnColor_1" onClick="findAuctionItems(this);" apId="${ap.id}"/>
														</c:if>
													</c:if>
												</td>
												
											</tr>
											<tr class="jingpaiJiluTr">
												<td colspan="9" class="jingpaiJiluTrTd">
												</td>
											</tr>
										</c:if>
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
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
