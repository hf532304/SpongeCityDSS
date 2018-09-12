package com.scwe.dss.datatransfer;

public class SensorData {
  public SensorData(String aTime, String tType, String aVersion, String tId, String reqType, String p1, String p2, String p3, String p4){
	time = aTime;
	terminalType = tType;
	version = aVersion;
	terminalId = tId;
	requestType = reqType;
	parameter1 = p1;
	parameter2 = p2;
	parameter3 = p3;
	parameter4 = p4;
  }
  public SensorData(){
	  super();
  }
  public String time;
  public String terminalType;
  public String version;
  public String terminalId;
  public String requestType;

  public String parameter1;
  public String parameter2;
  public String parameter3;
  public String parameter4;
}
