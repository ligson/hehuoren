<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>帮助中心 - 爱尔特合伙人</title>
		

		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- load MUI -->
    	<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css" rel="stylesheet" type="text/css" />
    	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/wrap/fg/css/focus1.css"/>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/image.js"></script>
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    		});
		</script>
		<style type="text/css">
		.indexEveryBox{width:100%}
		.qykBox{width:90%}
		.b{font-weight: bold;color:#7f0019}
		</style>
	</head>
	<body>
		
		
		
		<c:set var="g19Name" value="addFensi_totalPrice" scope="request"></c:set>
		<c:set var="g19" value="${applicationScope.globalMap[requestScope.g19Name]}"></c:set>
	
		<c:set var="g14Name" value="on_hhr_lvseTongdao" scope="request"></c:set>
		<c:set var="g14" value="${applicationScope.globalMap[requestScope.g14Name]}"></c:set>
	
		<c:set var="g15Name" value="hhr_syq_timeLong" scope="request"></c:set>
		<c:set var="g15" value="${applicationScope.globalMap[requestScope.g15Name]}"></c:set>
	
		
		<c:set var="g16Name" value="hhr_syq_numberFS" scope="request"></c:set>
		<c:set var="g16" value="${applicationScope.globalMap[requestScope.g16Name]}"></c:set>
	
		
		<c:set var="g21Name" value="kefu_telPhone" scope="request"></c:set>
		<c:set var="g21" value="${applicationScope.globalMap[requestScope.g21Name]}"></c:set>
	
		<c:set var="g22Name" value="admin_lxemail" scope="request"></c:set>
		<c:set var="g22" value="${applicationScope.globalMap[requestScope.g22Name]}"></c:set>
	
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="../include/rightMenu.jsp"></jsp:include>
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
								<h2>联系我们</h2>
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
										    		<h3>联系我们</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				公司名称：陕西通用石油化工有限公司
										    			</p>
										    			<p>
										    				客服电话：${g21.myValue}
										    			</p>
										    			<p>
										    				联系邮箱：${g22.myValue}
										    			</p>
										    		</div>
										    	</div>
									</div>
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
										    
								<jsp:include page="../include/footer.jsp"></jsp:include>
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