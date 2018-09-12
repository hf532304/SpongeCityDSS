package com.scwe.dss.datatransfer;

import java.io.Serializable;

public class RPTLFSummaryData implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 2457209602705499344L;
	
  public String linkId;
  public Float mfDepth;

  public RPTLFSummaryData(){	
  }
  public RPTLFSummaryData(String sId, Float aDepth){
	  linkId = sId;
	  mfDepth = aDepth;
  }
}
