package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class ProjectManagementPageBean extends iWorkerPageBean{
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 7802991180526644875L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.ProjectManagementPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  
  private ProjectData[] myProjects = null;
  
  public ProjectManagementPageBean(){
	super();
	setJspName("/pages/designmanagement/ProjectManagementPage.jsp");
  }

  public void doBeforeRun(Properties myProperty, HttpServletRequest request){
	DesignManagementRequest myRequest = null;
	myRequest = (DesignManagementRequest) getMyRequest();
	if ( myRequest.getMyProjectData() == null){	  
	  myProjects = DesignManagementDBHelper.getMyProjectData(myProperty, "Admin");
	  myRequest.setMyProjectData(myProjects);
	} else {
  	  myProjects = myRequest.getMyProjectData();
	}

  }

  public ProjectData[] getMyProjects(){
	return myProjects; 
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
	boolean retVal= true;
	String pId = request.getParameter("SelectedProject");
	String nextPageName = request.getParameter("Name") + "Bean";
	StringBuffer errBuf = new StringBuffer("");
	if (ProjectDeleteConfirmationPageBean.PageBeanName.endsWith(nextPageName) && StringHelper.isEmpty(pId)){
		errBuf.append("无项目可删除;");
		retVal = false;
	} else if (ProjectEditPageBean.PageBeanName.endsWith(nextPageName) && StringHelper.isEmpty(pId)){
		errBuf.append("无项目可编辑;");
		retVal = false;
	}
	setPageErrMsg(errBuf.toString());
	
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub	
  }

}
