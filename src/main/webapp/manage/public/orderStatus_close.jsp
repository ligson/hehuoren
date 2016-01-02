<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
        
<style>
.orderBox .pub-steps ul li{width:220px;}
</style>
        
        <%
        	String stepLiClass=request.getParameter("stepLiClass");
        	if(null!=stepLiClass && !stepLiClass.equals("")){
        		String[] stepLiClassArray=stepLiClass.split(",");
        		request.setAttribute("stepLiClassArray", stepLiClassArray);
        	}
        	
        %>
        <ul class="clearfix">
		        <li id="pub-step-1-nav" class=" ${requestScope.stepLiClassArray[0]} step-first">
		            <span class="step-deco"></span>
		            <span class="step-val">1</span>
		            <span class="step-title">
		                <span class="opt-ver">参加竞拍</span>
		            </span>
		        </li>
		        <li id="pub-step-2-nav" class="${requestScope.stepLiClassArray[1]} step-next">
		            <span class="step-deco"></span>
		            <span class="step-val">2</span>
		            <span class="step-title">拍卖结束,系统中标下单</span>
		        </li>
		        <li id="pub-step-3-nav" class="${requestScope.stepLiClassArray[2]} step-next">
		            <span class="step-deco"></span>
		            <span class="step-val">3</span>
		            <span class="step-title">已过支付期，买家未支付</span>
		        </li>
		        <li id="pub-step-4-nav" class="${requestScope.stepLiClassArray[3]} step-next">
		            <span class="step-deco"></span>
		            <span class="step-val">4</span>
		            <span class="step-title">
		            	系统将保证金支付给商家<br/>
		            	(${param.noSave_noPay2ShopDate})
		           </span>
		        </li>
		        <li class="step-last  step-next ${requestScope.stepLiClassArray[4]}">
		            <span class="step-deco"></span>
		            <span class="step-val">5</span>
		            <span class="step-title">交易完成</span>
		        </li>
		    </ul>