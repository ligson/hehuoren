<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>productList</title>
<style type="text/css">
#outer{width:100%;height:auto;padding:10px;background:#eee;margin:0 auto;}
#outer table{width:100%;}
#outer table thead tr td{background:#000;color:#fff;height:30px;line-height: 30px;}
#outer table tr td{padding:5px;border-right:1px solid #fff;border-bottom:1px solid #fff;}
.propertyDiv{height:30px;float:left;margin-right:10px;background-color:#fff;border:1px solid #ddd;padding-left:0px;padding-right:8px;}
.propertyDiv .name{float:left;height:100%;line-height: 30px;background:blue;color:#fff;font-weight: bold;padding-left:3px;padding-right:3px;}
.propertyDiv .value{float:left;line-height: 30px;height:100%;}
</style>
<script type="text/javascript" src="http://www.024lm.com/js/jquery-1.7.min.js"></script>
<script type="text/javascript">

</script>

</head>
<body>

	<div id="outer">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>ID</td>
					<td>产品名</td>
					<td>类型</td>
					<td>范围为ALL的数据</td>
					<td>范围特有的数据</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${requestScope.productList}">
					<tr>
						<td>${p.id}</td>
						<td>${p.name }</td>
						<td>${p.productTypeId}</td>
						<td>
							<c:forEach var="pd" items="${requestScope.propertyDistributeList_all}">
								<c:set var="propertyId_str" value="pId_${pd.propertyId}" scope="request"></c:set>
								<c:set var="property" value="${applicationScope.libraryPropertyMap[requestScope.propertyId_str]}"></c:set>
								<div class="propertyDiv">
									<div class="name">${property.propertyName}</div>
									<div class="value">
										${p.propertyValuesMap[requestScope.propertyId_str]}
									</div>
								</div>
							</c:forEach>
						</td>
						<td>
							<c:set var="productTypeIdStr" value="productTypeId_${p.productTypeId}" scope="request"></c:set>
							<c:set var="propertyDistributeList_dRangePT" value="${requestScope.propertyDistributeList_dRangePT_Map[requestScope.productTypeIdStr]}" scope="request"></c:set>
							<c:forEach var="pd" items="${requestScope.propertyDistributeList_dRangePT}">
								<c:set var="propertyId_str" value="pId_${pd.propertyId}" scope="request"></c:set>
								<c:set var="property" value="${applicationScope.libraryPropertyMap[requestScope.propertyId_str]}"></c:set>
								<div class="propertyDiv">
									<div class="name">${property.propertyName}</div>
									<div class="value">
										${p.propertyValuesMap[requestScope.propertyId_str]}
									</div>
								</div>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>