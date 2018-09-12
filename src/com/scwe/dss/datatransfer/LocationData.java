package com.scwe.dss.datatransfer;

import java.sql.Timestamp;

public class LocationData {
  public LocationData(String aId, String aTime, double aLatitude, double aLongitude, String desc){
	id = aId;
	time = aTime;
	latitude = aLatitude;
	longitude = aLongitude;
	description = desc;
  }
  public LocationData(){
	  super();
  }
  public String id;
  public String time;
  public String description;
  public double latitude;
  public double longitude;
}
