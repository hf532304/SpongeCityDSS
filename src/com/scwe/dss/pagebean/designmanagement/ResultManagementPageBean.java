package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;

public class ResultManagementPageBean extends iWorkerPageBean{
  
  private static final long serialVersionUID = -3672610882638187445L;
  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.ResultManagementPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  
  private DesignData[] myDesigns = null;
  
  public ResultManagementPageBean(){
	super();
	setJspName("/pages/designmanagement/ResultManagementPage.jsp");
  }

  public void doBeforeRun(Properties myProperty, HttpServletRequest request){
	String dId="";
	String pId="";
	DesignManagementRequest myRequest = null;
	myRequest = (DesignManagementRequest) getMyRequest();
	if ( myRequest.getMyDesignData() == null){	  
	  myDesigns = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
	  myRequest.setMyDesignData(myDesigns);
	} else {
  	  myDesigns = myRequest.getMyDesignData();
	}
	String nextAction = request.getParameter("NextAction");
	String selectedId = request.getParameter("DefaultDesign");
	if (!StringHelper.isEmpty(selectedId)){
		  String sID[] = selectedId.split(";");
		  if (sID != null){
		    pId = sID[0];
		    if (sID.length>1){
		      dId = sID[1];
		    }
		  }
		}
	if ("Default".equalsIgnoreCase(nextAction) && ! StringHelper.isEmpty(dId) && !"0".equals(dId)){  	
	  DesignManagementDBHelper.setDefaultDesign(myProperty, dId, "Admin");
	  setDefultDesign(dId);  
	}	
	
  }

  private void setDefultDesign(String designId){
	DesignData myDesigns[] = ((DesignManagementRequest ) getMyRequest()).getMyDesignData();	  
	for (int i=0; i<myDesigns.length; i++){
	  if (designId.equals(myDesigns[i].designId))
		myDesigns[i].isDefaultDesign = Boolean.valueOf(true);
	  else 
		myDesigns[i].isDefaultDesign = Boolean.valueOf(false);
	}
	((DesignManagementRequest ) getMyRequest()).setMyDesignData(myDesigns);	
  }

  public DesignData[] getMyDesigns(){
	return myDesigns; 
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
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub	
  }
  public String getCancelPage(){
	  return "AllManagementPage";
  }

  @Override
  public String isValidToAccess(){
    String retVal = "";
    String userStatus = getMyRequest().getMe().userStatus;
	if (! Constants.DESIGN_STATUS_SWMM.equals(userStatus) && ! Constants.DESIGN_STATUS_RPT.equals(userStatus) && ! Constants.DESIGN_STATUS_END.equals(userStatus))
		retVal = "默认方案尚未经模型计算,无法访问<运行结果管理>功能";
	return retVal;
  }  
  
}
