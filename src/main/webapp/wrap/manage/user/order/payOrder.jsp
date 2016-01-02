<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>爱尔特</title>
		

		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- load MUI -->
    	<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/manage/js/main.js"></script>
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    			startWXJSAPIPay();
    		});
    		function payOrder_wx(){
    			startWXJSAPIPay();
    		}
    		
    		
		</script>
		<style type="text/css">
		.qykBox{width:90%}
		.myaTabHeadBox ul li{width:33.333333%}
		.mui-numbox button{text-align: center;font-size: 20px;font-weight: bold;}
		.mui-numbox input{font-size: 20px;font-weight: bold;}
		</style>
	</head>
	<body>
		<jsp:include page="../../wxpayInput.jsp"></jsp:include>
		
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
										<h2>订单支付</h2>
									</div>
									<div id="myHead_r">
										<div class="mui-content-padded">
											<div class="flex-container">
												<a  class="myHeadA bkred" id="offCanvasShow"><span class="mui-icon mui-icon-list"></span></a>
											</div>
										</div>
									</div>
								</div>
								
								<div class="mysuojinDiv" >
									
									
								    
									<div class="qykBox">
								    	<div class="qykHead">
								    		<h3>订单支付</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<ul class="mui-table-view mui-table-view-chevron">
													<li class="mui-table-view-cell mui-media">
														<a class="mui-navigate-right" href="javascript:void(0);">
															<div class="mui-media-body">
																<p style="font-weight: bold;font-size:14px;">订单编号：${requestScope.order.markedNum}</p>
																<p style="">
																	下单日期：<fmt:formatDate value="${requestScope.order.createDate}" pattern="MM-dd HH:mm" />
																</p>
																<p class='mui-ellipsis'>
																	订单金额：${requestScope.order.totalPrice}元，订单数量：${requestScope.order.totalCount}
																</p>
																<p class='mui-ellipsis'>
																	真实姓名：${requestScope.order.shouhuoName},手机号：${requestScope.order.shouhuoTelphone}
																</p>
																<p class='mui-ellipsis'>
																	订单状态：
																	<c:if test='${requestScope.order.status=="CLOSE"}'>[已关闭]</c:if>
																	<c:if test='${requestScope.order.status=="WAITPAY"}'>[待付款]</c:if>
																	<c:if test='${requestScope.order.status=="PAYED"}'>[待自提]</c:if>
																	<c:if test='${requestScope.order.status=="PICKUPED"}'>[已结束]</c:if>
																</p>
															</div>
														</a>
													</li>			
												</ul>	
								    		</div>
								    	</div>
								    </div>
									
									
									
									
									<c:if test='${requestScope.order.status=="WAITPAY"}'>
									<div class="qykBox" id="querenPayBoxId">
								    	<div class="qykHead">
								    		<h3>确认支付</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<div class="mui-input-group">
													<div class="mui-input-row">
														<label>付款金额</label>
														<input name="totalPrice"  type="text" class="mui-input-clear mui-input" value="${requestScope.order.totalPrice}" readonly="readonly">
													</div>
													<div class="mui-input-row" style="display:none;">
														<label>财务密码</label>
														<input name="password" id='caiwupasswordInput' type="password" class="mui-input-clear mui-input" placeholder="请输入财务密码">
													</div>
												</div>
												<div class="mui-content-padded">
													<c:if test='${empty requestScope.order.outerType}'>
														<!-- 没决定用啥支付（财务账号、支付宝、微信） -->
													</c:if>
													<c:if test='${!empty requestScope.order.outerType}'>
														<c:if test='${requestScope.order.outerType=="WEIXIN"}'>
															<button  class="mui-btn mui-btn-block mui-btn-primary" onClick="payOrder_wx();" orderId="${requestScope.order.id}">确认微信支付</button>	
														</c:if>
														<c:if test='${requestScope.order.outerType=="ZHIFUBAO"}'>
															<button  class="mui-btn mui-btn-block mui-btn-primary" onClick="payOrder_zfb();" orderId="${requestScope.order.id}">确认支付宝支付</button>	
														</c:if>
													</c:if>
													
												</div>
								    		</div>
								    	</div>
								    </div>
									</c:if>
									<c:if test='${requestScope.order.status=="PAYED" || requestScope.order.status=="PICKUPED"}'>
									<div class="qykBox" >
								    	<div class="qykHead">
								    		<h3>支付记录</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<ul class="mui-table-view mui-table-view-chevron">
													<li class="mui-table-view-cell mui-media">
														<a class="mui-navigate-right" href="javascript:void(0);">
															<div class="mui-media-body">
																<p style="font-weight: bold;font-size:14px;">订单编号：${requestScope.order.markedNum}</p>
																<p style="">
																	支付时间：${requestScope.order.payDate}
																</p>
																<p class='mui-ellipsis'>
																	支付金额：${requestScope.order.totalPrice}元
																</p>
																<p class='mui-ellipsis'>
																	自提时间：${requestScope.order.pickDate}
																</p>
															</div>
														</a>
													</li>			
												</ul>	
												<c:if test='${requestScope.order.status=="PAYED"}'>
													<button id="wanchengzitiInputButton" class="mui-btn mui-btn-block mui-btn-primary" onclick="querenPickOrder(this);" orderId="${requestScope.order.id}">确认已经自提</button>
												</c:if>
								    		</div>
								    	</div>
								    </div>
									</c:if>
									
									
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
</html>