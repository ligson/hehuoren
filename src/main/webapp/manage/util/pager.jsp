<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="myFrameU.pager.pager.Pager" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%
	
%>

<c:if test='${requestScope.pager.totalPages==0}'>
						<!-- 如果就一页,就没有必要显示了 -->
					</c:if>
<c:if test='${requestScope.pager.totalPages==1}'>
						<!-- 如果就一页,就没有必要显示了 -->
					</c:if>
					<c:if test='${requestScope.pager.totalPages>1}'>
						<c:if test='${requestScope.pager.currentPage==1}'>
							<!-- 如果总页数大于1页，且此时第一页，则上一页无效，首页无效 -->
							<div class="page_style">
								
								<a class="needPIE" title="尾页" href="${requestScope.pager.lastUrl}">  尾   页   </a> 
								<a class="needPIE" title="下一页" href="${requestScope.pager.nextUrl}">下一页</a>
								<a class="page_style_txt">
								第${requestScope.pager.currentPage}/${requestScope.pager.totalPages}页 共${requestScope.pager.totalRows}条记录 &nbsp;&nbsp;|&nbsp;&nbsp;
								首页&nbsp;&nbsp;
								上一页&nbsp;&nbsp;
								</a>
							</div>
						</c:if>
						<c:if test='${requestScope.pager.currentPage>1 && requestScope.pager.currentPage<requestScope.pager.totalPages}'>
							<!-- 如果总页数大于1页,且此时不在第一页,也不在最后一页上,则都有效 -->
							<div class="page_style">
								<span class="page_style_txt">
									第${requestScope.pager.currentPage}/${requestScope.pager.totalPages}页 共${requestScope.pager.totalRows}条记录 &nbsp;&nbsp;|&nbsp;&nbsp;
								</span>
								
								<a class="needPIE" title="首页" href="${requestScope.pager.indexUrl}">首页</a> 
								<a class="needPIE" title="上一页" href="${requestScope.pager.prevUrl}">上一页</a>
								<a class="needPIE" title="下一页" href="${requestScope.pager.nextUrl}">下一页</a>
								<a class="needPIE" title="尾页" href="${requestScope.pager.lastUrl}">尾页</a> 
							</div>
						</c:if>
						<c:if test='${requestScope.pager.currentPage==requestScope.pager.totalPages}'>
							<!-- 如果总页数大于1页面,且此时在最后一页,则尾页和下一页无效 -->
							<div class="page_style">
								<span class="page_style_txt">
									第${requestScope.pager.currentPage}/${requestScope.pager.totalPages}页 共${requestScope.pager.totalRows}条记录 &nbsp;&nbsp;|&nbsp;&nbsp;
								</span>
								
								<a class="needPIE" title="首页" href="${requestScope.pager.indexUrl}">首页</a> 
								<a class="needPIE" title="上一页" href="${requestScope.pager.prevUrl}">上一页</a>
								<span class="page_style_txt" style="float:right;">
									下一页&nbsp;&nbsp;
									尾页&nbsp;&nbsp;
								</span>
								
							</div>
						</c:if>
					</c:if>
</body>
</html>