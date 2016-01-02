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

<title>用户实名认证 - 爱尔特合伙人后台</title>

</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="user-certi" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>用户管理->用户资料</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab1.jsp">
								<jsp:param value="myTabLiA1" name="rightTabName"/>
							</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<c:if test='${requestScope.certificationType=="shiming"}'>
							<div class="tableBox myList" >
								<table cellpadding="0" cellspacing="0">
									<thead>
										<tr>
											<td>名称</td>
											<td>身份证正面</td>
											<td>身份证反面</td>
											<td>身份证号码</td>
											<td>真实姓名</td>
											<td>状态</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="c" items="${requestScope.certificationList}">
											<tr>
												<td style="width:100px">${c.whoName}</td>
												<td style="width:100px">
													<c:if test='${c.shenfenzhengzUrl==null || c.shenfenzhengzUrl==""}'>
														无
													</c:if>
													<c:if test='${c.shenfenzhengzUrl!=null && c.shenfenzhengzUrl!=""}'>
														<a title="点击查看大图" href="<%=request.getContextPath()%>/${c.shenfenzhengzUrl}" target="_blank">
															<img src="<%=request.getContextPath()%>/${c.shenfenzhengzUrl}" style="width:90px"/>
														</a>
													</c:if>
													
												</td>
												
												<td style="width:100px">
													<c:if test='${c.shenfenzhengfUrl==null || c.shenfenzhengfUrl==""}'>
														无
													</c:if>
													<c:if test='${c.shenfenzhengfUrl!=null && c.shenfenzhengfUrl!=""}'>
														<a title="点击查看大图" href="<%=request.getContextPath()%>/${c.shenfenzhengfUrl}" target="_blank">
															<img src="<%=request.getContextPath()%>/${c.shenfenzhengfUrl}" style="width:90px"/>
														</a>
													</c:if>
													
												</td>
												<td>${c.shenfenzhenghaoma}</td>
												<td>${c.trueName}</td>
												<td>
													<c:if test='${c.shimingStatus=="WAIT"}'>
														等待商家申请
													</c:if>
													<c:if test='${c.shimingStatus=="APPLYOK"}'>
														审批通过
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<td colspan="6">
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
