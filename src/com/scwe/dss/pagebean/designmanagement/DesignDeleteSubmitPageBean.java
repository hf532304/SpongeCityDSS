package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.MyThankYouPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class DesignDeleteSubmitPageBean extends MyThankYouPageBean{
  
  private static final long serialVersionUID = -8653680296931619526L;

  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.DesignDeleteSubmitPageBean"; 

  public static String RequestName = "DesignManagementRequest";

  private String titleDisplay="项目管理";
  private String msgDisplay="";
  
  public DesignDeleteSubmitPageBean(){
	super();
	setJspName("/pages/common/MyThankYouPage.jsp");
  }

  public void doBeforeRun(HttpServletRequest request){
	
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	String pId="";
	String dId="";
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	if ( myRequest.getMyDesignData() == null){	  
	  // Impossible, should be set by DesignManagementPageBean
	  //myDesigns = DesignManagementDBHelper.getMyDesignData(myProperty);
	  //myRequest.setMyDesignData(myDesigns);
	}
	String selectedId = request.getParameter("DefaultDesign");
	if (! StringHelper.isEmpty(selectedId) && ! "0".equals(selectedId) ){
	  int retVal = DesignManagementDBHelper.deleteDesign(myProperty, selectedId);
	  if (retVal >= 1){
		msgDisplay = "<div align='center'>该方案信息已经从数据库中删除</div>";
		DesignData myDesigns[] = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
		myRequest.setMyDesignData(myDesigns);
	  } else {
		msgDisplay = "<div align='center'>删除数据库中的设计方案信息失败，请核查原因</div>";	  
	  }
	} else {
		msgDisplay = "<div align='center'>无设计方案可删除</div>";	  		
	}
  }
  
  private int deleteDesign(Properties myProperty){
	DesignData myDesigns[] = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
	((DesignManagementRequest ) getMyRequest()).setMyDesignData(myDesigns);
	return 0;
  }
	  

	
  @Override
  protected iWorkerRequest createMyRequest(iWorkerRequest oldRequest) {
	if (oldRequest == null)
	  // TODO Auto-generated method stub
	  return new DesignManagementRequest();
	else {
	  try {
		DesignManagementRequest tmpReq = (DesignManagementRequest) oldRequest;
		return tmpReq;
	  } catch (Exception ex){
		return new DesignManagementRequest();
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
	return "DesignManagementPage";
}

}
