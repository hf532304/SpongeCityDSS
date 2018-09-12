package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class ProjectDeleteConfirmationPageBean extends iWorkerPageBean{
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 3899959876040988986L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.ProjectDeleteConfirmationPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  private ProjectData myProjects[] = null;
  public ProjectData selectedProjData = new ProjectData();
  
  DesignManagementRequest myRequest = null;
  
  public ProjectDeleteConfirmationPageBean(){
	super();
	setJspName("/pages/designmanagement/ProjectDeleteConfirmationPage.jsp");
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
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
  }
  
  public String getCancelPage(){
	  return "ProjectManagementPage";
  }
}
