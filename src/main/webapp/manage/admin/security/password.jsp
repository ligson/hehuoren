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

<title>密码重置 - 爱尔特合伙人后台</title>
<style type="text/css">
.l{width:100px;}
</style>
<script type="text/javascript">
function modifyPassword(){
	var pwd = $("#passwordInput").val();
	var oldPwd=$("#oldpasswordInput").val();
	if(null!=pwd && pwd!=""){
		if(null!=oldPwd && oldPwd!=""){
			if(oldPwd!=pwd){
				$.ajax( {
					type : "POST",
					url : allWeb+"/admin/security/password?password="+pwd+"&oldPassword="+oldPwd,
					success : function(msg) {
						if(msg!=null && msg!=""){
							myAlert(200,130,"失败!",msg,"error");
						}else{
							myAlert(200,130,"成功!","修改成功","ok");
						}
					}
				})
			}else{
				alert("新老密码相同");
			}
		}else{
			alert("请输入老密码");
		}
	}else{
		alert("请输入新密码");
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
					<h2>账号安全--密码重置</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA1" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					
					<div id="mainContent">
						
						
						
						
						
						<div class="centerBox">
							<p class="tip"></p>
							<div class="formBox formBox1">
									<div class="item">
										<div class="l"> 老密码： </div>
										<div class="r">
											<span >
												<input name="phone" type="text" value="" class="textInput " id="oldpasswordInput"/>
												<em class="formTip"></em>
											</span>
										</div>
									</div>
									<div class="item">
										<div class="l"> 新密码： </div>
										<div class="r">
											<span >
												<input name="phone" type="text" value="" class="textInput " id="passwordInput"/>
												<em class="formTip"></em>
											</span>
										</div>
									</div>
									<div class="item">
										<div class="l"> 重新输入： </div>
										<div class="r">
											<span >
												<input name="phone" type="text"  value="" class="textInput " id="passwordInput1"/>
												<em class="formTip"></em>
											</span>
										</div>
									</div>
									
									
									<div class="item" style="clear:both;">
										<div class="l" style="width:95px;height:30px;"> </div>
										<div class="r">
											<input type="button" value="提交修改" class="btn" onClick="modifyPassword()"/>
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
