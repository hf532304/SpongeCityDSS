package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.MyThankYouPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class UploadFileSubmitPageBean extends MyThankYouPageBean{

  /**
	 * 
	 */
	private static final long serialVersionUID = 2020320995745804466L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.UploadFileSubmitPageBean"; 

  public static String RequestName = "DesignManagementRequest";

  private String titleDisplay="上传INP文件";
  private String msgDisplay="";
  
  public UploadFileSubmitPageBean(){
	super();
	setJspName("/pages/common/MyThankYouPage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	msgDisplay = myRequest.getMyMsg();
	if (StringHelper.isEmpty(msgDisplay)){
		msgDisplay = "<div align='center'>INP文件已经上传完毕</div>";
	} else {
		msgDisplay = "<div align='center'>" + myRequest.getMyMsg() + "</div>";
		myRequest.setMyMsg("");
	}
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
