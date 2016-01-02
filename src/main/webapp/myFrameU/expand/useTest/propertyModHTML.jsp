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
<script type="text/javascript">
function isNull(s){
	if(null!=s && s!="" && s!="undefined"){
		return true;
	}else{
		return false;
	}
}
//// alert(r[i].value+","+r[i].nextSibling.nodeValue);
function getCheckboxValues(name){
	var result="";
    var r=document.getElementsByName(name); 
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
        	result=result+r[i].value+",";
       }
    }
    var resultLen=result.length;
    result=result.substring(0,resultLen-1);
    if(result.substring(0,1) == ","){
    	var resultLen1=result.length;
    	result=result.substring(1,resultLen1);
    }
    
    //alert("js获取checkbox选中的值："+result);
    return result;
}


function saveResult(i){
	//获取节点类型，如INPUT,SELECT
	var nt=i.nodeName;
	//获取节点的值
	var v=i.value;
	//获取节点所对应的propertyId
	var propertyId=i.getAttribute("pId");
	//alert(nt+"=="+v+"=="+propertyId);
	if(isNull(propertyId)){
		//根据propertyId找到对应的【tempSaveResults_pId${property.id}】Input
		var saveResultInput=document.getElementById("tempSaveResults_pId"+propertyId);
		var pvIdOrPvalue="";
		var result="";
		var resultType="";
		if(nt=="INPUT"){
			var inputName=i.getAttribute("name");
			var inputType=i.getAttribute("type");
			if(inputType=="text"){
				pvIdOrPvalue=v;
				resultType="pvalue";
			}else if(inputType=="radio"){
				pvIdOrPvalue=v;
				resultType="pvId";
			}else if(inputType=="checkbox"){
				resultType="pvId";
				if(isNull(inputName)){
					pvIdOrPvalue=getCheckboxValues(inputName);
				}
			}
		}else if(nt=="SELECT"){
			resultType="pvId";
			pvIdOrPvalue=v;
		}
		
		if(isNull(pvIdOrPvalue) && isNull(resultType)){
			result="{'pId':'"+propertyId+"','"+resultType+"':'"+pvIdOrPvalue+"'}";
			saveResultInput.value=result;
			//alert(saveResultInput.value);
		}
	}
	//modProduct();
}
//组装所有的tempSaveResult->propertyValues（包括pvId和pvalue）
function getAllPropertyValues(){
	var allResultInputs=document.getElementsByName("tempSaveResults");
	var len=allResultInputs.length;
	var currentInput;
	var results="[";
	var value;
	for(var i=0;i<len;i++){
		currentInput=allResultInputs[i];
		value=currentInput.value;
		if(value!=""){
			results=results+value+",";
		}
		results=results+value+",";
	}
	 var resultLen=results.length;
	 results=results.substring(0,resultLen-1);
	 results=results+"]";
	return results;
	//alert(results);
}
function startFirst(s1,s2){
    if(s1.substring(0,1) == s2){
    	return true;
    }
    return false;
}
</script>
</head>
<body>
 <%//获得includeAction.jsp传来的值:
    String propertyDistributeListName = request.getParameter("propertyDistributeListName");
 	List<PropertyDistribute> list=(List<PropertyDistribute>)request.getAttribute(propertyDistributeListName);
 	request.setAttribute("propertyDistributeList", list);
%>

	
							<c:forEach var="pd" items="${requestScope.propertyDistributeList}">
								<c:set var="propertyId" value="${pd.propertyId}" scope="request"></c:set>
								<c:set var="pId_str" value="pId_${propertyId}" scope="request"></c:set>
								<c:set var="property" value="${applicationScope.libraryPropertyMap[requestScope.pId_str]}" scope="request"></c:set>
								<c:if test='${!empty property}'>
									<c:set var="selectedPValue" value="${requestScope.propertyValuesMap[requestScope.pId_str]}" scope="request"></c:set>
									<c:if test='${property.addType=="INPUT"}'>
										<input type="hidden" value="{'pId':'${property.id}','pvalue':'${requestScope.selectedPValue}'}" id="tempSaveResults_pId${property.id}" name="tempSaveResults"/>
										<div class="propertyDiv">
											<div class="name">${property.id}-${property.propertyName}</div>
											<div class="value">
												<input type="text" value="${requestScope.selectedPValue}" name="${property.propertyKey}" pId="${property.id}" onblur="saveResult(this)"/>
											</div>
										</div>
									</c:if>
									<c:if test='${property.addType=="SELECT"}'>
										
										<div class="propertyDiv">
											<div class="name">${property.id}-${property.propertyName}</div>
											<div class="value">
												<c:if test='${empty requestScope.selectedPValue}'>
													<select name="${property.propertyKey}"  pId="${property.id}" onchange="saveResult(this)">
														<option value="">请选择${property.propertyName}</option>
														<c:forEach var="pvId" items="${pd.propertyValueIdsMap}">
															 <c:set var="pv" value="${applicationScope.libraryPropertyValueMap[pvId.key]}" scope="request"></c:set>
															 <option value="${pv.id}">${pv.pvalue}</option>
														</c:forEach> 
													</select>
													<input type="hidden" value="" id="tempSaveResults_pId${property.id}" name="tempSaveResults"/>
												</c:if>
												<c:if test='${!empty requestScope.selectedPValue}'>
													<select name="${property.propertyKey}"  pId="${property.id}" onchange="saveResult(this)">
													<c:forEach var="pvId" items="${pd.propertyValueIdsMap}">
														 <c:set var="pv" value="${applicationScope.libraryPropertyValueMap[pvId.key]}" scope="request"></c:set>
														 <c:if test='${pv.pvalue==requestScope.selectedPValue}'>
														 	<c:set var="currentPvId" value="${pv.id}" scope="request"></c:set>
														 	<option value="${pv.id}" selected="selected">${pv.pvalue}</option>
														 </c:if>
														 <c:if test='${pv.pvalue!=requestScope.selectedPValue}'>
														 	<option value="${pv.id}">${pv.pvalue}</option>
														 </c:if>
													</c:forEach> 
													</select>
													<input type="hidden" value="{'pId':'${property.id}','pvId':'${requestScope.currentPvId}'}" id="tempSaveResults_pId${property.id}" name="tempSaveResults"/>
												</c:if>
												
											</div>
										</div>
									</c:if>
									<c:if test='${property.addType=="CHECKBOX"}'>
										<div class="propertyDiv">
											<div class="name">${property.id}-${property.propertyName}</div>
											<div class="value">
												<c:set var="currentPvId" value="" scope="request"></c:set>
												<c:forEach var="pvId" items="${pd.propertyValueIdsMap}">
													 <c:set var="pv" value="${applicationScope.libraryPropertyValueMap[pvId.key]}" scope="request"></c:set>
													 <c:set var="selectedPValue_preSuf" value=",${requestScope.selectedPValue}," scope="request"></c:set>
													 <c:set var="pvvalue_preSuf" value=",${pv.pvalue},"></c:set>
													 <!-- 如果selectedPValue_preSuf包含pvvalue_preSuf，说明选中 -->
													 <c:if test="${fn:contains(selectedPValue_preSuf, pvvalue_preSuf)}">
													 	<c:if test='${requestScope.currentPvId!=""}'>
													 		<c:set var="currentPvId" value="${requestScope.currentPvId},${pv.id}" scope="request"></c:set>
													 	</c:if>
													 	<c:if test='${requestScope.currentPvId==""}'>
													 		<c:set var="currentPvId" value="${pv.id}" scope="request"></c:set>
													 	</c:if>
													 	<label><input type="checkbox" checked="checked" name="${property.propertyKey}" value="${pv.id}"  pId="${property.id}"  onchange="saveResult(this)"/>${pv.pvalue}</label>
													 </c:if>
													  <c:if test="${!fn:contains(selectedPValue_preSuf, pvvalue_preSuf)}">
													 	<label><input type="checkbox" name="${property.propertyKey}" value="${pv.id}"  pId="${property.id}"  onchange="saveResult(this)"/>${pv.pvalue}</label>
													 </c:if>													 
												</c:forEach>
												<input type="hidden" value="{'pId':'${property.id}','pvId':'${requestScope.currentPvId}'}" id="tempSaveResults_pId${property.id}" name="tempSaveResults"/>
											</div>
										</div>
									</c:if>
									<c:if test='${property.addType=="RADIO"}'>
										<div class="propertyDiv">
											<div class="name">${property.id}-${property.propertyName}</div>
											<div class="value">
												<c:forEach var="pvId" items="${pd.propertyValueIdsMap}">
													 <c:set var="pv" value="${applicationScope.libraryPropertyValueMap[pvId.key]}" scope="request"></c:set>
													 <c:if test='${pv.pvalue==requestScope.selectedPValue}'>
													 	<c:set var="currentPvId" value="${pv.id}" scope="request"></c:set>
													 	<label><input type="radio" checked="checked" name="${property.propertyKey}" value="${pv.id}"  pId="${property.id}"  onchange="saveResult(this)"/>${pv.pvalue}</label>
													 </c:if>
													 <c:if test='${pv.pvalue!=requestScope.selectedPValue}'>
													 	<label><input type="radio" name="${property.propertyKey}" value="${pv.id}"  pId="${property.id}"  onchange="saveResult(this)"/>${pv.pvalue}</label>
													 </c:if>
												</c:forEach>
												<input type="hidden" value="{'pId':'${property.id}','pvId':'${requestScope.currentPvId}'}" id="tempSaveResults_pId${property.id}" name="tempSaveResults"/>
											</div>
										</div>
									</c:if>									
								</c:if>
								
								
							</c:forEach>
</body>
</html>