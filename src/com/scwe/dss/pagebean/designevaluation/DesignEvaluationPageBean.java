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
import com.scwe.dss.pagebean.designmanagement.ResultManagementPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;
import com.scwe.dss.util.FileHelper;

public class DesignEvaluationPageBean extends iWorkerPageBean{
 
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
  public RPTSubRunoffData srfData[]=null;

  private BigDecimal totalVolumn= new BigDecimal(0);
  public String totalVolumnStr="0";    
  public String defaultRainDesc="";
  private int pageOfSrfDataStr=0;
  /*
  public RPTData rptData;
  private String srfDataStr, lfsDataStr;
  public static int NumPerPage = 25;
  private int pageOfLfsDataStr=0;
  private int pageOfSrfDataStr=0;
  */
  public DesignEvaluationPageBean(){
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
	srfData = DesignManagementDBHelper.getSRFData(myProperty, defaultRPTName);

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
    
	myRequest.setPageOfLfsDataStr(1);
	myRequest.setPageOfSrfDataStr(1);
	RPTData rptData = new RPTData();
	rptData.srfData = srfData;
	myRequest.rptData = rptData;
	
  }

  public String getSRFPageStr(){
	  return StringHelper.mockPageStr(1, srfData.length, Constants.NUM_PER_PAGE);
  }

  public String getSrfDataStr(){
	  return getSrfDataStr(1);
  }
  public String getSrfDataStrNext(){
	  if(pageOfSrfDataStr > srfData.length/Constants.NUM_PER_PAGE )
		  pageOfSrfDataStr =  srfData.length/Constants.NUM_PER_PAGE;
	  else
		  pageOfSrfDataStr++;
	  return getSrfDataStr(pageOfSrfDataStr);
  }
  public String getSrfDataStrPrevious(){
	  if(pageOfSrfDataStr <=0 )
		  pageOfSrfDataStr = 0;
	  else
		  pageOfSrfDataStr--;
	  return getSrfDataStr(pageOfSrfDataStr);
  }  
  public String getSrfDataStr(int pageNum){
	 if (pageNum<=0)
		 pageNum=1;
	 int numStart = (pageNum-1) * Constants.NUM_PER_PAGE;
	 RPTSubRunoffData[] tmpData;
	 if (numStart < srfData.length){
		 tmpData = new RPTSubRunoffData[pageNum * Constants.NUM_PER_PAGE - numStart];
		 System.arraycopy(srfData, numStart, tmpData, 0, pageNum * Constants.NUM_PER_PAGE - numStart);
	 } else {
		 numStart = srfData.length % Constants.NUM_PER_PAGE;
		 if (numStart==0)
			 numStart = Constants.NUM_PER_PAGE;
		 tmpData = new RPTSubRunoffData[numStart];
		 System.arraycopy(srfData, srfData.length-numStart, tmpData, 0, numStart);		 
	 }		 
	 return (new Gson()).toJson(tmpData);
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
public String isValidToAccess(){
  String retVal = "";
  String userStatus = getMyRequest().getMe().userStatus;
  if (! Constants.DESIGN_STATUS_RPT.equals(userStatus) && ! Constants.DESIGN_STATUS_END.equals(userStatus))
	retVal = "RPT文件尚未生成,无法访问<方案评价>功能";
  return retVal;
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
