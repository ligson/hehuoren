<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/ui.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<style type="text/css">
.bigTrTd{background:#fff !important;}
.smallLastTr td{border-bottom:none !important;}
</style>
<title>短信发送规则 - 系统工具 - 艺术拍拍管理后台</title>
<script type="text/javascript">
<!--
function mydownload(a){
	var id=$(a).attr("dataId");
	$.ajax( {
		type : "POST",
		url : allWeb+"/admin/dataBackup/down?id="+id,
		success : function(msg) {
			alert(msg);
		}
	})
}
-->

</script>
</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="sys-tools" name="leftMenu"/>
				</jsp:include>	
			</div>
			<div id="layout_bodyI_r" style="height:1500px">
				<div id="bodyR_head">
					<h2>系统工具</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA4" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<div style="width:99%;height:40px;padding-top:10px;">
							<a href="<%=request.getContextPath()%>/admin/sms/toSend" class="aButton btn"><i class="add"></i>去发送短信</a>
						</div>
						<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>模块</td>
										<td>发送</td>
									</tr>
								</thead>
								<tbody>
									<tr >
										<td class="bigTrTd">用户模块</td>
										<td class="bigTrTd">
											<table cellpadding="0" cellspacing="0">
												<thead>
													<tr>
														<td>发送时机</td>
														<td>发送内容</td>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>注册</td>
														<td>尊敬的客户，您注册的验证码为xxxxxx,，请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
													<tr>
														<td>修改密码</td>
														<td>您忘记密码,修改成新密码时的验证码是xxxxx,请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
													<tr>
														<td>绑定手机</td>
														<td>尊敬的客户，您绑定手机的验证码为xxxx，请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
													<tr>
														<td>专场开始通知</td>
														<td>专场[....]开始了，您可前往竞拍!</td>
													</tr>
													<tr>
														<td>专场结束通知</td>
														<td>专场[....]马上结束了，您抓紧时间前去往竞拍!</td>
													</tr>
													<tr>
														<td>拍品开始通知</td>
														<td>拍品[....]开始拍卖了，您可前往竞拍!</td>
													</tr>
													<tr class="smallLastTr">
														<td>拍品结束通知</td>
														<td>拍品[....]拍卖马上结束了，您可抓紧前往竞拍!</td>
													</tr>
													
												</tbody>
											</table>
										</td>
									</tr>
									<tr >
										<td class="bigTrTd">商铺模块</td>
										<td class="bigTrTd">
											<table cellpadding="0" cellspacing="0">
												<thead>
													<tr>
														<td>发送时机</td>
														<td>发送内容</td>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>注册</td>
														<td>尊敬的客户，您注册的验证码为xxxxxx,，请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
													<tr>
														<td>修改密码</td>
														<td>您忘记密码,修改成新密码时的验证码是xxxxx,请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
													<tr class="smallLastTr">
														<td>绑定手机</td>
														<td>尊敬的客户，您绑定手机的验证码为xxxx，请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									
									
									
									<tr >
										<td class="bigTrTd">财务模块</td>
										<td class="bigTrTd">
											<table cellpadding="0" cellspacing="0">
												<thead>
													<tr>
														<td>发送时机</td>
														<td>发送内容</td>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>充值成功</td>
														<td>您成功充值XXX元</td>
													</tr>
													<tr>
														<td>修改财务密码</td>
														<td>尊敬的客户，您修改财务密码的验证码为xxx，请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
													<tr >
														<td>修改支付宝</td>
														<td>尊敬的客户，您修改支付宝账号的验证码为xxxx,，请于30分钟内输入，切勿将验证码泄露于他人。</td>
													</tr>
													<tr class="smallLastTr">
														<td>提现申请审核通过</td>
														<td>尊敬的客户，您的提现申请已经审核通过，请注意查收您的支付宝账号余额</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									
									
									
									
									<tr >
										<td class="bigTrTd">订单模块</td>
										<td class="bigTrTd">
											<table cellpadding="0" cellspacing="0">
												<thead>
													<tr>
														<td>发送时机</td>
														<td>发送对象</td>
														<td>发送内容</td>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>拍品结束中标</td>
														<td>用户</td>
														<td>您已中标拍品[....],中标价为xx元,请您尽快付款</td>
													</tr>
													<tr class="smallLastTr">
														<td>过N天后中标用户不付款</td>
														<td>商家</td>
														<td>[...]被用户[...]中标，已过付款期未付款,系统在M天将他的保证金付给您</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									
									
									
									
									
									
									
									
									
									
									
									
									
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
