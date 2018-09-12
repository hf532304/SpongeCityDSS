package com.scwe.dss.datatransfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class RPTSubRunoffData implements Serializable{

  /**
	 * 
	 */
  private static final long serialVersionUID = 7541163898456317048L;
  public String subcatchmentId;
  public Float tPcpttionmm;
  public Float tRunoffmm;
  public Float tRunoffCoeff;
  public Float aRateARV; 
  public Float tRateARV; 
  
  public RPTSubRunoffData(){}
  public RPTSubRunoffData(String sId, Float aTPmm, Float rMm, Float rCff, Float aRate){
	  subcatchmentId = sId;
	  tPcpttionmm =  (float)(Math.round(aTPmm*100))/100;
	  tRunoffmm = (float)(Math.round(rMm*100))/100;
	  tRunoffCoeff=(float)(Math.round(rCff*100))/100;	  
	  aRateARV = (float)(Math.round((1-tRunoffmm/tPcpttionmm)*100))/100;
	  tRateARV = (float)(Math.round(aRate*100))/100;
  }
  public RPTSubRunoffData(String sId, BigDecimal aTPmm, BigDecimal rMm, BigDecimal rCff, BigDecimal aRate){
	  subcatchmentId = sId;
	  tPcpttionmm = aTPmm.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
	  tRunoffmm = rMm.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
	  tRunoffCoeff=rCff.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();	  
	  aRateARV = (float)(Math.round((1-tRunoffmm/tPcpttionmm)*100))/100;
	  tRateARV = aRate.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
  }  
}
