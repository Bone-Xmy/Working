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
		textfield#s{
			width: 300px;
			height: 40px;
			/*display为inline的盒模型不会占据一行，即允许一行出现多个元素  */
			display:inline;
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
		<!--s:textfield name="startDate" label="开始日期"/ -->
		<!-- s:textfield id="s" name="startTime" label="开始时间"/ -->
		开始日期：<input name="startDate" type="text"/>
		开始时间：<input name="startTime" type="text"/>
		<!-- s:textfield name="endDate" label="结束日期"/ -->
		<!-- s:textfield name="endTime" label="结束时间"/ -->
		结束日期：<input name="endDate" type="text"/>
		结束时间：<input name="endTime" type="text"/>
		<br/>
		异常类型：
		<select name="checkTypes">
			<option>流水<>实收+优惠</option>
			<option>流水<>支付科目明细之和</option>
			<option>流水<>菜品销售金额合计</option>
			<option>实收<>实收科目合计</option>
			<option>实收<>菜品实收金额合计</option>
			<option>会员储值明细</option>
			<option>综合营业报表账单明细'</option>
			<option>综合营业报表支付明细</option>
			<option>综合营业报表菜品明细</option>
		</select!-- >
			<!-- s:select name="checkTypes" label="异常类型" labelposition="right"
				list="{'流水<>实收+优惠','流水<>支付科目明细之和','流水<>菜品销售金额合计','实收<>实收科目合计','实收<>菜品实收金额合计','会员储值明细','综合营业报表账单明细','综合营业报表支付明细','综合营业报表菜品明细'}"/ -->
		<button type="submit">查询</button>
			<!-- s:submit value="查询"/-->
		<!-- /div -->
	</s:form>
	<s:form action="showLogs">
		<!-- s:textfield name="orderLogs" label="操作日志"/ -->
		<!-- s:submit value="日志查询"/ -->
		操作日志：<input name="orderLogs" type="text"/>
		<button type="submit">日志查询</button>
	</s:form>
	<s:form action="checkSetFood">
		<!-- s:submit value="检查套餐明细"/ -->
		<button type="submit">检查套餐明细</button>
	</s:form>
	<s:form action="upload" enctype="multipart/form-data">
		<s:textfield name="title" label="文件标题"/>
		<!-- 生成一个文件上传域 -->
		<s:file name="upload" label="选择文件"/>
		<s:submit value="上传"/>
	</s:form>
</div>
<hr/>
</body>
</html>

