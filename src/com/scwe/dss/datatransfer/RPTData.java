package com.scwe.dss.datatransfer;

import java.io.Serializable;

public class RPTData implements Serializable{

  /**
	 * 
	 */
  private static final long serialVersionUID = 7094209247087738511L;
  public String rptFileName="";
  public Float tpDepth= Float.valueOf(0);
  public RPTSubRunoffData srfData[]=null;
  public RPTLFSummaryData lfsData[]=null;

  public RPTData(){}
  public RPTData(String fName, Float aDepth, RPTSubRunoffData[] subData, RPTLFSummaryData[] lfData){
	rptFileName = fName;
	tpDepth = aDepth;
	srfData = subData;
	lfsData = lfData;
  }
}
