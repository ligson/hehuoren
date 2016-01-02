<%@ page language="java" import="java.util.Date,myFrameU.util.commonUtil.date.DateUtils"  contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/user.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />

<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/ui.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/riqi.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/riqi.js"></script>

<title>订单管理  - 爱尔特合伙人后台</title>
<style type="text/css">
.tableBox thead tr td{border-top:none !important;}
.moreTr{display:none;}
.moreTr td{background:#eee;}
.moreTr td table{width:90%;margin:0 auto;}
.moreTr td table  td{background:#FFFFF0;}
</style>
<script type="text/javascript">

function deletePUA(b){
	var puaId=$(b).attr("puaId");
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/pua/remove?id="+puaId,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","删除成功","ok");
				$(b).parents("tr").remove();
			}
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
					<jsp:param value="order-ztd" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>自提点管理</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab1.jsp">
								<jsp:param value="myTabLiA1" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="queryArgsBox">
						<input type="hidden" value="${requestScope.queryArgs}" class="requestQueryArgsInput"/>
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="自提点编码" class="queryTxt" queryArgsKey="markedNum"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="自提点名称" class="queryTxt" queryArgsKey="name"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						<div class="everyQuery" >
								<span class="k"></span>
								<span>
									<span class="mySelect" width="100" status="close">
										<span class="input">省份</span>
										<span class="arrow"></span>
										<span class="down">
											<ul>
												<c:forEach var="pte" items="${applicationScope.addressROOT_map}">
													<c:set var="ad" value="${pte.value}"></c:set>
													<c:if test='${ad.isROOT==0}'>
														<li><a href="javascript:void(0);"  value="{'key':'addressTreeIds','value':'${ad.treeId}','operators':'lastlike'}">${ad.name}</a></li>
													</c:if>
												</c:forEach>
											</ul>
										</span>
									</span>
								</span>
						</div>
						
					</div>
					<div id="mainContent">
						
					
						<c:if test='${empty requestScope.pickUpAddressList}'>
							<div class="noDataTip"></div>
						</c:if>
						<c:if test='${!empty requestScope.pickUpAddressList}'>
							<div class="tableBox myList " >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>编码</td>
										<td>名称</td>
										<td>地区</td>
										<td>具体地址</td>
										<td>电话</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="o" items="${requestScope.pickUpAddressList}">
										<c:set var="addIdStr" value="addId_${o.addressId}" scope="request"></c:set>
										<c:set var="address" value="${applicationScope.addressAll_map[requestScope.addIdStr]}" scope="request"></c:set>
										<tr>
												<td style="width:120px;">
													${o.markedNum}
												</td>
												<td style="width:120px;">
													${o.name}
												</td>
												<td style="width:60px;">
													${requestScope.address.allName}
												</td>
												<td>
													${o.addressStr}
												</td>
												<td>
													${o.telPhone}
												</td>
												<td style="color:#999;" class="morePTD">
													<a href="<%=request.getContextPath()%>/admin/pua/findId2Modify?id=${o.id}" class="btn btnColor_1">修改</a>
													<a onClick="deletePUA(this)" puaId="${o.id}" href="javascript:void(0);" class="btn">删除</a>
												</td>
											</tr>
											
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6">
											<jsp:include page="../../util/pager.jsp"></jsp:include>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
						    	
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
