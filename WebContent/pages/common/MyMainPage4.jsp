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
  <link rel="stylesheet" href="http://localhost:8080/SpongeCityDSS/arcgis_js/dijit/themes/tundra/tundra.css" />
	<link rel="stylesheet" href="http://localhost:8080/SpongeCityDSS/arcgis_js/esri/css/esri.css" />
  <style type="text/css">
      body, html { font-family:helvetica,arial,sans-serif; font-size:90%; }
  </style>
  <script src="http://localhost:8080/SpongeCityDSS/js/javascript.js" type="text/javascript" language="JavaScript"></script>  
  <script src="http://localhost:8080/SpongeCityDSS/arcgis_js/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
        
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
    #layersDiv {  
      position: absolute;  
      right: 0px;  
      top: 80px;  
      z-index: 50;
      opacity: 0.8;
      background-color: #4169E1;
      color: white;
    } 
    #RendererDiv {  
      position: absolute;  
      right: 0px;  
      bottom: 0px;  
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
    
		    var _outfallsInfoTemplate = new esri.InfoTemplate();
		    _outfallsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");			
		    var _junctionsInfoTemplate = new esri.InfoTemplate();
		    _junctionsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");
		    var _conduitsInfoTemplate = new esri.InfoTemplate();
		    _conduitsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");
		    var _subcatchmentsInfoTemplate = new esri.InfoTemplate();
		    _subcatchmentsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");		    
		    var _outfallsInfoContent =
		        "<div class='demographicInfoContent'>" +
		        "RIMELEV: ${RIMELEV}<br/>" + 
		        "INVERTELEV: ${INVERTELEV}<br/>" +  
		        "MAXDEPTH: ${MAXDEPTH}<br/>" +
		        "MAXHGL: ${MAXHGL}<br/>" +
		        "TIMEMAXHGL: ${TIMEMAXHGL}" +
		        "</div>";
		    var _InfoContent =
		      "<div class='demographicInfoContent'>" +
		      "Tag: ${TAG}<br/>" + 
		      "DEPTH: ${DEPTH}<br/>" +  
		      "INVERT ELEV: ${INVERTELEV}<br/>" +
		      "RIM ELEV: ${RIMELEV}<br/>" +
		      "TIMEMAXHGL: ${TIMEMAXHGL}" +
		      "</div>";
		      var _conduitsInfoContent =
		          "<div class='demographicInfoContent'>" +
		          "Tag: ${TAG}<br/>" + 
		          "LENGTH: ${LENGTH}<br/>" +  
		          "SLOPE: ${SLOPE}<br/>" +
		          "MAXFLOW: ${MAXFLOW}<br/>" +
		          "TIMEMAXFLW: ${TIMEMAXFLW}" +
		          "</div>";
		      var _subcatchmentsInfoContent =
		          "<div class='demographicInfoContent'>" +
		          "RAINGAGE: ${RAINGAGE}<br/>" + 
		          "WIDTH: ${WIDTH}<br/>" +  
		          "SLOPE: ${SLOPE}<br/>" +
		          "ROUTING: ${ROUTING}<br/>" +
		          "RUNOFF COEF: ${RUNOFFCOEF}" +
		          "</div>";
		      _outfallsInfoTemplate.setContent("Outfalls for:<br>${X} ${Y}<br/>" + _outfallsInfoContent);
		      _junctionsInfoTemplate.setContent("Junctions for:<br>${X} ${Y}<br/>" + _InfoContent);
		      _conduitsInfoTemplate.setContent("Conduits for:<br>${INLETNODE} ${OUTLETNODE}<br/>" + _conduitsInfoContent);
		      _subcatchmentsInfoTemplate.setContent("Subcatchments for:<br>${X} ${Y}<br/>" + _subcatchmentsInfoContent);
		      
		   // var demographicsLayerURL = "http://123.206.254.127:6080/arcgis/rest/services/Hebi_Pre_Risk/MapServer"; //"http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer"; 
		    var demographicsLayerURL = "http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer"; 
		    var demographicsLayerOptions = {
		      "id": "demographicsLayer",
		      "opacity": 0.7,
		      "showAttribution": false
		    };
		    var demographicsLayer = new ArcGISDynamicMapServiceLayer(demographicsLayerURL, demographicsLayerOptions);
		    demographicsLayer.setInfoTemplates({
		    	0: { infoTemplate: _outfallsInfoTemplate },
		    	1: { infoTemplate: _junctionsInfoTemplate },
		    	2: { infoTemplate: _conduitsInfoTemplate },     	
		    	3: { infoTemplate: _subcatchmentsInfoTemplate }
		    });
		    demographicsLayer.setVisibleLayers([0,1,2,3]);
		    myMap.addLayer(demographicsLayer);

		    dojo.connect(demographicsLayer, "onLoad", loadLayerList);
		    function loadLayerList(layers) {
	
		        var html = "";
		        var infos = layers.layerInfos;
		        var length = infos.length; 
		        for (i = 0; i < length; i++) {
		            var info = infos[i];
		            //图层默认显示的话就把图层id添加到visible
		            if (info.defaultVisibility) {
		                visible.push(info.id);
		            }
		            //输出图层列表的html
		            html = html + "<div><input id='" + info.id + "' name='layerList' class='listCss' type='checkbox' value='checkbox' onclick='setVisibility();' " + (info.defaultVisibility ? "checked" : "") + " />" + info.name + "</div>";
		        }       
		        //设置可视图层
		        demographicsLayer.setVisibleLayers(visible);
		        //在右边显示图层名列表
		        dojo.byId("layersDiv").innerHTML = html;
		    }

		    setVisibility = function () {
		    	setLayerVisibility(demographicsLayer, ".listCss");
		    }		    
		         
        var fBtn=dom.byId("FeatureBTN");
        on(fBtn,"click",function(){
					loadFeatureTable(myMap, "http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer/3","MapBorder","TableBorder");
        });        
        var rBtn=dom.byId("RendererBTN");
        on(rBtn,"click",function(){
      	  conduitsRenderer(myMap,"http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer/2");        	  
        }); 
        var cBtn=dom.byId("CancelRendererBTN");
        on(cBtn,"click",function(){
      	  cancelConduitsRenderer(myMap);
        });          
        var dBtn=dom.byId("DisableFeatureBTN");
        on(dBtn,"click",function(){
      	  disableFeatureTable("MapBorder","TableBorder");
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
    <div id="layersDiv"></div>
    <div id="RendererDiv">
    	<button id="RendererBTN">渲染</button><button id="CancelRendererBTN">取消</button><br/><button id="FeatureBTN">参数</button><button id="DisableFeatureBTN">隐藏</button>
    </div> 
    	    	  
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