<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="utf-8">
<title>${sessionScope.myUser.nicheng}- 用户中心 - 爱尔特合伙人</title>


<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<!-- load MUI -->
<link href="<%=request.getContextPath()%>/wrap/mui/dist/css/mui.min.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/wrap/fg/css/myui.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/wrap/fg/css/main.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/wrap/manage/css/main.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/wrap/fg/js/jquery-1.7.min.js"></script>
<script src="<%=request.getContextPath()%>/wrap/mui/dist/js/mui.min.js"></script>
<script src="<%=request.getContextPath()%>/wrap/fg/js/main.js"></script>
<script src="<%=request.getContextPath()%>/wrap/manage/js/main.js"></script>
<script src="<%=request.getContextPath()%>/wrap/fg/js/mymui.alert.js"></script>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		mui.init({});
		initMaxWidth();

	});
	function recharge(a) {
		var price = $("#priceSelect").val();
		var password = $("#accountpwInputId").val();
		if (!isNaN(price)) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : allWeb + "wrap/user/account/recharge?price=" + price
						+ "&password=" + password + "&outerInterface=weixin",
				success : function(msg) {
					var error = msg.error;
					if (null == error || error == "") {
						var redirect = msg.redirect;
						var outerInterface = msg.outerInterface;
						var pcOrWrap = msg.pcOrWrap;
						//alert("redirect=" + redirect + "==outerInterface="+ outerInterface + "====pcOrWrap=" + pcOrWrap);
						if (outerInterface == "zhifubao") {
							window.open(redirect, "_blank");
						} else if (outerInterface == "weixin") {
							//wxPay(msg);
							window.location.href=redirect;
						}

					} else {
						alert(msg.errorMessage);
					}
				}
			})
		} else {

		}
	}

	/* function wxPay(jo) {
		var wxId = jo.wxId;
		var out_trade_no = jo.out_trade_no;
		var payType = jo.payType;
		var total_fee = jo.total_fee;
		var body = jo.body;
		if (null != wxId && null != out_trade_no && null != payType
				&& null != total_fee) {
			$.ajax({
						type : "POST",
						dataType : "json",
						url : allWeb + "weixin/pay/test?wxId=" + wxId
								+ "&out_trade_no=" + out_trade_no + "&payType="
								+ payType + "&total_fee=" + total_fee
								+ "&body=" + body,
						success : function(msg) {
							$("#wxInputId_appId").val(msg.appId);
							$("#wxInputId_timeStamp").val(msg.timeStamp);
							$("#wxInputId_nonceStr").val(msg.nonceStr);
							$("#wxInputId_package").val(msg.wpackage);
							$("#wxInputId_paySign").val(msg.paySign);
							alert("11111111111111111"+msg);
							startWXJSAPIPay();
						}
					})
		}
	}
 */
</script>

</head>
<body>






	<!--侧滑菜单容器-->
	<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
		<!--菜单部分-->
		<jsp:include page="../../../fg/include/rightMenu.jsp"></jsp:include>
		<div class="my-mui-inner-wrap">

			<div id="offCanvasContentScroll"
				class="mui-content mui-scroll-wrapper ">
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
									<h2>充值</h2>
								</div>
								<div id="myHead_r">
									<div class="mui-content-padded">
										<div class="flex-container">
											<a class="myHeadA bkred" id="offCanvasShow"><span
												class="mui-icon mui-icon-list"></span></a>
										</div>
									</div>
								</div>
							</div>

							<div class="mysuojinDiv">
								<div class="qykBox">
									<div class="qykHead">
										<h3>微信充值</h3>
									</div>
									<div class="qykBody">
										<div class="qykBodyI">
											<ul class="sinputBox">
												<li><span class="k">金额：</span> <span class="v"><input
														type="text" class="txtInput" onblur="mustNumber(this)"
														id="priceSelect" /></span></li>
												<li><span class="k">密码：</span> <span class="v"><input
														type="password" class="txtInput" id="accountpwInputId" /></span>
												</li>
												<li><input type="button" value="确认充值" class="button"
													style="" onClick="recharge()" id="submitApplyButton" /></li>
											</ul>
										</div>
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

</html>