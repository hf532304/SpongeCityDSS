package com.scwe.dss.pagebean.designmanagement;

import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;

public class ProjectEditPageBean extends iWorkerPageBean{
  
  /**
	 * 
	 */
	private static final long serialVersionUID = -5244254045873041777L;

  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.ProjectEditPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  private ProjectData myProjects[] = null;
  public ProjectData selectedProjData = new ProjectData();
  
  DesignManagementRequest myRequest = null;
  
  public ProjectEditPageBean(){
	super();
	setJspName("/pages/designmanagement/ProjectEditPage.jsp");
  }

  public ProjectData[] getMyProjects(){
	return myProjects; 
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub
	myRequest = (DesignManagementRequest) getMyRequest();
	myProjects = myRequest.getMyProjectData();
	selectedProjData.projectId = request.getParameter("SelectedProject");
	if (!StringHelper.isEmpty(selectedProjData.projectId)){
		for (int i=0; i<myProjects.length; i++){
			if (myProjects[i].projectId.equals(selectedProjData.projectId)){
				selectedProjData = myProjects[i];
				i = myProjects.length;
			}
		}
	}
	myRequest.selectedProjectId = selectedProjData.projectId;
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

  public int getProjectNum(){
	int retVal = -1;
	if (myProjects != null){
	  retVal = myProjects.length;	
	}
	return retVal;
  }
  
  public String getProjectId(int i){
	String retVal = "-1";
	if (myProjects != null && myProjects.length>0 && myProjects.length>i){
	  retVal = myProjects[i].projectId;	
	}
	return retVal;
  }
  public String getProjectName(int i){
	String retVal = "-1";
	if (myProjects != null && myProjects.length>0 && myProjects.length>i){
	  retVal = myProjects[i].projectName;	
	}
	return retVal;
  }
  
  @Override
  protected boolean isValid(HttpServletRequest request) {
	boolean retVal = true;
	String nextPage = request.getParameter("Name"); 
	if (! getCancelPage().equals(nextPage)){		
		StringBuffer errBuf = new StringBuffer("");
		String projectId = request.getParameter("ProjectId");
		String projectName = request.getParameter("ProjectName");	
		String projectDesc = request.getParameter("ProjectDesc");
		String mUrl = request.getParameter("MapURL");
		String tRateARV = request.getParameter("TRateARV");
	
		if (StringHelper.isEmpty(projectName)){
			errBuf.append("项目名称为空;");
			retVal = false;
		}	
		if (StringHelper.isEmpty(projectDesc)){
			errBuf.append("项目描述为空;");
			retVal = false;
		}
		if (StringHelper.isEmpty(mUrl)){
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
		String projectId = request.getParameter("ProjectId");	
		String projectName = request.getParameter("ProjectName");	
		String projectDesc = request.getParameter("ProjectDesc");
		String mUrl = request.getParameter("MapURL");
		String tRateARV = request.getParameter("TRateARV");		
		int num = DesignManagementDBHelper.updateProjectInfo(myProperty, projectId, projectName, projectDesc, mUrl, tRateARV, "Admin");
		if (num == 1){
		  ProjectData myProjects[] = DesignManagementDBHelper.getMyProjectData(myProperty, "Admin");
		  myRequest.setMyProjectData(myProjects);
		  myRequest.setMyMsg("");	  
		} else {
		  myRequest.setMyMsg("更新数据库中的项目信息失败，请核查原因!");
		}
	}
  }
  public String getCancelPage(){
	  return "ProjectManagementPage";
  }
  
}
