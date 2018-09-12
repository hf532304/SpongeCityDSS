package com.scwe.dss.datatransfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProjectData implements Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = 5390212896543663206L;
/**
	 * 
	 */
  public String projectId;
  public String projectName;
  public String projectDesc;
  public String mapUrl;
  public BigDecimal tRateARV;
  
  public String creator;
  public String creationTime;

  public ProjectData(String pId, String pName, String pDesc, String mUrl, BigDecimal aRate, String aCreator, String cTime){
	projectId = pId;
	projectName = pName;
	projectDesc = pDesc;
	mapUrl = mUrl;
	tRateARV = aRate;
	creationTime = cTime;
	creator = aCreator;
  }
  
  public ProjectData(){
	  super();
  }

}
