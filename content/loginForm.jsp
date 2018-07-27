<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtmll/DTD/xhtmll-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>登陆页面</title>
</head>
<body>
<h3>用户登陆</h3>
${tip}
<s:form action="login">
	<s:textfield name="username" label="用户名"/>
	<s:password name="password" label="密码"/>
	<s:submit value="登陆"/>
</s:form>
</body>
</html>

