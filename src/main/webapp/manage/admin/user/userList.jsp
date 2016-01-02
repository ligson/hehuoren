<%@ page language="java"  import="java.util.Date,myFrameU.util.commonUtil.date.DateUtils" contentType="text/html; charset=utf-8"
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
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />


<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/ui.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/riqi.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/riqi.js"></script>


<title>用户列表 - 爱尔特合伙人后台</title>
<script type="text/javascript">
function moniUserlogin(a){
	
	var userId=$(a).attr("userId");
	var name=$(a).attr("name");
	var password=$(a).attr("password");
	
	$.ajax( {
		type : "POST",
		url : allWeb+"/wrap/userLogin?name="+name+"&password="+password,
		success : function(msg) {
			alert(msg);
			if(null==msg || msg==""){
				myAlert(200,130,"成功!","登录成功","ok");
			}
			
		}
	})
}
function moniUserlogout(a){
	var userId=$(a).attr("userId");
	var name=$(a).attr("name");
	var password=$(a).attr("password");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/logout",
		success : function(msg) {
			myAlert(200,130,"成功!","退出成功","ok");
			$("#headermiddelBox").remove();
		}
	})
}
function beHeFensi(a){
	var userId=$(a).attr("userId");
	$.ajax( {
		type : "POST",
		url : allWeb+"wrap/user/beHeFensi?queryUserType=id&id="+userId,
		success : function(msg) {
			if(null!=msg && msg!=""){
				myAlert(200,130,"失败!",msg,"ok");
			}else{
				myAlert(200,130,"成功!","成功成为他的粉丝","ok");
			}
		}
	})
	
}
function modifyUJF(b){
	var userId=$(b).attr("userId");
	var jInputId=$(b).attr("jfInputId");
	var jInput=$("#"+jInputId);
	var jf=jInput.val();
	if(jf!=null && jf!=""){
		var jfI = parseInt(jf);
		if(jfI>=0){
			$.ajax( {
				type : "POST",
				url : allWeb+"admin/user/modifyScore?id="+userId+"&totalScore="+jfI,
				success : function(msg) {
					if(null!=msg && msg!=""){
						myAlert(200,130,"失败!",msg,"ok");
					}else{
						myAlert(200,130,"成功!","修改积分成功","ok");
					}
				}
			})
		}
	}
}
function moniUserTXApply(a){
	$.ajax( {
		type : "POST",
		url : allWeb+"user/apply/addApplySimple?price=1&applyTypeKey=AccountTXApply",
		success : function(msg) {
			if(msg!=null && msg!=""){
				alert(msg);
			}else{
				alert("提交提现申请成功，请耐心等待管理员审核");
			}
		}
	})
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
					<jsp:param value="user-user" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>用户管理->用户列表</h2>
					<div class="tabBox" id="bodyRH_tab">
						<c:set var="level1" value="{'key':'userLevelId','value':'1'}" scope="request"></c:set>
						<c:set var="level2" value="{'key':'userLevelId','value':'2'}" scope="request"></c:set>
						<c:set var="level3" value="{'key':'userLevelId','value':'3'}" scope="request"></c:set>
						<c:if test='${fn:contains(requestScope.queryArgs,requestScope.level1)}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA1" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${fn:contains(requestScope.queryArgs,requestScope.level2)}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA2" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${fn:contains(requestScope.queryArgs,requestScope.level3)}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA3" name="rightTabName"/>
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
									<span><input type="text" value="用户昵称" class="queryTxt" queryArgsKey="nicheng"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="用户电话" class="queryTxt" queryArgsKey="name"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						
						<%
							String now=DateUtils.getCurrDateStr_YYYY_MM_DD();
						%>
						<div class="everyQuery">
							<span class="k" style="display:block;">注册时间：</span>
							<span>
								<span class="dateRange" queryArgsKey="zhuceDate">
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
						<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>openId</td>
										<td>登录账号</td>
										<td>登录密码</td>
										<td>昵称</td>
										<td>粉丝数</td>
										<td>推荐人</td>
										<td>注册日期</td>
										<td>积分</td>
										<td style="display:none"></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="u" items="${requestScope.userList}">
										<c:set var="addIdStr" value="addId_${u.addressId}" scope="request"></c:set>
										<c:set var="address" value="${applicationScope.addressAll_map[requestScope.addIdStr]}" scope="request"></c:set>
										<tr>
											<td >
												${u.wxId}
											</td>
											<td >${u.name}</td>
											<td>
												${u.password}
											</td>
											<td>
												${u.nicheng}
											</td>
											<td>
												${u.fensiCount}
											</td>
											<td>
												${u.tuijianRenNicheng}
											</td>
											<td>
												<fmt:formatDate value="${u.zhuceDate}" pattern="yyyy年MM月dd日" /> 
											</td>
											
											<td class="txtAC">
												<input style="width:60px;height:27px;float:left;" id="userIdJFID_${u.id}" type="text" value="${u.totalScore}" />
												<input onClick="modifyUJF(this)" userId="${u.id}" jfInputId="userIdJFID_${u.id}" type="button" value="修改积分" style="width:60px;height:30px;float:left;cursor: pointer;text-align: center;"/>
											</td>
											<td style="display:none">
												<a href="javascript:void(0);" onClick="moniUserlogin(this)" userId="${u.id}" name="${u.name}" password="${u.password}">模拟登录</a>
												<a href="javascript:void(0);" onClick="moniUserTXApply(this)" userId="${u.id}" name="${u.name}" password="${u.password}">模拟提现1元</a>
												<c:if test='${sessionScope.myUser.id!=u.id}'>
													<c:if test='${u.userLevelId!=1}'>
														<a href="javascript:void(0);" onClick="beHeFensi(this)" userId="${u.id}">成为他的粉丝</a>
													</c:if>
												</c:if>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
