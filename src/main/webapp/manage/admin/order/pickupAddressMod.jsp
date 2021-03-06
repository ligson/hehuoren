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




<title>修改自提点  - 爱尔特合伙人后台</title>
<style type="text/css">
.formBox .item .l{width:100px;}
.addSpecialR,.addSpecialDQ{background:#eee;padding:10px;width:60%}
.addSpecialDQ{width:40%}
.addSpecialUl li{display:block;width:100%;height:30px;line-height: 30px;margin-bottom:10px;}
.addSpecialUl li .k,.addSpecialUl li .v{float:left;height:100%;line-height: 30px;}
.addSpecialUl li .k{width:100px;}
.addSpecialUl li .v label{padding-right:5px;}
#previewDateInput,#beginDateInput,#endDateInput{width:100px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
});
function verPassword(i){
	var val=$(i).val();
	if(null!=val&&val!=""){
		$.ajax( {
			type : "POST",
			url : allWeb+"shopVerPassword?shopUserPassword="+val,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
				}else{
					
				}
			}
		})
	}else{
		myAlert(200,130,"失败!","请填写密码","error");
	}
}
function verName(i){
	var val=$(i).val();
	if(null!=val&&val!=""){
		$.ajax( {
			type : "POST",
			url : allWeb+"userVerUserName?name="+val,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
				}else{
					
				}
			}
		})
	}else{
		myAlert(200,130,"失败!","请填写登录账号","error");
	}
}
</script>
</head>
<body>
<jsp:include page="../../../myFrameU/exception/refererException.jsp"></jsp:include>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="shop-shop" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r" style="height:1300px">
				<div id="bodyR_head">
					<h2>自提点管理->修改自提点</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab1.jsp">
							<jsp:param value="myTabLiA2" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<div class="centerBox">
							<div class="formBox">
								<form action="<%=request.getContextPath()%>/admin/pua/modify" method="post">
									<input type="hidden" value="redirect:/admin/pua/finds" name="redirect"/>
									<input type="hidden" value="${requestScope.pickUpAddress.id}" name="id"/>
									<div class="item" style="display:none">
										<div class="l"> 类型： </div>
										<div class="r">
											<span>
												<c:if test='${requestScope.pickUpAddress.tpName=="q"}'>
												<select name="tpName">
													<option value="q">轻汽油</option>
													<option value="z">重汽油</option>
												</select>
												</c:if>
												<c:if test='${requestScope.pickUpAddress.tpName=="z"}'>
												<select name="tpName">
													<option value="q">轻汽油</option>
													<option value="z" selected="selected">重汽油</option>
												</select>
												</c:if>
											</span>
										</div>
									</div>
									<div class="item">
										<div class="l"> 编码： </div>
										<div class="r">
											<span>
												<input name="markedNum" type="text" value="${requestScope.pickUpAddress.markedNum}" class="textInput" />
											</span>
										</div>
									</div>
									<div class="item">
										<div class="l"> 名称： </div>
										<div class="r">
											<span>
												<input name="name" type="text" value="${requestScope.pickUpAddress.name}" class="textInput" />
											</span>
										</div>
									</div>
									
									
									<div class="item">
										<div class="l"> 电话： </div>
										<div class="r">
											<span>
												<input name="telPhone" type="text" value="${requestScope.pickUpAddress.telPhone}" class="textInput" />
											</span>
										</div>
									</div>
									
									<div class="item">
										<div class="l">地区：</div>
										<div class="r">
											<jsp:include page="../../../myFrameU/address/addressListSelect.jsp">
												<jsp:param value="city" name="end"/>
											</jsp:include>
										</div>
									</div>
									<div class="item">
										<div class="l"> 具体地址： </div>
										<div class="r">
											<span>
												<input name="addressStr" type="text" value="${requestScope.pickUpAddress.addressStr}" class="textInput" />
											</span>
										</div>
									</div>
									
									<div class="item" style="clear:both;">
										<div class="l" style="width:95px;height:30px;"> </div>
										<div class="r">
											<input type="submit" value="提交" class="btn"/>
										</div>
									</div>
								</form>
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
