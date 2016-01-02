<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>账户管理  - 爱尔特合伙人后台</title>
<style type="text/css">
.tableBox thead tr td{border-top:none !important;}
.moreTr{display:none;}
.moreTr td{background:#eee;}
.moreTr td table{width:90%;margin:0 auto;}
.moreTr td table  td{background:#FFFFF0;}
</style>
<script type="text/javascript">
function findAccountItemList(a){
	var accountId=$(a).attr("accountId");
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/account/accountItemList?accountId="+accountId,
		success : function(msg) {
			var tr=$("#accountId_"+accountId+"_moreTr");
			var td=tr.find(".moreTrTd");
			tr.show();
			td.html(msg);
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
					<jsp:param value="account-account" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>账户管理</h2>
					<div class="tabBox" id="bodyRH_tab">
						<c:if test='${requestScope.whoclassName=="myFrameU.shop.entity.Shop"}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA3" name="rightTabName"/>
							</jsp:include>
						</c:if>
						<c:if test='${requestScope.whoclassName=="myFrameU.user.entity.User"}'>
							<jsp:include page="include_tab.jsp">
								<jsp:param value="myTabLiA4" name="rightTabName"/>
							</jsp:include>
						</c:if>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="queryArgsBox">
						<input type="hidden" value="${requestScope.queryArgs}" class="requestQueryArgsInput"/>
						
						<div class="everyQuery" >
							<span class="k"></span>
							<span>
								<span class="mySearch">
									<span><input type="text" value="角色名称" class="queryTxt" queryArgsKey="whoName"/></span>
									<span class="searchBut"></span>
								</span>
							</span>
						</div>
						
						<div class="everyQuery" >
								<span class="k"></span>
								<span>
									<span class="mySelect" width="80" status="close">
										<span class="input">总额</span>
										<span class="arrow"></span>
										<span class="down">
											<ul>
												<li><a href="javascript:void(0);"  value="{'key':'totalPrice','value':'0','operators':'!=0','filedType':'int'}">不为0</a></li>
												<li><a href="javascript:void(0);"  value="{'key':'totalPrice','value':'0','filedType':'int'}">为0</a></li>
											</ul>
										</span>
									</span>
								</span>
						</div>
					</div>
					<div id="mainContent">
						    	<div class="tableBox myList " >
							<table cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<td>账户</td>
										<td>可用余额</td>
										<td>冻结金额</td>
										<td>总金额</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="a" items="${requestScope.accountList}">
										<tr>
												
												<td style="width:120px;">
													${a.whoName}
												</td>
												<td>
													${a.availablePrice}
												</td>
												<td>
													${a.frozenPrice}
												</td>
												<td>
													${a.totalPrice}
												</td>
												<td style="display:none;">
													${a.zhifubao}
												</td>
												<td style="display:none;">
													${a.withdrawalsPwd}
												</td>
												<td>
													<a href="<%=request.getContextPath()%>/admin/account/accountItemList?accountId=${a.id}" class="btn btnColor_1"  accountId="${a.id}">查看明细</a>
												</td>
											</tr>
											<tr class="moreTr" id="accountId_${a.id}_moreTr">
												<td class="moreTrTd" colspan="7">
													
												</td>
											</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="7">
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
