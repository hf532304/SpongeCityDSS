package com.scwe.dss.request;

import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.datatransfer.RPTData;
import com.scwe.dss.datatransfer.RPTLFSummaryData;
import com.scwe.dss.datatransfer.RPTSubRunoffData;
import com.scwe.dss.datatransfer.RainModel;
import com.scwe.dss.datatransfer.RainModelData;
import com.scwe.dss.datatransfer.UserData;

public class DesignManagementRequest extends MyMainRequest{
  /**
	 * 
	 */
	private static final long serialVersionUID = 6854265387515916396L;

public static String RequestName = "DesignManagementRequest";
  
  private DesignData[] myDesignData = null;
  private ProjectData[] myProjectData = null;

  public String selectedProjectId = null;
  public DesignData selectedDesignData = null;
  private RainModel[] myModels = null;
  private RainModelData[] myRainData = null;
  private int pageOfLfsDataStr;
  private int pageOfSrfDataStr;
  public RPTData rptData;

  public RPTSubRunoffData[] getSrfData() {
	return rptData.srfData;
}

public RPTLFSummaryData[] getLfsData() {
	return rptData.lfsData;
}

public int getPageOfLfsDataStr() {
	return pageOfLfsDataStr;
  }

  public void setPageOfLfsDataStr(int pageOfLfsDataStr) {
	this.pageOfLfsDataStr = pageOfLfsDataStr;
  }

  public int getPageOfSrfDataStr() {
	return pageOfSrfDataStr;
  }

  public void setPageOfSrfDataStr(int pageOfSrfDataStr) {
	this.pageOfSrfDataStr = pageOfSrfDataStr;
  }

  public void setMyDesignData(DesignData[] myDesignData) {
	this.myDesignData = myDesignData;
  }

  public DesignData[] getMyDesignData() {
	return myDesignData;
  }
  

  public void setRainModels(RainModel[] aModels) {
	this.myModels = aModels;
  }
  public RainModel[] getRainModels() {
	return myModels;
  }  

  public void setMyDesignData(RainModelData[] aData) {
	this.myRainData = aData;
  }
  public RainModelData[] getRainData() {
	return myRainData;
  }
  
  public int clear(){
	selectedDesignData = null;
	myDesignData = null;
	myModels = null;
	myRainData = null;
	
	return 0;
  }
  
  public ProjectData[] getMyProjectData() {
	return myProjectData;
  }

  public void setMyProjectData(ProjectData[] myProjectData) {
	this.myProjectData = myProjectData;
  }

}
