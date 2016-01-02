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
    		function searchTJR(){
    			var phone=$("#tjrPhoneInput").val();
    			if(null!=phone && phone!=""){
    				var len = phone.length;
    				if(len==11){
    					var malert=new myMuiAlert({'height':'270'});
						var data={'url':'wrap/user/searchTJR?search='+phone,'callBack':function(myAlert){
						}};
						malert.show().setData(data);
    				}else{
    					alert("请输入正确的推荐人手机号码");	
    				}
    			}else{
    				alert("请输入推荐人手机号码");
    			}
    		}
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
							$("#myAlertId").remove();
							$("#fullScreenGray").remove();
							window.location.href=allWeb+"wrap/user/findMyTJR";
						}
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
										<h2>我的推荐人</h2>
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
									
									<div class="qykBox">
								    	<div class="qykHead">
								    		<h3>我的推荐人</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    			<c:if test='${sessionScope.myUser.tuijianRenId=="0"}'>
								    				<div class="wrapTipBox">
									    				<div class="wrapTipBoxI">
									    					<p>对不起，您目前还没有自己的推荐人，不能享受合伙人价格，请尽快选择推荐人</p>
									    				</div>
									    			</div>
								    			</c:if>
								    			<c:if test='${sessionScope.myUser.tuijianRenId!="0"}'>
								    				
								    				<ul class="mui-table-view mui-table-view-chevron">
								    				<li class="mui-table-view-cell mui-media">
														<a class="mui-navigate-right" href="javascript:void(0);">
															<img class="mui-media-object mui-pull-left" src="<%=request.getContextPath()%>/${requestScope.myTJR.touxiang}">
															<div class="mui-media-body">
																${requestScope.myTJR.nicheng}
																<p class='mui-ellipsis'>
																	级别：
																	<c:if test='${requestScope.myTJR.userLevelId=="2"}'>试用期合伙人</c:if>
																	<c:if test='${requestScope.myTJR.userLevelId=="3"}'>正式合伙人</c:if>
																</p>
															</div>
														</a>
													</li>
								    				</ul>
								    				
								    				
								    			</c:if>
								    		</div>
								    	</div>
								    	<div class="qykFoot">
								    		<div class="qykFootI">
								    			<c:if test='${sessionScope.myUser.tuijianRenId=="0"}'>
								    				<input name="name" id="tjrPhoneInput"  type="text" class="mui-input-clear mui-input" placeholder="请输入推荐人手机号码">
								    				<button  class="mui-btn mui-btn-block mui-btn-primary" onclick="searchTJR();">检索推荐人</button>
								    			</c:if>
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