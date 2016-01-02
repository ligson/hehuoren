<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/manage/css/help.css"/>

<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/image/favicon.ico" />
<title>帮助文档 - 爱尔特合伙人后台</title>
<script type="text/javascript">

</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<c:set var="g8Name" value="apply_tixian_number" scope="request"></c:set>
	<c:set var="g8" value="${applicationScope.globalMap[requestScope.g8Name]}"></c:set>
	
	<c:set var="g12Name" value="shopRegist_spending" scope="request"></c:set>
	<c:set var="g12" value="${applicationScope.globalMap[requestScope.g12Name]}"></c:set>
	
	
	<c:set var="g11Name" value="shopLevel221_price" scope="request"></c:set>
	<c:set var="g11" value="${applicationScope.globalMap[requestScope.g11Name]}"></c:set>
	
	<c:set var="g13Name" value="userLevel2vip_price" scope="request"></c:set>
	<c:set var="g13" value="${applicationScope.globalMap[requestScope.g13Name]}"></c:set>
	
	
	<div id="layout_body">
		<!-- 正文 -->
		<div id="layout_bodyInner">
			<div id="layout_bodyI_l">
				<jsp:include page="bodyLeftMenu.jsp">
					<jsp:param value="expand" name="leftMenu"/>
				</jsp:include>				
			</div>
			<div id="layout_bodyI_r">
				<div id="layout_bodyI_r_center">
					<div id="bodyR_head">
						<h2>产品扩展属性帮助</h2>
					</div>
					<div id="bodyR_content">
						<div class="hgroup">
							<div class="top">
								<h2>1、什么是产品的扩展属性</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										为了系统稳定，请尽量少可能的修改扩展属性以及扩展属性分配。
									</li>
									<li style="height:auto">
										由于暂时只能看到淘宝网的部分产品分类下的扩展属性（因为我们在淘宝上的账户下的主营类目只有没几个），所以其他分类下的属性并不知道，如果日后运营方能够
										看到这些产品分类的扩展属性，则可进行同步维护到我们自己的平台上来。
									</li>
									<li style="height:auto">
										1）不同产品类型具有不同的属性，即为产品的扩展属性。如淘宝分类系统中电脑类别和衣服类别的属性几乎完全不同。
									</li>
									<li style="height:auto">
										2）本系统中的产品扩展属性模块主要分为两部分，第一：维护系统统一全部的扩展属性库。第二：为某些产品类别的产品分配这些扩展属性。
									</li>
								</ul>
							</div>
						</div>
						
						<div class="hgroup">
							<div class="top">
								<h2>2、如何维护系统库中扩展属性</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										1）点击[系统设置]-[产品分类]-右侧的链接[维护属性]，在弹出的新页面中点击[追加扩展属性]
									</li>
									<li style="height:auto">
										2）需要填写的字段有：属性名、属性key（属性key必须英文或者英文和数字结合，且必须是唯一的）、在添加产品的时候选择该属性的添加方式（一般选择下拉即可）、选项、默认选中选项
									</li>
									<li style="height:auto">
										3）扩展属性可修改。为了保证系统稳定性，不提供删除功能，不需要删除，如果不使用，只要不再分配给其他产品即可。
									</li>
								</ul>
							</div>
						</div>
						
						
							<div class="hgroup">
							<div class="top">
								<h2>3、如何给某类产品分配系统扩展属性</h2>
							</div>
							<div class="bot">
								<ul>
									<li style="height:auto">
										点击[系统设置]-[产品分类]，点击每个产品分类，会在右侧出现可选择的扩展属性列表，选择相应的属性和属性值即可。
									</li>
								</ul>
							</div>
						</div>
						
						
						
						
						
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>