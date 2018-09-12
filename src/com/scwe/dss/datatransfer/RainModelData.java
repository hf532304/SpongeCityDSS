package com.scwe.dss.datatransfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class RainModelData implements Serializable{

  /**
	 * 
	 */
  private static final long serialVersionUID = -2003531820230800263L;
	public String modelName;
	public String modelDesc;
	public String rainTime;
	public BigDecimal rainValume;

  public RainModelData(String aName, String aDesc, String aTime, BigDecimal aVolume){
	modelName = aName;
	modelDesc = aDesc;
	rainTime = aTime;
	rainValume = aVolume;
  }
  
  public RainModelData(){
	  super();
  }

}
