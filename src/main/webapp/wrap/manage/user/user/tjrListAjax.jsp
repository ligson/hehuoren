<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    <div class="qykBox">
    	<div class="qykHead">
    		<h3>选定推荐人</h3>
    	</div>
    	<div class="qykBody">
    		<div class="qykBodyI">
    			<ul class="mui-table-view mui-table-view-chevron">
    				<c:forEach var="u" items="${requestScope.userList}">
    					<c:if test='${u.id==sessionScope.myUser.id}'>
    						<div class="wrapTipBox">
								<div class="wrapTipBoxI">
									<p>对不起，不能选择自己为推荐人</p>
								</div>
							</div>
    					</c:if>
    					<c:if test='${u.id!=sessionScope.myUser.id}'>
    						<c:if test='${u.userLevelId==1}'>
    							<div class="wrapTipBox">
									<div class="wrapTipBoxI">
										<p>对不起，该用户为普通用户，并非合伙人</p>
									</div>
								</div>
    						</c:if>
    						<c:if test='${u.userLevelId!=1}'>
    							<li class="mui-table-view-cell mui-media">
									<a class="mui-navigate-right" href="javascript:void(0);">
										<img class="mui-media-object mui-pull-left" src="<%=request.getContextPath()%>/${u.touxiang}">
										<div class="mui-media-body">
											${u.nicheng}
											<p class='mui-ellipsis'>
												级别：
												<c:if test='${u.userLevelId=="2"}'>试用期合伙人</c:if>
												<c:if test='${u.userLevelId=="3"}'>正式合伙人</c:if>
											</p>
										</div>
									</a>
								</li>
								<li>
									<button id='showCZPicker'  class="mui-btn mui-btn-block mui-btn-primary mui-btn-danger" onClick="selectTJR(this)" tjrId="${u.id}">选定他为您的推荐人</button>
								</li>
    						</c:if>
    					</c:if>
    				</c:forEach>
				</ul>
														
    		</div>
    	</div>
    	<div class="qykFoot">
    		<div class="qykFootI">
				<p class="tip">
					注意如果选定的推荐人为试用期合伙人，则试用期过后，判断是否可以转正，如果不能转正则你们之间的推荐人粉丝关系被接触。
				</p>
    		</div>
    	</div>
    </div>