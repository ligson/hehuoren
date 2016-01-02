<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>${sessionScope.myUser.nicheng} - 用户中心 - 爱尔特合伙人</title>
		

		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- load MUI -->
    	<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/manage/css/main.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/manage/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/mymui.alert.js"></script>
    	
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    			
    		});
    		function wxToPayAi(a){
    			var accountItemId = $(a).attr("accountItemId");
    			window.location.href=allWeb+"weixin/pay/test/toPayAi?accountItemId="+accountItemId;
    		}
		</script>
		
	</head>
	<body>
		
		
		
		
		
		
		
		
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="../../../fg/include/rightMenu.jsp"></jsp:include>
			<div class="my-mui-inner-wrap">
				
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper ">
					<div class="mui-scroll ">
						
						<div class="width100Box">
							<div class="widthNo100">
								<div id="myHead" class="">
									<div id="myHead_l">
										<div class="mui-content-padded">
											<div class="flex-container">
												<a class="myHeadA"><span class="mui-icon mui-icon-back"></span></a>
											</div>
										</div>
									</div>
									<div id="myHead_c">
										<h2>财务明细</h2>
									</div>
									<div id="myHead_r">
										<div class="mui-content-padded">
											<div class="flex-container">
												<a  class="myHeadA bkred" id="offCanvasShow"><span class="mui-icon mui-icon-list"></span></a>
											</div>
										</div>
									</div>
								</div>
								
								<div class="mysuojinDiv">
									<div class="tabBox">
												<div class="tabHeadBox">
												
													<!-- 
													<c:if test='${fn:contains(requestScope.queryArgs,"RECHARGE")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'RECHARGE'}]" method="" contentDiv="item1_1" class="selected">充值</a>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'INCOME'}]" method="" contentDiv="item1_2" >收入</a>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'WITHDRAWALS'}]" method="" contentDiv="item1_4" class="">提现</a>
													</c:if>
													 -->
													<c:if test='${fn:contains(requestScope.queryArgs,"INCOME")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'INCOME'}]" method="" contentDiv="item1_2" class="selected">收入</a>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'WITHDRAWALS'}]" method="" contentDiv="item1_4">提现</a>
													</c:if>
													<!-- 
													<c:if test='${fn:contains(requestScope.queryArgs,"SPENDING")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'RECHARGE'}]" method="" contentDiv="item1_1" >充值</a>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'INCOME'}]" method="" contentDiv="item1_2" >收入</a>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'SPENDING'}]" method="" contentDiv="item1_3" class="selected">支出</a>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'WITHDRAWALS'}]" method="" contentDiv="item1_4">提现</a>
													</c:if>
													 -->
													<c:if test='${fn:contains(requestScope.queryArgs,"WITHDRAWALS")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'INCOME'}]" method="" contentDiv="item1_2" >收入</a>
														<a href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'WITHDRAWALS'}]" method="" contentDiv="item1_4" class="selected">提现</a>
													</c:if>
													
												</div>
												<div class="tabContentBox">
													<div id="item1_1" class="tabContent selected">
														
														
														<ul class="mui-table-view mui-table-view-chevron">
															<c:forEach var="ai" items="${requestScope.accountItemList}">
																<c:set var="show" value="show"></c:set>
																<c:if test='${ai.itemType=="RECHARGE"}'>
																	<c:if test='${ai.status=="WAIT"}'>
																		<c:set var="show" value="noshow"></c:set>
																	</c:if>
																</c:if>
																<c:if test='${show=="show"}'>
																	<li class="mui-table-view-cell mui-media">
																		<a class="mui-navigate-right" href="javascript:void(0);">
																			<div class="mui-media-body">
																				<p style="font-weight: bold;font-size:14px;">${ai.info}</p>
																				<p style="">
																					编号：${ai.markedNum},金额：${ai.price}元
																				</p>
																				<p class='mui-ellipsis'>
																					交易日期：<fmt:formatDate value="${ai.relDate}" pattern="yy-MM-dd HH:mm" />
																					交易状态：
																						<c:if test='${ai.status=="FINISH"}'>
																							<c:if test='${ai.itemType=="RECHARGE"}'>成功</c:if>
																							<c:if test='${ai.itemType=="SPENDING"}'>成功</c:if>
																							<c:if test='${ai.itemType=="INCOME"}'>成功</c:if>
																							<c:if test='${ai.itemType=="FROZEN"}'>成功</c:if>
																							<c:if test='${ai.itemType=="itemType"}'>成功</c:if>
																						</c:if>
																						<c:if test='${ai.status=="CLOSE"}'>
																							已关闭
																						</c:if>
																					
																				</p>
																				
																			</div>
																		</a>
																	</li>
																</c:if>
																
															</c:forEach>
														</ul>
														<jsp:include page="../../../pager.jsp"></jsp:include>
														<div class="tableBox myList" style="display:none;">
										<table cellpadding="0" cellspacing="0">
											<thead>
												<tr>
													<td>类型</td>
													<td>金额</td>
													<td>日期</td>
													<td>状态</td>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="ai" items="${requestScope.accountItemList}">
													<tr>
														<td>
															<c:if test='${ai.itemType=="RECHARGE"}'>充值</c:if>
																<c:if test='${ai.itemType=="SPENDING"}'>支付</c:if>
																<c:if test='${ai.itemType=="INCOME"}'>收入</c:if>
																<c:if test='${ai.itemType=="FROZEN"}'>冻结</c:if>
																<c:if test='${ai.itemType=="WITHDRAWALS"}'>提现</c:if>
														</td>
														<td>${fn:replace(ai.price,".0","")}</td>
														<td class="link">
															<fmt:formatDate value="${ai.relDate}" pattern="yy-MM-dd HH:mm" />
														</td>
														<td class="link">
															<c:if test='${ai.status=="WAIT"}'>
																<c:if test='${ai.itemType=="RECHARGE"}'>
																	<c:if test='${ai.outerType=="WEIXIN"}'>
																	<input  type="button" value="去充值" class="btn btnColor_1" onClick="wxToPayAi(this);" accountItemId="${ai.id}"/>
																	</c:if>
																</c:if>
																<c:if test='${ai.itemType=="SPENDING"}'>
																	<input  type="button" value="去支付" class="btn btnColor_1" />
																</c:if>
															</c:if>
															<c:if test='${ai.status=="FINISH"}'>
																<c:if test='${ai.itemType=="RECHARGE"}'>成功</c:if>
																<c:if test='${ai.itemType=="SPENDING"}'>成功</c:if>
																<c:if test='${ai.itemType=="INCOME"}'>成功</c:if>
																<c:if test='${ai.itemType=="FROZEN"}'>成功</c:if>
																<c:if test='${ai.itemType=="itemType"}'>成功</c:if>
															</c:if>
															<c:if test='${ai.status=="CLOSE"}'>
																已关闭
															</c:if>
														</td>
													</tr>
												</c:forEach>
												
											</tbody>
											<tfoot>
												<tr>
													<td colspan="4">
														<div id="pageBoxOuter">
															<div id="pageBox">
																<jsp:include page="../../../pager.jsp"></jsp:include>
															</div>
														</div>
													</td>
												</tr>
											</tfoot>
										</table>
									</div>
									
													</div>
													<div id="item1_2" class="tabContent">
													</div>
													<div id="item1_3" class="tabContent">
													</div>
													<div id="item1_4" class="tabContent">
													</div>
												</div>
											</div>
								
										
									
									
									<jsp:include page="../../../fg/include/footer.jsp"></jsp:include>
										
								</div>
						
								
						</div>
					</div>
				</div>
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
		</div>
	</body>
	<script type="text/javascript">
	initLeftRight();
	</script>
</html>