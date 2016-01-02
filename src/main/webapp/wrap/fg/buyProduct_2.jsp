<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>确定加入购物车 - 爱尔特合伙人</title>
		

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
    		
    		
    		
    		
    		function submitAddCart(b){
    			//获取productPriceId
    			var productPriceId = $("#productPriceInfoBox").attr("productPriceId");
    			
    			//获取productId，pickupAddressId，ocount
    			//var productId=$(b).attr("productId");
    			//var productPriceId=$(b).attr("productPriceId");
    			var pickupAddressId=$("input[name=pickupAddressIdInput]:checked ").val();
    			var ocount=$("#ocountInput").val();
    			//alert("productId="+productId+";pickupAddressId="+pickupAddressId+";ocount="+ocount);
    			if(null!=productPriceId && productPriceId!="" && productPriceId!="undefined"){
    				if(null!=pickupAddressId && pickupAddressId!="" && pickupAddressId!="undefined"){
    					if(ocount!=null && ocount!=""){
    						//alert("o");
    						$.ajax( {
								type : "POST",
								url : allWeb+"wrap/user/cart/addProduct?productPriceId="+productPriceId+"&pickupAddressId="+pickupAddressId+"&ocount="+ocount,
								success : function(msg) {
									if(null!=msg && msg!=""){
										alert(msg);
									}else{
										//window.location.href=allWeb+"wrap/user/order/addOrder";
										//现在不提交了，直接进入购物车
										window.location.href=allWeb+"wrap/user/cart/myCart";
									}
								}
							})
    					}
    				}else{
    					alert("请选择自提点");
    				}
    			}
    			
    			
    			
    			
    			
    		}
    		
    		function findPickListByAddId(s){
    			var cityId=$(s).val();
    			var productPriceId=$(s).parents(".qykBox").attr("productPriceId");
    			//alert("cityId="+cityId);
    			$.ajax( {
					type : "POST",
					url : allWeb+"wrap/pua/findByAddId?addId="+cityId+"&productPriceId="+productPriceId,
					success : function(msg) {
						$("#pickListBoxId").html(msg);
					}
				})
				
    		}
		</script>
		<style type="text/css">
		.qykBox{width:90%}
		.myaTabHeadBox ul li{width:33.333333%}
		.mui-numbox button{text-align: center;font-size: 20px;font-weight: bold;}
		.mui-numbox input{font-size: 20px;font-weight: bold;}
		.pickSelectBox select{width:45%;}
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
										<li><a href="<%=request.getContextPath()%>/wrap/product/findId?id=${requestScope.product.id}&toFW=toFW" >自提/服务</a></li>
										<li><a class="selected" href="javascript:void(0);" >购买</a></li>
									</ul>
								</div>
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper ">
					<div class="mui-scroll ">
						<div class="width100Box">
							<div class="widthNo100">
								<div class="mysuojinDiv" style="margin-top:50px;">
									
									
									<div class="qykBox" id="productPriceInfoBox" productPriceId="${requestScope.productPrice.id}">
								    	<div class="qykHead">
								    		<h3>产品信息</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<ul class="mui-table-view mui-table-view-chevron" style="border-bottom:none;">
								    				<li class="mui-table-view-cell mui-media" style="border-bottom:none;">
														<a class="mui-navigate-right" href="javascript:void(0);"  style="border-bottom:none;">
															<img class="mui-media-object mui-pull-left" src="<%=request.getContextPath()%>/${requestScope.productPrice.productImg}">
															<div class="mui-media-body"  style="border-bottom:none;">
																${requestScope.productPrice.productName}
																<p>
																	净含量：${requestScope.productPrice.xilieName}  规格：${requestScope.productPrice.baozhuangName}
																	  适用：${requestScope.productPrice.shiyongName}
																</p>
																<p class='mui-ellipsis'>
																	价格：
																	<!-- 如果自己是合伙人，那就合伙人价格 -->
																	<!-- 如果自己不是合伙人，那就判断是否有推荐人，如果有推荐人，则是合伙人价格，如果没有推荐人，则实体价格 -->
																	<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
																		<c:if test='${sessionScope.myUser.tuijianRenId=="0"}'>
																			￥${requestScope.productPrice.price1}
																		</c:if>
																		<c:if test='${sessionScope.myUser.tuijianRenId!="0"}'>
																			￥${requestScope.productPrice.price2}
																		</c:if>
																	</c:if>
																	<c:if test='${sessionScope.myUser.userLevelId!="1"}'>
																		￥${requestScope.productPrice.price2}
																	</c:if>
																</p>
															</div>
														</a>
													</li>
								    			</ul>
								    		</div>
								    	</div>
								    </div>
									
									<div class="qykBox" style="display:none;">
								    	<div class="qykHead">
								    		<h3>我的推荐人</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<c:if test='${sessionScope.myUser.tuijianRenId=="0"}'>
								    				<div class="wrapTipBox">
									    				<div class="wrapTipBoxI">
									    					<p>对不起，您目前还没有自己的推荐人，不能享受合伙人价格，请尽快选择推荐人</p>
									    				</div>
									    			</div>
								    			</c:if>
								    			<c:if test='${sessionScope.myUser.tuijianRenId!="0"}'>
								    				<ul class="mui-table-view mui-table-view-chevron" style="border-bottom:none;">
								    				<li class="mui-table-view-cell mui-media" style="border-bottom:none;">
														<a class="mui-navigate-right" href="javascript:void(0);"  style="border-bottom:none;">
															<img class="mui-media-object mui-pull-left" src="<%=request.getContextPath()%>/${requestScope.myTJR.touxiang}">
															<div class="mui-media-body"  style="border-bottom:none;">
																${requestScope.myTJR.nicheng}
																<p class='mui-ellipsis'>
																	级别：
																	<c:if test='${requestScope.myTJR.userLevelId=="2"}'>试用期合伙人</c:if>
																	<c:if test='${requestScope.myTJR.userLevelId=="3"}'>正式合伙人</c:if>
																</p>
															</div>
														</a>
													</li>
								    				</ul>
								    			</c:if>
								    		</div>
								    	</div>
								    </div>
									
									<div class="qykBox">
										  <div class="qykHead">
										    <h3>购买数量</h3>
										  </div>
										  <div class="qykBody">
										    <div class="qykBodyI">
										    	<div class="mui-numbox" style="width: 100%;height: 50px;"  data-numbox-min='1' data-numbox-max='${requestScope.productPrice.productCount}'>
													<button class="mui-numbox-btn-minus" type="button">-</button>
													<input id="ocountInput" class="mui-numbox-input" type="number" value="${requestScope.ciCount}"/>
													<button class="mui-numbox-btn-plus" type="button">+</button>
												</div>
										    </div>
										  </div>
									</div>
									
									<c:if test='${!empty requestScope.cartItem}'>
										<input name="pickupAddressIdInput" type="radio" value="${requestScope.cartItem.pickupAddressId}" checked="checked">
									</c:if>
									<c:if test='${empty requestScope.cartItem}'>
										<c:if test='${!empty requestScope.ci_pro}'>
											<input name="pickupAddressIdInput" type="radio" value="${requestScope.ci_pro.pickupAddressId}" checked="checked">
										</c:if>
										<c:if test='${empty requestScope.ci_pro}'>
											<!-- 只有没有购买过pp+没有购买过p，才显示自提点 -->
											<div class="qykBox" productPriceId="${requestScope.productPrice.id}">
										  <div class="qykHead">
										    <h3>选择自提点</h3>
										  </div>
										  <div class="qykBody">
										    <div class="qykBodyI">
										    	<div id="pickSelectBox" class="pickSelectBox">
										    		<jsp:include page="../../myFrameU/address/addressListSelect.jsp">
														<jsp:param value="city" name="end"/>
														<jsp:param value="findPickListByAddId" name="endChange"/>
													</jsp:include>
										    	</div>
										    	<div id="pickListBoxId">
										    		<c:if test='${empty requestScope.cartItem}'>
										    			<!-- 之前没有购买过这个产品 -->
										    			<!-- 再分两种情况，一是没有买过product的任何组合，二是虽然没有买过这个组合，但是买过这个产品的其他组合 -->
										    			<c:if test='${empty requestScope.ci_pro}'>
										    				<!-- 没有买过这个产品的任何组合 -->
										    				<c:forEach var="pick" items="${requestScope.pickUpAddressList}">
																<c:set var="pickIDStr" value="[${pick.id}]"></c:set>
																<c:set var="addIdStr" value="addId_${pick.addressId}"></c:set>
																<div class="mui-input-row mui-radio mui-left" style="margin-bottom:5px;width:100%">
																		<label>${pick.name}(${pick.addressStr})</label>
																		<input name="pickupAddressIdInput" type="radio" value="${pick.id}" >
																</div>
															</c:forEach>	
										    			</c:if>
										    			<c:if test='${!empty requestScope.ci_pro}'>
										    				<!-- 虽然没有买过这个组合，但是买过这个产品的其他组合 -->
										    				<c:forEach var="pick" items="${requestScope.pickUpAddressList}">
																<c:set var="pickIDStr" value="[${pick.id}]"></c:set>
																<c:set var="addIdStr" value="addId_${pick.addressId}"></c:set>
																<c:if test='${requestScope.ci_pro.pickupAddressId==pick.id}'>
																	<div class="mui-input-row mui-radio mui-left" style="margin-bottom:5px;width:100%">
																		<label>${pick.name}(${pick.addressStr})</label>
																		<input name="pickupAddressIdInput" type="radio" value="${pick.id}" checked="checked">
																	</div>
																</c:if>
																<c:if test='${requestScope.ci_pro.pickupAddressId!=pick.id}'>
																	<div class="mui-input-row mui-radio mui-left" style="margin-bottom:5px;width:100%">
																		<label>${pick.name}(${pick.addressStr})</label>
																		<input name="pickupAddressIdInput" type="radio" value="${pick.id}" disabled="disabled">
																	</div>
																</c:if>
															</c:forEach>	
										    			</c:if>
										    			
										    		</c:if>
										    		<c:if test='${!empty requestScope.cartItem}'>
										    			<!-- 购买过不显示 -->
										    			<!-- 之前购买过这个产品，处理自提点默认是之前选择的 -->
										    			<c:set var="oldPickId" value="${requestScope.cartItem.pickupAddressId}"></c:set>
										    			<c:forEach var="pick" items="${requestScope.pickUpAddressList}">
																<c:set var="pickIDStr" value="[${pick.id}]"></c:set>
																<c:set var="addIdStr" value="addId_${pick.addressId}"></c:set>
																<c:if test='${oldPickId==pick.id}'>
																	<div class="mui-input-row mui-radio mui-left" style="margin-bottom:5px;width:100%">
																		<label>${pick.name}(${pick.addressStr})</label>
																		<input name="pickupAddressIdInput" type="radio" value="${pick.id}" checked="checked">
																	</div>
																</c:if>
																<c:if test='${oldPickId!=pick.id}'>
																	<div class="mui-input-row mui-radio mui-left" style="margin-bottom:5px;width:100%">
																		<label>${pick.name}(${pick.addressStr})</label>
																		<input name="pickupAddressIdInput" type="radio" value="${pick.id}" >
																	</div>
																</c:if>
																
														</c:forEach>
										    		</c:if>
										    	</div>
										    	
												
												<div style="display:none;">
												<c:if test='${empty requestScope.pickUpAddressList}'>
										    	<div class="wrapTipBox">
									    				<div class="wrapTipBoxI">
									    					<p>
									    						抱歉，该地区下，该产品尚无自提点,您可去用户中心
									    						<a href="<%=request.getContextPath()%>/wrap/user/myUser">修改您的所在城市</a>
									    						,或者选择省份、城市选择自提点
									    					</p>
									    				</div>
									    		</div>
										    	</c:if>
										    	<c:if test='${!empty requestScope.pickUpAddressList}'>
										    		
										    	</c:if>
												</div>
										    	
									    			
										    </div>
										  </div>
									</div>
						
										</c:if>
									</c:if>
												
														
									<jsp:include page="include/footer.jsp"></jsp:include>
								</div>
						</div>
					</div>
				</div>
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
			<jsp:include page="fixFooter.jsp">
				<jsp:param value="add2Cart" name="footerType"/>
			</jsp:include>
					
		</div>
		</div>
	</body>
</html>