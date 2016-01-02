<%@ page language="java" import="myFrameU.queryArgs.page.LinkInfo" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String queryArgs=(String)request.getAttribute("queryArgs");
	List<LinkInfo> linkInfoList = new ArrayList<LinkInfo>();
	String[] linkInfos = request.getParameterValues("linkInfos");
	int len=linkInfos.length;
	String linkInfo=null;
	String[] lis=null;
	String liks=null;
	for(int i=0;i<len;i++){
		linkInfo=linkInfos[i];
		lis=linkInfo.split("##");//link text className selected
		if(null!=lis){
			LinkInfo li = new LinkInfo();
			liks=lis[0];
			li.setLink(liks);
			li.setText(lis[1]);
			li.setClassName(lis[2]);
			if(liks.contains(queryArgs)){
				li.setSelected("YES");
			}else{
				li.setSelected("NO");
			}
			linkInfoList.add(li);
		}
	}
	request.setAttribute("linkInfoList", linkInfoList);
	
%>
<c:forEach var="l" items="${requestScope.linkInfoList}">
	<c:if test='${l.selected=="YES"}'>
		<a href="<%=request.getContextPath()%>/${l.link}" class="${l.className} ${param.selectedClass}">${l.text}</a>
	</c:if>
	<c:if test='${l.selected=="NO"}'>
		<a href="<%=request.getContextPath()%>/${l.link}" class="${l.className} ">${l.text}</a>
	</c:if>
</c:forEach>
    