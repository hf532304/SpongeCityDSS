<%@page 
import="com.scwe.dss.pagebean.designmanagement.DesignDeleteConfirmationPageBean"
import="com.scwe.dss.datatransfer.DesignData"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
DesignDeleteConfirmationPageBean pageBean = (DesignDeleteConfirmationPageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form name="Form1" id="Form1" action="Page" method="post">
	
	<div style="font-size:10px;" align="center">
		<div style="font-size:14px;">请确认删除以下方案及其文件<br/><hr/></div><br/><br/>
		
		<input type="hidden" id="Name" name="Name" value="DesignDeleteSubmitPage" />
		<input type="hidden" name="CurrentPageBean" value="<%=pageBean.getClass().getName()%>" />
		<input type="hidden" name="DefaultDesign" value="<%=pageBean.selectedDesignData.designId%>" />
		
		<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
				
		<table border="1" cellspacing="0" cellpadding="2">
			<tr><td>方案编号：</td><td><%=pageBean.selectedDesignData.designId%></td></tr>
			<tr><td>方案名称：</td><td><%=pageBean.selectedDesignData.designName%></td></tr>
			<tr><td>方案状态：</td><td><%=pageBean.selectedDesignData.designStatus%></td></tr>
			<tr><td>默认方案：</td><td><%=pageBean.selectedDesignData.isDefaultDesign%></td></tr>			
			<tr><td>设计雨型：</td><td><%=pageBean.selectedDesignData.rainName%></td></tr>			
			<tr><td>INP文件：</td><td><%=pageBean.selectedDesignData.inpFileName%></td></tr>
			<tr><td>RPT文件：</td><td><%=pageBean.selectedDesignData.rptFileName%></td></tr>
		</table>
		<br/><br/>
		<input type="submit" value="确认删除">
		<input type="button" onclick="document.getElementById('Name').value='<%=pageBean.getCancelPage()%>';document.getElementById('Form1').submit();" value="取消">
	</div>

</form>
<table>
</table>
</body>
</html>