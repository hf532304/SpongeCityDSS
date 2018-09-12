package com.scwe.dss.dbhelper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.jl.foundation.db.helpers.DBHelper;
import com.scwe.dss.datatransfer.DesignData;
import com.scwe.dss.datatransfer.ProjectData;
import com.scwe.dss.datatransfer.RPTData;
import com.scwe.dss.datatransfer.RPTSubRunoffData;
import com.scwe.dss.datatransfer.RainModel;
import com.scwe.dss.datatransfer.RainModelData;
import com.scwe.dss.datatransfer.UserData;

public class DesignManagementDBHelper {

  static String sql = null;  
  static DBHelper db1 = null;  
  static ResultSet ret = null;  
    
  public static ProjectData[] getMyProjectData(Properties myProperty, String aCreator){
	ProjectData myProjectData[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select ProjectId, ProjectName, ProjectDesc, MapUrl, TRateARV, Creator, CreationTime FROM projectinfo WHERE Creator=?  order by CreationTime desc";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
      db1.pst.setString(1, aCreator);
      ret = db1.pst.executeQuery();  
      if (ret.last()){ //结果集指针知道最后一行数据  
    	  int n = ret.getRow();    
    	  myProjectData = new ProjectData[n];
    	  ret.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据  
	      while (ret.next()) {  
	        int pId = ret.getInt(1);  
	        String pName = ret.getString(2);  
	        String pDesc = ret.getString(3); 
	        String mUrl = ret.getString(4);  
	        BigDecimal tRateARV =  new BigDecimal(ret.getString(5));  
	        String creator = ret.getString(6);  
	        String cTime = ret.getTimestamp(7).toLocalDateTime().toString();  
	        myProjectData[i++] = new ProjectData(String.valueOf(pId), pName, pDesc, mUrl, tRateARV, creator,cTime);
	      }
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return myProjectData;
  }
  
  
  public static UserData getMyInfo(Properties myProperty, String aCreator){
	  UserData myInfo=null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select ifnull(B.ProjectId,''), ifnull(A.ProjectName,''), ifnull(B.DesignId,0), ifnull(B.DesignName,''), ifnull(B.DesignStatus,''), ifnull(B.ModelName,''), ifnull(B.INPFileName,''), ifnull(B.RPTFileName,'') FROM DesignInfo B Left Join ProjectInfo A ON B.ProjectId=A.ProjectId WHERE b.IsDefault='Y' AND b.Creator= ? order by B.ProjectId desc, B.CreationTime desc";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
      db1.pst.setString(1, aCreator);
      ret = db1.pst.executeQuery();  
	  if (ret.next()) {  
        int pId = ret.getInt(1);  
        String pName = ret.getString(2);  
        int dId = ret.getInt(3);  
        String dName = ret.getString(4);  
        String dStatus = ret.getString(5);  
        String modelName = ret.getString(6);  
        String inpName = ret.getString(7);  
        String rptName = ret.getString(8);  
        if(pId>0)
          myInfo = new UserData(aCreator, String.valueOf(pId), pName, String.valueOf(dId), dName, dStatus, modelName, inpName, rptName);
        else
          myInfo = new UserData(aCreator, "", "", String.valueOf(dId), dName, dStatus, modelName, inpName, rptName);
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return myInfo;
  }
  
  public static DesignData[] getMyDesignData(Properties myProperty, String aCreator){
	DesignData myDesignData[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select ifnull(A.ProjectId,''), ifnull(A.ProjectName,''), ifnull(A.MapURL,''), ifnull(B.DesignId,0), ifnull(B.DesignName,''), ifnull(B.DesignStatus,''), ifnull(B.IsDefault,''), ifnull(B.ModelName,''), ifnull(B.INPFileName,''), ifnull(B.RPTFileName,''), B.Creator, B.CreationTime FROM DesignInfo B left join projectinfo A on a.projectId = b.projectId WHERE b.Creator=? order by A.ProjectId, B.CreationTime desc";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
      db1.pst.setString(1, aCreator);
      ret = db1.pst.executeQuery();  
      if (ret.last()){ //结果集指针知道最后一行数据  
    	  int n = ret.getRow();    
    	  myDesignData = new DesignData[n];
    	  ret.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据  
	      while (ret.next()) {  
	        int pId = ret.getInt(1);  
	        String pName = ret.getString(2);  
	        String mUrl = ret.getString(3); 
	        int dId = ret.getInt(4);  
	        String dName = ret.getString(5);  
	        String dStatus = ret.getString(6);  
	        String isDefault = ret.getString(7);  
	        String rainName = ret.getString(8);  
	        String inpName = ret.getString(9);  
	        String rptName = ret.getString(10);  
	        String creator = ret.getString(11);  
	        String cTime = ret.getTimestamp(12).toLocalDateTime().toString();
	        if(pId>0)
	          myDesignData[i++] = new DesignData(String.valueOf(pId), pName, mUrl, String.valueOf(dId), dName, dStatus, isDefault, rainName, inpName, rptName, creator,cTime);
	        else
	          myDesignData[i++] = new DesignData("", pName, mUrl, String.valueOf(dId), dName, dStatus, isDefault, rainName, inpName, rptName, creator,cTime);
	      }
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return myDesignData;
  }
  
  public static String getDefaultINPName(Properties myProperty, String aCreator){
	String inpName = "";
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select INPFileName FROM DesignInfo WHERE isDefault='Y' AND Creator=?";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
      db1.pst.setString(1, aCreator);
	    ret = db1.pst.executeQuery();  
	    if (ret.next()){   
	    	inpName = ret.getString(1);  
	    }
	    ret.close();  
	    db1.close();
	  } catch (SQLException e) {  
	      e.printStackTrace();  
	  } 

	return inpName;
  }
  
  
  public static RainModel[] getMyRainModels(Properties myProperty){
	RainModel myModels[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select distinct ModelName, ModelDesc from RainModelName order by ModelName";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
      ret = db1.pst.executeQuery();  
      if (ret.last()){ //结果集指针知道最后一行数据  
    	  int n = ret.getRow();   
    	  String rName, rDesc;
    	  myModels = new RainModel[n];
    	  ret.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据  
	      while (ret.next()) {  
	    	rName = ret.getString(1); 
	    	rDesc = ret.getString(2);
	    	myModels[i++] = new RainModel(rName, rDesc);
	      }
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return myModels;
  }

  public static RainModelData[] getMyRainModelData(Properties myProperty, String mName){
	RainModelData myModels[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	//sql = "select ModelName, RainTime, RainVolume from RainModelData where ModelName = ? order by RainTime";
	sql = "select A.ModelName, B.ModelDesc, A.RainTime, A.RainVolume from RainModelData A Left Join RainModelName B on a.ModelName = b.ModelName WHERE a.ModelName=? order by RainTime";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
  	  db1.pst.setString(1, mName);
      ret = db1.pst.executeQuery();  
      if (ret.last()){ //结果集指针知道最后一行数据  
	    String aName; 
	    String aDesc; 
	    String aTime; 
	    BigDecimal aVolume; 
    	int n = ret.getRow();    
	    myModels = new RainModelData[n];
	    ret.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据  
	    while (ret.next()) {  
	      aName = ret.getString(1); 
	      aDesc = ret.getString(2);
	      aTime = ret.getString(3); 
	      aVolume = ret.getBigDecimal(4); 
	      myModels[i++] = new RainModelData(aName, aDesc, aTime, aVolume); 
	    }
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return myModels;
  }

  public static int insertProjectInfo(Properties myProperty, String pName, String pDesc, String mUrl, String tRateARV, String creator){
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "Insert into ProjectInfo(ProjectName, ProjectDesc, MapUrl, TRateARV, Creator) Values(?, ?, ?, ?, ?)"; 
    db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int retVal=-1;
    try {  
      db1.pst.setString(1, pName);
      db1.pst.setString(2, pDesc);
      db1.pst.setString(3, mUrl);
      db1.pst.setBigDecimal(4, new BigDecimal(tRateARV));
      db1.pst.setString(5, creator);
      retVal = db1.pst.executeUpdate();  
      db1.close();
    } catch (SQLException e) {  
      e.printStackTrace();  
    } 
	return retVal;
  }

  public static int insertDesignInfo(Properties myProperty, String pId, String dName, String dStatus, String isDefault, String rModel, String aCreator){
		String dbUrl = myProperty.getProperty("DB_URL");
		String dbDriver = myProperty.getProperty("DB_DRIVER");
		String dbUser = myProperty.getProperty("DB_USER");
		String dbPassword = myProperty.getProperty("DB_PASSWORD");	
  		sql = "INSERT INTO DesignInfo(ProjectId, DesignName, DesignStatus, IsDefault, ModelName, Creator) VALUES(?, ?, ?, ?, ?, ?)";  
	    db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
	    int retVal=-1;
	    try {  
          db1.pst.setInt(1, Integer.parseInt(pId));
  		  db1.pst.setString(2, dName);
  		  db1.pst.setString(3, dStatus);
  		  db1.pst.setString(4, isDefault);
  		  db1.pst.setString(5, rModel);
  		  db1.pst.setString(6, aCreator);
	      retVal = db1.pst.executeUpdate();	  		
	      db1.close();
	    } catch (SQLException e) {  
	      e.printStackTrace();  
	    } 

		return retVal;
	  }

   public static int updateDesignInfo(Properties myProperty, String pId, String dId, String dName, String rName, String aCreator){
		String dbUrl = myProperty.getProperty("DB_URL");
		String dbDriver = myProperty.getProperty("DB_DRIVER");
		String dbUser = myProperty.getProperty("DB_USER");
		String dbPassword = myProperty.getProperty("DB_PASSWORD");	
		sql = "UPDATE DesignInfo SET ProjectId=?, DesignName=?, ModelName = ? WHERE DesignId = ? and Creator=?";
	    db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
	    int retVal=-1;
	    try {  	      
	      db1.pst.setInt(1, Integer.parseInt(pId));
	      db1.pst.setString(2, dName);
	      db1.pst.setString(3, rName);
	      db1.pst.setInt(4, Integer.parseInt(dId));
	      db1.pst.setString(5, aCreator);
	      retVal = db1.pst.executeUpdate();
	      db1.close();
	    } catch (SQLException e) {  
	      e.printStackTrace();  
	    } 

		return retVal;
	  }

  	public static int updateProjectInfo(Properties myProperty, String pId, String pName, String pDesc, String mUrl, String tRateARV, String aCreator){
		String dbUrl = myProperty.getProperty("DB_URL");
		String dbDriver = myProperty.getProperty("DB_DRIVER");
		String dbUser = myProperty.getProperty("DB_USER");
		String dbPassword = myProperty.getProperty("DB_PASSWORD");	
		sql = "UPDATE ProjectInfo SET ProjectName=?, ProjectDesc = ?, MapURL = ?, TRateARV =? WHERE ProjectId = ? and Creator=?";
	    db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
	    int retVal=-1;
	    try {  
	      db1.pst.setString(1, pName);
	      db1.pst.setString(2, pDesc);
	      db1.pst.setString(3, mUrl);
	      db1.pst.setBigDecimal(4, new BigDecimal(tRateARV));
	      db1.pst.setInt(5, Integer.parseInt(pId));
	      db1.pst.setString(6, aCreator);
	      //db1.pst.setString(5, aCreator);
	      retVal = db1.pst.executeUpdate();
	      db1.close();
	    } catch (SQLException e) {  
	      e.printStackTrace();  
	    } 
		return retVal;
	  }
  
  public static int setDefaultDesign(Properties myProperty, String designId, String creator){
	DesignData myDesignData[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
    int retVal=0;
    try {  
	  sql = "UPDATE DesignInfo SET IsDefault='N' WHERE Creator = ?";
	  db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
      db1.pst.setString(1, creator);      
      retVal = db1.pst.executeUpdate();  
      db1.pst.close();  

      sql = "UPDATE DesignInfo SET IsDefault='Y' WHERE DesignId = ?";
	  db1.pst = db1.conn.prepareStatement(sql);
      db1.pst.setInt(1, Integer.parseInt(designId));
      retVal = db1.pst.executeUpdate();  
      db1.close();
    } catch (SQLException e) {  
      e.printStackTrace();
      retVal = -1;
    } 	
	return retVal;
  }
  
  public static int deleteDesign(Properties myProperty, String designId){
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
    int retVal=0;
    try {  
	  sql = "DELETE FROM DesignInfo WHERE DesignId = ?";
	  db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
      db1.pst.setInt(1, Integer.parseInt(designId));
      retVal = db1.pst.executeUpdate();  

      db1.close();

    } catch (SQLException e) {  
      e.printStackTrace();
      retVal = -1;
    } 	
	return retVal;
  }
  
  public static int deleteProject(Properties myProperty, String pId){
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
    int retVal=0;
    try {  
      int projectId = Integer.parseInt(pId);
	  sql = "DELETE FROM ProjectInfo WHERE ProjectId = ?";
	  db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
      db1.pst.setInt(1, projectId);
      retVal = db1.pst.executeUpdate();  
      db1.pst.close();  

      sql = "DELETE FROM DesignInfo WHERE ProjectId = ?";
	  db1.pst = db1.conn.prepareStatement(sql);
      db1.pst.setInt(1, projectId);
      db1.pst.executeUpdate();  
      db1.close();

    } catch (SQLException e) {  
      e.printStackTrace();
      retVal = -1;
    } 	
	return retVal;
  }

  public static int saveRPTData(Properties myProperty, RPTData rptData){
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");
	
    int retVal=-1;
    try {  
      sql = "DELETE FROM RPTSummary WHERE RPTFileName = ?";
	  db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
      db1.pst.setString(1, rptData.rptFileName);
  	  db1.pst.executeUpdate();  
      db1.pst.close();  
	  sql = "INSERT INTO RPTSummary(RPTFileName, TPDepth) VALUES(?, ?)";  
  	  db1.pst = db1.conn.prepareStatement(sql);
      db1.pst.setString(1, rptData.rptFileName);
	  db1.pst.setBigDecimal(2, BigDecimal.valueOf(rptData.tpDepth));
      retVal = db1.pst.executeUpdate();	  		
      db1.pst.close();  
        
      sql = "DELETE FROM RPTSubcatchmentRunoff WHERE RPTFileName = ?";
	  db1.pst = db1.conn.prepareStatement(sql);
      db1.pst.setString(1, rptData.rptFileName);
	  db1.pst.executeUpdate();  
      db1.pst.close();  
      sql = "INSERT INTO RPTSubcatchmentRunoff(RPTFileName, SubcatchmentId, TPDepth, TotalRunoff, RunoffCoeff) VALUES(?, ?, ?, ?, ?)";
	  db1.pst = db1.conn.prepareStatement(sql);
	  for (int i=0; i<rptData.srfData.length; i++){
	    db1.pst.setString(1, rptData.rptFileName);
	    db1.pst.setString(2, rptData.srfData[i].subcatchmentId);
  	    db1.pst.setBigDecimal(3, BigDecimal.valueOf(rptData.srfData[i].tPcpttionmm));
   	    db1.pst.setBigDecimal(4, BigDecimal.valueOf(rptData.srfData[i].tRunoffmm));
	    db1.pst.setBigDecimal(5, BigDecimal.valueOf(rptData.srfData[i].tRunoffCoeff));
	    db1.pst.executeUpdate();  
	  }
      db1.pst.close();  

      sql = "DELETE FROM RPTLinkFlow WHERE RPTFileName = ?";
	  db1.pst = db1.conn.prepareStatement(sql);
      db1.pst.setString(1, rptData.rptFileName);
	  db1.pst.executeUpdate();  
      db1.pst.close();  
      sql = "INSERT INTO RPTLinkFlow(RPTFileName, LinkId, MFDepth) VALUES(?, ?, ?)";
	  db1.pst = db1.conn.prepareStatement(sql);
	  for (int i=0; i<rptData.lfsData.length; i++){
	    db1.pst.setString(1, rptData.rptFileName);
	    db1.pst.setString(2, rptData.lfsData[i].linkId);
  	    db1.pst.setBigDecimal(3, BigDecimal.valueOf(rptData.lfsData[i].mfDepth));
	    db1.pst.executeUpdate();  
	  }
      db1.close();
    } catch (SQLException e) {  
      e.printStackTrace();  
    } 

	return retVal;
  } 
  
  public static RPTSubRunoffData[] getSRFData(Properties myProperty, String fName){
	RPTSubRunoffData srfData[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");
	sql = "select A.RPTFileName, A.SubcatchmentId, A.TPDepth, A.TotalRunoff, A.RunoffCoeff, ifnull(C.TRateARV,0) from RPTSubcatchmentRunoff A, designinfo B, projectinfo C where A.RPTFileName=B.RPTFileName and B.ProjectId=C.ProjectId and A.RPTFileName=?";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
  	  db1.pst.setString(1, fName);
      ret = db1.pst.executeQuery();  
      if (ret.last()){ //结果集指针知道最后一行数据  
	    String aId; 
	    BigDecimal tpDepth; 
	    BigDecimal tRunoff; 
	    BigDecimal rCoeff; 
	    BigDecimal tRate; 
    	int n = ret.getRow();    
    	srfData = new RPTSubRunoffData[n];
	    ret.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据  
	    while (ret.next()) {  
	      aId = ret.getString(2); 
	      tpDepth = ret.getBigDecimal(3);
	      tRunoff = ret.getBigDecimal(4); 
	      rCoeff = ret.getBigDecimal(5); 
	      tRate =  ret.getBigDecimal(6);
	      srfData[i++] = new RPTSubRunoffData(aId, tpDepth, tRunoff, rCoeff, tRate); 
	    }
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 

	return srfData;
  }
  public static RainModelData[] getRPTSummary(Properties myProperty, String mName){
	RainModelData myModels[] = null;
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
	sql = "select TPDepth, TRateARV from RPTSummary where RPTFileName=?";
	db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
    int i =0;
    try {  
  	  db1.pst.setString(1, mName);
      ret = db1.pst.executeQuery();  
      if (ret.last()){ //结果集指针知道最后一行数据  
	    String aName; 
	    String aDesc; 
	    String aTime; 
	    BigDecimal aVolume; 
    	int n = ret.getRow();    
	    myModels = new RainModelData[n];
	    ret.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据  
	    while (ret.next()) {  
	      aName = ret.getString(1); 
	      aDesc = ret.getString(2);
	      aTime = ret.getString(3); 
	      aVolume = ret.getBigDecimal(4); 
	      myModels[i++] = new RainModelData(aName, aDesc, aTime, aVolume); 
	    }
      }
      ret.close();  
      db1.close();
    } catch (SQLException e) {  
        e.printStackTrace();  
    } 
	return myModels;
  }

  public static int UpdateINPUpload(Properties myProperty, String designId, String inpName, String dStatus){
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
    int retVal=0;
    try {  
      int id = Integer.valueOf(designId).intValue();
	  sql = "UPDATE DesignInfo SET INPFileName=?, DesignStatus=? WHERE DesignId = ?";
	  db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
      db1.pst.setString(1, inpName);      
      db1.pst.setString(2, dStatus);      
      db1.pst.setInt(3, id);      
      retVal = db1.pst.executeUpdate();  
      db1.close();
    } catch (SQLException e) {  
      e.printStackTrace();
      retVal = -1;
    } 	
	return retVal;
  }
  
  public static int updateRainModel(Properties myProperty, String designId, String mName, String dStatus){
	String dbUrl = myProperty.getProperty("DB_URL");
	String dbDriver = myProperty.getProperty("DB_DRIVER");
	String dbUser = myProperty.getProperty("DB_USER");
	String dbPassword = myProperty.getProperty("DB_PASSWORD");	
    int retVal=0;
    try {  
      int id = Integer.valueOf(designId).intValue();
	  sql = "UPDATE DesignInfo SET ModelName=?, DesignStatus=? WHERE DesignId = ?";
	  db1 = DBHelper.getNewDBHelper(dbDriver, dbUrl, dbUser, dbPassword, sql);
      db1.pst.setString(1, mName);      
      db1.pst.setString(2, dStatus);      
      db1.pst.setInt(3, id);      
      retVal = db1.pst.executeUpdate();  
      db1.close();
    } catch (SQLException e) {  
      e.printStackTrace();
      retVal = -1;
    } 	
	return retVal;
  }  
}
