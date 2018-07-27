<%@ page contentType="text/htm; charset=utf-8" language="java" errorPage= "" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://wwww.w3.org/TR/xhtmll/DTD/xhtmll-transactional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>文件上传</title>
</haed>
<body>
<s:form action="upload"
	enctype="multipart/form-data">
	<s:textfield name="title" label="文件标题"/>
	<!-- 生成一个文件上传域 -->
	<s:file name="upload" label="选择文件"/>
	<s:submit value="上传"/>
</s:form>
</body>
</html>
