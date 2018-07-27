<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtmll/DTD/xhtmll-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>成功页面</title>
	<style type="text/css">
		div#toolKinds {
			border:1px solid black;
			padding:5px;
			width:600px;
			height:30px;
			display:flex;
			flex-direction:row;
		}
		div>div {
			border:1px solid #aaf;
			width:200px;
			padding:5px;
		}
	</style>
</head>
<body>
<div>您已经登录!</div>
<hr/>
<div id="toolKinds">
	<div>报表对账</div>
	<div>修复异常数据</div>
	<div>sss3</div>
</div>
<br/>
<hr/>
<div id="choose">
	选择条件:<br/>
	<s:form action="showReports">
		<s:textfield name="startDate" label="开始日期"/>
		<s:textfield name="endDate" label="结束日期"/><br/>
		<s:textfield name="checkTypes" label="异常类型"/>
		<s:submit value="查询"/>
	</s:form>
	<s:form action="showLogs">
		<s:textfield name="orderLogs" label="操作日志"/>
		<s:submit value="日志查询"/>
	</s:form>
</div>
<hr/>
</body>
</html>

