package com.scwe.dss.request;

import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.RPTLFSummaryData;
import com.scwe.dss.datatransfer.RPTSubRunoffData;
import com.scwe.dss.datatransfer.RainModelData;

public class DesignEvaluationRequest extends MyMainRequest{
  /**
	 * 
	 */
	private static final long serialVersionUID = 6854265387515916396L;

public static String RequestName = "DesignManagementRequest";
  
  private DesignData[] myDesignData = null;
  public DesignData selectedDesignData = null;
  private String[] myModels = null;
  private RainModelData[] myRainData = null;
  
  private int pageOfLfsDataStr;
  private int pageOfSrfDataStr;

  private RPTSubRunoffData[] srfData;
  private RPTLFSummaryData[] lfsData;

  public RPTSubRunoffData[] getSrfData() {
	return srfData;
}

public void setSrfData(RPTSubRunoffData[] srfData) {
	this.srfData = srfData;
}

public RPTLFSummaryData[] getLfsData() {
	return lfsData;
}

public void setLfsData(RPTLFSummaryData[] lfsData) {
	this.lfsData = lfsData;
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
  

  public void setMyModels(String[] aModels) {
	this.myModels = aModels;
  }
  public String[] getRainModels() {
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

}
