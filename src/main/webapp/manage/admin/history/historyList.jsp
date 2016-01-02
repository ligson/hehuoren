<%@ page language="java" contentType="text/html; charset=utf-8"
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

<title>安全历史记录 - 爱尔特合伙人后台</title>
<script type="text/javascript">
function moniUserlogin(a){
	var userId=$(a).attr("userId");
	var name=$(a).attr("name");
	var password=$(a).attr("password");
	$.ajax( {
		type : "POST",
		url : allWeb+"/userLogin?name="+name+"&password="+password,
		success : function(msg) {
			myAlert(200,130,"成功!","登录成功","ok");
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
</script>
</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="admin-history" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>安全历史记录</h2>
					<div class="tabBox" id="bodyRH_tab">
						
						<c:if test='${fn:contains(requestScope.queryArgs,"ACCOUNT")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA2" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${fn:contains(requestScope.queryArgs,"LOGIN")}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA1" name="rightTabName"/>
							</jsp:include>
						</c:if>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>记录类型</td>
										<td>操作时间</td>
										<td>操作IP</td>
										<td>操作人</td>
										<td>操作标题</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="u" items="${requestScope.historyList}">
										<tr>
											<td >
												<c:if test='${u.historyType=="LOGIN"}'>登录历史</c:if>
											</td>
											<td >
											<fmt:formatDate value="${u.relDate}" pattern="yy-MM-dd HH:mm" /> 
											</td>
											<td>
												${u.ip}
											</td>
											<td>${u.whoName}</td>
											<td>
												${u.title}
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="5">
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
