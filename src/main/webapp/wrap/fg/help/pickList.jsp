<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
													<ul class="mui-table-view mui-table-view-chevron">
															<c:forEach var="o" items="${requestScope.pickUpAddressList}">
																<c:set var="addIdStr" value="addId_${o.addressId}"></c:set>
																<c:set var="add" value="${applicationScope.addressAll_map[addIdStr]}"></c:set>
																<li class="mui-table-view-cell mui-media">
																	<a class="mui-navigate-right" href="javascript:void(0);">
																		<div class="mui-media-body">
																			<p style="font-weight: bold;font-size:14px;">自提点名称：${o.name}</p>
																			<p style="">
																				地区：${add.allName}
																			</p>
																			<p class='mui-ellipsis'>
																				具体地址：${o.addressStr}
																			</p>
																			<p class='mui-ellipsis'>
																				客服电话：${o.telPhone}
																			</p>
																		</div>
																	</a>
																</li>	
															</c:forEach>		
														</ul>	