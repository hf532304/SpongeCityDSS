package com.scwe.dss.pagebean.IntelligentMonitoring;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.scwe.dss.datatransfer.SensorInfo;
import com.scwe.dss.dbhelper.IntelligentMonitoringDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.IntelligentMonitoringRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.GSONHelper;

public class IMMainPageBean extends iWorkerPageBean{
  
/**
	 * 
	 */
	private static final long serialVersionUID = 6229213441328634133L;
public static String PageBeanName = "com.scwe.dss.pagebean.IntelligentMonitoring.IMMainPageBean"; 
  public static String RequestName = "IntelligentMonitoringRequest";
  
  private SensorInfo[] mySensorInfo = null;
  
  public IMMainPageBean(){
	super();
	setJspName("/pages/intelligentmonitoring/IMMainPage.jsp");
  }
  public String getJsonSensorInfo(){	  
	return GSONHelper.parseGsonSensorInfo(mySensorInfo);
  }
  
  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {

	IntelligentMonitoringRequest myRequest = null;
	myRequest = (IntelligentMonitoringRequest) getMyRequest();
	if ( myRequest.getMySensorInfo() == null){	  
	  mySensorInfo = IntelligentMonitoringDBHelper.loadInitialData(myProperty);
	  myRequest.setMySensorInfo(mySensorInfo);
	} else {
	  mySensorInfo = myRequest.getMySensorInfo();
	}
		
  }

  @Override
  protected iWorkerRequest createMyRequest(iWorkerRequest oldRequest) {
	if (oldRequest == null)
	  // TODO Auto-generated method stub
	  return new IntelligentMonitoringRequest();
	else {
	  try {
		IntelligentMonitoringRequest tmpReq = (IntelligentMonitoringRequest) oldRequest;
		return tmpReq;
	  } catch (Exception ex){
		return new IntelligentMonitoringRequest();
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
