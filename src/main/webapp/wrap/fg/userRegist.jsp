<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<html>
	<head>
		<meta charset="utf-8">
		<title>用户注册 - 爱尔特合伙人</title>
		

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
    		function regist(){
    			var wxId=$("#wxIdInput").val();
    			var touxiang = $("#touxiangInput").val();
    			var name=$("#sendTelPhoneInput").val();
    			if(null!=name && name!="" && name.length==11){
    				var yzm=$("#shuruyanzhengmaInput").val();
    				if(null!=yzm && yzm!=""){
    					var pwd = $("#passwordInput").val();
    					if(null!=pwd && pwd!=""){
    						var pwdL = pwd.length;
    						if(pwdL<6){
    							mui.toast("密码必须大于等于6位");
    						}else{
    							var nicheng = $("#nichengInput").val();
    							if(null!=nicheng && nicheng!=""){
    								$.ajax( {
    			    					type : "POST",
    			    					url : allWeb+"wrap/userRegist?name="+name+"&smsYanzhengma="+yzm+"&password="+pwd+"&nicheng="+nicheng+"&wxId="+wxId+"&touxiang="+touxiang,
    			    					success : function(msg) {
    			    						if(msg!=null && msg!=""){
    			    							mui.toast(msg);
    			    						}else{
    			    							window.location.href=allWeb+"wrap/user/index";
    			    						}
    			    					}
    			    				})
    							}else{
    								mui.toast("请填写昵称");
    							}
    						}
    					}else{
    						mui.toast("请填写密码");
    					}
    				}else{
    					mui.toast("请填写验证码");
    				}
    			}else{
    				mui.toast("请填写正确的手机号");
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
								<h2>用户注册</h2>
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
									
										
										<input type="hidden" value="shop" id="roleInputId"/>
										<div class="userLogin mui-input-group">
												<input type="hidden" value="${requestScope.wxUser.openid}" id="wxIdInput"/>
												<input type="hidden" value="${requestScope.wxUser.openid}.jpg" id="touxiangInput"/>
												<div class="mui-input-row">
													<label>手机号</label>
													<input  type="text" class="mui-input-clear mui-input" placeholder="请输入账号"  id="sendTelPhoneInput" onblur="verTelPhone(this)">
												</div>
												<div class="mui-input-row  yzmBox inputBox">
													<label>验证码</label>
													<input timerId="yzmA"  role="user" onblur="verYZM(this)" tip="phoneTip" type="text" class="mui-input-clear mui-input" placeholder="请输入验证码" id="shuruyanzhengmaInput"/>
													<div  id="yzmA" class="mui-btn mui-btn-primary sendSMS"  onClick="sendYZM(this)" role="user" sdkMtType="regist">获取</div>
													<em class="formTip" id="phoneTip"></em>
												</div>
												<div class="mui-input-row">
													<label>密码</label>
													<input  type="text" class="mui-input-clear mui-input" placeholder="请输入密码"  id="passwordInput" >
												</div>
												<div class="mui-input-row">
													<label>昵称</label>
													<input  type="text" class="mui-input-clear mui-input" placeholder="请输入昵称"  id="nichengInput" value="${requestScope.wxUser.nickname}"/>
												</div>
											
											<div class="mui-content-padded">
												<button id='login' class="mui-btn mui-btn-block mui-btn-primary" onClick="regist()">注册</button>
												<div class="link-area">
													<a id='forgetPassword' href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx223227fe730659e3&redirect_uri=http://www.yicangjia.com/wrap/toUserLogin&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect">已经有账号，进行登录</a>
												</div>
											</div>
											<div class="mui-content-padded oauth-area">
								
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