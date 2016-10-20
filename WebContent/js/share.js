//var hostName = "http://127.0.0.1:8080/Camber";
var hostName = "http://123.57.209.163:3900/connect";
var read = "/read?menu=";
function httpAjax(menu,callback){
	var url = hostName+read+menu;
	ajaxServlet(url,callback);
}

function loadServiceAndPortfolio(menu,childmenu,callback){
	var url = hostName+read+menu+"&title="+childmenu;
	var h = window.location.href;
	if(h.indexOf("zh")!=-1){
		var url = hostName+read+menu+"&title="+childmenu+"&language=zh";
	}
	ajaxServlet(url,callback);
}

function ajaxServlet(url,callback){
	url += "&callback=?";
	$.ajax({
		url:url,
		type:'get',
		dataType:'jsonp',
		success:function(data){
			callback(data);
		},
		error:function(x){
			alert(x.status);
		}
	});
}
