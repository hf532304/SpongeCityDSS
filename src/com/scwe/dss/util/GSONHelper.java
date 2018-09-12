package com.scwe.dss.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwe.dss.datatransfer.SensorInfo;

public class GSONHelper {
	
  public static List<SensorInfo> parseGsonSensorInfo(String jsonData){
  	List<SensorInfo> dataList = null;
  	if (jsonData != null){
	  	Gson gson = new Gson();
	  	dataList = gson.fromJson(jsonData, new TypeToken<List<SensorInfo>>() {}.getType());
  	}
  	return dataList;
  }

  public static String parseGsonSensorInfo(SensorInfo[] jsonData){
	String retVal = null;
  	if (jsonData != null){
	  	Gson gson = new Gson();
	  	retVal = gson.toJson(jsonData);
  	}
  	return retVal;
  }
 
 
}
