<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript">
function findSons(s){
	var endChange=$("#endChangeInput").val();
	var end = $("#endInput").val();
	var addId=$(s).val();
	if(addId!="0"){
		$.ajax( {
			type : "POST",
			url : allWeb+"address/findSons?id="+addId+"&end="+end+"&endChange="+endChange,
			success : function(msg) {
				$(s).nextAll().remove();
				$(s).after(msg);
				$(s).attr("name","");
				
				
			}
		})
	}
}
</script>
<style type="text/css">
	select{border:1px solid #ddd !important;}
</style>
</head>
<body>
<input type="hidden" value="${param.end}" id="endInput"/>
<input type="hidden" value="${param.endChange}" id="endChangeInput"/>
<c:if test='${empty requestScope.currentAddress}'>
<select name="addressId" onChange="findSons(this);">
	<option value="0">请选择省份</option>
	<c:forEach var="ak" items="${applicationScope.addressROOT_map}">
		<c:set var="a" value="${ak.value}" scope="request"></c:set>
		<c:if test='${requestScope.a.isROOT=="0"}'>
			<option value="${requestScope.a.id}">${requestScope.a.name}</option>
		</c:if>
	</c:forEach>
</select>
</c:if>

<c:if test='${!empty requestScope.currentAddress}'>
	<c:if test='${requestScope.currentAddress.isROOT=="0"}'>
		<!-- 当前选中的是ROOT，也就是选中了省，这一步要选市 -->
		<select name="addressId" onChange="findSons(this);">
			<c:forEach var="ak" items="${applicationScope.addressROOT_map}">
				<c:set var="a" value="${ak.value}" scope="request"></c:set>
				<c:if test='${requestScope.a.id==requestScope.currentAddress.id}'>
					<option value="${requestScope.a.id}" selected="selected">${requestScope.a.name}</option>
				</c:if>
				<c:if test='${requestScope.a.id!=requestScope.currentAddress.id}'>
					<option value="${requestScope.a.id}" >${requestScope.a.name}</option>
				</c:if>
			</c:forEach>
		</select>
		<c:if test='${param.end=="city"}'>
			<select name="addressId">
				<c:forEach var="a" items="${applicationScope.addressList_all}">
					<c:if test='${a.jibie==2}'>
						<c:if test='${a.rootTypeId==requestScope.currentAddress.id}'>
							<option value="${a.id}" >${a.name}</option>
						</c:if>
					</c:if>
				</c:forEach>
			</select>
		</c:if>
	</c:if>
	<c:if test='${requestScope.currentAddress.jibie=="2"}'>
		<!-- 当前选中的是市 -->
		<c:set var="rootAddIdStr" value="addId_${requestScope.currentAddress.fatherId}" scope="request"></c:set>
		<c:set var="rootAddress" value="${applicationScope.addressROOT_map[requestScope.rootAddIdStr]}" scope="request"></c:set>
		<select  onChange="findSons(this);">
			<c:forEach var="ak" items="${applicationScope.addressROOT_map}">
				<c:set var="a" value="${ak.value}" scope="request"></c:set>
				<c:if test='${requestScope.a.id==requestScope.rootAddress.id}'>
					<option value="${requestScope.a.id}" selected="selected">${requestScope.a.name}</option>
				</c:if>
				<c:if test='${requestScope.a.id!=requestScope.rootAddress.id}'>
					<option value="${requestScope.a.id}" >${requestScope.a.name}</option>
				</c:if>
			</c:forEach>
		</select>
		<c:if test='${param.end=="city"}'>
			<select name="addressId">
				<c:forEach var="a1" items="${applicationScope.addressList_all}">
					<c:if test='${a1.fatherId==requestScope.rootAddress.id}'>
						<c:if test='${a1.jibie=="2"}'>
						<c:if test='${a1.id==requestScope.currentAddress.id}'>
							<option value="${a1.id}" selected="selected">${a1.name}</option>
						</c:if>
						<c:if test='${a1.id!=requestScope.currentAddress.id}'>
							<option value="${a1.id}" >${a1.name}</option>
						</c:if>
					</c:if>
					</c:if>
				</c:forEach>
			</select>
		</c:if>
		<c:if test='${param.end!="city"}'>
			
		</c:if>
	</c:if>
</c:if>

</body>
</html>