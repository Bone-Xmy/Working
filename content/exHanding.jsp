<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtmll/DTD/xhtmll-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>异常处理页面</title>
</head>
<body>
<div>修复异常数据</div>
<hr/>
<s:form action="exceptionHanding">
	<s:select name="exTypes" label="异常类型"
		labelposition="right"
		list="{'所支付金额与应收金额不符','删除订单号为空的数据(单号很长)','会员消费补单子'}"/>
	<s:textfield name="saasOrderKey" label="订单号(saasOrderKey)"/>
	<s:radio name="isHis" label="是否历史账单(结账时间超过30小时)"
		labelposition="right"
		list="{'否','是'}"/>
	<s:submit value="生成Sql"/>
</s:form>
<hr/>
<s:form>
	<div>
		<s:property value="[0].mySql"/>
	</div>
</s:form>
</body>
</html>

