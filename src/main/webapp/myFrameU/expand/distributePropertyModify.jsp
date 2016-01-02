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
table tbody tr td{padding:8px;border-bottom:1px solid #fff;}
.myAllTrokTr td{background-color:#AAFFEE !important;}
table tr:nth-child(even){background:#FFFFE0;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
function modifyDistributeProperty(c){
	var propertyId=$(c).val();
	var className=$("#classNameDiv").html();
	var dRange=$("#dRangeDiv").html();

	$.ajax( {
		type : "POST",
		url : allWeb+"disProperty/modifyDistributeProperty",
		data : "className="+className+"&dRange="+dRange+"&propertyId="+propertyId,
		success : function(msg) {
			if(msg=="add" || msg=="del"){
				if(msg=="add"){
					//alert("分配成功");	
				}else if(msg=="del"){
					alert("取消分配成功");
				}
			}else{
				alert(msg);
				
			}
			
		}
	})
}

function modifyDistributePropertyValue(c){
	var className=$("#classNameDiv").html();
	var dRange=$("#dRangeDiv").html();
	var propertyValueId=$(c).val();
	$.ajax( {
		type : "POST",
		url : allWeb+"disProperty/modifyDistributePropertyValue",
		data : "className="+className+"&dRange="+dRange+"&propertyValueId="+propertyValueId,
		success : function(msg) {
			if(null==msg || msg==""){
				//alert("分配成功");
			}else{
				alert(msg);
			}
		}
	})
}
</script>
</head>
<body>
<div style="display:none" id="classNameDiv">${requestScope.className}</div>
<div style="display:none" id="dRangeDiv">${requestScope.dRange}</div>
<table cellpadding="0" cellspacing="0">
							<thead style="display:none">
								<tr>
									<td>选择</td>
									<!-- <td>propertyId</td> -->
									<!-- <td>必填</td> -->
									<td>名称</td>
									<td>备选</td>
								</tr>
							</thead>
							<tbody id="propertyTbody">
								<c:forEach var="item" items="${applicationScope.libraryPropertyMap}">  
									<c:set value="${item.value}" var="p" scope="request"></c:set>
									<c:set value="pId_${requestScope.p.id}" var="pId_" scope="request"></c:set>
									<c:set var="myselfPd" value="${requestScope.mySelfPdMap[requestScope.pId_]}" scope="request"></c:set>
									<c:if test='${empty requestScope.myselfPd}'>
										<!-- 空的，说明没有选中这个属性 -->
										
										<c:set var="myAllPd" value="${requestScope.myAllPdMap[requestScope.pId_]}" scope="request"></c:set>
										<c:if test='${!empty requestScope.myAllPd}'>
											<!-- 虽然它自己没有选择这个property，但是它的类选取了 -->
											<tr class="myAllTrokTr">
												<td>
													<input type="checkbox" checked="checked" readonly="readonly" disabled="disabled"  value="${requestScope.p.id}" name="propertyId"  />
												</td>
												<!-- 
												<td>${requestScope.p.id}</td>
												 -->
												<!-- <td>
													<c:if test='${requestScope.myAllPd.must=="NO"}'>
														<select name="must" disabled="disabled">
															<option value="pId${requestScope.p.id}_NO">NO</option>
															<option value="pId${requestScope.p.id}_MUST">MUST</option>
														</select>
													</c:if>
													<c:if test='${requestScope.myAllPd.must=="MUST"}'>
														<select name="must" disabled="disabled">
															<option value="pId${requestScope.p.id}_MUST">MUST</option>
															<option value="pId${requestScope.p.id}_NO">NO</option>
														</select>
													</c:if>
												</td>
												 -->
												<td>${requestScope.p.propertyName}</td>
												<td>
													<c:if test='${requestScope.p.addType!="INPUT"}'>
														<c:forEach var="pv" items="${requestScope.p.sysLibraryPropertyValueSet}">
															
															
															
															<c:set value="${requestScope.myAllPd.propertyValueIdsMap}" var="pdpvIdsMap_1" scope="request"></c:set>
															<c:set value="pvId_${pv.id}" var="pdpvIdsMapKey_1" scope="request"></c:set>
															
															<c:if test='${empty requestScope.pdpvIdsMap_1[requestScope.pdpvIdsMapKey_1]}'>
																<label><input type="checkbox" value="${pv.id}" name="propertyValueIds" onClick="modifyDistributePropertyValue(this)"/>${pv.pvalue}</label>
															</c:if>
															<c:if test='${!empty requestScope.pdpvIdsMap_1[requestScope.pdpvIdsMapKey_1]}'>
																<label><input type="checkbox" disabled="disabled" checked="checked" value="${pv.id}" name="propertyValueIds"  />${pv.pvalue}</label>
															</c:if>
															
															
															
														</c:forEach>
													</c:if>
												</td>
											</tr>
										</c:if>
										<c:if test='${empty requestScope.myAllPd}'>
											<!-- 真正的没有选择这个property -->
											<tr>
												<td>
													<input type="checkbox"  value="${requestScope.p.id}" name="propertyId"  onClick="modifyDistributeProperty(this)"/>
												</td>
												<!-- 
												<td>${requestScope.p.id}</td>
												 -->
												<!-- 
												<td>
													<select name="must">
															<option value="pId${requestScope.p.id}_NO">NO</option>
															<option value="pId${requestScope.p.id}_MUST">MUST</option>
														</select>
												</td>
												 -->
												<td>${requestScope.p.propertyName}</td>
												<td>
													<c:if test='${requestScope.p.addType!="INPUT"}'>
														<c:forEach var="pv" items="${requestScope.p.sysLibraryPropertyValueSet}">
															<label><input type="checkbox" value="${pv.id}" name="propertyValueIds" onClick="modifyDistributePropertyValue(this)"/>${pv.pvalue}</label>
														</c:forEach>
													</c:if>
												</td>
											</tr>
										</c:if>
									</c:if>
									<c:if test='${!empty requestScope.myselfPd}'>
										<!-- 说明选中了这个属性 -->
										<c:set var="myAllPd1" value="${requestScope.myAllPdMap[requestScope.pId_]}" scope="request"></c:set>
										<c:if test='${!empty requestScope.myAllPd1}'>
											<!-- 不但你自己选择了，你的上级所有数据也选择了，这时候你自己不能删除所有数据的属性，只能操作自己多与的值 -->
											<tr class="myAllTrokTr">
												<td>
													<input type="checkbox" disabled="disabled" readonly="readonly" checked="checked" value="${requestScope.p.id}" name="propertyId" />
												</td>
												<!-- 
												<td>${requestScope.p.id}</td>
												 -->
												<!-- 
												<td>
													<c:if test='${requestScope.myselfPd.must=="NO"}'>
														<select name="must" disabled="disabled" >
															<option value="pId${requestScope.p.id}_NO">NO</option>
															<option value="pId${requestScope.p.id}_MUST">MUST</option>
														</select>
													</c:if>
													<c:if test='${requestScope.myselfPd.must=="MUST"}'>
														<select name="must" disabled="disabled">
															<option value="pId${requestScope.p.id}_MUST">MUST</option>
															<option value="pId${requestScope.p.id}_NO">NO</option>
														</select>
													</c:if>
												</td>
												 -->
												<td>${requestScope.p.propertyName}</td>
												<td>
													<c:if test='${requestScope.p.addType!="INPUT"}'>
														<!-- 区分开哪些是由上级继承下来的，这部分不能删除。哪些是自己的，是可以删除的。 -->
														<!-- 所有数据拥有的pv+自己特有的pv=system库中的pv -->
														<c:forEach var="pv" items="${requestScope.p.sysLibraryPropertyValueSet}">
															<c:set value="${requestScope.myselfPd.propertyValueIdsMap}" var="pdpvIdsMap" scope="request"></c:set>
															<c:set value="pvId_${pv.id}" var="pdpvIdsMapKey" scope="request"></c:set>
															
															<c:if test='${empty requestScope.pdpvIdsMap[requestScope.pdpvIdsMapKey]}'>
																<label><input type="checkbox" value="${pv.id}" name="propertyValueIds" onClick="modifyDistributePropertyValue(this)"/>${pv.pvalue}</label>
															</c:if>
															<c:if test='${!empty requestScope.pdpvIdsMap[requestScope.pdpvIdsMapKey]}'>
																<!-- 选择的值=自己特有的+所有数据拥有的 -->
																<!-- 这里判断 -->
																<c:if test='${empty requestScope.myAllPd1.propertyValueIdsMap[requestScope.pdpvIdsMapKey]}'>
																	<!-- 特有的 -->
																	<label><input type="checkbox" checked="checked" value="${pv.id}" name="propertyValueIds"  onClick="modifyDistributePropertyValue(this)"/>${pv.pvalue}</label>
																</c:if>
																<c:if test='${!empty requestScope.myAllPd1.propertyValueIdsMap[requestScope.pdpvIdsMapKey]}'>
																	<!-- 所有数据拥有的 -->
																	<label><input type="checkbox" disabled="disabled" checked="checked" value="${pv.id}" name="propertyValueIds" />${pv.pvalue}</label>
																</c:if>
																
															</c:if>
														</c:forEach>
													</c:if>
												</td>
												
											</tr>
										</c:if>
										<c:if test='${empty requestScope.myAllPd1}'>
											<!-- 你自己选择了，而且你的上级所有数据可没有选择，是完全自己特有的。 -->
											<tr>
												<td>
													<input type="checkbox" checked="checked" value="${requestScope.p.id}" name="propertyId" onClick="modifyDistributeProperty(this)"/>
												</td>
												<!-- 
												<td>${requestScope.p.id}</td>
												 -->
												<!-- 
												<td>
													<c:if test='${requestScope.myselfPd.must=="NO"}'>
														<select name="must">
															<option value="pId${requestScope.p.id}_NO">NO</option>
															<option value="pId${requestScope.p.id}_MUST">MUST</option>
														</select>
													</c:if>
													<c:if test='${requestScope.myselfPd.must=="MUST"}'>
														<select name="must">
															<option value="pId${requestScope.p.id}_MUST">MUST</option>
															<option value="pId${requestScope.p.id}_NO">NO</option>
														</select>
													</c:if>
												</td>
												 -->
												<td>${requestScope.p.propertyName}</td>
												<td>
													<c:if test='${requestScope.p.addType!="INPUT"}'>
														<c:forEach var="pv" items="${requestScope.p.sysLibraryPropertyValueSet}">
															<c:set value="${requestScope.myselfPd.propertyValueIdsMap}" var="pdpvIdsMap" scope="request"></c:set>
															<c:set value="pvId_${pv.id}" var="pdpvIdsMapKey" scope="request"></c:set>
															
															<c:if test='${empty requestScope.pdpvIdsMap[requestScope.pdpvIdsMapKey]}'>
																<label><input type="checkbox" value="${pv.id}" name="propertyValueIds" onClick="modifyDistributePropertyValue(this)"/>${pv.pvalue}</label>
															</c:if>
															<c:if test='${!empty requestScope.pdpvIdsMap[requestScope.pdpvIdsMapKey]}'>
																<label><input type="checkbox" checked="checked" value="${pv.id}" name="propertyValueIds"  onClick="modifyDistributePropertyValue(this)"/>${pv.pvalue}</label>
															</c:if>
														</c:forEach>
													</c:if>
												</td>
												
											</tr>
										</c:if>
									</c:if>
									
								</c:forEach>  
							</tbody>
						</table>
</body>
</html>