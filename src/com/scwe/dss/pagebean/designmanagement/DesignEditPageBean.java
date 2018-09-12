package com.scwe.dss.pagebean.designmanagement;

import java.util.ArrayList;
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

public class DesignEditPageBean extends iWorkerPageBean{
  
  private static final long serialVersionUID = -3672610882638187445L;
  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DesignEditPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  private DesignData myDesigns[] = null;
  public RainModel rainModels[] = null;
  public String myRainDesc = "";
  public ProjectData[] myProjects = null;  
  
  DesignManagementRequest myRequest = null;
  
  public DesignEditPageBean(){
	super();
	setJspName("/pages/designmanagement/DesignEditPage.jsp");
  }

  public DesignData[] getMyDesigns(){
	return myDesigns; 
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub
	String pId="";
	String dId="";
	myRequest = (DesignManagementRequest) getMyRequest();
	myDesigns = myRequest.getMyDesignData();
	myProjects = myRequest.getMyProjectData();
	rainModels = myRequest.getRainModels();
	if (rainModels == null || rainModels.length<=0){
		rainModels = DesignManagementDBHelper.getMyRainModels(myProperty);
		myRequest.setRainModels(rainModels);
	}
	String sId = request.getParameter("DefaultDesign");
	if (!StringHelper.isEmpty(sId)){
		String sID[] = sId.split(";");
		if (sID != null){
		  pId = sID[0];
		  if (sID.length>1){
		    dId = sID[1];
		  }
		}
	  myRequest.selectedDesignData = getSelectedData(myRequest.getMyDesignData(), pId, dId);
  	  myRainDesc = getMyRainDesc(rainModels, myRequest.selectedDesignData.rainName);
  	  if (StringHelper.isEmpty(myRainDesc)){
  		myRainDesc = rainModels[0].modelDesc;
  	  }
	}	
  }

  private String getMyRainDesc(RainModel[] rainModels, String myRainName){
	String retVal = "";
	for (int i=0; i<rainModels.length; i++){
	  if (rainModels[i].modelName.equals(myRainName)){
		retVal = rainModels[i].modelDesc;
		i = rainModels.length;
	  }
	}
	return retVal;
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

  public DesignData getSelectedData(){
    return myRequest.selectedDesignData;
  }   
  
  public int getProjectNum(){
	int retVal = -1;
	if (myDesigns != null){
	  retVal = myDesigns.length;	
	}
	return retVal;
  }
  
  public String getProjectId(int i){
	String retVal = "-1";
	if (myDesigns != null && myDesigns.length>0 && myDesigns.length>i){
	  retVal = myDesigns[i].projectId;	
	}
	return retVal;
  }
  public String getProjectName(int i){
	String retVal = "-1";
	if (myDesigns != null && myDesigns.length>0 && myDesigns.length>i){
	  retVal = myDesigns[i].projectName;	
	}
	return retVal;
  }
  
  @Override
  protected boolean isValid(HttpServletRequest request) {
	boolean retVal = true;
	String nextPage = request.getParameter("Name"); 
	if (! getCancelPage().equals(nextPage) && ! getUploadPage().equals(nextPage)){	
		StringBuffer errBuf = new StringBuffer("");
		String projectId = request.getParameter("ProjectId");
		String projectName = request.getParameter("ProjectName");	
		String designName = request.getParameter("DesignName");
		String isDefault = request.getParameter("DefaultSelection");
	
		if (StringHelper.isEmpty(projectName)){
			errBuf.append("项目名称为空;");
			retVal = false;
		}	
		if (StringHelper.isEmpty(designName)){
			errBuf.append("方案名称为空;");
			retVal = false;
		}
		setPageErrMsg(errBuf.toString());
	}
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	String nextPage = request.getParameter("Name"); 
	if (! getCancelPage().equals(nextPage) ){		  
	  DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	  String projectId = request.getParameter("ProjectId");	
	  String designId = request.getParameter("DesignId");
	  String designName = request.getParameter("DesignName");
	  String rName= request.getParameter("RainName");
	  if (!StringHelper.isEmpty(rName)){
	    rName = rName.split(":")[1];	
	  }
	  if ( ! getUploadPage().equals(nextPage) ){		  		
		int num=-1;
		if ("0".equals(designId))  // "0" means NULL in DesignInfo table, which means Not Exist so INSERT it
		  num = DesignManagementDBHelper.insertDesignInfo(myProperty, projectId, designName, Constants.DESIGN_STATUS_NEW, "Y", rName, "Admin");
		else		
		  num = DesignManagementDBHelper.updateDesignInfo(myProperty, projectId, designId, designName, rName, "Admin");
		if (num == 1){
		  DesignData myDesigns[] = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
		  myRequest.setMyDesignData(myDesigns);
		  myRequest.setMyMsg("");
		} else {
		  myRequest.setMyMsg("更新数据库中的设计方案失败，请核查原因!");
		}
	  }
	}
  }
  
  public String getUploadPage(){
	  return "UploadFilePage";
  }

  public String getCancelPage(){
	  return "DesignManagementPage";
  }
  

}
