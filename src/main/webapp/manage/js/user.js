//竞拍#
function userAddAuctionItem(b){
	var autionId=$(b).attr("autionId");
	var chujiaPrice=$("#jppriceInput_apId"+autionId).val();
	//alert(chujiaPrice);
	$.ajax( {
		type : "POST",
		url : allWeb+"/user/auctionItem/add?auctionPeriodId="+autionId+"&chujiaPrice="+chujiaPrice,
		success : function(msg) {
			if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				myAlert(200,130,"成功!","竞价成功","ok");
			}
		}
	})
}

function addPriceFunction(i){
	var apId=$(i).attr("apId");
	var chujiaPrice=$("#jppriceInput_apId"+apId).val();
	var addPrice=$(i).attr("addPrice");
	var newPrice=parseFloat(chujiaPrice)+parseFloat(addPrice);
	$("#jppriceInput_apId"+apId).val(newPrice);
	
}


//------------------收藏
function shoucang(a){
	var scEntityId=$(a).attr("scEntityId");
	var scEntity=$(a).attr("scEntity");
	if(null!=scEntity && scEntity!="" && null!=scEntityId && scEntityId!="" && scEntityId!="0"){
		$.ajax( {
			type : "POST",
			url : allWeb+"/user/shoucang/sc?scEntity="+scEntity+"&scEntityId="+scEntityId,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
				}else{
					myAlert(200,130,"成功!","收藏成功","ok");
				}
			}
		})
	}
}
function removeShoucang(a){
	var scId=$(a).attr("scId");
	if(null!=scId && scId!=""){
		$.ajax( {
			type : "POST",
			url : allWeb+"/user/shoucang/removeById?id="+scId,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
				}else{
					myAlert(200,130,"成功!","取消收藏成功","ok");
				}
			}
		})
	}
}

//---------------------设置提醒
var remindSetAlert;
function alertRemindSettings(a){
	var remindObjectId=$(a).attr("remindObjectId");
	var remindType=$(a).attr("remindType");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/remind/toAdd?remindObjectId="+remindObjectId+"&remindType="+remindType,
		success : function(msg) {
			var len = msg.length;
			if(len>100){
				remindSetAlert = art.dialog({title: "设置提醒",lock: true});
				remindSetAlert.content(msg);
			}else{
				myAlert(200,130,"失败!",msg,"error");
			}
		}
	})
}

function addRemindSet(a){
	var remindObjectId=$(a).attr("remindObjectId");
	var remindType=$(a).attr("remindType");
	var beginInput=$("#remindSetBox").find(".beginInput");
	var endInput=$("#remindSetBox").find(".endInput");
	var beginWENHAO="no";
	var endWENHAO="no";
	if(beginInput.attr("checked")){
		beginWENHAO="yes";
	}
	if(endInput.attr("checked")){
		endWENHAO="yes";
	}	 
	
	if(beginWENHAO=="no" && endWENHAO=="no"){
		alert("请选择提醒时机");
	}else{
		var remindTime;
		if(beginWENHAO=="yes" && endWENHAO=="yes"){
			remindTime="BEGINANDWILLEND";
		}else if(beginWENHAO=="yes" && endWENHAO=="no"){
			remindTime="BEGIN";
		}else if(beginWENHAO=="no" && endWENHAO=="yes"){
			remindTime="WILLEND";
		}
		if(null!=remindTime && remindTime!=""){
			$.ajax( {
				type : "POST",
				url : allWeb+"user/remind/addRemind?remindObjectId="+remindObjectId+"&remindType="+remindType+"&remindTime="+remindTime,
				success : function(msg) {
					if(msg!=null && msg!=""){
						myAlert(200,130,"失败!",msg,"error");
					}else{
						myAlert(200,130,"成功!","设置提醒成功","ok");
						remindSetAlert.close();
					}
				}
			})
		}
	}
	

}


//==================================================================================
function addCart(a){
	var pId=$(a).attr("productId");
	var ocount=$("#cartNumber_pId_"+pId).val();
	$.ajax( {
		type : "POST",
		url : allWeb+"/user/cart/addProduct?productId="+pId+"&ocount="+ocount+"&pickupAddressId=1",
		success : function(msg) {
			if(null!=msg && msg!=""){
				alert(msg);
			}else{
				alert("ok");
			}
			
		}
	})
}
function removeCI(a){
	var pId=$(a).attr("productId");
	$.ajax( {
		type : "POST",
		url : allWeb+"/user/cart/removeCartItem?productId="+pId,
		success : function(msg) {
			if(null!=msg && msg!=""){
				alert(msg);
			}else{
				alert("ok");
			}
			
		}
	})
}

function submitOrder(){
	$.ajax( {
		type : "POST",
		url : allWeb+"/user/order/addOrder",
		success : function(msg) {
			if(null!=msg && msg!=""){
				alert(msg);
			}else{
				alert("ok");
			}
			
		}
	})
}


















