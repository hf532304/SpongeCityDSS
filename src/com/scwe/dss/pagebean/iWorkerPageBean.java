package com.scwe.dss.pagebean;

import java.io.Serializable;
import com.scwe.dss.util.Constants;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.UserData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.designevaluation.DesignEvaluationPageBean;
import com.scwe.dss.pagebean.designmanagement.ResultManagementPageBean;
import com.scwe.dss.request.iWorkerRequest;


public abstract class iWorkerPageBean implements Serializable{

  private static final long serialVersionUID = 4553618101661547436L;
  public static String PageBeanName = "iWorkerPageBean"; 
  public String JSPName = "iWorkerJSP.jsp";
  public static iWorkerRequest myRequest;  
  private static String myURL = null;
  private static String myWebAppName = null;  
  private static String arcGISMapServer = null;
  private String errMsg = "";

  protected UserData me = null;
  
  public String getWebAppName(){
	return myWebAppName;
  }
  
  public String getAppURL(){
	return myURL;
  }
  
  public String getArcGISMapServer(){
	return arcGISMapServer;
  }
    
  public iWorkerPageBean(){
	super();
  }
  public iWorkerPageBean(String beanName, String jspName){
	super();
	setPageBeanName(beanName);
	setJspName(jspName);
  }
  
  protected static final void setPageBeanName(String beanName) {
	  PageBeanName = beanName;
  }

  protected void setJspName(String jspName) {
	  JSPName = jspName;
  }

  public void prepareData(Properties myProperty, HttpServletRequest request){
	if (StringHelper.isEmpty(myURL)){
	  myWebAppName = request.getContextPath();
	  myURL = request.getScheme() //当前链接使用的协议
		  +"://" + request.getServerName()//服务器地址 
		  + ":" + request.getServerPort() //端口号 
		  + myWebAppName;  
	  arcGISMapServer = myProperty.getProperty("ARCGIS_MAPSERVER");
	}
	HttpSession mySession = request.getSession();
	myRequest = (iWorkerRequest) mySession.getAttribute(Constants.IWORKER_REQUEST_NAME);
	myRequest = createMyRequest(myRequest);
	mySession.setAttribute(Constants.IWORKER_REQUEST_NAME, myRequest);
	me = getMyInfoFromDB(myProperty);
	myRequest.setMe(me);
	doBeforeRun(myProperty, request);  
  }
  
  public boolean doAction(Properties myProperty, HttpServletRequest request){
	boolean retVal = isValid(request);
	if (retVal)
	  doSubmit(myProperty, request);
	return retVal;
  }
  
  protected abstract boolean isValid(HttpServletRequest request);
  protected abstract void doSubmit(Properties myProperty, HttpServletRequest request);
  
  protected abstract void doBeforeRun(Properties myProperty, HttpServletRequest request);
  protected abstract iWorkerRequest createMyRequest(iWorkerRequest oldRequest);
  
  protected iWorkerRequest getMyRequest(){
	return myRequest;
  }
  
  public String getPageErrMsg() {
	return errMsg;
  }

  public void setPageErrMsg(String errMsg) {
	this.errMsg = errMsg;
  }
  
  protected DesignData getSelectedData(DesignData designData[], String pId, String dId){
	DesignData retVal = null;
	for (int i=0; i< designData.length; i++){
	  if (pId.equalsIgnoreCase(designData[i].projectId) && dId.equals(designData[i].designId)){
		  retVal = designData[i];
		  i = designData.length;
	  }
	}	
	return retVal;
  }
  private UserData getMyInfoFromDB(Properties myProperty){
	return DesignManagementDBHelper.getMyInfo(myProperty, "Admin");
  }
  
  public String getMyInfoStr(){
	StringBuffer retVal = new StringBuffer("");
	if (me != null){	  
	  if (!StringHelper.isEmpty(me.projectName))
		retVal.append("<hr/>").append(me.projectName).append("<br/>");
	  retVal.append("<hr/>").append(me.designName).append("<br/><hr/>").append(me.userStatus).append("<br/><hr/>");	  
	  if (!StringHelper.isEmpty(me.inpFileName))
		retVal.append(me.inpFileName).append("<br/>");
	  if (!StringHelper.isEmpty(me.rptFileName))
		retVal.append(me.rptFileName);
	}
	return retVal.toString();
  }
  
  public String getMyRainModel(){
	String retVal = "";
	if (me != null){
	  retVal = me.modelName;
	}
	return retVal;
  }
  public String getMyDesignName(){
	String retVal = "";
	if (me != null){
	  retVal = me.designName;
	}
	return retVal;
  }
  public String getMyDesignId(){
	String retVal = "";
	if (me != null){
	  retVal = me.designId;
	}
	return retVal;
  }
  public String getMyINPFileName(){
	String retVal = "";
	if (me != null){
	  retVal = me.inpFileName;
	}
	return retVal;
  }
  
  public String isValidToAccess(){
	return "";
  }
    
}
