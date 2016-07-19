var hostName = "http://127.0.0.1:8080/Camber";
var read = "/read?menu=";
function httpAjax(menu,callback){
	var url = hostName+read+menu;
	ajaxServlet(url,callback);
}

function loadServiceAndPortfolio(menu,childmenu,callback){
	var url = hostName+read+menu+"&title="+childmenu;
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
