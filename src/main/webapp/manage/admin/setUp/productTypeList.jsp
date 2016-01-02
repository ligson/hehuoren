<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/ui.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/alert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/list.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<title>添加产品种类 - 爱尔特合伙人后台</title>
<style type="text/css">
#ptLeft{width:250px;}
#ptLeft .content{height:659px;padding:0px;overflow-y: scroll;}
#ptLeft .content a.selected{background-color:#eee;}
#ptRight{float:left;width:700px;border:1px solid #e7e7eb;min-height:700px;margin-top:10px;border-left:none;}
#addPTBox table td{padding:5px;}
.wtc{color:red;float:right !important;}
</style>
<script type="text/javascript">
function addPtHref(a){
	window.location.href=allWeb+"admin/productType/finds?forwardPage=admin/setUp/productTypeAdd";
}
function delPt(a){
	var ptId=$(a).attr("ptId");
	var isRoot=$(a).attr("isRoot");
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/productType/remove?id="+ptId,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","删除成功","ok");
				if(isRoot=="yes"){
					$(a).parents(".everyRootPt").remove();
				}else{
					$(a).parents(".everyTwoPt").remove();
				}
			}
		}
	})
}
function findId(a){
	var ptId=$(a).attr("ptId");
	window.location.href=allWeb+"admin/productType/findById?id="+ptId;
}
function findDis(a){
	var dRange=$(a).attr("dRange");
	$.ajax( {
		type : "POST",
		url : allWeb+"disProperty/findDistribute?className=myFrameU.product.entity.Product&dRange="+dRange,
		beforeSend:function(){
			$("#rightBPt").html($(a).attr("ptName"));
			$("#myContent").html("");
			$("#ptLeft").find("a").removeClass("selected");
			$(a).addClass("selected");
		},
		success : function(msg) {
			$("#myContent").html(msg);
		}
	})
}
</script>
</head>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="setup-system" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>系统设置</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA3" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="bodyRC_tip">
						<p class="desc">
							为保证系统正常运行，请慎重修改产品分类以及分类下的产品扩展属性
						</p>
					</div>
					<div id="mainContent">
						
						<div class="quyukuang treeBox" id="ptLeft">
							<div class="title">
						        <h3>产品分类</h3>
						        <span><i class="add1" id="addRootI" onClick="addPtHref(this);"></i></span>
						    </div>
						    <div class="content">
						    	<ul>
						    		<c:forEach var="pt" items="${requestScope.app_productTypeList}">
						    			<c:if test='${pt.isROOT=="0"}'>
							    			<li class="everyRootPt">
								    			<a href="javascript:void(0);" onClick="findDis(this);" dRange="{'key':'productTypeId','value':'${pt.id}'}" ptName="${pt.name}">
								    				<span class="ptNamespan">${pt.name}<span class="wtc" style="display:none;">(${pt.webTicheng}%)</span></span>
								    				<span class="ptDelS"><i class="del" onClick="delPt(this);" ptId="${pt.id}" isRoot="yes"></i></span>
								    				<span class="ptModS"><i class="mod"  onClick="findId(this)" ptId="${pt.id}"></i></span>
								    			</a>
								    			<ul>
								    				<c:forEach var="pt2" items="${pt.childs}">
								    					<li class="everyTwoPt">
									    					<a  href="javascript:void(0);"  onClick="findDis(this);" dRange="{'key':'productTypeId','value':'${pt2.id}'}" ptName="${pt2.name}">
									    						<span class="quans"><i clas="quan"></i></span>
											    				<span class="ptNamespan1">${pt2.name}<span class="wtc" style="display:none;">(${pt2.webTicheng}%)</span></span>
											    				<span class="ptDelS"><i class="del"  onClick="delPt(this);" ptId="${pt2.id}" isRoot="no"></i></span>
											    				<span class="ptModS"><i class="mod"   onClick="findId(this)" ptId="${pt2.id}" ></i></span>
											    			</a>
									    				</li>
								    				</c:forEach>
								    			</ul>
								    		</li>
						    			</c:if>
						    		</c:forEach>
						    	</ul>
						    </div>
						</div>
						<div class="quyukuang" id="ptRight">
							<div class="title">
						        <h3>为[<b style="color:red;" id="rightBPt"></b>]下的产品分配属性</h3>
						        <span style="float:right;">
						        	<a href="<%=request.getContextPath()%>/sysProperty/findAllPropertys" target="_blank">维护属性</a>
						        </span>
						    </div>
						    <div class="content" id="myContent">
						    </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
