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

<title>店铺级别设置 - 系统设置 - 艺术拍拍管理后台</title>
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
					<h2>系统设置</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA2" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="bodyRC_tip">
						<p class="desc">
							为保证系统正常运行，请慎重修改店铺级别。&nbsp;&nbsp;&nbsp;&nbsp;双击输入框获得焦点进行修改
						</p>
					</div>
					<div id="mainContent">
						<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>名称</td>
										<td>最多能选择多少个大类</td>
										<td>自己举办专场费用</td>
										<td>自己举办专场退还方案</td>
										<td>平台举办的专场退还方案</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="m" items="${requestScope.app_shopLevelMap}">
										<c:set value="${m.value}" var="sl" scope="request"></c:set>
										<tr>
											<td>${requestScope.sl.name}</td>
											
											<td class="txtAC" >
												<input type="text" 
												value="${requestScope.sl.shopMainBigProductTypeCount}" 
												class="textInput" 
												onblurMod="yes"
												oldVal="${requestScope.sl.shopMainBigProductTypeCount}"
												url="<%=request.getContextPath()%>/admin/shopLevel/modify?id=${requestScope.sl.id}&propertyKey=shopMainBigProductTypeCount&propertyValue="
												readonly="readonly"
												/>
												个
											</td>
											<td class="txtAC">
												<input type="text" 
												value="${requestScope.sl.specialPrice}" 
												class="textInput" 
												onblurMod="yes"
												oldVal="${requestScope.sl.specialPrice}"
												url="<%=request.getContextPath()%>/admin/shopLevel/modify?id=${requestScope.sl.id}&propertyKey=specialPrice&propertyValue="
												readonly="readonly"
												/>
												元
											</td>
											<td class="txtAC">
												<select 
													class="mySelect" 
													onChangeMod="yes"
													url="<%=request.getContextPath()%>/admin/shopLevel/modify?id=${requestScope.sl.id}&propertyKey=specialReturnPlan&propertyValue="
													oldVal="${requestScope.sl.specialReturnPlan}"
												>
													<c:if test='${requestScope.sl.specialReturnPlan=="ONE"}'>
														<option selected="selected" value='ONE'>ONE方案</option>
														<option value='TWO'>TWO方案</option>
													</c:if>
													<c:if test='${requestScope.sl.specialReturnPlan=="TWO"}'>
														<option value='ONE'>ONE方案</option>
														<option selected="selected" value='TWO'>TWO方案</option>
													</c:if>
												</select>
											</td>
											<td class="txtAC">
												<select 
													class="mySelect" 
													onChangeMod="yes"
													url="<%=request.getContextPath()%>/admin/shopLevel/modify?id=${requestScope.sl.id}&propertyKey=webSpecialReturnPlan&propertyValue="
													oldVal="${requestScope.sl.webSpecialReturnPlan}"
												>
													<c:if test='${requestScope.sl.webSpecialReturnPlan=="ONE"}'>
														<option selected="selected" value='ONE'>ONE方案</option>
														<option value='TWO'>TWO方案</option>
													</c:if>
													<c:if test='${requestScope.sl.webSpecialReturnPlan=="TWO"}'>
														<option value='ONE'>ONE方案</option>
														<option selected="selected" value='TWO'>TWO方案</option>
													</c:if>
												</select>
											</td>
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
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
