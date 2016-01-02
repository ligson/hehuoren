<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<div class="tableBox myList " >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>店铺/用户</td>
										<td>店铺名/昵称</td>
										<td>手机号</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="a" items="${requestScope.msuList}">
										<tr class="everyAccount" id="everyAccount_id_${a.telPhone}">
												<td style="width:50px;text-align: center;">
													<c:if test='${a.userOrShop=="SHOP"}'>店铺</c:if>
													<c:if test='${a.userOrShop=="USER"}'>用户</c:if>
												</td>
												<td>
													${a.name}
												</td>
												<td>
													${a.telPhone}
												</td>
												
												<td>
													<a href="javascript:void(0);" class="btn btnColor_1" onClick="selectRole(this)" telPhone="${a.telPhone}">选择</a>
												</td>
											</tr>
											
									</c:forEach>
								</tbody>
							</table>
						</div>