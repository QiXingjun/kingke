<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的个人信息</title>
<style type="text/css"> 
body{ font-size:12px; line-height:24px;} 
.exp{font-size:390%;} 
</style> 
</head>
<body>

<%
	String username =(String)request.getAttribute("username");
	String city =(String)request.getAttribute("city");
	String country =(String)request.getAttribute("country");
	String touxiang =(String)request.getAttribute("touxiang");
%>
<br>
<br>
<div class="exp">您的昵称是：
<%=username %>
<br>
<br>
您的城市是：
<%=city %>
<br>
<br>
您的国家是：
<%=country %>
<br>
<br>
您的头像是：</div>
<br>
<br>
<img src="<%=touxiang %>">
</body>
</html>