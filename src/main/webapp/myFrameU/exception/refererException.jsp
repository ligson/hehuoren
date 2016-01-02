<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	var msg = $("#errorMessageInput").val();
	if(msg!=null && msg!=""){
		myAlert(200,130,"失败!",msg,"error");
	}
	
});
</script>
</head>
<body>
<input type="hidden" value="${sessionScope.errorMessage}" id="errorMessageInput"/>
<%
session.removeAttribute("errorMessage");
%>
</body>
</html>