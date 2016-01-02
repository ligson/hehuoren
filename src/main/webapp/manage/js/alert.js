
function myAlert(width,height,title,data,infoType,footButOkFun){
	$("#myAlert").remove();
	
	var divI_w=parseInt(width)-6;
	var divI_h=parseInt(height)-6;
	
	var divI_con_w=parseInt(divI_w-20);
	var divI_con_h=parseInt(divI_h-20-25-35);
	
	
	
	var div=$("<div>").attr("id","myAlert");
		var divI=$("<div>").attr("class","myAlertI");
		divI.appendTo(div);
			var divI_t=$("<div>").attr("class","title");
			divI_t.appendTo(divI);
			var divI_t_span=$("<span>");
			divI_t_span.appendTo(divI_t);
			var divI_c=$("<div>").attr("class","content");
			divI_c.appendTo(divI);
				var divI_c_tipImg=$("<div>").addClass("macTipImg").addClass(infoType);
				divI_c_tipImg.css("top",(divI_con_h/2-20)+"px");
				divI_c_tipImg.appendTo(divI_c);
				var divI_c_Data=$("<div>").addClass("contentData");
				divI_c_Data.appendTo(divI_c);
			var divI_f=$("<div>").attr("class","foot");
			divI_f.appendTo(divI);
				var divI_f_butClose=$("<a>").attr("href","javascript:void(0)").addClass("footBut").addClass("footButClose").attr("footButType","close");
				divI_f_butClose.html("关闭");
				divI_f_butClose.appendTo(divI_f);
				divI_f_butClose.click(function(){
					//关闭
					div.hide();
				});
				if(footButOkFun!=null && footButOkFun!=undefined){
					var divI_f_butOk=$("<a>").attr("href","javascript:void(0)").addClass("footBut").addClass("footButOk").attr("footButType","ok");
					divI_f_butOk.html("确定");
					divI_f_butOk.appendTo(divI_f);
					divI_f_butOk.click(function(){
						//确定
						if(typeof footButOkFun =="function"){
							footButOkFun(div);
						 }
						div.hide();
					});
				}
				
			
			
		
				var top = ($(window).height() - height)/2; 
				var left = ($(window).width() - width)/2; 
				var scrollTop = $(document).scrollTop(); 
				var scrollLeft = $(document).scrollLeft(); 
				div.css( { 'top' : top + scrollTop, left : left + scrollLeft } ).show();
				div.css("width",width+"px").css("height",height+"px");
				
		
		divI.css("width",divI_w+"px").css("height",divI_h+"px");
		divI_t.css("width",divI_w+"px");
		divI_t.find("span").html(title);
		
		divI_c.css("width",divI_con_w+"px").css("height",divI_con_h+"px");
		divI_c_Data.css("width",(divI_con_w-50)+"px");
		var divIcData_h=divI_c_Data.height();
		divIcData_h=parseInt(divIcData_h);
		divI_c_Data.css("top",((divI_con_h-divIcData_h)/2-10)+"px");
		divI_c_Data.html(data);
		
		divI_f.css("width",divI_w+"px");
		
		
	$("body:last").append(div);
	div.show();
}


//下拉alert
function initXLAlert(width,height,boxId,title){
	
}

















