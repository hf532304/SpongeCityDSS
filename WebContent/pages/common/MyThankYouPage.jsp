<%@page 
import="com.scwe.dss.pagebean.MyThankYouPageBean"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
MyThankYouPageBean pageBean = (MyThankYouPageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form action="Page?Name=<%=pageBean.getNextPage()%>" method="post">
	<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />	
	<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
	
	<div style="font-size:10px;" align="center">
		<div style="font-size:14px;"><%=pageBean.getTitleDisplay()%><br/><hr/></div><br/><br/>
		<%=pageBean.getMsgDisplay()%>  
		<br/><br/>
		<input type="submit" value="返回">		
  </div>
</form>
</body>
</html>