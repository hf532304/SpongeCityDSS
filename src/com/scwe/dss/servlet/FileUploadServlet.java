package com.scwe.dss.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class FileUploadServlet
 */
	 
public class FileUploadServlet extends iWorkerServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	String savePath = getProperty("UPLOAD_PATH"); // "d:/temp/upload"; // 
	File file = new File(savePath);
	if (!file.exists() && !file.isDirectory()) {
	  System.out.println(savePath+" just created");
	  file.mkdir();
	}
	String message = "";
	try{
	  DiskFileItemFactory factory = new DiskFileItemFactory();
	  ServletFileUpload upload = new ServletFileUpload(factory);
	  upload.setHeaderEncoding("UTF-8"); 
	  if(!ServletFileUpload.isMultipartContent(request)){
		return;
	  }
	  List<FileItem> list = upload.parseRequest(request);
	  for(FileItem item : list){
		if(item.isFormField()){
			String name = item.getFieldName();
			String value = item.getString("UTF-8");
		//value = new String(value.getBytes("iso8859-1"),"UTF-8");
		}else{
			String filename = item.getName();
			if(filename==null || filename.trim().equals("")){
			  continue;
			}
			filename = filename.substring(filename.lastIndexOf("\\")+1);
			InputStream in = item.getInputStream();
			FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
			out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			in.close();
			item.delete();
			message = "Succeeded to delete";
		}
	  }
	}catch (Exception e) {
		message= "Failed";
		e.printStackTrace();	 
	}
		request.setAttribute("message",message);
		request.getRequestDispatcher("/"+"home.html").forward(request, response);
	}
	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	 
	doGet(request, response);
	}

}
