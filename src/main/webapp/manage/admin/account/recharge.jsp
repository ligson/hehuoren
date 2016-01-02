<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
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

<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery.selectui.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>

<title>充值 - 爱尔特合伙人后台</title>
<style type="text/css">
#ptRight{float:left;width:750px;border:1px solid #e7e7eb;height:350px;margin-top:10px;margin-left:100px;}
#addPTBox table td{padding:5px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$("select").selectui({
		// 是否自动计算宽度
		autoWidth: true,
		// 是否启用定时器刷新文本和宽度
		interval: true
	});
});

function recharge(a){
	var price=$("#priceSelect").val();
	//var password = $("#accountpwInputId").val();
	/* if(null!=password && password!=""){
		
	}else{
		alert("请输入财务密码");
	} */
	if(!isNaN(price)){
		$.ajax( {
			type : "POST",
			url : allWeb+"admin/account/recharge?price="+price,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
				}else{
					myAlert(200,130,"成功!","财务充值成功","ok");
				}
			}
		})
	}else{
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
					<jsp:param value="web-account" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>财务管理</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab_my.jsp">
							<jsp:param value="myTabLiA3" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						
						<div class="quyukuang" id="ptRight">
							<div class="title">
						        <h3>管理员直接操作自己账号充值</h3>
						    </div>
						    <div class="content">
						    	<div class="contentI">
						    		<div id="addPTBox">
						    			<table>
						    				<tbody>
						    					<tr>
						    						<td colspan="2">
						    							<p class="tip"><i class="tip"></i>
						    							请填写充值金额，点击充值
						    							</p>
						    						</td>
						    					</tr>
						    					<tr>
						    						<td>充值金额：</td>
						    						<td>
						    							<span class="select_ui ">
						    							<b class="select_arrow"></b>
						    							<select class="mySelect" id="priceSelect" name="price">
						    								<option value="">选择充值金额</option>
						    								<option value="100">100</option>
						    								<option value="200">200</option>
						    								<option value="300">300</option>
						    								<option value="500">500</option>
						    								<option value="800">800</option>
						    								<option value="1000">1000</option>
						    								<option value="1500">1500</option>
						    								<option value="2000">2000</option>
						    								<option value="2500">2500</option>
						    								<option value="3000">3000</option>
						    								<option value="4000">4000</option>
						    								<option value="5000">5000</option>
						    								<option value="10000">10000</option>
						    								<option value="20000">20000</option>
						    							</select>
						    							</span>
						    						</td>
						    					</tr>
						    					<tr style="display:none">
						    						<td style="width:100px;">财务密码：</td>
						    						<td>
						    							<input id="accountpwInputId" name="phone" type="text" value="" class="textInput"/>
						    						</td>
						    					</tr>
						    					<tr>
						    						<td></td>
						    						<td>
						    							<input type="button" value="提交" class="btn" onClick="recharge()"/>
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
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
