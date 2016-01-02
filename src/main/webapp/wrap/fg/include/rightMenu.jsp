<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
    
    
    				<c:set var="g23Name" value="webUrl" scope="request"></c:set>
					<c:set var="g23" value="${applicationScope.globalMap[requestScope.g23Name]}"></c:set>
					<c:set var="webUrlVAR" value="${g23.myValue}"></c:set>
    
<!--菜单部分-->
			<aside id="offCanvasSide" class="mui-off-canvas-right">
				<div id="offCanvasSideScroll" class="mui-scroll-wrapper">
					<div class="mui-scroll">
						<div class="content">
							<p style="margin: 10px 15px;">
								<button id="offCanvasHide" type="button" class="mui-btn mui-btn-danger mui-btn-block" style="padding: 5px 20px;" >关闭侧滑菜单</button>
							</p>
						</div>
						<ul class="mui-table-view mui-table-view-chevron mui-table-view-inverted">
							<li class="mui-table-view-cell">
								<a class="mui-navigate-right"  style="color:#fff;" href="<%=request.getContextPath()%>/wrap/user/cart/myCart">
									我的购物车
								</a>
							</li>
							<li class="mui-table-view-cell">
								<a class="mui-navigate-right" href="<%=request.getContextPath()%>/wrap/index" style="color:#fff;">
									商城首页
								</a>
							</li>
							
							<li class="mui-table-view-cell">
								<a class="mui-navigate-right"  style="color:#fff;" href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx247c7e42bcde403d&redirect_uri=${webUrlVAR}wrap/toUserRegist&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect">
									注册用户
								</a>
							</li>
							
							<c:if test='${empty sessionScope.myUser}'>
								<li class="mui-table-view-cell">
									<a class="mui-navigate-right"  style="color:#fff;" href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx247c7e42bcde403d&redirect_uri=${webUrlVAR}wrap/toUserLogin&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect">
										用户登录
									</a>
								</li>
							</c:if>
							<c:if test='${!empty sessionScope.myUser}'>
								<li class="mui-table-view-cell">
									<a class="mui-navigate-right"  style="color:#fff;" href="<%=request.getContextPath()%>/wrap/user/index">
										用户后台管理中心
									</a>
								</li>
							</c:if>
							<li class="mui-table-view-cell">
								<a class="mui-navigate-right"  style="color:#fff;" href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx247c7e42bcde403d&redirect_uri=${webUrlVAR}wrap/user/my2wm&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect">
									专属二维码
								</a>
							</li>
							<li class="mui-table-view-cell">
								<a class="mui-navigate-right"  style="color:#fff;" href="<%=request.getContextPath()%>/wrap/fg/help/help-hhr.jsp">
									合伙人论坛
								</a>
							</li>
							<li class="mui-table-view-cell">
								<a class="mui-navigate-right"  style="color:#fff;" href="<%=request.getContextPath()%>/wrap/fg/help/help-hhr.jsp">
									合伙人帮助
								</a>
							</li>
						</ul>
					</div>
				</div>
			</aside>