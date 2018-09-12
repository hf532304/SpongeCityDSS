<%@page 
import="com.scwe.dss.pagebean.designmanagement.RainManagementPageBean"
import="com.scwe.dss.datatransfer.RainModel"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
RainManagementPageBean pageBean = (RainManagementPageBean) session.getAttribute("PageBean"); 
RainModel[] myModels = pageBean.getMyRainModels();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>海绵城市规划</title>
  <script src="js/javascript.js" type="text/javascript" language="JavaScript"></script>
  <script>
  function getRainData(mName) { 
	  var tmpStr = null;
     //1/得到xhr对象  
     var xhr=getXHR();  
     //2.注册状态变化监听器  
     xhr.onreadystatechange=function(){  
        if(xhr.readyState==4) {  
          if(xhr.status==200) {
        	  tmpStr = xhr.responseText;
          }  
        }  
		  	if (tmpStr==""){
		  		document.getElementById('RainData').value ="没有取到数据";   		  		
		  	} else {
		  		document.getElementById('RainData').value = tmpStr;
     		}
     }  
     //3.建立与服务器的连接  
     xhr.open("GET","Page"+"?Name=Ajax&ModelName="+mName+"&CurrentPageBean=RainData");  
     //4.向服务器发出请求  
     xhr.send();  
  };
  
  function updateINP(mName,designId) { 
	  var tmpStr = null;
    //1/得到xhr对象  
    var xhr=getXHR();  
    //2.注册状态变化监听器  
    xhr.onreadystatechange=function(){  
       if(xhr.readyState==4) {  
         if(xhr.status==200) {
       	  tmpStr = xhr.responseText;
         }  
       }  
		  	if (tmpStr==""){
		  		document.getElementById('ReturnMSG').innerHTML ="没有取到数据";        		  		
		  	} else {
		  		document.getElementById('ReturnMSG').innerHTML = "INP文件更新完毕";
    		}
    }  
    //3.建立与服务器的连接  
    xhr.open("GET","Page"+"?Name=Ajax&ModelName="+mName+"&DesignID="+designId+"&CurrentPageBean=UpdateINP");  
    //4.向服务器发出请求  
    xhr.send();  
  };
  
  </script>
</head>
<body>
<form name="Form1" id="Form1" action="Page" method="post">
	
		<div style="font-size:14px;" align="center">更新默认方案INP文件中的降雨数据<br/><hr/></div><br/>
		
		<input type="hidden" id="Name" name="Name" value="DesignEditSubmitPage" />				
		<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />
		<input type="hidden" name="DesignID" value=<%=pageBean.getClass().getName()%> />
		<div id="ReturnMSG" style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>
				
		<table width="100%">
			<tr><td width="40%" align="right">默认方案：</td><td width="60%">
			<input type="hidden" name="DefaultDesignId" id="DefaultDesignId" value="<%=pageBean.getMyDesignId()%>" size="50" readonly/>
			<input name="DefaultDesign" id="DefaultDesign" value="<%=pageBean.getMyDesignName()%>" size="50" readonly/>
			</td></tr>
			<tr><td width="40%" align="right">INP文件：</td><td>
			<input name="DefaultINP" id="DefaultINP" value="<%=pageBean.getMyINPFileName()%>" size="50" readonly/>
			</td></tr>
			<tr><td colspan="2"><img src="images/clear.gif" width="1" height="10" border="0" alt=""></td></tr>	
			<tr><td width="40%" align="right">请选择降雨类型：</td><td>
				<select name="ModelName" id="ModelName" onchange="getRainData(this.value)">
				<%for (int i=0; i<myModels.length; i++){
					  if (myModels[i].modelName.equals(pageBean.getMyRainModel())){
				%>
							<option value="<%=myModels[i].modelName%>" selected><%=myModels[i].modelName%></option>
				<%  } else {%>				
							<option value="<%=myModels[i].modelName%>"><%=myModels[i].modelName%></option>
				<%	}
				  }%>								
				</select> 			
			</td></tr>
			<tr><td colspan="2"><img src="images/clear.gif" width="1" height="10" border="0" alt=""></td></tr>	
			<tr><td width="40%" align="right">降雨数据：</td><td>
			<textarea name="RainData" id="RainData" rows="6" readonly><%=pageBean.getRainData(pageBean.getMyRainModel())%></textarea>
			</td></tr>
			<tr><td colspan="2"><img src="images/clear.gif" width="1" height="20" border="0" alt=""></td></tr>				
			<tr><td colspan=2 align="center">
					<button type="button" value="更新INP" onclick="updateINP(document.getElementById('ModelName').value, document.getElementById('DefaultDesignId').value)">开始更新INP文件</button>			
					<input type="button" onclick="document.getElementById('Name').value='<%=pageBean.getCancelPage()%>';document.getElementById('Form1').submit();" value="取消">
			</td></tr>		
				
		</table>

</form>
<table>
</table>
</body>
</html>