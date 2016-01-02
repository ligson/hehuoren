<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    <div class="qykBox">
    	<div class="qykHead">
    		<h3>申请提现</h3>
    	</div>
    	<div class="qykBody">
    		<div class="qykBodyI">
    			<c:if test='${requestScope.account.availablePrice==0}'>
    				<div class="wrapTipBox">
							<div class="wrapTipBoxI">
								<p>抱歉，您当前可用余额为0，不能申请提现</p>
							</div>
					</div>
    			</c:if>
    			<c:if test='${requestScope.account.availablePrice!=0}'>
    				<ul class="sinputBox">
						<li>
							<span class="k">金额：</span>
							<span class="v">
								<span style="display:none;">onblur="mustNumber(this)" </span>
								<input type="text" class="txtInput" value="${requestScope.leastPrice}" id="priceSelect" readonly="readonly"/>
							</span>
						</li>   
						<li style="display:none;">
							<span class="k">密码：</span>
							<span class="v"><input type="password" class="txtInput" id="accountpwInputId"/></span>
						</li>  	
						<li>
							<input type="button" value="提交申请" class="button" style=""  onClick="submitApply()" id="submitApplyButton"/>
						</li>			
	    			</ul>
    			</c:if>
    		</div>
    	</div>
    	<div class="qykFoot">
    		<div class="qykFootI">
				<p class="tip">
					<span style="display:none;">
						最多可申请提现${requestScope.account.availablePrice}元
						<c:if test='${!empty requestScope.leastPrice}'>
						,本次最少提现${requestScope.leastPrice}元
						</c:if>
					</span>
					
					
				</p>
				<p class="tip">
					发起提现申请，会冻结提现金额，等待审批.
				</p>
				<p class="tip">
					您本月还有${requestScope.shengyuCount}次提现机会
				</p>	
				<p class="tip">
					审核通过后，平台会将您所提现的资金提现到您的微信零钱包中
				</p>	
    		</div>
    	</div>
    </div>