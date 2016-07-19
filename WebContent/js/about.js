$(function(){
	httpAjax("about",callback);
	function callback(data){
		var img = "img/about/"+data.img;
		$(".about_img").attr("src",img);
		var content = '<p style="padding: 10px">'+data.content+'</p>';
		$(".about_content").html(content);
	}
})