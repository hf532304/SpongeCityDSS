<%@page import="com.scwe.dss.pagebean.designmanagement.DisplayDesignPageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% DisplayDesignPageBean pageBean = (DisplayDesignPageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>海绵城市规划</title>
	 	<link rel="stylesheet" href="arcgis_js/dijit/themes/claro/claro.css">
	<link rel="stylesheet" href="arcgis_js/esri/css/esri.css" />
	<link rel="stylesheet" href="arcgis_js/dojox/grid/resources/claroGrid.css">	
  <style type="text/css">
      body, html { font-family:helvetica,arial,sans-serif; font-size:90%; }
  </style>
  <script src="js/javascript.js" type="text/javascript" language="JavaScript"></script>  
  <script src="arcgis_js/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
        
  <style>
    html, body, #arcgisDiv{
      width:100%;
      height:100%;
      margin:0;
      padding:0;
      border:1px solid #000;
    }  
    #BMapToggle {  
      position: absolute;  
      right: 0px;  
      top: 0px;  
      z-index: 50;
 			opacity: 0.8;
    }  
    #PageHeader {  
      position: absolute;  
      left: 70px;  
      top: 0px;  
      z-index: 50;
      opacity: 0.8;
    }              
    #LayersDiv {  
      position: absolute;  
      right: 0px;  
      top: 80px;  
      z-index: 50;
      opacity: 0.8;
      background-color: #4169E1;
      color: white;
    } 
    #PrecipitationNode {position:absolute;width:400px;height:20px;left:50%;top:50%; margin-left:-200px;margin-top:-100px;border:0px;font-size:200%} 
    #ConfirmationNode {position:absolute;width:400px;height:20px;left:50%;top:50%; margin-left:-200px;margin-top:-100px;border:0px;font-size:200%} 
        
  </style>

  <script>
	  var visible = [];
		var sRunoffLayout = [[{'name': '汇水区', 'field': 'subcatchmentId', 'width': '100px'},
			{'name': '总降雨量mm', 'field': 'tPcpttionmm', 'width': '100px'},
	      {'name': '总径流mm', 'field': 'tRunoffmm', 'width': '100px'},
	      {'name': '径流系数', 'field': 'tRunoffCoeff', 'width': '100px'},
	      {'name': '实际年径流总控制率', 'field': 'aRateARV', 'width': '120px'}
		]];
		var linkFlowLayout = [[{'name': '管道', 'field': 'linkId', 'width': '200px'},
		{'name': '最大/最满深度', 'field': 'mfDepth', 'width': '100px'}
		]];
		var myMap;	  
    require([
    	"esri/basemaps",
      "esri/IdentityManager",
      "esri/layers/FeatureLayer",
      "esri/dijit/FeatureTable",
      "esri/geometry/webMercatorUtils",
      "esri/map",
      "dojo/dom-construct",
      "dojo/dom",
      "dojo/ready",
      "dojo/on", 
      "dojo/parser",      
      "dojo/_base/lang",
      "dijit/registry",
      "dijit/form/Button",
      "dijit/layout/ContentPane",
      "dijit/layout/BorderContainer",
      "esri/dijit/BasemapToggle",
      "esri/layers/ArcGISDynamicMapServiceLayer",
      "dojo/colors",
      "esri/symbols/SimpleFillSymbol",
      "esri/symbols/SimpleLineSymbol",
      "esri/renderers/ClassBreaksRenderer",  
      "esri/tasks/query",
      "esri/tasks/QueryTask",
      "dojox/grid/DataGrid",
      "dojo/data/ItemFileWriteStore",
      "dojo/domReady!"
    ], function (
    	esriBasemaps,IdentityManager, FeatureLayer, FeatureTable, webMercatorUtils, Map, domConstruct, dom, ready, on, parser, lang,registry, Button, ContentPane, 
    	BorderContainer, BasemapToggle, ArcGISDynamicMapServiceLayer,Color,SimpleFillSymbol, SimpleLineSymbol, ClassBreaksRenderer, Query, QueryTask,
    	DataGrid, ItemFileWriteStore
    ) {
      ready(function(){
		 		esriBasemaps.delorme = {
          baseMapLayers: [
              //中国矢量地图服务
              { url: "http://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer" }
          ],
          //缩略图
          thumbnailUrl: "images/mapIcon.jpg",
          title: "矢量图"
		    };		
				var mapOption = {};
				mapOption.center = esri.geometry.Point(114.2992, 35.745091);		
		
		    myMap = new Map("arcgisDiv", {
		        center: [114.2992, 35.745091],	// 117.1564,39.094
		        zoom: 11,
		        basemap: "delorme"
		      });
				//卫星底图  
		    var toggle = new BasemapToggle({  
		        map: myMap,  
		        basemap: "satellite"  
		    }, "BMapToggle");  
		    toggle.startup();
		    addLayerAndInfoTemplate(myMap, "http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer");

        on(dom.byId("SRunoffUp"),"click",function(){getTableData("sRunoffDataPrev", "sRunoffNode")});             
        on(dom.byId("SRunoffDown"),"click",function(){getTableData("sRunoffDataNext", "sRunoffNode")});                     
        on(dom.byId("LinkFlowUp"),"click",function(){getTableData("linkFlowDataPrev", "linkFlowNode")});  
        on(dom.byId("LinkFlowDown"),"click",function(){getTableData("linkFlowDataNext", "linkFlowNode")});  
        
        var tabContainer = dijit.byId("MyTab");	 
        dojo.connect(tabContainer,"_transition", function(newTab, oldTab){
        	if (newTab.id == "MapDiv"){
        		document.getElementById("LayersDiv").style.display="";
	      		document.getElementById("BMapToggle").style.display="";
	      	} else {
	      		document.getElementById("LayersDiv").style.display="none";
	      		document.getElementById("BMapToggle").style.display="none";
	        	if (newTab.id == "JunctionTab")
							loadFeatureTableTab(myMap, "http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer/1", "myJunctionNode");
	        	else if (newTab.id == "ConduitTab")
							loadFeatureTableTab(myMap, "http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer/2", "myConduitNode");
	        	else if (newTab.id == "SubcatchmentTab")
							loadFeatureTableTab(myMap, "http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer/3", "mySubcatchmentNode");
	        	else if (newTab.id == "SRunoffTab")
	        		loadDataGridTab(myMap, lang, ItemFileWriteStore, DataGrid, <%=pageBean.getSrfDataStr()%>, sRunoffLayout, "sRunoffNode");
	        	else if (newTab.id == "LinkFlowTab")
	        		loadDataGridTab(myMap, lang, ItemFileWriteStore, DataGrid, <%=pageBean.getLfsDataStr()%>, linkFlowLayout, "linkFlowNode");	        	
        	}
        });
      });
      
      document.getElementById("PrecipitationNode").innerHTML="Total Precipitation(mm): <%=pageBean.rptData.tpDepth%>";    

      function getTableData(dType, nodeName) { 
      	   var tmpStr = null;
      	   var pageStr = null;
          var xhr=getXHR();  
          xhr.onreadystatechange=function(){  
             if(xhr.readyState==4) {  
               if(xhr.status==200) {
       			  	if (xhr.responseText!=""){
       			  		pageStr = xhr.responseText.substring(0, xhr.responseText.indexOf("["));
       						tmpStr = eval('(' + xhr.responseText.substring(xhr.responseText.indexOf("[")) + ')'); 
      				  		  if (dType=="linkFlowDataPrev" || dType == "linkFlowDataNext"){
      				  				document.getElementById("LFSPageStr").innerHTML = pageStr;
      			  		  		loadDataGridTab(myMap, lang, ItemFileWriteStore, DataGrid, tmpStr, linkFlowLayout, nodeName);
      				  		  } else {
      				  				document.getElementById("SRFPageStr").innerHTML = pageStr;
      			  		  		loadDataGridTab(myMap, lang, ItemFileWriteStore, DataGrid, tmpStr, sRunoffLayout, nodeName);
      				  		  }
      				     	}
               }  
             }  
          }    
          xhr.open("GET","Page"+"?Name=Ajax&DataType="+dType+"&CurrentPageBean=RPTFileData");    
          xhr.send();  
       };        
			
    });
  </script>
      
</head>

<body class="claro">
  <div style="width: 100%; height: 100%;">   
  	<div id="BMapToggle"></div> 
    <div id="LayersDiv"></div>    
    <div id="MapBorder" dojoType="dijit.layout.BorderContainer" style="width: 100%; height: 100%">
      <div id="Title" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'" align="center">设计方案结果展示a (<%=pageBean.getDefaultPrjName()%>/<%=pageBean.getDefaultDsnName()%>/<%=pageBean.getDefaultINPName()%>/<%=pageBean.getDefaultRPTName()%>)</div>
	    <div id="MyTab" data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center'" style="height:100%">
        <div data-dojo-type="dijit/layout/ContentPane" id="MapDiv" title="方案地图展示" splitter="true" style="height:100%"><div id="arcgisDiv" class="mapClass"></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="JunctionTab" title="检查井参数"><div id="myJunctionNode"></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="ConduitTab" title="管道参数"><div id="myConduitNode"></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="SubcatchmentTab" title="汇水区参数"><div id="mySubcatchmentNode"></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="PrecipitationTab" title="总降雨量"><div id="PrecipitationNode" align="center"></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="SRunoffTab" title="汇水区径流汇总">
        	<div align="center"><button id="SRunoffUp" type="button">上一页</button>
			         <img src="images/clear.gif" width="5" height="1" border="0" alt=""/><button id="SRFPageStr" type="button" style="border:0;background:none"><%=pageBean.getSRFPageStr()%></button><img src="images/clear.gif" width="5" height="1" border="0" alt=""/> 
        			 <button id="SRunoffDown" type="button">下一页</button>
        	</div>
        	<div id="sRunoffNode" style="width: 100%; height: 100%"></div>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" id="LinkFlowTab" title="管道流量汇总">
        	<div align="center"><button id="LinkFlowUp" type="button">上一页</button>
			         <img src="images/clear.gif" width="5" height="1" border="0" alt=""/><button id="LFSPageStr" type="button" style="border:0;background:none"><%=pageBean.getLFSPageStr()%></button><img src="images/clear.gif" width="5" height="1" border="0" alt=""/> 
        			 <button id="LinkFlowDown">下一页</button>
        	</div>
        	<div id="linkFlowNode" style="width: 100%; height: 100%"></div>
        </div>           
        <div data-dojo-type="dijit/layout/ContentPane" id="SaveRPTResultsTab" title="方案结果保存">
        	<FORM name="Form1" id="Form1" action="Page" method="post">
						<input type="hidden" id="Name" name="Name" value="DisplayDesignSubmitPage" />	        	
						<input type="hidden" name="CurrentPageBean" value=<%=pageBean.getClass().getName()%> />
						<div style="font-size:10px;color:red;" align="left"><%=pageBean.getPageErrMsg()%></div>        
        		<div id="ConfirmationNode" align="center"><br/>
        			<input type="submit" value="认可本方案计算结果并保存"/><br/><br/>
        			<input type="button" onclick="document.getElementById('Name').value='<%=pageBean.getCancelPage()%>';document.getElementById('Form1').submit();" value="取消">	        						        		
        		</div>
        	</FORM>
        </div>
  	  </div>	    
		</div>
	</div>		

</body>

</html>