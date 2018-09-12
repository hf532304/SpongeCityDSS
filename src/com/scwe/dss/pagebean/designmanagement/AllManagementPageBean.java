package com.scwe.dss.pagebean.designmanagement;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.dbhelper.DesignManagementDBHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.request.DesignManagementRequest;
import com.scwe.dss.request.iWorkerRequest;

public class AllManagementPageBean extends iWorkerPageBean{
  
  /**
	 * 
	 */
	private static final long serialVersionUID = -4714259405375192485L;

public static String PageBeanName = "com.scwe.dss.pagebean.designmanagement.AllManagementPageBean"; 

  public static String RequestName = "DesignManagementRequest";
  
  private DesignData[] myDesigns = null;
  
  public AllManagementPageBean(){
	super();
	setJspName("/pages/designmanagement/AllManagementPage.jsp");
  }

  public void doBeforeRun(Properties myProperty, HttpServletRequest request){
	String dId="";
	String pId="";
	DesignManagementRequest myRequest = null;
	myRequest = (DesignManagementRequest) getMyRequest();
	if ( myRequest.getMyDesignData() == null){	  
	  myDesigns = DesignManagementDBHelper.getMyDesignData(myProperty, "Admin");
	  myRequest.setMyDesignData(myDesigns);
	} else {
  	  myDesigns = myRequest.getMyDesignData();
	}
	String nextAction = request.getParameter("NextAction");
	String selectedId = request.getParameter("DefaultDesign");
	if (!StringHelper.isEmpty(selectedId)){
		  String sID[] = selectedId.split(";");
		  if (sID != null){
		    pId = sID[0];
		    if (sID.length>1){
		      dId = sID[1];
		    }
		  }
		}
	if ("Default".equalsIgnoreCase(nextAction) && ! StringHelper.isEmpty(dId) && !"0".equals(dId)){  	
	  DesignManagementDBHelper.setDefaultDesign(myProperty, dId, "Admin");
	  setDefultDesign(dId);  
	}	
	if ( myRequest.getMyProjectData() == null){	  
	  myRequest.setMyProjectData(DesignManagementDBHelper.getMyProjectData(myProperty, "Admin"));
	}	
  }

  private void setDefultDesign(String designId){
	DesignData myDesigns[] = ((DesignManagementRequest ) getMyRequest()).getMyDesignData();	  
	for (int i=0; i<myDesigns.length; i++){
	  if (designId.equals(myDesigns[i].designId))
		myDesigns[i].isDefaultDesign = Boolean.valueOf(true);
	  else 
		myDesigns[i].isDefaultDesign = Boolean.valueOf(false);
	}
	((DesignManagementRequest ) getMyRequest()).setMyDesignData(myDesigns);	
  }

  public DesignData[] getMyDesigns(){
	return myDesigns; 
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
	boolean retVal= true;
	String sId = request.getParameter("DefaultDesign");
	String pId="";
	String dId="";
	if (!StringHelper.isEmpty(sId)){
	  String sID[] = sId.split(";");
	  if (sID != null){
		pId = sID[0];
		if (sID.length>1){
		  dId = sID[1];
		}
	  }
	}
	String nextPageName = request.getParameter("Name") + "Bean";
	StringBuffer errBuf = new StringBuffer("");
    if (DesignEditPageBean.PageBeanName.endsWith(nextPageName) && (StringHelper.isEmpty(dId) || "0".equals(dId)) ){
		errBuf.append("无设计方案可编辑;");
		retVal = false;
	} else if (DesignDeleteSubmitPageBean.PageBeanName.endsWith(nextPageName) && (StringHelper.isEmpty(dId) || "0".equals(dId)) ){
		errBuf.append("无设计方案可删除;");
		retVal = false;
	} else if (ProjectDeleteSubmitPageBean.PageBeanName.endsWith(nextPageName) && StringHelper.isEmpty(pId)){
		errBuf.append("无项目可删除;");
		retVal = false;
	} else if (AllManagementPageBean.PageBeanName.endsWith(nextPageName) && (StringHelper.isEmpty(dId) || "0".equals(dId)) ){
		String nextAction = request.getParameter("NextAction");
		if ("Default".equalsIgnoreCase(nextAction)){  	
			errBuf.append("无方案可设置为默认;");
			retVal = false;
		}			
	}
	setPageErrMsg(errBuf.toString());
	
	return retVal;
  }

  @Override
  protected void doSubmit(Properties myProperty, HttpServletRequest request) {
	// TODO Auto-generated method stub	
  }

}
