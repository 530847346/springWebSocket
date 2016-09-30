<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务端</title>
</head>
<script  src="../js/jquery-3.1.1.min.js">//引入jquery框架
</script>
<script type="text/javascript">
	function sendMsg(){	
		var username = $('#username').val();
		var message = "服务器："+$('#message').val();
		$.ajax({
	  		type:"POST",
	  		url:"serverSend",
	  		data:{username:username,message:message},
	  		success:function(data){
	  			//alert("消息发送成功");
	  		},
	  		error: function (err) {
	             alert("消息发送失败");
	        }
	  	});
	}

</script>
<body>
	用户名称：<input type="text" id="username"/>
	回复内容:<input type="text" id="message"/>
	<button onclick="sendMsg()">发送</button>
</body>
</html>