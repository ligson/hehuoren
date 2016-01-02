<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>${sessionScope.myUser.nicheng} - 用户中心 - 艺藏家</title>
		

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
    			$("#offCanvasContentScroll .djsTimeBox").each(function(){
    				var curTimeBox=$(this);
    				var boxId=curTimeBox.attr("id");
    				var endDate=curTimeBox.attr("endDate");
    				fg_initTimer(boxId,endDate)
    			});
    		});
    		
    		function spending(b){
    			var id=$(b).attr("orderId");
    			var myAddressId=$("#myAddressInputId").val();
    			var name = $("#maNameInput").val();
    			var telPhone = $("#maPhoneInput").val();
    			var addressId=$("#myAddress_addIdBox").find("select[name=addressId]").val();
    			//alert(addressId);
    			var jutiAddress=$("#maJtdzInput").val();
    			var zipcode=$("#maYbInput").val();
    			if(null!=name && name!="" && telPhone!=null && telPhone!="" && null!=addressId && addressId!=0
    				&& 	null!=jutiAddress && jutiAddress!="" && null!=zipcode && zipcode!=""
    			){
    				var pwd = $("#accountpwInputId").val();
    				if(null!=pwd && pwd!=""){
    					$.ajax( {
    						type : "POST",
    						url : allWeb+"wrap/user/order/spendingOrder?id="+id+"&password="+pwd+"&myAddressId="+myAddressId
    								+"&name="+name+"&telPhone="+telPhone+"&zipcode="+zipcode+"&addressId="+addressId+"&jutiAddress="+jutiAddress,
    						success : function(msg) {
    							if(msg!=null && msg!=""){
    								mui.toast(msg);
    							}else{
    								mui.toast("支付成功");
    							}
    						}
    					})
    				}else{
    					mui.toast("请填写财务密码");
    				}
    			}else{
    				mui.toast("请填写正确的收货地址");
    			}
    		}
    		function shouhuo(a){
    			var id=$(b).attr("orderId");
    			$.ajax( {
					type : "POST",
					url : allWeb+"wrap/user/order/receiptGoods?id="+id,
					success : function(msg) {
						if(msg!=null && msg!=""){
							mui.toast(msg);
						}else{
							mui.toast("收货成功");
						}
					}
				})
    		}	
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
								
								<div class="mysuojinDiv">
								
								
									<h1 style="font-size:14px;padding:10px;font-weight: bold;">
										订单状态：
										<c:if test='${requestScope.order.status=="CLOSE"}'>已关闭</c:if>
										<c:if test='${requestScope.order.status=="WAITPAY"}'>中标待付款</c:if>
										<c:if test='${requestScope.order.status=="PAYED"}'>
											<c:if test='${requestScope.order.aiType=="BAOZHENGJIN"}'>逾期未付款已支付保证金</c:if>
											<c:if test='${requestScope.order.aiType=="QUANKUAN"}'>全款付款</c:if>
										</c:if>
									</h1>
								
									<div class="qykBox">
								    	<div class="qykHead">
								    		<h3>订单详情</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<table>
								    				<tbody>
								    					<tr>
								    						<td style="width:40px;">拍品</td>
								    						<td>
								    							<a href="#">${requestScope.auctionPeriod.title}</a>
								    						</td>
								    					</tr>
								    					<tr>
								    						<td style="width:40px;">总额</td>
								    						<td>
								    							${requestScope.order.price}
								    						</td>
								    					</tr>
								    					<tr>
								    						<td style="width:40px;">时间</td>
								    						<td>
								    							<fmt:formatDate value="${requestScope.order.createDate}" pattern="yy-MM-dd HH:mm" />
								    							<c:if test='${requestScope.order.status=="WAITPAY"}'>
								    								(于<fmt:formatDate value="${requestScope.order.noSave_payTimeOverDate}" pattern="yy-MM-dd HH:mm" />前支付)
								    							</c:if>
								    						</td>
								    					</tr>
								    					
								    					<tr>
								    						<td style="width:40px;">机构</td>
								    						<td>
								    							${requestScope.auctionPeriod.shopName}
								    						</td>
								    					</tr>
								    				</tbody>
								    			</table>
								    		</div>
								    	</div>
								    </div>
								
									<c:if test='${requestScope.order.status=="WAITPAY"}'>
									<div class="qykBox">
								    	<div class="qykHead">
								    		<h3>选择收货地址</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    					<ul class="sinputBox">
								    						<input type="hidden" value="${requestScope.myAddressDefault.id}" id="myAddressInputId"/>
															<li>
																<span class="k">姓名：</span>
																<span class="v"><input type="text" value="${requestScope.myAddressDefault.name}" class="txtInput"  id="maNameInput"/></span>
															</li>   
															<li>
																<span class="k">电话：</span>
																<span class="v"><input value="${requestScope.myAddressDefault.telPhone}" type="text" class="txtInput" id="maPhoneInput"/></span>
															</li>  
															<li>
																<span class="k">地区：</span>
																<span class="v mySelectBox" id="myAddress_addIdBox">
																	<jsp:include page="../../../../myFrameU/address/addressListSelect.jsp">
																		<jsp:param value="city" name="end"/>
																	</jsp:include>
																</span>
															</li>  
															
															<li>
																<span class="k">地址：</span>
																<span class="v"><input type="text" value="${requestScope.myAddressDefault.jutiAddress}" class="txtInput" id="maJtdzInput"/></span>
															</li>	
															<li>
																<span class="k">邮编：</span>
																<span class="v"><input type="text" value="${requestScope.myAddressDefault.zipcode}" class="txtInput" id="maYbInput"/></span>
															</li>	
										    			</ul>
								    		</div>
								    	</div>
								    </div>
								    </c:if>
									<c:if test='${requestScope.order.status=="PAYED"}'>
										<c:if test='${requestScope.order.aiType=="QUANKUAN"}'>
											<div class="qykBox">
										    	<div class="qykHead">
										    		<h3>选择收货地址</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    					<ul class="sinputBox">
										    						<input type="hidden" value="${requestScope.myAddressDefault.id}" id="myAddressInputId"/>
																	<li>
																		<span class="k">姓名：</span>
																		<span class="v"><input type="text" value="${requestScope.myAddressDefault.name}" readonly="readonly" class="txtInput"  id="maNameInput"/></span>
																	</li>   
																	<li>
																		<span class="k">电话：</span>
																		<span class="v"><input readonly="readonly"  value="${requestScope.myAddressDefault.telPhone}" type="text" class="txtInput" id="maPhoneInput"/></span>
																	</li>  
																	<li>
																		<span class="k">地区：</span>
																		<span class="v mySelectBox" id="myAddress_addIdBox">
																			<jsp:include page="../../../../myFrameU/address/addressListSelect.jsp">
																				<jsp:param value="city" name="end"/>
																			</jsp:include>
																		</span>
																	</li>  
																	
																	<li>
																		<span class="k">地址：</span>
																		<span class="v"><input readonly="readonly"  type="text" value="${requestScope.myAddressDefault.jutiAddress}" class="txtInput" id="maJtdzInput"/></span>
																	</li>	
																	<li>
																		<span class="k">邮编：</span>
																		<span class="v"><input readonly="readonly"  type="text" value="${requestScope.myAddressDefault.zipcode}" class="txtInput" id="maYbInput"/></span>
																	</li>	
												    			</ul>
										    		</div>
										    	</div>
										    </div>
										</c:if>
								    </c:if>
								    
								    <c:if test='${requestScope.order.status=="WAITPAY"}'>
								    	<div class="qykBox">
									    	<div class="qykHead">
									    		<h3>完成支付</h3>
									    	</div>
									    	<div class="qykBody">
									    		<div class="qykBodyI">
									    			<ul class="sinputBox">
														<li>
															<span class="k">金额：</span>
															<span class="v"><input type="text"  value="${requestScope.order.price}" class="txtInput" readonly="readonly"/></span>
														</li>   
														<li>
															<span class="k">密码：</span>
															<span class="v"><input type="password" class="txtInput" id="accountpwInputId"/></span>
														</li>  	
														<li>
															<input type="button" value="完成支付" class="button" style=""  onClick="spending(this)" orderId="${requestScope.order.id}"/>
														</li>			
									    			</ul>
									    		</div>
									    	</div>
									    	<div class="qykFoot">
									    		<div class="qykFootI">
														
									    		</div>
									    	</div>
									    </div>
								    </c:if>
								    
								    
								    
								    <c:if test='${requestScope.order.status=="PAYED"}'>
								    	<c:if test='${requestScope.order.aiType=="QUANKUAN"}'>
								    		<div class="qykBox">
										    	<div class="qykHead">
										    		<h3>支付详情</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<ul class="sinputBox">
															<li>
																<span class="k">金额：</span>
																<span class="v"  style="text-align: left;">
																	${requestScope.order.price}
																</span>
															</li>   
															<li>
																<span class="k">时间：</span>
																<span class="v" style="text-align: left;">
																	<fmt:formatDate value="${requestScope.order.payDate}" pattern="yy-MM-dd HH:mm" />
																</span>
															</li>  
															<li>
																<span class="k">类型：</span>
																<span class="v" style="text-align: left;">
																	全额付款
																</span>
															</li> 	
										    			</ul>
										    		</div>
										    	</div>
										    	<div class="qykFoot">
										    		<div class="qykFootI">
															
										    		</div>
										    	</div>
										    </div>
								    	</c:if>
								    	<c:if test='${requestScope.order.aiType=="BAOZHENGJIN"}'>
								    		<div class="qykBox">
										    	<div class="qykHead">
										    		<h3>支付详情</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<ul class="sinputBox">
															<li>
																<span class="k">付款金额：</span>
																<span class="v"  style="text-align: left;">
																	${requestScope.order.baozhengjinPrice}
																</span>
															</li>   
															<li>
																<span class="k">付款时间：</span>
																<span class="v" style="text-align: left;">
																	<fmt:formatDate value="${requestScope.order.payDate}" pattern="yy-MM-dd HH:mm" />
																</span>
															</li>  
															<li>
																<span class="k">付款类型：</span>
																<span class="v" style="text-align: left;">
																	逾期未付款，将保证金付给机构
																</span>
															</li> 	
										    			</ul>
										    		</div>
										    	</div>
										    	<div class="qykFoot">
										    		<div class="qykFootI">
															
										    		</div>
										    	</div>
										    </div>
								    	</c:if>
								    </c:if>
								    
								    
								     <c:if test='${requestScope.order.status=="SENDOUTGOODS"}'>
								     	<div class="qykBox">
										    	<div class="qykHead">
										    		<h3>确认收货</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<ul class="sinputBox">
															<li>
																<input type="button" value="确认收货" class="button" style=""  onClick="shouhuo(this)" orderId="${requestScope.order.id}"/>
															</li>	
										    			</ul>
										    		</div>
										    	</div>
										    	<div class="qykFoot">
										    		<div class="qykFootI">
															
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
	<script type="text/javascript">
	initLeftRight();
	</script>
</html>