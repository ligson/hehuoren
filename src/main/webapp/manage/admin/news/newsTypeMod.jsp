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

<title>修改新闻种类 - 爱尔特合伙人后台</title>
<style type="text/css">
#ptRight{float:left;width:750px;border:1px solid #e7e7eb;height:350px;margin-top:10px;margin-left:100px;}
#addPTBox table td{padding:5px;}
</style>
<script type="text/javascript">
function modNt(a){
	var name=$("#ptNameInput").val();
	$.ajax( {
		type : "POST",
		url : allWeb+"admin/newsType/modify?&name="+name,
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
					<jsp:param value="news" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>微信帮助资讯管理</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA3" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						
						<div class="quyukuang" id="ptRight">
							<div class="title">
						        <h3>修改新闻分类</h3>
						    </div>
						    <div class="content">
						    	<div class="contentI">
						    		<div id="addPTBox">
						    			<table>
						    				<tbody>
						    					<tr>
						    						<td>分类名：</td>
						    						<td>
						    							<input type="text" class="textInput" value="${requestScope.newsType.name}" id="ptNameInput"/>
						    						</td>
						    					</tr>
						    					<tr>
						    						<td></td>
						    						<td>
						    							<input type="button" value="提交" class="btn" onClick="modNt(this);"/>
						    						</td>
						    					</tr>
						    				</tbody>
						    			</table>
						    		</div>
						    	</div>
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
