<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加product</title>
<style type="text/css">
#outer{width:800px;height:auto;padding:10px;background:#eee;margin:0 auto;}
.propertyDiv{height:30px;float:left;margin-right:10px;background-color:#fff;border:1px solid #ddd;padding-left:0px;padding-right:8px;}
.propertyDiv .name{float:left;height:100%;line-height: 30px;background:blue;color:#fff;font-weight: bold;padding-left:3px;padding-right:3px;}
.propertyDiv .value{float:left;line-height: 30px;height:100%;}
</style>
<script type="text/javascript" src="http://www.024lm.com/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
function getPropertyDistributes(s){
	var productTypeId=$(s).val();
	var dRange="{'key':'productTypeId','value':'"+productTypeId+"'}";
	var className="myFrameU.expand.use.test.entity.TestProduct";
	$.ajax( {
		type : "POST",
		url : "http://localhost:8080/yishupaipai/expand/test/findDistributePropertys_productTypeId",
		data : "className="+className+"&dRange="+dRange,
		success : function(msg) {
			$("#dRangePropertyTd").html(msg);
		}
	})
}
function addProduct(i){
	var form=document.getElementById("myForm");
	var pvrFormInput=document.getElementById("propertyValuesResultForm");
	var result = getAllPropertyValues();
	alert(result);
	pvrFormInput.value=result;
	alert(pvrFormInput.value);
	form.submit();
}
</script>
</head>
<body>

	<div id="outer">
		<form action="http://localhost:8080/yishupaipai/expand/test/addProduct" method="post" id="myForm">
			<table>
				<tbody>
					<tr>
						<td>产品名:</td>
						<td><input type="text" value="" name="name"/></td>
					</tr>
					<tr>
						<td>所有数据的扩展属性:</td>
						<td>
							<jsp:include page="propertyAddHTML.jsp"></jsp:include>
						</td>
					</tr>
					<tr>
						<td>产品类型:</td>
						<td>
							<select name="productTypeId" onchange="getPropertyDistributes(this)">
								<option value="">选择类型</option>
								<c:forEach var="pt" items="${applicationScope.expandTest_testProductTypeList}">
									<option value="${pt.id}">${pt.id}-${pt.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>产品类型属性</td>
						<td id="dRangePropertyTd">
							
						</td>
					</tr>
					<tr>
						<td></td>
						<td >
							<input type="text" value="" id="propertyValuesResultForm" name="propertyValues"/>
							<input type="button" value="提交" style="padding:5px;" onClick="return addProduct(this)"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>