<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    
    <%
    	String endDate = request.getParameter("endDate");
    	if(null!=endDate && !endDate.equals("")){
    		endDate=endDate.replaceAll("-","/");
    		endDate=endDate.replace(".0","");
    		request.setAttribute("endDate", endDate);
    		//System.out.println(endDate+"|||||||||||||||||||||||||||||||||||");
    	}
    %>
    
    
    <div class="djsTimeBox ${param.djsTimeBoxClass}" id="${param.boxId}" endDate="${requestScope.endDate}">
																<div class="djsgroup group_h">
																	<span class="num">0</span>
																	<span class="txt">时</span>
																</div>
																<div class="djsgroup group_m">
																	<span class="num">0</span>
																	<span class="txt">分</span>
																</div>
																<div class="djsgroup group_s">
																	<span class="num">0</span>
																</div>
	</div>															
