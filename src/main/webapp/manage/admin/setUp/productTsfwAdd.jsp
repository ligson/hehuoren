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

<title>添加拍品特色服务 - 系统设置 - 艺术拍拍管理后台</title>
<script type="text/javascript">

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
							<jsp:param value="myTabLiA4" name="rightTabName"/>
						</jsp:include>
					</div>
				</div>
				<div id="bodyR_content">
					<div id="mainContent">
						<div style="width:99%;height:40px;padding-top:10px;">
							<a href="<%=request.getContextPath()%>/admin/productTsfw/finds?forwardPage=admin/setUp/productTsfw" class="aButton btn"><i class="back"></i>返回产品特色列表</a>
						</div>
						<div class="centerBox">
							<p class="tip"><i class="tip"></i>名称和是否必选必须填写</p>
							<div class="formBox">
								<form action="<%=request.getContextPath()%>/admin/productTsfw/add" method="post">
									<input type="hidden" value="redirect:/admin/productTsfw/finds" name="redirect"/>
									<div class="item">
										<div class="l"> 名称： </div>
										<div class="r">
											<span>
												<input name="name" type="text" value="" class="textInput"/>
												<em class="formTip">0/35</em>
											</span>
										</div>
									</div>
									<div class="item">
										<div class="l"> 选中： </div>
										<div class="r">
											<span>
												<select class="mySelect" name="addProductMust">
													<option value="true">默认选中</option>
													<option value="false">默认不选中</option>
												</select>
											</span>
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
