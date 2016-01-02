<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 


<c:forEach var="s" items="${requestScope.specialList}" varStatus="vs">
															<c:if test='${vs.index%2==0}'>
																<c:set var="twoClass" value=""></c:set>
															</c:if>
															<c:if test='${vs.index%2!=0}'>
																<c:set var="twoClass" value="two"></c:set>
															</c:if>
															<div class="everySpecial ${twoClass}">
																<c:if test='${s.status=="END"||s.status=="FINISH"}'>
																	<div class="spresult"></div>
																</c:if>
																<c:if test='${s.status=="ING"}'>
																	<div class="numBox">
																		<p class="num">${s.chujiaCount}</p>
																		<p>人竞价</p>
																	</div>
																</c:if>
																<c:if test='${s.status!="ING"}'>
																	<div class="numBox wg">
																		<p class="num">${s.weiguanNum}</p>
																		<p>人围观</p>
																	</div>
																</c:if>
																<div class="everySpecial_t">
																	<a href="special.html">
																		<img src="<%=request.getContextPath()%>/${s.image}" />
																	</a>
																</div>
																<div class="everySpecial_b">
																	<p class="sptitle">
																		<a href="">${s.name}</a>
																	</p>
																	<c:if test='${s.status=="END"||s.status=="FINISH"}'>
																		<p class="sptime">
																			结束时间：${s.actualEndDate}
																		</p>
																	</c:if>
																	<c:if test='${s.status=="PREVIEW"}'>
																	<div class="timeBox pre">
																		<div class="timeDIV">距开展：</div>
																		<div class="timeDIV">
																				<jsp:include page="../include/djsTime.jsp">
																					<jsp:param value="djsTimeBox_${s.id}_yz" name="boxId"/>
																					<jsp:param value="${s.beginDate}" name="endDate"/>
																				</jsp:include>
																		</div>
																	</div>
																	</c:if>
																	<c:if test='${s.status=="ING"}'>
																	<div class="timeBox">
																		<div class="timeDIV">距结束：</div>
																		<div class="timeDIV">
																				<jsp:include page="../include/djsTime.jsp">
																					<jsp:param value="djsTimeBox_${s.id}_yz" name="boxId"/>
																					<jsp:param value="${s.actualEndDate}" name="endDate"/>
																				</jsp:include>
																		</div>
																	</div>
																	</c:if>
																</div>
															</div>
														</c:forEach>
















