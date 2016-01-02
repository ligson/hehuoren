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

<title>操作账户 - 爱尔特合伙人后台</title>
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
function searchAccount(i){
	var val=$(i).val();
	if(val!="" && val!=null && val!=undefined && val!="undefined"){
		$.ajax( {
			type : "POST",
			url : allWeb+"admin/account/likeAccountList?whoName="+val,
			success : function(msg) {
				$("#accountB_list").html(msg);
			}
		})
	}
}

function submitOper(){
	var accountId=$("#accountIdInput").val();
	var operType=$("#operTypeSelect").val();
	var price=$("#operPriceInput").val();
	var accountType=$("#accountTypeInput").val();
	//var password = $("#accountpwInputId").val();
	if(accountId!="" && operType!="" && price!="" && accountId!=null && operType!=null && price!=null){
		if(accountId!="undefined" && operType!="undefined" && price!="undefined"){
			/* if(null!=password && password!=""){
				
			}else{
				alert("请输入财务密码");
			} */
			if(price!="0" && price!="0.0"){
				$.ajax( {
					type : "POST",
					url : allWeb+"admin/account/operAccount?accountId="+accountId+"&operType="+operType+"&price="+price,
					success : function(msg) {
						if(msg!=null && msg!=""){
							myAlert(200,130,"失败!",msg,"error");
						}else{
							myAlert(200,130,"成功!","操作成功","ok");
						}
					}
				})
			}
		}
	}
}
function selectAccount(a){
	var accountId=$(a).attr("accountId");
	$("#accountIdInput").val(accountId);
	$("#accountB_list").find(".everyAccount").find("td").removeClass("selectedA");
	$("#everyAccount_id_"+accountId).find("td").addClass("selectedA");
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
					<jsp:param value="account-account" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>财务管理-人工操作账户</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA2" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						
						<div class="quyukuang" id="ptRight">
							<div class="title">
						        <h3>人工操作账户</h3>
						    </div>
						    <div class="content">
						    	<div class="contentI">
						    		<div id="addPTBox">
						    			<table>
						    				<tbody>
						    					<tr>
						    						<td colspan="2">
						    							<p class="tip"><i class="tip"></i>
						    							请选择操作的账户、操作类型、操作金额等信息
						    							</p>
						    						</td>
						    					</tr>
						    					
						    					<tr>
						    						<td>账户信息：</td>
						    						<td>
						    							<input type="hidden" value="" id="accountIdInput"/>
						    							<div id="accountBox">
						    								<div id="accountB_s">
						    									<input type="text" class="txtInput" onblur="searchAccount(this)" value=""/>
						    								</div>
						    								<div id="accountB_list">
						    									
						    								</div>
						    							</div>
						    						</td>
						    					</tr>
						    					<tr>
						    						<td>操作类型：</td>
						    						<td>
						    							<span>
						    							<select class="mySelect" id="operTypeSelect">
						    								<option value="">选择操作类型</option>
						    								<option value="RECHARGE">充值</option>
						    								<option value="FROZEN">冻结</option>
						    								<option value="SPENDING">支付(支付给平台)</option>
						    								<option value="INCOME">收入(从平台支出)</option>
						    							</select>
						    							</span>
						    						</td>
						    					</tr>
						    					<tr>
						    						<td>操作金额：</td>
						    						<td>
						    							<div id="operPriceBox">
						    								<div id="operPriceBox_l">
						    									<input type="text" value="" id="operPriceInput" style="width:100px;height:30px;font-size:16px;"/>
						    								</div>
						    								<div id="operPriceBox_r">
						    								</div>
						    							</div>
						    						</td>
						    					</tr>
						    					<tr style="display:none;">
						    						<td style="width:100px;">财务密码：</td>
						    						<td>
						    							<input id="accountpwInputId" name="phone" type="text" value="" class="textInput"/>
						    						</td>
						    					</tr>
						    					<tr>
						    						<td></td>
						    						<td>
						    							<a href="javascript:void(0);" class="btn"  onClick="submitOper()">提交操作</a>
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
