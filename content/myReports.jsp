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
<jsp:include page="welcome.jsp"/>
<!-- 显示结果集 -->
<div>
	<!-- 不知道干啥用的 -->
	<!-- s:form action="showReports" -->
		<!-- s:submit value="查询"/ -->
	<!-- s:property value="[0].rsList"/ -->
	<!-- /s:form -->
	<table border="1">
		<tr>
			<s:iterator value="[0].colList" id="col" status="rs1">
				<th>
					<s:property value="col"/>
				</th>
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
