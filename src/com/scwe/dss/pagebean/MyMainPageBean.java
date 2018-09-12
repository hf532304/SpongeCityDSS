package com.scwe.dss.pagebean;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.scwe.dss.request.MyMainRequest;
import com.scwe.dss.request.iWorkerRequest;

public class MyMainPageBean extends iWorkerPageBean{
  
  private static final long serialVersionUID = -375680551104010354L;
  
  public static String PageBeanName = "com.scwe.dss.pagebean.MyMainPageBean"; 
  public static String RequestName = "MyMainRequest";
  
  public MyMainPageBean(){
	super();
	setJspName("/pages/common/MyMainPage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub	
  }

  @Override
  protected iWorkerRequest createMyRequest(iWorkerRequest oldRequest) {
	if (oldRequest == null)
	  // TODO Auto-generated method stub
	  return new MyMainRequest();
	else {
	  try {
		MyMainRequest tmpReq = (MyMainRequest) oldRequest;
		return tmpReq;
	  } catch (Exception ex){
		return new MyMainRequest();
	  }
	}
		
  }

@Override
protected boolean isValid(HttpServletRequest request) {
	// TODO Auto-generated method stub
	return true;
}

@Override
protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub
	
}


}
