
	var panelBuffer = '<div class="mui-poppicker my-mui-poppicker">\
		<div class="mui-poppicker-header">\
			<button class="mui-btn mui-poppicker-btn-cancel">关闭</button>\
			<div class="mui-poppicker-clear"></div>\
		</div>\
		<div class="mui-poppicker-body">\
		</div>\
	</div>';

	var pickerBuffer = '<div class="mui-listpicker">\
		<div class="mui-listpicker-inner">\
			<ul>\
			</ul>\
		</div>\
	</div>';



function myMuiAlert(options){
	this.options=options;
}
myMuiAlert.prototype.show=function(){
	//remove掉原先的
	$("#myAlertId").remove();
	$("#fullScreenGray").remove();
	
	var htmlbody=$("body");
	
	var fs=$("<div>").attr("id","fullScreenGray");
	fs.addClass("fullScreen");
	htmlbody.append(fs);
	
	
	
	var contentDivId=this.options.contentDivId;
		var content=$("#"+contentDivId).html();
		//alert(content);
		var myAlert=$("<div>").attr("id","myAlertId");
		myAlert.html(panelBuffer);
		var body=myAlert.find(".mui-poppicker-body");
		//body.html(content);
		body.animate({ 
		    height: this.options.height
		}, 100 );
		
		
		htmlbody.append(myAlert);
		//alert(myAlert.html());
		var outer=myAlert.find(".mui-poppicker");
		
		var callBack=this.options.callBack;
		if(typeof callBack=="function"){
			callBack(outer);
		}
		
		
		var ok=outer.find(".mui-poppicker-btn-ok");
		var ca=outer.find(".mui-poppicker-btn-cancel");
		ca.click(function(){
			myAlert.remove();
			fs.remove();
		});
		return this;
}
myMuiAlert.prototype.getOuter=function(){
	return $("#myAlertId").find(".my-mui-poppicker");
}

myMuiAlert.prototype.setData=function(data){
	//优先加载穿进来的id
	//如果id没有，则说明穿进来的是string
	var url=data.url;
	if(null!=url && url!="" && url!=undefined){
		$.ajax( {
			type : "GET",
			url : allWeb+url,
			success : function(msg) {
				var body=$("#myAlertId").find(".mui-poppicker-body");
				body.html(msg);
			}
		})
	}else{
		var conDivId=data.contentDivId;
		if(conDivId!=undefined && conDivId!=""){
			var con=$("#"+conDivId).html();
			var body=this.getOuter().find(".mui-poppicker-body");
			body.html(con);
		}else{
			var body=this.getOuter().find(".mui-poppicker-body");
			body.html(data.content);
		}
	}

	
	
	var outer=this;
	var callBack=data.callBack;
	if(typeof callBack=="function"){
			callBack(outer);
	}
		
	
}

myMuiAlert.prototype.hide=function(){
	$("#myAlertId").remove();
	$("#fullScreenGray").remove();
}



function simpleAlert(content){
	content="<div class='simpleDiv'>"+content+"</div>";
	var malert=new myMuiAlert({'height':'270'});
	var data={'content':content,'callBack':function(myAlert){
		
	}};
	malert.show().setData(data);
}







































	function showMyAlert(options){
		$("#myAlertId").remove();
		$("#fullScreenGray").remove();
		
		var fs=$("<div>").attr("id","fullScreenGray");
		fs.addClass("fullScreen");
		$("body").append(fs);
		
		var contentDivId=options.contentDivId;
		var content=$("#"+contentDivId).html();
		//alert(content);
		var myAlert=$("<div>").attr("id","myAlertId");
		myAlert.html(panelBuffer);
		var body=myAlert.find(".mui-poppicker-body");
		body.html(content);
		body.animate({ 
		    height: options.height
		}, 100 );
		
		
		$("body").append(myAlert);
		//alert(myAlert.html());
		var outer=myAlert.find(".mui-poppicker");
		
		var callBack=options.callBack;
		if(typeof callBack=="function"){
			callBack(outer);
		}
		
		
		var ok=outer.find(".mui-poppicker-btn-ok");
		var ca=outer.find(".mui-poppicker-btn-cancel");
		ca.click(function(){
			myAlert.remove();
			fs.remove();
		});
		
		
		
		
	}
