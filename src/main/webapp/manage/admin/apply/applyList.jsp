<%@ page language="java" import="java.util.Date,myFrameU.util.commonUtil.date.DateUtils" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/user.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/ui.js"></script>

<link href="<%=request.getContextPath()%>/artDialog-5.0.3/skins/twitter.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/artDialog-5.0.3/artDialog.min.js"></script>
<script src="<%=request.getContextPath()%>/artDialog-5.0.3/artDialog.plugins.min.js"></script>

<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/riqi.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/riqi.js"></script>


<title>审批大厅  - 爱尔特合伙人管理后台</title>
<style type="text/css">
.tableBox thead tr td{border-top:none !important;}
.moreTr{display:none;}
.moreTr td{background:#eee;}
.moreTr td table{width:90%;margin:0 auto;}
.moreTr td table  td{background:#FFFFF0;}
</style>
<script type="text/javascript">
function qiehuan(a){
	var contentId=$(a).attr("contentBoxId");
	$(".apply_xq_conBox").hide();
	$("#"+contentId).show();
	
	$(".apply_xq_tab").find("a").removeClass("selected");
	$(a).addClass("selected");
}

function apply(a){
	var applyId=$(a).attr("applyId");
	var result=$(a).attr("result");
	var remarks=$("#remarksTxt").val();
	var ok="ok";
	if(result=="APPLYERROR"){
		if(null==remarks || remarks==""){
			ok="error";
		}
	}
	if(ok=="ok"){
		$.ajax( {
			type : "POST",
			url : allWeb+"admin/apply/approval?id="+applyId+"&result="+result+"&remarks="+remarks,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
				}else{
					myAlert(200,130,"成功!","审批成功","ok");
					if(null!=myDialog && myDialog!=undefined){
						myDialog.close();
					}
				}
			}
		})
	}else{
		alert("审批不通过，要填写原因");
	}
}

var myDialog;
function findApplyById(a){
	var applyId=$(a).attr("applyId");
	myDialog = art.dialog({title: "申请详情",lock: true});
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/apply/findById?id="+applyId,
		success : function(msg) {
			myDialog.content(msg);
		}
	})
}


</script>
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>

</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="apply-apply" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>审批大厅</h2>
					<div class="tabBox" id="bodyRH_tab">
						<c:if test='${fn:contains(requestScope.queryArgs,"AccountTXApply")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA1" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${fn:contains(requestScope.queryArgs,"UserLevelHHR")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA2" name="rightTabName"/>
							</jsp:include>
						</c:if>
					</div>
				</div>
				<div id="bodyR_content">
					
					<div id="queryArgsBox">
						<input type="hidden" value="${requestScope.queryArgs}" class="requestQueryArgsInput"/>
						
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="申请人" class="queryTxt" queryArgsKey="sponsorName"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						
						<div class="everyQuery" >
							<span class="k">审批结果:</span>
							<span>
								<span class="mySelect" width="100" status="close">
									<span class="input">审核结果</span>
									<span class="arrow"></span>
									<span class="down">
										<ul>
											<li><a href="javascript:void(0);"  value="{'key':'result','value':'APPLYOK'}">审核通过</a></li>
											<li><a href="javascript:void(0);"  value="{'key':'result','value':'APPLYERROR'}">审核不通过</a></li>
										</ul>
									</span>
								</span>
							</span>
						</div>
						
						<div class="everyQuery">
							<span class="k">审批进度:</span>
							<span>
								<span class="mySelect" width="100" status="close">
									<span class="input">审核进度</span>
									<span class="arrow"></span>
									<span class="down">
										<ul>
											<li><a href="javascript:void(0);"  value="{'key':'speed','value':'WAIT'}">等待审核</a></li>
											<li><a href="javascript:void(0);"  value="{'key':'speed','value':'FINISH'}">审核完毕</a></li>
										</ul>
									</span>
								</span>
							</span>
						</div>
						
						<%
							String now=DateUtils.getCurrDateStr_YYYY_MM_DD();
						%>
						<div class="everyQuery">
							<span class="k"></span>
							<span>
								<span class="dateRange" queryArgsKey="relDate">
									<span class="begin riqiBox">
										<input name="beginDate" type="text" value="" class="textInput" id="beginDateInput" readonly="readonly"/>
										<i></i>
									</span>
									<span class="fenge">至</span>
									<span class="begin riqiBox">
										<input name="endDate" type="text" value="<%=now%>" class="textInput" id="endDateInput" readonly="readonly"/>
										<i></i>
									</span>
								</span>
							</span>
						</div>
						
					</div>
					
					
					
					
					
					
					<div id="mainContent">
						<c:if test='${empty requestScope.applyList}'>
							<div class="noDataTip"></div>
						</c:if>
						<c:if test='${!empty requestScope.applyList}'>
							<div class="tableBox myList " >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>申请类型</td>
										<td>申请人</td>
										<td>申请时间</td>
										<td>申请标题</td>
										<td>审批时间</td>
										<td>审批结果</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="o" items="${requestScope.applyList}">
										<tr>
												<td style="width:120px;">
													<c:if test='${o.applyTypeKey=="UserLevelHHR"}'>申请成为合伙人</c:if>
													<c:if test='${o.applyTypeKey=="AccountTXApply"}'>提现申请</c:if>
												</td>
												<td>
													${o.sponsorName}
												</td>
												<td>
													<fmt:formatDate value="${o.relDate}" pattern="yy-MM-dd HH:mm" /> 
												</td>
												<td>
													${o.applyTitle}
												</td>
												<td style="width:100px;">
													<fmt:formatDate value="${o.dealDate}" pattern="yy-MM-dd HH:mm" /> 
												</td>
												<td>
													<c:if test='${o.result=="APPLYOK"}'>
														<i class="weixinIOC shenhetongguo"></i>
													</c:if>
													<c:if test='${o.result=="APPLYERROR"}'>
														<i class="weixinIOC shenheshibai"></i>
													</c:if>
													<c:if test='${o.speed=="WAIT" || o.speed=="ING"}'>
														<i class="weixinIOC shenhezhong"></i>
													</c:if>
												</td>
												
												<td style="color:#999;" class="morePTD">
													<a href="javascript:void(0);"  class="btn btnColor_1" onClick="findApplyById(this)" applyId="${o.id}">详情</a>
												</td>
											</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="7">
											<jsp:include page="../../util/pager.jsp"></jsp:include>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
