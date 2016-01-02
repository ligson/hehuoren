<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>关于爱尔特 - 爱尔特合伙人</title>
		

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
		.qykBodyI img{width:100%}
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
								<h2>关于爱尔特</h2>
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
										    		<h3>公司简介</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				陕西通用石油化工有限公司是一家现代化股份制企业，成立于2005年，坐落于古城西安曲江开发区。
										    			</p>
										    			<p>
										    				公司成立以来一直以专注于研制、生产天然气发动机专用润滑油为主，现已取得各类天然气汽车专用润滑油产品国家专利证书，公司拥有现代化的操作平台，并设有润滑节能研究所、油品研发检测中心。各类生产设备、检测检验仪器齐全，并与2006年通过了ISO9001国际质量体系认证，年润滑油生产能力已达20000吨以上。由于多年来一直专注于天然气发动机润滑技术的研发，公司生产的爱尔特牌系列天然气发动机专用润滑油各项参数均已达到国际先进水平，业已成为国内多个城市天然气公交系统指定合作品牌。
										    			</p>
										    			<p>
															公司建立了以满足顾客利益为核心的企业经营理念，专注于发展天然气发动机润滑油事业，以打造中国天然气发动机润滑油领导品牌为目标，努力寻求与相关行业建立长期合作伙伴关系，共同发展和进步。
										    			</p>
										    		</div>
										    	</div>
										    </div>
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>企业文化</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				<span class="b">企业使命：</span>
										    				为每一台天然气发动机提供最佳的润滑解决方案！
										    			</p>
										    			<p>
										    				<span class="b">发展愿景：</span>
										    				打造中国天然气发动机专用润滑油领导品牌。
										    			</p>
										    			<p>
										    				<span class="b">核心价值观：</span>
										    				以人为立足之本，以专注专业为指导方针，以顾客利益为核心，坚持质量第一的经营理念。
										    			</p>
										    		</div>
										    	</div>
										    </div>
										    
										    
										    
										    
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>公司资质及荣誉证书</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				<img src="<%=request.getContextPath()%>/wrap/fg/image/about-zz-yyzz.jpg"/>
										    			</p>
										    			<p>
										    				<img src="<%=request.getContextPath()%>/wrap/fg/image/about-zz-xy.jpg"/>
										    			</p>
										    			<p>
										    				<img src="<%=request.getContextPath()%>/wrap/fg/image/about-zz-zzjg.jpg"/>
										    			</p>
										    			<p>
										    				<img src="<%=request.getContextPath()%>/wrap/fg/image/about-zz-fmzl.jpg"/>
										    			</p>
										    			<p>
										    				<img src="<%=request.getContextPath()%>/wrap/fg/image/about-zz-zlzs.jpg"/>
										    			</p>
										    			<p>
										    				<img src="<%=request.getContextPath()%>/wrap/fg/image/about-zz-kjzs.jpg"/>
										    			</p>
										    			<p>
										    				<img src="<%=request.getContextPath()%>/wrap/fg/image/about-zz-swdj.jpg"/>
										    			</p>
										    		</div>
										    	</div>
										    </div>
										    
										    
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>合作客户</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				西安市公共交通总公司
										    			</p>
										    			<p>
										    				西安城市交通投资集团客运公司
										    			</p>
										    			<p>
										    				西安公交巴士股份有限公司
										    			</p>
										    			<p>
										    				西安市长安公交有限责任公司
										    			</p>
										    			<p>西安西高公交有限公司</p>
										    			<p>西安公交万里实业有限公司</p>
										    			<p>西安通成客运有限责任公司</p>
										    			<p>宝鸡市公共交通总公司</p>
										    			<p>西宁市公共交通总公司</p>
										    			<p>成都市公共交通总公司</p>
										    			<p>银川市公共交通总公司</p>
										    			<p>乌海市公共交通总公司</p>
										    			<p>陕西通汇天然气股份有限公司</p>
										    			<p>陕汽通力专用汽车股份有限公司</p>
										    			<p>陕西欧舒特汽车股份有限公司</p>
										    			<p>中集（陕汽）专用汽车有限公司</p>
										    			<p>秦星专用汽车有限责任公司</p>
										    			<p>陕西重曼卡专用汽车有限公司</p>
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