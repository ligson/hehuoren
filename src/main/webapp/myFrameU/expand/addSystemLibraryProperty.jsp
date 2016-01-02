<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加系统property属性库</title>
<style type="text/css">
table tbody tr td{padding:3px;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/ui.css"/>
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
function addProperty(){
	var name=$("#propertyNameInput").val();
	var key=$("#propertyKeyInput").val();
	var keyError=$("#propertyKeyInput").attr("error");
	var addType=$("#addTypeSelect").val();
	var valueAlternative=$("#valueAlternativeInput").val();
	var valueDefault=$("#valueDefaultInput").val();
	if(null!=name && name!=""){
		if(null!=key && key!=""){
			if(keyError=="error"){
				alert("请输入唯一的属性key");
			}else{
				if(addType!="INPUT"){
					if(null==valueAlternative || valueAlternative==""){
						alert("非输入属性，请输入可选值");
					}else{
						$.ajax( {
							type : "POST",
							url : allWeb+"sysProperty/addProperty?propertyName="+
									name+"&propertyKey="+key+"&dataType=STRING&dataFrom=MANUAL&addType="+
									addType+"&showType=ENUM&valueAlternative="+valueAlternative+"&valueDefault="+valueDefault
									+"&search=false&list=false&queryArg=true&must=false",
							success : function(msg) {
								if(msg!=null && msg!=""){
									myAlert(200,130,"失败!",msg,"error");
								}else{
									myAlert(200,130,"成功!","追加系统属性成功","ok");
								}
							}
						})
					}
				}
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
	<h1 align="center">添加系统property属性库</h1>
	<div style="width:600px;margin:0 auto;background-color:#eee;padding:20px;">
			<table>
				<tbody>
					<tr>
						<td>属性名：</td>
						<td>
							<input type="text" value="" name="propertyName" id="propertyNameInput" onblur="propertyNameNull(this)"/>
						</td>
					</tr>
					<tr>
						<td>属性key：</td>
						<td>
							<input type="text" value="" name="propertyKey" id="propertyKeyInput" onblur="verUniqueKey(this)"/>
						</td>
					</tr>
					<tr style="display:none;">
						<td>数据类型：</td>
						<td>
							<select name="dataType">
								<option value="">请选择数据类型</option>
								<option value="INT">int</option>
								<option value="LONG">long</option>
								<option value="FLOAT">float</option>
								<option value="DOUBLE">double</option>
								<option value="STRING">string</option>
								<option value="DATE">date</option>
								<option value="BOOLEAN">boolean</option>
							</select>
						</td>
					</tr>
					<tr style="display:none;">
						<td>数据来源：</td>
						<td>
							<select name="dataFrom">
								<option value="MANUAL">手动输入</option>
								<option value="WEB">获取程序数据选择</option>
							</select>
						</td>
					</tr>
					<tr style="display:none;">
						<td>WEBUrl：</td>
						<td>
							<input type="text" value="" name="dataFromWebUrl"/>
						</td>
					</tr>
					<tr>
						<td>后台添加对象时的类型：</td>
						<td>
							<select name="addType" id="addTypeSelect">
								<option value="SELECT">请选择添加对象使用的展示类型</option>
								<option value="INPUT">输入框</option>
								<option value="SELECT">下拉</option>
								<option value="RADIO">单选</option>
								<option value="CHECKBOX">多选</option>
							</select>
						</td>
					</tr>
					<tr style="display:none;">
						<td>前台展示方式：</td>
						<td>
							<select name="showType" id="showTypeSelect">
								<option value="">请选择前台展示方式</option>
								<option value="ENUM">枚举</option>
								<option value="SELECT">下拉</option>
								<option value="RADIO">单选</option>
								<option value="CHECKBOX">多选</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>选项：</td>
						<td>
							<span><input type="text" value="" name="valueAlternative" id="valueAlternativeInput"/></span>
							<span>多值用英文逗号隔开</span>
						</td>
					</tr>
					<tr>
						<td>默认值：</td>
						<td>
							<span><input type="text" value="" name="valueDefault" id="valueDefaultInput"/></span>
							<span>多值用英文逗号隔开</span>
						</td>
					</tr>
					<tr style="display:none;">
						<td>是否参与搜索 ：</td>
						<td>
							<select name="search">
								<option value="false">不参与</option>
								<option value="true">参与</option>
							</select>
						</td>
					</tr>
					<tr style="display:none;">
						<td>是否必须填写 ：</td>
						<td>
							<select name="must">
								<option value="false">不需要</option>
								<option value="true">必须填</option>
							</select>
						</td>
					</tr>
					
					<tr style="display:none;">
						<td>是否参与检索 ：</td>
						<td>
							<select name="queryArg">
								<option value="false">不参与</option>
								<option value="true">参与</option>
							</select>
						</td>
					</tr>
					
					<tr style="display:none;">
						<td>是否展示在list中 ：</td>
						<td>
							<select name="list">
								<option value="false">不参与</option>
								<option value="true">参与</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td>
							<input type="button" value="提交" style="padding:5px;" onClick="addProperty()"/>
						</td>
					</tr>
					
					
					
				</tbody>
			</table>
	</div>
</body>
</html>