package com.scwe.dss.pagebean.designmanagement;

import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.MyThankYouPageBean;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;

public class ProjectNewSubmitPageBean extends MyThankYouPageBean{
  
  private static final long serialVersionUID = -8653680296931619526L;

  public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.ProjectNewSubmitPageBean"; 

  public static String RequestName = "DesignManagementRequest";

  private String titleDisplay="项目管理";
  private String msgDisplay="";
  
  public ProjectNewSubmitPageBean(){
	super();
	setJspName("/pages/common/MyThankYouPage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
	msgDisplay = myRequest.getMyMsg();
	if (StringHelper.isEmpty(msgDisplay)){
		msgDisplay = "<div align='center'>新增项目信息已经成功加入到数据库中！</div>";
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
	return "ProjectManagementPage";
}

}
