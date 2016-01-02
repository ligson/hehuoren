<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
	<c:if test='${empty sessionScope.myUser}'>
				<div class="fixBot" style="height:auto;">
					<c:set var="g23Name" value="webUrl" scope="request"></c:set>
					<c:set var="g23" value="${applicationScope.globalMap[requestScope.g23Name]}"></c:set>
					<c:set var="webUrlVAR" value="${g23.myValue}"></c:set>
					<a id='reg' href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx247c7e42bcde403d&redirect_uri=${webUrlVAR}wrap/toUserLogin&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect" 
					class="mui-btn mui-btn-block mui-btn-primary" style="background:#0191d7;margin-bottom:0px;border:none;">请先登录进行购买</a>
				</div>							
	</c:if>
	<c:if test='${!empty sessionScope.myUser}'>
		<c:if test='${param.footerType=="toBuy"}'>
			<div class="fixBot">
					<div class="fixBotI buyBut" style="width:100%;">
						<div class="buyBut_l" style="">
							<div class="buyButl_img" style="margin-left:20px;">
								<img src="<%=request.getContextPath()%>/img/user/${sessionScope.myUser.touxiang}"/>					
							</div>
							<div class="buyButl_info">
								<p>
									${sessionScope.myUser.nicheng}
									<span style="display:none;">
																	
									</span>
								</p>
								<p style="display:none;">
									<c:if test='${sessionScope.myUser.tuijianRenId=="0"||sessionScope.myUser.tuijianRenId==""}'>暂无推荐人</c:if>
									<c:if test='${sessionScope.myUser.tuijianRenId!="0" && sessionScope.myUser.tuijianRenId!=""}'>
										推荐人:${sessionScope.myUser.tuijianRenNicheng}
									</c:if>
								</p>
								<p>
										<c:if test='${sessionScope.myUser.userLevelId=="1"}'>用户级别：普通用户</c:if>
										<c:if test='${sessionScope.myUser.userLevelId=="2"}'>用户级别：试用期合伙人</c:if>
										<c:if test='${sessionScope.myUser.userLevelId=="3"}'>用户级别：正式合伙人</c:if>		
								</p>
							</div>
						</div>
						<div class="buyBut_r">
							<a style="display:none;" href="javascript:void(0);" onClick="alertPPList(this)" productId="${requestScope.product.id}" class="buyAButton">点击购买</a>
							<a  href="<%=request.getContextPath()%>/wrap/product/toBuy_step1?productId=${requestScope.product.id}" class="buyAButton">点击购买</a>
						</div>
					</div>
				</div>
		</c:if>
		<c:if test='${param.footerType=="toHHR"}'>
			<div class="fixBot">
				<div class="fixBotI" style="width:100%;">
					<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
						<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" onClick="applyHHR()">申请成为合伙人</button>
					</c:if>
					<c:if test='${sessionScope.myUser.userLevelId=="2"}'>
						<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger">您目前为试用期合伙人</button>
					</c:if>
					<c:if test='${sessionScope.myUser.userLevelId=="3"}'>
						<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger">您目前为合伙人</button>
					</c:if>			
				</div>
			</div>
		</c:if>
		<c:if test='${param.footerType=="add2Cart"}'>
			<div class="fixBot" style="height:auto;">
				<button onClick="submitAddCart(this)" productPriceId="${requestScope.productPrice.id}" class="mui-btn mui-btn-block mui-btn-primary" style="background:#FF4500;margin-bottom:0px;border:none;color:#fff;">加入我的购物车</button>
			</div>
		</c:if>
		<c:if test='${param.footerType=="add2Cart_next"}'>
			<div class="fixBot" style="height:auto;" id="footer_button_next2Cart">
				<button onClick="submitAddCart_next(this)" productPriceId="${requestScope.ppFirst.id}" class="mui-btn mui-btn-block mui-btn-primary" style="background:#FF4500;margin-bottom:0px;border:none;color:#fff;">下一步</button>
			</div>
		</c:if>
		
	</c:if>
						