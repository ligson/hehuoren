<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>自提点总览 - 爱尔特合伙人</title>
		

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
    		function searchPickList(){
    			var txt=$("#searchInputId").val();
    			if(null!=txt && txt!=""){
    				$.ajax( {
    					type : "POST",
    					url : allWeb+"pua/search?txt="+txt,
    					success : function(msg) {
    						alert(msg);
    						$("#pickListAjaxBoxId").html(msg);
    					}
    				})
    			}
    		}
		</script>
		<style type="text/css">
		.indexEveryBox{width:100%}
		.qykBox{width:90%}
		.b{font-weight: bold;color:#7f0019}
		.qykBodyI img{width:100%}
		</style>
	</head>
	<body>
		
		
		
		
		
		
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
								<h2>自提点总览</h2>
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
								
									<div class="mui-card">
										<ul class="mui-table-view">
											<li class="mui-table-view-cell mui-collapse" >
												<a class="mui-navigate-right" href="javascript:void(0);">检索自提点</a>
												<div class="mui-collapse-content" style="display:block;">
													<div class="mui-input-group">
														<div class="mui-input-row">
															<label>检索</label>
															<input type="text" placeholder="输入检索文字" id="searchInputId">
														</div>
														
														<div class="mui-button-row">
															<button class="mui-btn mui-btn-primary" type="button" onclick="searchPickList()">提交检索</button>&nbsp;&nbsp;
														</div>
													</div>
												</div>
											</li>
										</ul>
									</div>	
									<div id="pickListAjaxBoxId">
										
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