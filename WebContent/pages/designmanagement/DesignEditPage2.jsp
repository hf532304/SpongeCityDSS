<%@page 
import="com.scwe.dss.pagebean.designmanagement.DesignEditPageBean"
import="com.scwe.dss.datatransfer.DesignData"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
DesignEditPageBean pageBean = (DesignEditPageBean) session.getAttribute("PageBean"); 
DesignData[] myDesignData = pageBean.getMyDesigns();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
</head>
<body>
<form name="Form1" id="Form1" action="Page" method="post">
	
			<div style="font-size:10px;" align="center">
			<div style="font-size:14px;">编辑方案<br/><hr/></div><br/>
		<input type="hidden" id="Name" name="Name" value="DesignEditSubmitPage" />
		<input type="hidden" name="CurrentPageBean" value="<%=pageBean.getClass().getName()%>" />		
		<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
				
		<table board="1">
				<tr><td>所属项目编号：</td><td>
				<% 
				  if (pageBean.getSelectedData().projectId == null || pageBean.getSelectedData().projectId==""){%>
						<input name="ProjectId" id="ProjectId" type="text" value="<%=pageBean.myProjects[0].projectId%>" readonly>					  					  
				 <% }	else {%>
						<input name="ProjectId" id="ProjectId" type="text" value="<%=pageBean.getSelectedData().projectId%>" readonly>					  
				  <%}
				%>
			</td></tr>
			<tr><td>所属项目名称：</td><td>
				<select name="ProjectName" onchange="document.getElementById('ProjectId').value=this.value">
				<%for (int i=0; i<pageBean.myProjects.length; i++){
					  if (pageBean.getSelectedData().projectId.equals(pageBean.myProjects[i].projectId)){
				%>
							<option value="<%=pageBean.myProjects[i].projectId%>" selected><%=pageBean.myProjects[i].projectName%></option>
				<%  } else {%>				
							<option value="<%=pageBean.myProjects[i].projectId%>"><%=pageBean.myProjects[i].projectName%></option>
				<%	}
				  }%>				
				</select> 			
			</td></tr>
			<tr><td colspan=2><img src="images/clear.gif" width="1" height="20" border="0" alt=""></td></tr>
			<tr><td>方案编号：</td><td><input name="DesignId" type="text" value="<%=pageBean.getSelectedData().designId%>" readonly></td></tr>
			<tr><td>方案名称：</td><td><input name="DesignName" type="text" maxlength="40" size="30" value="<%=pageBean.getSelectedData().designName%>" ></td></tr>
			<tr><td>方案状态：</td><td><input name="DesignStatus" type="text" maxlength="30" size="30" value="<%=pageBean.getSelectedData().designStatus%>" readonly></td></tr>
			<tr><td>默认方案：</td><td><input name="DefaultSelection" type="checkbox" value="Y" <%=pageBean.getSelectedData().isDefault()%> DISABLED></td></tr>
			
			<tr><td>设计雨型：</td><td>
				<select name="RainName" onchange="document.getElementById('RainDesc').value=this.value.split(':', 1)">
				<%for (int i=0; i<pageBean.rainModels.length; i++){
					  if (pageBean.rainModels[i].modelName.equals(pageBean.getSelectedData().rainName)){
				%>
							<option value="<%=pageBean.rainModels[i].modelDesc%>:<%=pageBean.rainModels[i].modelName%>" selected><%=pageBean.rainModels[i].modelName%></option>
				<%  } else {%>				
							<option value="<%=pageBean.rainModels[i].modelDesc%>:<%=pageBean.rainModels[i].modelName%>"><%=pageBean.rainModels[i].modelName%></option>
				<%	}
				  }%>				
				</select> 			
			</td></tr>
			<tr><td>雨型描述：</td><td>
				<input name="RainDesc" id="RainDesc" type="text" size="50" value="<%=pageBean.myRainDesc%>" readonly>
			</td></tr>
									
			<tr><td>INP文件：</td><td><input name="INPFile" type="text" value="<%=pageBean.getSelectedData().inpFileName%>" readonly></td></tr>
			<tr><td>RPT文件：</td><td><input name="RPTFile" type="text" value="<%=pageBean.getSelectedData().rptFileName%>" readonly></td></tr>
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