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
	var userId=$(a).attr("userId");
	if(userId!=0 && userId!="" && userId!=null && userId!="null"){
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
	}else{
		window.location.href=allWeb+"toUserLogin";
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

function addRemindSet(a){
	var remindObjectId=$(a).attr("remindObjectId");
	var remindType=$(a).attr("remindType");
	var beginInput=$("#remindSetBox").find(".beginInput");
	var endInput=$("#remindSetBox").find(".endInput");
	
	var remindTime=$("#addRemindBox").find(".remindTimeDIV").attr("remindTime");
	if(null!=remindTime && remindTime!=""){
		$.ajax( {
			type : "POST",
			url : allWeb+"user/remind/addRemind?remindObjectId="+remindObjectId+"&remindType="+remindType+"&remindTime="+remindTime,
			success : function(msg) {
				mui.toast(msg);
				
			}
		})
	}
}






