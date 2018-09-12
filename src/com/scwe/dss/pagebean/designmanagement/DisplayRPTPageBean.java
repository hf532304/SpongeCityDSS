package com.scwe.dss.pagebean.designmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.RPTData;
import com.scwe.dss.datatransfer.RainModelData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;
import com.scwe.dss.util.FileHelper;

public class DisplayRPTPageBean extends iWorkerPageBean{

/**
	 * 
	 */
  private static final long serialVersionUID = 5345205415214441633L;

  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DisplayRPTPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  
  private String defaultPrjName, defaultDsnName, defaultRPTName;
  
  public RPTData rptData;
  
  public DisplayRPTPageBean(){
	super();
	setJspName("/pages/designmanagement/DisplayRPTPage.jsp");
  }

  public void doBeforeRun(Properties aProperty, HttpServletRequest request){
	DesignData myDesignData[];

	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	if ( myRequest.getMyDesignData() == null){	  
		myDesignData = DesignManagementDBHelper.getMyDesignData(aProperty, "Admin");
		myRequest.setMyDesignData(myDesignData);
	} else {
		myDesignData = myRequest.getMyDesignData();
	}
	for (int i=0; i<myDesignData.length; i++){
	  if (myDesignData[i].isDefaultDesign){
		  defaultPrjName = myDesignData[i].projectName;
		  defaultDsnName = myDesignData[i].designName;
		  defaultRPTName = myDesignData[i].rptFileName;
		  i=myDesignData.length;
	  }
	}
	rptData = parseRPTFile(aProperty, defaultRPTName);
	rptData.rptFileName=defaultRPTName;
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
	return true;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub	
  }
  
  private static RPTData parseRPTFile(Properties myProperty, String fName){
	String fileName = myProperty.getProperty("RPT_PATH") + File.separator + fName;
	return FileHelper.parseRPTFile(fileName);
  }

  public String getDefaultPrjName() {
	return this.defaultPrjName;
  }
  public String getDefaultDsnName() {
	return this.defaultDsnName;
  }
  public String getDefaultRPTName() {
	return this.defaultRPTName;
  }
  
}
