package com.scwe.dss.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jl.foundation.util.StringHelper;
import com.scwe.dss.android.AndroidHelper;
import com.scwe.dss.datatransfer.RPTLFSummaryData;
import com.scwe.dss.datatransfer.RPTSubRunoffData;
import com.scwe.dss.datatransfer.RainModelData;
import com.scwe.dss.datatransfer.SensorData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.dbhelper.IntelligentMonitoringDBHelper;
import com.scwe.dss.pagebean.designmanagement.DisplayDesignPageBean;
import com.scwe.dss.pagebean.designmanagement.RainManagementPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class AjaxHelper {
  public AjaxHelper(){	  
  }
  
  public void processRequest(HttpServletRequest request, HttpServletResponse response, Properties myProperty, String reqType) {
	String outStr = "Illegal Request";
	if (Constants.AJAX_SENSOR_DATA.equalsIgnoreCase(reqType)){
		String tId = request.getParameter("SensorId");
		SensorData myData = IntelligentMonitoringDBHelper.getSensorData(myProperty, tId);
		if (myData == null)
		  outStr = "";	
		else {
		  Gson gson = new Gson();
		  outStr = gson.toJson(myData);  
		}
	} else if (Constants.AJAX_RAIN_DATA.equalsIgnoreCase(reqType)){
		String mName = request.getParameter("ModelName");
		RainModelData myData[] = DesignManagementDBHelper.getMyRainModelData(myProperty, mName);
		if (myData == null)
		  outStr = "";	
		else {
		  StringBuffer tmpBuffer = new StringBuffer("");
		  RainModelData aData;
		  for (int i=0; i<myData.length; i++){
			aData = myData[i];
			tmpBuffer.append(aData.rainTime).append(" ").append(aData.rainValume.toPlainString()).append("\n");
		  }
		  outStr = tmpBuffer.toString();
		}
	} else if (Constants.AJAX_UPDATE_INP.equalsIgnoreCase(reqType)){
		String mName = request.getParameter("ModelName");
		String dId = request.getParameter("DesignID");		
		outStr = RainManagementPageBean.updateINPFile(myProperty, dId, mName, "Admin");
		//String inpFName = DesignManagementDBHelper.getDefaultINPName(myProperty, "Admin");
		RainModelData myData[] = DesignManagementDBHelper.getMyRainModelData(myProperty, mName);
		
		if (myData == null)
		  outStr = "";	
		else {
		  StringBuffer tmpBuffer = new StringBuffer("");
		  RainModelData aData;
		  for (int i=0; i<myData.length; i++){
			aData = myData[i];
			tmpBuffer.append(aData.rainTime).append(" ").append(aData.rainValume.toString()).append("\n");
		  }
		  outStr = tmpBuffer.toString();
		}
	} else if (Constants.AJAX_FILEDATA_RPT.equalsIgnoreCase(reqType)){
		String dType = request.getParameter("DataType");
		HttpSession mySession = request.getSession();
		DesignManagementRequest myRequest = (DesignManagementRequest) mySession.getAttribute(Constants.IWORKER_REQUEST_NAME);
		
		int curLfsPage = myRequest.getPageOfLfsDataStr();
		int curSrfPage = myRequest.getPageOfSrfDataStr();
		int totalPages = 0;
		RPTSubRunoffData[] srfData = myRequest.getSrfData();
		RPTLFSummaryData[] lfsData = myRequest.getLfsData();
	
		RPTSubRunoffData[] tmpSrfData = new RPTSubRunoffData[Constants.NUM_PER_PAGE];
		RPTLFSummaryData[] tmpLfsData = new RPTLFSummaryData[Constants.NUM_PER_PAGE];
		
		if (Constants.SRUNOFF_PREV_RPT.equals(dType)){
			if (curSrfPage<=1)
				curSrfPage = 1;
			else
				curSrfPage--;
			System.arraycopy(srfData, Constants.NUM_PER_PAGE*(curSrfPage-1), tmpSrfData, 0, Constants.NUM_PER_PAGE);	 
			outStr = StringHelper.mockPageStr(curSrfPage, srfData.length, Constants.NUM_PER_PAGE) + (new Gson()).toJson(tmpSrfData);
		} else if (Constants.SRUNOFF_NEXT_RPT.equals(dType)){
			if (curSrfPage>=srfData.length/Constants.NUM_PER_PAGE){
				if (0 == srfData.length % Constants.NUM_PER_PAGE)
					curSrfPage = srfData.length/Constants.NUM_PER_PAGE;
				else
					curSrfPage = srfData.length/Constants.NUM_PER_PAGE + 1;
			} else {
				curSrfPage++;
			}
			System.arraycopy(srfData, Constants.NUM_PER_PAGE*(curSrfPage-1), tmpSrfData, 0, Constants.NUM_PER_PAGE);	 
			outStr = StringHelper.mockPageStr(curSrfPage, srfData.length, Constants.NUM_PER_PAGE) + (new Gson()).toJson(tmpSrfData);			
		} else if (Constants.LINKFLOW_PREV_RPT.equals(dType)){
			if (curLfsPage<=1)
				curLfsPage = 1;
			else
				curLfsPage--;
			System.arraycopy(lfsData, Constants.NUM_PER_PAGE*(curLfsPage-1), tmpLfsData, 0, Constants.NUM_PER_PAGE);	 
			outStr = StringHelper.mockPageStr(curLfsPage, lfsData.length, Constants.NUM_PER_PAGE) + (new Gson()).toJson(tmpLfsData);
		} else if (Constants.LINKFLOW_NEXT_RPT.equals(dType)){
			if (curLfsPage>=lfsData.length/Constants.NUM_PER_PAGE){
				if (0 == lfsData.length % Constants.NUM_PER_PAGE){
					curLfsPage = lfsData.length/Constants.NUM_PER_PAGE;
				} else {
					curLfsPage = lfsData.length/Constants.NUM_PER_PAGE + 1;
				}
			} else {
				curLfsPage++;
			}
			System.arraycopy(lfsData, Constants.NUM_PER_PAGE*(curLfsPage-1), tmpLfsData, 0, Constants.NUM_PER_PAGE);
			outStr = StringHelper.mockPageStr(curLfsPage, lfsData.length, Constants.NUM_PER_PAGE) + (new Gson()).toJson(tmpLfsData);
		}
		
		myRequest.setPageOfLfsDataStr(curLfsPage);
		myRequest.setPageOfSrfDataStr(curSrfPage);			
	}	
	
	try {
		AndroidHelper.sendResponse(response, outStr);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	 
  }  
  
}
