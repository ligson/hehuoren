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
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/ui.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<title>查看财务密码  -爱尔特合伙人后台</title>
<style type="text/css">
.l{width:100px;}
</style>
<script type="text/javascript">
function lookAccountPwd(){
	var yzm = $("#shuruyanzhengmaInput").val();
	if(null!=yzm && yzm!=""){
		$.ajax( {
			type : "POST",
			url : allWeb+"/admin/security/lookPassword?smsYanzhengma="+yzm,
			success : function(msg) {
				myAlert(200,130,"查看财务密码",msg,"ok");
			}
		})
	}else{
		alert("请输入验证码");
	}
}
</script>
</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="admin-security" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>账号安全--查看财务密码</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA5" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						
						
						
						
						
						<div class="centerBox">
							<p class="tip"></p>
							<div class="formBox formBox1">
									<div class="item">
										<div class="l"> 绑定手机： </div>
										<div class="r">
											<span>
												<input name="phone" type="text" readonly="readonly" value="${requestScope.myAdmin.telPhone}" class="textInput readonly" id="sendTelPhoneInput"/>
												<em class="formTip"><a href="<%=request.getContextPath()%>/admin/security/toTelphone">重新绑定</a></em>
											</span>
										</div>
									</div>
									
									
									<div class="item">
										<div class="l"> 验证码： </div>
										<div class="r">
											<span class="yzmBox">
												<input timerId="yzmA" role="admin" onblur="verYZM(this)" tip="phoneTip" name="smallName" type="text" value="" class="textInput" id="shuruyanzhengmaInput"/>
												<a  id="yzmA" href="javascript:void(0);" class="huoquyzmA" onClick="sendYZM(this)" role="admin" sdkMtType="lookAccountPwd">获取验证码</a>
												<em class="formTip" id="phoneTip"></em>
											</span>
										</div>
									</div>
									
									<div class="item" style="clear:both;">
										<div class="l" style="width:95px;height:30px;"> </div>
										<div class="r">
											<input type="button" value="查看密码" class="btn" onClick="lookAccountPwd()"/>
										</div>
									</div>
							</div>
						</div>
						
						
						
						
						
						
						
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
