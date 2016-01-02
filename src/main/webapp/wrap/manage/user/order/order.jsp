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
    		
    		
    		
    		function to3PayOrder(b){
    			//获取id和password
    			var outerType=$(b).attr("outerType");
    			//var password=$("#caiwupasswordInput").val();
    			var id=$(b).attr("orderId");
    			if(null!=id && id!="" && null!=outerType && outerType!=""){
					$.ajax( {
						type : "POST",
						dataType : "json",
						url : allWeb+"wrap/user/order/to3PayOrder?id="+id+"&outerType="+outerType,
						success : function(msg) {
							var error = msg.error;
							var emsg=msg.errorMessage;
							
							if (null == error || error == "") {
								var redirect = msg.redirect;
								var outerType = msg.outerType;
								//alert("redirect=" + redirect + "==outerInterface="+ outerInterface + "====pcOrWrap=" + pcOrWrap);
								if (outerType == "ZHIFUBAO") {
									//window.open(redirect, "_blank");
								} else if (outerType == "WEIXIN") {
									//wxPay(msg);
									window.location.href=redirect;
								}

							} else {

								alert(msg.errorMessage);
							}
						}
					})
/*     				if(null!=password && password!=""){

    				}else{
    					alert("请输入财务密码，如果第一次下单不知道财务密码，请到用户后台管理中心查看");
    				} */
    			}
    		}
    		function toWXPayOrder(b){
    			var id=$(b).attr("orderId");
    			if(null!=id && id!=""){
    				window.location.href=allWeb+"weixin/pay/toPayOrder?orderId="+id;
    			}
    		}
    		function querenPickOrder(b){
    			var id=$(b).attr("orderId");
    			var fukuanFangshi=$(b).attr("fukuanFangshi");
    			if(null!=id && id!="" && null!=fukuanFangshi && fukuanFangshi!=""){
    				if(fukuanFangshi=="zitifukuan"){
    					var pwd=prompt("管理员输入自提密码","");
    					if(null!=pwd && pwd!=""){
    						$.ajax( {
    							type : "POST",
    							url : allWeb+"wrap/user/order/picked?id="+id+"&fukuanFangshi="+fukuanFangshi+"&pwd="+pwd,
    							success : function(msg) {
    								if(null!=msg && msg!=""){
    									alert(msg);
    								}else{
    									alert("恭喜你完成自提!");
    									$("#wanchengzitiInputButton").remove();
    								}
    							}
    						})
    					}else{
    						alert("请管理员输入自提密码");
    					}
    				}else{
    					$.ajax( {
							type : "POST",
							url : allWeb+"wrap/user/order/picked?id="+id+"&fukuanFangshi="+fukuanFangshi,
							success : function(msg) {
								if(null!=msg && msg!=""){
									alert(msg);
								}else{
									alert("恭喜你完成自提!");
									$("#wanchengzitiInputButton").remove();
								}
							}
						})
    				}
    			}
    		}
    		
    		function modifyPickAddress(i){
    			var val=$(i).val();
    			var orderItemId=$(i).attr("orderItemId");
    			if(null!=val && val!="" && null!=orderItemId && orderItemId!=""){
    				if (confirm("您确认要修改自提点吗？如果修改，则同一产品下所有明细均修改为新自提点")) {
    					$.ajax( {
    						type : "POST",
    						url : allWeb+"wrap/user/order/modifyOIPUAId?pickUpAddressId="+val+"&orderItemId="+orderItemId,
    						success : function(msg) {
    							if(null!=msg && msg!=""){
    								alert(msg);
    							}else{
    								window.location.href=window.location.href;
    								alert("修改自提点完成");
    								
    							}
    						}
    					})
	    		    }
    			}
    		}
    		function orderPayType_huodaofukuan(b){
    			var orderId=$(b).attr("orderId");
    			$.ajax( {
					type : "POST",
					url : allWeb+"wrap/user/order/orderPayType_hdfk?id="+orderId,
					success : function(msg) {
						if(null!=msg && msg!=""){
							alert(msg);
						}else{
							$("#querenPayBoxId").remove();
							alert("您已将该订单支付方式设定为自提付款");
						}
					}
				})
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
								
								<div class="mysuojinDiv" >
									<c:if test='${requestScope.order.status=="PAYING"}'>
									<div class="qykBox" id="querenPayBoxId">
								    	<div class="qykHead">
								    		<h3>确认已经自提付款</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<button id="wanchengzitiInputButton" class="mui-btn mui-btn-block mui-btn-primary" onclick="querenPickOrder(this);" orderId="${requestScope.order.id}" fukuanFangshi="zitifukuan">确认已经自提</button>
								    		</div>
								    	</div>
								    </div>
									</c:if>
									
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
													<c:if test='${requestScope.order.orderPayType=="Unknown"}'>
														<div class="wrapTipBox">
										    				<div class="wrapTipBoxI">
										    					<p>建议选择微信支付</p>
										    				</div>
										    			</div>
														<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onClick="to3PayOrder(this);" outerType="WEIXIN" orderId="${requestScope.order.id}">微信支付</button>
														<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onClick="orderPayType_huodaofukuan(this);"  orderId="${requestScope.order.id}">提货付款</button>
													</c:if>
													<c:if test='${requestScope.order.orderPayType=="Huodaofukuan"}'>
													
													</c:if>
													<c:if test='${requestScope.order.orderPayType=="outerType"}'>
													<c:if test='${empty requestScope.order.outerType}'>
														<!-- 没决定用啥支付（财务账号、支付宝、微信） -->
														<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onClick="to3PayOrder(this);" outerType="WEIXIN" orderId="${requestScope.order.id}">微信支付</button>
													</c:if>
													<c:if test='${!empty requestScope.order.outerType}'>
														<c:if test='${requestScope.order.outerType=="WEIXIN"}'>
															<c:if test='${empty requestScope.order.outerMarkedNum}'>
																<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onClick="to3PayOrder(this);" outerType="WEIXIN" orderId="${requestScope.order.id}">微信支付</button>
															</c:if>
															<c:if test='${!empty requestScope.order.outerMarkedNum}'>
																<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onClick="toWXPayOrder(this);"  orderId="${requestScope.order.id}">微信支付</button>
															</c:if>
														</c:if>
													</c:if>
													</c:if>
													
												</div>
								    		</div>
								    	</div>
								    </div>
									</c:if>
									
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
								    		<h3>订单概要</h3>
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
																	<c:if test='${requestScope.order.status=="PAYING"}'>[待自提付款]</c:if>
																	<c:if test='${requestScope.order.status=="PICKUPED"}'>[已结束]</c:if>
																</p>
															</div>
														</a>
													</li>			
												</ul>	
								    		</div>
								    	</div>
								    </div>
									
									
									<c:forEach var="oi" items="${requestScope.order.oiSet}" varStatus="vs">
										<c:set var="proPriceIdStr" value="productPriceId_${oi.productPriceId}"></c:set>
										<c:set var="pp" value="${requestScope.productPriceMap[proPriceIdStr]}"></c:set>
										
										<c:if test='${!empty pp}'>
											<div class="qykBox" productPriceId="${pp.id}" pickId="${oi.pickupAddressId}">
												  <div class="qykHead">
												    <h3>订单明细-[${vs.index+1}]</h3>
												  </div>
												  <div class="qykBody">
												    <div class="qykBodyI">
												    	<!-- 产品简介 -->
												    	<ul class="mui-table-view mui-table-view-chevron" style="border-bottom:none;">
										    				<li class="mui-table-view-cell mui-media" style="border-bottom:none;">
																<a class="mui-navigate-right" href="javascript:void(0);"  style="border-bottom:none;">
																	<img class="mui-media-object mui-pull-left" src="<%=request.getContextPath()%>/${pp.productImg}">
																	<div class="mui-media-body"  style="border-bottom:none;">
																		${pp.productName}
																		<p>
																			净含量：${pp.xilieName}  规格:${pp.baozhuangName}  适用：${pp.shiyongName}
																		</p>
																		<p class='mui-ellipsis'>
																			价格：￥${oi.price}
																		</p>
																	</div>
																</a>
															</li>
										    			</ul>
										    			<!-- 购买数量 -->
										    			<div class="mui-numbox" style="width: 100%;height: 50px;">
															<input id="ocountInput" class="mui-numbox-input" type="number" value="${oi.ocount}" readonly="readonly"/>
														</div>
														
														<c:set var="oipickId" value="${oi.pickupAddressId}"></c:set>
														<c:set var="oipickIdStr" value="pickUpAddressId_${oipickId}"></c:set>
														<c:set var="oipick" value="${applicationScope.pickUpAddressMap[oipickIdStr]}"></c:set>
														<c:set var="oipickAddIdStr" value="addId_${oipick.addressId}"></c:set>
														<c:set var="oipickAdd" value="${applicationScope.addressAll_map[oipickAddIdStr]}"></c:set>
														
														<c:set var="currentAddress" value="${applicationScope.addressAll_map[oipickAddIdStr]}" scope="request"></c:set>
														<!-- 自提点 -->
														<div id="pickSelectBox_pId_${pp.id}" class="pickSelectBox">
															<jsp:include page="../../../../myFrameU/address/addressListSelect.jsp">
																<jsp:param value="city" name="end"/>
																<jsp:param value="findPickListByAddId" name="endChange"/>
															</jsp:include>
														</div>
														<div id="pickListBoxId_pId_${pp.id}" class="pickListBoxId">
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
																				<input id="radio_pick_id_${pick.id}" name="pickupAddressIdInput_pId_${pp.id}" type="radio" value="${pick.id}" checked="checked" onClick="modifyPickAddress(this)" orderItemId="${oi.id}"/>
																			</c:if>
																			<c:if test='${pick.id!=oi.pickupAddressId}'>
																				<label>[${pick.markedNum}]${pick.name}(${pick.addressStr})</label>
																				<input  id="radio_pick_id_${pick.id}" name="pickupAddressIdInput_pId_${pp.id}" type="radio" value="${pick.id}" onClick="modifyPickAddress(this)" orderItemId="${oi.id}"/>
																			</c:if>
																		</div>
																		
																	</c:if>
															</c:forEach>
														</div>
														
														
														
														
												
												    </div>
												  </div>
											</div>
										</c:if>
									</c:forEach>
									
									
									
									<c:if test='${requestScope.order.status=="PAYED" || requestScope.order.status=="PICKUPED"}'>
									<c:if test='${requestScope.order.orderPayType!="Huodaofukuan"}'>
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
													<button id="wanchengzitiInputButton" class="mui-btn mui-btn-block mui-btn-primary" onclick="querenPickOrder(this);" orderId="${requestScope.order.id}" fukuanFangshi="zaixian">确认已经自提</button>
												</c:if>
								    		</div>
								    	</div>
								    </div>
									</c:if>
									
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