package com.scwe.dss.util;

public class Constants {
	static public String APP_CONFIG_FILE = "/config/SpongeCityDSS.properties";
	public static final String ANDROID_ACTION_QUERY = "1";
	public static final String ANDROID_ACTION_LOGIN = "0";
	public static final String CODE_SUCCEEDED = "0";
	public static final String INVALID_SENDER = "Invalid sender";
	public static final String INITIAL_DATA_FILE = "54398.txt";
	public static final String SERVER_RESPONSE_TERMINAL_UPLOAD = "S3";
	public static final String IWORKER_REQUEST_NAME = "iWorkerRequest";
	public static final String AJAX_SENSOR_DATA = "SensorData";
	public static final String AJAX_RAIN_DATA = "RainData";
	public static final String AJAX_UPDATE_INP = "UpdateINP";
	public static final String AJAX_FILEDATA_RPT = "RPTFileData";
	public static final String SRUNOFF_NEXT_RPT = "sRunoffDataNext";
	public static final String SRUNOFF_PREV_RPT = "sRunoffDataPrev";
	public static final String LINKFLOW_NEXT_RPT = "linkFlowDataNext";
	public static final String LINKFLOW_PREV_RPT = "linkFlowDataPrev";
	
	
	public static final String INP_RAINGAGES_T1 = "[RAINGAGES]";
	public static final String INP_RAINGAGES_T2 = ";;                   Rain      Time   Snow   Data";      
	public static final String INP_RAINGAGES_T3 = ";;Name               Type      Intrvl Catch  Source";    
	public static final String INP_RAINGAGES_T4 = ";;------------------ --------- ------ ------ ----------";

	public static final String INP_RAINGAGES_END = "[SUBCATCHMENTS]";
	public static final String INP_SUBCATCHMENTS_END = "[SUBAREAS]";

	public static final String INP_TIMESERIES_T1 = "[TIMESERIES]";
	public static final String INP_TIMESERIES_T2 = ";;Name           Date       Time       Value";      
	public static final String INP_TIMESERIES_T3 = ";;-------------- ---------- ---------- ----------";
			     
	public static final String INP_TIMESERIES_END = "[REPORT]";

	public static final String INP_RAIN_TYPE = "VOLUME";
	public static final String INP_RAINGAGES_5MINS = "_5m";	
	public static final String INP_RAINGAGES_5MINS_INTRVL = "0:05";	
	public static final String INP_RAINGAGES_10MINS = "_10m";	
	public static final String INP_RAINGAGES_10MINS_INTRVL = "0:10";	
	public static final String INP_RAINGAGES_SNOWCATCH = "1.0";	
	public static final String INP_RAINGAGES_TIMESERIES = "TIMESERIES";	
	 
	public static final String FILE_CHAR_CODING_GBK="GBK";
	
	public static final String RPT_TOTAL_PRECIPITATION = "Total Precipitation ......";	
	public static final String RPT_SUBCATCHMENT_RUNOFF_SUMMARY = "Subcatchment Runoff Summary";
	public static final String RPT_SUBCATCHMENT_RUNOFF_SUMMARY_END = "Node Depth Summary";
	public static final String RPT_LINK_FLOW_SUMMARY = "Link Flow Summary";
	public static final String RPT_LINK_FLOW_SUMMARY_END = "Flow Classification Summary";
		
	public static final String DESIGN_STATUS_NEW = "新建";	
	public static final String DESIGN_STATUS_INP = "INP已上传";	
	public static final String DESIGN_STATUS_RPT = "RPT已存储";	
	public static final String DESIGN_STATUS_SWMM = "计算已完成";	
	public static final String DESIGN_STATUS_RAIN = "更改雨型";	
	public static final String DESIGN_STATUS_END = "完成";	
	
	public static int NUM_PER_PAGE = 25;
}
