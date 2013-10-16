<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head runat="server">
    <title></title>

    <script type="text/javascript">
        function RequestWebService() {
            //这是我们在第一步中创建的Web服务的地址
            var URL = "http://localhost:8080/LCSISTerminal/services/UserInfoService";
            
            //在这处我们拼接
            var data;
            data = '<?xml version="1.0" encoding="utf-8"?>';
            data = data + '<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">';
            data = data + '<soap12:Body>';
            data = data + '<queryClassAndReport xmlns="http://tempuri.org/" >';
            data = data + '<xml>&lt;?xml version=\"1.0\" encoding=\"utf-8\"?&gt;&lt;ROOT&gt; &lt;loginname&gt;zjcs&lt;/loginname&gt; &lt;password&gt;zjcs&lt;/password&gt; &lt;imsitype&gt;phone&lt;/imsitype&gt;&lt;/ROOT&gt;</xml>';
            data = data + '</queryClassAndReport>';
            data = data + '</soap12:Body>';
            data = data + '</soap12:Envelope>';
            
            //创建异步对象
            var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            xmlhttp.Open("POST", URL, false);
            xmlhttp.SetRequestHeader ("Content-Type","text/xml; charset=utf-8");   
            xmlhttp.SetRequestHeader ("SOAPAction","http://tempuri.org/");   
			xmlhttp.Send(data);

            document.getElementById("data").innerHTML = xmlhttp.responseText;
        }        
    </script>


</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input id="One" type="button" value="JsCallWebService" onclick="RequestWebService()" />
    </div>
    <div id="data">
    </div>
    </form>
</body>
</html>
