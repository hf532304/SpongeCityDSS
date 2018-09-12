package com.scwe.dss.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.scwe.dss.datatransfer.SensorData;
import com.scwe.dss.dbhelper.IntelligentMonitoringDBHelper;
import com.scwe.dss.util.Constants;
import com.scwe.dss.util.GSONHelper;;

public class AndroidHelper {

  public static boolean isValidDevice(Properties myProp, String rIp){
	boolean retVal = false;
	
	if (myProp != null){
	  String tmpStr = myProp.getProperty("REMOTE_DEVICES");
	  if (tmpStr != null && tmpStr.indexOf(rIp)>=0){
		retVal = true;
	  }
	}
	return retVal;
  }
  
  public static boolean processAndroidData(StringBuffer inData){
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
  
  public static AndroidData parseParameters(HttpServletRequest request, Properties myProperties) {
	String sensorId = request.getParameter("SensorID");
	String actionId = request.getParameter("Action");
	String deviceId = request.getParameter("ID");
	String account = request.getParameter("ACT");
    String p1=null;
    String p2=null;
    String p3=null;	

	if (Constants.ANDROID_ACTION_QUERY.equals(actionId)){
      p1 = request.getParameter("P1");
      p2 = request.getParameter("P2");
      p3 = request.getParameter("P3");
	} else if (Constants.ANDROID_ACTION_LOGIN.equals(actionId)){
      p1 = request.getParameter("ACT");
      p2 = request.getParameter("PW");
	}
    
    return new AndroidData(deviceId, account, actionId, sensorId, p1, p2, p3);
  }
  
  public static boolean isValidSender(AndroidData aData, Properties myProperties){
	boolean retVal = false;
	// If DeviceID & Account pair match the values stored in myProperties, it is valid sender
	String act = myProperties.getProperty(aData.deviceId);
	if (act != null && act.equals(aData.account))
	  retVal = true;
	return retVal;
  }
  
  public static String doAction(AndroidData aData, Properties myProperties){
	String retVal = null;
	
	if ( "0".equals(aData.actionId) ){
	  retVal = loginUser(aData, myProperties);
	} else if ( isValidSender(aData, myProperties)){
	  // 1 means to getData from DB
		if ( "1".equals(aData.actionId) ){
		  //retVal = readFromJsonFile(aData, myProperties);
		  retVal = readFromDB(aData, myProperties);
		} else if ( "9".equals(aData.actionId) ){
		  //retVal = loadInitialData(aData, myProperties);
		  retVal = GSONHelper.parseGsonSensorInfo(IntelligentMonitoringDBHelper.loadInitialData(myProperties));
		}
	} else {
   	  retVal = Constants.INVALID_SENDER;
	}
		
	return retVal;
  }
  
  public static String loginUser(AndroidData aData, Properties myProperties){
	String msg = "-1";
  		
	if ("Admin".equals(aData.parameter1) && "abcd1234".equals(aData.parameter2) || "Ad".equals(aData.parameter1) && "a1".equals(aData.parameter2)){
		myProperties.setProperty(aData.deviceId, aData.account);

		msg = "0";	
	}
	return msg;
  }
  
  public static String readFromDB(AndroidData aData, Properties myProperties){
	String outStr=null;
	SensorData myData = IntelligentMonitoringDBHelper.getSensorData(myProperties, aData.sensorId );
	if (myData == null)
	  outStr = "";	
	else {
	  Gson gson = new Gson();
	  outStr = gson.toJson(myData);
	}	
	return outStr;
  }
  
  public static String readFromJsonFile(AndroidData aData, Properties myProperties){
	String msg = "";

	String fName = aData.sensorId + ".json";
	
	if ("54517".equals(aData.sensorId))
	  fName = "SN12304561.json";
	
    File localFile = null; 
  	InputStream in = null; 
	try {
 	  localFile = new File(myProperties.getProperty("LOCAL_DATA_FOLDER") + File.separator + fName); 
  	  in = new FileInputStream(localFile); 
      byte b[]=new byte[(int)localFile.length()];     //���������ļ���С������   
      in.read(b);    //��ȡ�ļ��е����ݵ�b[]���� 
      msg = (new String(b));      
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	  if (in != null)
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	return msg;
  }
  
  public static String loadInitialData(AndroidData aData, Properties myProperties){
	String msg = null;
		
	File localFile = null; 
	InputStream in = null; 
	try {
	  localFile = new File(myProperties.getProperty("LOCAL_DATA_FOLDER") + File.separator + myProperties.getProperty("INITIAL_DATA_FILE")); 
	  in = new FileInputStream(localFile); 
	  byte b[]=new byte[(int)localFile.length()];     //���������ļ���С������   
	  in.read(b);    //��ȡ�ļ��е����ݵ�b[]���� 
	  msg = (new String(b));      
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	  if (in != null)
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	return msg;
  }
  
  public static void sendResponse(HttpServletResponse response, String msg) throws IOException {
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");  
    PrintWriter write;
	write = response.getWriter();
    write.write(msg);  
    write.flush(); 
    write.close();
  }
	
}
