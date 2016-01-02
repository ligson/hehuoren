<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/manage/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var endDateStr="2015/06/25 03:23:00";
	
	alert(endDateStr);
	var milliseconds=Date.parse(endDateStr);
	alert(milliseconds);
});
</script>
</head>
<body>

</body>
</html>