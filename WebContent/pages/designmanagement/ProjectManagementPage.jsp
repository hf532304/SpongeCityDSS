<%@page 
import="com.scwe.dss.pagebean.designmanagement.ProjectManagementPageBean"
import="com.scwe.dss.datatransfer.ProjectData"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
ProjectManagementPageBean pageBean = (ProjectManagementPageBean) session.getAttribute("PageBean"); 
ProjectData myProjData[] = pageBean.getMyProjects();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form action="Page" method="post">
	
<div style="font-size:10px;" align="center">
	<div style="font-size:14px;">项目管理<br/><hr/></div><br/>
	
	<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />	
	<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
		  <input type="hidden" id="Name" name="Name" value="">
	<%
	if (myProjData != null){	%>
	<table border="1" cellspacing="0" cellpadding="2" width="100%">
		<tr><td></td><td>项目编号</td><td>项目名称</td><td>项目描述</td><td>地图服务地址</td><td>目标年径流总控制率</td><td>创建者</td><td width="20">创建时间</td></tr>
	<%for (int i=0; i<myProjData.length; i++){ %>
		<tr><td><input type="radio" name="SelectedProject" value="<%=myProjData[i].projectId%>"/></td><td><%=myProjData[i].projectId %></td>
		<td><%=myProjData[i].projectName%></td><td><%=myProjData[i].projectDesc%></td><td><%=myProjData[i].mapUrl%></td>
		<td><%=myProjData[i].tRateARV.setScale(2, java.math.BigDecimal.ROUND_HALF_UP).toPlainString()%></td>
		<td><%=myProjData[i].creator%></td><td><%=myProjData[i].creationTime%></td></tr>
	  <%}%>
	</table>
	<br/>
	  <input type="hidden" id="NextAction" name="NextAction" value="">
	<table> 
		<tr>
		<td><input type="submit" onclick="document.getElementById('Name').value='ProjectNewPage'" value="新建项目"></td>
		<td><img src="images/clear.gif" width="10" height="1" border="0" alt="">
		<input type="submit" onclick="document.getElementById('Name').value='ProjectEditPage'" value="编辑项目"></td>
		<td><img src="images/clear.gif" width="10" height="1" border="0" alt="">
		<input type="submit" onclick="document.getElementById('Name').value='ProjectDeleteConfirmationPage'" value="删除项目"></td>
		<td><img src="images/clear.gif" width="10" height="1" border="0" alt="">
		<input type="submit" onclick="document.getElementById('Name').value='AllManagementPage'" value="返回"></td>		
	</tr></table><br/>
	<%} else {%>	
		<tr><td align="center">数据库中尚无建立任何项目</td></tr>
	</table>
	<br/><br/>
	<table> 
		<tr><td><input type="submit" onclick="document.getElementById('Name').value='ProjectNewPage'" value="新建项目"></td></tr>
	</table>
  <%}%>
	</div>
</form>
</body>
</html>