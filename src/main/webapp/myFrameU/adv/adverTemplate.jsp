<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="advertingMn" value="${param.markeNums}" scope="request"></c:set>
<c:set var="curAdvering" value="${applicationScope.advertisingMap[requestScope.advertingMn]}" scope="request"></c:set>
<c:set value="${applicationScope.advertisementMap[param.markeNums]}" var="aList"></c:set>



<c:if test='${requestScope.curAdvering.haveSon=="NO"}'>
	<c:if test='${requestScope.curAdvering.width100==1}'>
		<!-- width为100%的 -->
		<c:forEach var="a" items="${aList}">
			<c:if test='${a.status=="ING"}'>
									<c:if test='${a.advType=="IMAGE"}'>
										<!-- 普通图片 -->
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img class="width100Image"  src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img class="width100Image"  src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
									</c:if>
									<c:if test='${a.advType=="FOUCS"}'>
										<!-- 焦点广告 -->
										<c:if test='${a.picA==null}'>
											${param.prefix}<a href="javascript:void(0);" target="_blank"  class="bgFocusA" style="background:url('<%=request.getContextPath()%>/${a.picUrl}') no-repeat scroll center center transparent;"
												smallImage="" >
											</a>${param.suffix}
										</c:if>
										<c:if test='${a.picA!=null}'>
											${param.prefix}<a href="${a.picA}" target="_blank"  class="bgFocusA" style="background:url('<%=request.getContextPath()%>/${a.picUrl}') no-repeat scroll center center transparent;"
												smallImage="" >
											</a>${param.suffix}
										</c:if>
									</c:if>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test='${requestScope.curAdvering.width100==0}'>
		<!-- width不为100%的 -->
		<c:forEach var="a" items="${aList}">
			<c:if test='${a.status=="ING"}'>
									<c:if test='${a.advType=="IMAGE"}'>
										<!-- 普通图片 -->
										<c:if test='${requestScope.curAdvering.width!=0 && requestScope.curAdvering.height!=0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img width="${requestScope.curAdvering.width}px" height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img width="${requestScope.curAdvering.width}px" height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
										<c:if test='${requestScope.curAdvering.width==0 && requestScope.curAdvering.height!=0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img  height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img  height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
										<c:if test='${requestScope.curAdvering.width!=0 && requestScope.curAdvering.height==0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img width="${requestScope.curAdvering.width}px"  src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img width="${requestScope.curAdvering.width}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
										
										<c:if test='${requestScope.curAdvering.width==0 && requestScope.curAdvering.height==0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
									</c:if>
									<c:if test='${a.advType=="FOUCS"}'>
										<!-- 焦点广告 -->
										<c:if test='${a.picA==null}'>
											${param.prefix}<a href="javascript:void(0);" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
										</c:if>
										<c:if test='${a.picA!=null}'>
											${param.prefix}<a href="${a.picA}" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
										</c:if>
									</c:if>
			</c:if>
		</c:forEach>
	</c:if>

</c:if>


<c:if test='${requestScope.curAdvering.haveSon=="YES"}'>
	<c:if test='${requestScope.curAdvering.width100==1}'>
		<!-- width为100%的 -->
		<c:forEach var="a" items="${aList}">
			<c:if test='${a.sonByValue==param.productType}'>
				<c:if test='${a.status=="ING"}'>
									<c:if test='${a.advType=="IMAGE"}'>
										<!-- 普通图片 -->
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img class="width100Image"  src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img class="width100Image"  src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
									</c:if>
									<c:if test='${a.advType=="FOUCS"}'>
										<!-- 焦点广告 -->
										<c:if test='${a.picA==null}'>
											${param.prefix}<a href="javascript:void(0);" target="_blank"  class="bgFocusA" style="background:url('<%=request.getContextPath()%>/${a.picUrl}') no-repeat scroll center center transparent;"
												smallImage="" >
											</a>${param.suffix}
										</c:if>
										<c:if test='${a.picA!=null}'>
											${param.prefix}<a href="${a.picA}" target="_blank"  class="bgFocusA" style="background:url('<%=request.getContextPath()%>/${a.picUrl}') no-repeat scroll center center transparent;"
												smallImage="" >
											</a>${param.suffix}
										</c:if>
									</c:if>
				</c:if>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test='${requestScope.curAdvering.width100==0}'>
		<!-- width不为100%的 -->
		<c:forEach var="a" items="${aList}">
			<c:if test='${a.sonByValue==param.productType}'>
				<c:if test='${a.status=="ING"}'>
								<c:if test='${a.advType=="IMAGE"}'>
										<!-- 普通图片 -->
										<c:if test='${requestScope.curAdvering.width!=0 && requestScope.curAdvering.height!=0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img width="${requestScope.curAdvering.width}px" height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img width="${requestScope.curAdvering.width}px" height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
										<c:if test='${requestScope.curAdvering.width==0 && requestScope.curAdvering.height!=0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img  height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img  height="${requestScope.curAdvering.height}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
										<c:if test='${requestScope.curAdvering.width!=0 && requestScope.curAdvering.height==0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img width="${requestScope.curAdvering.width}px"  src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img width="${requestScope.curAdvering.width}px" src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
										
										<c:if test='${requestScope.curAdvering.width==0 && requestScope.curAdvering.height==0}'>
											<c:if test='${a.picA==null}'>
												${param.prefix}<a href="javascript:void(0);" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
											<c:if test='${a.picA!=null}'>
												${param.prefix}<a href="${a.picA}" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
											</c:if>
										</c:if>
									</c:if>
									<c:if test='${a.advType=="FOUCS"}'>
										<!-- 焦点广告 -->
										<c:if test='${a.picA==null}'>
											${param.prefix}<a href="javascript:void(0);" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
										</c:if>
										<c:if test='${a.picA!=null}'>
											${param.prefix}<a href="${a.picA}" target="_blank"><img src="<%=request.getContextPath()%>/${a.picUrl}" alt="${a.picTitle}" /></a>${param.suffix}
										</c:if>
									</c:if>
				</c:if>
			</c:if>
		</c:forEach>
	</c:if>

</c:if>






