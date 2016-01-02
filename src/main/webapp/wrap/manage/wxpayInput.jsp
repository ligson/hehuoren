<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="wxPayInputBox" style="height:1000px;width:100%;background:#eee;display:none;">
		<input type="text" id="wxInputId_appId" value="${requestScope.wxPayJSAPINeedEntity.appId}"/>
		<input type="text" id="wxInputId_timeStamp" value="${requestScope.wxPayJSAPINeedEntity.timeStamp}"/>
		<input type="text" id="wxInputId_nonceStr" value="${requestScope.wxPayJSAPINeedEntity.nonceStr}"/>
		<input type="text" id="wxInputId_package" value="${requestScope.wxPayJSAPINeedEntity.wpackage}"/>
		<input type="text" id="wxInputId_signType" value="MD5"/>
		<input type="text" id="wxInputId_paySign" value="${requestScope.wxPayJSAPINeedEntity.paySign}"/>
		
		
		<input type="text" id="wxInputId_myself_finishCallback_url" value="wrap/user/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]"/>
	</div>
