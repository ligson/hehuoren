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
								<h2>订单帮助</h2>
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
										    		<h3>订单的状态流程</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				1）订单有以下状态：等待付款（WAITPAY）、已付款（PAYED）、已付款（PAYED）、已取货（PICKUPED）
										    			</p>
										    			<p>
										    				2）订单的款项支付均由本系统的财务账户支付，所以请事先充值。
										    			</p>
										    		</div>
										    	</div>
										    </div>
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>订单与积分</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				1）订单金额中，每100元则奖励${g18.myValue}分作为奖励
										    			</p>
										    			<p>
										    				2）管理员可在后台修改用户的积分，用来给用户积分兑换。
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