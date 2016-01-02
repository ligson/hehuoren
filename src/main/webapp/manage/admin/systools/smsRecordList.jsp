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

<title>短信发送记录 - 爱尔特合伙人后台</title>
<script type="text/javascript">
<!--
function mydownload(a){
	var id=$(a).attr("dataId");
	$.ajax( {
		type : "POST",
		url : allWeb+"/admin/dataBackup/down?id="+id,
		success : function(msg) {
			alert(msg);
		}
	})
}
-->

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
					<div id="bodyRC_tip">
						<p class="desc">
							您当前短信余额：${requestScope.balance}条。《<a href="<%=request.getContextPath()%>/manage/public/help/sms.jsp" target="_blank">查看发送规则</a>》
						</p>
					</div>
					<div id="mainContent">
						<div style="width:99%;height:40px;padding-top:10px;display:none;">
							<a href="<%=request.getContextPath()%>/admin/sms/toSend" class="aButton btn"><i class="add"></i>去发送短信</a>
						</div>
						<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>日期</td>
										<td>手机号</td>
										<td>类型</td>
										<td>内容</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="d" items="${requestScope.smsRecordList}">
										<tr>
											<td>
												<fmt:formatDate value="${d.relDate}" pattern="yy-MM-dd HH:mm" />
											</td>
											<td class="txtAC">
												${d.telPhone}
											</td>
											<td class="txtAC">${d.sdkMtType}</td>
											<td>
												${d.content}
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="4">
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
