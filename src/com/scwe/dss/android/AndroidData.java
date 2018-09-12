package com.scwe.dss.android;

public class AndroidData {
  public AndroidData(String id, String act, String action, String sId, String p1, String p2, String p3){
	deviceId = id;
	account = act;
	sensorId = sId;
	actionId = action;
	parameter1 = p1;
	parameter2 = p2;
	parameter3 = p3;
  }
  public String deviceId;
  public String account;
  public String sensorId;
  public String actionId;
  public String parameter1;
  public String parameter2;
  public String parameter3;
}
