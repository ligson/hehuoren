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
			<jsp:include page="include/rightMenu.jsp"></jsp:include>
			<div class="my-mui-inner-wrap">
				<div class="myaTabHeadBox">
									<ul>
										<li><a class="selected" href="<%=request.getContextPath()%>/wrap/product/findId?id=${requestScope.product.id}">产品介绍</a></li>
										<li><a href="<%=request.getContextPath()%>/wrap/product/findId?id=${requestScope.product.id}&toFW=toFW" >自提/服务</a></li>
									</ul>
								</div>
				<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper ">
					<div class="mui-scroll ">
						<div class="width100Box">
							<div class="widthNo100">
								
								
								<div class="mysuojinDiv"  style="margin-top:50px;">
									<div id="productJIESHAO">
										<c:if test='${requestScope.product.lunboImage2!="" && requestScope.product.lunboImage2!=null}'>
											<div class="proMainBox" style="padding-top:0px;padding-bottom:0px;">
												<p class="pimgBoxp">
													<a href="<%=request.getContextPath()%>/wrap/fg/help/help-hhr.jsp">
														<img src="<%=request.getContextPath()%>/${requestScope.product.lunboImage2}"/>
													</a>
												</p>
												<p class="pimgBoxp">
													<img src="<%=request.getContextPath()%>/${requestScope.product.mainImage}"/>
												</p>
											</div>
										</c:if>
										<c:if test='${requestScope.product.lunboImage2==null || requestScope.product.lunboImage2==""}'>
										<div class="proMainBox">
											<h1 class="ptiname">${requestScope.product.name}</h1>
											<p class="pm_dszdjp">
												<span class="k">电商最低价：</span>
												<span class="v">${requestScope.product.price1}元</span>
											</p>
											<p class="pm_hhrj">
												<span class="k">合伙人推荐价：</span>
												<span class="v">${requestScope.product.price2}元</span>
											</p>
											<p class="pm_pcount" style="display:none;">
												<span>热销${requestScope.product.saleCount}件</span>
											</p>
											<p class="pm_wsmwm">
												<a href="<%=request.getContextPath()%>/wrap/fg/help/help-hhr.jsp">为什么我们这么便宜？</a>
											</p>
											<p class="pimgBoxp" style="margin-top:20px;">
												<img src="<%=request.getContextPath()%>/${requestScope.product.mainImage}"/>
											</p>
										</div>
										</c:if>
										<div class="proConBox">
											${requestScope.productContent.content}
										</div>
									</div>
									<jsp:include page="include/footer.jsp"></jsp:include>
								</div>
								
								
						</div>
					</div>
				</div>
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
			
			
			
			<jsp:include page="fixFooter.jsp">
				<jsp:param value="toBuy" name="footerType"/>
			</jsp:include>
					
		</div>
		</div>
	</body>
</html>