<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>${sessionScope.myUser.nicheng} - 用户中心 - 爱尔特</title>
		

		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- load MUI -->
    	<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/manage/css/main.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/manage/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/mymui.alert.js"></script>
    	
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    		});
    		
		</script>
		
	</head>
	<body>
		
		
		
		
		
		
		
		
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="../../../fg/include/rightMenu.jsp"></jsp:include>
			<div class="my-mui-inner-wrap">
				
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper ">
					<div class="mui-scroll ">
						
						<div class="width100Box">
							<div class="widthNo100">
								<div id="myHead" class="">
									<div id="myHead_l">
										<div class="mui-content-padded">
											<div class="flex-container">
												<a class="myHeadA"><span class="mui-icon mui-icon-back"></span></a>
											</div>
										</div>
									</div>
									<div id="myHead_c">
										<h2>我的订单</h2>
									</div>
									<div id="myHead_r">
										<div class="mui-content-padded">
											<div class="flex-container">
												<a  class="myHeadA bkred" id="offCanvasShow"><span class="mui-icon mui-icon-list"></span></a>
											</div>
										</div>
									</div>
								</div>
								
								<div class="mysuojinDiv">
								
								
								
									<div class="tabBox">
												<div class="tabHeadBox">
													<!-- 待付款、待自提、已自提、已关闭 -->
													<c:if test='${fn:contains(requestScope.queryArgs,"WAITPAY")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]" method="" contentDiv="item1_1" class="selected">待付款</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PAYED,PAYING','operators':'in'}]" method="" contentDiv="item1_2" >待自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PICKUPED'}]" method="" contentDiv="item1_3" >已自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'CLOSE'}]" method="" contentDiv="item1_4" class="">已关闭</a>
													</c:if>
													<c:if test='${fn:contains(requestScope.queryArgs,"PAYED")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]" method="" contentDiv="item1_1">待付款</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PAYED,PAYING','operators':'in'}]" method="" contentDiv="item1_2"  class="selected">待自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PICKUPED'}]" method="" contentDiv="item1_3" >已自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'CLOSE'}]" method="" contentDiv="item1_4" class="">已关闭</a>
													</c:if>
													<c:if test='${fn:contains(requestScope.queryArgs,"PICKUPED")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]" method="" contentDiv="item1_1">待付款</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PAYED,PAYING','operators':'in'}]" method="" contentDiv="item1_2" >待自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PICKUPED'}]" method="" contentDiv="item1_3"  class="selected">已自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'CLOSE'}]" method="" contentDiv="item1_4" class="">已关闭</a>
													</c:if>
													<c:if test='${fn:contains(requestScope.queryArgs,"CLOSE")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]" method="" contentDiv="item1_1">待付款</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PAYED,PAYING','operators':'in'}]" method="" contentDiv="item1_2" >待自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'PICKUPED'}]" method="" contentDiv="item1_3" >已自提</a>
														<a href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'CLOSE'}]" method="" contentDiv="item1_4"  class="selected">已关闭</a>
													</c:if>
													
													
													
												</div>
												<div class="tabContentBox">
													<div id="item1_1" class="tabContent selected">
														<ul class="mui-table-view mui-table-view-chevron">
															<c:forEach var="o" items="${requestScope.orderList}">
																<li class="mui-table-view-cell mui-media">
																	<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/order/findId?id=${o.id}">
																		<div class="mui-media-body">
																			自提点：
																			<c:set var="orderIdStr" value="orderId_${o.id}"></c:set>
																			<c:set var="pickList" value="${requestScope.orderPickMap[orderIdStr]}"></c:set>
																			<c:forEach var="pi" items="${pickList}">
																				[${pi.markedNum}]
																			</c:forEach>
																			<p style="font-weight: bold;font-size:14px;">
																				订单编号：${o.markedNum}
																			</p>
																			<p style="">
																				下单日期：${o.createDate}
																			</p>
																			<p class='mui-ellipsis'>
																				订单金额：${o.totalPrice}元，订单数量：${o.totalCount}
																			</p>
																			<p class='mui-ellipsis'>
																				订单状态：
																				<c:if test='${o.status=="CLOSE"}'>[已关闭]</c:if>
																				<c:if test='${o.status=="WAITPAY"}'>[待付款]</c:if>
																				<c:if test='${o.status=="PAYED"}'>[待自提]</c:if>
																				<c:if test='${o.status=="PAYING"}'>[待自提付款]</c:if>
																				<c:if test='${o.status=="PICKUPED"}'>[已结束]</c:if>
																			</p>
																		</div>
																	</a>
																</li>	
															</c:forEach>		
														</ul>	
														
													</div>
													<div id="item1_2" class="tabContent">
													</div>
													<div id="item1_3" class="tabContent">
													</div>
													<div id="item1_4" class="tabContent">
													</div>
												</div>
											</div>
								
								
								
								
											<div id="pageBoxOuter">
															<div id="pageBox">
																<jsp:include page="../../../pager.jsp"></jsp:include>
															</div>
											</div>
								
								
								
								
								
								
								
								
								
								
								
									
									
									
									<jsp:include page="../../../fg/include/footer.jsp"></jsp:include>
										
								</div>
						
								
						</div>
					</div>
				</div>
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
		</div>
	</body>
	<script type="text/javascript">
	initLeftRight();
	</script>
</html>