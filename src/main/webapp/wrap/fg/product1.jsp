<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>爱尔特合伙人</title>
		

		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- load MUI -->
    	<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/mymui.alert.js"></script>
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    			
    		});
		</script>
		<style type="text/css">
		.qykBox{width:90%}
		</style>
	</head>
	<body>
		
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="include/rightMenu.jsp"></jsp:include>
			<div class="my-mui-inner-wrap">
				<div class="myaTabHeadBox">
									<ul>
										<li><a href="<%=request.getContextPath()%>/wrap/product/findId?id=${requestScope.product.id}">产品介绍</a></li>
										<li><a class="selected" href="<%=request.getContextPath()%>/wrap/product/findId?id=${requestScope.product.id}&toFW=toFW" >自提/服务</a></li>
									</ul>
								</div>
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper ">
					<div class="mui-scroll ">
						<div class="width100Box">
							<div class="widthNo100">
								<div class="mysuojinDiv" style="margin-top:50px;">
									
									
									<div class="qykBox">
										  <div class="qykHead">
										    <h3>产品的属性</h3>
										  </div>
										  <div class="qykBody">
										    <div class="qykBodyI" style="padding-bottom:10px;">
										    	<div class="propertyDiv">
													<div class="name">网店价</div>
													<div class="value">
														${requestScope.product.price1}
													</div>
												</div>
												<div class="propertyDiv">
													<div class="name">合伙人价</div>
													<div class="value">
														${requestScope.product.price2}
													</div>
												</div>
												<div class="propertyDiv">
													<div class="name">销量</div>
													<div class="value">
														${requestScope.product.saleCount}件
													</div>
												</div>
										    	<jsp:include page="../../manage/expand/propertyShowHTML.jsp">
													<jsp:param value="propertyDistributeList_all" name="propertyDistributeListName"/>
												</jsp:include>
												<jsp:include page="../../manage/expand/propertyShowHTML.jsp">
													<jsp:param value="propertyDistributeList_dRangePT" name="propertyDistributeListName"/>
												</jsp:include>
										    </div>
										  </div>
									</div>
									
									<div class="qykBox">
										  <div class="qykHead">
										    <h3>产品的自提点</h3>
										  </div>
										  <div class="qykBody">
										    <div class="qykBodyI">
										    	<c:if test='${empty requestScope.pickUpAddressList}'>
										    	<div class="wrapTipBox">
									    				<div class="wrapTipBoxI">
									    					<p>抱歉，该地区下，该产品尚无自提点</p>
									    				</div>
									    		</div>
										    	</c:if>
										    	<c:if test='${!empty requestScope.pickUpAddressList}'>
										    		<ul class="mui-table-view mui-table-view-chevron">
														<c:forEach var="pick" items="${requestScope.pickUpAddressList}">
															<c:set var="pickIDStr" value="[${pick.id}]"></c:set>
															<c:set var="addIdStr" value="addId_${pick.addressId}"></c:set>
															<li class="mui-table-view-cell mui-media">
																			<a class="mui-navigate-right" href="javascript:void(0);">
																				<div class="mui-media-body">
																					<p style="font-weight: bold;font-size:14px;">${pick.name}</p>
																					<p style="">
																						<c:set var="add" value="${applicationScope.addressAll_map[addIdStr]}"></c:set>
																						地区：${add.allName}
																					</p>
																					<p class='mui-ellipsis'>
																						地址： ${pick.addressStr}
																					</p>
																					<p class='mui-ellipsis'>
																						电话：${pick.telPhone}
																					</p>
																				</div>
																			</a>
															</li>	
														</c:forEach>
													</ul>	
										    	</c:if>
										    		
										    </div>
										  </div>
									</div>
									
									<div class="qykBox">
										  <div class="qykHead">
										    <h3>售后服务</h3>
										  </div>
										  <div class="qykBody">
										    <div class="qykBodyI">
										    	<p>
										    		如果有任何服务问题，其拨打官方24小时客服电话：
										    		<c:set var="g21Name" value="kefu_telPhone" scope="request"></c:set>
													<c:set var="g21" value="${applicationScope.globalMap[requestScope.g21Name]}"></c:set>
													<b style="color:blue">${g21.myValue}</b>
										    	</p>
										    </div>
										  </div>
									</div>
									
									
									
									
									
									<jsp:include page="include/footer.jsp"></jsp:include>
								</div>
						</div>
					</div>
				</div>
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
			<jsp:include page="fixFooter.jsp">
				<jsp:param value="toBuy" name="footerType"/>
			</jsp:include>
					
		</div>
		</div>
	</body>
</html>