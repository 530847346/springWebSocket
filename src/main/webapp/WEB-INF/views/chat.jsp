<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>  
<html>  
<head>  
<title>Testing websockets</title>  
<script  src="../js/jquery-3.1.1.min.js">//引入jquery框架
</script>
<script type="text/javascript">  
    var webSocket  
    function initpage() {  
        //创建socket对象  
        webSocket = new WebSocket("ws://" + window.location.host  
                + "/webchat/websocket");  
        //为websocket连接服务器异常注册事件  
        webSocket.onerror = function(event) {  
            onError(event)  
        };  
        //为websocket连接到服务器时注册事件  
        webSocket.onopen = function(event) {  
            onOpen(event)  
        };  
  
        //为websocket注册消息处理事件  
        webSocket.onmessage = function(event) {  
            onMessage(event)  
        };  
    }  
    //显示消息对话框  
    function initWebSocket() {  
        document.getElementById("dialog").style.display = "";  
    }  
    //处理从服务端发送过来的数据  
    function onMessage(event) {  
        //alert("receive from service:" + event.data);
        $('#showMsg').append(event.data).append("\r\n");
        var msgjson = JSON.parse(event.data);  
        if (msgjson.sendMark == "1") {  
            document.getElementById('showMsg').value = event.data;  
            //alert("json msg:" + msgjson.msg);  
        } else {  
            alert("消息发送失败");  
        }  
    }  
    //连接打开时所做的处理  
    function onOpen(event) {  
        //document.getElementById('connectState').innerHTML = 'connected'; 
        $('#connectState').text('连接状态:connected');
    }  
    //连接异常时所做的处理  
    function onError(event) {
    	alert("异常"+event.data);
        //alert(event.data);  
    }  
    //向服务端推送消息  
    function sendMessage() {  
        var message = document.getElementById('msg').value;  
        //alert("message:" + message);  
        var frommcn = document.getElementById('frommcn').value;  
        //alert("frommcn:" + frommcn);  
        var tomcn = document.getElementById('tomcn').value;  
        //alert("tomcn:" + tomcn);  
        //创建一个空的对象  
        var msgjson = {};  
        msgjson.frommcn = frommcn;  
        msgjson.tomcn = tomcn;  
        msgjson.msg = message;  
        //将对象转换成json字符串  
        var jsonstr = JSON.stringify(msgjson);  
        //alert("jsonstr:" + jsonstr);  
        webSocket.send(jsonstr);  
        var nowtime = dateformat();  
        document.getElementById("showMsg").value += "<br/>" + nowtime;  
        document.getElementById("showMsg").value += "<br/>" + message;  
    }  
</script>  
</head>  
<body onload="initpage();">  
    <a href="javascript:initWebSocket();">105015</a>  
    <input type="hidden" value="101515" id="tomcn" />  
    <input type="hidden" value="147258" id="frommcn" />  
    <div id="messages"></div>  
    <div style="width: 70%; text-align: right; margin: auto; display: none"  
        id="dialog">  
        <div>  
            <table style="border: 1px solid black; margin: auto;"> 
            	<tr>
            		<td colspan="2">
                		<div style="width:100%;text-align: center" id="connectState"></div>
                	</td> 
            	<tr>
                <tr> 
                    <td colspan="2">
                    <textarea rows="10" cols="80" id="showMsg"  
                            style="overflow-y: scroll; resize: none"></textarea></td>  
                </tr>  
                <tr>  
                    <td colspan="2"><textarea id="msg" rows="3" cols="80"  
                            style="overflow-y: scroll; resize: none"></textarea></td>  
                </tr>  
                <tr>  
                    <td colspan="2"><input type="button" value="发送"  
                        style="width: 100%" onclick="sendMessage();" /></td>  
                </tr>  
            </table>  
        </div>  
    </div>  
</body>  
</html>  