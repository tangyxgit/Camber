$(function(){
	httpAjax("career", callback);
	function callback(data){
		$(".career_content").html(data.content);
	}
});