package com.scwe.dss.pagebean.designmanagement;

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
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.MyMainRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;
import com.scwe.dss.util.FileHelper;

public class DisplayDesignPageBean2 extends iWorkerPageBean{
  
/**
	 * 
	 */
  private static final long serialVersionUID = -6983182241808144425L;
  
  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DisplayDesignPageBean"; 
  public static String RequestName = "DesignManagementRequest";

  private String defaultPrjName, defaultDsnName, defaultINPName, defaultRPTName;
  public RPTData rptData;
  private String srfDataStr, lfsDataStr;

  private int pageOfLfsDataStr=0;
  private int pageOfSrfDataStr=0;
  public DisplayDesignPageBean2(){
	super();
	setJspName("/pages/designmanagement/DisplayDesignPage.jsp");
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
		  defaultINPName = myDesignData[i].inpFileName;
		  defaultRPTName = myDesignData[i].rptFileName;
		  i = myDesignData.length;
	  }
	}
	rptData = parseRPTFile(myProperty, defaultRPTName);
	rptData.rptFileName=defaultRPTName;

	int num = 0;
	if (Constants.NUM_PER_PAGE>=rptData.srfData.length)
	  num = rptData.srfData.length;
	else
	  num = Constants.NUM_PER_PAGE;
	RPTSubRunoffData[] srfTmpData = new RPTSubRunoffData[num];
	System.arraycopy(rptData.srfData, 0, srfTmpData, 0, num);	
	srfDataStr = (new Gson()).toJson(srfTmpData);

	if (Constants.NUM_PER_PAGE>=rptData.lfsData.length)
	  num = rptData.lfsData.length;
	else
	  num = Constants.NUM_PER_PAGE;
	RPTLFSummaryData[] lfsTmpData = new RPTLFSummaryData[num];
	System.arraycopy(rptData.lfsData, 0, lfsTmpData, 0, num);	 
	lfsDataStr = (new Gson()).toJson(lfsTmpData);
	
	myRequest.setPageOfLfsDataStr(1);
	myRequest.setPageOfSrfDataStr(1);
	myRequest.rptData = rptData;
  }

  public String getSrfDataStr(){
	  return getSrfDataStr(1);
  }
  
  public String getSrfDataStrNext(){
	  if(pageOfSrfDataStr > rptData.srfData.length/Constants.NUM_PER_PAGE )
		  pageOfSrfDataStr =  rptData.srfData.length/Constants.NUM_PER_PAGE;
	  else
		  pageOfSrfDataStr++;
	  return getLfsDataStr(pageOfLfsDataStr);
  }
  public String getSrfDataStrPrevious(){
	  if(pageOfSrfDataStr <=0 )
		  pageOfSrfDataStr = 0;
	  else
		  pageOfSrfDataStr--;
	  return getLfsDataStr(pageOfSrfDataStr);
  }  
  public String getSrfDataStr(int pageNum){
	 if (pageNum<=0)
		 pageNum=1;
	 int numStart = (pageNum-1) * Constants.NUM_PER_PAGE;
	 RPTSubRunoffData[] tmpData;
	 if (numStart < rptData.srfData.length){
		 tmpData = new RPTSubRunoffData[pageNum * Constants.NUM_PER_PAGE - numStart];
		 System.arraycopy(rptData.srfData, numStart, tmpData, 0, pageNum * Constants.NUM_PER_PAGE - numStart);
	 } else {
		 numStart = rptData.srfData.length % Constants.NUM_PER_PAGE;
		 if (numStart==0)
			 numStart = Constants.NUM_PER_PAGE;
		 tmpData = new RPTSubRunoffData[numStart];
		 System.arraycopy(rptData.srfData, rptData.srfData.length-numStart, tmpData, 0, numStart);		 
	 }		 
	 return (new Gson()).toJson(tmpData);
  }
  
  public String getLFSPageStr(){
	  return StringHelper.mockPageStr(1, rptData.lfsData.length, Constants.NUM_PER_PAGE);
  }  
  public String getSRFPageStr(){
	  return StringHelper.mockPageStr(1, rptData.srfData.length, Constants.NUM_PER_PAGE);
  }
  
  public String getLfsDataStr(){
	  return getLfsDataStr(1);
  }
  public String getLfsDataStrNext(){
	  if(pageOfLfsDataStr > rptData.lfsData.length/Constants.NUM_PER_PAGE )
		  pageOfLfsDataStr =  rptData.lfsData.length/Constants.NUM_PER_PAGE;
	  else
		  pageOfLfsDataStr++;
	  return getLfsDataStr(pageOfLfsDataStr);
  }
  public String getLfsDataStrPrevious(){
	  if(pageOfLfsDataStr <=0 )
		  pageOfLfsDataStr = 0;
	  else
		  pageOfLfsDataStr--;
	  return getLfsDataStr(pageOfLfsDataStr);
  }
  public String getLfsDataStr(int pageNum){
	 if (pageNum<=0)
		 pageNum=1;
	 int numStart = (pageNum-1) * Constants.NUM_PER_PAGE;
	 RPTLFSummaryData[] tmpData;
	 if (numStart < rptData.lfsData.length){
		 tmpData = new RPTLFSummaryData[pageNum * Constants.NUM_PER_PAGE - numStart];
		 System.arraycopy(rptData.lfsData, numStart, tmpData, 0, pageNum * Constants.NUM_PER_PAGE - numStart);
	 } else {
		 numStart = rptData.lfsData.length % Constants.NUM_PER_PAGE;
		 if (numStart==0)
			 numStart = Constants.NUM_PER_PAGE;
		 tmpData = new RPTLFSummaryData[numStart];
		 System.arraycopy(rptData.lfsData, rptData.lfsData.length-numStart, tmpData, 0, numStart);		 
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
protected boolean isValid(HttpServletRequest request) {
	// TODO Auto-generated method stub
	return true;
}

	@Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	String tmpStr = request.getParameter("TRateARV");
	if (!StringHelper.isEmpty(tmpStr)){
	  BigDecimal tRateARV = new BigDecimal(tmpStr);
	  DesignManagementRequest myReq = (DesignManagementRequest) getMyRequest();
	  DesignManagementDBHelper.saveRPTData(myProperty, myReq.rptData);	  
	}
  }

  private static RPTData parseRPTFile(Properties myProperty, String fName){
	String fileName = myProperty.getProperty("RPT_PATH") + File.separator + fName;
	return FileHelper.parseRPTFile(fileName);
  }

}
