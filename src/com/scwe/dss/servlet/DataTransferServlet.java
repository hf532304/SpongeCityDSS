package com.scwe.dss.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scwe.dss.datatransfer.SensorHelper;
import com.scwe.dss.util.HTTPHelper;


/**
 * Servlet implementation class FileUploadServlet
 */
	 
public class DataTransferServlet extends iWorkerServlet {


  private static final long serialVersionUID = 593707140067300668L;

	/**
   * @see HttpServlet#HttpServlet()
   */
  public DataTransferServlet() {
      super();
      // TODO Auto-generated constructor stub
  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String ip = HTTPHelper.getIp(request);  
	String tmpS = request.getRequestURL().toString();

//	if (SensorHelper.isValidRemoteSensers(myProperties, ip)){

     /*
      //��ȡ�յ��ı���  
      
      BufferedReader reader = request.getReader();  
      String line = "";  
      StringBuffer inputStr = new StringBuffer();  
      while ((line = reader.readLine()) != null) {  
        inputStr.append(line);  
      }  
         
      //���б�Ҫ�������ڱ���������������֤�ͼ��ܵĲ���
      boolean isDone = TransferHelper.processSensorData(inputStr);        
      if (isDone){
    */
	   	SensorHelper.saveData(request, myProperties);
	   	SensorHelper.sendResponse(response);
	    	
//      }
	}
//  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
  }

		 
  @Override
  public String getServletInfo() {
      return "DataTransferServlet";
  }
	
}
