<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

								<div class="qykBox" >
								    	<div class="qykHead">
								    		<h3>产品价格组合</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI" style="overflow-y: auto;height:330px;">
								    			<ul class="mui-table-view mui-table-view-chevron">
								    				<c:forEach var="pp" items="${requestScope.productPriceList}">
								    					<li class="mui-table-view-cell mui-media">
															<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/product/toBuy?id=${pp.id}">
																<div class="mui-media-body">
																	<p style="font-weight: bold;font-size:14px;">系列：${pp.xilieName}</p>
																	<p>
																		包装：${pp.baozhuangName}
																	</p>
																	<p>
																		价格：
																		<!-- 价格 -->
																		<!-- 如果自己是合伙人，那就合伙人价格 -->
																		<!-- 如果自己不是合伙人，那就判断是否有推荐人，如果有推荐人，则是合伙人价格，如果没有推荐人，则实体价格 -->
																		<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
																			<c:if test='${sessionScope.myUser.tuijianRenId=="0"}'>
																				￥${pp.price1}
																			</c:if>
																			<c:if test='${sessionScope.myUser.tuijianRenId!="0"}'>
																				￥${pp.price2}
																			</c:if>
																		</c:if>
																		<c:if test='${sessionScope.myUser.userLevelId!="1"}'>
																			￥${pp.price2}
																		</c:if>
																	</p>
																</div>
															</a>
														</li>
								    				</c:forEach>
								    			</ul>
								    			
								    		</div>
								    	</div>
								    </div>



