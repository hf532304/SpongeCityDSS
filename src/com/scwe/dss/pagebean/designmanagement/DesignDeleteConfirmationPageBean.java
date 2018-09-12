package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class DesignDeleteConfirmationPageBean extends iWorkerPageBean{
  
/**
	 * 
	 */
	private static final long serialVersionUID = 7882541967842262016L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DesignDeleteConfirmationPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  public DesignData selectedDesignData = new DesignData();
  
  DesignManagementRequest myRequest = null;
  
  public DesignDeleteConfirmationPageBean(){
	super();
	setJspName("/pages/designmanagement/DesignDeleteConfirmationPage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub
	String pId="";
	String dId="";
	myRequest = (DesignManagementRequest) getMyRequest();
	String sId = request.getParameter("DefaultDesign");
	if (!StringHelper.isEmpty(sId)){
		String sID[] = sId.split(";");
		if (sID != null){
		  pId = sID[0];
		  if (sID.length>1){
		    dId = sID[1];
		  }
		}
	  selectedDesignData = getSelectedData(myRequest.getMyDesignData(), pId, dId);
	  myRequest.selectedDesignData = selectedDesignData;
	}

  }
  
  public String getCancelPage(){
	  return "DesignManagementPage";
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
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
  }

}
