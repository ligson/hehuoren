//轮播图
//focus:jquery对象
//EG:
function initLunbotu(focus,sWidth,sHeight,focusStyleType){
	//var sWidth = focus.width(); //获取焦点图的宽度（显示面积）
	
	if(sWidth=="100%"){
		//sWidth=$(document).width();
		sWidth=$(".widthNo100").width();
	}
	focus.css("width",sWidth);
	focus.find("ul").css("width",sWidth);
	focus.find("ul li").css("width",sWidth);
	focus.find("ul li a").css("width",sWidth);
	focus.find("ul li img").css("width",sWidth);
	if(sHeight=="100%"){
		var imgJQ=focus.find("img:first");
		var firstImg_js = imgJQ[0];
		firstImg_js.onload = function() {
			var width = firstImg_js.offsetWidth;
			var height = firstImg_js.offsetHeight;
			sHeight=height;
			focus.css("height",sHeight+"px");
			focus.find("ul").css("height",sHeight+"px");
			focus.find("ul li").css("height",sHeight+"px");
			focus.find("ul li a").css("height",sHeight+"px");
			focus.find("ul li img").css("height",sHeight);
		}
		//alert(sHeight);
		
	}else if(sHeight=="auto"){
		sHeight=focus.find("img").height();
		focus.css("height",sHeight+"px");
		focus.find("ul").css("height","auto");
		focus.find("ul li").css("height","auto");
		focus.find("ul li a").css("height","auto");
		focus.find("ul li img").css("height","auto");
	}else{
		focus.css("height",sHeight+"px");
		focus.find("ul").css("height",sHeight+"px");
		focus.find("ul li").css("height",sHeight+"px");
		focus.find("ul li a").css("height",sHeight+"px");
		focus.find("ul li img").css("height",sHeight+"px");
	}

	var len = focus.find(" ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	
	//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
	
	if(null!=focusStyleType && focusStyleType=="suolvetu"){
		var btnBg=$("<div>");
		btnBg.attr("class","btnBg");
		focus.append(btnBg);
		
		var btn=$("<div>");
		btn.addClass("btn");
		var btnUl=$("<ul>");
		var btnUl_w=len*170;
		btnUl.css("width",btnUl_w+"px");
		btnUl.attr("class","btnUl");
		btnUl.appendTo(btn);
		focus.find(".bgFocusA").each(function(){
			var curA=$(this);
			var src=curA.attr("smallImage");
			var newLi=$("<li>");
				var newImg=$("<img>");
				newImg.attr("src",src);
				newImg.appendTo(newLi);
				
				var liCover=$("<div>");
				liCover.attr("class","liCover");
				liCover.appendTo(newLi);
			newLi.appendTo(btnUl);
		});
		focus.append(btn);
		var prev=$("<div>");
		prev.addClass("preNext").addClass("pre");
		prev.appendTo(focus);
		
		var next=$("<div>");
		next.addClass("preNext").addClass("next");
		next.appendTo(focus);
		
		
		
		
	}else{
		var btn = "<div class='btnBg'></div><div class='btn'>";
		for(var i=0; i < len; i++) {
			btn += "<span>0"+(i+1)+"</span>";
		}
		btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
		focus.append(btn);
	}
	
	
	
	focus.find(".btnBg").css("-moz-opacity",0.5).css("opacity",0.5).css("filter","alpha(opacity=50)");

	//为小按钮添加鼠标滑入事件，以显示相应的内容
	focus.find(" .btn span").css("opacity",0.5).mouseenter(function() {
		index = focus.find(".btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");

	//上一页、下一页按钮透明度处理
	focus.find(".preNext").css("opacity",0.2).hover(function() {
		$(this).stop(true,false).animate({"opacity":"0.5"},300);
	},function() {
		$(this).stop(true,false).animate({"opacity":"0.5"},300);
	});

	//上一页按钮
	focus.find(".pre").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	//下一页按钮
	focus.find(".next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	focus.find("ul").css("width",sWidth * (len));
	
	
	
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	focus.hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},4000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		focus.find("ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
		//$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		focus.find(".btn span").stop(true,false).animate({"opacity":"0.5"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
	}
	
	
	
	//focus.find(".pre").css("top",(sHeight-100)/2+"px");
	//focus.find(".next").css("top",(sHeight-100)/2+"px");
	focus.find(".btnBg").css("width",sWidth+"px");
}



//广告的图片
/**
 *  .width100Box { width:100%;position:relative;overflow:hidden;height:200px;}
	.width100{width:3000px;position:absolute; left:50%;margin-left:-1500px; }
 */
$(document).ready(function(){
	var width100Images=$(".width100Image");
	width100Images.each(function(){
		var curImage=$(this);
		var width=curImage[0].offsetWidth;
		var height=curImage[0].offsetHeight;
		var widthInt=parseInt(width);
		var widthInt2=widthInt/2;
		var imageBoxA=curImage.parent("a");
		var width100Box=$("<div>");
			width100Box.css("width","100%").css("position","relative").css("overflow","hidden").css("height",height+"px");
		var width100=$("<div>");
			width100.css("width",width+"px").css("position","absolute").css("left","50%").css("margin-left","-"+widthInt2+"px");
		curImage.appendTo(width100);
		width100.appendTo(width100Box);
		//alert(width100Box.html());
		width100Box.appendTo(imageBoxA);
	});
});




























