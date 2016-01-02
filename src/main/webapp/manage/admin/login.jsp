<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>管理员登录 - 爱尔特合伙人后台管理平台</title>
<link href="<%=request.getContextPath()%>/manage/css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<script type="text/javascript">

$(document).keypress(function(e) {  
    // 回车键事件  
    if(e.which == 13) { 
    	submitLogin();
    }  
}); 
   
function submitLogin(){
	$("#loginForm").submit();
	var isOk=$("#yanzhengImg").attr("rel");
	if(isOk=="ok"){
		$("#loginForm").submit();
	}else if(isOk=="error"){
		alert("验证码错误");
	}
}

</script>
</head>
<body>
<jsp:include page="../../myFrameU/exception/refererException.jsp"></jsp:include>
<div class="outer">
	<div class="formDiv">
	<form action="<%=request.getContextPath()%>/adminLogin" method="post" id="loginForm">
		<div class="lg_form">
			<div class="lg_name"><input type="text" value="" class="name" name="name"/></div>
			<div class="lg_pass"><input type="password" value="" class="password" name="password"/></div>
			<div class="lg_but"><a href="javascript:void(0);" class="sbA"><input type="button" value="" class="sb" onclick="submitLogin();"/></a></div>
		</div>
		
	</form>	
	<div id="alertYzmDiv">
	</div>
	</div>
	
</div>

</body>
</html>
