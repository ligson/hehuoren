<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
$(document).ready(function(){
	$("#menuBar").find("a").removeClass("selected");
	var curVal=$("#leftMenuInput").val();
	var curA=$("#"+curVal);
	curA.addClass("selected");
});
</script>
</head>

<body >
<input type="hidden" value="${param.leftMenu}" id="leftMenuInput" />
				<div class="menu_box" id="menuBar">
					<dl class="menu no_extra">    
						<dd class="menu_item">
							<a href="<%=request.getContextPath()%>/admin/index" id="index">我的首页</a>
						</dd>
					</dl>
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu apply"></i>工作台    
						</dt> 
						<dd class="menu_item ">
							<a href="<%=request.getContextPath()%>/admin/apply/findApplys?queryArgs=[{'key':'applyTypeKey','value':'AccountTXApply'}]" id="apply-apply">审批大厅</a>
						</dd>
					</dl>
					
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu order" ></i>订单管理           
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/order/finds?queryArgs=[{'key':'status','value':'WAITPAY'}]" class="" id="order-order">订单管理</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/pua/finds?fromDB=no" class="" id="order-ztd">自提点管理</a>
						</dd>
					</dl>
					
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu auction" ></i>产品管理        
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/product/finds"  id="auction-product">产品列表</a>
						</dd>
					</dl>
					
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu account"></i>账户管理   
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/account/index"  id="web-account">我的账户</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/account/toOperation"  id="account-account">账户管理</a>
						</dd>
					</dl>
					
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu user"></i>用户管理   
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/user/finds?queryArgs=[{'key':'userLevelId','value':'1'}]" id="user-user">用户列表</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/order/findAllShouhuoren" id="user-order">购买名单</a>
						</dd>
					</dl>
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu webset"></i>系统
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/global/findAllGlobal" id="setup-system">系统设置</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/dataBackup/findAlls" id="sys-tools">系统工具</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/security/toPassword" id="admin-security">账号安全</a>
						</dd>
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/history/finds?historyType=LOGIN" id="admin-history">安全记录</a>
						</dd>
					</dl>
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu help"></i>微信帮助资讯
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/admin/news/finds" id="news">微信帮助资讯</a>
						</dd>
					</dl>
					<dl class="menu ">    
						<dt class="menu_title">        
							<i class="icon_menu help"></i>帮助文档
						</dt> 
						<dd class="menu_item">
							<a  href="<%=request.getContextPath()%>/manage/public/help/apply.jsp" target="_blank" id="apply">帮助文档</a>
						</dd>
					</dl>
				</div>
</body>
</html>