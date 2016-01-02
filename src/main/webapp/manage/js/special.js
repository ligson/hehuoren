$(document).ready(function(){
	$("#specialListBox").delegate(".shenheshibai","mouseover",function(){
		var sb=$(this);
		var specialId = sb.attr("specialId");
		var msg=sb.attr("applyRmaker");
		var xialaAlert=$("#xialaAlert_ID_"+specialId);
		xialaAlert.find(".xla_body").html(msg);
		xialaAlert.show();
		xialaAlert.mouseout(function(){
			//$(this).hide();
		});
	});
	$(".xla_close").click(function(){
		var c=$(this);
		var a=c.parents(".xialaAlert");
		a.hide();
	});
});