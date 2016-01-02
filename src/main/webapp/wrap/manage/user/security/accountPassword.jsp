<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<html>
	<head>
		<meta charset="utf-8">
		<title>${sessionScope.myUser.nicheng} - 用户中心 - 爱尔特合伙人</title>
		

		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- load MUI -->
    	<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css" rel="stylesheet" type="text/css" />
    	<link href="<%=request.getContextPath()%>/wrap/manage/css/main.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/manage/js/main.js"></script>
    	<script src="<%=request.getContextPath()%>/wrap/fg/js/mymui.alert.js"></script>
    	
    	<script type="text/javascript" charset="utf-8">
    		$(document).ready(function(){
    		});
    		
    		function modifyAccoutPwd(){
    			var yzm = $("#shuruyanzhengmaInput").val();
    			var pwd = $("#passwordInput").val();
    			var pwd1=$("#passwordInput1").val();
    			if(null!=pwd && pwd!=""){
    				if(null!=pwd1 && pwd1!=""){
    					if(pwd1!=pwd){
    						$.ajax( {
    							type : "POST",
    							url : allWeb+"/user/security/accountpassword?withdrawalsPwd="+pwd+"&smsYanzhengma="+yzm+"&withdrawalsPwdOld="+pwd1,
    							success : function(msg) {
    								if(msg!=null && msg!=""){
    									mui.toast(msg);
    								}else{
    									mui.toast("修改成功");
    								}
    							}
    						})
    					}else{
    						mui.toast("老密码和新密码相同！");
    					}
    				}else{
    					mui.toast("请输入老密码");
    				}
    			}else{
    				mui.toast("请输入新密码");
    			}
    		}
		</script>
	</head>
	<body>
		
		
		
		
		<!--侧滑菜单容器-->
		<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
			<!--菜单部分-->
			<jsp:include page="../../../fg/include/rightMenu.jsp"></jsp:include>
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
										<h2>修改财务密码</h2>
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
									
									<div class="userLogin mui-input-group">
											
												<div class="mui-input-row">
													<label>手机号</label>
													<input  type="text" class="mui-input-clear mui-input"   id="sendTelPhoneInput" value="${requestScope.myUser.telPhone}" readonly="readonly"/>
												</div>
												<div class="mui-input-row  yzmBox inputBox">
													<label>验证码</label>
													<input timerId="yzmA"  role="user" onblur="verYZM(this)" tip="phoneTip" type="text" class="mui-input-clear mui-input" placeholder="请输入验证码" id="shuruyanzhengmaInput"/>
													<div  id="yzmA" class="mui-btn mui-btn-primary sendSMS"  onClick="sendYZM(this)" role="user" sdkMtType="modifyZhipassword">获取</div>
													<em class="formTip" id="phoneTip"></em>
												</div>
												<div class="mui-input-row">
													<label>原密码</label>
													<input  type="text" class="mui-input-clear mui-input"   id="passwordInput1" />
												</div>
												<div class="mui-input-row">
													<label>新密码</label>
													<input  type="text" class="mui-input-clear mui-input"   id="passwordInput" />
												</div>
											<div class="mui-content-padded">
												<button id='login' class="mui-btn mui-btn-block mui-btn-primary" onClick="modifyAccoutPwd()">修改</button>
											</div>
											<div class="mui-content-padded oauth-area">
								
											</div>
										</div>
										
									
									
									<jsp:include page="../../../fg/include/footer.jsp"></jsp:include>
										
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
	<script type="text/javascript">
	initLeftRight();
	</script>
</html>