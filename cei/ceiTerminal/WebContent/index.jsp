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
            var URL = "http://localhost:8080/ceiTerminal/services/UserInfoService";
            
            //在这处我们拼接
            var data;
            data = '<?xml version="1.0" encoding="utf-8"?>';
            data = data + '<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">';
            data = data + '<soap12:Body>';
            data = data + '<queryReportByType xmlns="http://tempuri.org/" >';
            data = data + '<xml>&lt;?xml version=\"1.0\" encoding=\"utf-8\"?&gt;&lt;ROOT&gt;&lt;functionids&gt;40288a7837bbda430137bbf4a9230018&lt;/functionids&gt;&lt;/ROOT&gt;</xml>';
            data = data + '</queryReportByType>';
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


	<!-- JavaScript专用链代码 -->

	<script src="http://pstatic.xunlei.com/js/webThunderDetect.js"></script>
	<script src="http://pstatic.xunlei.com/js/base64.js"></script>
	<script language="javascript">
	var thunder_url = "http://192.168.10.248:8091/cei/kj/flash.zip";
	var thunder_pid = "57029";
	var restitle = "";
	document.write('<a href="#" thunderHref="' + ThunderEncode(thunder_url) + '" thunderPid="' + thunder_pid + '" thunderResTitle="' + restitle + '" onClick="return OnDownloadClick_Simple(this,2,4)" oncontextmenu="ThunderNetwork_SetHref(this)">迅雷专用高速下载</a> ');
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
