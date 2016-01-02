<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
        
<style>
.d-content{padding:0px !important;}
/***.d-w,.d-title,.d-e,.d-s{background-color: #f4f5f9 !important;}***/
#addRemindBox{width:100%;height:200px;padding:20px; /***background-color: #f4f5f9;****/}
#addRemindBox .wrap{width:100%;float:left;border-bottom: 1px solid #eee;min-height:30px;padding-top:10px;padding-bottom:10px;}
#addRemindBox .wrap .title{font-size:16px;color:#666;font-weight: 700;}
#addRemindBox .wrap .remindTime{height:47px;width:100%;}
#addRemindBox .wrap .remindTime .one{
height:47px;width:100%;
background:url(<%=request.getContextPath()%>/wrap/fg/image/remindTime.jpg) no-repeat;
}



#addRemindBox .wrap .remindTime .two{
height:47px;width:100%;
background:url(<%=request.getContextPath()%>/wrap/fg/image/remindTime.jpg) -0px -46px no-repeat;
}

.way-list li{padding:5px;padding-left:0px;margin-top:5px;}
#addRemindBot{text-align: center;border-bottom:none !important;}
</style>
        
        <c:if test='${requestScope.remindType=="SPECIAL"}'>
        	<div id="addRemindBox">
		         <div id="addRemindTop" class="wrap">
		        	<div class="title">提醒时间</div>
		        	<div class="remindTime">
		        		<c:if test='${requestScope.special.status=="PREVIEW"}'>
        					<c:if test='${empty requestScope.Special_PREVIEW_canRSBegin}'>
        						<div class="one remindTimeDIV" remindTime="BEGINANDWILLEND"></div>
        					</c:if>
        					<c:if test='${!empty requestScope.Special_PREVIEW_canRSBegin}'>
        						<div class="two remindTimeDIV" remindTime="BEGINANDWILLEND"></div>
        					</c:if>
        				</c:if>
        				<c:if test='${requestScope.special.status=="ING"}'>
        					<div class="two remindTimeDIV" remindTime="WILLEND"></div>
        				</c:if>
		        	</div>
		        </div>
		        <div id="addRemindMid" class="wrap">
		        	<div class="title">提醒方式</div>
		        	<ul class="way-list">
		                <li><label>短信提醒：${sessionScope.myUser.telPhone}</label></li>
		                <li style="display:none;"><label><input type="checkbox" data-value="2" checked="checked">阿里旺旺/旺信：xuxianlin8</label></li>
		                <li style="display:none;"><label><input type="checkbox" data-value="3" checked="checked">手机淘宝客户端</label></li>
	            	</ul>
		        </div>
		        <div id="addRemindBot" class="wrap">
		        	<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onClick="addRemindSet(this)" remindType="${requestScope.remindType}" remindObjectId="${requestScope.remindObjectId}">确定</button>
		        </div>
	        </div>
        </c:if>
        
       <c:if test='${requestScope.remindType=="AUCTIONPERIOD"}'>
        	<div id="addRemindBox">
		         <div id="addRemindTop" class="wrap">
		        	<div class="title">提醒时间</div>
		        	<div class="remindTime">
		        		<c:if test='${requestScope.auctionPeriod.status=="WAITBEGIN"}'>
		        			<c:if test='${empty requestScope.Special_PREVIEW_canRSBegin}'>
		        				<div class="one remindTimeDIV" remindTime="BEGINANDWILLEND"></div>
		        			</c:if>
		        			<c:if test='${!empty requestScope.Special_PREVIEW_canRSBegin}'>
		        				<div class="two remindTimeDIV" remindTime="BEGINANDWILLEND"></div>
		        			</c:if>
		        		</c:if>
		        		<c:if test='${requestScope.auctionPeriod.status=="ING"}'>
		        			<div class="two remindTimeDIV" remindTime="WILLEND"></div>
		        		</c:if>
		        	</div>
		        </div>
		        <div id="addRemindMid" class="wrap">
		        	<div class="title">提醒方式</div>
		        	<ul class="way-list">
		                <li><label>短信提醒：${sessionScope.myUser.telPhone}</label></li>
		                <li style="display:none;"><label><input type="checkbox" data-value="2" checked="checked">阿里旺旺/旺信：xuxianlin8</label></li>
		                <li style="display:none;"><label><input type="checkbox" data-value="3" checked="checked">手机淘宝客户端</label></li>
	            	</ul>
	            
		        </div>
		        <div id="addRemindBot" class="wrap">
		        	<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onClick="addRemindSet(this)" remindType="${requestScope.remindType}" remindObjectId="${requestScope.remindObjectId}">确定</button>
		        </div>
	        </div>
        </c:if>
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        <div id="remindSetBox" style="display:none">
        	<c:if test='${empty requestScope.remindSettings}'>
        		<c:if test='${requestScope.remindType=="SPECIAL"}'>
        			<div class="rsb_t">
        				<c:if test='${requestScope.special.status=="PREVIEW"}'>
        					<c:if test='${empty requestScope.Special_PREVIEW_canRSBegin}'>
        						<label><input type="checkbox" value="BEGIN" checked="checked" class="beginInput"/>开展提醒</label>
			        			<label><input type="checkbox" value="WILLEND" checked="checked" class="endInput"/>结束前10分钟提醒</label>
        					</c:if>
        					<c:if test='${!empty requestScope.Special_PREVIEW_canRSBegin}'>
        						<label><input type="checkbox" value="BEGIN" disabled="disabled" class="beginInput"/>开展提醒</label>
			        			<label><input type="checkbox" value="WILLEND" checked="checked" class="endInput"/>结束前10分钟提醒</label>
        					</c:if>
        				</c:if>
        				<c:if test='${requestScope.special.status=="ING"}'>
	        				<label><input type="checkbox" value="BEGIN" disabled="disabled" class="beginInput"/>开展提醒</label>
			        		<label><input type="checkbox" value="WILLEND" checked="checked" class="endInput"/>结束前10分钟提醒</label>
        				</c:if>
		        	</div>
		        	<div class="rsb_b">
		        		<input type="button" class="btn" value="确定提醒" onClick="addRemindSet(this)" remindType="${requestScope.remindType}" remindObjectId="${requestScope.remindObjectId}"/>
		        	</div>
        		</c:if>
        		<c:if test='${requestScope.remindType=="AUCTIONPERIOD"}'>
        			<div class="rsb_t">
        				<c:if test='${requestScope.auctionPeriod.status=="WAITBEGIN"}'>
        					<c:if test='${empty requestScope.auction_WAITBEGIN_canRSBegin}'>
        						<label><input type="checkbox" value="BEGIN" checked="checked" class="beginInput"/>开拍提醒</label>
		        				<label><input type="checkbox" value="WILLEND" checked="checked" class="endInput"/>结束前10分钟提醒</label>
        					</c:if>
        					<c:if test='${!empty requestScope.auction_WAITBEGIN_canRSBegin}'>
        						<label><input type="checkbox" value="BEGIN" disabled="disabled" class="beginInput"/>开拍提醒</label>
		        				<label><input type="checkbox" value="WILLEND" checked="checked" class="endInput"/>结束前10分钟提醒</label>
        					</c:if>
        				</c:if>
        				<c:if test='${requestScope.auctionPeriod.status=="ING"}'>
        					<label><input type="checkbox" value="BEGIN" disabled="disabled" class="beginInput"/>开拍提醒</label>
		        			<label><input type="checkbox" value="WILLEND" checked="checked" class="endInput"/>结束前10分钟提醒</label>
        				</c:if>
		        	</div>
		        	<div class="rsb_b">
		        		<input type="button" class="btn" value="确定提醒" onClick="addRemindSet(this)" remindType="${requestScope.remindType}" remindObjectId="${requestScope.remindObjectId}"/>
		        	</div>
        		</c:if>
        	</c:if>
        	<c:if test='${!empty requestScope.remindSettings}'>
        		<c:if test='${requestScope.remindType=="SPECIAL"}'>
	        		<p>您已经设置了该专场的提醒：</p>
	        		<p>
	        			<c:if test='${requestScope.remindSettings.remindTime=="BEGIN"}'>
	        				开展提醒：${requestScope.special.beginDate}会通知您!
	        			</c:if>
	        			<c:if test='${requestScope.remindSettings.remindTime=="WILLEND"}'>
	        				即将结束提醒：在结束时间(${requestScope.special.actualEndDate})之前的10分钟会通知您!
	        			</c:if>
	        			<c:if test='${requestScope.remindSettings.remindTime=="BEGINANDWILLEND"}'>
	        				开展提醒：${requestScope.special.beginDate}会通知您!
	        				即将结束提醒：在结束时间(${requestScope.special.actualEndDate})之前的10分钟会通知您!
	        			</c:if>
	        		</p>
        		</c:if>
        		<c:if test='${requestScope.remindType=="AUCTIONPERIOD"}'>
	        		<p>您已经设置了该拍品的提醒：</p>
	        		<p>
	        			<c:if test='${requestScope.remindSettings.remindTime=="BEGIN"}'>
	        				开拍提醒：${requestScope.auctionPeriod.beginDate}会通知您!
	        			</c:if>
	        			<c:if test='${requestScope.remindSettings.remindTime=="WILLEND"}'>
	        				即将结束提醒：在结束时间(${requestScope.auctionPeriod.actualEndDate})之前的10分钟会通知您!
	        			</c:if>
	        			<c:if test='${requestScope.remindSettings.remindTime=="BEGINANDWILLEND"}'>
	        				开拍提醒：${requestScope.auctionPeriod.beginDate}会通知您!
	        				即将结束提醒：在结束时间(${requestScope.auctionPeriod.actualEndDate})之前的10分钟会通知您!
	        			</c:if>
	        		</p>
        		</c:if>
        	</c:if>
        </div>
        
        
        
        
