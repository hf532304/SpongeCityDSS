package com.scwe.dss.datatransfer;

import java.io.Serializable;

public class RainModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9015957229067499301L;
	public String modelName;
	public String modelDesc;

  public RainModel(String aName, String aDesc){
	modelName = aName;
	modelDesc = aDesc;
  }
  
  public RainModel(){
	  super();
  }

}
