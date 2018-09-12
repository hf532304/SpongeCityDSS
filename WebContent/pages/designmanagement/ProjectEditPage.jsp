<%@page 
import="com.scwe.dss.pagebean.designmanagement.ProjectEditPageBean"
import="com.scwe.dss.datatransfer.ProjectData"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
ProjectEditPageBean pageBean = (ProjectEditPageBean) session.getAttribute("PageBean"); 
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
			<div style="font-size:14px;">编辑项目<br/><hr/></div><br/>
			
		<input type="hidden" id="Name" name="Name" value="ProjectEditSubmitPage" />				
		<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />
		<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
				
		<table>
			<tr><td>项目编号：</td><td><input name="ProjectId" type="text" value="<%=pageBean.selectedProjData.projectId%>" readonly></td></tr>
			<tr><td>项目名称：</td><td><input name="ProjectName" type="text" maxlength="30" size="30" value="<%=pageBean.selectedProjData.projectName%>" ></td></tr>
			<tr><td>项目描述：</td><td><input name="ProjectDesc" type="text" maxlength="100" size="100" value="<%=pageBean.selectedProjData.projectDesc%>"></td></tr>
			<tr><td>地图服务地址：</td><td><input name="MapURL" type="text" maxlength="100" size="100" value="<%=pageBean.selectedProjData.mapUrl%>"></td></tr>
			<tr><td>目标年径流总控制率：</td><td><input name="TRateARV" type="text" pattern="[0-1].?[0-9]*" title="1位整数+5位小数,最大值为1" maxlength="7" size="7" value="<%=pageBean.selectedProjData.tRateARV.setScale(2, java.math.BigDecimal.ROUND_HALF_UP).toPlainString()%>"></td></tr>
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