package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.datatransfer.RainModel;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;

public class DesignNewPageBean extends iWorkerPageBean{
  
  private static final long serialVersionUID = -3672610882638187445L;
  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DesignNewPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  public RainModel rainModels[] = null;  
  public ProjectData[] myProjects = null;
  
  public DesignNewPageBean(){
	super();
	setJspName("/pages/designmanagement/DesignNewPage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	rainModels = myRequest.getRainModels();
	myProjects = myRequest.getMyProjectData();
	if (rainModels == null || rainModels.length<=0){
		rainModels = DesignManagementDBHelper.getMyRainModels(myProperty);
		myRequest.setRainModels(rainModels);
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
  protected boolean isValid(HttpServletRequest request) {
	boolean retVal = true;
	String nextPage = request.getParameter("Name"); 
	if (! getCancelPage().equals(nextPage)){
		StringBuffer errBuf = new StringBuffer("");
		String projectId = request.getParameter("ProjectId");
		String projectName = request.getParameter("ProjectName");
		String designName = request.getParameter("DesignName");
		String isDefault = request.getParameter("DefaultDesign");
	
		
		if (StringHelper.isEmpty(projectId)){
			errBuf.append("项目编号为空;");
			retVal = false;
		}
		if (StringHelper.isEmpty(projectName)){
			errBuf.append("项目名称为空;");
			retVal = false;
		}	
		if (StringHelper.isEmpty(designName)){
			errBuf.append("设计方案名称为空;");
			retVal = false;
		}
		if (StringHelper.isEmpty(isDefault)){
			errBuf.append("默认方案为空;");
			retVal = false;
		}	
		setPageErrMsg(errBuf.toString());
	}
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	String projectId = request.getParameter("ProjectId");
	String designName = request.getParameter("DesignName");
	String isDefault = request.getParameter("DefaultDesign");
	String rainName = request.getParameter("RainName");
	if (!StringHelper.isEmpty(rainName)){
		rainName = rainName.split(":")[1];	
		}	
	int num = DesignManagementDBHelper.insertDesignInfo(myProperty, projectId, designName, Constants.DESIGN_STATUS_NEW, isDefault, rainName, "Admin");
	if (num == 1){
	  DesignData myDesigns[] = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
	  myRequest.setMyDesignData(myDesigns);
	  myRequest.setMyMsg("");
	} else {
	  myRequest.setMyMsg("数据库新增设计方案信息失败，请核查原因!");
	}
  }
  
  public String getCancelPage(){
	  return "DesignManagementPage";
  }
  
}
