<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
													<c:if test='${param.status=="WAITBEGIN"}'>
														<i class="auctionStatus waitbegin"></i>
													</c:if>
													<c:if test='${param.status=="ING"}'>
														<i class="auctionStatus ing"></i>
													</c:if>
													<c:if test='${param.status=="CLOSE"}'>
														<i class="auctionStatus close"></i>
													</c:if>
													<c:if test='${param.status=="END" || param.status=="FINISH"}'>
														<c:if test='${param.result=="BIDWAITPAY"}'>
															<i class="auctionResult waitpay"></i>
														</c:if>
														<c:if test='${param.result=="BIDNOPAY"}'>
															<i class="auctionResult nopay"></i>
														</c:if>
														<c:if test='${param.result=="BIDPAYED"}'>
															<i class="auctionResult payed"></i>
														</c:if>
														<c:if test='${param.result=="NOBID"}'>
															<i class="auctionResult nobid"></i>
															<c:if test='${param.nobidYuanyin=="NOBODY"}'>无人竞拍</c:if>
															<c:if test='${param.nobidYuanyin=="BAOLIUJIA"}'>最高价小于保留价</c:if>
															<c:if test='${param.nobidYuanyin=="USERLEVEL1"}'>最高价为虚拟用户</c:if>
														</c:if>
													</c:if>
