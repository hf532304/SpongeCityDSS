<%@page 
import="com.scwe.dss.pagebean.designmanagement.ProjectDeleteConfirmationPageBean"
import="com.scwe.dss.datatransfer.ProjectData"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
ProjectDeleteConfirmationPageBean pageBean = (ProjectDeleteConfirmationPageBean) session.getAttribute("PageBean"); 
ProjectData[] myDesignData = pageBean.getMyProjects();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form name="Form1" id="Form1" action="Page" method="post">
	
		<div style="font-size:10px;" align="center">
		<div style="font-size:14px;">请确认删除以下项目及其所属方案<br/><hr/></div><br/><br/>
		
		<input type="hidden" id="Name" name="Name" value="ProjectDeleteSubmitPage" />				
		<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />
		<input type="hidden" name="SelectedProject" value=<%=pageBean.selectedProjData.projectId%> />		
		<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
				
		<table border="1" cellspacing="0" cellpadding="2">
			<tr><td>项目编号：</td><td><%=pageBean.selectedProjData.projectId%></td></tr>
			<tr><td>项目名称：</td><td><%=pageBean.selectedProjData.projectName%></td></tr>
			<tr><td>项目描述：</td><td><%=pageBean.selectedProjData.projectDesc%></td></tr>
			<tr><td>地图服务地址：</td><td><%=pageBean.selectedProjData.mapUrl%></td></tr>
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