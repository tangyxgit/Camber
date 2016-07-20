$(function(){
	var $gallery = $(".gallery");
	var $checkedLi = $(".left_menu li").eq(0);
	loadServiceAndPortfolio("portfolio", $checkedLi.attr("title"), callback);
	$(".left_menu li").click(function(){
		$checkedLi.removeClass("lichecked");
		$checkedLi = $(this);
		$checkedLi.addClass("lichecked");
		$gallery.find("li").remove();
		$(".content").html("");
		var title = $checkedLi.attr("title");
		if(title=="HITACHI"){
			$(".hitachi").removeClass("hide");
		}else{
			$(".hitachi").addClass("hide");
			loadServiceAndPortfolio("portfolio", title, callback);
		}
	});
	function callback(data){
		var title = $checkedLi.attr("title");
		$(".content").html(data.content);
		var arrayImg = data.img;
		for (var int = 0; int < arrayImg.length; int++) {
			var img = 'img/portfolio/'+title+'/'+arrayImg[int];
			var li = '<li><a href="'+img+'"><img class="item img-thumbnail" src="'+img+'"></a></li>';
			$gallery.append(li);
		}
	}
});