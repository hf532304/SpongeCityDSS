package com.scwe.dss.pagebean.designmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.RainModel;
import com.scwe.dss.datatransfer.RainModelData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;
import com.scwe.dss.util.FileHelper;

public class RainManagementPageBean extends iWorkerPageBean{
  
  /**
	 * 
	 */
	private static final long serialVersionUID = -2217235500950028715L;

  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.RainManagementPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  
  private RainModelData myRainData[] = null;
  
  private RainModel[] myModels=null;
  
  public int selectedModel=0;
  
  private Properties myProperty;
  
  public RainManagementPageBean(){
	super();
	setJspName("/pages/designmanagement/RainManagementPage.jsp");
  }

  public void doBeforeRun(Properties aProperty, HttpServletRequest request){
	String dId="";
	String pId="";
	DesignManagementRequest myRequest = null;
	myRequest = (DesignManagementRequest) getMyRequest();
	myProperty = aProperty;
	if ( myRequest.getRainModels() == null || myRequest.getRainModels().length <=0 ){	  
	  myModels = DesignManagementDBHelper.getMyRainModels(myProperty);
	  myRequest.setRainModels(myModels);
	} else {
	  myModels = myRequest.getRainModels();
	}
  }

  public String getRainData(String mName){
	String outStr = "";
	if (myModels != null && myModels.length>0) {
	  myRainData = DesignManagementDBHelper.getMyRainModelData(myProperty, mName);
		if (myRainData != null && myRainData.length>0){
		  StringBuffer tmpBuffer = new StringBuffer("");
		  RainModelData aData;
		  for (int i=0; i<myRainData.length; i++){
			aData = myRainData[i];
			tmpBuffer.append(aData.rainTime).append(" ").append(aData.rainValume.toPlainString()).append("\n");
		  }
		  outStr = tmpBuffer.toString();
		}	  
	}
	return outStr;
  }
  
  public String getDefaultRainData(){
	return getRainData(myModels[0].modelName);
  }
  
  public RainModel[] getMyRainModels(){
	return myModels; 
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
  
  public static String updateINPFile(Properties myProperty, String dId, String modelName, String creator){
	String retVal = "";
	String folderName = myProperty.getProperty("INP_PATH") + File.separator;
	String inpFName = DesignManagementDBHelper.getDefaultINPName(myProperty, creator);
	RainModelData myData[] = DesignManagementDBHelper.getMyRainModelData(myProperty, modelName);
	
	String rainGagesData = mockRainGages(modelName);
	String timeSeriesData = mockTimeSeries(modelName, myData);
	retVal = FileHelper.updateINPFile(modelName, rainGagesData, timeSeriesData, inpFName, folderName);	
	if (!StringHelper.isEmpty(retVal)){
	  DesignManagementDBHelper.updateRainModel(myProperty, dId, modelName, Constants.DESIGN_STATUS_RAIN);
	}
	
	return retVal;
  }

  public static String mockRainGages(String modelName){
	StringBuffer retVal = new StringBuffer(Constants.INP_RAINGAGES_T1).append("\r\n"); 
	retVal.append(Constants.INP_RAINGAGES_T2).append("\r\n"); 
	retVal.append(Constants.INP_RAINGAGES_T3).append("\r\n"); 
	retVal.append(Constants.INP_RAINGAGES_T4).append("\r\n"); 
	
	retVal.append(StringHelper.getFixedLengthStr(modelName, 21)).append( StringHelper.getFixedLengthStr(Constants.INP_RAIN_TYPE, 10) );
	if (modelName.contains(Constants.INP_RAINGAGES_5MINS))
		retVal.append(StringHelper.getFixedLengthStr(Constants.INP_RAINGAGES_5MINS_INTRVL, 7));
	else if (modelName.contains(Constants.INP_RAINGAGES_10MINS))
		retVal.append(StringHelper.getFixedLengthStr(Constants.INP_RAINGAGES_10MINS_INTRVL, 7));
	retVal.append(StringHelper.getFixedLengthStr(Constants.INP_RAINGAGES_SNOWCATCH, 7));
	retVal.append(StringHelper.getFixedLengthStr(Constants.INP_RAINGAGES_TIMESERIES, 11));
	retVal.append(modelName);	
	retVal.append("\r\n"); 
	
	return retVal.toString();	  
  }

  public static String mockTimeSeries(String modelName, RainModelData myData[]){
	StringBuffer retVal = new StringBuffer(Constants.INP_TIMESERIES_T1).append("\r\n"); 
	retVal.append(Constants.INP_TIMESERIES_T2).append("\r\n"); 
	retVal.append(Constants.INP_TIMESERIES_T3).append("\r\n"); 
	
	for (int i=0; i<myData.length; i++){
		retVal.append(StringHelper.getFixedLengthStr(modelName, 28)).append( StringHelper.getFixedLengthStr(myData[i].rainTime, 11) );
		retVal.append(myData[i].rainValume.toPlainString());
		retVal.append("\r\n"); 
	}
	
	return retVal.toString();	  
  }
  
  public String getCancelPage(){
	  return "AllManagementPage";
  }
  
}
