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
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    			
    		});
    		
    		
    		
    		function submitCreateOrder(b){
    			var zsName=$("#user_zsxm_input").val();
    			var zsPhone=$("#user_zs_phone").val();
    			if(null!=zsName && zsName!=""){
    				if(null!=zsPhone && zsPhone!=""){
        				var pLen=zsPhone.length;
        				if(pLen==11){
        					$.ajax( {
        						type : "POST",
        						dataType : "json",
        						url : allWeb+"wrap/user/order/addOrder?shouhuoTelphone="+zsPhone+"&shouhuoName="+zsName,
        						success : function(msg) {
        							var error = msg.error;
        							if (null == error || error == "") {
        								var orderId=msg.orderId;
        								if(orderId!=null && orderId!="" && orderId!="0"){
        									window.location.href=allWeb+"wrap/user/order/findId?id="+orderId;	
        								}
        							} else {
        								alert(msg.errorMessage);
        							}
        							
        						}
        					})
        				}else{
        					alert("手机号码错误");
        				}
        			}else{
        				alert("请填写真实手机号");
        			}	
    			}else{
    				alert("请填写真实姓名");
    			}
    		}
    		
    		function modifyPickAddress(i){
    			if (confirm("确认要修改自提点吗？如果修改，则同一产品下所有明细均修改为新自提点")) {
    				var pickupAddressId=$(i).val();
        			var productPriceId=$(i).attr("productPriceId");
        			if(null!=pickupAddressId && pickupAddressId!="" && null!=productPriceId && productPriceId!=""){
        				$.ajax( {
    						type : "POST",
    						url : allWeb+"wrap/user/cart/modifyPuaId?pickupAddressId="+pickupAddressId+"&productPriceId="+productPriceId,
    						success : function(msg) {
    							if(null!=msg && msg!=""){
    								alert(msg);
    							}else{
    								//alert("修改自提点完成");
    								window.location.href=allWeb+"wrap/user/cart/myCart";
    							}
    						}
    					})
        			}    
    	        }
    			
    		}
    		function clearMyCart(){
    			$.ajax( {
					type : "POST",
					url : allWeb+"wrap/user/cart/clearCart",
					success : function(msg) {
						if(null!=msg && msg!=""){
							alert(msg);
						}else{
							alert("清空成功");
							window.location.href=allWeb+"wrap/user/cart/myCart";
						}
					}
				})
    		}
    		function removeCartItem(b){
    			var productPriceId=$(b).attr("productPriceId");
    			$.ajax( {
					type : "POST",
					url : allWeb+"wrap/user/cart/removeCartItem?productPriceId="+productPriceId,
					success : function(msg) {
						if(null!=msg && msg!=""){
							alert(msg);
						}else{
							alert("成功从购物车中移除");
							window.location.href=allWeb+"wrap/user/cart/myCart";
						}
					}
				})
    		}
    		function modifyCICount(b){
    			var productPriceId=$(b).attr("productPriceId");
    			var ocountInputId=$(b).attr("ocountInputId");
    			var ocountInput=$("#"+ocountInputId);
    			var ocount=ocountInput.val();
    			//alert(ocount+"=======");
    			var ocountI=parseInt(ocount);
    			var addOrjian=$(b).attr("addOrjian");
    			var proKucun=$(b).attr("proKucun");
    			var proKucunI=parseInt(proKucun);
    			 if(addOrjian=="add"){
    				ocountI=ocountI+1;
    				if(ocountI>proKucunI){
    					ocountInput.val(proKucunI);
    					alert("抱歉，产品库存不足，最多您可购买"+proKucunI+"件");
    				}
    			}else if(addOrjian=="jian"){
    				ocountI=ocountI-1;
    			} 
    			if(ocountI==0){
    				//ocountInput.val("1");
    				alert("数量不能为0，如想删除，请点击下方移除按钮");
    			}else{
    				//alert(ocountI);
    				$.ajax( {
    					type : "POST",
    					url : allWeb+"wrap/user/cart/modifyCICount?productPriceId="+productPriceId+"&ocount="+ocountI,
    					success : function(msg) {
    						if(null!=msg && msg!=""){
    							alert(msg);
    						}else{
    							alert("修改数量成功");
    							//window.location.href=allWeb+"wrap/user/cart/myCart";
    						}
    					}
    				})	
    			}
    		}
    		function toIndex(b){
    			window.location.href=allWeb+"wrap/index";
    		}
    		
    		function findPickListByAddId(s){
    			var cityId=$(s).val();
    			
    			var curqykBox=$(s).parents(".qykBox");
    			var productId=curqykBox.attr("productPriceId");
    			var pickId=curqykBox.attr("pickId");
    			var pickListBoxId="pickListBoxId_pId_"+productId;
    			var pickListBox=$("#"+pickListBoxId);
    			//pickListBoxId_pId_${p.id}
    			//alert("cityId="+cityId);
    			$.ajax( {
					type : "POST",
					url : allWeb+"wrap/pua/findByAddId?addId="+cityId+"&productPriceId="+productId,
					success : function(msg) {
						pickListBox.html(msg);
						
						//将ajaxList里的input的name赋值\id赋值
						var pickIdStr="radio_pick_id_"+pickId;
						var radioName="pickupAddressIdInput_pId_"+productId;
						pickListBox.find("input").attr("name",radioName).attr("id",pickIdStr);
						
						//处理默认的select
						// id="radio_pick_id_${pick.id}"
						//var pickRaido=$("#"+pickIdStr);
						//pickRaido.attr("checked","checked");
						
					}
				})
				
    		}
    		
		</script>
		<style type="text/css">
		.qykBox{width:90%}
		.myaTabHeadBox ul li{width:33.333333%}
		.mui-numbox button{text-align: center;font-size: 20px;font-weight: bold;}
		.mui-numbox input{font-size: 20px;font-weight: bold;}
		.pickSelectBox{margin-top:10px;}
		.pickSelectBox select{width:45%;}
		</style>
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
										<h2>我的购物车</h2>
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
									<c:if test='${empty sessionScope.myCart}'>
										<div class="wrapTipBox">
											<div class="wrapTipBoxI">
									    		<div class="mui-content-padded">
													<button  class="mui-btn mui-btn-block mui-btn-primary" onClick="toIndex(this);" >您的购物车为空，请前去选择购买产品</button>
												</div>
											</div>
										</div>
									</c:if>
									<c:if test='${!empty sessionScope.myCart}'>
									<div class="qykBox">
										  <div class="qykHead">
										    <h3>收货人的信息</h3>
										  </div>
										  <div class="qykBody">
										    <div class="qykBodyI">
										    	<div action="<%=request.getContextPath()%>/wrap/userLogin" method="post" id="loginForm" class="mui-input-group">
													<div class="mui-input-row">
														<label>手机号</label>
														<input value="${sessionScope.myUser.telPhone}" name="name" id='user_zs_phone' type="text" class="mui-input-clear mui-input" placeholder="请输入您的手机号码">
													</div>
													<div class="mui-input-row noAfter">
														<label>真实姓名</label>
														<input value="${sessionScope.myUser.selfInfo}" name="password" id='user_zsxm_input' type="text" class="mui-input-clear mui-input" placeholder="请输入您的真实姓名">
													</div>
												</div>
										    </div>
										  </div>
									</div>
				
									
									
									
									<div class="qykBox" id="querenPayBoxId">
								    	<div class="qykHead">
								    		<h3>确认提交生成订单</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<div class="mui-input-group">
													<div class="mui-input-row">
														<label>购物车内金额</label>
														<input name="totalPrice"  type="text" class="mui-input-clear mui-input" value="${sessionScope.myCart.totalPrice}" readonly="readonly">
													</div>
												</div>
												<div class="mui-content-padded">
													<button  class="mui-btn mui-btn-block mui-btn-primary" onClick="submitCreateOrder(this);" >确认提交订单</button>
													<button  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" onClick="clearMyCart()" style="display:none;">清空我的购物车</button>
													<button  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" onClick="toIndex(this);" >再去买点儿</button>
												</div>
								    		</div>
								    	</div>
								    </div>
									</c:if>
									
									
									
									<c:forEach var="oi" items="${sessionScope.myCart.cartItemSet}" varStatus="vs">
										<c:set var="proPriceIdStr" value="productPriceId_${oi.productPriceId}"></c:set>
										<c:set var="p" value="${requestScope.productPriceMap[proPriceIdStr]}"></c:set>
										<c:if test='${!empty p}'>
											<div class="qykBox" productPriceId="${p.id}" pickId="${oi.pickupAddressId}">
												  <div class="qykHead">
												    <h3>订单明细-[${vs.index+1}]</h3>
												  </div>
												  <div class="qykBody">
												    <div class="qykBodyI">
												    	<!-- 产品简介 -->
												    	<ul class="mui-table-view mui-table-view-chevron" style="border-bottom:none;">
										    				<li class="mui-table-view-cell mui-media" style="border-bottom:none;">
																<a class="mui-navigate-right" href="javascript:void(0);"  style="border-bottom:none;">
																	<img class="mui-media-object mui-pull-left" src="<%=request.getContextPath()%>/${p.productImg}">
																	<div class="mui-media-body"  style="border-bottom:none;">
																		${p.productName}
																		<p>
																			净含量：${p.xilieName}  规格:${p.baozhuangName}  适用：${p.shiyongName}
																		</p>
																		<p class='mui-ellipsis'>
																			价格：￥${oi.price}
																		</p>
																	</div>
																</a>
															</li>
										    			</ul>
										    			<!-- 购买数量 -->
										    			<div class="mui-numbox" style="width: 100%;height: 50px;" data-numbox-min='1' data-numbox-max='${p.productCount}'>
																<button class="mui-numbox-btn-minus" type="button" onClick="modifyCICount(this)" productPriceId="${p.id}" ocountInputId="ocountInput_pId_${p.id}" proKucun="${p.productCount}" addOrjian="jian">-</button>
																<input id="ocountInput_pId_${p.id}" class="mui-numbox-input" type="number" value="${oi.ocount}" readonly="readonly"/>
																<button class="mui-numbox-btn-plus" type="button"  onClick="modifyCICount(this)" productPriceId="${p.id}" ocountInputId="ocountInput_pId_${p.id}" proKucun="${p.productCount}" addOrjian="add">+</button>
														</div>
														
														<c:set var="oipickId" value="${oi.pickupAddressId}"></c:set>
														<c:set var="oipickIdStr" value="pickUpAddressId_${oipickId}"></c:set>
														<c:set var="oipick" value="${applicationScope.pickUpAddressMap[oipickIdStr]}"></c:set>
														<c:set var="oipickAddIdStr" value="addId_${oipick.addressId}"></c:set>
														<c:set var="oipickAdd" value="${applicationScope.addressAll_map[oipickAddIdStr]}"></c:set>
														
														<c:set var="currentAddress" value="${applicationScope.addressAll_map[oipickAddIdStr]}" scope="request"></c:set>
														<div id="pickSelectBox_pId_${p.id}" class="pickSelectBox">
															<jsp:include page="../../../../myFrameU/address/addressListSelect.jsp">
																<jsp:param value="city" name="end"/>
																<jsp:param value="findPickListByAddId" name="endChange"/>
															</jsp:include>
														</div>
														<div id="pickListBoxId_pId_${p.id}" class="pickListBoxId">
															<!-- 自提点 -->
															<!-- 在上一步中肯定选择了，那么这里就找到那个item对应的pickId，找到这个pick，pick的addressId，然后找到这个addressId下该product的自提点 -->
															<c:forEach var="picke" items="${applicationScope.pickUpAddressMap}">
																<c:set var="pick" value="${picke.value}"></c:set>
																<c:set var="pickIDStr" value="[${pick.id}]"></c:set>
																<c:set var="addIdStr" value="addId_${pick.addressId}"></c:set>
																	<!-- 这个产品下的，产品筛选进来了 -->
																	<c:if test='${pick.addressId==oipick.addressId}'>
																		<!-- 一千选择的地区也进来了 -->
																		<div class="mui-input-row mui-radio mui-left" style="margin-bottom:5px;width:100%">
																			<c:if test='${pick.id==oi.pickupAddressId}'>
																				<label>[${pick.markedNum}]${pick.name}(${pick.addressStr})</label>
																				<input name="pickupAddressIdInput_pId_${p.id}" type="radio" value="${pick.id}" checked="checked" onClick="modifyPickAddress(this)" productPriceId="${p.id}"/>
																			</c:if>
																			<c:if test='${pick.id!=oi.pickupAddressId}'>
																				<label>[${pick.markedNum}]${pick.name}(${pick.addressStr})</label>
																				<input name="pickupAddressIdInput_pId_${p.id}" type="radio" value="${pick.id}" onClick="modifyPickAddress(this)" productPriceId="${p.id}"/>
																			</c:if>
																		</div>
																	</c:if>
															</c:forEach>
														</div>
														
														
														
												
												    </div>
												  </div>
												  	<div class="qykFoot">
										    			<div class="qykFootI">
										    				<button  class="mui-btn mui-btn-block mui-btn-primary" style="background:#8FBC8F;border:1px solid #8FBC8F" onClick="removeCartItem(this);" productPriceId="${p.id}">将该明细从购物车删除</button>
										    			</div>
										    		</div>
											</div>
										</c:if>
									</c:forEach>
									
									
									
									
									
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