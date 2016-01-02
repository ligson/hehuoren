<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<div class="tableBox myList " >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>账户</td>
										<td>可用余额</td>
										<td>冻结金额</td>
										<td>总金额</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="a" items="${requestScope.accountList}">
										<tr class="everyAccount" id="everyAccount_id_${a.id}">
												<td style="width:120px;">
													${a.whoName}
												</td>
												<td>
													${a.availablePrice}
												</td>
												<td>
													${a.frozenPrice}
												</td>
												<td>
													${a.totalPrice}
												</td>
												
												<td>
													<a href="javascript:void(0);" class="btn btnColor_1" onClick="selectAccount(this)" accountId="${a.id}">选择</a>
												</td>
											</tr>
											
									</c:forEach>
								</tbody>
							</table>
						</div>