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
								<h2>财务帮助</h2>
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
										    		<h3>系统财务系统概要</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<ul>
									<li style="height:auto">1）系统内有自己的财务系统，只需要在充值与提现时候与外界进行通信（如充值、提现），其余的业务逻辑均使用系统内财务</li>
									<li style="height:auto">2）本系统与外界通信的方案选择微信支付、支付宝</li>
									<li style="height:auto">3）提现需要提出申请，待管理员审核通过，需管理员人工进行转账。</li>
								</ul>
										    		</div>
										    	</div>
										    </div>
										    <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>使用财务系统前需要完善的资料</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<ul>
									<li>1）用户需绑定手机号，因为只有绑定手机号才能查看系统默认为您生成的财务密码</li>
									<li style="height:auto">2）完善财务密码，将系统默认给您生成的财务密码修改为自己能识别记忆的密码，并妥善保管</li>
								</ul>
										    		</div>
										    	</div>
										    </div>
										    
										    
										      <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>财务账户内的总额、可用余额、冻结金额</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<ul>
									<li>1）财务账户内有总额、可用余额、冻结金额三者基本信息。总额=冻结资金+可用余额</li>
									<li style="height:auto">2）什么是冻结资金？保证金的金额形式。如：用户申请提现需要冻结相应资金。</li>
								</ul>
										    		</div>
										    	</div>
										    </div>
										    
										    
										    
										    
										     <div class="qykBox">
										    	<div class="qykHead">
										    		<h3>账户提现申请</h3>
										    	</div>
										    	<div class="qykBody">
										    		<div class="qykBodyI">
										    			<ul>
									<li>1) 用户提现需要提出申请,提交申请之后，程序会自动冻结所欲提现的资金，待管理员审核</li>
									<li>2) 用户每个月可成功申请提现<b class="numB">${g8.myValue}</b>次。</li>
									<li>3) 管理员在审核通过之后，务必遵守信约将所提现资金打到用户支付宝账号/微信账号中</li>
									<li>4) 每次申请提现最少提现M元（M等于销售佣金+可提现的宣传佣金）</li>
									<li>5) 管理员审核不通过之后，会将所冻结的资金在本平台财务系统中解冻</li>
								</ul>
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