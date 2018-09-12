<%@page 
import="com.scwe.dss.pagebean.designmanagement.ResultManagementPageBean"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
ResultManagementPageBean pageBean = (ResultManagementPageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form name="Form1" id="Form1" action="Page" method="post">
	
<div style="font-size:10px;" align="center">
	<div style="font-size:14px;">运行结果管理<br/><hr/></div><br/>
	
	<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />	
	<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
		  <input type="hidden" id="Name" name="Name" value="">

	<br/>
	  <input type="hidden" id="NextAction" name="NextAction" value="">
	<table>
		<tr><td align="center"><input type="submit" onclick="document.getElementById('Name').value='DisplayDesignPage'" value="设计方案展示"></td></tr>
		<tr><td><img src="images/clear.gif" width="1" height="10" border="0" alt=""></td></tr>
		<tr><td align="center">
					<input type="button" onclick="document.getElementById('Name').value='<%=pageBean.getCancelPage()%>';document.getElementById('Form1').submit();" value="返回">
		</td></tr>					
		
		</tr>
	</table>
	</div>
</form>
</body>
</html>