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
	<div>sss1</div>
	<div>sss2</div>
	<div>sss3</div>
</div>
<br/>
<hr/>
<div>
	选择条件:
</div>
<hr/>
<div>
	<s:form action="showReports">
		<s:submit value="查询"/>
	<!-- s:property value="[0].rsList"/ -->
	</s:form>
	<table border="1">
		<tr>
			<s:iterator value="[0].colList" id="col" status="rs1">
				<td>
					<s:property value="col"/>
				</td>
			</s:iterator>
		</tr>
		<s:iterator value="[0].rsList" id="row" status="rs2">
			<tr>
				<s:iterator value="row" id="cols" status="rs3">
					<td>
						<s:property value="cols"/>
					</td>
				</s:iterator>
			</tr>
		</s:iterator>
	</table>
</div>
</body>
</html>
