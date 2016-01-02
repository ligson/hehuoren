<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/ui.css"/>
<title>修改系统property属性库</title>
<style type="text/css">
table tbody tr td{padding:3px;}
</style>
<script type="text/javascript">

function verUniqueKey(i){
	var val=$(i).val();
	if(null==val || val==""){
		alert("请输入唯一的key");
	}else{
		$.ajax( {
			type : "POST",
			url : allWeb+"sysProperty/uniquePropertyKey?propertyKey="+val,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
					$(i).attr("error","error");
				}else{
					myAlert(200,130,"成功!","key值唯一性检查成功","ok");
					$(i).attr("error","ok");
				}
			}
		})
	}
}
function propertyNameNull(i){
var val=$(i).val();
if(null==val || val==""){
	alert("请输入属性名称");
}
} 



function modProperty(){
	var id=$("#propertyIdInput").val();
	var name=$("#propertyNameInput").val();
	var key=$("#propertyKeyInput").val();
	var keyError=$("#propertyKeyInput").attr("error");
	var addType=$("#addTypeSelect").val();
	if(null!=name && name!=""){
		if(null!=key && key!=""){
			if(keyError=="error"){
				alert("请输入唯一的属性key");
			}else{
				$.ajax( {
					type : "POST",
					url : allWeb+"sysProperty/modifyP?propertyName="+
							name+"&propertyKey="+key+"&addType="+addType+"&id="+id+"&dataType=STRING&dataFrom=MANUAL&showType=ENUM&search=false&list=false&queryArg=true&must=false",
					success : function(msg) {
						if(msg!=null && msg!=""){
							myAlert(200,130,"失败!",msg,"error");
						}else{
							myAlert(200,130,"成功!","修改系统属性成功","ok");
						}
					}
				})
			}
		}else{
			alert("请输入有效的属性key");
		}
	}else{
		alert("请输入有效的属性名称");
	}
}








</script>
</head>
<body>
	<h1 align="center">修改系统property属性库</h1>
	<div style="width:600px;margin:0 auto;background-color:#eee;padding:20px;">
			<table>
				<tbody>
					<tr>
						<td>属性名：</td>
						<td>
							<input type="hidden" value="${requestScope.systemLibraryProperty.id}" id="propertyIdInput"/>
							<input id="propertyNameInput" onblur="propertyNameNull(this)" type="text" value="${requestScope.systemLibraryProperty.propertyName}" name="propertyName" />
						</td>
					</tr>
					<tr>
						<td>属性key：</td>
						<td>
							<input id="propertyKeyInput" onblur="verUniqueKey(this)" type="text" value="${requestScope.systemLibraryProperty.propertyKey}" name="propertyKey"/>
						</td>
					</tr>
					<tr style="display:none;">
						<td>数据类型：</td>
						<td>
							<c:if test='${requestScope.systemLibraryProperty.dataType=="INIT"}'>
								<select name="dataType">
									<option value="INT">int</option>
									<option value="LONG">long</option>
									<option value="FLOAT">float</option>
									<option value="DOUBLE">double</option>
									<option value="STRING">string</option>
									<option value="DATE">date</option>
									<option value="BOOLEAN">boolean</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.dataType=="LONG"}'>
								<select name="dataType">
									<option value="INT">int</option>
									<option value="LONG" selected="selected">long</option>
									<option value="FLOAT">float</option>
									<option value="DOUBLE">double</option>
									<option value="STRING">string</option>
									<option value="DATE">date</option>
									<option value="BOOLEAN">boolean</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.dataType=="FLOAT"}'>
								<select name="dataType">
									<option value="INT">int</option>
									<option value="LONG">long</option>
									<option value="FLOAT" selected="selected">float</option>
									<option value="DOUBLE">double</option>
									<option value="STRING">string</option>
									<option value="DATE">date</option>
									<option value="BOOLEAN">boolean</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.dataType=="DOUBLE"}'>
								<select name="dataType">
									<option value="INT">int</option>
									<option value="LONG">long</option>
									<option value="FLOAT">float</option>
									<option value="DOUBLE" selected="selected">double</option>
									<option value="STRING">string</option>
									<option value="DATE">date</option>
									<option value="BOOLEAN">boolean</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.dataType=="STRING"}'>
								<select name="dataType">
									<option value="INT">int</option>
									<option value="LONG">long</option>
									<option value="FLOAT">float</option>
									<option value="DOUBLE">double</option>
									<option value="STRING" selected="selected">string</option>
									<option value="DATE">date</option>
									<option value="BOOLEAN">boolean</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.dataType=="DATE"}'>
								<select name="dataType">
									<option value="INT">int</option>
									<option value="LONG">long</option>
									<option value="FLOAT">float</option>
									<option value="DOUBLE">double</option>
									<option value="STRING">string</option>
									<option value="DATE" selected="selected">date</option>
									<option value="BOOLEAN">boolean</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.dataType=="BOOLEAN"}'>
								<select name="dataType">
									<option value="INT">int</option>
									<option value="LONG">long</option>
									<option value="FLOAT">float</option>
									<option value="DOUBLE">double</option>
									<option value="STRING">string</option>
									<option value="DATE">date</option>
									<option value="BOOLEAN" selected="selected">boolean</option>
								</select>
							</c:if>
						</td>
					</tr>
					<tr style="display:none;">
						<td>数据来源：</td>
						<td>
							<c:if test='${requestScope.systemLibraryProperty.dataFrom=="MANUAL"}'>
								<select name="dataFrom">
									<option value="MANUAL">手动输入</option>
									<option value="WEB">获取程序数据选择</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.dataFrom=="WEB"}'>
								<select name="dataFrom">
									<option value="MANUAL">手动输入</option>
									<option value="WEB" selected="selected">获取程序数据选择</option>
								</select>
							</c:if>
						</td>
					</tr>
					<tr style="display:none;">
						<td>WEBUrl：</td>
						<td>
							<input type="text" value="${requestScope.systemLibraryProperty.dataFromWebUrl}" name="dataFromWebUrl" />
						</td>
					</tr>
					<tr>
						<td>后台添加对象时的类型：</td>
						<td>
							<c:if test='${requestScope.systemLibraryProperty.addType=="INPUT"}'>
								<select name="addType" id="addTypeSelect">
									<option value="INPUT">输入框</option>
									<option value="SELECT">下拉</option>
									<option value="RADIO">单选</option>
									<option value="CHECKBOX">多选</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.addType=="SELECT"}'>
								<select name="addType" id="addTypeSelect">
									<option value="INPUT">输入框</option>
									<option value="SELECT" selected="selected">下拉</option>
									<option value="RADIO">单选</option>
									<option value="CHECKBOX">多选</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.addType=="RADIO"}'>
								<select name="addType" id="addTypeSelect">
									<option value="INPUT">输入框</option>
									<option value="SELECT">下拉</option>
									<option value="RADIO" selected="selected">单选</option>
									<option value="CHECKBOX">多选</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.addType=="CHECKBOX"}'>
								<select name="addType" id="addTypeSelect">
									<option value="INPUT">输入框</option>
									<option value="SELECT">下拉</option>
									<option value="RADIO">单选</option>
									<option value="CHECKBOX" selected="selected">多选</option>
								</select>
							</c:if>
						</td>
					</tr>
					<tr style="display:none;">
						<td>前台展示方式：</td>
						<td>
							<c:if test='${requestScope.systemLibraryProperty.showType=="ENUM"}'>
								<select name="showType">
									<option value="ENUM">枚举</option>
									<option value="SELECT">下拉</option>
									<option value="RADIO">单选</option>
									<option value="CHECKBOX">多选</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.showType=="SELECT"}'>
								<select name="showType">
									<option value="ENUM">枚举</option>
									<option value="SELECT" selected="selected">下拉</option>
									<option value="RADIO">单选</option>
									<option value="CHECKBOX">多选</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.showType=="RADIO"}'>
								<select name="showType">
									<option value="ENUM">枚举</option>
									<option value="SELECT">下拉</option>
									<option value="RADIO" selected="selected">单选</option>
									<option value="CHECKBOX">多选</option>
								</select>
							</c:if>
							<c:if test='${requestScope.systemLibraryProperty.showType=="CHECKBOX"}'>
								<select name="showType">
									<option value="ENUM">枚举</option>
									<option value="SELECT">下拉</option>
									<option value="RADIO">单选</option>
									<option value="CHECKBOX" selected="selected">多选</option>
								</select>
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td>
							<input type="button" value="提交" style="padding:5px;" onClick="modProperty()"/>
						</td>
					</tr>
					
					
					
				</tbody>
			</table>
	</div>
</body>
</html>