<%@page 
import="com.scwe.dss.pagebean.designmanagement.UploadFilePageBean"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
UploadFilePageBean pageBean = (UploadFilePageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
	<form name="Form1" id="Form1" action="Page?Name=UploadFileSubmitPage" method="post" enctype="multipart/form-data">
	
			<div style="font-size:14px;" align="center">
			<div style="font-size:14px;">上传INP文件<br/><hr/></div><br/>
		<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%>/>					
		<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
				
		<table>
			<tr><td colspan="2">欲上传的INP文件必须以"inp"为文件文件扩展名,如:"Pre30YVerified.inp"</td></tr>
			<tr><td colspan="2">上传完毕后系统会自动在文件名前面增加本方案编号</td></tr>
			<tr><td><img src="images/clear.gif" width="1" height="40" border="0" alt=""></td></tr>
			<tr><td>目前系统存储的INP文件:</td><td><input name="INPDisplayOnly" type="text" value="<%=pageBean.selectedDesignData.inpFileName%>" readonly>
							<input type="file" name="INPFileName"></td></tr>
		</table>
		<br/><br/>
		<input type="submit" value="开始上传">
		<input type="button" onclick="javascript:window.close();" value="取消">		
		
		</div>

	</form>
</table>
</body>
</html>