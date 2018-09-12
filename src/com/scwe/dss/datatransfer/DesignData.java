package com.scwe.dss.datatransfer;

import java.io.Serializable;

public class DesignData implements Serializable{
  /**
	 * 
	 */
  private static final long serialVersionUID = -1208938936988941997L;
  public String projectId;
  public String projectName;
  public String mapUrl;
  public String designId;
  public String designName;
  public String designStatus;
  public Boolean isDefaultDesign;
  public String rainName;
  public String inpFileName;
  public String rptFileName;
  public String creationTime;
  public String creator;

  public DesignData(String pId, String pName, String mUrl, String dId, String dName, String dStatus, String isDefault, String rName, String inpName, String rptName, String aCreator, String cTime){
	projectId = pId;
	projectName = pName;
	mapUrl = mUrl;
	designId = dId;
	designName = dName;
	designStatus = dStatus;
	if ("Y".equalsIgnoreCase(isDefault))
	  isDefaultDesign = true;
	else
	  isDefaultDesign = false;	
	rainName = rName;
	inpFileName = inpName;
	rptFileName = rptName;
	creationTime = cTime;
	creator = aCreator;
  }
  
  public DesignData(){
	  super();
  }
  public String isDefault(){
	if (isDefaultDesign.booleanValue())
	  return "checked";
	else
	  return "";
  }
}
