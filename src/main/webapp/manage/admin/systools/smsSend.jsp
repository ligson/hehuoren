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
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<title>发送短信 - 系统工具 - 艺术拍拍管理后台</title>
<style type="text/css">
#ptRight{float:left;width:750px;border:1px solid #e7e7eb;min-height:450px;height:auto;margin-top:10px;margin-left:100px;}
#addPTBox table td{padding:5px;}
#accountBox{width:500px;height:auto;background:#eee;border:1px solid #ddd;}
#accountB_s,#accountB_list{width:90%;margin:0 auto;padding:10px;}
#accountB_s{height:30px;}
#accountB_list{border-top:1px solid #ddd;}
#accountB_list table tbody tr td{background:#fff;}
.txtInput{width:100%;height:30px;color:#666;font-weight: bold;}
#operPriceBox{width:480px;padding:10px;padding-left:0px;}
#operPriceBox_l,#operPriceBox_r{float:left;}
#operPriceBox_r{line-height: 30px;padding-left:10px;}
.selectedA{background:#FF8C00 !important; color:#fff;}
</style>
<script type="text/javascript">
function searchRole(i){
	var val=$(i).val();
	if(val!="" && val!=null && val!=undefined && val!="undefined"){
		$.ajax( {
			type : "POST",
			url : allWeb+"admin/likRoles?whoName="+val,
			success : function(msg) {
				$("#accountB_list").html(msg);
			}
		})
	}
}

function submitOper(){
	var telPhone=$("#roleTelPhoneInput").val();
	var content=$("#contentTextId").val();
	if(telPhone!="" && content!="" && telPhone!=null && content!=null){
		var len=content.length;
		if(len<=65){
			if(telPhone!="undefined" && content!="undefined"){
				$.ajax( {
					type : "POST",
					url : allWeb+"admin/sms/sendSMS?telPhones="+telPhone+"&content="+content,
					success : function(msg) {
						if(msg!=null && msg!=""){
							myAlert(200,130,"失败!",msg,"error");
						}else{
							myAlert(200,130,"成功!","发送成功","ok");
						}
					}
				})
			}
		}else{
			alert("字数不能大于65");
		}
	}else{
		alert("请输入内容,并且选择发送对象");
	}
}
function selectRole(a){
	var telPhone=$(a).attr("telPhone");
	$("#roleTelPhoneInput").val(telPhone);
	$("#accountB_list").find(".everyAccount").find("td").removeClass("selectedA");
	$("#everyAccount_id_"+telPhone).find("td").addClass("selectedA");
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
					<jsp:param value="sys-tools" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
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
						
						<div class="quyukuang" id="ptRight">
							<div class="title">
						        <h3>人工发送短信</h3>
						    </div>
						    <div class="content">
						    	<div class="contentI">
						    		<div id="addPTBox">
						    			<table>
						    				<tbody>
						    					<tr>
						    						<td colspan="2">
						    							<p class="tip"><i class="tip"></i>
						    							选择发送对象，填写发送内容，发送内容不超过65个字。
						    							</p>
						    						</td>
						    					</tr>
						    					
						    					<tr>
						    						<td>选择对象：</td>
						    						<td>
						    							<input type="hidden" value="" id="roleTelPhoneInput"/>
						    							<div id="accountBox">
						    								<div id="accountB_s">
						    									<input type="text" class="txtInput" onblur="searchRole(this)" value=""/>
						    								</div>
						    								<div id="accountB_list">
						    									
						    								</div>
						    							</div>
						    						</td>
						    					</tr>
						    					
						    					<tr>
						    						<td>发送内容：
						    							<br/>
						    							(最多不超过65个字符)
						    						</td>
						    						<td>
						    							<div id="operPriceBox">
						    								<div id="operPriceBox_l">
						    									<textarea style="width:500px;height:100px;" id="contentTextId"></textarea>
						    								</div>
						    								<div id="operPriceBox_r">
						    								</div>
						    							</div>
						    						</td>
						    					</tr>
						    					<tr>
						    						<td></td>
						    						<td>
						    							<a href="javascript:void(0);" class="btn"  onClick="submitOper()">发送</a>
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
