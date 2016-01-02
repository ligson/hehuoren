<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>productList</title>
<style type="text/css">
#outer{width:100%;height:auto;padding:10px;background:#eee;margin:0 auto;}
#outer table{width:100%;}
#outer table thead tr td{background:#000;color:#fff;height:30px;line-height: 30px;}
#outer table tr td{padding:5px;border-right:1px solid #fff;border-bottom:1px solid #fff;}
.propertyDiv{height:30px;float:left;margin-right:10px;background-color:#fff;border:1px solid #ddd;padding-left:0px;padding-right:8px;}
.propertyDiv .name{float:left;height:100%;line-height: 30px;background:blue;color:#fff;font-weight: bold;padding-left:3px;padding-right:3px;}
.propertyDiv .value{float:left;line-height: 30px;height:100%;}
.propertyDiv .value select{width:100%;height:100%}
#queryArgsOuterDIV{width:100%;height:100px;border:1px solid #000;background-color:#fff;}
#dRangePDDiv{height:50px;width:100%;}



a.current{background:red;color:#fff;}

</style>
<script type="text/javascript" src="http://localhost:8080/yishupaipai/myFrameU/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/yishupaipai/myFrameU/js/json2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	initArgsProperty();
});
function getPropertyDistributes(s){
	var productTypeId=$(s).val();
	var dRange="{'key':'productTypeId','value':'"+productTypeId+"'}";
	var className="myFrameU.expand.use.test.entity.TestProduct";
	$.ajax( {
		type : "POST",
		url : "http://localhost:8080/yishupaipai/expand/test/findDistributePropertys_productTypeId",
		data : "className="+className+"&dRange="+dRange,
		success : function(msg) {
			$("#dRangePds").html(msg);
		}
	})
}
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
//选择每个属性，要进行查询
function queryArgFind(a){
	var propertyDiv=$(a).parents(".propertyDiv");
	var tagName=a.tagName;
	if(tagName=="A"){
		propertyDiv.find("a").each(function(){
			//先都取消选中
			$(this).removeClass("current");
		});
		$(a).addClass("current");
		//修改tempsave里的值
		var pId=$(a).attr("pId");
		var pvId=$(a).attr("pvId");
		var myTempsave=$("#tempSaveInput_pId"+pId);
		if(pvId==undefined){
			//选择了全部
			myTempsave.val("");
		}else{
			var curQuery="{'pId':'"+pId+"','pvId':'"+pvId+"'}";
			myTempsave.val(curQuery);
		}
		
	}else if(tagName=="SELECT"){
		var pId=$(a).attr("pId");
		var myTempsave=$("#tempSaveInput_pId"+pId);
		var pvId=$(a).val();
		if(pvId==""){
			myTempsave.val("");
		}else{
			var curQuery="{'pId':'"+pId+"','pvId':'"+pvId+"'}";
			myTempsave.val(curQuery);
		}
	}else if(tagName=="INPUT"){
		var inputType=$(a).attr("type");
		if(inputType=="radio"){
			var pId=$(a).attr("pId");
			var myTempsave=$("#tempSaveInput_pId"+pId);
			var pvId=$(a).val();
			if(pvId==""){
				myTempsave.val("");
			}else{
				var curQuery="{'pId':'"+pId+"','pvId':'"+pvId+"'}";
				myTempsave.val(curQuery);
			}
		}else if(inputType=="checkbox"){
			var pId=$(a).attr("pId");
			var myTempsave=$("#tempSaveInput_pId"+pId);
			var curQuery = getCheckboxValues($(a).attr("name"));
			curQuery="{'pId':'"+pId+"','pvId':'"+curQuery+"'}";
			myTempsave.val(curQuery);
		}
		
	}
	
	var queryArgs=createQueryArgsProperty();
	if(queryArgs!=""){
		queryArgs="["+queryArgs+"]";
	}
	//alert(queryArgs);
	
	var form=$("<form>");
	form.attr("method","post");
	form.attr("action","http://localhost:8080/yishupaipai/expand/test/findProductListQueryArgs?queryArgsProperty="+queryArgs);
	form.submit();

	
	
}

//遍历
function createQueryArgsProperty(a){
	var queryArgs="";
	var i=0;
	$(".tempSaveInput").each(function(){
		var cur=$(this);
		var val=cur.val();
		if(val!=""){
			i++;
			if(i==1){
				queryArgs=val;
			}else{
				queryArgs=queryArgs+","+val;
			}
		}
	});
	return queryArgs;
}


//页面刚加载的时候，读取queryArgsProperty，使组合查询能保留
function initArgsProperty(){
	var queryArgsProperty=$("#queryArgsPropertyInput").val();
	//分配每个tempsave
	//current class
	//[{'pId':''3','pvId':'17'}]
	//[{'pId':''3','pvId':'17'}]
	var jos = eval(queryArgsProperty); 
	
	//alert(jos.length);
	var len=jos.length;
	var jo=null;
	for(var i=0;i<len;i++){
		jo=jos[i];
		//tempSaveInput_pId${property.id}
		var tempSaveInputId="tempSaveInput_pId"+jo.pId;
		var tempSaveInput=$("#"+tempSaveInputId);
		var aorinput=$("#queryArgsPvID_"+jo.pvId);
		var tagName=aorinput[0].tagName;
		if(tagName=="A"){
			tempSaveInput.val("{'pId':'"+jo.pId+"','pvId':'"+jo.pvId+"'}");
			aorinput.addClass("current");
		}else if(tagName=="OPTION"){
			tempSaveInput.val("{'pId':'"+jo.pId+"','pvId':'"+jo.pvId+"'}");
			aorinput.parent("select").val(jo.pvId);
		}else if(tagName=="INPUT"){
			var inputType=$(aorinput).attr("type");
			if(inputType=="radio"){
				tempSaveInput.val("{'pId':'"+jo.pId+"','pvId':'"+jo.pvId+"'}");
				aorinput.attr("checked","checked");
			}else if(inputType=="checkbox"){
				var pvIds=jo.pvId;
				var s = pvIds.indexOf(",");
				if(s>0){
			    	//多个值
			    	var array=pvIds.split(",");
			    	var len1=array.length;
			    	var pvId1;
			    	var que;
			    	for(var j=0;j<len1;j++){
			    		pvId1=array[j];
			    		var aorinput1=$("#queryArgsPvID_"+pvId1);
			    		aorinput1.attr("checked","checked");
			    	}
			    	tempSaveInput.val("{'pId':'"+jo.pId+"','pvId':'"+jo.pvId+"'}");
			    }else{
			    	tempSaveInput.val("{'pId':'"+jo.pId+"','pvId':'"+jo.pvId+"'}");
			    	aorinput.attr("checked","checked");
			    }
				
			}
		}
	}
}





</script>

</head>
<body>
	<input type="hidden" value="${requestScope.queryArgsProperty}" id="queryArgsPropertyInput"/>
	<div id="outer">
		<div id="queryArgsOuterDIV">
			<!-- 加载上ALL -->
			<c:forEach var="pd" items="${requestScope.propertyDistributeList_all}">
				<c:set var="propertyId_str" value="pId_${pd.propertyId}" scope="request"></c:set>
				<c:set var="property" value="${applicationScope.libraryPropertyMap[requestScope.propertyId_str]}"></c:set>
				<c:if test='${property.addType!="INPUT"}'>
					<div class="propertyDiv" id="propertyDivId_${property.id}">
						<input type="text" value="" class="tempSaveInput" id="tempSaveInput_pId${property.id}"/>
						<div class="name">${property.propertyName}</div>
						<div class="value">
							<c:if test='${property.showType=="ENUM"}'>
								<a onClick="queryArgFind(this)" href="javascript:void(0);" pId="${property.id}">全部</a>   
								<c:forEach var="item" items="${pd.propertyValueIdsMap}">  
								 <c:set var="libraryPV" value="${applicationScope.libraryPropertyValueMap[item.key]}"></c:set>
								 	<a onClick="queryArgFind(this)" href="javascript:void(0);" 
								 	pvId="${libraryPV.id}" pId="${property.id}" 
								 	id="queryArgsPvID_${libraryPV.id}">${libraryPV.pvalue}</a>   
								</c:forEach>  
							</c:if>
							<c:if test='${property.showType=="SELECT"}'>
								<select name="${property.propertyKey}" onchange="queryArgFind(this)"
								 pId="${property.id}" 
								id="queryArgsPvID_${libraryPV.id}"
								>
									<option value="">请选择${property.propertyName}</option>
									<c:forEach var="item" items="${pd.propertyValueIdsMap}">  
										 <c:set var="libraryPV" value="${applicationScope.libraryPropertyValueMap[item.key]}"></c:set>
										 <option value="${libraryPV.id}" id="queryArgsPvID_${libraryPV.id}">${libraryPV.pvalue}</option> 
									</c:forEach>  
								</select>
							</c:if>
							<c:if test='${property.showType=="RADIO"}'>
								<c:forEach var="item" items="${pd.propertyValueIdsMap}">  
									<c:set var="libraryPV" value="${applicationScope.libraryPropertyValueMap[item.key]}"></c:set>
									<input onchange="queryArgFind(this)" pId="${property.id}" pvId="${libraryPV.id}" id="queryArgsPvID_${libraryPV.id}" type="radio" name="${property.propertyKey}" value="${libraryPV.id}"/> ${libraryPV.pvalue}
								</c:forEach>  
							</c:if>
							<c:if test='${property.showType=="CHECKBOX"}'>
								<c:forEach var="item" items="${pd.propertyValueIdsMap}">  
									<c:set var="libraryPV" value="${applicationScope.libraryPropertyValueMap[item.key]}"></c:set>
									<input onchange="queryArgFind(this)" pId="${property.id}" pvId="${libraryPV.id}" id="queryArgsPvID_${libraryPV.id}"  type="checkbox" name="${property.propertyKey}" value="${libraryPV.id}"/> ${libraryPV.pvalue}
								</c:forEach>  
							</c:if>
						</div>
					</div>
				</c:if>
			</c:forEach>
			<div id="dRangePDDiv">
				<div style="float:left;">
					<select name="productTypeId" onchange="getPropertyDistributes(this)">
								<option value="">选择类型</option>
								<c:forEach var="pt" items="${applicationScope.expandTest_testProductTypeList}">
									<option value="${pt.id}">${pt.id}-${pt.name}</option>
								</c:forEach>
					</select>
				</div>
				<div style="float:left;" id="dRangePds">
					
				</div>
			</div>
			
		</div>
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>ID</td>
					<td>产品名</td>
					<td>类型</td>
					<td>范围为ALL的数据</td>
					<td>范围特有的数据</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${requestScope.productList}">
					<tr>
						<td>${p.id}</td>
						<td>${p.name }</td>
						<td>${p.productTypeId}</td>
						<td>
							<c:forEach var="pd" items="${requestScope.propertyDistributeList_all}">
								<c:set var="propertyId_str" value="pId_${pd.propertyId}" scope="request"></c:set>
								<c:set var="property" value="${applicationScope.libraryPropertyMap[requestScope.propertyId_str]}"></c:set>
								<div class="propertyDiv">
									<div class="name">${property.propertyName}</div>
									<div class="value">
										${p.propertyValuesMap[requestScope.propertyId_str]}
									</div>
								</div>
							</c:forEach>
						</td>
						<td>
							<c:set var="productTypeIdStr" value="productTypeId_${p.productTypeId}" scope="request"></c:set>
							<c:set var="propertyDistributeList_dRangePT" value="${requestScope.propertyDistributeList_dRangePT_Map[requestScope.productTypeIdStr]}" scope="request"></c:set>
							<c:forEach var="pd" items="${requestScope.propertyDistributeList_dRangePT}">
								<c:set var="propertyId_str" value="pId_${pd.propertyId}" scope="request"></c:set>
								<c:set var="property" value="${applicationScope.libraryPropertyMap[requestScope.propertyId_str]}"></c:set>
								<div class="propertyDiv">
									<div class="name">${property.propertyName}</div>
									<div class="value">
										${p.propertyValuesMap[requestScope.propertyId_str]}
									</div>
								</div>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>