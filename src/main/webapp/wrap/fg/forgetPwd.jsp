<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<html>
	<head>
		<meta charset="utf-8">
		<title>找回密码 - 爱尔特合伙人</title>
		

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
    		function verTelPhone(i){
    			var phone=$(i).val();
    			if(null!=phone && phone!=""){
    				$.ajax( {
    					type : "POST",
    					url : allWeb+"verify/geshi?geshi=TELPHONE&value="+phone,
    					success : function(msg) {
    						if(msg!=null && msg!=""){
    							mui.toast(msg);
    							$(i).attr("result","error");
    						}else{
    							$(i).attr("result","ok");
    						}
    					}
    				})
    			}else{
    				mui.toast("请填写手机号");
    			}
    		}
    		
    		function getPwd(){
    			var yzm=$("#shuruyanzhengmaInput").val();
    			var pTip=$("#phoneTip").html();
    			if(pTip=="验证码正确"){
    				var telPhone=$("#sendTelPhoneInput").val();
    				var smsYanzhengma=yzm;
    				var role=$("#roleInputId").val();
    				if(null!=yzm && yzm!="" && telPhone!=null && telPhone!=""){
    					$.ajax( {
    						type : "POST",
    						url : allWeb+"getPwd_forgetPwd?telPhone="+telPhone+"&role="+role+"&smsYanzhengma="+smsYanzhengma,
    						success : function(msg) {
    							if(msg.indexOf("ok-")>=0){
    								msg=msg.replace("ok-","");
    								//myAlert(200,130,"成功!",msg,"ok");	
    								//mui.toast(msg);
    								alert(msg);
    							}else{
    								mui.toast(msg);
    							}
    						}
    					})
    				}
    				//window.location.href=allWeb+"userRegist_step_2?smsYanzhengma="+yzm;
    			}
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
								<h2>找回密码</h2>
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
									
										<%
	String referer = request.getHeader("referer");
	request.setAttribute("referer", referer);
%>
									
									
									<c:if test='${fn:contains(requestScope.referer,"toShopLogin")}'>
										<input type="hidden" value="shop" id="roleInputId"/>
										<div class="userLogin">
											<form id='login-form' class="mui-input-group">
												<div class="mui-input-row">
													<label>手机号</label>
													<input  type="text" class="mui-input-clear mui-input" placeholder="请输入账号"  id="sendTelPhoneInput" onblur="verTelPhone(this)">
												</div>
												<div class="mui-input-row  yzmBox inputBox">
													<label>验证码</label>
													<input timerId="yzmA"  role="shop" onblur="verYZM(this)" tip="phoneTip" type="text" class="mui-input-clear mui-input" placeholder="请输入验证码" id="shuruyanzhengmaInput"/>
													<div  id="yzmA" class="mui-btn mui-btn-primary sendSMS"  onClick="sendYZM(this)" role="shop" sdkMtType="forgetPassword">获取</div>
													<em class="formTip" id="phoneTip"></em>
												</div>
											</form>
											
											<div class="mui-content-padded">
												<button id='login' class="mui-btn mui-btn-block mui-btn-primary" onClick="getPwd()">找回</button>
												<div class="link-area">
													<a id='reg' href="<%=request.getContextPath()%>/wrap/fg/userRegist.jsp">注册账号</a> <span class="spliter">|</span> <a id='forgetPassword' href="<%=request.getContextPath()%>/wrap/toShopLogin">登录</a>
												</div>
											</div>
											<div class="mui-content-padded oauth-area">
								
											</div>
										</div>
									</c:if>
									<c:if test='${fn:contains(requestScope.referer,"toUserLogin")}'>
										<input type="hidden" value="user" id="roleInputId"/>
										<div class="userLogin">
											<form id='login-form' class="mui-input-group">
												<div class="mui-input-row">
													<label>手机号</label>
													<input type="text" class="mui-input-clear mui-input" placeholder="请输入账号" id="sendTelPhoneInput" onblur="verTelPhone(this)">
												</div>
												<div class="mui-input-row yzmBox inputBox">
													<label>验证码</label>
													<input timerId="yzmA"  role="shop" onblur="verYZM(this)" tip="phoneTip" type="text" class="mui-input-clear mui-input" placeholder="请输入验证码" id="shuruyanzhengmaInput"/>
													<div  id="yzmA" class="mui-btn mui-btn-primary sendSMS"  onClick="sendYZM(this)" role="shop" sdkMtType="forgetPassword">获取</div>
													<em class="formTip" id="phoneTip"></em>
												</div>
											</form>
											
											<div class="mui-content-padded">
												<button id='login' class="mui-btn mui-btn-block mui-btn-primary" onClick="getPwd()">找回</button>
												<div class="link-area">
													<a id='reg' href="<%=request.getContextPath()%>/wrap/fg/userRegist.jsp">注册账号</a> <span class="spliter">|</span> <a id='forgetPassword' href="<%=request.getContextPath()%>/wrap/toUserLogin">登录</a>
												</div>
											</div>
											<div class="mui-content-padded oauth-area">
								
											</div>
										</div>
									</c:if>
									
									
									
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