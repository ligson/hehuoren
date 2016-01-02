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
    		
    		function modifyNC(){
    			var addressId=$("#shop_addIdBox").find("select[name=addressId]").val();
    			var nicheng=$("#nichengInput").val();
    			if(null!=nicheng && nicheng!=""){
    				$.ajax( {
						type : "POST",
						url : allWeb+"/wrap/user/modifyUser?nicheng="+nicheng+"&addressId="+addressId,
						success : function(msg) {
							mui.toast("修改成功");
						}
					})
    			}else{
    				mui.toast("请填写正确的昵称");
    			}
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
										<h2>修改昵称</h2>
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
								    		<h3>用户昵称</h3>
								    	</div>
								    	<div class="qykBody">
								    		<div class="qykBodyI">
								    					<ul class="sinputBox">
															<li>
																<span class="k">姓名：</span>
																<span class="v"><input type="text" value="${requestScope.myUser.nicheng}" class="txtInput"  id="nichengInput"/></span>
															</li>  
															<li>
																<span class="k">地区：</span>
																<span class="v mySelectBox" id="shop_addIdBox">
																	<jsp:include page="../../../../myFrameU/address/addressListSelect.jsp">
																		<jsp:param value="city" name="end"/>
																	</jsp:include>
																</span>
															</li>  
															<li>
																<input type="button" value="提交修改" class="button" style=""  onClick="modifyNC()"/>
															</li>	
										    			</ul>
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