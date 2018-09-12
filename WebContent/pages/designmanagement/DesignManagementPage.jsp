<%@page 
import="com.scwe.dss.pagebean.designmanagement.DesignManagementPageBean"
import="com.scwe.dss.datatransfer.DesignData"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
DesignManagementPageBean pageBean = (DesignManagementPageBean) session.getAttribute("PageBean"); 
DesignData myDesignData[] = pageBean.getMyDesigns();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form action="Page" method="post">
	
<div style="font-size:10px;" align="center">
	<div style="font-size:14px;">规划方案管理<br/><hr/></div><br/>
	
	<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />	
	<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
		  <input type="hidden" id="Name" name="Name" value="">
	<%
	if (myDesignData != null){	%>
	<table border="1" cellspacing="0" cellpadding="2">
		<tr><td></td><td>方案编号</td><td>方案名称</td><td>方案状态</td><td>雨型</td><td>INP文件</td><td>RPT文件</td><td>地图服务地址</td><td>项目编号</td><td>项目名称</td><td>创建时间</td></tr>
	<%for (int i=0; i<myDesignData.length; i++){ %>
		<tr><td><input type="radio" name="DefaultDesign" value="<%=myDesignData[i].projectId%>;<%=myDesignData[i].designId%>" <%=myDesignData[i].isDefault()%>/></td><td><%=myDesignData[i].designId %></td><td><%=myDesignData[i].designName%></td><td><%=myDesignData[i].designStatus%></td><td><%=myDesignData[i].rainName%></td><td><%=myDesignData[i].inpFileName%></td><td><%=myDesignData[i].rptFileName%></td><td width="40"><%=myDesignData[i].mapUrl%></td><td><%=myDesignData[i].projectId %></td><td><%=myDesignData[i].projectName%></td><td><%=myDesignData[i].creationTime%></td></tr>
	  <%}%>
	</table>
	<br/>
	  <input type="hidden" id="NextAction" name="NextAction" value="">
	<table><tr>
		<td><input type="submit" onclick="document.getElementById('Name').value='DesignNewPage'" value="新建方案"></td>
		<td><img src="images/clear.gif" width="10" height="1" border="0" alt="">
		<input type="submit" onclick="document.getElementById('Name').value='DesignEditPage'" value="编辑方案"></td>
		<td><img src="images/clear.gif" width="10" height="1" border="0" alt="">
		<input type="submit" onclick="document.getElementById('Name').value='DesignManagementPage';document.getElementById('NextAction').value='Default'" value="默认方案"></td>
		<td><img src="images/clear.gif" width="10" height="1" border="0" alt="">
		<input type="submit" onclick="document.getElementById('Name').value='DesignDeleteConfirmationPage'" value="删除方案"></td>
		<td><img src="images/clear.gif" width="10" height="1" border="0" alt="">
		<input type="submit" onclick="document.getElementById('Name').value='AllManagementPage'" value="返回"></td>
		</tr>
	</table>
	<%} else {%>	
		<tr><td align="center">尚无任何规划方案</td></tr>
	</table>
	<br/><br/>
	<table> 
		<tr><td><input type="submit" onclick="document.getElementById('Name').value='DesignNewPage'" value="新建方案"></td></tr>
	</table>
  <%}%>
	</div>
</form>
</body>
</html>