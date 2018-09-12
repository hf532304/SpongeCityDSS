package com.scwe.dss.datatransfer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserData implements Serializable{

  private static final long serialVersionUID = -5366317670844040537L;
  public String userId;
  public String userName;
  public String userStatus;
  public String machineId;
  public String loginTime;
  public String projectId;
  public String projectName;
  public String designName;
  public String designId;
  public String modelName;
  public String inpFileName;
  public String rptFileName;

  public UserData(String uId, String pId, String pName, String dId, String dName, String dStatus, String mName, String inpName, String rptName){
	  userId = uId;
	  projectId = pId;
	  projectName = pName;
	  designId = dId;
	  designName = dName;
	  userStatus = dStatus;
	  modelName = mName;
	  inpFileName = inpName;
	  rptFileName = rptName;
	  loginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(System.currentTimeMillis())).toString();
  }
}
