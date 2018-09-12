package com.scwe.dss.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scwe.dss.android.AndroidData;
import com.scwe.dss.android.AndroidHelper;
import com.scwe.dss.datatransfer.SensorHelper;
import com.scwe.dss.util.Constants;
import com.scwe.dss.util.HTTPHelper;


/**
 * Servlet implementation class FileUploadServlet
 */
	 
public class AndroidServlet extends iWorkerServlet {


  private static final long serialVersionUID = 593707140067300668L;

	/**
   * @see HttpServlet#HttpServlet()
   */
  public AndroidServlet() {
      super();
      // TODO Auto-generated constructor stub
  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	//�ж��������Ƿ����ԺϷ���ip��ַ  
	String ip = HTTPHelper.getIp(request);  
	    
	//������ȡ���ı��ģ�����ip��ַ��������֤�����ܵȵ����ж������ĵķ������Ƿ���Ȩ��  
	//if (AndroidHelper.isValidDevice(myProperties, ip)){
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
		AndroidData aData =  AndroidHelper.parseParameters(request, myProperties);		
		String retMsg = AndroidHelper.doAction(aData, myProperties);
	   	AndroidHelper.sendResponse(response, retMsg);
	    	
//      }
	}
  //}

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
