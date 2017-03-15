<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
	<input type="hidden"  value="${openid}" id="openid">
	<input type="hidden"  value="${redirectUrl}" id="redirectUrl">
</body>
<script type="text/javascript">
	var openid = document.getElementById("openid").value;
	var redirectUrl = document.getElementById("redirectUrl").value;
	if(openid!=null&&redirectUrl!=null){
		window.location.href = redirectUrl +"?openid="+ openid;
	}
</script>
</html>