//初始化上传插件
function initUpImg(fileInpuId,saveType,onCompleteFun){
	
	initUploadImage(fileInpuId,saveType,"",90,25,1,onCompleteFun);
}
function initUploadImage(fileInpuId,saveType,folder,flashW,flashH,queueSize,onCompleteFun){
	var can=0;//可以上传
	var finput=$("#"+fileInpuId);
	var curItem=finput.parents("li.pm-item");
	var showBox=curItem.find(".pm-box");
	var loadBox=curItem.find(".pm-loading");
	var botButton=curItem.find(".act");
	var saveResultInput=curItem.find(".saveResultInput");
	finput.uploadify({
		  'uploader'  : allWeb+'swfupload/uploadify.swf',
		  'script'    : allWeb+'servlet/uploadImage?saveType='+saveType,
		  'cancelImg' : allWeb+'swfupload/cancel.png',
		  'buttonImg' : allWeb+'swfupload/upload.png',
		  'folder'    : '../'+folder,
		  'width':flashW,
		  'height':flashH,
		  'multi'   : true,
		  'auto'    : true,
		  'fileExt' : '*.jpg;*.JPG;*.jpeg;*.JPEG',
		  'fileDesc'       : 'Image Files (.JPG,jpg,jpeg,JPEG)',
		  'queueID'        : 'custom-queue',
		  'queueSizeLimit' : queueSize,
		  'simUploadLimit' : 10,
		  'onSelect': function(e, queueId, fileObj) {
				if(fileObj.size>2*1024*1024){
					alert(fileObj.name+"图片超过2M,不能上传");
					$('#custom_file_upload').uploadifyCancel(queueId);
				}else{
					loadBox.show();
				}
		  },
		'onComplete' : function(event,queueId,fileObj,response,data){
		   if(typeof onCompleteFun == 'function'){
			   onCompleteFun(response);
		   }else{
			   loadBox.hide();
			   showBox.html("");
			   var dataObj=eval("("+response+")");
			   //alert(dataObj.url);
			   var im=$("<img>");
			   im.attr("src",allWeb+dataObj.url);
			   im.attr("width","100%");
			   im.appendTo(showBox);
			   showBox.removeClass("noImage");
			   botButton.show();
			   saveResultInput.val(dataObj.url);
			   
			   //为删除按钮绑定单机事件
			   var del=botButton.find(".del");
			   del.click(function(){
				   showBox.addClass("noImage");
				   showBox.html("");
				   saveResultInput.val("");
				   botButton.hide();
			   });
			   
		   }
		},
		'onSelectOnce'   : function(event,data) {},
		'onAllComplete'  : function(event,data) {
			
		}
	})
}
	

