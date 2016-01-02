/**
 * 弹出选择列表插件
 * 此组件依赖 listpcker ，请在页面中先引入 mui.listpicker.css + mui.listpicker.js
 * varstion 1.0.1
 * by Houfeng
 * Houfeng@DCloud.io
 */

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
