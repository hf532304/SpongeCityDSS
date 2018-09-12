<%@page import="com.scwe.dss.pagebean.MyMainPageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% MyMainPageBean pageBean = (MyMainPageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>海绵城市规划</title>
  <link rel="stylesheet" href="arcgis_js/dijit/themes/tundra/tundra.css" />
	<link rel="stylesheet" href="arcgis_js/esri/css/esri.css" />
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
      left: 0px;  
      top: 54px;  
      z-index: 50;
 			opacity: 0.8;
    }  
    #PageHeader {  
      position: absolute;  
      left: 0px;  
      top: 0px;  
      z-index: 50;
      opacity: 0.8;
    }              
    #LayersDiv {  
      position: absolute;  
      right: 0px;  
      bottom: 0px;  
      z-index: 50;
      opacity: 0.8;
      background-color: #4169E1;
      color: white;
    }     
    #MsgDiv {  
      position: absolute; 
      font-size:8px; 
      right: 0px;  
      top: 0px;  
      z-index: 50;
      opacity: 0.8;
      background-color: #4169E1;
      color: white;
    }       
  </style>

  <script>
	  var visible = [];
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
      "esri/tasks/QueryTask"
    ], function (
    	esriBasemaps,IdentityManager, FeatureLayer, FeatureTable, webMercatorUtils, Map, domConstruct, dom, ready, on, lang,registry, Button, ContentPane, 
    	BorderContainer, BasemapToggle, ArcGISDynamicMapServiceLayer,Color,SimpleFillSymbol, SimpleLineSymbol, ClassBreaksRenderer, Query,QueryTask
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
        
        myMap.on("load",function () {
         	myMap.hideZoomSlider();
          dom.byId("MsgDiv").innerHTML = "<%=pageBean.getMyInfoStr()%>";         	
        });        
      })  

    });

  </script>
      
</head>

<body class="tundra">
  <div style="width: 100%; height: 100%;">   
  	<div id="BMapToggle"></div> 
	  <div id="PageHeader">
			  <%@ include file="../includes/PageHeader.html" %>	  
	  </div>  	    
    <div id="LayersDiv"></div>
    <div id="MsgDiv" align="center"></div>
    	    	  
    <div id="MapBorder" dojoType="dijit.layout.BorderContainer" style="width: 100%; height: 100%">
      <div dojoType="dijit.layout.ContentPane" region="top" splitter="true" style="height:100%">    
	      <div id="arcgisDiv" class="mapClass">
	      </div>
	    </div>
	  </div>   
    <div id="TableBorder" dojoType="dijit.layout.BorderContainer" style="width: 100%;">
      <div id="tableDiv" dojoType="dijit.layout.ContentPane" region="top" splitter="true" style="width:98%;height:100%">    
	      <div id="myTableNode"></div>
	    </div> 
	  </div>
	  
	</div>
</body>

</html>