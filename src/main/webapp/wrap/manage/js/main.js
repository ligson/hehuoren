function startWXJSAPIPay(){
	//alert("aaaaaaaaaaa");
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			}
		} else {
			//alert("bbbbbbbbbbbb");
			onBridgeReady();
		}
	}
	
	function onBridgeReady() {
		//alert("ccccccccccccccccc");
		var appId=$("#wxInputId_appId").val();
		var timeStamp=$("#wxInputId_timeStamp").val();
		var nonceStr=$("#wxInputId_nonceStr").val();
		var wpackage=$("#wxInputId_package").val();
		var paySign=$("#wxInputId_paySign").val();
		/*alert("45454545454545===========onBridgeReady方法获取页面上input的各个值：appId="+
				appId+";timeStamp="+timeStamp+";nonceStr="+nonceStr+";wpackage="+wpackage+";paySign="+paySign);*/
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : appId, //公众号名称，由商户传入     
			"timeStamp" : timeStamp, //时间戳，自1970年以来的秒数     
			"nonceStr" : nonceStr, //随机串     
			"package" : wpackage,
			"signType" : "MD5", //微信签名方式:     
			"paySign" : paySign //微信签名 
		}, function(res) {
			//alert("555555555===res.err_msg==="+res.err_msg);
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				var backUrl=$("#wxInputId_myself_finishCallback_url").val();
				window.location.href=allWeb+backUrl;
			} // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		});
	}
	
	
	
	
	
	
	

function wxPayAccountItem(a){
		var accountItemId=$(a).attr("accountItemId");
		$.ajax({
			type : "POST",
			dataType : "json",
			url : allWeb + "weixin/pay/payAi?accountItemId=" + accountItemId,
			success : function(msg) {
				$("#wxInputId_appId").val(msg.appId);
				$("#wxInputId_timeStamp").val(msg.timeStamp);
				$("#wxInputId_nonceStr").val(msg.nonceStr);
				$("#wxInputId_package").val(msg.wpackage);
				$("#wxInputId_paySign").val(msg.paySign);
				//alert("11111111111111111"+msg);
				startWXJSAPIPay();
			}
		})
}
	
/*function wxPayAccountItem(a){
	var accountItemId=$(a).attr("accountItemId");
	$.ajax({
		type : "POST",
		dataType : "json",
		url : allWeb + "weixin/pay/test/payAi?accountItemId=" + accountItemId,
		success : function(msg) {
			$("#wxInputId_appId").val(msg.appId);
			$("#wxInputId_timeStamp").val(msg.timeStamp);
			$("#wxInputId_nonceStr").val(msg.nonceStr);
			$("#wxInputId_package").val(msg.wpackage);
			$("#wxInputId_paySign").val(msg.paySign);
			//alert("11111111111111111"+msg);
			startWXJSAPIPay();
		}
	})
}*/