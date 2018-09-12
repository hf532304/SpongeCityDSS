package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.MyThankYouPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class ProjectDeleteSubmitPageBean extends MyThankYouPageBean{


/**
	 * 
	 */
  private static final long serialVersionUID = -1023646481689746708L;

  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.ProjectDeleteSubmitPageBean"; 

  public static String RequestName = "DesignManagementRequest";

  private String titleDisplay="项目管理";
  private String msgDisplay="";
  
  public ProjectDeleteSubmitPageBean(){
	super();
	setJspName("/pages/common/MyThankYouPage.jsp");
  }

  public void doBeforeRun(HttpServletRequest request){
	
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	if ( myRequest.getMyProjectData() == null){	  
	  // Impossible, should be set by DesignManagementPageBean
	  //myDesigns = DesignManagementDBHelper.getMyDesignData(myProperty);
	  //myRequest.setMyDesignData(myDesigns);
	}
	String selectedId = request.getParameter("SelectedProject");
	if (! StringHelper.isEmpty(selectedId) && ! "0".equals(selectedId) ){
	  int retVal = DesignManagementDBHelper.deleteProject(myProperty, selectedId);
	  if (retVal >= 1){
		msgDisplay = "<div align='center'>该项目及所属方案信息已经从数据库中删除</div>";
		ProjectData myProjects[] = DesignManagementDBHelper.getMyProjectData(myProperty, "Admin");
		myRequest.setMyProjectData(myProjects);
	  } else {
		msgDisplay = "<div align='center'>删除数据库中的项目及其方案信息失败，请核查原因</div>";	  
	  }
	} else {
		msgDisplay = "<div align='center'>无项目可删除</div>";	  		
	}
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
  public String getTitleDisplay() {
	// TODO Auto-generated method stub
	return titleDisplay;
  }

  @Override
  public String getMsgDisplay() {
	// TODO Auto-generated method stub
	return msgDisplay;
  }

@Override
protected boolean isValid(HttpServletRequest request) {
	// TODO Auto-generated method stub
	return true;
}

@Override
protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub
	
}

@Override
public String getNextPage() {
	// TODO Auto-generated method stub
	return "ProjectManagementPage";
}

}
