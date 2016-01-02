$(document).ready(function(){
	$(".myList").delegate(".link a","click",function(){
		var curA=$(this);
		var type=curA.attr("type");
		var myurl=curA.attr("url");
		
		var parent=curA.attr("parent");
		if(myurl!=undefined && myurl!="undefined" && myurl!=""){
			if(type=="delete"){
				$.ajax( {
					type : "POST",
					url : allWeb+myurl,
					success : function(msg) {
						if(msg!=null && msg!=""){
							myAlert(200,130,"失败!",msg,"error");
						}else{
							myAlert(200,130,"成功!","删除成功","ok");
						}
						curA.parents(parent).remove();
					}
				})
			}
		}
	});
	$(".myList").delegate("input","dblclick",function(){
		var curInput=$(this);
		var isMod=curInput.attr("onblurMod");
		if(null!=isMod && isMod!=undefined && isMod!=""){
			curInput.removeAttr("readonly");
		}
	});
	$(".myList").delegate("input","blur",function(){
		var curInput=$(this);
		var isMod=curInput.attr("onblurMod");
		var myurl=curInput.attr("url");
		if(null!=isMod && isMod!=undefined && isMod!=""){
			var newVal=curInput.val();
			var oldVal=curInput.attr("oldVal");
			if(newVal!=oldVal){
				myurl=myurl+newVal;
				$.ajax( {
					type : "POST",
					url : myurl,
					success : function(msg) {
						if(msg!=null && msg!=""){
							myAlert(200,130,"失败!",msg,"error");
						}else{
							myAlert(200,130,"成功!","修改成功","ok");
						}
						curInput.attr("readonly","readonly");
					}
				})
			}else{
				curInput.attr("readonly","readonly");
			}
		}
	});
	
	
	$(".myList").delegate("select","change",function(){
		var curS=$(this);
		var isMod=curS.attr("onChangeMod");
		var myurl=curS.attr("url");
		if(null!=isMod && isMod!=undefined && isMod!=""){
			var newVal=curS.val();
			var oldVal=curS.attr("oldVal");
			if(newVal!=oldVal){
				myurl=myurl+newVal;
				$.ajax( {
					type : "POST",
					url : myurl,
					success : function(msg) {
						if(msg!=null && msg!=""){
							myAlert(200,130,"失败!",msg,"error");
							curS.val(oldVal);
						}else{
							myAlert(200,130,"成功!","修改成功","ok");
						}
					}
				})
			}else{
			}
		}
	});
	
	
	
	
	
	
	
	$(".overClickTr2Right").delegate("tr","mousemove",function(){
		var curTr=$(this);
		var se=curTr.attr("selected");
		if(se!="selected"){
			curTr.find("td").addClass("selected");
		}
	});
	$(".overClickTr2Right").delegate("tr","mouseout",function(){
		var curTr=$(this);
		var se=curTr.attr("selected");
		if(se!="selected"){
			curTr.find("td").removeClass("selected");
		}
	});
	var allTr=$(".overClickTr2Right").find("tr");
	$(".overClickTr2Right").delegate("tr","click",function(){
		var curTr=$(this);
		allTr.find("td").removeClass("selectedd");
		allTr.find("td").removeClass("selected");
		allTr.removeAttr("selected");
		
		var se=curTr.attr("selected");
		if(se!="selected"){
			curTr.attr("selected","selected");
			curTr.find("td").addClass("selectedd");
		}
		
		var myurl=curTr.attr("url");
		if(null!=myurl && myurl!=""){
			$("#myContent").html("");
			$.ajax( {
				type : "POST",
				url : allWeb+myurl,
				success : function(msg) {
					$("#myContent").html(msg);
				}
			})
		}
		
	});
	
	
	
});



