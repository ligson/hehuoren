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

<title>拍品特色服务 - 系统设置 - 艺术拍拍管理后台</title>

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
							<jsp:param value="myTabLiA4" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<div style="width:99%;height:40px;padding-top:10px;">
							<a href="<%=request.getContextPath()%>/manage/admin/setUp/productTsfwAdd.jsp" class="aButton btn"><i class="add"></i>新建产品特色</a>
						</div>
						<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>名称</td>
										<td>在新增产品时，是否默认选中？</td>
										<td>删除</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="m" items="${requestScope.app_productTesefuwuMap}">
										<c:set value="${m.value}" var="ptf" scope="request"></c:set>
										<tr>
											<td class="txtAC">
												<input type="text" 
												value="${requestScope.ptf.name}" 
												class="textInput" 
												onblurMod="yes"
												oldVal="${requestScope.ptf.name}"
												url="<%=request.getContextPath()%>/admin/productTsfw/modify?id=${requestScope.ptf.id}&propertyKey=name&propertyValue="
												readonly="readonly"
												/>
											</td>
											<td class="txtAC">
												<select 
													class="mySelect" 
													onChangeMod="yes"
													url="<%=request.getContextPath()%>/admin/productTsfw/modify?id=${requestScope.ptf.id}&propertyKey=addProductMust&propertyValue="
													oldVal="${requestScope.ptf.addProductMust}"
												>
													<c:if test='${requestScope.ptf.addProductMust==true}'>
														<option selected="selected" value='true'>默认选中</option>
														<option value='false'>默认不选中</option>
													</c:if>
													<c:if test='${requestScope.ptf.addProductMust==false}'>
														<option value='true'>默认选中</option>
														<option selected="selected"  value='false'>默认不选中</option>
													</c:if>
												</select>
											</td>
											<td class="link">
												<a href="javascript:void(0);" type="delete" url="admin/productTsfw/remove?id=${requestScope.ptf.id}" parent="tr">删除</a>
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
