<%@ page language="java" import="java.util.Date,myFrameU.util.commonUtil.date.DateUtils"  contentType="text/html; charset=utf-8"
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
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/ui.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/riqi.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/riqi.js"></script>

<title>微信帮助资讯 - 爱尔特合伙人后台</title>
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
					<jsp:param value="news" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>微信帮助资讯</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA1" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					
					<div id="mainContent">
						<c:if test='${empty requestScope.newsList}'>
							<div class="noDataTip"></div>
						</c:if>
						<c:if test='${!empty requestScope.newsList}'>
							<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>ID</td>
										<td>标题</td>
										<td>分类</td>
										<td>浏览量</td>
										<td>状态</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="n" items="${requestScope.newsList}">
										<tr>
											<td>${n.id}</td>
											<td style="width:300px">
												<p>${n.title}</p>
											</td>
											<td>
												<c:forEach var="nt" items="${requestScope.newsTypeList}">
													<c:if test='${nt.id==n.newsTypeId}'>
														${nt.allName}
													</c:if>
												</c:forEach>
											</td>
											<td>${n.viewsCount}</td>
											<td style="width:80px;">
												<c:if test='${n.status=="OK"}'>
													正常
												</c:if>
												<c:if test='${n.status=="CLOSE"}'>
													已冻结
												</c:if>
											</td>
											<td>
												<a href="<%=request.getContextPath()%>/admin/news/toMod?id=${n.id}" class="btn btnColor_1">修改</a>
												<a style="display:none;" href="<%=request.getContextPath()%>/admin/news/del?id=${n.id}" class="btn ">删除</a>
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
