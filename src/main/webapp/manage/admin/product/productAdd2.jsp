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


<title>添加产品 - 爱尔特合伙人管理后台</title>
<style type="text/css">
.formBox .item .l{width:100px;}
.priceZUHETable{width:650px;background:#eee;padding:10px;}
.priceZUHETable tr td{width:14% !important;}
.priceZUHETable tr td input{width:99% !important;padding:0px !important;}
</style>
<script type="text/javascript">
$(document).ready(function() {
	
	
	//initUpImg("upImageButton_1","ueditor-product");
	
	
	
	try{
		//var a = [['Bold','ForeColor','Link', 'Unlink','|','Paragraph','RowSpacing','FontFamily','FontSize','insertimage','imagenone', 'imageleft', 'imageright','insertvideo']];
		var editor2 = new baidu.editor.ui.Editor( {
			textarea : 'content',
			autoClearinitialContent : true,
			imageUrl : allWeb+'servlet/uploadImage?saveType=ueditor-product'
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
					<h2>产品中心->添加产品</h2>
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
								<form action="<%=request.getContextPath()%>/admin/product/add?productTypeId=${requestScope.productType.id}" method="post">
									<input type="hidden" value="redirect:/admin/product/finds" name="redirect"/>
									<input type="hidden" value="1" name="shopId"/>
									<div class="item">
										<div class="l"> 名称： </div>
										<div class="r">
											<span>
												<input name="name" type="text" value="" class="textInput"/>
												<em class="formTip">0/35</em>
											</span>
										</div>
									</div>
									
									<c:if test='${!empty requestScope.propertyDistributeList_ALL}'>
									<div class="item">
										<div class="l"> product所有数据属性： </div>
										<div class="r" style="margin-left:0px;">
											<span>
												<div class="expandPropertyBox">
													<jsp:include page="../../expand/propertyAddHTML.jsp">
														<jsp:param value="propertyDistributeList_ALL" name="propertyDistributeListName"/>
													</jsp:include>
												</div>
											</span>
										</div>
									</div>
									</c:if>
									<c:if test='${!empty requestScope.propertyDistributeList_self}'>
									<div class="item">
										<div class="l"> ${requestScope.productType.name}属性： </div>
										<div class="r" style="margin-left:0px;">
											<span>
												<div class="expandPropertyBox">
													<jsp:include page="../../expand/propertyAddHTML.jsp">
														<jsp:param value="propertyDistributeList_self" name="propertyDistributeListName"/>
													</jsp:include>
												</div>
											</span>
										</div>
									</div>
									</c:if>
									<c:if test='${!empty requestScope.propertyDistributeList_root}'>
									<div class="item">
										<div class="l"> 属性： </div>
										<div class="r" style="margin-left:0px;">
											<span>
												<div class="expandPropertyBox">
													<jsp:include page="../../expand/propertyAddHTML.jsp">
														<jsp:param value="propertyDistributeList_root" name="propertyDistributeListName"/>
													</jsp:include>
												</div>
											</span>
										</div>
									</div>
									</c:if>
									<div class="item">
										<div class="l">产品图片：</div>
										<div class="r">
											<jsp:include page="../../../myFrameU/up/upImage.jsp">
												<jsp:param value="2" name="number"/>
												<jsp:param value="lunboImage1,lunboImage2" name="imageName"/>
												<jsp:param value="product" name="saveType"/>
												<jsp:param value="请上传产品的焦点图，第一张为封面，第二张为产品单页的信息简介自定义图" name="bottomTipTxt"/>
											</jsp:include>
										</div>
									</div>
									<div class="item">
										<div class="l">产品介绍：</div>
										<div class="r">
											<script type="text/plain" id="myEditor2" style="width:639px;"></script> 
										</div>
									</div>
									
									
									<div class="item" style="display:none;">
										<div class="l">自提点：</div>
										<div class="r">
											<span>
												<c:forEach var="picke" items="${applicationScope.pickUpAddressMap}">
													<c:set var="pick" value="${picke.value}"></c:set>
													<label>
														<input name="pickupAddressIds" type="checkbox" value="${pick.id}"/>${pick.name}
													</label>
												</c:forEach>
											</span>
										</div>
									</div>
									<div class="item" style="display:none;">
										<div class="l">价格组合：</div>
										<div class="r">
											<table class="priceZUHETable">
												<thead>
													<tr>
														<td>净含量</td>
														<td>规格</td>
														<td>适用</td>
														<td>数量</td>
														<td>网店价格</td>
														<td>合伙人价格</td>
														<td>提成</td>
													</tr>
												</thead>
												<tbody>
													<c:forEach begin="1" end="20" varStatus="vs">
														<tr>
															<td>
																<select name="xilieName">
																	<option value="4L">4L</option>
																	<option value="18L">18L</option>
																	<option value="22L">22L</option>
																</select>
															</td>
															<td>
																<select name="baozhuangName">
																	<option value="10W-30">10W-30</option>
																	<option value="15W-40">15W-40</option>
																	<option value="20W-50">20W-50</option>
																</select>
															</td>
															<td>
																<input name="shiyongName" type="text" value="" class="textInput"/>
															</td>
															
															<td>
																<input name="productCount" type="text" value="" class="textInput"/>
															</td>
															<td>
																<input name="price1" type="text" value="" class="textInput"/>
															</td>
															<td>
																<input name="price2" type="text" value="" class="textInput"/>
															</td>
															<td>
																<input name="toTjrTc" type="text" value="" class="textInput"/>
															</td>
															
														</tr>
													</c:forEach>
													
												</tbody>
											</table>
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
