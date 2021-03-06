$(function(){
	var $checkedType = $(".service_type li").eq(0);
	var url = window.location.href;
	init();
	function init(){
		var index = getUrlParam("index");
		if(index==null){
			index=0;
		}
		$checkedType = $(".service_type li").eq(index);
		chengeTypeTheme($checkedType);
	}
	$(".service_type li").click(function(){
		var title = $(this).attr("title");
		var url = "service.html";
		if(title == "design"){
			url +="?index=1";
		}else if(title == "construction"){
			url +="?index=2";
		}else if(title == "projectmanagment"){
			url +="?index=3";
		}
		document.location=url;
	});
	function otherTypeTheme(other){
		var other = '<span class="icon_right">&gt; </span><span class="type_title">Fit-out consulting</span>';
		var checkedTitle = $checkedType.attr("title");
		if(checkedTitle=="design"){
			other = '<span class="icon_right">&gt; </span><span class="type_title">Design</span>';
		}else if(checkedTitle=="construction"){
			other = '<span class="icon_right">&gt; </span><span class="type_title">Construction</span>';
		}else if(checkedTitle=="projectmanagment"){
			other = '<span class="icon_right">&gt; </span><span class="type_title">Project management</span>';
		}
		if(url.indexOf("zh")==-1){
			$checkedType.css("padding-top","0px");
		}
		$checkedType.html(other);
	}
	function chengeTypeTheme($changeType){
		var title = $changeType.attr("title");
		var swf = "Fit-out.swf";
		var url = document.location.href;
		var $rightImg = $(".right_img");
		var $ulImg = $(".ul_img");
		if(title == "design"){
			swf = "design.swf";
			$rightImg.addClass("hide");
			$ulImg.removeClass("hide");
		}else if(title == "construction"){
			swf = "construction.swf";
			$rightImg.addClass("hide");
			$ulImg.removeClass("hide");
		}else if(title == "projectmanagment"){
			swf = "project.swf";
			$rightImg.addClass("hide");
			$ulImg.removeClass("hide");
		}
		var baseImg = "../img/service/"+title+"/";
		if(url.indexOf("zh")==-1){
			baseImg = "img/service/"+title+"/";
			$changeType.css("padding-top","3px");
			var type = '<object data="img/service/'+swf+'" type="application/x-shockwave-flash" width="192px" height="30px" align="top"><param name="quality" value="high"><param name="wmode" value="opaque"><param name="swfversion" value="8.0.35.0"><param name="expressinstall" value="Scripts/expressInstall.swf"><param name="flashvars" value="e= '+url+'"><div><h4>Content on this page requires a newer version of Adobe Flash Player.</h4><p><a href="http://www.adobe.com/go/getflashplayer"><img src="./Fit-out consulting-Service-Camber_files/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33"></a></p></div></object>';
			$changeType.html(type);
		}else{
			$changeType.find("span").addClass("select_title");
		}
		loadServiceAndPortfolio("service",title,callback)
		function callback(data){
			$(".service_content").html(data.content);
			if($rightImg.is(":hidden")){
				var dataImg = data.img;
				for (var int = 0; int < dataImg.length; int++) {
					var img = '<img alt="" class="img-responsive" src='+baseImg+dataImg[int]+'>'
					$ulImg.append(img);
				}
			}else{
				var img = '<img alt="" src='+baseImg+data.img+'>'
				$rightImg.html(img);
			}
		}
	}
	
	function getUrlParam(name){  
	    //构造一个含有目标参数的正则表达式对象  
	    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");  
	    //匹配目标参数  
	    var r = window.location.search.substr(1).match(reg);  
	    //返回参数值  
	    if (r!=null) return unescape(r[2]);  
	    return null;  
	}  
})