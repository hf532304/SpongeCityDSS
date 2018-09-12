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

public class DesignPageBean extends iWorkerPageBean{
  
  /**
	 * 
	 */
	private static final long serialVersionUID = -5618640376294141913L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DesignPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  
  DesignManagementRequest myRequest = null;
  
  public DesignPageBean(){
	super();
	setJspName("/pages/designmanagement/DesignPage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub
  }

  public String getCancelPage(){
	return "DesignManagementPage";
  }

  @Override
  protected iWorkerRequest createMyRequest(iWorkerRequest oldRequest) {
	return new iWorkerRequest();	
  }

 @Override
  protected boolean isValid(HttpServletRequest request) {
	boolean retVal = true;
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
  }

}
