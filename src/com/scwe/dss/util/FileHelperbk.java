package com.scwe.dss.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.jl.foundation.util.StringHelper;

public class FileHelperbk {
  public static String RAIN_DATA_SECTION_BEGIN = ";;-------------- ---------------- ---------------- -------- -------- -------- -------- -------- --------";
  public static String RAIN_DATA_SECTION_END = "[SUBAREAS]";

  public static String updateINPFile(String modelName, String rainGagesData, String timeSeriesData, String fName, String folderName){

	String newName = folderName+"New_"+fName;
	fName = folderName + fName;
	String retVal = "成功更新了INP文件.新INP文件名为 " + newName;

	try{	
		BufferedReader in = new BufferedReader(new FileReader(fName));
		BufferedWriter out = new BufferedWriter(new FileWriter(newName));
		String s = null;
		String tmpStr=null;
		int i;

		boolean ifReplace = false;
	    
		while((s = in.readLine()) != null){
		  if (s.contains(Constants.INP_RAINGAGES_T1)){
		    out.write(rainGagesData);
		    out.newLine();
		    while((s = in.readLine()) != null && !ifReplace){
		    	if (s.contains(Constants.INP_RAINGAGES_END)){
		    	  for (int j=0;j<3;j++){
 		  		    out.write(s);
					out.newLine();		    		
					s = in.readLine();  
		    	  }
	  		      out.write(s);
				  out.newLine();		    		
			      ifReplace = true; 
		    	} 
		    }
		  }

		  if (ifReplace){
			if (s.contains(Constants.INP_SUBCATCHMENTS_END)){
				ifReplace = false;
			} else {
				s = replaceRaingage(s, modelName);
			}
		  } else if (s.contains(Constants.INP_TIMESERIES_T1)){
			    out.write(timeSeriesData);
			    out.newLine();
			    while((s = in.readLine()) != null && !ifReplace){
			    	if (s.contains(Constants.INP_TIMESERIES_END)){
			  		  out.write(s);
					  out.newLine();			    		
				      ifReplace = true; 
			    	} 
			    }		  
		  }
		  out.write(s);
		  out.newLine();		    	
		}

		out.flush();
		in.close();
		out.close();
	}catch(IOException e){
	  e.printStackTrace();
	  retVal = "";
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
		  tmpBuffer.append(tmpStr1).append(StringHelper.getFixedLengthStr(newModel, 16)).append(tmpStr.substring(i)); 	
		} else {
		  if (s.length()>34){
			  tmpStr = s.substring(34);	
			  tmpBuffer.append(tmpStr1).append(StringHelper.getFixedLengthStr(newModel, 16)).append(tmpStr);
		  } else {
			tmpBuffer.append(s);			  
		  }
		}  
	} else
		tmpBuffer.append(s);
	return tmpBuffer.toString();
  }
  
  public void renameFile(String path,String oldname,String newname){ 
      if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
          File oldfile=new File(path+"/"+oldname); 
          File newfile=new File(path+"/"+newname); 
          if(!oldfile.exists()){
              return;//重命名文件不存在
          }
          if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
              System.out.println(newname+"已经存在！"); 
          else{ 
              oldfile.renameTo(newfile); 
          } 
      }else{
          System.out.println("新文件名和旧文件名相同...");
      }
  }
	
}
