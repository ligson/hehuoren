<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>${requestScope.product.name} - 爱尔特合伙人</title>
		

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
    		function submitAddCart_next(a){
    			var productId=$(a).attr("productId");
    			//获取当前price组合，通过条件ajax查询productPriceId
    			//根据productPriceId到下一步
    			
    		}
    		
    		
    		function selectThis(li){
    			var productId=$("#offCanvasContentScroll").attr("productId");
    			var val=$(li).attr("pppvv");
    			if(null!=val && val!=""){
    				var search="yes";
    				var myClass=$(li).attr("class");
    				if(null!=myClass && myClass!="" && myClass!=undefined && myClass!="undefined"){
    					if(myClass=="selected"){
    						//选中了的话，就不需要查询了
    						search="no";
    					}
    				}
    				
    				if(search=="yes"){
    					//第一、处理自己为选中状态
    					var curEvery=$(li).parents(".everyPPZH");
    					var cur_allLis=curEvery.find("li");
    					cur_allLis.removeClass("selected");
    					$(li).addClass("selected");
    					
    					var curResultInput=curEvery.find("input");
    					var curResultInput_oldResult=curResultInput.val();
    					curResultInput.val(val);
    					
    					//第二、获取三对的值
						var xilieName=getCurSelectValue("xilieName");
						var baozhuangName=getCurSelectValue("baozhuangName");
						var shiyongName=getCurSelectValue("shiyongName");
						
						
						if(null!=xilieName && xilieName!="" && null!=baozhuangName && baozhuangName!="" && null!=shiyongName && shiyongName!=""){
							$.ajax( {
								type : "POST",
								dataType : "json",
								url : allWeb+"wrap/proprice/search?xilieName="+xilieName+"&baozhuangName="+baozhuangName+"&shiyongName="+shiyongName+"&productId="+productId,
								success : function(msg) {
									if(null!=msg && msg!=""){
										//alert(msg);
										//alert(msg.xilieName);
										//js修改本页面的价格、数量
										var canHHRPrice=$("#offCanvasContentScroll").attr("canHHRPrice");
										if(canHHRPrice=="false"){
											$("#curPPPriceId").html(msg.price1);	
										}else{
											$("#curPPPriceId").html(msg.price2);
										}
										//js修改下一步按钮，有了productPriceId
										$("#footer_button_next2Cart").find("button").attr("productPriceId",msg.id);	
									}else{
										$("#footer_button_next2Cart").find("button").attr("productPriceId","");	
										alert("抱歉，没有相应的产品价格组合");
										$("#curPPPriceId").html("没有该组合价格");
									}
									
								}
							})
						}
						
						
    				}
    				
    			}
    		}
    		
    		function getCurSelectValue(keyPy){
    			var everyId="everyPPZH_ID_"+keyPy;
    			var everyResultId="";
    			var curValue=$("#"+everyId).find("input").val();
    			return curValue;
    		}
    		
    		
    		function submitAddCart_next(b){
    			var productPriceId=$(b).attr("productPriceId");
    			if(null!=productPriceId && productPriceId!=""){
    				window.location.href=allWeb+"wrap/product/toBuy_step2?id="+productPriceId;
    			}else{
    				alert("当前没有该价格组合！");
    			}
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
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper " productId="${requestScope.product.id}"
				 canHHRPrice="${requestScope.canUseHHRPrice}"
				>
					<div class="mui-scroll ">
						<div class="width100Box">
							<div class="widthNo100">
								<div class="mysuojinDiv" style="margin-top:50px;">
									
									<c:set var="maxPrice_1_str" value="maxPrice_1"></c:set>
									<c:set var="minPrice_1_str" value="minPrice_1"></c:set>
									<c:set var="maxPrice_2_str" value="maxPrice_2"></c:set>
									<c:set var="minPrice_2_str" value="minPrice_2"></c:set>
									<div class="proBox">
										<div class="proBox_l">
											<img src="<%=request.getContextPath()%>/${requestScope.product.mainImage}"/>
										</div>
										<div class="proBox_r">
											<p>
												${requestScope.product.name}
											</p>
											<p class="curPPPriceSpan" id="curPPPriceId">
												<c:if test='${requestScope.canUseHHRPrice==true}'>
													${requestScope.ppFirst.price2}
												</c:if>
												<c:if test='${requestScope.canUseHHRPrice==false}'>
													${requestScope.ppFirst.price1}
												</c:if>	
											</p>
											<p>
																		
												<c:if test='${requestScope.canUseHHRPrice==true}'>
													${requestScope.priceMap[minPrice_2_str]}-${requestScope.priceMap[maxPrice_2_str]}
												</c:if>
												<c:if test='${requestScope.canUseHHRPrice==false}'>
													${requestScope.priceMap[minPrice_1_str]}-${requestScope.priceMap[maxPrice_1_str]}
												</c:if>
											</p>
										</div>
									</div>
									<div class="propriceListBox">
										<c:forEach var="pppv" items="${requestScope.pppvList}">
											<div class="everyPPZH"  id="everyPPZH_ID_${pppv.keyPy}">
												<c:if test='${pppv.keyPy=="xilieName"}'>
													<input type="hidden" id="everyPPZH_ID_${pppv.keyPy}_Result" value="${requestScope.ppFirst.xilieName}"/>
												</c:if>
												<c:if test='${pppv.keyPy=="baozhuangName"}'>
													<input type="hidden" id="everyPPZH_ID_${pppv.keyPy}_Result" value="${requestScope.ppFirst.baozhuangName}"/>
												</c:if>
												<c:if test='${pppv.keyPy=="shiyongName"}'>
													<input type="hidden" id="everyPPZH_ID_${pppv.keyPy}_Result" value="${requestScope.ppFirst.shiyongName}"/>
												</c:if>
												
												<div class="everyPPZH-T">
													${pppv.keyName}:
												</div>
												<div class="everyPPZH-B">
													<ul>
														<c:forEach var="pppvv" items="${pppv.noSave_keyValues}">
															<c:if test='${pppvv==requestScope.ppFirst.xilieName}'>
																<li class="selected" onClick="selectThis(this)" pppvv="${pppvv}">${pppvv}</li>
															</c:if>
															<c:if test='${pppvv==requestScope.ppFirst.baozhuangName}'>
																<li class="selected" onClick="selectThis(this)" pppvv="${pppvv}">${pppvv}</li>
															</c:if>
															<c:if test='${pppvv==requestScope.ppFirst.shiyongName}'>
																<li class="selected" onClick="selectThis(this)" pppvv="${pppvv}">${pppvv}</li>
															</c:if>
															<c:if test='${pppvv!=requestScope.ppFirst.xilieName && pppvv!=requestScope.ppFirst.baozhuangName && pppvv!=requestScope.ppFirst.shiyongName}'>
																<li onClick="selectThis(this)" pppvv="${pppvv}">${pppvv}</li>
															</c:if>
														</c:forEach>
													</ul>
												</div>
											</div>											
										</c:forEach>
									</div>
									<div class="propriceListBox" style="display:none;">
											<div class="everyPPZH">
												<div class="everyPPZH-T">
													净含量:
												</div>
												<div class="everyPPZH-B">
													<ul>
														<c:forEach var="pp" items="${requestScope.productPriceList}">
															<c:if test='${pp.noSave_select=="YES"}'>
																<li class="selected">${pp.xilieName}</li>
															</c:if>
															<c:if test='${pp.noSave_select!="YES"}'>
																<li>${pp.xilieName}</li>
															</c:if>
														</c:forEach>
													</ul>
												</div>
											</div>
											<div class="everyPPZH">
												<div class="everyPPZH-T">
													规格:
												</div>
												<div class="everyPPZH-B">
													<ul>
														<c:forEach var="pp" items="${requestScope.productPriceList}">
															<c:if test='${pp.noSave_select=="YES"}'>
																<li class="selected">${pp.baozhuangName}</li>
															</c:if>
															<c:if test='${pp.noSave_select!="YES"}'>
																<li>${pp.baozhuangName}</li>
															</c:if>
														</c:forEach>
													</ul>
												</div>
											</div>
											<div class="everyPPZH">
												<div class="everyPPZH-T">
													适用:
												</div>
												<div class="everyPPZH-B">
													<ul>
														<c:forEach var="pp" items="${requestScope.productPriceList}">
															<c:if test='${pp.noSave_select=="YES"}'>
																<li class="selected">${pp.shiyongName}</li>
															</c:if>
															<c:if test='${pp.noSave_select!="YES"}'>
																<li>${pp.shiyongName}</li>
															</c:if>
														</c:forEach>
													</ul>
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
				<jsp:param value="add2Cart_next" name="footerType"/>
			</jsp:include>
					
		</div>
		</div>
	</body>
</html>