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

<title>修改广告信息 - 爱尔特合伙人后台</title>
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
						<div class="centerBox" style="margin-top:10px;">
							<p class="tip"><i class="tip"></i>名称和是否必选必须填写</p>
							<div class="formBox">
								<form action="<%=request.getContextPath()%>/admin/adv/modifyAdvertisement?id=${requestScope.advertisement.id}" method="post">
									<input type="hidden" value="redirect:/admin/adv/findAdvertisements" name="redirect"/>
									<div class="item">
										<div class="l"> 图片： </div>
										<div class="r">
											<jsp:include page="../../../myFrameU/up/upImage.jsp">
												<jsp:param value="adverBox" name="box"/>
												<jsp:param value="1" name="number"/>
												<jsp:param value="picUrl" name="imageName"/>
												<jsp:param value="${requestScope.advertisement.picUrl}" name="imageValue"/>
												<jsp:param value="adver" name="saveType"/>
											</jsp:include>
										</div>
									</div>
									<div class="item">
										<div class="l"> 链接： </div>
										<div class="r">
											<span>
												<input name="picA" type="text" value="${requestScope.advertisement.picA}" class="textInput"/>
											</span>
										</div>
									</div>
									<div class="item">
										<div class="l"> 标题： </div>
										<div class="r">
											<span>
												<input name="picTitle" type="text" value="${requestScope.advertisement.picTitle}" class="textInput"/>
												<em class="formTip">0/35</em>
											</span>
										</div>
									</div>
									<c:if test='${requestScope.advertisement.advType!="FOUCS"}'>
										<input type="hidden" value="0" name="indexNum"/>
									</c:if>
									<c:if test='${requestScope.advertisement.advType=="FOUCS"}'>
										<input type="hidden" value="${requestScope.advertisement.indexNum}" name="indexNum"/>
									<div class="item" style="display:none;">
										<div class="l"> 次序： </div>
										<div class="r">
											<span>
												<select name="indexNum">
													<c:forEach begin="1" end="${requestScope.advertising.picNumber }" varStatus="vs">
														<c:if test='${vs.index==requestScope.advertisement.indexNum}'>
															<option value="${vs.index}" selected="selected">${vs.index}</option>
														</c:if>
														<c:if test='${vs.index!=requestScope.advertisement.indexNum}'>
															<option value="${vs.index}" >${vs.index}</option>
														</c:if>
													</c:forEach>
												</select>
											</span>
										</div>
									</div>
									</c:if>
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
