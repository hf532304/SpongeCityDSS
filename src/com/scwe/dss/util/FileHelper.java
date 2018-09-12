package com.scwe.dss.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.datatransfer.RPTData;
import com.scwe.dss.datatransfer.RPTLFSummaryData;
import com.scwe.dss.datatransfer.RPTSubRunoffData;

public class FileHelper {
  public static String RAIN_DATA_SECTION_BEGIN = ";;-------------- ---------------- ---------------- -------- -------- -------- -------- -------- --------";
  public static String RAIN_DATA_SECTION_END = "[SUBAREAS]";

  
  public static RPTData parseRPTFile(String fName){
	RPTData rptData = new RPTData();
	ArrayList<RPTSubRunoffData> sData = new ArrayList<RPTSubRunoffData>();
	ArrayList<RPTLFSummaryData> lData = new ArrayList<RPTLFSummaryData>();
	String[] tmpStr; 
	BufferedReader in = null;
	try {
	  String s = null;
	  boolean ifTpDepth=false;
	  boolean ifSubcatchment=false;
	  boolean ifLink=false;
	  boolean ifAllDone=false;
	  in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fName)),Constants.FILE_CHAR_CODING_GBK));
	    
	  while((s = in.readLine()) != null && !ifAllDone){
		if (s.contains(Constants.RPT_TOTAL_PRECIPITATION) && !ifTpDepth){
		  tmpStr = s.split("\\s+"); 
		  if (tmpStr.length==6)
			rptData.tpDepth = Float.valueOf(tmpStr[5]);
		  ifTpDepth = true;
		}
		if (s.contains(Constants.RPT_SUBCATCHMENT_RUNOFF_SUMMARY) && !ifSubcatchment){
		  for (int i=0; i<7; i++){
			s = in.readLine();
		  }	
		  RPTSubRunoffData subData;
		  while((s = in.readLine()) != null && !ifSubcatchment){
			if (StringHelper.isEmpty(s) || s.contains(Constants.RPT_SUBCATCHMENT_RUNOFF_SUMMARY_END)){
			  RPTSubRunoffData[] tmpData = (RPTSubRunoffData[]) sData.toArray(new RPTSubRunoffData[sData.size()]);		       
			  rptData.srfData = tmpData; 
			  ifSubcatchment = true;	
			} else {
				tmpStr = s.split("\\s+"); 
				if (tmpStr.length==10){
				  subData = new RPTSubRunoffData(tmpStr[1], Float.valueOf(tmpStr[2]), Float.valueOf(tmpStr[6]), Float.valueOf(tmpStr[9]), Float.valueOf(0));	
				  sData.add(subData);
			  }
			}
		  }
		}
		if (s.contains(Constants.RPT_LINK_FLOW_SUMMARY) && !ifLink){
		  for (int i=0; i<7; i++){
			s = in.readLine();
		  }	
		  RPTLFSummaryData linkData;		  
		  while((s = in.readLine()) != null && !ifLink){
			if (StringHelper.isEmpty(s) || s.contains(Constants.RPT_LINK_FLOW_SUMMARY_END)){
				RPTLFSummaryData tmpData[] = (RPTLFSummaryData[]) lData.toArray(new RPTLFSummaryData[lData.size()]);
 			    rptData.lfsData = tmpData;
 			    ifLink = true;	
			} else {
				tmpStr = s.split("\\s+"); 
				if (tmpStr.length==9){
  				  linkData = new RPTLFSummaryData(tmpStr[1], Float.valueOf(tmpStr[8]));
				  lData.add(linkData);
				}
			}			 
		  }
		}		
		if (ifTpDepth && ifSubcatchment && ifLink){
		  ifAllDone = true;
		}
	  }
	}catch(IOException e){
	  e.printStackTrace();
	}finally{
	  try {
		in.close();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	}	
	return rptData;
  }
  
  public static String updateINPFile(String modelName, String rainGagesData, String timeSeriesData, String fName, String folderName){
	String retVal = "成功更新了INP文件 :" + fName;
	String newName = folderName+"New_"+fName;
	fName = folderName + fName;
	BufferedReader in=null;
	BufferedWriter out=null;
	try{	
		//BufferedReader in = new BufferedReader(new FileReader(fName));
		//BufferedWriter out = new BufferedWriter(new FileWriter(newName));		
		in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fName)),Constants.FILE_CHAR_CODING_GBK));
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(newName)), Constants.FILE_CHAR_CODING_GBK));
		
		String s = null;
		String tmpStr=null;
		int i;

		boolean ifReplace = false;
		boolean ifRaingages = false;
		boolean ifTimeSeries = false;
	    
		while((s = in.readLine()) != null){
		  if (!ifRaingages && s.contains(Constants.INP_RAINGAGES_T1)){
		    out.write(rainGagesData);
		    out.newLine();
		    while((s = in.readLine()) != null && !ifRaingages){
		    	if (s.contains(Constants.INP_RAINGAGES_END)){
		    	  for (int j=0;j<3;j++){
 		  		    out.write(s);
					out.newLine();		    		
					s = in.readLine();  
		    	  }
	  		      out.write(s);
				  out.newLine();		    		
				  ifRaingages = true; 
				  ifReplace = true;
		    	} 
		    }
		  }

		  if (!ifRaingages && s.contains(Constants.INP_RAINGAGES_END)){
		    out.write(rainGagesData);
		    out.newLine();  
		    for (int j=0;j<4;j++){
	  		  out.write(s);
			  out.newLine();		    		
			  s = in.readLine();  
	    	}
			ifRaingages = true; 		    
			ifReplace = true;
		  }
		  
		  if (ifReplace){
			if (s.contains(Constants.INP_SUBCATCHMENTS_END)){
				ifReplace = false;
			} else {
				s = replaceRaingage(s, modelName);
			}
		  } 
		  
		  if (!ifTimeSeries && s.contains(Constants.INP_TIMESERIES_T1)){
		    out.write(timeSeriesData);
		    out.newLine();
		    while((s = in.readLine()) != null && !ifTimeSeries){
		    	if (s.contains(Constants.INP_TIMESERIES_END)){
		  		  out.write(s);
				  out.newLine();			    		
				  ifTimeSeries = true; 
		    	} 
		    }		  
		  }
		  if (!ifTimeSeries && s.contains(Constants.INP_TIMESERIES_END)){
		    out.write(timeSeriesData);
		    out.newLine();		    
  		  	ifTimeSeries = true; 
		  }

		  out.write(s);
		  out.newLine();		    	
		}  
	}catch(IOException e){
	  e.printStackTrace();
	  retVal = "";
	}finally{
	  try {
		out.flush();
		in.close();
		out.close();
		
		deleteFile(fName);
		renameFile(newName, fName);
		
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }	
	}
	
	return retVal;
  }
  
  
  /*
[SUBCATCHMENTS]
;;                                                 Total    Pcnt.             Pcnt.    Curb     Snow    
;;Name           Raingage         Outlet           Area     Imperv   Width    Slope    Length   Pack    
;;-------------- ---------------- ---------------- -------- -------- -------- -------- -------- -------- 
   * */
  public static String replaceRaingage(String s, String newModel){
	String tmpStr1, tmpStr;
	StringBuffer tmpBuffer = new StringBuffer();
	int i;
	if (s.length()>17){
		tmpStr1 = s.substring(0, 17);
		tmpStr = s.substring(17); // Start point of 
		i = tmpStr.indexOf(" ");
		if (i>16){
		  tmpBuffer.append(tmpStr1).append(StringHelper.getFixedLengthStr(newModel, 17)).append(tmpStr.substring(i)); 	
		} else {
		  if (s.length()>34){
			  tmpStr = s.substring(34);	
			  tmpBuffer.append(tmpStr1).append(StringHelper.getFixedLengthStr(newModel, 17)).append(tmpStr);
		  } else {
			tmpBuffer.append(s);			  
		  }
		}  
	} else
		tmpBuffer.append(s);
	return tmpBuffer.toString();
  }
  
  
  public static void renameFile(String oldName,String newName){ 
      if(!oldName.equals(newName)){//新的文件名和以前文件名不同时,才有必要进行重命名 
          File oldfile=new File(oldName); 
          File newfile=new File(newName); 
          if(!oldfile.exists()){
              return;//重命名文件不存在
          }
          if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
              System.out.println(newName+"已经存在！"); 
          else{ 
              oldfile.renameTo(newfile); 
          } 
      }else{
          System.out.println("新文件名和旧文件名相同...");
      }
  }
  
  public static void renameFile(String path,String oldname,String newname){
	  renameFile(path+"/"+oldname, path+"/"+newname);
  }

  public static String uploadINPFile(HttpServletRequest request, String savePath, String newFileName) {
	File file = new File(savePath);
	if (!file.exists() && !file.isDirectory()) {
	  file.mkdir();
	}
	String retVal = "";
	try{
	  DiskFileItemFactory factory = new DiskFileItemFactory();
	  ServletFileUpload upload = new ServletFileUpload(factory);
	  upload.setHeaderEncoding("UTF-8"); 
	  if(ServletFileUpload.isMultipartContent(request)){
		List<FileItem> list = upload.parseRequest(request);
		for(FileItem item : list){
		  if(! item.isFormField() ){
			String filename = item.getName();
			if(filename==null || filename.trim().equals("")){
			  continue;
			}
			newFileName = newFileName+"_"+filename.substring(filename.lastIndexOf("\\")+1);
			InputStream in = item.getInputStream();
			retVal = savePath + File.separator + newFileName;
			FileOutputStream out = new FileOutputStream(retVal);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
			out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			in.close();
			item.delete();
		  }
		}
	  }
	  
	}catch (Exception e) {
		e.printStackTrace();	 
	}
	return retVal;
  }

  public static String getCurrentPageNameFromMultipart(HttpServletRequest request, String fieldName) {
	String retVal = "";
	try{
	  DiskFileItemFactory factory = new DiskFileItemFactory();
	  ServletFileUpload upload = new ServletFileUpload(factory);
	  upload.setHeaderEncoding("UTF-8"); 
	  if(ServletFileUpload.isMultipartContent(request)){
		List<FileItem> list = upload.parseRequest(request);
		for(FileItem item : list){
		  if(item.isFormField()){
			if (fieldName.equals(item.getFieldName())){
			  retVal = item.getString("UTF-8");
			  return retVal.substring(0, retVal.lastIndexOf("/"));
			}
		  }
		}
	  }
	}catch (Exception e) {
		e.printStackTrace();	 
	}
	return retVal;
  }

  public static boolean deleteFile(String sPath) {  
    boolean retVal = false;  
    File file = new File(sPath);  
    // 路径为文件且不为空则进行删除  
    if (file.isFile() && file.exists()) {  
        file.delete();  
        retVal = true;  
    }  
    return retVal;  
  } 
}
