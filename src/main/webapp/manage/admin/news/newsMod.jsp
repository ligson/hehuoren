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

 <script type="text/javascript" src="<%=request.getContextPath()%>/ueditor126/ueditor.config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ueditor126/ueditor.all.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/ueditor126/themes/default/css/ueditor.css" />



<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />


<title>修改资讯 - 爱尔特合伙人管理后台</title>
<style type="text/css">
.formBox .item .l{width:100px;}
</style>
<script type="text/javascript">
$(document).ready(function() {
	
	
	//initUpImg("upImageButton_1","ueditor-product");
	
	
	
	try{
		//var a = [['Bold','ForeColor','Link', 'Unlink','|','Paragraph','RowSpacing','FontFamily','FontSize','insertimage','imagenone', 'imageleft', 'imageright','insertvideo']];
		var editor2 = new baidu.editor.ui.Editor( {
			textarea : 'content',
			imageUrl : allWeb+'servlet/uploadImage?saveType=ueditor-news'
		});
		editor2.render("myEditor2");
		/* var options = {
			target:'#my_content',
			success:showResponse,
			beforeSubmit: showRequest 
		};
		$('#submitSales').submit(function() {
			editor2.sync();
			$(this).ajaxSubmit(options);
			return false; 
		}); */
		}catch(e){
			alert(e.name + ":" + e.message);
		}
});

</script>
</head>
<body>
<jsp:include page="../../../myFrameU/exception/refererException.jsp"></jsp:include>
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
					<div id="mainContent">
						<div style="width:99%;height:40px;padding-top:10px;">
						</div>
						<div class="centerBox">
							<p class="tip"><i class="tip"></i>名称和是否必选必须填写</p>
							<div class="formBox">
								<form action="<%=request.getContextPath()%>/admin/news/mod?id=${requestScope.news.id}" method="post">
									<input type="hidden" value="redirect:/admin/news/finds" name="redirect"/>
									<input type="hidden" value="${requestScope.news.newsTypeId}" name="newsTypeId"/>
									<div class="item">
										<div class="l"> 标题： </div>
										<div class="r">
											<span>
												<input name="title" type="text" value="${requestScope.news.title}" class="textInput"/>
												<em class="formTip">0/35</em>
											</span>
										</div>
									</div>
									
									<div class="item">
										<div class="l">资讯内容：</div>
										<div class="r">
											<script type="text/plain" id="myEditor2" style="width:639px;">
												${requestScope.newsContent.contentValue1}
											</script> 
										</div>
									</div>
									
									<div class="item">
										<div class="l"> </div>
										<div class="r">
											<input type="submit" value="提交" class="btn"/>
										</div>
									</div>
								</form>
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
