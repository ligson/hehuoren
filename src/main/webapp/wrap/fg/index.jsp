<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>爱尔特合伙人</title>
		

		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- load MUI -->
    	<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    			
    		});
		</script>
		<style type="text/css">
		.mui-content-padded{margin:0px;}
		</style>
	</head>
	<body>
		
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="include/rightMenu.jsp"></jsp:include>
			<div class="my-mui-inner-wrap">
				
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper ">
					<div class="mui-scroll ">
						
						<div class="width100Box">
							<div class="widthNo100">
								
								
								
								<div id="myHead" class="absoult">
							<div id="myHead_l">
								<div class="mui-content-padded">
									<div class="flex-container">
										<a class="myHeadA"><span class="mui-icon mui-icon-back"></span></a>
									</div>
								</div>
							</div>
							<div id="myHead_c">
								<h2>商城首页</h2>
							</div>
							<div id="myHead_r">
								<div class="mui-content-padded">
									<div class="flex-container">
										<a  class="myHeadA bkred" id="offCanvasShow"><span class="mui-icon mui-icon-list"></span></a>
									</div>
								</div>
							</div>
						</div>
						
						
								<div class="myaHead">
									<div class="myaHeadI">
										<span class="logo"></span>
										<span class="title">爱尔特合伙人</span>
									</div>
								</div>
		
		
								<div class="mysuojinDiv">
									<div class="proListBox">
										<c:forEach var="p" items="${requestScope.productList}">
											<a class="everyPro" href="<%=request.getContextPath()%>/wrap/product/findId?id=${p.id}">
												<div class="everyPro_alert_price">
													<p>节省
														${fn:replace(p.price1Price2,".0","")}
													元</p>
												</div>
												<div class="everyPro_alert_price sc">
													<p>已售
														${p.saleCount}
													件</p>
												</div>
												<div class="everyProT">
													<div class="proImgBox">
														<img src="<%=request.getContextPath()%>/${p.mainImage}"/>
													</div>
												</div>
												<div class="everyProM">
													
												</div>
												<div class="everyProB">
													<div class="everyProB_l">
														<p class="pname">${p.name}</p>
													</div>
													<div class="everyProB_r">
														<div class="everyProBrI">
															<p class="hhrPP">
																<span class="k">合伙人价:</span>
																<span class="v">￥${fn:replace(p.price2,".0","")}</span>
															</p>
															<p class="wdPP">
																<span class="k">电商最低价:</span>
																<span class="v">￥${fn:replace(p.price1,".0","")}</span>
															</p>
														</div>
													</div>
												</div>
											</a>
										</c:forEach>
									</div>
									<jsp:include page="include/footer.jsp"></jsp:include>
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