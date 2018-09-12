package com.scwe.dss.request;

import java.io.Serializable;

import com.scwe.dss.datatransfer.UserData;

public class iWorkerRequest implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = -3213305790062546257L;
public static String RequestName = "iWorkerRequest";
 protected String msg="";

 protected UserData me;
 public void setMyMsg(String aMsg){ msg = aMsg;}
 public String getMyMsg(){ return msg;}
 public void setMe(UserData aMe){ me = aMe;}
 public UserData getMe(){ return me;} 
}
