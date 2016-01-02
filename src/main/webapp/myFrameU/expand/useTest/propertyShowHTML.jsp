<%@ page language="java" import="java.util.List" import="myFrameU.expand.distribution.entity.PropertyDistribute" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加记录时展示所需要填写的属性</title>
<style type="text/css">
.propertyDiv{height:30px;float:left;margin-right:10px;background-color:#fff;border:1px solid #ddd;padding-left:0px;padding-right:8px;}
.propertyDiv .name{float:left;height:100%;line-height: 30px;background:blue;color:#fff;font-weight: bold;padding-left:3px;padding-right:3px;}
.propertyDiv .value{float:left;line-height: 30px;height:100%;}
</style>

</head>
<body>
 <%//获得includeAction.jsp传来的值:
    String propertyDistributeListName = request.getParameter("propertyDistributeListName");
 	List<PropertyDistribute> list=(List<PropertyDistribute>)request.getAttribute(propertyDistributeListName);
 	request.setAttribute("propertyDistributeList", list);
%>

	
							<c:forEach var="pd" items="${requestScope.propertyDistributeList}">
								<c:set var="propertyId_str" value="pId_${pd.propertyId}" scope="request"></c:set>
								<c:set var="property" value="${applicationScope.libraryPropertyMap[requestScope.propertyId_str]}"></c:set>
								<div class="propertyDiv">
									<div class="name">${property.propertyName}</div>
									<div class="value">
										${requestScope.propertyValuesMap[requestScope.propertyId_str]}
									</div>
								</div>
							</c:forEach>
</body>
</html>