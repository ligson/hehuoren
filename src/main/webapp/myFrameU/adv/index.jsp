<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
    <%@ taglib prefix="cache" uri="/ehcache" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${sessionScope.address.name}装修网 - 找装修公司 - 最具权威的装修网 - 【易淘装360装修网】 - 装修360度全方位服务 - 一站式服务装修平台</title>
<meta name="keywords" content="无锡装修网,无锡装修网站大全,无锡装修网站哪个好,无锡装修,无锡装修公司排名,装修流程">  
<meta name="description" content="【易淘装360装修网】是无锡最具权威的装修网，3分钟免费发布装修招标！3家装修公司提供免费上门服务！PK装修方案、PK质量、PK哪家更省钱！目前100多个城市分站，累计服务超过600万家庭，汇聚6

万家正规装修公司，60万室内设计师，装修360度服务为您保驾护航！是业主和装修公司的不二选择！找装修公司，尽在易淘装360装修网！" >
<meta name="baidu-site-verification" content="OEBnqJzdbt" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/index.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/focus1.css"/>	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/index.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	initLunbotu($("#focus_bigFocus"),"100%",405,"");
});
</script>
<!--[if lt IE 10]>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/PIE.js"></script>
<![endif]-->
<script language="javascript">
$(function() {
    if (window.PIE) {
        $('.needPIE').each(function() {
            PIE.attach(this);
        });
    }
});
</script>
</head>
<body>
<c:set var="yuyueNumKey" value="yuyueNum" ></c:set>
<c:set var="yuyueNum" value="${applicationScope.webNumMap[pageScope.yuyueNumKey]}" scope="request"></c:set>

<c:set var="taskNumKey" value="taskNum" ></c:set>
<c:set var="taskNum" value="${applicationScope.webNumMap[pageScope.taskNumKey]}" scope="request"></c:set>

<c:set var="caseAlbumNumKey" value="caseAlbumNum" ></c:set>
<c:set var="caseAlbumNum" value="${applicationScope.webNumMap[pageScope.caseAlbumNumKey]}" scope="request"></c:set>

<c:set var="shejishiNumKey" value="shejishiNum" ></c:set>
<c:set var="shejishiNum" value="${applicationScope.webNumMap[pageScope.shejishiNumKey]}" scope="request"></c:set>

<c:set var="shopNumKey" value="shopNum" ></c:set>
<c:set var="shopNum" value="${applicationScope.webNumMap[pageScope.shopNumKey]}" scope="request"></c:set>


    
<jsp:include page="itopHeader.jsp"></jsp:include>
<div id="main" pageName="index">
	<jsp:include page="iheader.jsp"></jsp:include>
	<jsp:include page="imenu.jsp"></jsp:include>
	<div id="focusOUTER" style="display:none;">
		<div id="focus_bigFocus" class="focus bigFocus" style="height:350px;">
			<ul>
				<li>
					<a href="javascript:void(0);" target="_blank"  class="bgFocusA" style="background:url('<%=request.getContextPath()%>/images/1.jpg') no-repeat scroll center center transparent;"
						smallImage="<%=request.getContextPath()%>/images/1.jpg" >
					</a>
				</li>
				<li>
					<a href="javascript:void(0);" target="_blank"  class="bgFocusA" style="background:url('<%=request.getContextPath()%>/images/2.jpg') no-repeat scroll center center transparent;"
						smallImage="<%=request.getContextPath()%>/images/2.jpg" >
					</a>
				</li>
				<li>
					<a href="javascript:void(0);" target="_blank"  class="bgFocusA" style="background:url('<%=request.getContextPath()%>/images/3.jpg') no-repeat scroll center center transparent;"
						smallImage="<%=request.getContextPath()%>/images/3.jpg" >
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div id="menuBotBox">
		<div class="menuBotBoxI">
			<div class="block index_line1">
	<jsp:include page="iindexLeftYuyue.jsp"></jsp:include>
	<div class="izxlc_ri">
		<div class="izxlc_tad">
		
			<c:if test='${sessionScope.address!=null && sessionScope.address!=""}'>
					<a href="<%=request.getContextPath()%>/${sessionScope.address.url}/shopList-pager-1.html" target="_blank" class="tad_zxb" rel="nofollow">
						<strong>
							装修保，装修界的支付宝<br>
							<span>装修满意后，再把工程款付给装修公司</span>
							<span class="tad_spanr"><b>&gt;</b>立刻了解</span>
						</strong>
					</a>			
			</c:if>
			<c:if test='${sessionScope.address==null || sessionScope.address==""}'>
				<a href="<%=request.getContextPath()%>/shopList-pager-1.html" target="_blank" class="tad_zxb" rel="nofollow">
						<strong>
							装修保，装修界的支付宝<br>
							<span>装修满意后，再把工程款付给装修公司</span>
							<span class="tad_spanr"><b>&gt;</b>立刻了解</span>
						</strong>
				</a>	
			</c:if>
			<a href="<%=request.getContextPath()%>/userLogin.html" target="_blank" class="tad_zxgj" rel="nofollow">
				<strong>
					<br>
					<span></span>
					<span class="tad_spanr"><b>&gt;</b></span>
				</strong>
			</a>
		</div>
		<div id="picBox" class="izxlc_img">
			<div id="focus" class="focus">
						<ul>
							<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-1" name="markeNums"/>
            					<jsp:param value="<li>" name="prefix"/>
            					<jsp:param value="</li>" name="suffix"/>
            				</jsp:include>
						</ul>
					</div>
		</div>
	</div>
</div>
		</div>
	</div>
	<div id="containerOUTER" >
		<div id="container" >
			<div class="block index_line2" style="clear:both;">
					<div class="index_title">
				        		<ul>首创“不上当的装修流程”</ul>
				                <p><a href="#" class="tbtx">特别提醒：易淘装360装修网，致力装修行业的阳光透明，不会向业主收取任何费用</a></p>
					</div>
					<a href="javascript:void(0);" target="_blank" rel="nofollow">
					<dl>
						
						<dt>
							<img src="<%=request.getContextPath()%>/images/index_v3_lcad1.jpg">
						</dt>
						<dd>
							<strong>1.菜鸟心中有底</strong>
							申请3家的报价方案送上门
						</dd>
						
					</dl>
					</a>
					<a href="javascript:void(0);" target="_blank" rel="nofollow">
					<dl>
						
						<dt>
							<img src="<%=request.getContextPath()%>/images/index_v3_lcad2.jpg">
						</dt>
						<dd>
							<strong>2.挤干报价水份</strong>
							60万设计专家来审核报价和点评
						</dd>
						
					</dl>
					</a>
					<a href="javascript:void(0);" target="_blank" rel="nofollow">
					<dl>
						
						<dt>
							<img src="<%=request.getContextPath()%>/images/index_v3_lcad3.jpg">
						</dt>
						<dd>
							<strong>3.签订三方合同</strong>
							拒绝合同陷阱，加入易淘装360装修网“装修保”
						</dd>
						
					</dl>
					</a>
					<a href="javascript:void(0);" target="_blank" rel="nofollow">
					<dl>
						
						<dt>
							<img src="<%=request.getContextPath()%>/images/index_v3_lcad4.jpg">
						</dt>
						<dd>
							<strong>4.监理上门监管</strong>
							免费申请第三方监理上门把关
						</dd>
					</dl>
					</a>
					<a href="javascript:void(0);" target="_blank" rel="nofollow">
					<dl style="margin-right:0px;">
						
						<dt>
							<img src="<%=request.getContextPath()%>/images/index_v3_lcad5.jpg">
						</dt>
						<dd>
							<strong>5.装修满意付款</strong>
				            满意1个月后，再付款给<br>装修公司
						</dd>
						
					</dl>
					</a>
				</div>
			
			
			
			<div class="newIndexBox nib" id="allTaskBoxUl" style="height:320px;overflow: hidden;">
				<div class="nibTitle">
					<ul >
						<li ><a href="javascript:void(0);" class="nibt_gongdi cur" tabConId="indexTaskTab_con_1" arrowLeft="0px"></a></li>
						<li ><a href="javascript:void(0);" class="nibt_task_new_sub" tabConId="indexTaskTab_con_2" arrowLeft="150px"></a></li>
						<li ><a href="javascript:void(0);" class="nibt_task_new_suc" tabConId="indexTaskTab_con_3" arrowLeft="300px"></a></li>
						<li class="nibtitle_rigLi">汇聚 <b>${requestScope.taskNum}</b> 招标信息</li>
					</ul>
				</div>
				<div class="m_l_y_z_y_title_line">
			        <div class="m_l_y_z_y_title_arrow_box" style="width: 140px; left: 0px;" id="allTaskBoxUlARROW">
			            <div class="m_l_y_z_y_title_bg m_l_y_z_y_title_arrow" id=""></div>
			        </div>
			    </div>
			    <div class="nibCon">
			    	<div class="nibCon_l root61" id="indexTaskTab_con_2" style="display:none;">
			    		<div class="yz_city_content_col_l f_l">
							<p class="user_num_dj">当前 <strong>31970位</strong> 业主在使用</p>
							<a href="javascript:void(0);" target="_blank" class="titl">【易淘装360装修网】专业服务提供给您！</a>
							<div class="img_er">
								<div class="img_box">
									<ul class="mianfeiFbUl ">
										<li class="mffbulli_1"><a href="<%=request.getContextPath()%>/toAddTask.html"><span class=" line_No">1</span><span>免费发布招标</span></a></li>
										<li class="mffbulli_2"><a href="<%=request.getContextPath()%>/yuyue-sw2-st0-wt2-sid0-sjs0-gd0.html"><span class=" line_No">2</span><span>免费户型设计</span></a></li>
										<li class="mffbulli_3"><a href="<%=request.getContextPath()%>/yuyue-sw2-st0-wt3-sid0-sjs0-gd0.html"><span class=" line_No">3</span><span>精准装修报价</span></a></li>
										<li class="mffbulli_4"><a href="<%=request.getContextPath()%>/yuyue-sw2-st0-wt5-sid0-sjs0-gd0.html"><span class=" line_No">4</span><span>免费验房申请</span></a></li>
										<li class="mffbulli_5"><a href="<%=request.getContextPath()%>/yuyue-sw2-st0-wt6-sid0-sjs0-gd0.html"><span class=" line_No">5</span><span>第三方监理</span></a></li>
									</ul>
								</div>
								<div class="erw">
									<p class="p1">-刷什么油漆更好？</p>
									<p class="p1">-选什么灯更合适？</p>
									<p class="p1">-沙发要多大尺寸？</p>
									<div class="img_bb"><img src="<%=request.getContextPath()%>/images/erweima.jpg"></div>
									<p class="p2">二维码扫描收藏</p>
								</div>
							</div>
			            </div>
			            <div class="f_l yz_city_content_col_center"></div>
			            <div class="yz_city_content_col_r f_l">
							<p class="user_num_dj">当前已有 <strong>5871位</strong> 业主申请了各种免费装修服务</p>
							<div id="scrollDiv" style="position:relative;">
							<div id="scrollDiv1"> 
								<table cellpadding="0" cellspacing="0">
									<tbody>
										<c:forEach var="yy" items="${cache:list(\"zhuangxiu\",\"yuyue_idNew\",\"YuyueCache\",9,0,null)}" varStatus="vs">
											<c:set var="yyAddIdStr" value="addId_${yy.addressId}" scope="request"></c:set>
											<c:set var="yyAdd" value="${applicationScope.addressAll_map[requestScope.yyAddIdStr]}" scope="request"></c:set>
											<c:set var="yyCityIdStr" value="addId_${requestScope.yyAdd.fatherId}" scope="request"></c:set>
											<c:set var="yyCity" value="${applicationScope.addressAll_map[requestScope.yyCityIdStr]}" scope="request"></c:set>
											<c:if test='${vs.index%2==0}'>
												<c:set var="yyTrColor" value="background-color:#f4f4f4;" scope="request"></c:set>
											</c:if>
											<c:if test='${vs.index%2!=0}'>
												<c:set var="yyTrColor" value="" scope="request"></c:set>
											</c:if>
											<tr style="${requestScope.yyTrColor}">
												<td class="ff_td c1">${fn:substring(yy.relDate,5,16)}</td>
												<td class="c2 ov_elli">
													<a target="_blank" href="javascript:void(0);" title="">${requestScope.yyCity.name}的<span class="yzname">[${fn:substring(yy.name,0,1)}**业主]</span></a>
												</td>
												<td class="blue c3">
													<c:if test='${yy.shopOrweb=="1"}'>
														<!-- 店铺预约 -->
														<c:if test='${yy.ifShopYyType=="1"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>公司-${fn:substring(yy.shopName,0,7)}免费报价</a>
														</c:if>
														<c:if test='${yy.ifShopYyType=="2"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>公司-${fn:substring(yy.shopName,0,7)}免费设计</a>
														</c:if>
														<c:if test='${yy.ifShopYyType=="4"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>设计师-${fn:substring(yy.shejishiName,0,3)}</a>
														</c:if>
														<c:if test='${yy.ifShopYyType=="5"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>参观工地-${fn:substring(yy.gongdiName,0,7)}</a>
														</c:if>
													</c:if>
													<c:if test='${yy.shopOrweb=="2"}'>
														<c:if test='${yy.ifWebYyType=="1"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">发布招标,</font>三家公司方案PK</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="2"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>免费户型设计</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="3"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>精准装修报价</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="5"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>免费验房</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="6"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>第三方监理</a>
														</c:if>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div> 
							<div id="scrollDiv2"> 
								<table cellpadding="0" cellspacing="0">
									<tbody>
										<c:forEach var="yy" items="${cache:list(\"zhuangxiu\",\"yuyue_idNew\",\"YuyueCache\",9,0,null)}" varStatus="vs">
											<c:set var="yyAddIdStr" value="addId_${yy.addressId}" scope="request"></c:set>
											<c:set var="yyAdd" value="${applicationScope.addressAll_map[requestScope.yyAddIdStr]}" scope="request"></c:set>
											<c:set var="yyCityIdStr" value="addId_${requestScope.yyAdd.fatherId}" scope="request"></c:set>
											<c:set var="yyCity" value="${applicationScope.addressAll_map[requestScope.yyCityIdStr]}" scope="request"></c:set>
											<c:if test='${vs.index%2==0}'>
												<c:set var="yyTrColor" value="background-color:#f4f4f4;" scope="request"></c:set>
											</c:if>
											<c:if test='${vs.index%2!=0}'>
												<c:set var="yyTrColor" value="" scope="request"></c:set>
											</c:if>
											<tr style="${requestScope.yyTrColor}">
												<td class="ff_td c1">${fn:substring(yy.relDate,5,16)}</td>
												<td class="c2 ov_elli">
													<a target="_blank" href="javascript:void(0);" title="">${requestScope.yyCity.name}的<span class="yzname">[${fn:substring(yy.name,0,1)}**业主]</span></a>
												</td>
												<td class="blue c3">
													<c:if test='${yy.shopOrweb=="1"}'>
														<!-- 店铺预约 -->
														<c:if test='${yy.ifShopYyType=="1"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>公司-${fn:substring(yy.shopName,0,7)}免费报价</a>
														</c:if>
														<c:if test='${yy.ifShopYyType=="2"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>公司-${fn:substring(yy.shopName,0,7)}免费设计</a>
														</c:if>
														<c:if test='${yy.ifShopYyType=="4"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>设计师-${fn:substring(yy.shejishiName,0,3)}</a>
														</c:if>
														<c:if test='${yy.ifShopYyType=="5"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">预约</font>参观工地-${fn:substring(yy.gongdiName,0,7)}</a>
														</c:if>
													</c:if>
													<c:if test='${yy.shopOrweb=="2"}'>
														<c:if test='${yy.ifWebYyType=="1"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">发布招标,</font>三家公司方案PK</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="2"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>免费户型设计</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="3"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>精准装修报价</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="5"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>免费验房</a>
														</c:if>
														<c:if test='${yy.ifWebYyType=="6"}'>
															<a href="javascript:void(0);" rel="nowfollow"><font color="#A1A1A1">申请了</font>第三方监理</a>
														</c:if>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						   </div>
							<script type="text/javascript"> 
								 var speeds=3000; 
								 var tarDiv = document.getElementById('scrollDiv'); 
								 var tarDiv1 = document.getElementById('scrollDiv1'); 
								 var tarDiv2 = document.getElementById('scrollDiv2'); 
								 tarDiv2.innerHTML=tarDiv1.innerHTML; 
								 //alert(tarDiv2.offsetTop);
								 function Marquees(){ 
									 if(tarDiv2.offsetTop-tarDiv.scrollTop<=0) 
										tarDiv.scrollTop-=tarDiv1.offsetHeight; 
									 else{ 
										 tarDiv.scrollTop+=200; 
									 } 
								 } 
								 var MyMars=window.setInterval(Marquees,speeds); 
								 tarDiv.onmouseover=function() {clearInterval(MyMars)} 
								 tarDiv.onmouseout=function() {MyMars=setInterval(Marquees,speeds)} 
							</script>
			            </div>
			    	</div>
			    	<div class="nibCon_l" id="indexTaskTab_con_1" style="display:block">
			    		<div class="gongdiBox">
			    			<c:forEach var="gd" items="${cache:list(\"zhuangxiu\",\"order_gongdi_yuyue\",\"GongdiCache\",4,sessionScope.addressId,null)}" varStatus="vs">
			    				<c:if test='${vs.index%2!=0}'>
			    					<c:set var="gdMarginLeft" value="l_city_content_margin_left" scope="request"></c:set>
			    				</c:if>
			    				<c:if test='${vs.index%2==0}'>
			    					<c:set var="gdMarginLeft" value="" scope="request"></c:set>
			    				</c:if>
				    			<div class="l_city_content_col f_l ${requestScope.gdMarginLeft}">
					                <div class="l_city_content_col_img f_l">
					                	<jsp:include page="util/linkAddressObjectSelf.jsp">
					                				<jsp:param value="_blank" name="target"/>
													<jsp:param value="${gd.addressId}" name="addressId"/>
													<jsp:param value="shop/${gd.shopId}/gongdi-${gd.id}.html" name="link"/>
													<jsp:param value="<img src='${requestScope.webApp}img/shop/gongdi/min/${gd.image}' width='175px' height='120px' alt='${gd.xiaoquName}'/>" name="aTitle"/>
										</jsp:include>
					                    <div class="img_a_black_bg"></div>
					                    <a target="_blank" class="btn img_a_txt" href="javascript:void(0);" title="由提供" rel="nofollow">由${fn:substring(gd.shopName,0,10)}提供</a>
					                </div>
					                <div class="l_city_content_col_txt f_l">
					                    <div class="txt_title">
					                    	<jsp:include page="util/linkAddressObjectSelf.jsp">
					                				<jsp:param value="_blank" name="target"/>
													<jsp:param value="${gd.addressId}" name="addressId"/>
													<jsp:param value="shop/${gd.shopId}/gongdi-${gd.id}.html" name="link"/>
													<jsp:param value="${gd.xiaoquName}" name="aTitle"/>
											</jsp:include>
					                    </div>
					                    <div class="txt_desc">合同价：<span class="txt_span_color">${gd.price}元</span></div>
					                    <div class="txt_desc">面积：${gd.mianji}平</div>
					                    <div class="txt_desc">预约参观人次：<span class="txt_span_color">${gd.yuyueCanguanNum}次</span></div>
					                    <div class="txt_desc">浏览：<span class="txt_span_color">${gd.dig}次</span></div>
					                </div>
					            </div>
				    		</c:forEach>
			    		</div>
			    	</div>
			    	<div class="nibCon_l" id="indexTaskTab_con_3" style="display:none;">
			    		<div class="banner-steps " >
		                   <div class="banner-steps-cont">
		                    <p>发布装修招标</p>
		                    <p>等待平台审核</p>
		                    <p>等待随机轮标</p>
		                    <p>成功被分配给三家</p>
		                    <p>平台售后服务</p>
		                   </div>
		                   <div class="banner-steps-btn">
		                    <a class="ui-btn ui-btn-inverse ui-btn-large ui-btn-primary" type="button" href="<%=request.getContextPath()%>/toAddTask.html" target="_blank">立即发布需求<span class="right-arrx"></span></a>
		                    </div>
		                </div>
		                
			    		<div class="root61" >
			    			<c:set var="ttsList" value="${cache:list(\"zhuangxiu\",\"order_tasktoshopOk\",\"PollTaskToShopCache\",9,0,null)}" scope="request"></c:set>
			    			<c:set var="ttsShopMap" value="${cache:map(\"zhuangxiu\",\"order_tasktoshopOk_shopMap\",\"PollTaskToShopCache\",9)}" scope="request"></c:set>
			    			<c:forEach var="tts" items="${requestScope.ttsList}" varStatus="vs">
			    				
			    				<c:set var="tts_shopIdStr" value="shopId_${tts.shopId}" scope="request"></c:set>
			    				<c:set var="ttsShop" value="${requestScope.ttsShopMap[requestScope.tts_shopIdStr]}" scope="request"></c:set>
				    			<div class="zx_city_content_col f_l">
					                <div class="zx_city_content_col_img f_l">
					                	<jsp:include page="util/linkAddressObjectSelf.jsp">
					                			<jsp:param value="_blank" name="target"/>
												<jsp:param value="${requestScope.ttsShop.addressId}" name="addressId"/>
												<jsp:param value="shop/${requestScope.ttsShop.id}/" name="link"/>
												<jsp:param value="<img src='${requestScope.webApp}img/shop/logo/min/${requestScope.ttsShop.logo}' width='64px' height='64px'>" name="aTitle"/>
										</jsp:include>
					                </div>
					                <div class="zx_city_content_col_txt f_l">
					                    <div class="txt_title">
					                    	<jsp:include page="util/linkAddressObjectSelf.jsp">
					                			<jsp:param value="_blank" name="target"/>
												<jsp:param value="${requestScope.ttsShop.addressId}" name="addressId"/>
												<jsp:param value="shop/${requestScope.ttsShop.id}/" name="link"/>
												<jsp:param value="${requestScope.ttsShop.name}" name="aTitle"/>
											</jsp:include>
					                    </div>
					                    <div class="txt_desc"><span class="txt_span_color">新签约</span>&nbsp;业主${tts.task.name}</div>
					                    <div>${fn:substring(tts.task.xiaoquName,0,10)}</div>
					                </div>
					            </div>
				    		</c:forEach>
			    		</div>
			    	</div>
			    	
			    	<div class="nibCon_r" >
                        <a rel="nofollow" href="<%=request.getContextPath()%>/about/zblc.jsp?aboutLeft=zblc" class="btn m_content_right_line_first" target="_blank">
			                <div class="m_content_right_icon f_l" style="background-position-x: -70px;"></div>
			                <div class="m_content_right_txt_box f_l">
			                    <div><span class="title">招标流程说明</span></div>
			                    <div>详细了解招标流程，选择最优质服务</div>
			                </div>
			            </a>
			             <a rel="nofollow" href="<%=request.getContextPath()%>/about/rhfbzb.jsp?aboutLeft=rhfbzb" class="btn m_content_right_line" target="_blank">
			                <div class="m_content_right_icon f_l" style="background-position-x: -70px;"></div>
			                <div class="m_content_right_txt_box f_l">
			                    <div><span class="title">如何发布招标</span></div>
			                    <div>教业主如何在易淘装360装修网发布招标</div>
			                </div>
			            </a>
			             <a rel="nofollow" href="http://www.taoz360.com/baike/zxgl/news1117.html" class="btn m_content_right_line" target="_blank">
			                <div class="m_content_right_icon f_l" style="background-position-x: -70px;"></div>
			                <div class="m_content_right_txt_box f_l">
			                    <div><span class="title">装修合同参考</span></div>
			                    <div>家装、公装、标准版合同都齐了</div>
			                </div>
			            </a>
			            <a rel="nofollow" href="http://www.taoz360.com/baike/zxgl/news1118.html" class="btn m_content_right_line" target="_blank">
			                <div class="m_content_right_icon f_l" style="background-position-x: -70px;"></div>
			                <div class="m_content_right_txt_box f_l">
			                    <div><span class="title">易淘装360装修网监理项目表</span></div>
			                    <div>免费赠送水电、泥工、油漆、安装监理</div>
			                </div>
			            </a>
			    	</div>
			    </div>
			    
			</div>
			
			
			<div class="newIndexBox nib" style="margin-top:0px;">
				<div class="nibTitle">
					<ul>
						<li><a href="javascript:void(0);" class="nibt_shop_hot"></a></li>
						<li class="nibtitle_rigLi">汇聚 <b>${requestScope.shopNum}</b> 家装修公司</li>
					</ul>
				</div>
				<div class="m_l_y_z_y_title_line">
			        <div class="m_l_y_z_y_title_arrow_box" style="width: 140px; left: 0px;">
			            <div class="m_l_y_z_y_title_bg m_l_y_z_y_title_arrow"></div>
			        </div>
			    </div>
			    
			    <div class="nibCon" style="height:791px">
			    	<div class="nibCon_l">
			    		<div class="nibCon_l_smallType">
			    			<ul>
                    			<!--小分类-->
                                        <c:if test='${sessionScope.addressId==null || sessionScope.addressId==""}'>
                                        	
                                        </c:if>
                                        <c:if test='${sessionScope.addressId!=null && sessionScope.addressId!=""}'>
                                        	<li class="m_city_li_first first"><a href="javascript:;" class="cur ">全部</a></li>
                                        	<!-- 城市地址不为空 -->
											<c:set var="address_sons_ids" value="address_sons_${sessionScope.addressId}" scope="request"></c:set>
											<c:forEach var="a" items="${cache:list(\"web\",\"address_sons\",\"AddressCache\",20,sessionScope.addressId,null)}" varStatus="vs">
												<c:if test='${vs.index<11}'>
													<c:if test='${vs.last}'>
			                                        	<li class="m_city_li">
			                                        		<jsp:include page="util/linkAddressSession.jsp">
								                        		<jsp:param value="shopSearch-bzjn-fwqy${a.id}-thtn-fgn-ordertotalScore-pager-1.html" name="link"/>
								                        		<jsp:param value="${a.name}" name="aTitle"/>
								                        		<jsp:param value="m_city_letter_on" name="Aclass"/>
								                        	</jsp:include>
			                                        	</li>
			                                        </c:if>
			                                        <c:if test='${!vs.last}'>
			                                        	<li class="m_city_li">
			                                        		<jsp:include page="util/linkAddressSession.jsp">
								                        		<jsp:param value="shopSearch-bzjn-fwqy${a.id}-thtn-fgn-ordertotalScore-pager-1.html" name="link"/>
								                        		<jsp:param value="${a.name}" name="aTitle"/>
								                        	</jsp:include>
			                                        	</li>
			                                        </c:if>
												</c:if>
											</c:forEach>
                                        </c:if>
                            </ul>
			    		</div>
			    		<div class="index-seller-bd" id="shopListBoxId" >
			    			<div class="index-seller-tab">
			    				<ul class="ui-tab-bd clearfix">
			    						<c:forEach var="s" items="${cache:list(\"zhuangxiu\",\"shopListWithCase\",\"Index\",8,sessionScope.addressId,null)}">
			    						 <li class="index-listitem">
	                                		<div class="item-thumb">
	                                   			<a href="javascript:void(0);" target="_blank" class="user-card" >
	                                  				<img src="<%=request.getContextPath()%>/img/shop/logo/min/${s.logo}"  width="120" height="120" alt="${s.name}" class="lazyload" style="display: inline;">
	                                  			</a>
	                                 		</div>
	                                		<h3 class="yahei">
	                                			<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${s.addressId}" name="addressId"/>
													<jsp:param value="shop/${s.id}/" name="link"/>
													<jsp:param value="${fn:substring(s.name,0,16)}" name="aTitle"/>
												</jsp:include>
	                                		</h3>
			                                <div class="item-info">
			                                <span class="ioci"><img src="<%=request.getContextPath()%>/images/dengji/static/${s.dengji}.gif"/></span><span></span>
			                                </div>
	                                		<div class="gray item-dt">${fn:substring(s.specificAddress,0,21)}</div>
	                                		<ul class="unstyled item-dd">
	                                			<c:forEach var="c" items="${cache:list(\"zhuangxiu\",\"shopListWithCase_caseAlbumList\",\"Index\",8,sessionScope.addressId,null)}">
	                                				<c:if test='${c.shopId==s.id}'>
	                                					<li>
	                                						<span class="highlight">
	                                							<c:if test='${c.priceInterval=="1"}'>3万以下</c:if>
	                                							<c:if test='${c.priceInterval=="2"}'>3-8万</c:if>
	                                							<c:if test='${c.priceInterval=="3"}'>8-30万</c:if>
	                                							<c:if test='${c.priceInterval=="4"}'>30万以上</c:if>
	                                						</span>
	                                						<a href="<%=request.getContextPath()%>/shop/${c.shopId}/shopAlbums${c.id}.html" target="_blank" title="${c.name}">
	                                							<c:if test='${c.jiaOrgong=="1"}'>[家装]</c:if>
	                                							<c:if test='${c.jiaOrgong=="2"}'>[公装]</c:if>
	                                						${c.name} (${fn:substring(c.relDate,0,10)})</a>
	                                					</li>
	                                				</c:if>
	                                			</c:forEach>
	                                        </ul>
	                            		</li>
			    					</c:forEach>
                              	</ul>
			    			</div>
			    		</div>
			    		<div class="advImgLogoBox advertisementBox">
			    			<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-4" name="markeNums"/>
            				</jsp:include>
            				<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-5" name="markeNums"/>
            				</jsp:include>
            				<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-6" name="markeNums"/>
            				</jsp:include>
            				<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-7" name="markeNums"/>
            				</jsp:include>
			    		</div>
			    	</div>
			    	<div class="nibCon_r" style="height:791px;">
                        <div class="orderDivTop">
                        	<a href="javascript:void(0);" class="cur" onClick="tabShopOrder(this)" orderType="lundan">轮单排行榜</a>
                        	<a href="javascript:void(0);" onClick="tabShopOrder(this)" orderType="huoyue">最近活跃公司</a>
                        </div>
                        <ul class="r_list" id="order_lundan_company" style="display: block;">
                        	<c:forEach var="s" items="${cache:list(\"zhuangxiu\",\"shopOrder_taskPolll\",\"ShopCache\",10,sessionScope.addressId,null)}" varStatus="vs">
                        	<c:if test='${vs.index<5}'>
                        	<c:if test='${vs.first}'>
	                        	<li class="first_thr">
									<div class="s_photo">
										<a href="javascript:void(0);" target="_blank" rel="nofollow">
											<img src="<%=request.getContextPath()%>/img/shop/logo/min/${s.logo}">
										</a>
									</div>
									<div class="order_num line_No${vs.index}"></div>
									<div class="s_data">
										<span class="s_name">
											<jsp:include page="util/linkAddressObjectSelf.jsp">
												<jsp:param value="${s.addressId}" name="addressId"/>
												<jsp:param value="shop/${s.id}/" name="link"/>
												<jsp:param value="${fn:substring(s.smallName,0,6)}" name="aTitle"/>
												<jsp:param value="rslr_name" name="Aclass"/>
											</jsp:include>
										</span>
										<span class="s_det" style="top:0px;">轮单数：<b>${s.taskPollAllNum}</b></span>
									</div>
									<div class="cler"></div>
								</li>
							</c:if>
							<c:if test='${!vs.first}'>
								<li>
									<div class="s_photo">
										<a href="javascript:void(0);" target="_blank" rel="nofollow"><img src="<%=request.getContextPath()%>/img/shop/logo/min/${s.logo}" width="45px" height="45px"></a>
									</div>
									<div class="order_num line_No${vs.index}"></div>
									<div class="s_data">
										<span class="s_name">
											<jsp:include page="util/linkAddressObjectSelf.jsp">
												<jsp:param value="${s.addressId}" name="addressId"/>
												<jsp:param value="shop/${s.id}/" name="link"/>
												<jsp:param value="${fn:substring(s.smallName,0,6)}" name="aTitle"/>
												<jsp:param value="orderNOFirstName" name="Aclass"/>
											</jsp:include>
										</span>
										<span class="s_det">轮单数：<b>${s.taskPollAllNum}</b></span>
									</div>
									<div class="cler"></div>
								</li>
							</c:if>
							</c:if>
							</c:forEach>
						</ul>
						
						
						
						
						
						
						<ul class="r_list" id="order_huoyue_company" style="display: none;">
                        	<c:forEach var="iie" items="${cache:list(\"zhuangxiu\",\"zuijinShop\",\"Jifen\",20,sessionScope.addressId,null)}" varStatus="vs">
                        	<c:if test='${vs.index<5}'>
                        	<c:if test='${vs.first}'>
	                        	<li class="first_thr">
									<div class="s_photo">
										<a href="javascript:void(0);" target="_blank" rel="nofollow">
											<img src="<%=request.getContextPath()%>/img/shop/logo/min/${iie.logo}" width="45" height="45">
										</a>
									</div>
									
									<div class="order_num line_No${vs.index}"></div>
									<div class="s_data">
										<span class="s_name">
											<jsp:include page="util/linkAddressObjectSelf.jsp">
												<jsp:param value="${iie.addressId}" name="addressId"/>
												<jsp:param value="shop/${iie.shopId}/" name="link"/>
												<jsp:param value="${iie.shopName}" name="aTitle"/>
												<jsp:param value="rslr_name" name="Aclass"/>
											</jsp:include>
										</span>
										<span class="s_det" style="top:0px;">+<b>${iie.fraction}</b></span>
									</div>
									<div class="cler"></div>
								</li>
							</c:if>
							<c:if test='${!vs.first}'>
								<li>
									<div class="s_photo">
										<a href="javascript:void(0);" target="_blank" rel="nofollow"><img src="<%=request.getContextPath()%>/img/shop/logo/min/${iie.logo}" width="45px" height="45px"></a>
									</div>
									<div class="order_num line_No${vs.index}"></div>
									<div class="s_data">
										<span class="s_name">
											<jsp:include page="util/linkAddressObjectSelf.jsp">
												<jsp:param value="${iie.addressId}" name="addressId"/>
												<jsp:param value="shop/${iie.shopId}/" name="link"/>
												<jsp:param value="${iie.shopName}" name="aTitle"/>
												<jsp:param value="orderNOFirstName" name="Aclass"/>
											</jsp:include>
										</span>
										<span class="s_det">+<b>${iie.fraction}</b></span>
									</div>
									<div class="cler"></div>
								</li>
							</c:if>
							</c:if>
							</c:forEach>
						</ul>
						
						
						<div class="advertisementBox" style="width:265px;padding:10px;">
							<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-2" name="markeNums"/>
            				</jsp:include>
            				<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-3" name="markeNums"/>
            				</jsp:include>
            				
						</div>
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
			    	</div>
			    </div>
			    
			    
			    <div class="imgXuxian_x">
			    	
			    </div>
			</div>
			
			<div class="block index_line_ad advImgLogoBox" style="border-bottom:none;">
				<ul>
				<li class="fl">
					<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-8" name="markeNums"/>
            		</jsp:include>
				</li>
				<li class="fr">
					<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-9" name="markeNums"/>
            		</jsp:include>
				</li>
				</ul>
			</div>
			
			
			
			
			
			
			<div class="newIndexBox nib ">
				<div class="nibTitle">
					<ul>
						<li><a href="javascript:void(0);" class="nibt_case_hot"></a></li>
						<li class="nibtitle_rigLi">汇聚 <b>${requestScope.caseAlbumNum}</b> 套装修设计案例</li>
					</ul>
				</div>
				<div class="m_l_y_z_y_title_line">
			        <div class="m_l_y_z_y_title_arrow_box" style="width: 140px; left: 0px;">
			            <div class="m_l_y_z_y_title_bg m_l_y_z_y_title_arrow"></div>
			        </div>
			    </div>
			    
			    <div class="nibCon">
			    	<div class="nibCon_l">
			    		<div class="advImgLogoBox noborder">
			    			<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-10" name="markeNums"/>
            				</jsp:include>
			    			<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-11" name="markeNums"/>
            				</jsp:include>
            				<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-12" name="markeNums"/>
            				</jsp:include>
            				<jsp:include page="adverTemplate.jsp">
            					<jsp:param value="1-13" name="markeNums"/>
            				</jsp:include>
			    		</div>
			    		<div class="nibCon_l_smallType noborda midst" style="height:30px;">
			    			<ul>
			    				<li class="m_city_li_first first">
			    					<a href="<%=request.getContextPath()%>/xiaoguotu-kjn-jbn-fgBn-hxn-prn-thtn-pager-1.html" class="cur">全部</a>
			    				</li>
			    				<li class="m_city_li">
			    					<a href="<%=request.getContextPath()%>/xiaoguotu-kj1-jbn-fgB1-hx1-prn-thtn-pager-1.html" class="">小户型欧式客厅装修效果图</a>
			    				</li>
			    				<li class="m_city_li">
			    					<a href="<%=request.getContextPath()%>/xiaoguotu-kj11-jbn-fgBn-hx9-prn-thtn-pager-1.html" class="">二居室卧室装修效果图</a>
			    				</li>
			    				<li class="m_city_li">
			    					<a href="<%=request.getContextPath()%>/xiaoguotu-kj11-jbn-fgBn-hxn-pr1-thtn-pager-1.html" class="">3万元搞定卧室装修</a>
			    				</li>
			    				<li class="m_city_li">
			    					<a href="<%=request.getContextPath()%>/xiaoguotu-kj1-jb1-fgBn-hxn-prn-thtn-pager-1.html" class="">客厅背景墙装修效果图</a>
			    				</li>
                            </ul>
			    		</div>
			    		<div class="caseListBox zxppg_left_xgt_img_box">
						    <div class="zxppg_left_xgt_img f_l">
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kj1-jbn-fgBn-hxn-prn-thtn-pager-1.html" target="_blank"><img src="<%=request.getContextPath()%>/images/index_case_1.jpg" width="200px" height="200px" alt="客厅装修效果图"></a>
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kj1-jbn-fgBn-hxn-prn-thtn-pager-1.html" class="btn zxppg_left_xgt_img_link" target="_blank">客厅装修效果图</a>
						    </div>
						    <div class="zxppg_left_xgt_img f_l">
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kj11-jbn-fgBn-hxn-prn-thtn-pager-1.html" target="_blank"><img src="<%=request.getContextPath()%>/images/index_case_2.jpg" width="200px" height="200px" alt="卧室装修效果图"></a>
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kj11-jbn-fgBn-hxn-prn-thtn-pager-1.html" class="btn zxppg_left_xgt_img_link" target="_blank">卧室装修效果图</a>
						    </div>
						    <div class="zxppg_left_xgt_img f_l">
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kjn-jb12-fgBn-hxn-prn-thtn-pager-1.html" target="_blank"><img src="<%=request.getContextPath()%>/images/index_case_3.jpg" width="200px" height="200px" alt="吊顶装修效果图"></a>
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kjn-jb12-fgBn-hxn-prn-thtn-pager-1.html" class="btn zxppg_left_xgt_img_link" target="_blank">吊顶装修效果图</a>
						    </div>
						    <div class="zxppg_left_xgt_img f_l">
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kj21-jbn-fgBn-hxn-prn-thtn-pager-1.html" target="_blank"><img src="<%=request.getContextPath()%>/images/index_case_4.jpg" width="200px" height="200px" alt="卫生间装修效果图"></a>
						        <a href="<%=request.getContextPath()%>/xiaoguotu-kj21-jbn-fgBn-hxn-prn-thtn-pager-1.html" class="btn zxppg_left_xgt_img_link" target="_blank">卫生间效果图</a>
						    </div>
			    		</div>
			    	</div>
			    	<div class="nibCon_r">
                       <div class="zxppg_right f_l">
                            <div class="zxppg_right_title_box">
							    <div class="f_l title">优秀效果图案例</div>
							    <div class="f_r right_change_btn_box">
							        <a href="javascript:void(0);" class="btn f_l right_change_btn on" onClick="caseXTab(this)" leftNum="-0px"></a>
							        <a href="javascript:void(0);" class="btn f_l right_change_btn" onClick="caseXTab(this)" leftNum="-275px"></a>
							        <a href="javascript:void(0);" class="btn f_l right_change_btn"  onClick="caseXTab(this)" leftNum="-550px"></a>
							    </div>
							</div>
							<div class="zxppg_right_content_box">
							    <div class="all_content_box" style="left: 0px;" id="all_content_boxId">
							    	<c:forEach var="ca" items="${cache:list(\"zhuangxiu\",\"caseAlbum_mustHot\",\"CaseAlbumCache\",3,sessionScope.addressId,null)}" varStatus="vs">
			                        	<c:if test='${vs.index<3}'>
			                        		<div class="one_content_box f_l">
									            <div class="zxppg_right_content_title_box">
									                <div class="f_l" style="color:#4bae36;font-size:14px;">${ca.shopName}</div>
									            </div>
									            <div class="zxppg_right_content_video">
									                <a target="_blank" href="<%=request.getContextPath()%>/shop/${ca.shopId}/shopAlbums${ca.id}.html" >
									                	<img src="<%=request.getContextPath()%>/img/shop/album/min/${ca.fm}" width="265px" >
									               </a>
									            </div>
									            <div class="zxppg_right_content_title">
									            	<a target="_blank" href="<%=request.getContextPath()%>/shop/${ca.shopId}/shopAlbums${ca.id}.html">
									            		${ca.name}<b style="padding-left:5px;color:#999">(${ca.count}张)</b>
									            	</a>
									            </div>
									            <div class="zxppg_right_content_desc">
									            	<c:if test='${ca.jiaOrgong=="1"}'><b style="color:#4bae36">[家装]&nbsp;</b></c:if>
									            	<c:if test='${ca.jiaOrgong=="2"}'><b style="color:#4bae36">[公装]&nbsp;</b></c:if>
									            	<c:if test='${ca.priceInterval=="1"}'>3万以下,</c:if>
									            	<c:if test='${ca.priceInterval=="2"}'>3-8万,</c:if>
									            	<c:if test='${ca.priceInterval=="3"}'>8-30万,</c:if>
									            	<c:if test='${ca.priceInterval=="4"}'>30万以下,</c:if>
									            	<c:set var="fenggeTypeIdStr" value="fenggeTypeId_${ca.fenggeTypeId}" scope="request"></c:set>
									            	<c:set var="fenggeType" value="${applicationScope.fenggeTypeMap[requestScope.fenggeTypeIdStr]}" scope="request"></c:set>
									            	<c:if test='${requestScope.fenggeType!=null}'>
									            		[装修风格:${requestScope.fenggeType.name}.]
									            	</c:if>
									            	<c:set var="huxingTypeIdStr" value="huxingTypeId_${ca.huxingTypeId}" scope="request"></c:set>
									            	<c:set var="huxingType" value="${applicationScope.huxingTypeMap[requestScope.huxingTypeIdStr]}" scope="request"></c:set>
									            	<c:if test='${requestScope.huxingType!=null}'>
									            		[户型:${requestScope.huxingType.name}.]
									            	</c:if>
									            </div>
									        </div>
			                        	</c:if>
		                        	</c:forEach>
							    </div>
							</div>
                                                                                                                                                                                                                                                                                        </div>
			    	</div>
			    </div>
			    
			    
			    
			</div>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<div class="newIndexBox nib ">
				<div class="nibTitle">
					<ul>
						<li><a href="javascript:void(0);" class="nibt_sjs_hot"></a></li>
						<li class="nibtitle_rigLi">汇聚 <b>${requestScope.shejishiNum}</b> 室内设计师</li>
					</ul>
				</div>
				<div class="m_l_y_z_y_title_line">
			        <div class="m_l_y_z_y_title_arrow_box" style="width: 140px; left: 0px;">
			            <div class="m_l_y_z_y_title_bg m_l_y_z_y_title_arrow"></div>
			        </div>
			    </div>
			    
			    <div class="nibCon">
			    	<div class="nibCon_l">
			    		<div class="sjsListBox">
			    			<ul>
			    				<c:forEach var="s" items="${cache:list(\"zhuangxiu\",\"index_bigAreaSjs\",\"Index\",10,sessionScope.addressId,null)}">
			    				<li>
			    					<div class="sjsTx">
			    						<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${s.addressId}" name="addressId"/>
													<jsp:param value="shop/${s.shopId}/sjs-${s.id}.html" name="link"/>
													<jsp:param value="<img src='${requestScope.webApp}img/shop/shejishi/min/${s.touxiang}'/>" name="aTitle"/>
													<jsp:param value="rslr_name" name="Aclass"/>
										</jsp:include>
			    					</div>
			    					<div class="sjsName">
			    						<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${s.addressId}" name="addressId"/>
													<jsp:param value="shop/${s.shopId}/sjs-${s.id}.html" name="link"/>
													<jsp:param value="${s.name}" name="aTitle"/>
													<jsp:param value="rslr_name" name="Aclass"/>
										</jsp:include>
			    					</div>
			    					<div class="sjsName">
			    						<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${s.addressId}" name="addressId"/>
													<jsp:param value="shop/${s.shopId}/" name="link"/>
													<jsp:param value="${fn:substring(s.shopName,0,11)}" name="aTitle"/>
													<jsp:param value="rslr_name" name="Aclass"/>
										</jsp:include>
			    					</div>
			    				</li>
			    				</c:forEach>
			    			</ul>
			    		</div>
			    	</div>
			    	<div class="nibCon_r" style="margin-top:8px;">
                        <div class="orderDivTop">
                        	<a href="javascript:void(0);" class="cur" onClick="tabSjs(this)" orderType="yuyue">最热设计师</a>
                        	<a href="javascript:void(0);" onClick="tabSjs(this)" orderType="caseAlbum">案例最多设计师</a>
                        </div>
                        <ul class="r_list" id="order_yuyue_sjs" style="display: block;">
                        	<c:forEach var="sjs" items="${cache:list(\"zhuangxiu\",\"order_sjs_yuyue\",\"ShejishiCache\",5,sessionScope.addressId,null)}" varStatus="vs">
                        		<c:if test='${vs.first}'>
		                        	<li class="first_thr">
										<div class="s_photo">
											<a href="javascript:void(0);" target="_blank" rel="nofollow">
												<img src="<%=request.getContextPath()%>/img/shop/shejishi/min/${sjs.touxiang}" width="60" height="60">
											</a>
										</div>
										<div class="order_num line_No${vs.index}"></div>
										<div class="s_data">
											<span class="s_name">
												<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${sjs.addressId}" name="addressId"/>
													<jsp:param value="shop/${sjs.shopId}/sjs-${sjs.id}.html" name="link"/>
													<jsp:param value="${sjs.name}" name="aTitle"/>
													<jsp:param value="rslr_name" name="Aclass"/>
												</jsp:include>
											</span>
											<span class="s_det" style="top:0px;">预约数:<b>${sjs.yuyueNum}</b></span>
										</div>
										<div class="cler"></div>
									</li>
								</c:if>
								<c:if test='${!vs.first}'>
									<li>
										<div class="s_photo">
											<a href="javascript:void(0);" target="_blank" rel="nofollow"><img src="<%=request.getContextPath()%>/img/shop/shejishi/min/${sjs.touxiang}" width="60" height="60"></a>
										</div>
										<div class="order_num line_No${vs.index}"></div>
										<div class="s_data">
											<span class="s_name">
												<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${sjs.addressId}" name="addressId"/>
													<jsp:param value="shop/${sjs.shopId}/sjs-${sjs.id}.html" name="link"/>
													<jsp:param value="${sjs.name}" name="aTitle"/>
													<jsp:param value="orderNOFirstName" name="Aclass"/>
												</jsp:include>
											</span>
											<span class="s_det">预约数：<b>${sjs.yuyueNum}</b></span>
										</div>
										<div class="cler"></div>
									</li>
								</c:if>
                        	</c:forEach>
						</ul>
						<ul class="r_list" id="order_caseAlbum_sjs" style="display: none;">
                        	<c:forEach var="sjs" items="${cache:list(\"zhuangxiu\",\"order_sjs_caseAlbum\",\"ShejishiCache\",5,sessionScope.addressId,null)}" varStatus="vs">
                        		<c:if test='${vs.first}'>
		                        	<li class="first_thr">
										<div class="s_photo">
											<a href="javascript:void(0);" target="_blank" rel="nofollow">
												<img src="<%=request.getContextPath()%>/img/shop/shejishi/min/${sjs.touxiang}" width="60" height="60">
											</a>
										</div>
										<div class="order_num line_No${vs.index}"></div>
										<div class="s_data">
											<span class="s_name">
												<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${sjs.addressId}" name="addressId"/>
													<jsp:param value="shop/${sjs.shopId}/sjs-${sjs.id}.html" name="link"/>
													<jsp:param value="${sjs.name}" name="aTitle"/>
													<jsp:param value="rslr_name" name="Aclass"/>
												</jsp:include>
											</span>
											<span class="s_det" style="top:0px;">案例数:<b>${sjs.caseNum}</b></span>
										</div>
										<div class="cler"></div>
									</li>
								</c:if>
								<c:if test='${!vs.first}'>
									<li>
										<div class="s_photo">
											<a href="javascript:void(0);" target="_blank" rel="nofollow"><img src="<%=request.getContextPath()%>/img/shop/shejishi/min/${sjs.touxiang}" width="60" height="60"></a>
										</div>
										<div class="order_num line_No${vs.index}"></div>
										<div class="s_data">
											<span class="s_name">
												<jsp:include page="util/linkAddressObjectSelf.jsp">
													<jsp:param value="${sjs.addressId}" name="addressId"/>
													<jsp:param value="shop/${sjs.shopId}/sjs-${sjs.id}.html" name="link"/>
													<jsp:param value="${sjs.name}" name="aTitle"/>
													<jsp:param value="orderNOFirstName" name="Aclass"/>
												</jsp:include>
											</span>
											<span class="s_det">案例数：<b>${sjs.caseNum}</b></span>
										</div>
										<div class="cler"></div>
									</li>
								</c:if>
                        	</c:forEach>
						</ul>
						
						
						
						
						
						
						
						
						
						
						
						
						
			    	</div>
			    </div>
			    
			    
			    
			</div>
			
			
			
			
			
			
			
			
			
			
			
			<div class="block index_line_ad">
				<ul>
				<li class="fl">
					<jsp:include page="adverTemplate.jsp">
            			<jsp:param value="1-14" name="markeNums"/>
            		</jsp:include>
				<li class="fr">
					<jsp:include page="adverTemplate.jsp">
            			<jsp:param value="1-15" name="markeNums"/>
            		</jsp:include>
				</ul>
			</div>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<div class="new_list limit_w">
        		<dl>
            		<dt><a target="_blank" href="javascript:void(0);" id="zb_url"><i class="new_zb"></i>最新招标</a></dt>
            		<c:forEach var="t" items="${cache:list(\"zhuangxiu\",\"index_bigAreaTask\",\"Index\",7,sessionScope.addressId,null)}" varStatus="vs">
            		 	<c:if test='${vs.index<7}'>
            		 	<dd>
            		 		<c:set var="taskAddIdStr" value="addId_${t.addressId}" scope="request"></c:set>
            		 		<c:set var="curTaskAdd" value="${applicationScope.addressAll_map[requestScope.taskAddIdStr]}" scope="request"></c:set>
            		 		<a href="javascript:void(0);" target="_blank">${requestScope.curTaskAdd.allName} ${t.xiaoquName}</a>
            		 		<span>${fn:substring(t.relDate,0,10)}</span>
            		 	</dd>
            		 	</c:if>
            		</c:forEach>
                </dl>
        		<dl class="white_bg">
            		<dt><a target="_blank" href="<%=request.getContextPath()%>/baike/" id="bbs_url"><i class="new_bbs"></i>装修热门资讯</a></dt>
            		<c:forEach var="n" items="${cache:list(\"zhuangxiu\",\"newsIndex_mustNew\",\"LoadNewsList\",10,0,null)}" varStatus="vs">
						<c:if test='${vs.index<7}'>
							 <dd>
							 	<jsp:include page="util/newsLink.jsp">
									<jsp:param value="${n.newsTypeROOTId}" name="rootId"/>
									<jsp:param value="${n.id}" name="newsId"/>
									<jsp:param value="${n.title}" name="title"/>
								</jsp:include>
							 	<span>${fn:substring(n.relDate,0,10)}</span>
							 </dd>
						</c:if>
					</c:forEach>
               </dl>
        		<dl>
            		<dt><a target="_blank" href="<%=request.getContextPath()%>/ask/"><i class="new_ask"></i>室内装修常见问题</a></dt>
            		<c:forEach var="q" items="${cache:list(\"zhuangxiu\",\"ask_digHot\",\"Ask\",8,0,null)}" varStatus="vs">
            			<dd>
							<a href="<%=request.getContextPath()%>/ask/question${q.id}.html" class="ask">${fn:substring(q.name,0,19)}</a>
            				<span>${fn:substring(q.relDate,0,10)}</span></dd>
            		</c:forEach>
               </dl>
    		</div>
			
			
			
			
			
			
			
			
			
			<div class="friend_link limit_w mt15">
        		<div class="dashed_bg"></div>
        		<div class="friend_link_list">
        			<c:forEach var="l" items="${applicationScope.linkList_city}">
        				<c:if test='${sessionScope.addressId==l.addressId}'>
        					<a target="_blank" href="${l.herf}">${l.name}</a>
        				</c:if>
        			</c:forEach>
                </div>
    		</div>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		</div>
	</div>
	<jsp:include page="ifooter.jsp"></jsp:include>
</div>

</body>
</html>
