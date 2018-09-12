package com.scwe.dss.request;

public class MyMainRequest extends iWorkerRequest{
  /**
	 * 
	 */
	private static final long serialVersionUID = -1162318418682660749L;

public static String RequestName = "MyMainRequest";
  
  private String initGsonData = null;

  public String getInitGsonData() {
	return initGsonData;
  }

  public void setInitGsonData(String initGsonData) {
	this.initGsonData = initGsonData;
  }
}
