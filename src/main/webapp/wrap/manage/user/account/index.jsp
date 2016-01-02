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
    			
    			$("#showTXAlert").click(function(){
    				var malert=new myMuiAlert({'height':'270'});
					var data={'url':'wrap/user/account/toApplyTX','callBack':function(myAlert){
						//alert("dddddddddddd");
					}};
					malert.show().setData(data);
				});
    		});
    		
    		function submitApply(){
    			var price=$("#priceSelect").val();
    			//var password=$("#accountpwInputId").val();
    			if(!isNaN(price)){
    				$.ajax( {
    					type : "POST",
    					url : allWeb+"user/apply/addApplySimple?price="+price+"&applyTypeKey=AccountTXApply",
    					success : function(msg) {
    						if(msg!=null && msg!=""){
    							mui.toast(msg);
    						}else{
    							mui.toast("提交提现申请成功，请耐心等待管理员审核");
    							$("#myAlertId").remove();
    							$("#fullScreenGray").remove();
    						}
    					}
    				})
    			}
    		}
    		function toCZ(){
    			window.location.href=allWeb+"wrap/user/account/toRecharge";
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
										<h2>用户中心</h2>
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
									
									<div class="userInfoBox accountInfoBox">
										<div class="top">
										</div>
										<div class="bot">
											<ul>
												<li>
													<p>剩余总额</p>
													<p>${requestScope.account.totalPrice}</p>
												</li>
												<li>
													<p>冻结金额</p>
													<p>${requestScope.account.frozenPrice}</p>
												</li>
												<li>
													<p>可用余额</p>
													<p>${requestScope.account.availablePrice}</p>
												</li>
												<li style="background:#FFE4E1">
													<p>宣传佣金</p>
													<p>${requestScope.account.xcPrice}</p>
												</li>
												<li style="background:#FFE4E1">
													<p>销售佣金</p>
													<p>${requestScope.account.xsPrice}</p>
												</li>
											</ul>
										</div>
									</div>
									
									
									
									<div class="userIndexNavBox">
											<ul class="mui-table-view">
												<li class="mui-table-view-cell" style="display:none;">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/security/toLookPassword">
														查看财务密码
													</a>
												</li>
												
												<li class="mui-table-view-cell" style="display:none;">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/security/toAccountpassword">
														修改财务密码
													</a>
												</li>
												<li class="mui-table-view-cell">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'INCOME'}]">
														收入明细
													</a>
												</li>
												<li class="mui-table-view-cell">
													<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/user/account/accountItemList?queryArgs=[{'key':'itemType','value':'WITHDRAWALS'}]">
														提现明细
													</a>
												</li>
											</ul>
									</div>
									
									
									<div class="logBox">
										<div class="mui-content-padded">
											<button id='showCZPicker' class="mui-btn mui-btn-block mui-btn-primary" onClick="toCZ()" style="display:none">微信充值</button>
											<button id='showTXAlert' class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger">申请提现</button>
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