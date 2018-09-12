package com.scwe.dss.pagebean;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.scwe.dss.request.iWorkerRequest;

public class MessagePageBean extends MyThankYouPageBean{
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1070577047511317753L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.MessagePageBean"; 

  public static String RequestName = "iWorkerRequest";

  private String titleDisplay="项目管理";
  private String msgDisplay="异常情况导致系统无法顺利运行,请查看提示信息并点击<返回键>重新开始";
  
  public MessagePageBean(){
	super();
	setJspName("/pages/common/MyThankYouPage.jsp");
  }

  public void doBeforeRun(HttpServletRequest request){
	
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
  }
  
  @Override
  protected iWorkerRequest createMyRequest(iWorkerRequest oldRequest) {
	if (oldRequest == null)
	  // TODO Auto-generated method stub
	  return new iWorkerRequest();
	else {
	  try {
		  iWorkerRequest tmpReq = (iWorkerRequest) oldRequest;
		return tmpReq;
	  } catch (Exception ex){
		return new iWorkerRequest();
	  }
	}	
  }

  @Override
  public String getTitleDisplay() {
	// TODO Auto-generated method stub
	return titleDisplay;
  }

  @Override
  public String getMsgDisplay() {
	// TODO Auto-generated method stub
	return msgDisplay;
  }
  
  public void setMegDisplay(String msg){
	  msgDisplay = msg;
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

@Override
public String getNextPage() {
	// TODO Auto-generated method stub
	return "MyMainPage";
}

}
