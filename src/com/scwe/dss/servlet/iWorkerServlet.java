package com.scwe.dss.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jl.foundation.util.PropertyFileParser;
import com.jl.foundation.util.StringHelper;
import com.scwe.dss.pagebean.iWorkerPageBean;
import com.scwe.dss.util.Constants;

/**
 * Servlet implementation class iWorkerServlet
 */

	 
public class iWorkerServlet extends HttpServlet {

  private static final long serialVersionUID = 2340400866198494331L;
  public static Properties myProperties = null;		  
  
  public Properties loadProperties() {  	  
    Properties properties = new Properties();  
	InputStream stream = this.getClass().getClassLoader().getResourceAsStream(Constants.APP_CONFIG_FILE);  
	String rootFolder = this.getClass().getClassLoader().getResource("/data").getPath();
	try {  
	  properties.load(stream);  
	  properties.setProperty("UPLOAD_PATH", rootFolder + properties.getProperty("UPLOAD_FOLDER"));
	  properties.setProperty("INPUT_PATH", rootFolder + properties.getProperty("INPUT_FOLDER"));
	  properties.setProperty("OUTPUT_PATH", rootFolder + properties.getProperty("OUTPUT_FOLDER"));
	  properties.setProperty("INP_PATH", rootFolder + properties.getProperty("INP_FOLDER"));
	  properties.setProperty("RPT_PATH", rootFolder + properties.getProperty("RPT_FOLDER"));
	  properties.setProperty("EXE_PATH", rootFolder + properties.getProperty("EXE_FOLDER"));	  
	} catch (IOException e) {  
	  // TODO Auto-generated catch block  
	  System.out.println("Failed to load properties - " + Constants.APP_CONFIG_FILE);  
	}  
	return properties;  
  }  
  
  public iWorkerServlet(){
	super();
	if (myProperties == null){
	  myProperties = loadProperties();
	}
  }
  
  public static String getProperty(String key){
	  if (myProperties == null)
		return null;
	  else
		return myProperties.getProperty(key); 
  }
  
  protected void navigateTo(HttpServletRequest request, HttpServletResponse response, String currentPageBean, String nextPageBean) throws ServletException, IOException{
	boolean isValidForNext = false;
	String isValidToAccess = "";
	try {
		iWorkerPageBean curPageBean=null;
		iWorkerPageBean nPageBean=null;
		iWorkerPageBean msgPageBean=null;
		HttpSession mySession = request.getSession();
		// Verify if valid user
		if (mySession == null || mySession.getAttribute("iWorkerId") == null) {
		  try {
			curPageBean = (iWorkerPageBean) Class.forName(currentPageBean).newInstance();
			isValidForNext = curPageBean.doAction(myProperties, request);
		  } catch (Exception ex){
			// Should be the login page
		    curPageBean = (iWorkerPageBean) Class.forName("com.scwe.dss.pagebean.MessagePageBean").newInstance();
		    curPageBean.setPageErrMsg(ex.getMessage());
		  } finally {
			if (isValidForNext){
				nPageBean = (iWorkerPageBean) Class.forName(nextPageBean).newInstance();
				isValidToAccess = nPageBean.isValidToAccess();
				if (StringHelper.isEmpty(isValidToAccess)){
				  startPageBean(request, response, mySession, nPageBean);
				} else {
				  // Should be the login page or MainPage
				  nPageBean = (iWorkerPageBean) Class.forName("com.scwe.dss.pagebean.MessagePageBean").newInstance();					
				  nPageBean.setPageErrMsg(isValidToAccess);
				  startPageBean(request, response, mySession, nPageBean);
				}
			} else {
				startPageBean(request, response, mySession, curPageBean);			  
			}
		  }
		}
	  } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
  }

  private void startPageBean(HttpServletRequest request, HttpServletResponse response,	HttpSession mySession, iWorkerPageBean nextPageBean)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException, ServletException, IOException {
	  nextPageBean.prepareData(myProperties, request);
  
	mySession.setAttribute("PageBean", nextPageBean);
	request.getRequestDispatcher(nextPageBean.JSPName).forward(request, response);
  }  
}
