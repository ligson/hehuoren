<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
        
        <c:if test='${requestScope.pager.totalPages!=0}'>
        	<c:if test='${requestScope.pager.currentPage==1 && requestScope.pager.currentPage!=requestScope.pager.totalPages}'>
	        	<a href="${requestScope.pager.nextUrl}" class="next" style="width:100%;"><span class="arrow-right">下一页</span></a>
	        </c:if>
	        <c:if test='${requestScope.pager.currentPage==requestScope.pager.totalPages && requestScope.pager.currentPage!=1}'>
	        	<a href="${requestScope.pager.prevUrl}" class="previous"  style="width:100%;"><span class="arrow-right">上一页</span></a>
	        </c:if>
	         <c:if test='${requestScope.pager.currentPage!=1 && requestScope.pager.currentPage!=requestScope.pager.totalPages}'>
	         	<a href="${requestScope.pager.prevUrl}" class="previous" ><span class="arrow-right">上一页</span></a>
	         	<a href="${requestScope.pager.nextUrl}" class="next"><span class="arrow-right">下一页</span></a>
	         </c:if>
        </c:if>
        
