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
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    		});
		</script>
		
	</head>
	<body>
		
		
		
		
		
		
		
		
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="../../fg/include/rightMenu.jsp"></jsp:include>
			<div class="my-mui-inner-wrap">
				<header id="header" class="mui-bar mui-bar-nav">
					<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
					<h1 class="mui-title">用户中心</h1>
					<a href="javascript:void(0);" class="mui-icon mui-icon-bars mui-pull-right" id="offCanvasShow" ></a>
				</header>
		
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper ">
					<div class="mui-scroll ">
						
						<div class="width100Box">
							<div class="widthNo100">
								
								<div id="myHead" class="" style="display:none;">
									<div id="myHead_l">
										<div class="mui-content-padded">
											<div class="flex-container">
												<a class="myHeadA"><span class="mui-icon mui-icon-back"></span></a>
											</div>
										</div>
									</div>
									<div id="myHead_c">
										<h2>用户中心</h2>
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
									
										
									
									<div class="userInfoBox">
										<div class="top">
											<ul class="mui-table-view">
												<li class="mui-table-view-cell">
													<a class="mui-navigate-right">
														${sessionScope.myUser.nicheng}
														<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
															(普通用户)
														</c:if>
														<c:if test='${sessionScope.myUser.userLevelId=="2"}'>
															(试用期合伙人)
														</c:if>
														<c:if test='${sessionScope.myUser.userLevelId=="3"}'>
															(合伙人)
														</c:if>
													</a>
												</li>
											</ul>
										</div>
										<div class="bot">
											<ul>
												<li>
													<p>待取货</p>
													<p>${requestScope.orderCount_waitpick}</p>
												</li>
												<li>
													<p>待付款</p>
													<p>${requestScope.orderCount_waitpay}</p>
												</li>
												<li>
													<p>粉丝数</p>
													<p>${requestScope.fensiCount}</p>
												</li>
												
												<li>
													<p>可用余额</p>
													<p>${fn:replace(requestScope.account.availablePrice,".0","")}</p>
												</li>
											</ul>
										</div>
									</div>
									
									
									
									<div class="userIndexNavBox">
											<ul class="mui-table-view">
												<li class="mui-table-view-cell">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/userInfoIndex">
														我的基本信息维护
													</a>
												</li>
												<li class="mui-table-view-cell">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/account/index">
														我的财务钱包
													</a>
												</li>
												<li class="mui-table-view-cell">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]">
														我的订单
													</a>
												</li>
												<c:if test='${sessionScope.myUser.userLevelId!="1"}'>
													<li class="mui-table-view-cell">
														<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/findMyFensi">
															我的粉丝
														</a>
													</li>
												</c:if>
												<li class="mui-table-view-cell">
														<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/findMyTJR">
															我的推荐人
															<c:if test='${sessionScope.myUser.tuijianRenId!="0"}'>(${sessionScope.myUser.tuijianRenNicheng})</c:if>
															<c:if test='${sessionScope.myUser.tuijianRenId=="0"}'>(尚未指定)</c:if>
														</a>
												</li>
												
												<li class="mui-table-view-cell">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'AccountTXApply'}]">
														审批中心
													</a>
												</li>
												
											</ul>
									</div>
									
									
									<div class="logBox">
										<div class="mui-content-padded">
											
											<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
												<c:if test='${empty requestScope.apply}'>
													<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" onClick="applyHHR()">申请成为合伙人</button>
												</c:if>
												<c:if test='${!empty requestScope.apply}'>
													<c:if test='${requestScope.apply.speed=="WAIT"||requestScope.apply.speed=="ING"}'>
														<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" style="background:#AAAAAA;border:#AAA;">已提交合伙人申请，等待审核</button>
													</c:if>
													<c:if test='${requestScope.apply.speed=="FINISH"}'>
														<c:if test='${requestScope.apply.result=="APPLYERROR"}'>
															<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" onClick="applyHHR()">审核不通过，重新申请成为合伙人</button>	
														</c:if>
													</c:if>
												</c:if>
												
											</c:if>
											<c:if test='${sessionScope.myUser.userLevelId=="2"}'>
												<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" >您目前为试用期合伙人</button>
												<p style="width:100%;text-align: center;padding-bottom:10px;">(在未来${requestScope.shengyu_day}天内再吸纳${requestScope.shengyu_fensi}个粉丝即可转正)</p>
											</c:if>
											<c:if test='${sessionScope.myUser.userLevelId=="3"}'>
												<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger">您目前为正式合伙人</button>
											</c:if>
											<a id='reg' class="mui-btn mui-btn-block mui-btn-primary" href="<%=request.getContextPath()%>/wrap/user/logout">退出</a>
										</div>
									</div>
									
									
									<jsp:include page="../../fg/include/footer.jsp"></jsp:include>
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
</html>