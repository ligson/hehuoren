<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>productId</title>
<style type="text/css">
#outer{width:800px;height:auto;padding:10px;background:#eee;margin:0 auto;}
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
			<table>
				<tbody>
					<tr>
						<td>产品名:</td>
						<td>${requestScope.product.name}</td>
					</tr>
					<tr>
						<td>所有数据的扩展属性:</td>
						<td>
							<jsp:include page="propertyShowHTML.jsp">
								<jsp:param value="propertyDistributeList_all" name="propertyDistributeListName"/>
							</jsp:include>
						</td>
					</tr>
					<tr>
						<td>产品类型:</td>
						<td>
							${requestScope.product.productTypeId}
						</td>
					</tr>
					<tr>
						<td>产品类型属性</td>
						<td>
							<jsp:include page="propertyShowHTML.jsp">
								<jsp:param value="propertyDistributeList_dRangePT" name="propertyDistributeListName"/>
							</jsp:include>
						</td>
					</tr>
					
				</tbody>
			</table>
	</div>
</body>
</html>