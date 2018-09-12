package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.MyMainRequest;
import com.scwe.dss.request.iWorkerRequest;

public class DisplayDesignPageBeanbk extends iWorkerPageBean{
  
  private static final long serialVersionUID = -375680551104010354L;
  
  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DisplayDesignPageBean"; 
  public static String RequestName = "DesignManagementRequest";
  
  public DisplayDesignPageBeanbk(){
	super();
	setJspName("/pages/designmanagement/DisplayDesignPage.jsp");
  }

  private String defaultPrjName, defaultDsnName, defaultINPName;
  public String getDefaultPrjName() {
	return this.defaultPrjName;
  }
  public String getDefaultDsnName() {
	return this.defaultDsnName;
  }
  public String getDefaultINPName() {
	return this.defaultINPName;
  }

@Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	DesignData myDesignData[];

	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	if ( myRequest.getMyDesignData() == null){	  
		myDesignData = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
		myRequest.setMyDesignData(myDesignData);
	} else {
		myDesignData = myRequest.getMyDesignData();
	}
	for (int i=0; i<myDesignData.length; i++){
	  if (myDesignData[i].isDefaultDesign){
		  defaultPrjName = myDesignData[i].projectName;
		  defaultDsnName = myDesignData[i].designName;
		  defaultINPName = myDesignData[i].inpFileName;
		  i = myDesignData.length;
	  }
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
	// TODO Auto-generated method stub
	return true;
}

@Override
protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub
	
}


}
