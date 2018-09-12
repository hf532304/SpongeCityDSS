package com.scwe.dss.dbhelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.jl.foundation.db.helpers.DBHelper;
import com.scwe.dss.datatransfer.SensorData;
import com.scwe.dss.datatransfer.SensorInfo;

public class IntelligentMonitoringDBHelper {

  static String sql = null;  
  static DBHelper db1 = null;  
  static ResultSet ret = null;  
    
  public static SensorInfo[] loadInitialData(Properties myProperty){
	SensorInfo mySensorInfo[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select SensorId, SensorName, SensorDesc, Longitude, Latitude, Height from SensorInfo";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
      ret = db1.pst.executeQuery();  
      if (ret.last()){ //结果集指针知道最后一行数据  
    	  int n = ret.getRow();    
    	  mySensorInfo = new SensorInfo[n];
    	  ret.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据  
	      while (ret.next()) {  
	        String sId = ret.getString(1);  
	        String sName = ret.getString(2);  
	        String sDesc = ret.getString(3); 
	        Float sLong = ret.getFloat(4);  
	        Float sLati = ret.getFloat(5);  
	        Float sHeig = ret.getFloat(6); 
	        mySensorInfo[i++] = new SensorInfo(sId, sName, sDesc, sLong, sLati, sHeig);
	      }
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return mySensorInfo;
  }
  
  public static SensorData getSensorData(Properties myProperty, String tId){
	SensorData mySensorData = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select TerminalId, TerminalType, Version, RequestType, Temperature, PH, COD, Chlorophyll, SendTime from SensorData where TerminalId=? order by SendTime desc limit 1";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
      db1.pst.setString(1, tId);
      ret = db1.pst.executeQuery();  
      if (ret.next()){ //结果集指针知道最后一行数据  
        String aId = ret.getString(1);  
        String tType = ret.getString(2);  
        String aVersion = ret.getString(3); 
        String rType = ret.getString(4);  
        String temp = ret.getString(5);  
        String ph = ret.getString(6);  
        String cod = ret.getString(7);  
        String chlo = ret.getString(8); 
        String aTime = ret.getString(9);
        mySensorData = new SensorData(aTime, tType, aVersion, aId, rType, temp, ph, cod, chlo);
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    }
    
    return mySensorData;
  }
    
  public static int saveSensorData(Properties myProperty, SensorData sData){
	int retVal = -1;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "INSERT INTO SensorData(TerminalId, TerminalType, Version, RequestType, Temperature, PH, COD, Chlorophyll, SendTime) VALUES(?,?,?,?,?,?,?,?,?)";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    try {  
      db1.pst.setString(1, sData.terminalId);
      db1.pst.setString(2, sData.terminalType);
      db1.pst.setString(3, sData.version);
      db1.pst.setString(4, sData.requestType);
      db1.pst.setString(5, sData.parameter1);
      db1.pst.setString(6, sData.parameter2);
      db1.pst.setString(7, sData.parameter3);
      db1.pst.setString(8, sData.parameter4);
      db1.pst.setString(9, sData.time);
      retVal = db1.pst.executeUpdate();        
      db1.pst.close();  

      sql = "DELETE FROM SensorData WHERE TerminalId = ? AND DATE(CreationTime) != CURDATE()";
	  db1.pst = db1.conn.prepareStatement(sql);
      db1.pst.setString(1, sData.terminalId);
      retVal = db1.pst.executeUpdate();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return retVal;
  }
  
}
