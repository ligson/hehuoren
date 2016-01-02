<%@ page language="java" import="java.util.List" import="myFrameU.expand.distribution.entity.PropertyDistribute" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>添加记录时展示所需要填写的属性</title>
<style type="text/css">
.propertyDiv{height:30px;float:left;margin-right:10px;background-color:#fff;border:1px solid #ddd;padding-left:0px;padding-right:0px;
margin:5px;
}
.propertyDiv .name{float:left;height:100%;line-height: 30px;background:blue;color:#fff;font-weight: bold;padding-left:3px;padding-right:3px;}
.propertyDiv .value{float:left;line-height: 30px;height:100%;padding-left:0px;}
.propertyDiv .value select{border:none;border-left:1px solid #ddd;}
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
	}
	 var resultLen=results.length;
	 results=results.substring(0,resultLen-1);
	 results=results+"]";
	return results;
	//alert(results);
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
									<input type="hidden" value="" id="tempSaveResults_pId${property.id}" name="tempSaveResults"/>
									<c:if test='${property.addType=="INPUT"}'>
										<div class="propertyDiv">
											<div class="name">${property.propertyName}</div>
											<div class="value">
												<input type="text" value="${property.valueDefault}" name="${property.propertyKey}" pId="${property.id}" onblur="saveResult(this)"/>
											</div>
										</div>
									</c:if>
									<c:if test='${property.addType=="SELECT"}'>
										<div class="propertyDiv">
											<div class="name">${property.propertyName}</div>
											<div class="value">
												<select name="${property.propertyKey}"  pId="${property.id}" onchange="saveResult(this)">
													<option value="">请选择${property.propertyName}</option>
													<c:forEach var="pvId" items="${pd.propertyValueIdsMap}">
														 <c:set var="pv" value="${applicationScope.libraryPropertyValueMap[pvId.key]}" scope="request"></c:set>
														 <option value="${pv.id}">${pv.pvalue}</option>
													</c:forEach>  
												</select>
											</div>
										</div>
									</c:if>
									<c:if test='${property.addType=="CHECKBOX"}'>
										<div class="propertyDiv">
											<div class="name">${property.propertyName}</div>
											<div class="value">
												<c:forEach var="pvId" items="${pd.propertyValueIdsMap}">
													 <c:set var="pv" value="${applicationScope.libraryPropertyValueMap[pvId.key]}" scope="request"></c:set>
													 <label>
													 	<input type="checkbox" name="${property.propertyKey}" value="${pv.id}"  pId="${property.id}"  onchange="saveResult(this)"/>${pv.pvalue}
													 </label>													 
												</c:forEach>
											</div>
										</div>
									</c:if>
									<c:if test='${property.addType=="RADIO"}'>
										<div class="propertyDiv">
											<div class="name">${property.propertyName}</div>
											<div class="value">
												<c:forEach var="pvId" items="${pd.propertyValueIdsMap}">
													 <c:set var="pv" value="${applicationScope.libraryPropertyValueMap[pvId.key]}" scope="request"></c:set>
													 <label><input type="radio" name="${property.propertyKey}" value="${pv.id}"  pId="${property.id}"  onchange="saveResult(this)"/>${pv.pvalue}</label>
												</c:forEach>
											</div>
										</div>
									</c:if>									
								</c:if>
								
								
							</c:forEach>
</body>
</html>
