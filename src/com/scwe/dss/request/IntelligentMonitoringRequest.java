package com.scwe.dss.request;

import com.scwe.dss.datatransfer.SensorInfo;

public class IntelligentMonitoringRequest extends MyMainRequest{
  private static final long serialVersionUID = 1L;
  public static String RequestName = "IntelligentMonitoringRequest";
  
  private SensorInfo[] mySensorInfo = null;
  public SensorInfo selectedSensorInfo = null;
  public SensorInfo[] getMySensorInfo() {
	return mySensorInfo;
  }

  public void setMySensorInfo(SensorInfo[] aSensorInfo) {
	this.mySensorInfo = aSensorInfo;
  }

}
