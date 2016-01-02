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
										<h2>审批中心</h2>
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
													<c:if test='${fn:contains(requestScope.queryArgs,"AccountTXApply")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'AccountTXApply'}]"" method="" contentDiv="item1_1" class="selected">提现申请</a>
														<a href="<%=request.getContextPath()%>/wrap/user/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'UserLevelHHR'}]" method="" contentDiv="item1_2" >合伙人申请</a>
													</c:if>
													<c:if test='${fn:contains(requestScope.queryArgs,"UserLevelHHR")}'>
														<a href="<%=request.getContextPath()%>/wrap/user/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'AccountTXApply'}]"" method="" contentDiv="item1_1">提现申请</a>
														<a href="<%=request.getContextPath()%>/wrap/user/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'UserLevelHHR'}]" method="" contentDiv="item1_2"  class="selected">合伙人申请</a>
													</c:if>
												</div>
												
												<div class="tabContentBox">
													<div id="item1_1" class="tabContent selected">
														<ul class="mui-table-view mui-table-view-chevron">
															<c:forEach var="o" items="${requestScope.applyList}">
																<li class="mui-table-view-cell mui-media">
																		<a class="mui-navigate-right" href="javascript:void(0);">
																			<div class="mui-media-body">
																				<p style="font-weight: bold;font-size:14px;">${o.applyTitle}</p>
																				<p style="">
																					申请类型：
																					<c:if test='${o.applyTypeKey=="AccountTXApply"}'>提现</c:if>
																					<c:if test='${o.applyTypeKey=="UserLevelHHR"}'>合伙人</c:if>
																					，
																					审核状态：
																					<c:if test='${o.result=="APPLYOK"}'>
																						通过
																					</c:if>
																					<c:if test='${o.result=="APPLYERROR"}'>
																						失败
																					</c:if>
																					<c:if test='${o.speed=="WAIT" || o.speed=="ING"}'>
																						待审
																					</c:if>																				</p>
																				<p class='mui-ellipsis'>
																					申请日期：
																					<fmt:formatDate value="${o.relDate}" pattern="MM-dd HH:mm" />
																					，审核日期：
																					<fmt:formatDate value="${o.dealDate}" pattern="MM-dd HH:mm" />
																				</p>
																			</div>
																		</a>
																	</li>
															</c:forEach>
														</ul>
														<jsp:include page="../../../pager.jsp"></jsp:include>
														
									
													</div>
													<div id="item1_2" class="tabContent">
													</div>
													<div id="item1_3" class="tabContent">
													</div>
													<div id="item1_4" class="tabContent">
													</div>
												</div>
											</div>
									
									<div class="tableBox myList " style="display:none;">
										<table cellpadding="0" cellspacing="0">
											<thead>
												<tr>
													<td style="width:40px;">类型</td>
													<td>时间</td>
													<td>标题</td>
													<td style="width:40px;">结果</td>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="o" items="${requestScope.applyList}">
													<tr>
															<td>
																<c:if test='${o.applyTypeKey=="AccountTXApply"}'>提现</c:if>
																<c:if test='${o.applyTypeKey=="USERSHIMINGRENZHENG"}'>实名<br/>认证</c:if>
																<c:if test='${o.applyTypeKey=="USERLEVELUPDATEVIP"}'>升级<br/>VIP</c:if>
															</td>
															<td>
																<fmt:formatDate value="${o.relDate}" pattern="MM-dd" /><br/>
																<fmt:formatDate value="${o.relDate}" pattern="HH:mm" />
															</td>
															<td>
																${o.applyTitle}
															</td>
															<td>
																<c:if test='${o.result=="APPLYOK"}'>
																	通过
																</c:if>
																<c:if test='${o.result=="APPLYERROR"}'>
																	失败
																</c:if>
																<c:if test='${o.speed=="WAIT" || o.speed=="ING"}'>
																	待审
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