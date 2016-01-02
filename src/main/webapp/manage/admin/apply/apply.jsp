<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 


<style>
	.apply_xq_tab{width:100%;height:30px;margin-top:10px;margin-bottom:10px;}
	.apply_xq_tab ul{display:block;float:left;width:100%}
	.apply_xq_tab ul li{display:block;float:left;width:33.3%;}
	.apply_xq_tab ul li a{display:block;float:left;width:100%;height:100%;line-height: 30px;text-align: center;
		background:#eee;
	}
	.apply_xq_tab ul li a:hover,.apply_xq_tab ul li a.selected{background:orange;color:#fff;}
	.apply_xq_conBox{display:none;}
	#apply_xq_conBox_1{display:block;}
	.apply_xq_conBox table tr td{padding:5px;}
	.apply_xq_conBox table thead tr td{background:#eee;padding:5px;}
</style>
<script type="text/javascript">
	
</script>


        <div id="applyBox">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td colspan="4">申请详情</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="k">申请类型</td>
						<td class="v">
							 <c:if test='${requestScope.apply.applyTypeKey=="AccountTXApply"}'> 财务提现申请</c:if>
							 <c:if test='${requestScope.apply.applyTypeKey=="UserLevelHHR"}'> 合伙人申请</c:if>
						</td>
						<td class="k">申请时间</td>
						<td class="v">${requestScope.apply.relDate}</td>
					</tr>
					<tr>
						<td class="k">申请人类型</td>
						<td class="v">
							<c:if test='${requestScope.apply.sponsorClassName=="myFrameU.shop.entity.Shop"}'>
								商铺
							</c:if>
							<c:if test='${requestScope.apply.sponsorClassName=="myFrameU.user.entity.User"}'>
								用户
							</c:if>
						</td>
						<td class="k">申请人</td>
						<td class="v">
							<c:if test='${requestScope.apply.sponsorClassName=="myFrameU.shop.entity.Shop"}'>
								<a href="<%=request.getContextPath()%>/fshop/${requestScope.apply.sponsorId}/" target="_blank">${requestScope.apply.sponsorName}</a>
							</c:if>
							<c:if test='${requestScope.apply.sponsorClassName=="myFrameU.user.entity.User"}'>
								${requestScope.apply.sponsorName}
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="k">审批进度</td>
						<td class="v">
							<c:if test='${requestScope.apply.speed=="WAIT" || requestScope.apply.speed=="ING"}'>
								等待审批
							</c:if>
							<c:if test='${requestScope.apply.speed=="FINISH"}'>
								审批结束
							</c:if>
						</td>
						<td class="k">审批结果</td>
						<td class="v">
							<c:if test='${requestScope.apply.result=="APPLYOK"}'>
								审批通过
							</c:if>
							<c:if test='${requestScope.apply.result=="APPLYERROR"}'>
								审批不通过
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="k">申请内容</td>
						<td class="v" colspan="4">
							<p style="color:red">${requestScope.apply.applyContent}</p>
						</td>
					</tr>
					<tr>
						<td class="k">审批批复</td>
						<td class="v" colspan="4">
							<c:if test='${requestScope.apply.speed=="WAIT" || requestScope.apply.speed=="ING"}'>
								<textarea id="remarksTxt" style="width:400px;height:50px;font-size:14px;" >${requestScope.apply.remarks}</textarea>
							</c:if>
							<c:if test='${requestScope.apply.speed=="FINISH"}'>
								<textarea id="remarksTxt" style="width:400px;height:50px;font-size:14px;" readonly="readonly">${requestScope.apply.remarks}</textarea>
							</c:if>
						</td>
					</tr>
					<c:if test='${requestScope.apply.speed=="WAIT" || requestScope.apply.speed=="ING"}'>
						<tr>
							<td class="k"></td>
							<td class="v" colspan="4">
								<a applyId="${requestScope.apply.id}" href="javascript:void(0);" class="btn btnColor_1" onClick="apply(this)" result="APPLYOK">审批通过</a>
								<a applyId="${requestScope.apply.id}" href="javascript:void(0);" class="btn btnColor_1" onClick="apply(this)" result="APPLYERROR">审批不通过</a>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			
			
			<c:if test='${requestScope.apply.applyTypeKey=="AccountTXApply"}'>
				<div class="apply_xq_tab">
					<ul>
						<li>
							<a class="selected" href="javascript:void(0);" contentBoxId="apply_xq_conBox_1" onClick="qiehuan(this)">提现资金</a>
						</li>
						<li>
							<a href="javascript:void(0);"  contentBoxId="apply_xq_conBox_2" onClick="qiehuan(this)">他的粉丝们</a>
						</li>
						<li>
							<a href="javascript:void(0);"  contentBoxId="apply_xq_conBox_3" onClick="qiehuan(this)">粉丝们的订单</a>
						</li>
					</ul>
				</div>
				<div class="apply_xq_con">
					<div class="apply_xq_conBox" id="apply_xq_conBox_1">
						<table>
									<tbody>
										<tr>
											<td>总余额(${requestScope.account.totalPrice})</td>
											<td>可用额(${requestScope.account.availablePrice})</td>
											<td>冻结额(${requestScope.account.frozenPrice})</td>
										</tr>
									</tbody>
								</table>
					</div>
					<div class="apply_xq_conBox" id="apply_xq_conBox_2">
						<table>
							<thead>
								<tr>
									<td>ID</td>
									<td>名字</td>
									<td>电话</td>
									<td>地区</td>
									<td>级别</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="u" items="${requestScope.fensiList}">
									<tr>
										<td>${u.id}</td>
										<td>${u.nicheng}</td>
										<td>${u.name}</td>
										<td>
											<c:set var="addIdStr" value="${u.addressId}"></c:set>
											<c:set var="add" value="${applicationScope.addressList_all[addIdStr]}"></c:set>
											${add.allName}
										</td>
										<td>
											<c:if test='${u.userLevelId=="1"}'>普通</c:if>
											<c:if test='${u.userLevelId=="2"}'>试用期</c:if>
											<c:if test='${u.userLevelId=="3"}'>正式合伙人</c:if>
										</td>
									</tr>									
								</c:forEach>
							</tbody>
						</table>			
					</div>
					<div class="apply_xq_conBox" id="apply_xq_conBox_3">
						<table>
							<thead>
								<tr>
									<td>编号</td>
									<td>状态</td>
									<td>金额</td>
									<td>给推荐人</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="o" items="${requestScope.fensiOrderList}">
									<tr>
										<td>${o.markedNum}</td>
										<td>
											<c:if test='${o.status=="CLOSE"}'>已关闭</c:if>
											<c:if test='${o.status=="WAITPAY"}'>待付款</c:if>
											<c:if test='${o.status=="PAYED"}'>已付款待自提</c:if>
											<c:if test='${o.status=="PAYING"}'>自提待付款</c:if>
											<c:if test='${o.status=="PICKUPED"}'>已自提</c:if>
										</td>
										<td>${o.totalPrice}</td>
										<td>
											${o.toHehuorenPrice}
										</td>
									</tr>									
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
			
			
		</div>
        