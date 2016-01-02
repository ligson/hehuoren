<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>添加资讯 - 爱尔特合伙人后台</title>
<style type="text/css">
#ptLeft{width:450px;margin-left:200px;}
#ptLeft .content{height:659px;padding:0px;}
#ptLeft .content a.selected{background-color:#eee;}
</style>
<script type="text/javascript">

function toAdd_step(a){
	var ntId=$(a).attr("ntId");
	window.location.href=allWeb+"admin/news/toAdd_step2?newsTypeId="+ntId;
}



</script>
</head>
<jsp:include page="../../../myFrameU/exception/refererException.jsp"></jsp:include>
<body>
	<jsp:include page="../head.jsp"></jsp:include>
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="../bodyLeftMenu.jsp">
					<jsp:param value="auction-product" name="leftMenu"/>
				</jsp:include>
			</div>
			<div id="layout_bodyI_r">
				<div id="bodyR_head">
					<h2>微信帮助资讯</h2>
					<div class="tabBox" id="bodyRH_tab">
						<jsp:include page="include_tab.jsp">
							<jsp:param value="myTabLiA2" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="bodyRC_tip" style="display:none">
						<p class="desc">
							首先选择类型，然后点击进入添加
						</p>
					</div>
					
					
					
					
					<div id="mainContent">
						
						<div class="quyukuang treeBox" id="ptLeft">
							<div class="title">
						        <h3>资讯分类</h3>
						    </div>
						    <div class="content">
						    	<ul>
						    		<c:forEach var="nt" items="${requestScope.newsTypeList}">
						    			<c:if test='${nt.isROOT=="0"}'>
							    			<li class="everyRootPt">
								    			<a href="javascript:void(0);" >
								    				<span class="ptNamespan">${nt.name}</span>
								    			</a>
								    			<ul>
								    				<c:forEach var="nt2" items="${nt.childs}">
								    						<li class="everyTwoPt">
										    					<a  href="javascript:void(0);"  onClick="toAdd_step(this);"  ntId="${nt2.id}">
										    						<span class="quans"><i clas="quan"></i></span>
												    				<span class="ptNamespan1">${nt2.name}</span>
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
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>
