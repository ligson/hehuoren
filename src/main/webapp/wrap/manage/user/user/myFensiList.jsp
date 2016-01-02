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
										<h2>我的粉丝</h2>
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
									
									
									<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
								    	<div class="wrapTipBox">
								    		<div class="wrapTipBoxI">
								    			<p>对不起，您需要先申请成为合伙人，才可以吸纳粉丝</p>
								    		</div>
								    	</div>
								   </c:if>
									
									<c:if test='${sessionScope.myUser.userLevelId!="1"}'>
										<c:if test='${empty requestScope.fensiList}'>
											<div class="wrapTipBox">
									    		<div class="wrapTipBoxI">
									    			<p>您目前还没有一个粉丝哦</p>
									    		</div>
									    	</div>
										</c:if>
										
										<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
											<c:forEach var="u" items="${requestScope.fensiList}">
												<li class="mui-table-view-cell">
													<div class="mui-slider-cell">
														<div class="oa-contact-cell mui-table">
															<div class="oa-contact-avatar mui-table-cell" style="width:80px">
																<img src="<%=request.getContextPath()%>/img/user/${u.touxiang}" style="width:60px;height:60px;border-radius: 50%"/>
															</div>
															<div class="oa-contact-content mui-table-cell">
																<div class="mui-clearfix">
																	<h4 class="oa-contact-name">${u.nicheng}</h4>
																	<span class="oa-contact-position mui-h6">
																		<c:if test='${u.userLevelId=="1"}'>普通用户</c:if>
																		<c:if test='${u.userLevelId=="2"}'>试用期合伙人</c:if>
																		<c:if test='${u.userLevelId=="3"}'>正式合伙人</c:if>
																	</span>
																</div>
																<p class="oa-contact-email mui-h6">
																	${u.telPhone}
																</p>
															</div>
														</div>
													</div>
												</li>
											</c:forEach>
										</ul>
									</c:if>
									<jsp:include page="../../../pager.jsp"></jsp:include>
									
									
									
									
									
									
									
									
									
									
									
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