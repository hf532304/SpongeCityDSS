package com.scwe.dss.datatransfer;

public class SensorInfo {
	public String id;
	public String name;
	public String desc;
	public Float lat;
	public Float lon;
	public Float height;
	
  public SensorInfo(String aId, String aName, String aDesc, Float aLon, Float aLat, Float aHei){
		id = aId;
		name = aName;
		desc = aDesc;
		lon = aLon;
		lat = aLat;
		height = aHei;
	  }
	
}
