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

<title>产品中心 - 爱尔特合伙人后台</title>
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
					<jsp:param value="auction-product" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>产品中心</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA1" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					
					<div id="queryArgsBox">
						<input type="hidden" value="${requestScope.queryArgs}" class="requestQueryArgsInput"/>
						
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="产品名称" class="queryTxt" queryArgsKey="name"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						
						
						<div class="everyQuery" >
								<span class="k"></span>
								<span>
									<span class="mySelect" width="100" status="close">
										<span class="input">分类</span>
										<span class="arrow"></span>
										<span class="down">
											<ul>
												<c:forEach var="pt" items="${requestScope.productTypeList}">
													<c:if test='${pt.isROOT==0}'>
														<li><a href="javascript:void(0);"  value="{'key':'productTypeTreeIds','value':'${pt.treeId}','operators':'lastlike'}">${pt.name}</a></li>
													</c:if>
												</c:forEach>
											</ul>
										</span>
									</span>
								</span>
						</div>
						
						
					</div>
					
					
					
					
					
					<div id="mainContent">
						<c:if test='${empty requestScope.productList}'>
							<div class="noDataTip"></div>
						</c:if>
						<c:if test='${!empty requestScope.productList}'>
							<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>封面</td>
										<td>名称</td>
										<td>分类</td>
										<td>库存</td>
										<td>状态</td>
										<td>操作</td>
										<td style="display:none;">模拟</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="p" items="${requestScope.productList}">
										<tr>
											
											
											<td>
												<img src="<%=request.getContextPath()%>/${p.mainImage}" style="width:60px;"/>
											</td>
											<td style="width:200px">
												<p>${p.name}</p>
											</td>
											<td>
												<c:forEach var="pt" items="${requestScope.productTypeList}">
													<c:if test='${pt.id==p.productTypeId}'>
														${pt.allName}
													</c:if>
												</c:forEach>
											</td>
											<td>
												${p.productCount}
											</td>
											<td style="width:80px;">
												<c:if test='${p.status=="OK"}'>
													正常
												</c:if>
												<c:if test='${p.status=="CLOSE"}'>
													已冻结
												</c:if>
											</td>
											<td>
												<a href="<%=request.getContextPath()%>/admin/product/toMod?id=${p.id}" class="btn btnColor_1">修改</a>
												<a href="<%=request.getContextPath()%>/admin/product/del?id=${p.id}" class="btn ">删除</a>
											</td>
											<td style="display:none;">
												<c:set var="pIdstr" value="[${p.id}]"></c:set>
												<c:if test='${fn:contains(sessionScope.myCart.productIds,pIdstr)}'>
													<input type="text" style="width:50px;" id="cartNumber_pId_${p.id}" value="1"/>
													<a onClick="addCart(this);" productId="${p.id}"  href="javascript:void(0);" class="btn ">修改数量</a>
													<a onClick="removeCI(this)" productId="${p.id}" href="javascript:void(0);" class="btn">删除</a>
												</c:if>
												<c:if test='${!fn:contains(sessionScope.myCart.productIds,pIdstr)}'>
													<input type="text" style="width:50px;" id="cartNumber_pId_${p.id}" value="1"/>
													<a onClick="addCart(this);" productId="${p.id}"  href="javascript:void(0);" class="btn ">加入购物车</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="7">
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
