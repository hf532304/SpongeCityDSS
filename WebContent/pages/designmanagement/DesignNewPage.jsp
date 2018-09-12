<%@page 
import="com.scwe.dss.pagebean.designmanagement.DesignNewPageBean"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
DesignNewPageBean pageBean = (DesignNewPageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form name="Form1" id="Form1" action="Page" method="post">

		
		<div style="font-size:10px;" align="center">
			<div style="font-size:14px;">新增设计方案<br/><hr/></div><br/>
		<input type="hidden" id="Name" name="Name" value="DesignNewSubmitPage" />		
		<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />
		<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
				
		<table board="1">
				<tr><td>所属项目编号：</td><td>
				<input name="ProjectId" id="ProjectId" type="text" maxlength="12" size="12" value=<%=pageBean.myProjects[pageBean.myProjects.length-1].projectId%> readonly>
			</td></tr>
			<tr><td>所属项目名称：</td><td>
				<select name="ProjectName" onchange="document.getElementById('ProjectId').value=this.value">
				<%for (int i=0; i<pageBean.myProjects.length; i++){
					  if (i == pageBean.myProjects.length-1){
				%>
							<option value=<%=pageBean.myProjects[i].projectId%> selected><%=pageBean.myProjects[i].projectName%></option>
				<%  } else {%>				
							<option value=<%=pageBean.myProjects[i].projectId%>><%=pageBean.myProjects[i].projectName%></option>
				<%	}
				  }%>				
				</select> 			
			</td></tr>
			<tr><td colspan=2><img src="images/clear.gif" width="1" height="20" border="0" alt=""></td></tr>
			<tr><td>新方案名称：</td><td><input name="DesignName" type="text" maxlength="30" size="30" ></td></tr>
			<tr><td>默认方案：</td><td><input name="DefaultDesign" type="checkbox" value="Y" checked readonly></td></tr>
			<tr><td colspan=2><img src="images/clear.gif" width="1" height="20" border="0" alt=""></td></tr>			
			<tr><td>设计雨型：</td><td>
				<select name="RainName" onchange="document.getElementById('RainDesc').value=this.value.split(':', 1)">
				<%for (int i=0; i<pageBean.rainModels.length; i++){
					  if (i==0){
				%>
							<option value="<%=pageBean.rainModels[i].modelDesc%>:<%=pageBean.rainModels[i].modelName%>" selected><%=pageBean.rainModels[i].modelName%></option>
				<%  } else {%>				
							<option value="<%=pageBean.rainModels[i].modelDesc%>:<%=pageBean.rainModels[i].modelName%>"><%=pageBean.rainModels[i].modelName%></option>
				<%	}
				  }%>				
				</select> 			
			</td></tr>
			<tr><td>雨型描述：</td><td>
				<input name="RainDesc" id="RainDesc" type="text" value="<%=pageBean.rainModels[0].modelDesc%>" readonly size="100">
			</td></tr>
			
		</table>
		<br/><br/>
		<input type="submit" value="保存">
		<input type="button" onclick="document.getElementById('Name').value='<%=pageBean.getCancelPage()%>';document.getElementById('Form1').submit();" value="取消">
		
		
		</div>
</form>
<table>
</table>
</body>
</html>