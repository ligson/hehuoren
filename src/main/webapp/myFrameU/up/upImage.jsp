<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfupload/swfobject.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfupload/jquery.uploadify.v2.1.0.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/swfupload/swfupload.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/swfupload/uploadify.css" type="text/css" />
<style type="text/css">

/****************上传图片框开始************************/
.itemPic {
  overflow: hidden;
  line-height: 1.3;
}
.pic-manager2 {
  border: 1px solid #d5e4fa;
  padding: 9px 10px 13px;
  overflow: hidden;
}
.pm-sample {
  width: 127px;
  float: left;
}
.pm-bar li, .pm-box {
  border: 1px solid #dedede;
  color: grey;
  display: block;
}
.pm-sample .pm-box {
  height: 88px;
  padding: 20px 6px 0;
  width: 96px;
}
.itemPic .current, .pm-main .first .pm-box {
  border-color: #ffc097;
}
.pm-sample .pm-bar li {
  width: 19px;
  height: 19px;
  float: left;
  margin: 7px 2px 0 0;
}
.pm-main {
  float: left;
  width: 490px;
}
.pm-pwrap {
  padding-top: 19px;
  overflow: hidden;
  margin-right: -50px;width: auto;
}
.itemPic .pm-item {
  float: left;
  margin-right: 10px;
  position: relative;
  width: 90px;
}
.pm-item .noImage {
  background: url(../../swfupload/upload_sets.png) 20px -120px no-repeat;
}

.pm-main .pm-box, .pm-main .preview {
  width: 88px;
    height: 88px;
  }
  #itemPic .current, .pm-main .first .pm-box {
  border-color: #ffc097;
}
.pm-loading {
  height: 88px;
  left: 1px;
  position: absolute;
  text-align: center;
  top: 1px;
  width: 88px;
  z-index: 8;
  color: #ffc097;
  background: rgba(255,255,255,.95);
  filter: alpha(opacity=95);
  display:none;
}
.pic-manager2 .act {
  background: #C1DBFF;
  display: none;
  height: 18px;
  left: 1px;
  opacity: .8;
  overflow: hidden;
  padding: 7px 0 0 17px;
  position: absolute;
  z-index: 7;
  top: 64px;
  width: 71px;
}
.pm-main .preview {
  font-size: 0;
  font-family: arial;
  background: #fff;
  line-height: 88px;
  text-align: center;
  visibility: hidden;
  position: absolute;
  top: 1px;
  left: 1px;
  z-index: 6;
}
.imgSpaceBar a, .pic-manager2 .upload a {
  background: url(../../swfupload/upload_t.png)no-repeat;
}
.pic-manager2 .upload a {
  background-position: 0 0;
  cursor: pointer;
  display: block;
  height: 25px;
  margin-top: 3px;
  overflow: hidden;
  width: 90px;
}
.pic-manager2 .upload input {
  cursor: pointer;
  direction: rtl;
  font-size: 40px;
  height: 40px;
  opacity: 0;
  width: 100px;
  filter: alpha(opacity=0);  vertical-align: inherit;
}
.pm-loading .pm-loading-inner {
  position: relative;
  padding-top: 30px;
}
.pic-manager2 .act span {
  cursor: pointer;
  float: right;
  height: 12px;
  margin-right: 10px;
  text-indent: -999em;
  width: 12px;
  background: url(../../swfupload/T1IoOeXoRsXXXXXXXX-180-130.png)no-repeat;
}

.pic-manager2 .act .moveleft {
  background-position: 0 -13px;
}
.pic-manager2 .act .moveright {
  background-position: -13px -13px;
}
.pic-manager2 .act .del {
  background-position: -13px 0;
}
.pm-msg {
  padding-top: 5px;clear:both;
}
.hidden {
  display: none;
}
.itemPic .msg {
  margin: 6px 0 0;
}
.tips-image {
  color: #AAA;
  margin-top: 5px;
}
.msg .attention{
  color: #404040;
  /***background: url(//assets.alicdn.com/sys/common/img/msg_bg.png) no-repeat;**/
  border: 1px solid #ddd;
  float: left;
  padding: 2px 10px 2px 23px;
  line-height: 18px;
}
.msg .attention {
  background-position: 3px -147px;
  border-color: #40b3ff;
  background-color: #e5f5ff;
}
/****************上传图片框结束************************/

</style>
<%
	String numStr=request.getParameter("number");
	if(null!=numStr){
		int number=new Integer(numStr).intValue();
		request.setAttribute("number", number);
	}
	String saveType=request.getParameter("saveType");
	request.setAttribute("saveType", saveType);
	String imageName=request.getParameter("imageName");
	String[] imageNames=null;
	if(null!=imageName && !imageName.equals("")){
		if(!imageName.contains(",")){
			imageNames=new String[1];
			imageNames[0]=imageName;
		}else{
			imageNames=imageName.split(",");
		}
	}
	request.setAttribute("imageNames", imageNames);
	
	
	String imageValue=request.getParameter("imageValue");
	String[] imageValues=null;
	if(null!=imageValue && !imageValue.equals("")){
		if(!imageValue.contains(",")){
			imageValues=new String[1];
			imageValues[0]=imageValue;
		}else{
			imageValues=imageValue.split(",");
		}
	}
	request.setAttribute("imageValues", imageValues);
	
	
	
	
%>
<script type="text/javascript">
$(document).ready(function(){
	var box="${param.box}";
	
	var numStr=$("#"+box+"_numberInputId").val();
	var num=parseInt(numStr);
	var saveType=$("#"+box+"_saveTypeInput").val();
	
	for(var i=1;i<=num;i++){
		initUpImg("box"+box+"_upImageButton_"+i,saveType);
	}
	
	var imvInput=$("#"+box+"_imageValuesInput").val();
	if(null!=imvInput && imvInput!=""){
		$("#"+box+"_itemPic").delegate(".del","click",function(){
			var curDel=$(this);
			var curItem=curDel.parents(".pm-item");
			var showBox=curItem.find(".pm-box");
			var saveResultInput=curItem.find(".saveResultInput");
			showBox.addClass("noImage");
			showBox.html("");
			saveResultInput.val("");
			curDel.parents(".act").hide();
		});
	}
	
});
</script>
</head>
<body>
<input type="hidden" value="${param.box}" id="${param.box}InputId"/>
<input type="hidden" value="${requestScope.number}" id="${param.box}_numberInputId"/>
<input type="hidden" value="${requestScope.saveType}" id="${param.box}_saveTypeInput"/>
<input type="hidden" value="${requestScope.imageValues}" id="${param.box}_imageValuesInput"/>

											<div id="${param.box}_itemPic" class="itemPic">
												<div class="pic-manager2">
												
													<div class="pm-sample" style="display:none;">
									                    <div class="pm-box current">
									                        800px*800px<br>以上的图片，可以在宝贝详情页提供图片放大功能
									                    </div>
									                    <ul class="pm-bar">
									                        <li class="current"></li>
									                        <li></li>
									                        <li></li>
									                        <li></li>
									                        <li></li>
									                    </ul>
									                </div>
									                
									                <div class="pm-main">
                    										<ul id="" class="pm-pwrap">
                    											<c:forEach begin="1" end="${requestScope.number}" step="1" varStatus="vs">
                    												<li class="pm-item ">                                
	                                                    				
	                                                    				<c:if test='${!empty requestScope.imageValues[vs.index-1]}'>
	                                                    						<c:if test='${requestScope.imageValues[vs.index-1]!=""}'>
	                                                    							<c:if test='${fn:contains(requestScope.imageValues[vs.index-1], "img") }'>
		                                                    							<div class="pm-box">
		                                                    								<img src="<%=request.getContextPath()%>/${requestScope.imageValues[vs.index-1]}" style="width:100%"/>
	                                                    								</div>
	                                                    							</c:if>			
	                                                    						</c:if>	
	                                                    				</c:if>
	                                                    				<c:if test='${empty requestScope.imageValues[vs.index-1] || requestScope.imageValues[vs.index-1]==""}'>
	                                                    					<div class="pm-box noImage">
	                                                    					</div>
	                                                    				</c:if>
	                                                    				
	                                									<div class="pm-itemcont">
	                                    									<div class="preview"></div>
																			<span class="upload">
																				<a href="javascript:void(0);">
	                                                    							<input type="file" id="box${param.box}_upImageButton_${vs.index}">
	                                                							</a>
																			</span>
	                                    									<input type="hidden" class="saveResultInput" name="${requestScope.imageNames[vs.index-1]}" value="${requestScope.imageValues[vs.index-1]}" id="${param.box}_saveResult_${vs.index}">                                    
	                                									</div>
	                                									<div class="pm-loading">
	                                    									<div class="pm-loading-inner">
	                                        									<img alt="" src="<%=request.getContextPath()%>/swfupload/load.gif">上传中
	                                    									</div>
	                                									</div>
	                                									<c:if test='${empty requestScope.imageValues[vs.index-1] || requestScope.imageValues[vs.index-1]==""}'>
	                                										<div class="act" style="display: none;">
		                                    									<span class="moveleft" style="display:none;">左移</span>
		                                    									<span class="moveright" style="display:none;">右移</span>
		                                    									<span class="del" >删除</span>
		                                									</div>
	                                									</c:if>
	                                									
	                                                    				<c:if test='${!empty requestScope.imageValues[vs.index-1]}'>
	                                                    						<c:if test='${requestScope.imageValues[vs.index-1]!=""}'>
	                                                    							<c:if test='${fn:contains(requestScope.imageValues[vs.index-1], "img") }'>
				                                                    					<div class="act" style="display: block;">
					                                    									<span class="moveleft" style="display:none;">左移</span>
					                                    									<span class="moveright" style="display:none;">右移</span>
					                                    									<span class="del" >删除</span>
					                                									</div>
	                                                    							</c:if>			
	                                                    						</c:if>	
	                                                    				</c:if>
	                            									</li>
                    											</c:forEach>
                                            				</ul>
                											<div class="kf-msg" style="display: none"></div>
                										</div>
									                	<div class="pm-msg">
										                    <div class="msg hidden" id="J_TipFood">
										                        <p class="attention" style="width:559px">
										                        </p>
										                    </div>
										                    <c:if test='${empty param.bottomTipTxt}'>
										                   	 	<div class="tips-image"> 请上传图片</div>
										                    </c:if>
										                    <c:if test='${!empty param.bottomTipTxt}'>
										                   	 	<div class="tips-image"> ${param.bottomTipTxt}</div>
										                    </c:if>
										                </div>
												</div>
											</div>
</body>
</html>