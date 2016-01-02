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

<title>诚信设置 - 系统设置 - 艺术拍拍管理后台</title>
<script type="text/javascript">

</script>
</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="setup-system" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>诚信设置</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA6" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<div style="width:99%;height:40px;padding-top:10px;">
							<a href="<%=request.getContextPath()%>/manage/admin/setUp/productTsfwAdd.jsp" class="aButton btn"><i class="add"></i>去扣除商家诚信分值</a>
						</div>
						<div class="tongjiBox quyukuang" style="width:49%;margin-left:5px;">
							<div class="title">
						        <h3>诚信级别</h3>
						    </div>
						    <div class="content">
						    	<div class="tableBox myList" >
									<table cellpadding="0" cellspacing="0">
										<tbody>
											<c:forEach var="g" items="${requestScope.sincerityLevelList}">
												<tr>
													<td>${g.levelName}</td>
													<td class="txtAC">(${g.lowScore}-${g.heighScore}]</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
						    </div>
						</div>
						<div class="tongjiBox quyukuang" style="width:49%;margin-left:5px;">
							<div class="title">
						        <h3>诚信扣分制度</h3>
						    </div>
						    <div class="content">
						    	<div class="tableBox myList" >
									<table cellpadding="0" cellspacing="0">
										<tbody>
											<c:forEach var="g" items="${requestScope.sincerityTypeList}">
												<tr>
													<td>${g.typeName}</td>
													<td class="txtAC">扣除${g.score}</td>
												</tr>
											</c:forEach>
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
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
