/**
 * 发送短信获取验证码js开始=====================================================================
 */
var oTimer;
function sendYZM(a){
	var sdkMtType=$(a).attr("sdkMtType");
	var telPhones=$("#sendTelPhoneInput").val();
	var role=$(a).attr("role");
	$.ajax( {
		type : "POST",
		url : allWeb+role+"/sms/send?sdkMtType="+sdkMtType+"&telPhones="+telPhones,
		success : function(msg) {
			var contentIndex=msg.indexOf("content");
			if(contentIndex>=0){
				initTimer("yzmA",59000);
				//myAlert(300,200,"成功!",msg,"ok");
			}else{
				myAlert(200,130,"失败!",msg,"error");
			}
			/*if(msg!=null && msg!=""){
				myAlert(200,130,"失败!",msg,"error");
			}else{
				initTimer("yzmA",59000);
			}*/
		}
	})
}
function verYZM(i){
	var smsYanzhengma=$(i).val();
	var tip=$("#"+$(i).attr("tip"));
	var role=$(i).attr("role");
	var timerId=$(i).attr("timerId");
	$.ajax( {
		type : "POST",
		url : allWeb+role+"/sms/yzm?smsYanzhengma="+smsYanzhengma,
		success : function(msg) {
			if(msg=="ok"){
				tip.html("验证码正确");
				$("#"+timerId).html("重新获取");
		 		window.clearInterval(oTimer);
		 		$(i).addClass("readonly");
			}else{
				tip.html("验证码错误");
			}
		}
	})
}
function timer(timerId,ts)  {  
    //var ts = (new Date(2018, 11, 11, 9, 0, 0)) - (new Date());//计算剩余的毫秒数
	//var ts=60000;
    var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
    var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
    var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
    var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
    dd = checkTime(dd);  
    hh = checkTime(hh);  
    mm = checkTime(mm);  
    ss = checkTime(ss);  
    //$("#"+timerId).html(dd + "天" + hh + "时" + mm + "分" + ss + "秒");
   /////////// setInterval("timer(timerId)",1000);
    $("#"+timerId).html(ss + "秒");
    ts=ts-1000;
    return ts;
}  
function initTimer(timerId,ts){
	oTimer =window.setInterval(function(){
		 	if(ts>=0){
		 		ts=timer(timerId,ts);
		 	}else{
		 		$("#"+timerId).html("重新获取");
		 		window.clearInterval(oTimer);
		 	}
	 },1000);
}
function checkTime(i)    
{    
   if (i < 10) {    
       i = "0" + i;    
    }    
   return i;    
} 

/**
 * 发送短信获取验证码js结束=====================================================================
 */




/**
 * UI倒计时开始=====================================================================
 */
function fg_timer(timerId,endDateStr)  {
	var milliseconds=Date.parse(endDateStr);
	var endDate=new Date();
	endDate.setTime(milliseconds);
    
    var ts = (endDate) - (new Date());//计算剩余的毫秒数
	//var ts=60000;
    var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
    var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
    var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
    var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
   // dd = checkTime(dd);  
    hh = checkTime(hh);  
    mm = checkTime(mm);  
    ss = checkTime(ss);  
    var timerBox=$("#"+timerId);
    timerBox.find(".group_d").find(".num").html(dd);
    timerBox.find(".group_h").find(".num").html(hh);
    timerBox.find(".group_m").find(".num").html(mm);
    timerBox.find(".group_s").find(".num").html(ss);
    
    //$("#"+timerId).html(dd + "天" + hh + "时" + mm + "分" + ss + "秒");
   /////////// setInterval("timer(timerId)",1000);
   // $("#"+timerId).html(ss + "秒");
    ts=ts-1000;
    return ts;
}  
function fg_initTimer(timerId,endDateStr){
	var ts=0;
	fg_oTimer =window.setInterval(function(){
		 	if(ts>=0){
		 		ts=fg_timer(timerId,endDateStr);
		 	}else{
		 		window.clearInterval(fg_oTimer);
		 	}
	 },1000);
}








/**
 * UI倒计时结束=====================================================================
 */



/**
 * select开始=====================================================================
 */
function initDate(queryArgs){
	//初始化默认值
	/**
	 * 判断queryArgs里有没有key为queryArgsKey的
	 */
	var dateRange=$("#queryArgsBox").find(".dateRange");
	var queryArgsKey=dateRange.attr("queryArgsKey");
	
	var noValueQ="{'key':'"+queryArgsKey+"','value':'";
	var noValueQIndex=queryArgs.indexOf(noValueQ);
	if(noValueQIndex>0){
		//存在
		var queryArgs_1=queryArgs.substring(noValueQIndex,queryArgs.length);
		var firstRightKHIndex=queryArgs_1.indexOf("'}");
		queryArgs_1=queryArgs_1.substring(0,firstRightKHIndex);
		
		queryArgs_1=queryArgs_1.replace("','operators':'daterange","");
		queryArgs_1=queryArgs_1.replace(noValueQ,"");
		var beginEnd=queryArgs_1.split("_");
		var beginDate=beginEnd[0];
		var endDate=beginEnd[1];
		dateRange.find("input[name=beginDate]").val(beginDate);
		dateRange.find("input[name=endDate]").val(endDate);
	}else{
		//不存在
		//不存在则需要将endDate
	}
}
$(document).ready(function(){
	var queryArgsBox=$("#queryArgsBox");
	var queryArgsBoxId=queryArgsBox.attr("id");
	if(null!=queryArgsBoxId && queryArgsBoxId!=undefined && queryArgsBoxId!=""){
		if(null!=queryArgsBox && queryArgsBox!=undefined && queryArgsBox!=""){
			var queryArgs=$("#queryArgsBox").find(".requestQueryArgsInput").val();
			initDate(queryArgs);
			$("#queryArgsBox").find(".dateRange input").each(function(){
				//初始化日历控件
				var curInput=$(this);
				var curInput_name=curInput.attr("name");
				var dateRange=curInput.parents(".dateRange");
				var queryArgsKey=dateRange.attr("queryArgsKey");
				
				curInput.simpleDatepicker({startdate:2015,enddate:2020,callFunction:function(myDate){
					if(curInput_name=="beginDate"){
						//如果单机的是开始时间
						//那么判断下结束时间有没有。
						var beginDate=myDate;
						var endDateInput=dateRange.find("input[name=endDate]");
						var endDate=endDateInput.val();
						if(null!=endDate && endDate!=undefined && endDate!=""){
							//比较两个日期的大小，看看合格不合格
							var beginDate_d = new Date(beginDate.replace(/\-/g, "\/"));  
							var endDate_d = new Date(endDate.replace(/\-/g, "\/"));  
							if(beginDate_d<=endDate_d){
								 var queryArgs_self="{'key':'"+queryArgsKey+"','value':'"+beginDate+"_"+endDate+"','operators':'daterange'}";
								 var lastUrl=getNewUrl(queryArgs,queryArgs_self);
								 window.location.href=lastUrl;
							 }else{
								 alert("日期区间错误");
							 }
						}
					}else if(curInput_name=="endDate"){
						var endDate=myDate;
						var beginDateInput=dateRange.find("input[name=beginDate]");
						var beginDate=beginDateInput.val();
						if(null!=beginDate && beginDate!=undefined && beginDate!=""){
							var beginDate_d = new Date(beginDate.replace(/\-/g, "\/"));  
							var endDate_d = new Date(endDate.replace(/\-/g, "\/")); 
							if(beginDate_d<=endDate_d){
								 var queryArgs_self="{'key':'"+queryArgsKey+"','value':'"+beginDate+"_"+endDate+"','operators':'daterange'}";
								 var lastUrl=getNewUrl(queryArgs,queryArgs_self);
								 window.location.href=lastUrl;
							 }else{
								 alert("日期区间错误");
							 }
						}
					}
				}});
				
			});
			
			
			
			
			
			
			
			$("#queryArgsBox").delegate(".mySearch .searchBut","click",function(){
				var curBut=$(this);
				var curSearchBox=curBut.parent(".mySearch");
				var curInput=curSearchBox.find(".queryTxt");
				var curKey=curInput.attr("queryArgsKey");
				var value=curInput.val();
				if(null!=value && value!=""){
					var curQueryArgs="{'key':'"+curKey+"','value':'"+value+"','operators':'like'}";
					lastUrl=getNewUrl(queryArgs,curQueryArgs);
					window.location.href=lastUrl;
				}
				
			});
			$("#queryArgsBox").find(".mySearch").each(function(){
				var curSearchBox=$(this);
				var curInput=curSearchBox.find(".queryTxt");
				var curKey=curInput.attr("queryArgsKey");
				var czStr="{'key':'"+curKey+"','value':'";
				var keyIndex=queryArgs.indexOf(czStr);
				if(keyIndex>0){
					//说明存在
					//找到原先queryArgs中这个key的value，填充上input的value
					var queryArgs_1=queryArgs.substring(keyIndex,queryArgs.length);
					var rightKHIndex=queryArgs_1.indexOf("'}");
					queryArgs_1=queryArgs_1.substring(0,rightKHIndex);
					queryArgs_1=queryArgs_1.replace("','operators':'like","");
					var s = "{'key':'"+curKey+"','value':'";
					queryArgs_1=queryArgs_1.replace(s,"");
					//alert(queryArgs_1);
					curInput.val(queryArgs_1);
				}else{
				}
			});
			
			$("#queryArgsBox .mySelect").each(function(){
				var cur=$(this);
				var width=cur.attr("width");
				var down=cur.find(".down");
				var input=cur.find(".input");
				input.css("width",width+"px");
				down.css("width",width+"px");
				//处理queryArgs的默认值
				var firstLiA_value=down.find("li:first").find("a").attr("value");
				var valueJSON=eval('('+firstLiA_value+')'); 
				var noValueQueryArg="{'key':'"+valueJSON.key+"','value':'";
				var b =contains(queryArgs,noValueQueryArg);
				if(b){
					var index=queryArgs.indexOf(noValueQueryArg);
					var queryArgs_1=queryArgs.substring(index,queryArgs.length);
					var index_1=queryArgs_1.indexOf("'}");
					var lensss=noValueQueryArg.length;
					queryArgs_1=queryArgs_1.substring(lensss,index_1);

					//queryArgs_1就是从服务器传过来的queryArgs中这个key的值，现在要对应这个值，去把该select是这个值的给他选中。
					var defaultQueryArg=noValueQueryArg+queryArgs_1+"'}";
					down.find("a").each(function(){
						var curA=$(this);
						var curAValue=curA.attr("value");
						if(curAValue==defaultQueryArg){
							input.html(curA.html());
						}
					});
				}
			});
			$("body").delegate("#queryArgsBox .mySelect .input","click",function(){
				var cur=$(this);
				var curSelect=cur.parent(".mySelect");
				var status=curSelect.attr("status");
				var down=curSelect.find(".down");
				if(status=="close"){
					down.show();
					curSelect.attr("status","open");
				}else if(status=="open"){
					down.hide();
					curSelect.attr("status","close");
				}
			});
			$("body").delegate("#queryArgsBox .mySelect .down ul li a","click",function(){
				//var queryArgs=$("#queryArgsBox").find(".requestQueryArgsInput").val();
				var cur=$(this);
				var curSelect=cur.parents(".mySelect");
				var txt=cur.html();
				var value=cur.attr("value");
				var curInput=curSelect.find(".input");
				curInput.html(txt);
				curInput.attr("value",value);
				var curDown=cur.parents(".down");
				curDown.hide();
				curSelect.attr("status","close");
				
				
				var lastUrl=getNewUrl(queryArgs,value);
				window.location.href=lastUrl;
				//alert(lastUrl);
			});
		}
	}
});

//根据选择的value+原来的queryArgs，生成新的url
function getNewUrl(queryArgs,value){
	
	//{'key':'result','value':'APPLYOK'}
	var valueJSON=eval('('+value+')'); 
	
	var reg=new RegExp("{'key':'"+valueJSON.key+"','value':'.*'}","gmi");
	var noValueQueryArg="{'key':'"+valueJSON.key+"','value':";
	
	if(null!=queryArgs && queryArgs!="" && queryArgs!="undefined"){
		var b =contains(queryArgs,noValueQueryArg);
		if(b){
			queryArgs=queryArgs.replace(reg,value);
		}else{
			queryArgs=queryArgs.substring(0,queryArgs.length-1);
			queryArgs=queryArgs+","+value+"]";
		}
	}else{
		queryArgs="["+value+"]";
	}
	
	var lastUrl;
	
	
	//alert(window.location.href);
	//alert(window.location.search);
	//alert(window.location.pathname);
	var args=getUrlArgObject();
	var noQueryArgs_args=allPrpos(args);
	var pathname=window.location.pathname;
	if(contains(pathname,"024lm")){
		pathname=pathname.replace("/024lm/","");
	}
	//window.location.href="";
	if(null==noQueryArgs_args || noQueryArgs_args==""){
		//说明只有queryArgs，或者是一个参数都没有
		lastUrl=allWeb+pathname+"?queryArgs="+queryArgs;
	}else{
		lastUrl=allWeb+pathname+"?queryArgs="+queryArgs+noQueryArgs_args;
	}
	return lastUrl;
}

function contains(longTxt,smallTxt){
	if(longTxt.indexOf(smallTxt) > 0 ){
	    return true;
	}
	return false;
}
function getUrlArgObject(){ 
    var args=new Object(); 
    var query=location.search.substring(1);//获取查询串 
    var pairs=query.split("&");//在逗号处断开 
    for(var i=0;i<pairs.length;i++){ 
        var pos=pairs[i].indexOf('=');//查找name=value 
        if(pos==-1){//如果没有找到就跳过 
            continue; 
        } 
        var argname=pairs[i].substring(0,pos);//提取name 
        var value=pairs[i].substring(pos+1);//提取value 
        args[argname]=unescape(value);//存为属性 
    } 
    return args;//返回对象 
} 

function allPrpos ( obj ) { 
	  // 用来保存所有的属性名称和值 
	  var props = "" ; 
	  // 开始遍历 
	  for ( var p in obj ){ // 方法 
		  if ( typeof ( obj [ p ]) == " function " ){ 
			  obj [ p ]() ; 
		  } else {
			  if(p!="queryArgs"){
				  props=props+"&"+p+"="+obj[p];
				//  props += p + "=" + obj [ p ]; 
			  }
		  } 
	  } // 最后显示所有的属性 
	  //alert ( props ) ;
	  return props;
}

/**
 * select结束=====================================================================
 */



function verNumber(i){
	var val=$(i).val();
	if(null!=val && val!=""){
		$.ajax( {
			type : "POST",
			url : allWeb+"verify/geshi?geshi=NUMBER&value="+val,
			success : function(msg) {
				if(msg!=null && msg!=""){
					myAlert(200,130,"失败!",msg,"error");
				}else{
				}
			}
		})
	}else{
		myAlert(200,130,"失败!","不能为空","error");
	}
}




