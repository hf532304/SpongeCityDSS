package com.scwe.dss.pagebean.designevaluation;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.RPTData;
import com.scwe.dss.datatransfer.RPTLFSummaryData;
import com.scwe.dss.datatransfer.RPTSubRunoffData;
import com.scwe.dss.datatransfer.RainModelData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.FileHelper;

public class DesignEvaluationPageBean2 extends iWorkerPageBean{
 
  /**
	 * 
	 */
  private static final long serialVersionUID = 6062867018729908170L;
  public static String PageBeanName = "com.scwe.dss.pagebean.designevaluation.DesignEvaluationPageBean"; 
  public static String RequestName = "DesignEvaluationRequest";

  private String defaultPrjName, defaultDsnName, defaultRainName, defaultINPName, defaultRPTName;
  private RainModelData[] myRainData;
  public String xAxisStr="";
  public String xAxisData="";
  private BigDecimal totalVolumn= new BigDecimal(0);
  public String totalVolumnStr="0";    
  public String defaultRainDesc="";
  /*
  public RPTData rptData;
  private String srfDataStr, lfsDataStr;
  public static int NumPerPage = 25;
  private int pageOfLfsDataStr=0;
  private int pageOfSrfDataStr=0;
  */
  public DesignEvaluationPageBean2(){
	super();
	setJspName("/pages/designevaluation/DesignEvaluationPage.jsp");
  }
  
  public String getDefaultPrjName() {
	return this.defaultPrjName;
  }
  public String getDefaultDsnName() {
	return this.defaultDsnName;
  }
  public String getDefaultINPName() {
	return this.defaultINPName;
  }
  public String getDefaultRPTName() {
	return this.defaultRPTName;
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
		  defaultRainName= myDesignData[i].rainName; 
		  defaultINPName = myDesignData[i].inpFileName;
		  defaultRPTName = myDesignData[i].rptFileName;
		  i = myDesignData.length;
	  }
	}
	myRainData = DesignManagementDBHelper.getMyRainModelData(myProperty, defaultRainName);
	StringBuffer buffer1 = new StringBuffer("['");
	StringBuffer buffer2 = new StringBuffer("[");	
	for (int i=0; i<myRainData.length; i++){
	  if (i==0){
		buffer1.append(myRainData[i].rainTime);
		buffer2.append(myRainData[i].rainValume);
	  } else {
		buffer1.append("','").append(myRainData[i].rainTime);
		buffer2.append(",").append(myRainData[i].rainValume);		  
	  }
	  totalVolumn=totalVolumn.add(myRainData[i].rainValume);
	  totalVolumnStr = totalVolumn.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}
    buffer1.append("']");
    buffer2.append("]");
    defaultRainDesc = myRainData[0].modelDesc;
    xAxisStr = buffer1.toString();
    xAxisData = buffer2.toString();
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

  private static RPTData parseRPTFile(Properties myProperty, String fName){
	String fileName = myProperty.getProperty("RPT_PATH") + File.separator + fName;
	return FileHelper.parseRPTFile(fileName);
  }

}
