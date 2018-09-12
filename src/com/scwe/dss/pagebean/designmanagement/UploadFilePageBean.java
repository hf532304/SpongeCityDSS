package com.scwe.dss.pagebean.designmanagement;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;
import com.scwe.dss.util.Constants;
import com.scwe.dss.util.FileHelper;

public class UploadFilePageBean extends iWorkerPageBean{

  /**
	 * 
	 */
	private static final long serialVersionUID = -215846934858355933L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.UploadFilePageBean"; 

  public static String RequestName = "DesignManagementRequest";
  public DesignData selectedDesignData = null;

  public UploadFilePageBean(){
	super();
	setJspName("/pages/designmanagement/UploadFilePage.jsp");
  }

  @Override
  protected void doBeforeRun(Properties myProperty, HttpServletRequest request) {	
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();
		
	if ( myRequest.selectedDesignData == null){	  
	  // Impossible
	} else {
	  selectedDesignData = myRequest.selectedDesignData;	
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
  protected boolean isValid(HttpServletRequest request) {
	boolean retVal = true;
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	DesignManagementRequest myRequest = (DesignManagementRequest) getMyRequest();	  
	String savePath = myProperty.getProperty("INP_PATH"); // "UPLOAD_PATH"; //
	String newFileName = myRequest.selectedDesignData.designId;
	String retVal = FileHelper.uploadINPFile(request, savePath, newFileName);
	if (! StringHelper.isEmpty(retVal)){
	  newFileName = retVal.substring(retVal.lastIndexOf(File.separator)+1);
	  int num = DesignManagementDBHelper.UpdateINPUpload(myProperty, myRequest.selectedDesignData.designId, newFileName, Constants.DESIGN_STATUS_INP);
	  if (num == 1){
		  DesignData myDesigns[] = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
		  myRequest.setMyDesignData(myDesigns);
		  myRequest.setMyMsg("");
		} else {
		  myRequest.setMyMsg("更新数据库中的INP文件名失败，请核查原因");
	  }	  
	}	
  }

}
