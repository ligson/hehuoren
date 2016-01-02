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

<title>诚信管理 - 艺术拍拍管理后台</title>
<style type="text/css">
#ptRight{float:left;width:750px;border:1px solid #e7e7eb;height:350px;margin-top:10px;margin-left:100px;}
#addPTBox table td{padding:5px;}
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
					<h2>诚信管理</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA6" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						
						<div class="quyukuang" id="ptRight">
							<div class="title">
						        <h3>扣除商家的诚信分值</h3>
						    </div>
						    <div class="content">
						    	
						    	
						    	
						    	
						    	
						    	
						    	
						    	<div class="contentI">
						    		<div id="addPTBox">
						    			<table>
						    				<tbody>
						    					<tr>
						    						<td colspan="2">
						    							<p class="tip"><i class="tip"></i>
						    							请选择操作的商家、操作分值
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
						    							<select class="mySelect" id="operTypeSelect">
						    								<option value="">选择操作类型</option>
						    								<option value="RECHARGE">充值</option>
						    								<option value="FROZEN">冻结</option>
						    							</select>
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
						    					<tr>
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
