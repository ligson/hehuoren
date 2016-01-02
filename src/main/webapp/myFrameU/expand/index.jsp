<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>扩展属性首页</title>
<style type="text/css">
.div{display:block;float:left;width:400px;height:100px;background:#eee;margin:50px;}
.div a{display:block;float:left;width:100%;height:100%;color:#000;text-align: center;line-height: 100px}
.div a:hover{background:green;color:#fff;}
</style>
</head>
<body>
		<c:if test='${!empty sessionScope.myAdmin}'>
			<div class="div">
				<a href="http://localhost:8080/yishupaipai/sysProperty/findAllPropertys">系统库维护</a>
			</div>
			<div class="div">
				<a href="http://localhost:8080/yishupaipai/disProperty/index">分配属性</a>
			</div>
		</c:if>
</body>
</html>