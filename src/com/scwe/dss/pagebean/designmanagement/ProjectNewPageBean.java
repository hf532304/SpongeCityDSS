package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class ProjectNewPageBean extends iWorkerPageBean{
  
  /**
	 * 
	 */
  private static final long serialVersionUID = -6941607399982773713L;

  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.ProjectNewPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  
  public ProjectNewPageBean(){
	super();
	setJspName("/pages/designmanagement/ProjectNewPage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {	
  }

  @Override
  protected iWorkerRequest createMyRequest(iWorkerRequest oldRequest) {
	if (oldRequest == null)
	  // TODO Auto-generated method stub
	  return new DesignManagementRequest();
	else {
	  try {
		DesignManagementRequest tmpReq = (DesignManagementRequest) oldRequest;
		return tmpReq;
	  } catch (Exception ex){
		return new DesignManagementRequest();
	  }
	}		
  }

  @Override
  protected boolean isValid(HttpServletRequest request) {
	boolean retVal = true;
	String nextPage = request.getParameter("Name"); 
	if (! getCancelPage().equals(nextPage)){	
		StringBuffer errBuf = new StringBuffer("");	  
		String projectName = request.getParameter("ProjectName");
		String projectDesc = request.getParameter("ProjectDesc");
		String mapURL = request.getParameter("MapURL");	
		String tRateARV = request.getParameter("TRateARV");	
		
		if (StringHelper.isEmpty(projectName)){
			errBuf.append("项目名称为空;");
			retVal = false;
		}
		if (StringHelper.isEmpty(projectDesc)){
			errBuf.append("项目描述为空;");
			retVal = false;
		}
		if (StringHelper.isEmpty(mapURL)){
			errBuf.append("地图服务地址为空;");
			retVal = false;
		}
		if (StringHelper.isEmpty(tRateARV)){
			errBuf.append("目标年径流总控制率为空;");
			retVal = false;
		}			
		setPageErrMsg(errBuf.toString());
	}
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	String nextPage = request.getParameter("Name"); 
	if (! getCancelPage().equals(nextPage)){		  
		DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
		String projectName = request.getParameter("ProjectName");
		String projectDesc = request.getParameter("ProjectDesc");
		String mapURL = request.getParameter("MapURL");
		String tRateARV = request.getParameter("TRateARV");		
		
		int retVal = DesignManagementDBHelper.insertProjectInfo(myProperty, projectName, projectDesc, mapURL, tRateARV, "Admin");
		if (retVal == 1){
			ProjectData myProjects[] = DesignManagementDBHelper.getMyProjectData(myProperty, "Admin");
			myRequest.setMyProjectData(myProjects);
			myRequest.setMyMsg("");		
		} else {
			myRequest.setMyMsg("数据库新增项目信息失败，请核查原因！");	  
		}
	}
  }

  public String getCancelPage(){
	  return "ProjectManagementPage";
  }

}
