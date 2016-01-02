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
								<h2>合伙人帮助</h2>
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
										    		<h3>爱尔特合伙人模式</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>“爱尔特合伙人”是集团推出的新型电商平台，采用的是消费者共建、消费者推广的新型电商模式，以下简称合伙人平台或者平台。</p>
										    			<p>申请参与建设与推广“爱尔特合伙人”的消费者，我们称之为“爱儿特合伙人”，以下简称“合伙人”</p>
										    			<p>
										    				合伙人将参与平台商城产品的挑选，参与产品迭代，提升产品质量与用户体验；同时，由于平台的推广由合伙人承担，我们节省了广告费等中间环节成本，
										    				因此我们可以给消费者提供更加优惠的价格。
										    			</p>
										    		</div>
										    	</div>
										    </div>
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>爱尔特合伙人特权</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>爱尔特合伙人有三大特权：</p>
										    			<p><span class="b">购买特权：</span>只有合伙人才能有资格在合伙人平台上购买产品，其他普通消费者如果想要享受合伙人的低廉价格，必须要成为某位合伙人的粉丝，才可以在我们平台上以
										    			较低的合伙人价格购买产品。
										    			</p>
										    			<p>
										    				<span class="b">分享特权：</span>
										    				成为合伙人后，可以吸收普通消费者成为自己的粉丝，可以把自己的价格分享给他们使用，让你的粉丝也享受合伙人的价格。
										    			</p>
										    			<p>
										    				<span class="b">佣金特权：</span>
										    				合伙人的佣金有两大方面，一方面当成功吸收了一位粉丝后，平台会奖励${g19.myValue}元宣传佣金，当合伙人的粉丝在平台成功消费一笔之后，
										    				合伙人会根据销售金额获取一定的销售佣金。
										    			</p>
										    		</div>
										    	</div>
										    </div>
										    
										    
										    
										    
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>如何申请合伙人？</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p><span class="b">1、什么是绿色通道？</span></p>
										    			<p>
										    				为了让更多的用户参与平台进行共同营建，系统会定期的开放绿色通道。
										    			</p>
										    			<p>
										    				所谓的绿色通道是指：普通用户（已经注册的）可无需购买本平台产品即可提交成为合伙人申请。
										    			</p>
										    			<p>
										    				当前绿色通道状态为：
										    				<c:if test='${g14.myValue=="1"}'>已经开通</c:if>
										    				<c:if test='${g14.myValue!="1"}'>已经关闭</c:if>
										    			</p>
										    			<p><span class="b">2、如何申请成为合伙人？</span></p>
										    			<p>
										    				如果系统绿色通道已经关闭，则需要用户先在本平台内购买一次 产品，然后才有资格提交成为合伙人申请。
										    			</p>
										    			<p>
										    				如果绿色通道已经打开，则用户可直接申请成为合伙人。
										    			</p>
										    			<c:if test='${!empty sessionScope.myUser}'>
										    				<c:if test='${sessionScope.myUser.userLevelId=="1"}'>
										    					<p>
										    						<a  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" href="<%=request.getContextPath()%>/wrap/user/index">去用户中心申请合伙人</a>
										    					</p>
										    				</c:if>
										    				<c:if test='${sessionScope.myUser.userLevelId=="2"}'>
										    						
										    				</c:if>
										    			</c:if>
										    		</div>
										    	</div>
										    </div>
										    
										    
										    
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>合伙人试用期制度</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				当管理员审核审批成功通过后，该用户就成为了本平台的试用期合伙人，试用期结束后，如果不满足条件，则将降为普通用户。
										    			</p>
										    			<p>
										    				试用期为${g15.myValue}天，在${g15.myValue}内吸纳${g16.myValue}位粉丝以上，则试用期结束变为正式合伙人。
										    			</p>
										    		</div>
										    	</div>
										    </div>
										    
										    
										    
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>合伙人佣金制度</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<p>
										    				1)合伙人的财务账户中存在销售佣金和宣传佣金
										    			</p>
										    			<p>
										    				2)<span class="b">销售佣金：</span>如一个产品网店普通价格为100元，合伙人价格为80元，您的粉丝中有一人购买了该产品，则您会有相应的销售佣金。<br/>
															销售佣金的计算方式是：每个产品的销售佣金提成百分比x粉丝购买的订单金额
										    			</p>
										    			<p>
										    				3)<span class="b">宣传佣金：</span>每增加一位粉丝，您会收到系统给您的${g19.myValue}元作为宣传佣金。
															但是可提现的宣传佣金最多不能超过当前财务账户中销售佣金的${g20.myValue}%
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