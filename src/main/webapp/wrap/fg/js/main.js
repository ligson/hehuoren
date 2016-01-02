$(document).ready(function() {
	
	initHeadBack();
	initImageCenter();
	$(".tabHeadBox").delegate(".tabA", "click", function() {
		var curA = $(this);
		var curTabBox = curA.parents(".tabBox");
		var curTabHeadBox = curTabBox.find(".tabHeadBox");
		var curTabContentBox = curTabBox.find(".tabContentBox");
		var method = curA.attr("method");
		var contentId = curA.attr("contentDiv");
		var contentDiv = $("#" + contentId);
		if (method == "" || method == undefined || method == null) {
			// 默认的就是直接定死的
			curTabHeadBox.find("a").removeClass("selected");
			curA.addClass("selected");
			curTabContentBox.find(".tabContent").hide();
			contentDiv.show();

			$(".img-section").each(function() {
				var cur = $(this);
				var curA = cur.parent();
				cur.css("width", curA.width() + "px");
			});

		} else {
			$.ajax({
				type : "GET",
				url : allWeb + method,
				success : function(msg) {
					curTabHeadBox.find("a").removeClass("selected");
					curA.addClass("selected");
					curTabContentBox.find(".tabContent").hide();
					contentDiv.html(msg).show();
				}
			})
		}
	});
	mui.init({});
	initMaxWidth();
	
	
	$("#offCanvasShow").click(function(){
		$("#offCanvasSide").css("zIndex","111").css("visibility","visible");
		$("body").css("overflow","hidden");
	});
	$("#offCanvasHide").click(function(){
		$("#offCanvasSide").css("zIndex","-1").css("visibility","hidden");
		$("body").css("overflow","visible");
	});
});

function initHeadBack(){
	var ref = document.referrer;
	var backA = $("#myHead_l").find("a");
	if (ref != null && ref != "") {
		backA.attr("href", ref);
	}
}


// 根据图片高度来确定轮播图box的高度
function initFocusBoxHeight(focusBoxId) {
	var focusBox = $("#" + focusBoxId);
	var firstImg = focusBox.find("img:first");
	var firstImg_js = firstImg[0];
	firstImg_js.onload = function() {
		var width = firstImg_js.offsetWidth;
		var height = firstImg_js.offsetHeight;
		// alert(width+"=="+height);
		focusBox.css("height", height + "px");
	}
}

function initLeftRight() {

}

function initMaxWidth() {
	var firstNo100 = $(".widthNo100:first");
	if (null != firstNo100) {
		var maxWidth = firstNo100.width();
		// alert(maxWidth);
		$("#myHead").width(maxWidth);
	}
}

function getScreen() {
	var height = window.screen.height;
	var width = window.screen.width;
	alert(height + "==" + width);
}

// ---------------------------------more ajax--------------------------------
function myAjaxMore(options) {
	options = options || [];
	this.options = options;
	// alert(options[0].outer);
}

myAjaxMore.prototype.ajaxMore = function(jsonItem_current) {
	if (null != jsonItem_current) {

		var cur_outer = jsonItem_current.outer;
		var cur_action = jsonItem_current.action;
		var cur_callBack = jsonItem_current.callBack;
		var moreId = jsonItem_current.moreId;
		$("#" + moreId).remove();

		if (null != cur_outer && null != cur_action) {
			$.ajax({
				type : "GET",
				url : allWeb + cur_action,
				success : function(msg) {
					$("#" + cur_outer).append(msg);
					// $("#"+cur_outer).append(moreAlink);
					if (typeof cur_callBack == "function") {
						cur_callBack(cur_outer);
					}
				}
			})
		}
	}
}
myAjaxMore.prototype.init = function() {
	var self = this;
	var len = this.options.length;
	var jsonItem;
	var outer;
	var moreId;
	for (var i = 0; i < len; i++) {
		jsonItem = this.options[i];
		outer = jsonItem.outer;
		moreId = jsonItem.moreId;
		$("#" + moreId).click(function() {
			self.ajaxMore(jsonItem);
		});
	}
}

// ---------------------------------------图片居中----------------
function initImageCenter(){
	$(".img-section").each(function() {
		var cur = $(this);
		var curA = cur.parent();
		cur.css("width", curA.width() + "px");
	});
}

// -----------------------------------倒计时----------------------------------------------
function fg_timer(timerId, endDateStr) {
	var milliseconds = Date.parse(endDateStr);
	var endDate = new Date();
	endDate.setTime(milliseconds);

	var ts = (endDate) - (new Date());// 计算剩余的毫秒数
	// var ts=60000;
	var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);// 计算剩余的天数
	var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);// 计算剩余的小时数
	var mm = parseInt(ts / 1000 / 60 % 60, 10);// 计算剩余的分钟数
	var ss = parseInt(ts / 1000 % 60, 10);// 计算剩余的秒数
	// dd = checkTime(dd);
	hh = checkTime(hh);
	mm = checkTime(mm);
	ss = checkTime(ss);

	var hhNew = dd * 24;
	hhNew = parseInt(hhNew + parseInt(hh));

	var timerBox = $("#" + timerId);
	// /timerBox.find(".group_d").find(".num").html(dd);
	timerBox.find(".group_h").find(".num").html(hhNew);
	timerBox.find(".group_m").find(".num").html(mm);
	timerBox.find(".group_s").find(".num").html(ss);

	// $("#"+timerId).html(dd + "天" + hh + "时" + mm + "分" + ss + "秒");
	// ///////// setInterval("timer(timerId)",1000);
	// $("#"+timerId).html(ss + "秒");
	ts = ts - 1000;
	return ts;
}
function fg_initTimer(timerId, endDateStr) {
	var ts = 0;
	fg_oTimer = window.setInterval(function() {
		if (ts >= 0) {
			ts = fg_timer(timerId, endDateStr);
		} else {
			window.clearInterval(fg_oTimer);
		}
	}, 1000);
}
function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}

// ================================================================
var oTimer;
function sendYZM(a) {
	var sdkMtType = $(a).attr("sdkMtType");
	var telPhones = $("#sendTelPhoneInput").val();
	var role = $(a).attr("role");
	$.ajax({
		type : "POST",
		url : allWeb + "sms/send?sdkMtType=" + sdkMtType + "&telPhones="
				+ telPhones,
		success : function(msg) {
			var contentIndex = msg.indexOf("content");
			if (contentIndex >= 0) {
				initTimer("yzmA", 59000);
				//mui.toast("成功=" + msg);
			} else {
				//mui.toast(msg);
			}
			/*
			 * if(msg!=null && msg!=""){ myAlert(200,130,"失败!",msg,"error");
			 * }else{ initTimer("yzmA",59000); }
			 */
		}
	})
}
function verYZM(i) {
	var smsYanzhengma = $(i).val();
	var tip = $("#" + $(i).attr("tip"));
	var role = $(i).attr("role");
	var timerId = $(i).attr("timerId");
	$.ajax({
		type : "POST",
		url : allWeb + "sms/yzm?smsYanzhengma=" + smsYanzhengma,
		success : function(msg) {
			if (msg == "ok") {
				tip.html("验证码正确");
				$("#" + timerId).html("重新获取");
				window.clearInterval(oTimer);
				$(i).addClass("readonly");
			} else {
				tip.html("验证码错误");
			}
		}
	})
}
function initTimer(timerId, ts) {
	oTimer = window.setInterval(function() {
		if (ts >= 0) {
			ts = timer(timerId, ts);
		} else {
			$("#" + timerId).html("重新获取");
			window.clearInterval(oTimer);
		}
	}, 1000);
}
function timer(timerId, ts) {
	// var ts = (new Date(2018, 11, 11, 9, 0, 0)) - (new Date());//计算剩余的毫秒数
	// var ts=60000;
	var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);// 计算剩余的天数
	var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);// 计算剩余的小时数
	var mm = parseInt(ts / 1000 / 60 % 60, 10);// 计算剩余的分钟数
	var ss = parseInt(ts / 1000 % 60, 10);// 计算剩余的秒数
	dd = checkTime(dd);
	hh = checkTime(hh);
	mm = checkTime(mm);
	ss = checkTime(ss);
	// $("#"+timerId).html(dd + "天" + hh + "时" + mm + "分" + ss + "秒");
	// ///////// setInterval("timer(timerId)",1000);
	$("#" + timerId).html(ss + "秒");
	ts = ts - 1000;
	return ts;
}

// ===========================================================================================

function mustNumber(i){
	var val = $(i).val();
	if(!/^[0-9]*$/.test(val)){  
        alert("请输入数字!");  
    }  
}

function toUserLogin(){
	window.location.href="/wrap/toUserLogin";
}
//==========================================================================================================
//申请成为合伙人
function applyHHR(){
	//alert("dddddddddddddddd");
	$.ajax( {
		type : "POST",
		url : allWeb+"user/apply/addApplySimple?applyTypeKey=UserLevelHHR",
		success : function(msg) {
			if(msg!=null && msg!=""){
				mui.toast(msg);
			}else{
				mui.toast("提交成为合伙人申请成功");
			}
		}
	})
}






function verUserPhone(i){
	var phone=$(i).val();
	if(null!=phone && phone!=""){
		var len=phone.length;
		if(len==11){
			$.ajax( {
				type : "POST",
				url : allWeb+"verUserTelphone?telPhones="+phone,
				success : function(msg) {
					if(msg!=null && msg!=""){
						alert(msg);
					}else{
						
					}
				}
			})
		}else{
			alert("请输入正确的手机号码");
		}
	}else{
		alert("请输入手机号码");
	}
}


//======================================================
function alertPPList(a){
	var productId = $(a).attr("productId");
	var malert=new myMuiAlert({'height':'370'});
	var data={'url':'wrap/proprice/findsByPId?productId='+productId,'callBack':function(myAlert){
		//alert("dddddddddddd");
	}};
	malert.show().setData(data);
}



