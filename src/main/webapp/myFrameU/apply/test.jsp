<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试申请</title>
</head>
<body>
	<a href="http://localhost:8080/yishupaipai/user/apply/addApplySimple?applyTypeKey=joinExhibition&apply_extraData={'zhanhuiId':'4','dsId':'3','sssId':'v'}">提交一个申请</a>
	<a href="http://localhost:8080/yishupaipai/admin/apply/findById?id=1">查看单个申请</a>
	
	<a href="http://localhost:8080/yishupaipai/admin/apply/approval?id=1&result=APPLYOK">审批ID=1的申请为通过</a>
	<a href="http://localhost:8080/yishupaipai/admin/apply/approval?id=1&result=APPLYERROR">审批ID=1的申请为不通过</a>
</body>
</html>