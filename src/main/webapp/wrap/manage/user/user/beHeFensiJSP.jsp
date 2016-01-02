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
    		function selectTJR(a){
    			var tjrId = $(a).attr("tjrId");
    			$.ajax( {
					type : "POST",
					url : allWeb+"wrap/user/beHeFensi?queryUserType=id&id="+tjrId,
					success : function(msg) {
						if(msg!=null && msg!=""){
							alert(msg);
						}else{
							alert("恭喜您，成功指定了自己的推荐人");
							window.location.href=allWeb+"wrap/user/findMyTJR";
						}
					}
				})
    		}
    		
    		
    		//关注指定的微信号
    		function weixinAddContact(name){
    		        WeixinJSBridge.invoke("addContact", {webtype: "1",username: "gh_19cc3fdf0201"}, function(e) {
    		                WeixinJSBridge.log(e.err_msg);
    		                //e.err_msg:add_contact:added 已经添加
    		                //e.err_msg:add_contact:cancel 取消添加
    		                //e.err_msg:add_contact:ok 添加成功
    		                if(e.err_msg == 'add_contact:added' || e.err_msg == 'add_contact:ok'){
    		                    //关注成功，或者已经关注过
    		                }
    		        })
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
										<h2>扫描成为他的粉丝</h2>
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
								
								
									<c:if test='${requestScope.guanzhuWX=="no"}'>
										<div class="qykBox">
										    	<div class="qykHead">
										    		<h3>抱歉，你还没有关注”爱尔特“公众平台</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<div class="wrapTipBox">
										    				<div class="wrapTipBoxI">
										    					<p>请您先关注“爱尔特”微信公共平台，长按下面的二维码，点击弹出的识别图中的二维码</p>
										    				</div>
										    			</div>
										    			<img src="<%=request.getContextPath()%>/wrap/fg/image/aierte.jpg" style="width:100%"/>
										    		</div>
										    	</div>
										    </div>
									</c:if>
									<c:if test='${requestScope.guanzhuWX=="yes"}'>
										<div class="qykBox">
									    	<div class="qykHead">
									    		<h3>以下是您扫描的推荐人，确定成为他的粉丝吗？</h3>
									    	</div>
									    	<div class="qykBody">
									    		<div class="qykBodyI">
									    			
									    			<ul class="mui-table-view mui-table-view-chevron">
								    					<li class="mui-table-view-cell mui-media">
															<a class="mui-navigate-right" href="javascript:void(0);">
																<img class="mui-media-object mui-pull-left" src="<%=request.getContextPath()%>/img/user/${requestScope.he.touxiang}">
																<div class="mui-media-body">
																	${requestScope.he.nicheng}
																	<p class='mui-ellipsis'>
																		级别：
																		<c:if test='${requestScope.he.userLevelId=="2"}'>试用期合伙人</c:if>
																		<c:if test='${requestScope.he.userLevelId=="3"}'>正式合伙人</c:if>
																	</p>
																</div>
															</a>
														</li>
														<li>
															<c:if test='${sessionScope.myUser.id!=requestScope.he.id}'>
																<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" onClick="selectTJR(this)" tjrId="${requestScope.he.id}">选定他为您的推荐人</button>
															</c:if>
															
														</li>
													</ul>
									    			
									    		</div>
									    	</div>
									    </div>
									</c:if>
									
									
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