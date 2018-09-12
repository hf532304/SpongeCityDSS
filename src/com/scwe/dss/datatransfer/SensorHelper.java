package com.scwe.dss.datatransfer;

import java.io.File;

import com.scwe.dss.dbhelper.IntelligentMonitoringDBHelper;
import com.scwe.dss.util.Constants;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jl.foundation.util.StringHelper;

public class SensorHelper {

  public static boolean isValidRemoteSensers(Properties myProp, String rIp){
	boolean retVal = false;
	
	if (myProp != null){
	  String tmpStr = myProp.getProperty("REMOTE_SENSERS");
	  if (tmpStr != null && tmpStr.indexOf(rIp)>=0){
		retVal = true;
	  }
	}
	return retVal;
  }
  
  public static boolean processSensorData(StringBuffer inData){
	boolean retVal = false;
	if (inData != null || inData.length()>0){
/*		
		  String tmpStr = myProp.getProperty("REMOTE_SENSERS");
		  String tmpDelimitor = myProp.getProperty("REMOTE_ADDRESS_DELIMITOR");
		  if ( StringHelper.isEmpty(tmpDelimitor))
			tmpDelimitor = ";";
		  if (tmpStr != null && rIp.indexOf(tmpDelimitor)>=0){
			retVal = true;
		  }
		*/
	}
	return retVal;
  }
  
  public static void saveData(HttpServletRequest request, Properties myProperties) {
	String time = request.getParameter("Time");
	String terminalType = request.getParameter("TerminalType");
	String version = request.getParameter("Version");
	String tId = request.getParameter("TerminalID");
	String requestType = request.getParameter("Type");    
	String p1 = request.getParameter("P1");
    String p2 = request.getParameter("P2");
    String p3 = request.getParameter("P3");
    String p4 = request.getParameter("P4");
    
    SensorData sData = new SensorData(time, terminalType, version, tId, requestType, p1, p2, p3, p4);
    List<SensorData> dataList = new ArrayList<SensorData>();
    dataList.add(sData);
    
    writeToFile(myProperties, dataList);
    IntelligentMonitoringDBHelper.saveSensorData(myProperties, sData);
  }
  
  public static boolean writeToFile(Properties myProperties, List<SensorData> dataList){
	boolean retVal = false;
	Gson gson = new Gson();
	String outputStr = gson.toJson(dataList);
	String fName = dataList.get(0).terminalId + ".json";
	File localFile = new File(myProperties.getProperty("LOCAL_DATA_FOLDER") + File.separator + fName); 
	try {
	  OutputStream is = new FileOutputStream(localFile);
	  is.write(outputStr.getBytes());
	  is.flush();
	  is.close();
	  retVal = true;
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	return retVal;
  }
  
  public static void sendResponse(HttpServletResponse response) throws IOException {
	//���������֤�ϸ����������Ĳ���װ�䷵�صı���
	StringBuffer resultBuffer = mockResponse();  
	// ���÷��ͱ��ĵĸ�ʽ  
  	response.setContentType("text/plain");  
  	response.setCharacterEncoding("UTF-8");  
   	PrintWriter out = response.getWriter();  
   	out.println(resultBuffer.toString());  
   	out.flush();  
   	out.close();
  }
	
  public static StringBuffer mockResponse() {
	String time = StringHelper.getSystemTime("yyyy-MM-dd HH:mm:ss");
	StringBuffer rspBuffer = new StringBuffer("[");	
	rspBuffer.append(time).append(",");  
	rspBuffer.append(Constants.SERVER_RESPONSE_TERMINAL_UPLOAD).append("]");
	return rspBuffer;
	
  }   
}
