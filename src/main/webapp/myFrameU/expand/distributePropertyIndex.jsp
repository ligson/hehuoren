<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>分配属性</title>
<style type="text/css">
body,html{padding:0px;margin:0px;}
table{width:100%}
table thead tr td{background-color:#ccc;padding:5px;font-weight: bold;border-right:1px solid #ddd;}
table tbody tr td{padding:3px;border-bottom:1px solid #fff;}
#left,#right{float:left;height:1000px;display:block;overflow: hidden;}
#left{width:18%;border-right:2px solid #000;background-color:#eee;}
#left ul{overflow: hidden;height:auto;padding:0px;}
#left ul li{display:block;float:left;height:auto;border-bottom:1px solid #ccc;width:100%}
#left ul li a{display:block;float:left;height:100%;line-height: 20px;text-decoration: none;color:#333;padding:10px;width:100%}
#left ul li a:hover,.currentLeftA{background:#000;color:#fff !important;}
#right{width:80%;float:right}
#rightTitle{border-bottom:1px solid #ddd;width:100%;height:50px;margin-bottom:10px;}
#rightTip{width:100%;height:30px;color:red;font-weight: bold;}
.tip{display:none}
</style>
<script type="text/javascript" src="http://www.024lm.com/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
function findDistribute(a){
	var url=$(a).attr("url");
	$.ajax( {
		type : "POST",
		url : url,
		success : function(msg) {
			$(".tip").hide();
			var dRange=$(a).attr("dRange");
			if(dRange=="ALL"){
				$("#dRangeAll").show();
			}else{
				$("#dRangeNotAll").show();
			}
			$("#left").find("li a").removeClass("currentLeftA");
			$(a).addClass("currentLeftA");
			$("#rightContent").html(msg);
			$("#rightTitle h2").html($(a).html());
		}
	})
}
</script>
</head>
<body>

<div id="left">
	<ul>
		<c:forEach var="due" items="${requestScope.distribute_dbuList}">
			<li><a dRange="${due.dRange}" href="javascript:void(0);" onClick="findDistribute(this)" url="http://localhost:8080/yishupaipai/disProperty/findDistribute?className=${due.className}&dRange=${due.dRange}">${due.text}</a></li>
		</c:forEach>
	</ul>
</div>
<div id="right">
	<div id="rightTitle"><h2></h2></div>
	<div id="rightTip">
		<span id="dRangeAll" class="tip">为类全部数据分配属性时：所有属性都可以分配/取消分配</span>
		<span id="dRangeNotAll" class="tip">为类部分数据分配属性时：所属类的全部数据的property和propertyValue不可取消，但可以追加自己的propertyValue</span>
	</div>
	<div id="rightContent">
		
	</div>
</div>

	
</body>
</html>