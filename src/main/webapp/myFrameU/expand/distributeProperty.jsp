<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>分配属性</title>
<style type="text/css">
table{width:100%}
table thead tr td{background-color:#ccc;padding:5px;font-weight: bold;border-right:1px solid #ddd;}
table tbody tr td{padding:3px;border-bottom:1px solid #fff;}
</style>
<script type="text/javascript" src="http://www.024lm.com/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
function rangeSelect(s){
	var v=$(s).val();
	if(v=="all"){
		$("#object1Tr").hide();
	}else{
		$("#object1Tr").show();
	}
}
function findFields(i){
	var v=$(i).val();
	if(null!=v && v!=""){
		
		$.ajax( {
			type : "POST",
			url : "http://localhost:8080/yishupaipai/disProperty/findClassFields",
			data : "className="+v,
			success : function(msg) {
				$("#object1TrSPAN").html(msg);
			}
		})
	}
}
</script>
</head>
<body>
	<h1 align="center">分配属性</h1>
	<div style="width:100%;margin:0 auto;background-color:#eee;padding:20px;">
		<form action="<%=request.getContextPath()%>/disProperty/addDistributeProperty" method="post">
			<table>
			<tbody>
				
				<tr>
					<td>被分配className</td>
					<td>
						<input type="text" value="" name="className" id="fenpei_className" onblur="findFields(this)"/>
					</td>
				</tr>
				
				<tr>
					<td>范围</td>
					<td>
						<select onchange="rangeSelect(this)">
							<option value="all">该类所有数据</option>
							<option value="bufen">该类某一部分数据</option>
						</select>
					</td>
				</tr>
				<tr id="object1Tr" style="display:none;">
					<td></td>
					<td id="">
						<span id="object1TrSPAN"></span>
						<span>具体数值<input type="text" value="" name="dRange_value" /></span>
					</td>
				</tr>
				
				<tr>
					<td>选择属性和属性值</td>
					<td>
						<table cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<td>选择</td>
									<td>必填</td>
									<td>tree传递</td>
									<td>备选</td>
									<td>名称</td>
									<td>Key</td>
									<td>数据类型</td>
									<td>数据来源</td>
									<td>WebUrl</td>
									<td>addType</td>
									<td>showType</td>
									
								</tr>
							</thead>
							<tbody id="propertyTbody">
								<c:forEach var="item" items="${applicationScope.libraryPropertyMap}">  
									<c:set value="${item.value}" var="p" scope="request"></c:set>
									<tr>
										<td>
											<input type="checkbox" value="${requestScope.p.id}" name="propertyId"/>
										</td>
										<td>
											<select name="must">
												<option value="pId${requestScope.p.id}_NO">NO</option>
												<option value="pId${requestScope.p.id}_MUST">MUST</option>
											</select>
										</td>
										<td>
											<select name="transfer">
												<option value="pId${requestScope.p.id}_SELF">SELF</option>
												<option value="pId${requestScope.p.id}_SONS">SONS</option>
												<option value="pId${requestScope.p.id}_SONSSONS">SONSSONS</option>
											</select>
										</td>
										<td>
											<c:if test='${requestScope.p.addType!="INPUT"}'>
												<c:forEach var="pv" items="${requestScope.p.sysLibraryPropertyValueSet}">
													<label>
														<input type="checkbox" value="pId${requestScope.p.id}_pvId${pv.id}" name="propertyValueIds"/>${pv.pvalue}
													</label>
												</c:forEach>
											</c:if>
										</td>
										<td>${requestScope.p.propertyName}</td>
										<td>${requestScope.p.propertyKey}</td>
										<td>${requestScope.p.dataType}</td>
										<td>${requestScope.p.dataFrom}</td>
										<td>${requestScope.p.dataFromWebUrl}</td>
										<td>${requestScope.p.addType}</td>
										<td>${requestScope.p.showType}</td>
										
										
									</tr>
								</c:forEach>  
							</tbody>
						</table>
						
					</td>
				</tr>
				<tr>
					<td>提交</td>
					<td>
						<input type="submit" value="提交" />
					</td>
				</tr>
			</tbody>
		</table>
		</form>
		
		
	</div>
</body>
</html>