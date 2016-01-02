<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<table  cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td colspan="9" style="text-align: left;">订单信息</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				订单状态：
				<c:if test='${requestScope.order.status=="CLOSE"}'>已关闭</c:if>
				<c:if test='${requestScope.order.status=="WAITPAY"}'>待付款</c:if>
				<c:if test='${requestScope.order.status=="PAYED"}'>已付款</c:if>
				<c:if test='${requestScope.order.status=="PICKUPED"}'>已取货</c:if>
			</td>
			<td>
				下单时间：<fmt:formatDate value="${requestScope.order.createDate}" pattern="yy-MM-dd HH:mm" />
			</td>
			
			<td>
				
			</td>
			<td>
				<p></p>
				<p>
					
				</p>
			</td>
		</tr>
	</tbody>
</table>        

<table  cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td colspan="3" style="text-align: left;">订单买家信息</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td></td>
			<td>
				
			</td>
			<td>
				
			</td>
		</tr>
	</tbody>
</table>        

<table  cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td colspan="5" style="text-align: left;">推荐人</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td></td>
			<td>
				
			</td>
			<td>
				
			</td>
			<td>
				
			</td>
		</tr>
	</tbody>
</table>        


<c:if test='${!empty requestScope.accountItem}'>
<table  cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td colspan="5" style="text-align: left;">订单级联的财务明细</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>编号：${requestScope.accountItem.markedNum}</td>
			<td>
				交易类型：支付
			</td>
			<td>
				交易时间：
				<fmt:formatDate value="${requestScope.accountItem.relDate}" pattern="yy-MM-dd HH:mm" />
			</td>
			<td>
				交易金额：${fn:replace(requestScope.accountItem.price,".0","")}
			</td>
			<td>
				交易状态：
				<c:if test='${requestScope.accountItem.status=="FINISH"}'>完成</c:if>
			</td>
		</tr>
	</tbody>
</table>        
</c:if>