<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>所有的property属性列表</title>
<style type="text/css">
table{width:2000px}
table thead tr td{background-color:#ccc;padding:5px;font-weight: bold;border-right:1px solid #ddd;}
table tbody tr td{padding:3px;border-bottom:1px solid #fff;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#propertyTbody").delegate(".htmlAddTd input","click",function(){
		var curInput=$(this);
		var type=curInput.attr("type");
		if(type=="checkbox"){
			var propertyValueId=curInput.attr("propertyValueId");
			var defaultValue;
			 if(curInput.is(":checked")==true){
				 //alert("现在要选中，代表之前是没有选中的");
				 defaultValue=true;
			 }else{
				// alert("现在要取消选中，代表之前是选中的");
				 defaultValue=false;
			 } 
			 var pvalue=curInput.val();
			 modifyPropertyValue("defaultValue",propertyValueId,defaultValue,pvalue);
		}
	});
});
function modifyPVValue(a){
	var pvalue=$(a).val();
	var oldValue=$(a).attr("oldValue");
	if(oldValue!=pvalue){
		var propertyValueId=$(a).attr("propertyValueId");
		modifyPropertyValue("pvalue",propertyValueId,false,pvalue);
	}
	
}
function modifyPropertyValue(modifyValueOrDefault,pvId,defaultValue,pvalue){
	$.ajax( {
		type : "POST",
		url : allWeb+"sysProperty/modifyPv",
		data : "pvalue="+pvalue+"&defaultValue="+defaultValue+"&pvId="+pvId+"&modifyValueOrDefault="+modifyValueOrDefault,
		success : function(msg) {
			if(null==msg || msg==""){
				alert("成功");
			}
		}
	})
}
function addPvs(i){
	var pId=$(i).attr("pId");
	var pvalue=$("#addPvInput_pId"+pId).val();
	if(null!=pvalue && pvalue!="" && null!=pId){
		
		$.ajax( {
			type : "POST",
			url : allWeb+"sysProperty/addPVs",
			data : "pId="+pId+"&pvalue="+pvalue,
			success : function(msg) {
				if(null==msg || msg==""){
					alert("添加成功，请刷新");
				}
			}
		})
	}
}
</script>
</head>
<body>
	<h1 align="center">所有的property属性列表<a href="<%=request.getContextPath()%>/myFrameU/expand/addSystemLibraryProperty.jsp">【追加属性】</a></h1>
	<div style="width:2000px;margin:0 auto;background-color:#eee;padding:20px;">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>修改</td>
					<td>名称</td>
					<td>Key</td>
					<td>数据类型</td>
					<td>addType</td>
					<td>备选</td>
					<td>数据</td>
					<td>追加数据</td>
				</tr>
			</thead>
			<tbody id="propertyTbody">
				<c:forEach var="p" items="${requestScope.propertyList}">
					<tr>
						<td>
							<a href="<%=request.getContextPath()%>/sysProperty/findPropertyById?id=${p.id}">修改</a>
						</td>
						<td>${p.propertyName}</td>
						<td>${p.propertyKey}</td>
						<td>${p.dataType}</td>
						<td>${p.addType}</td>
						<td>
							<c:if test='${p.addType!="INPUT"}'>
								<c:forEach var="pv" items="${p.sysLibraryPropertyValueSet}">
									<input oldValue="${pv.pvalue}" value="${pv.pvalue}" type="text" style="width:50px" propertyValueId="${pv.id}"  onBlur="modifyPVValue(this);"/>
								</c:forEach>
							</c:if>
						</td>
						<td style="width:300px;" class="htmlAddTd">
							${p.htmlAdd}
						</td>
						<td>
							<input type="text" value="" name="pvalue" id="addPvInput_pId${p.id}" style="width:60px;"/>
							<input type="button" value="提交" style="padding:3px;" onClick="addPvs(this)" pId="${p.id}"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>