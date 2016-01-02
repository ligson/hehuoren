<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<c:forEach var="pick" items="${requestScope.pickUpAddressList}">
	<c:set var="pickIDStr" value="[${pick.id}]"></c:set>
	<c:set var="addIdStr" value="addId_${pick.addressId}"></c:set>
	<div class="mui-input-row mui-radio mui-left" style="margin-bottom:5px;width:100%">
		<label>${pick.name}(${pick.addressStr})</label>
		<input name="pickupAddressIdInput" type="radio" value="${pick.id}" >
	</div>
</c:forEach>



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