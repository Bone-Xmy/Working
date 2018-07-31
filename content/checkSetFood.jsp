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
		<s:select name="checkTypes" label="异常类型" labelposition="right"
			list="{'流水<>实收+优惠','流水<>支付科目明细之和','流水<>菜品销售金额合计','实收<>实收科目合计','实收<>菜品实收金额合计','会员储值明细','综合营业报表账单明细','综合营业报表支付明细','综合营业报表菜品明细'}"/>
		<s:submit value="查询"/>
	</s:form>
	<s:form action="showLogs">
		<s:textfield name="orderLogs" label="操作日志"/>
		<s:submit value="日志查询"/>
	</s:form>
	<s:form action="checkSetFood">
		<s:submit value="检查套餐明细"/>
	</s:form>
	<s:form action="upload" enctype="multipart/form-data">
		<s:textfield name="title" label="文件标题"/>
		<!-- 生成一个文件上传域 -->
		<s:file name="upload" label="选择文件"/>
		<s:submit value="上传"/>
	</s:form>
</div>
<hr/>
<div>
	<s:form action="showReports">
		<s:submit value="查询"/>
	<!-- s:property value="[0].rsList"/ -->
	<!-- s:property value="[0].foodDetailList"/-->
	</s:form>
	<table border="1">
		<tr>
			<th>套餐名称</th>
			<th>明细菜品名称</th>
			<th>明细菜品key值</th>
			<th>是否有同名菜品</th>
		</tr>
		<s:iterator value="[0].foodDetailList" id="row" status="rs1">
			<tr>
				<s:iterator value="row" id="cols" status="rs2">
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