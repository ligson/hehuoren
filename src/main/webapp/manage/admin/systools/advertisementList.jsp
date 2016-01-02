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

<title>广告列表 - 爱尔特合伙人后台</title>
<script type="text/javascript">
<!--
function mydownload(a){
	var id=$(a).attr("dataId");
	$.ajax( {
		type : "POST",
		url : allWeb+"/admin/dataBackup/down?id="+id,
		success : function(msg) {
			alert(msg);
		}
	})
}
-->
function modifyStatus(s){
	var id=$(s).attr("adId");
	var status=$(s).val();
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/adv/modifyStatus?id="+id+"&status="+status,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","修改成功","ok");
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
					<jsp:param value="sys-tools" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>系统工具</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA3" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<div class="tableBox myList" >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>类型</td>
										<td>所在页面</td>
										<td>所属位置</td>
										<td>广告</td>
										<td>状态</td>
										<td>修改</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="entry" items="${requestScope.advertisementMap}">
										<c:forEach var="a" items="${entry.value}">
											<c:set var="adving" value="${requestScope.advertisingMap[a.advertisingMarkedNum]}" scope="request"></c:set>
											<c:set var="apage" value="${requestScope.advertingPageMap[requestScope.adving.advertingPageNameKey]}" scope="request"></c:set>
											<tr>
												<td>
													<c:if test='${requestScope.adving.advType=="FOUCS"}'>
														焦点轮播
													</c:if>
													<c:if test='${requestScope.adving.advType=="IMAGE"}'>
														单图广告
													</c:if>
												</td>
												<td>
													${requestScope.apage.name}
												</td>
												<td>
													${requestScope.adving.info}
													<c:if test='${requestScope.adving.advType=="FOUCS"}'>
														<br/>[第${a.indexNum}张广告图位置]
													</c:if>
													<c:if test='${requestScope.adving.haveSon=="YES"}'>
														<c:set var="ptIdStr" value="productTypeId_${a.sonByValue}"></c:set>
														<c:set var="pt" value="${applicationScope.productTypeMap[ptIdStr]}"></c:set>
														<b style="color:red;">[${pt.name}]</b>
													</c:if>
												</td>
												<td style="width:400px">
													<img src="<%=request.getContextPath()%>/${a.picUrl}" style="width:400px;"/>
												</td>
												<td>
													<select onchange="modifyStatus(this)" adId="${a.id}">
														<c:if test='${a.status=="ING"}'>
															<option value="ING">生效</option>
															<option value="CLOSE">失效</option>
														</c:if>
														<c:if test='${a.status=="CLOSE"}'>
															<option value="ING">生效</option>
															<option value="CLOSE" selected="selected">失效</option>
														</c:if>
													</select>
												</td>
												<td>
													<a href="<%=request.getContextPath()%>/admin/adv/findAdvertisementId2Mod?id=${a.id}">修改</a>
												</td>
											</tr>
										</c:forEach>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
