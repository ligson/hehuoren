<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<html>
	<head>
		<meta charset="utf-8">
		<title>用户登录 - 爱尔特合伙人</title>
		

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
		<script type="text/javascript">
$(document).keypress(function(e) {  
    // 回车键事件  
    if(e.which == 13) { 
    	submitLogin();
    }  
}); 

function submitLogin(){
	$("#loginForm").submit();
}

</script>
	</head>
	<body>
		
		<jsp:include page="../../myFrameU/exception/refererException.jsp"></jsp:include>
		
		
		
		
		
		
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="include/rightMenu.jsp"></jsp:include>
			
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
								<h2>用户登录</h2>
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
									
										
									
									
									
									<div class="userLogin">
										<form action="<%=request.getContextPath()%>/wrap/userLogin" method="post" id="loginForm" class="mui-input-group">
											<div class="mui-input-row">
												<label>手机号</label>
												<input name="name" id='account' type="text" class="mui-input-clear mui-input" placeholder="请输入账号">
											</div>
											<div class="mui-input-row">
												<label>密码</label>
												<input name="password" id='password' type="password" class="mui-input-clear mui-input" placeholder="请输入密码">
											</div>
										</form>
										<div class="mui-content-padded">
											<button id='reg' class="mui-btn mui-btn-block mui-btn-primary" onclick="submitLogin();">登录</button>
											<div class="link-area">
												<a id='reg' href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx223227fe730659e3&redirect_uri=http://www.yicangjia.com/wrap/toUserRegist&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect">注册账号</a> <span class="spliter">|</span> <a id='forgetPassword' href="<%=request.getContextPath()%>/wrap/fg/forgetPwd.jsp">忘记密码</a>
											</div>
										</div>
									</div>
									
									
									
									<jsp:include page="include/footer.jsp"></jsp:include>
										
										
								</div>
						
							</div>
						</div>
						
						
					</div>
				</div>
				<!-- off-canvas backdrop -->
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
		
	</body>
	<script type="text/javascript">
	initLeftRight();
	</script>
</html>